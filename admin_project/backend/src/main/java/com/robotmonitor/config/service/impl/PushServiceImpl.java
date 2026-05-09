/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.ai.PushMessage
 *  com.robotmonitor.common.utils.JsonUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.data.redis.core.RedisTemplate
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.config.service.impl;

import com.robotmonitor.common.core.domain.ai.PushMessage;
import com.robotmonitor.common.utils.JsonUtils;
import com.robotmonitor.config.service.IPushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class PushServiceImpl
implements IPushService {
    private static final Logger log = LoggerFactory.getLogger(PushServiceImpl.class);
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void push(PushMessage message) {
        log.info("receive push message : {}", (Object)JsonUtils.obj2String((Object)message));
        this.redisTemplate.convertAndSend("redis.websocket.push", (Object)JsonUtils.obj2String((Object)message));
    }
}
