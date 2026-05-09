/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.quartz.service;

import com.robotmonitor.quartz.domain.SysJobLog;
import java.util.List;

public interface ISysJobLogService {
    public List<SysJobLog> selectJobLogList(SysJobLog var1);

    public SysJobLog selectJobLogById(Long var1);

    public void addJobLog(SysJobLog var1);

    public int deleteJobLogByIds(Long[] var1);

    public int deleteJobLogById(Long var1);

    public void cleanJobLog();
}
