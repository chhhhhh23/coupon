package com.imooc.coupon.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * <h1>过期时间类型</h1>
 * @author cyw
 */
@Getter
@AllArgsConstructor
public enum  PeriodType {

    REGULAR("固定过期时间",1),
    SHIFT("变动的过期时间(领取之日开始计算)",2),//从用户领取到优惠券才开始计时
    ;

    /** 分发目标描述 */
    private String description;
    /** 分发目标编码 */
    private Integer code;

    public static PeriodType of(Integer code){
        Objects.requireNonNull(code,"code is empty");

        return Stream.of(values())
                .filter(e->e.getCode().equals(code))
                .findAny()
                .orElseThrow(()->new IllegalArgumentException(code+" not exists!") );
    }
}
