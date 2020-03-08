package com.imooc.coupon.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * <h1>产品线</h1>
 * @author cyw
 */
@Getter
@AllArgsConstructor
public enum ProductLine {

    DAMAO("大猫",1),
    DABAO("大宝",2)
    ;

    /** 产品线描述 */
    private String description;
    /** 产品线编码 */
    private Integer code;

    public static ProductLine of(Integer code){
        Objects.requireNonNull(code,"code is empty");

        return Stream.of(values())
                .filter(e->e.getCode().equals(code))
                .findAny()
                .orElseThrow(()->new IllegalArgumentException(code+" not exists!") );
    }

}
