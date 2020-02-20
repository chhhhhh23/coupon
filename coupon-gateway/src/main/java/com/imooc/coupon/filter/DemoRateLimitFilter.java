package com.imooc.coupon.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.imooc.coupon.config.DemoRateLimitProperty;
import com.imooc.coupon.util.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author cyw

@Slf4j
@Component
//如果开启RateLimit那么该过滤器不起作用
@ConditionalOnProperty(prefix = "zuul.ratelimit", name = "enabled", value = "false")
public class DemoRateLimitFilter extends AbstractPreZuulFilter {

    @Autowired
    private DemoRateLimitProperty demoRateLimitProperty;
    private static final String pre = "[%s]";
 */
    /**
     * 每秒获得两个令牌

    RateLimiter rateLimiter = RateLimiter.create(2.0);

    @Override
    protected Object cRun() {
        Boolean enabled = demoRateLimitProperty.getEnabled();
        Boolean limitSuccess;
        if (enabled) {
            HttpServletRequest request = context.getRequest();
            Map<String, String> map = demoRateLimitProperty.getLimitConfig();
            String uri = request.getRequestURI();
            String limit = map.get(String.format(pre, uri));
            //todo 以用户ip为key 存入redis 判断
            log.info(String.format("%s 访问---> %s", IpUtils.getIp(request), uri));
            limitSuccess=true;//限流成功
        }
        limitSuccess=false;//限流失败
        return enabled?
                limitSuccess?success():fail(429,"请勿频繁访问")
                :null;
    }

    @Override
    public int filterOrder() {
        return 2;
    }

}
     */