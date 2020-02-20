package com.imooc.coupon.exception;

import java.util.Objects;
import java.util.function.Supplier;

public class CouponException extends RuntimeException {
    private static final long serialVersionUID = 8128870510942907415L;

    public CouponException() {
    }

    public CouponException(String message) {
        super(message);
    }

    public CouponException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouponException(Throwable cause) {
        super(cause);
    }

    public CouponException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static Supplier<CouponException> err(String message) {
        Objects.requireNonNull(message);
        return () -> new CouponException(message);
    }
}
