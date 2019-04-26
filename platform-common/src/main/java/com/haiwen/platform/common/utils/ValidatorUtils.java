package com.haiwen.platform.common.utils;


import com.google.common.base.Joiner;
import com.haiwen.platform.common.exception.PlatformException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 校验工具类
 **/
public class ValidatorUtils {
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public static void validateEntity(Object object, Class<?>... groups)
            throws PlatformException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
        if (!constraintViolations.isEmpty()) {
            List<String> list = new ArrayList<>();
            for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
                list.add(constraintViolation.getMessage());
            }
            throw new PlatformException(Joiner.on(", ").join(list));
        }
    }
}
