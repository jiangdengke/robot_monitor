/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  org.springframework.data.redis.core.StringRedisTemplate
 */
package com.robotmonitor.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisRequestClient {
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RedisRequestClient(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public <T> T sendAndReceive(String queueName, Object request, Class<T> responseType, int timeoutSeconds) {
        try {
            String correlationId = UUID.randomUUID().toString();
            String taskJson = this.objectMapper.writeValueAsString(request);
            this.redisTemplate.opsForList().rightPush((Object)queueName, (Object)(correlationId + "|" + taskJson));
            String resultKey = queueName + "Result:" + correlationId;
            long startTime = System.currentTimeMillis();
            while (true) {
                String resultJson;
                if ((resultJson = (String)this.redisTemplate.opsForValue().get((Object)resultKey)) != null) {
                    this.redisTemplate.delete((Object)resultKey);
                    return (T)this.objectMapper.readValue(resultJson, responseType);
                }
                if (System.currentTimeMillis() - startTime > (long)(timeoutSeconds * 1000)) {
                    throw new RuntimeException("\u7b49\u5f85\u5ba2\u6237\u7aef\u5904\u7406\u8d85\u65f6");
                }
                Thread.sleep(50L);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Redis\u8bf7\u6c42\u5f02\u5e38: " + e.getMessage(), e);
        }
    }
}
