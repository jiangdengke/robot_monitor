/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.quartz.mapper;

import com.robotmonitor.quartz.domain.SysJobLog;
import java.util.List;

public interface SysJobLogMapper {
    public List<SysJobLog> selectJobLogList(SysJobLog var1);

    public List<SysJobLog> selectJobLogAll();

    public SysJobLog selectJobLogById(Long var1);

    public int insertJobLog(SysJobLog var1);

    public int deleteJobLogByIds(Long[] var1);

    public int deleteJobLogById(Long var1);

    public void cleanJobLog();
}
