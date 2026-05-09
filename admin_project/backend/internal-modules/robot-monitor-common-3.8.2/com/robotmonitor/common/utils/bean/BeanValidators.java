/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.validation.ConstraintViolationException
 *  jakarta.validation.Validator
 */
package com.robotmonitor.common.utils.bean;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;

public class BeanValidators {
    public static void validateWithException(Validator validator, Object object, Class<?> ... groups) throws ConstraintViolationException {
        Set constraintViolations = validator.validate(object, (Class[])groups);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}
