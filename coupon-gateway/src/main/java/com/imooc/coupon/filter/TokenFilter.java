package com.imooc.coupon.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cyw
 */
@Slf4j
@Component
public class TokenFilter extends AbstractPreZuulFilter {

    private static final String TOKEN="token";

    @Override
    protected Object cRun() {
        HttpServletRequest request = context.getRequest();
        log.info(String.format("%s request to %s",
                request.getMethod(),request.getRequestURI()));
        Object token = request.getParameter(TOKEN);
        // TODO:
        //if (null==token){
        //    log.error("error:token is Empty");
        //    //请求重新登录页面
        //    return fail(401,"error:token is Empty");
        //}
        return success();
    }

    @Override
    public int filterOrder() {
        return 1;
    }
}
