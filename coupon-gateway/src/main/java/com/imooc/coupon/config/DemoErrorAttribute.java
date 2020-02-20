package com.imooc.coupon.config;


import com.imooc.coupon.dto.R;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class DemoErrorAttribute extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest requestAttributes, boolean includeStackTrace) {

        Map<String, Object> result = super.getErrorAttributes(requestAttributes, includeStackTrace);
        //不是状态码429就不用自定义返回处理;
        if(!result.get("status").equals(429)){ 
            return result;
        }else {
            //修改返回状态码为200
            requestAttributes.setAttribute("javax.servlet.error.status_code",200,RequestAttributes.SCOPE_REQUEST);
            //自定义消息体 需要返回一个map，如果是实体类，可以转为map再返回
            RequestContext context = RequestContext.getCurrentContext();
            // TODO: 需求 获取被限流方法名称  + 用户ip --->日志记录
            context.getRequest().getLocalName();
            Map<String,Object> map = new HashMap<>();
            map.put("code", R.FAIL);
            map.put("message","请勿频繁请求");
            return map;
        }

    }
}