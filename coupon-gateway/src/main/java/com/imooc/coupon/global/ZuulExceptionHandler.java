package com.imooc.coupon.global;

import com.imooc.coupon.dto.R;
import com.imooc.coupon.exception.CouponException;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class ZuulExceptionHandler {

    @ExceptionHandler(value = {ZuulException.class})
    public R handleZuulException(CouponException ex) {
        return R.fail(ex.getMessage());
    }

}

