package com.imooc.coupon.config;

import com.imooc.coupon.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author cyw
 */
@Slf4j
@EnableAsync(proxyTargetClass = true)
@Configuration
public class AsyncThreadPoolConfig implements AsyncConfigurer {

    /**
     *  返回自定的一个线程池
     */
    @Bean("taskExecutor")
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor  executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(20);
        executor.setKeepAliveSeconds(60);
        //拒绝策略
        executor.setThreadNamePrefix("taskExecutor-");
        executor.setRejectedExecutionHandler(
                new ThreadPoolExecutor.AbortPolicy()
        );

        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(600);

        executor.initialize();
        return executor;
    }

    /**
     *  定义异步任务异常处理类
     *      只处理没有返回值的异步任务！
     *      那么有返回值的异步任务抛出异常怎么办？
     *          客户端获取结果的时候就会抛出异常--->客户端进行对异常的处理
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncExceptionHandler();
    }

    class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler{

        @Override
        public void handleUncaughtException(Throwable throwable, Method method, Object... params) {
            log.error("AsyncError:{} , Method:{} ,Param{}",
                    throwable.getMessage(),
                    method.getName(),
                    JsonUtils.toJson(params)
                    );
            throwable.printStackTrace();

            // TODO: 发送邮件或者短信
        }
    }
}
