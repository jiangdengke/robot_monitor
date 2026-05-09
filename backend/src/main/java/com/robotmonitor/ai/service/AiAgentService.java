/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.robot.RobotChatRequest
 *  com.robotmonitor.common.core.domain.robot.RobotChatResponse
 *  com.robotmonitor.common.core.domain.robot.RobotListenQwenRequest
 */
package com.robotmonitor.ai.service;

import com.robotmonitor.common.core.domain.robot.RobotChatRequest;
import com.robotmonitor.common.core.domain.robot.RobotChatResponse;
import com.robotmonitor.common.core.domain.robot.RobotListenQwenRequest;

public interface AiAgentService {
    public RobotChatResponse chat(RobotChatRequest var1);

    public RobotChatResponse unitreeChat(RobotChatRequest var1);

    public RobotChatResponse chatOnly(RobotChatRequest var1);

    public RobotChatResponse intentDetection(RobotListenQwenRequest var1);
}
