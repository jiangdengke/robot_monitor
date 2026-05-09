/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.quartz.DisallowConcurrentExecution
 *  org.quartz.JobExecutionContext
 */
package com.robotmonitor.quartz.util;

import com.robotmonitor.quartz.domain.SysJob;
import com.robotmonitor.quartz.util.AbstractQuartzJob;
import com.robotmonitor.quartz.util.JobInvokeUtil;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution
extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}
