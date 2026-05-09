/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package com.robotmonitor.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

public static class JsonUtils.JsonBuilder {
    private Map<String, Object> map = new HashMap<String, Object>();

    JsonUtils.JsonBuilder() {
    }

    public JsonUtils.JsonBuilder put(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

    public String build() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this.map);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }
}
