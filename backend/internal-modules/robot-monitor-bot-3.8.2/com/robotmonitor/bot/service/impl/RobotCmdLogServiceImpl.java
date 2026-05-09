/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.robot.RobotCmdLog
 *  com.robotmonitor.common.utils.DateUtils
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.bot.service.impl;

import com.robotmonitor.bot.mapper.RobotCmdLogMapper;
import com.robotmonitor.bot.service.IRobotCmdLogService;
import com.robotmonitor.common.core.domain.robot.RobotCmdLog;
import com.robotmonitor.common.utils.DateUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RobotCmdLogServiceImpl
implements IRobotCmdLogService {
    @Autowired
    private RobotCmdLogMapper robotCmdLogMapper;

    @Override
    public RobotCmdLog selectRobotCmdLogById(Long id) {
        return this.robotCmdLogMapper.selectRobotCmdLogById(id);
    }

    @Override
    public List<RobotCmdLog> selectRobotCmdLogList(RobotCmdLog robotCmdLog) {
        return this.robotCmdLogMapper.selectRobotCmdLogList(robotCmdLog);
    }

    @Override
    public int insertRobotCmdLog(RobotCmdLog robotCmdLog) {
        robotCmdLog.setCreateTime(DateUtils.getNowDate());
        return this.robotCmdLogMapper.insertRobotCmdLog(robotCmdLog);
    }

    @Override
    public int updateRobotCmdLog(RobotCmdLog robotCmdLog) {
        return this.robotCmdLogMapper.updateRobotCmdLog(robotCmdLog);
    }

    @Override
    public int deleteRobotCmdLogByIds(Long[] ids) {
        return this.robotCmdLogMapper.deleteRobotCmdLogByIds(ids);
    }

    @Override
    public int deleteRobotCmdLogById(Long id) {
        return this.robotCmdLogMapper.deleteRobotCmdLogById(id);
    }
}
