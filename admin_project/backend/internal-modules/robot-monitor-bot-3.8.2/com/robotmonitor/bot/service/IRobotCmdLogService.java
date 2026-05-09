/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.robot.RobotCmdLog
 */
package com.robotmonitor.bot.service;

import com.robotmonitor.common.core.domain.robot.RobotCmdLog;
import java.util.List;

public interface IRobotCmdLogService {
    public RobotCmdLog selectRobotCmdLogById(Long var1);

    public List<RobotCmdLog> selectRobotCmdLogList(RobotCmdLog var1);

    public int insertRobotCmdLog(RobotCmdLog var1);

    public int updateRobotCmdLog(RobotCmdLog var1);

    public int deleteRobotCmdLogByIds(Long[] var1);

    public int deleteRobotCmdLogById(Long var1);
}
