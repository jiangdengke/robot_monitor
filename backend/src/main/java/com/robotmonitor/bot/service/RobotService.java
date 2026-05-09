/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.config.ConfigTask
 *  com.robotmonitor.common.core.domain.robot.RobotPosition
 *  com.robotmonitor.common.core.domain.robot.RobotTask
 */
package com.robotmonitor.bot.service;

import com.robotmonitor.bot.domain.CustomerNotificationRequest;
import com.robotmonitor.bot.domain.RobotHttpCmdRequest;
import com.robotmonitor.bot.domain.RobotMoveRequest;
import com.robotmonitor.bot.domain.RobotSimpleCmdRequest;
import com.robotmonitor.common.core.domain.config.ConfigTask;
import com.robotmonitor.common.core.domain.robot.RobotPosition;
import com.robotmonitor.common.core.domain.robot.RobotTask;
import java.util.List;

public interface RobotService {
    public void sendCmd(RobotSimpleCmdRequest var1);

    public RobotTask guide(RobotMoveRequest var1);

    public void setRobotState(RobotHttpCmdRequest var1);

    public RobotTask notifyCustomer(CustomerNotificationRequest var1);

    public RobotPosition getRobotPosition(String var1);

    public List<RobotPosition> getRobotPositions(String var1);

    public List<RobotPosition> getPositionByRoomCode(String var1);

    public void interruptGuideTask(String var1);

    public void runConfigTask(ConfigTask var1);
}
