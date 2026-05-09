/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.robot.RobotTask
 */
package com.robotmonitor.bot.mapper;

import com.robotmonitor.common.core.domain.robot.RobotTask;
import java.util.List;

public interface RobotTaskMapper {
    public RobotTask selectRobotTaskById(Long var1);

    public List<RobotTask> selectRobotTaskList(RobotTask var1);

    public int insertRobotTask(RobotTask var1);

    public int updateRobotTask(RobotTask var1);

    public int deleteRobotTaskById(Long var1);

    public int deleteRobotTaskByIds(Long[] var1);

    public List<RobotTask> findTaskListByRobotId(String var1);
}
