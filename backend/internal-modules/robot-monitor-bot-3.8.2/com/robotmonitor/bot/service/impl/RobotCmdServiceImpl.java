/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.robot.RobotCmdLog
 *  com.robotmonitor.common.core.domain.robot.RobotHttpCmd
 *  com.robotmonitor.common.core.domain.robot.RobotTaskCmd
 *  com.robotmonitor.common.core.domain.robot.RobotVideoStreamCmd
 *  com.robotmonitor.common.utils.JsonUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.data.redis.core.RedisTemplate
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.bot.service.impl;

import com.robotmonitor.bot.service.IRobotCmdLogService;
import com.robotmonitor.bot.service.RobotCmdService;
import com.robotmonitor.common.core.domain.robot.RobotCmdLog;
import com.robotmonitor.common.core.domain.robot.RobotHttpCmd;
import com.robotmonitor.common.core.domain.robot.RobotTaskCmd;
import com.robotmonitor.common.core.domain.robot.RobotVideoStreamCmd;
import com.robotmonitor.common.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RobotCmdServiceImpl
implements RobotCmdService {
    private static final Logger log = LoggerFactory.getLogger(RobotCmdServiceImpl.class);
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IRobotCmdLogService robotCmdLogService;

    @Override
    public void sendCmd(RobotTaskCmd robotCmd) {
        String cmdStr = JsonUtils.obj2String((Object)robotCmd);
        log.info("\u53d1\u9001\u673a\u5668\u4eba\u6307\u4ee4 \uff1a {}", (Object)cmdStr);
        this.saveLog("" + robotCmd.getRobot_id(), cmdStr, "CMD");
        this.redisTemplate.convertAndSend("redis.robot.cmd", (Object)cmdStr);
    }

    @Override
    public void sendVoiceCmd(RobotTaskCmd robotTaskCmd) {
        String cmdStr = JsonUtils.obj2String((Object)robotTaskCmd);
        log.info("\u53d1\u9001\u673a\u5668\u4eba\u8bed\u97f3\u6307\u4ee4 \uff1a {}", (Object)cmdStr);
        this.saveLog("" + robotTaskCmd.getRobot_id(), cmdStr, "VOICE");
        this.redisTemplate.convertAndSend("redis.robot.voice", (Object)cmdStr);
    }

    @Override
    public void sendHttpCmd(RobotHttpCmd robotHttpCmd) {
        String cmdStr = JsonUtils.obj2String((Object)robotHttpCmd);
        log.info("\u53d1\u9001\u673a\u5668\u4ebahttp\u6307\u4ee4 \uff1a {}", (Object)cmdStr);
        this.saveLog("" + robotHttpCmd.getRobotId(), cmdStr, "HTTP");
        this.redisTemplate.convertAndSend("redis.robot.http", (Object)cmdStr);
    }

    @Override
    public void sendVideoStreamCmd(RobotVideoStreamCmd robotVideoStreamCmd) {
        String cmdStr = JsonUtils.obj2String((Object)robotVideoStreamCmd);
        log.info("\u53d1\u9001\u673a\u5668\u4eba\u89c6\u9891\u6d41\u6307\u4ee4 \uff1a {}", (Object)cmdStr);
        this.saveLog(robotVideoStreamCmd.getRobotId(), cmdStr, "VIDEO");
        this.redisTemplate.convertAndSend("redis.robot.video", (Object)cmdStr);
    }

    private void saveLog(String robotId, String cmd, String cmdType) {
        RobotCmdLog robotCmdLog = new RobotCmdLog();
        robotCmdLog.setRobotId(robotId);
        robotCmdLog.setCmd(cmd);
        robotCmdLog.setCmdType(cmdType);
        this.robotCmdLogService.insertRobotCmdLog(robotCmdLog);
    }
}
