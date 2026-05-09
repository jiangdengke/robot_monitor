/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.robot.RobotStatus
 *  com.robotmonitor.common.core.redis.RedisCache
 *  com.robotmonitor.config.mapper.ConfigRobotMapper
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 */
package com.robotmonitor.quartz.task;

import com.robotmonitor.common.core.domain.robot.RobotStatus;
import com.robotmonitor.common.core.redis.RedisCache;
import com.robotmonitor.config.mapper.ConfigRobotMapper;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="updateRobotStatusTask")
public class UpdateRobotStatusTask {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ConfigRobotMapper configRobotMapper;

    public void updateRobotStatus() {
        Set redisRobotKeys = this.redisCache.getKeysByPattern("robot_current_status:robot_id_*");
        for (String robotRedisKye : redisRobotKeys) {
            RobotStatus robotStatus = (RobotStatus)this.redisCache.getCacheObject(robotRedisKye);
            this.configRobotMapper.updateRobotStatus("" + robotStatus.getRobot_id(), Long.valueOf(robotStatus.getBattery_state()), robotStatus.isCharging_state() ? "1" : "0", robotStatus.isWorking_state() ? "1" : "0", robotStatus.isStandby_state() ? "1" : "0", robotStatus.getPositioning_state(), robotStatus.isRobot_error() ? "1" : "0", robotStatus.getError_messages(), Long.valueOf(robotStatus.getTask_id()));
        }
    }
}
