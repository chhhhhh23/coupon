package com.imooc.coupon.exception;

import java.util.Objects;
import java.util.function.Supplier;

public class ServiceException extends RuntimeException {
    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static Supplier<ServiceException> err(String message) {
        Objects.requireNonNull(message);
        return () -> new ServiceException(message);
    }
}
