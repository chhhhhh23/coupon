package com.imooc.coupon.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * <h1>分发目标</h1>
 * @author cyw
 */
@Getter
@AllArgsConstructor
public enum DistributeTarget {

    SINGLE("单用户",1),//用户需要自己去领取优惠券
    MULTI("多用户",2),//批量分发优惠券，用户不用自己去领取
    ;

    /** 分发目标描述 */
    private String description;
    /** 分发目标编码 */
    private Integer code;

    public static DistributeTarget of(Integer code){
        Objects.requireNonNull(code,"code is empty");

        return Stream.of(values())
                .filter(e->e.getCode().equals(code))
                .findAny()
                .orElseThrow(()->new IllegalArgumentException(code+" not exists!") );
    }

}
