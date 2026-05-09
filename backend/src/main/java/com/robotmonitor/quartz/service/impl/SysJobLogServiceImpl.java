/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.quartz.service.impl;

import com.robotmonitor.quartz.domain.SysJobLog;
import com.robotmonitor.quartz.mapper.SysJobLogMapper;
import com.robotmonitor.quartz.service.ISysJobLogService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysJobLogServiceImpl
implements ISysJobLogService {
    @Autowired
    private SysJobLogMapper jobLogMapper;

    @Override
    public List<SysJobLog> selectJobLogList(SysJobLog jobLog) {
        return this.jobLogMapper.selectJobLogList(jobLog);
    }

    @Override
    public SysJobLog selectJobLogById(Long jobLogId) {
        return this.jobLogMapper.selectJobLogById(jobLogId);
    }

    @Override
    public void addJobLog(SysJobLog jobLog) {
        this.jobLogMapper.insertJobLog(jobLog);
    }

    @Override
    public int deleteJobLogByIds(Long[] logIds) {
        return this.jobLogMapper.deleteJobLogByIds(logIds);
    }

    @Override
    public int deleteJobLogById(Long jobId) {
        return this.jobLogMapper.deleteJobLogById(jobId);
    }

    @Override
    public void cleanJobLog() {
        this.jobLogMapper.cleanJobLog();
    }
}
