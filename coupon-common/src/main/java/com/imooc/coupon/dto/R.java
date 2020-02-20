package com.imooc.coupon.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class R<T> implements Serializable {

    private static final long serialVersionUID = 510019869310111711L;

    public final static int SUCCESS = 1;
    public final static int FAIL = 0;
    public final static String SUCCESS_MSG = "成功";
    public final static String FAIL_MSG = "失败";

    private Integer code;
    private String message;
    private T data;

    public R() {

    }

    public R(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public R(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 通用静态方法
     */
    public static R success(){
         return new R(SUCCESS,SUCCESS_MSG,null);
    }

    public static <T> R<T> success(T data){
        return new R(SUCCESS,SUCCESS_MSG,data);
    }

    public static <T> R<T> success(String message , T data){
        return new R(SUCCESS,message,data);
    }

    public static R fail(){
        return new R(FAIL,FAIL_MSG,null);
    }

    public static <T> R<T> fail(T data){
        return new R(FAIL,FAIL_MSG,data);
    }

    public static <T> R<T> fail(String message){
        return new R(FAIL,message);
    }

    public static <T> R<T> fail(int code, String message){
        return new R(FAIL,message);
    }

    public static <T> R<T> fail(String message , T data){
        return new R(FAIL,message, data);
    }

    public static <T> R<T> fail(int code, String message , T data){
        return new R(code,message, data);
    }


}