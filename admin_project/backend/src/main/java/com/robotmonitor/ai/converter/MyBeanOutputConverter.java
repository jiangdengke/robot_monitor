/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  org.springframework.ai.converter.BeanOutputConverter
 *  org.springframework.core.ParameterizedTypeReference
 *  org.springframework.lang.NonNull
 */
package com.robotmonitor.ai.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robotmonitor.ai.utils.AiUtils;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.lang.NonNull;

public class MyBeanOutputConverter<T>
extends BeanOutputConverter<T> {
    public MyBeanOutputConverter(Class clazz) {
        super(clazz);
    }

    public MyBeanOutputConverter(Class clazz, ObjectMapper objectMapper) {
        super(clazz, objectMapper);
    }

    public MyBeanOutputConverter(ParameterizedTypeReference typeRef) {
        super(typeRef);
    }

    public MyBeanOutputConverter(ParameterizedTypeReference typeRef, ObjectMapper objectMapper) {
        super(typeRef, objectMapper);
    }

    public T convert(@NonNull String text) {
        text = AiUtils.removeThinkTag(text);
        return (T)super.convert(text);
    }
}
