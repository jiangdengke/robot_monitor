/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.robot.RobotTask
 *  com.robotmonitor.common.utils.DateUtils
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.bot.service.impl;

import com.robotmonitor.bot.mapper.RobotTaskMapper;
import com.robotmonitor.bot.service.IRobotTaskService;
import com.robotmonitor.common.core.domain.robot.RobotTask;
import com.robotmonitor.common.utils.DateUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RobotTaskServiceImpl
implements IRobotTaskService {
    @Autowired
    private RobotTaskMapper robotTaskMapper;

    @Override
    public RobotTask selectRobotTaskById(Long id) {
        return this.robotTaskMapper.selectRobotTaskById(id);
    }

    @Override
    public List<RobotTask> selectRobotTaskList(RobotTask robotTask) {
        return this.robotTaskMapper.selectRobotTaskList(robotTask);
    }

    @Override
    public int insertRobotTask(RobotTask robotTask) {
        robotTask.setCreateTime(DateUtils.getNowDate());
        return this.robotTaskMapper.insertRobotTask(robotTask);
    }

    @Override
    public int updateRobotTask(RobotTask robotTask) {
        return this.robotTaskMapper.updateRobotTask(robotTask);
    }

    @Override
    public int deleteRobotTaskByIds(Long[] ids) {
        return this.robotTaskMapper.deleteRobotTaskByIds(ids);
    }

    @Override
    public int deleteRobotTaskById(Long id) {
        return this.robotTaskMapper.deleteRobotTaskById(id);
    }

    @Override
    public List<RobotTask> findTaskListByRobotId(String robotId) {
        return this.robotTaskMapper.findTaskListByRobotId(robotId);
    }
}
