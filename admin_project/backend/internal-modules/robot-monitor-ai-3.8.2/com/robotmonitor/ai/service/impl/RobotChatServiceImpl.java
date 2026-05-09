/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.bot.domain.RobotHttpCmdRequest
 *  com.robotmonitor.bot.service.RobotService
 *  com.robotmonitor.common.core.redis.RedisCache
 *  com.robotmonitor.config.service.IConfigRobotService
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.data.redis.core.StringRedisTemplate
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.ai.service.impl;

import com.robotmonitor.ai.service.RobotChatService;
import com.robotmonitor.bot.domain.RobotHttpCmdRequest;
import com.robotmonitor.bot.service.RobotService;
import com.robotmonitor.common.core.redis.RedisCache;
import com.robotmonitor.config.service.IConfigRobotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RobotChatServiceImpl
implements RobotChatService {
    private static final Logger log = LoggerFactory.getLogger(RobotChatServiceImpl.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private RobotService robotService;
    @Autowired
    private IConfigRobotService configRobotService;
    private static final String PREFIX = "chat:conversation";

    @Override
    public boolean resetMemory(String robotId, boolean stopListen) {
        log.info("delete redis key : {}", (Object)(PREFIX + robotId));
        this.stringRedisTemplate.delete((Object)(PREFIX + robotId));
        this.redisCache.deleteObject("robot_chat_status:robot_id_" + robotId);
        if (stopListen) {
            this.robotService.setRobotState(new RobotHttpCmdRequest(Long.parseLong(robotId), 2));
        }
        return true;
    }
}
