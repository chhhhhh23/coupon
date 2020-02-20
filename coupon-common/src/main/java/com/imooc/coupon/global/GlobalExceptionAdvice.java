package com.imooc.coupon.global;

import com.imooc.coupon.dto.R;
import com.imooc.coupon.exception.CouponException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler(value = {CouponException.class})
    public R handleServiceException(CouponException ex) {
        return R.fail(ex.getMessage());
    }

    @ExceptionHandler(value = {BindException.class})
    public R handleBindException(BindException ex) {
        List<FieldError> fieldErrors = ex.getFieldErrors();
        return handleFieldErrors(fieldErrors);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        return handleFieldErrors(fieldErrors);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public R handleMethodArgumentNotValidException(ConstraintViolationException ex) {
        String errMsg = ex.getConstraintViolations().stream()
                .filter(Objects::nonNull)
                .map(ConstraintViolation::getMessage)
                .filter(StringUtils::isNoneEmpty)
                .collect(Collectors.joining(";"));
        return R.fail(errMsg);
    }

    private R handleFieldErrors(List<FieldError> fieldErrors) {
        String errMsg = fieldErrors.stream()
                .filter(Objects::nonNull)
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(StringUtils::isNoneEmpty)
                .collect(Collectors.joining(";"));
        return R.fail(errMsg);
    }
}
