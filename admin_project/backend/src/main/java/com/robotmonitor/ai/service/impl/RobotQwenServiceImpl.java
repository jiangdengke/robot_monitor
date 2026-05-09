/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.robot.RobotChatResponse
 *  com.robotmonitor.common.core.domain.robot.RobotListenQwenRequest
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.ai.service.impl;

import com.robotmonitor.ai.service.RobotQwenService;
import com.robotmonitor.common.core.domain.robot.RobotChatResponse;
import com.robotmonitor.common.core.domain.robot.RobotListenQwenRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RobotQwenServiceImpl
implements RobotQwenService {
    private static final Logger log = LoggerFactory.getLogger(RobotQwenServiceImpl.class);

    @Override
    public RobotChatResponse intentDetection(RobotListenQwenRequest robotListenQwenRequest) {
        return null;
    }
}
