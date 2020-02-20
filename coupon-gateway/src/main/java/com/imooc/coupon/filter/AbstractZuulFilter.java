package com.imooc.coupon.filter;

import com.imooc.coupon.dto.R;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.imooc.coupon.util.JsonUtils ;

/**
 * <h1>通用的抽象过滤器类</h1>
 *
 * @author cyw
 */
public abstract class AbstractZuulFilter extends ZuulFilter {

    //用于在过滤器中传递消息，数据保存在ThreadLocal中
    //并且扩展了ConcurrentHashMap
    RequestContext context;

    private static final String NEXT = "next";

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        /* spring cloud内置的过滤器是没有 next这个键值对的 所以需要设置默认值 true */
        return (boolean) ctx.getOrDefault(NEXT, true);
    }

    @Override
    public Object run() throws ZuulException {
        context = RequestContext.getCurrentContext();
        return cRun();
    }

    protected abstract Object cRun();

    Object fail(int code,String message){
        context.set(NEXT,false);
        context.setSendZuulResponse(false);

        context.getResponse().setContentType("application/json;charset=utf-8");
        context.setResponseStatusCode(code);
        context.setResponseBody(JsonUtils.toJson(R.fail(code,message)));
        return null;
    }

    Object success(){
        context.set(NEXT,true);
        return null;
    }

}
