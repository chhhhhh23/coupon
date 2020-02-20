package com.imooc.coupon.util;

import com.imooc.coupon.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CheckUtils {
    private static Validator validator;

    @Autowired
    public CheckUtils(Validator validator) {
        CheckUtils.validator = validator;
    }

    public static void checkNull(Object o, String entityName) {
        if (o == null) {
            throw new ServiceException(entityName + "不能为空");
        }
    }

    public static void checkEmpty(String o, String entityName) {
        if (StringUtils.isEmpty(o)) {
            throw new ServiceException(entityName + "不能为空");
        }
    }

    public static void checkEmpty(Collection<?> c, String entityName) {
        if (c == null || c.isEmpty()) {
            throw new ServiceException(entityName + "不能为空");
        }
    }

    public static void checkEquals(Object o, String entityName, Object... choice) {
        checkNull(o, entityName);

        Arrays.stream(choice)
                .filter(Objects::nonNull)
                .filter(c -> Objects.equals(c, o))
                .findAny()
                .orElseThrow(() -> new ServiceException(entityName + "的值不合法"));
    }

    public static void check(Object obj, String entityName) {
        checkNull(obj, entityName);
        Set<ConstraintViolation<Object>> validate = validator.validate(obj, Default.class);

        String errMsg = validate.stream()
                .map(ConstraintViolation::getMessage)
                .filter(StringUtils::isNoneEmpty)
                .collect(Collectors.joining(";"));

        if (StringUtils.isNotEmpty(errMsg)) {
            throw new ServiceException(errMsg);
        }
    }
}
