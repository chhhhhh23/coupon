package com.imooc.coupon.util;

import javax.servlet.http.HttpServletRequest;

public class IpUtils {
    private final static String[] headers = new String[]{
            "X-Real-IP",
            "X-Forwarded-For",
    };

    public static String getIp(HttpServletRequest request) {
        for (String header : headers) {
            String requestHeader = request.getHeader(header);
            if (requestHeader != null) return requestHeader;
        }
        return request.getRemoteAddr();
    }
}
