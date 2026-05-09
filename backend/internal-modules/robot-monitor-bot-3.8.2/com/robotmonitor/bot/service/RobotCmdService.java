/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.robot.RobotHttpCmd
 *  com.robotmonitor.common.core.domain.robot.RobotTaskCmd
 *  com.robotmonitor.common.core.domain.robot.RobotVideoStreamCmd
 */
package com.robotmonitor.bot.service;

import com.robotmonitor.common.core.domain.robot.RobotHttpCmd;
import com.robotmonitor.common.core.domain.robot.RobotTaskCmd;
import com.robotmonitor.common.core.domain.robot.RobotVideoStreamCmd;

public interface RobotCmdService {
    public void sendCmd(RobotTaskCmd var1);

    public void sendVoiceCmd(RobotTaskCmd var1);

    public void sendHttpCmd(RobotHttpCmd var1);

    public void sendVideoStreamCmd(RobotVideoStreamCmd var1);
}
