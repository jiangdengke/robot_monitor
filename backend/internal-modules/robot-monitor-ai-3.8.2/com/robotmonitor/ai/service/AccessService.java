/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.robot.Admittance
 *  com.robotmonitor.common.core.domain.robot.RobotChatResponse
 */
package com.robotmonitor.ai.service;

import com.robotmonitor.common.core.domain.robot.Admittance;
import com.robotmonitor.common.core.domain.robot.RobotChatResponse;

public interface AccessService {
    public RobotChatResponse validateAdmittance(Admittance var1);
}
