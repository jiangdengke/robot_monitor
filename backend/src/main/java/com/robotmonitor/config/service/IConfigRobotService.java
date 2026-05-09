/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.config.ConfigRobot
 *  com.robotmonitor.common.core.domain.robot.RobotOnlineRequest
 *  com.robotmonitor.common.core.domain.robot.RobotStatus
 */
package com.robotmonitor.config.service;

import com.robotmonitor.common.core.domain.config.ConfigRobot;
import com.robotmonitor.common.core.domain.robot.RobotOnlineRequest;
import com.robotmonitor.common.core.domain.robot.RobotStatus;
import java.util.List;

public interface IConfigRobotService {
    public ConfigRobot selectConfigRobotById(Long var1);

    public ConfigRobot selectConfigRobotByRobotId(String var1);

    public List<ConfigRobot> selectConfigRobotList(ConfigRobot var1);

    public int insertConfigRobot(ConfigRobot var1);

    public int updateConfigRobot(ConfigRobot var1);

    public int deleteConfigRobotByIds(Long[] var1);

    public int deleteConfigRobotById(Long var1);

    public ConfigRobot getConfigRobotByRobotId(String var1);

    public List<String> getRobotIdsByRoomCode(String var1);

    public int updateRobotIp(Long var1, String var2);

    public void updateRobotStatus(RobotStatus var1);

    public RobotStatus getRobotStatus(String var1);

    public void updateRobotOnlineStatus(RobotOnlineRequest var1);
}
