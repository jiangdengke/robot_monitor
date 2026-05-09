/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.robot.RobotVoice
 *  com.robotmonitor.common.core.domain.robot.RobotVoiceResponse
 *  reactor.core.publisher.Mono
 */
package com.robotmonitor.ai.service;

import com.robotmonitor.common.core.domain.robot.RobotVoice;
import com.robotmonitor.common.core.domain.robot.RobotVoiceResponse;
import reactor.core.publisher.Mono;

public interface RobotVoiceService {
    public Mono<RobotVoiceResponse> listen(RobotVoice var1);
}
