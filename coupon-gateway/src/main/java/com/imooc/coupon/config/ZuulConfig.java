package com.imooc.coupon.config;

import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.RateLimitKeyGenerator;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.properties.RateLimitProperties;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.repository.DefaultRateLimiterErrorHandler;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.repository.RateLimiterErrorHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cyw
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(DemoRateLimitProperty .class)
public class ZuulConfig {

    //@Bean
    //public RateLimitKeyGenerator rateLimitKeyGenerator(){
    //    return new RateLimitKeyGenerator() {
    //        @Override
    //        public String key(HttpServletRequest request, Route route, RateLimitProperties.Policy policy) {
    //            log.info("==========================");
    //            return null;
    //        }
    //    };
    //}

    //@Bean
    //public RateLimiter rateLimiter() {
    //    return new RateLimiter() {
    //        @Override
    //        public Rate consume(RateLimitProperties.Policy policy, String key, Long requestTime) {
    //            return null;
    //        }
    //    };
    //}

    @Bean
    public RateLimiterErrorHandler rateLimitErrorHandler() {
        return new DefaultRateLimiterErrorHandler() {
            @Override
            public void handleSaveError(String key, Exception e) {
                // custom code
                log.error("保存key:[{}]异常", key, e);
            }

            @Override
            public void handleFetchError(String key, Exception e) {
                // custom code
                log.error("路由失败:[{}]异常", key);
            }

            @Override
            public void handleError(String msg, Exception e) {
                // custom code
                log.error("限流异常:[{}]", msg, e);
            }
        };
    }
}
