/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.annotation;

import com.robotmonitor.common.enums.OperationType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.METHOD})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface AutoFill {
    public OperationType value();
}
