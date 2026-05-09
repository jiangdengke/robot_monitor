/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.config.HandshakeBusinessLogic
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.ai.service.impl;

import com.robotmonitor.ai.service.RobotChatService;
import com.robotmonitor.common.config.HandshakeBusinessLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RobotHandshakeServiceImpl
implements HandshakeBusinessLogic {
    private static final Logger log = LoggerFactory.getLogger(RobotHandshakeServiceImpl.class);
    @Autowired
    private RobotChatService robotChatService;

    public void handle(String robotId) {
        log.info("websocket\u8fde\u63a5\u65f6\u6e05\u7a7a\u673a\u5668\u4eba\u8bb0\u5fc6\uff0crobotId : {}", (Object)robotId);
        this.robotChatService.resetMemory(robotId, false);
    }
}
