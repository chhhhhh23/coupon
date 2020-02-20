package com.imooc.coupon.controller;

import com.imooc.coupon.dto.R;
import com.imooc.coupon.exception.CouponException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cyw
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/limit")
    public R testLimit(@RequestParam String name) {
        return R.success(name + "真的很不错");
    }

    @GetMapping("/wrong")
    public R wrong() {
        throw new CouponException("asdasda");
        //return Resp.success("真的很不错");
    }

}
