/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.bot.service.RobotTaskSchedulerService
 *  com.robotmonitor.common.core.redis.RedisCache
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 */
package com.robotmonitor.quartz.task;

import com.robotmonitor.bot.service.RobotTaskSchedulerService;
import com.robotmonitor.common.core.redis.RedisCache;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="goHomeTask")
public class GoHomeTask {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private RobotTaskSchedulerService robotTaskSchedulerService;

    public void goHome() {
        Set redisRobotKeys = this.redisCache.getKeysByPattern("robot_is_at_home_flag:*");
        for (String robotRedisKye : redisRobotKeys) {
            String robotId = robotRedisKye.substring(robotRedisKye.lastIndexOf(":") + 1);
            this.robotTaskSchedulerService.geHome(robotId);
        }
    }
}
