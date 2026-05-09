/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.utils.ExceptionUtil
 *  com.robotmonitor.common.utils.StringUtils
 *  com.robotmonitor.common.utils.bean.BeanUtils
 *  com.robotmonitor.common.utils.spring.SpringUtils
 *  org.quartz.Job
 *  org.quartz.JobExecutionContext
 *  org.quartz.JobExecutionException
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.robotmonitor.quartz.util;

import com.robotmonitor.common.utils.ExceptionUtil;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.common.utils.bean.BeanUtils;
import com.robotmonitor.common.utils.spring.SpringUtils;
import com.robotmonitor.quartz.domain.SysJob;
import com.robotmonitor.quartz.domain.SysJobLog;
import com.robotmonitor.quartz.service.ISysJobLogService;
import java.util.Date;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractQuartzJob
implements Job {
    private static final Logger log = LoggerFactory.getLogger(AbstractQuartzJob.class);
    private static ThreadLocal<Date> threadLocal = new ThreadLocal();

    public void execute(JobExecutionContext context) throws JobExecutionException {
        SysJob sysJob = new SysJob();
        BeanUtils.copyBeanProp((Object)((Object)sysJob), (Object)context.getMergedJobDataMap().get((Object)"TASK_PROPERTIES"));
        try {
            this.before(context, sysJob);
            if (sysJob != null) {
                this.doExecute(context, sysJob);
            }
            this.after(context, sysJob, null);
        }
        catch (Exception e) {
            log.error("\u4efb\u52a1\u6267\u884c\u5f02\u5e38  - \uff1a", (Throwable)e);
            this.after(context, sysJob, e);
        }
    }

    protected void before(JobExecutionContext context, SysJob sysJob) {
        threadLocal.set(new Date());
    }

    protected void after(JobExecutionContext context, SysJob sysJob, Exception e) {
        Date startTime = threadLocal.get();
        threadLocal.remove();
        SysJobLog sysJobLog = new SysJobLog();
        sysJobLog.setJobName(sysJob.getJobName());
        sysJobLog.setJobGroup(sysJob.getJobGroup());
        sysJobLog.setInvokeTarget(sysJob.getInvokeTarget());
        sysJobLog.setStartTime(startTime);
        sysJobLog.setStopTime(new Date());
        long runMs = sysJobLog.getStopTime().getTime() - sysJobLog.getStartTime().getTime();
        sysJobLog.setJobMessage(sysJobLog.getJobName() + " \u603b\u5171\u8017\u65f6\uff1a" + runMs + "\u6beb\u79d2");
        if (e != null) {
            sysJobLog.setStatus("1");
            String errorMsg = StringUtils.substring((String)ExceptionUtil.getExceptionMessage((Throwable)e), (int)0, (int)2000);
            sysJobLog.setExceptionInfo(errorMsg);
        } else {
            sysJobLog.setStatus("0");
        }
        ((ISysJobLogService)SpringUtils.getBean(ISysJobLogService.class)).addJobLog(sysJobLog);
    }

    protected abstract void doExecute(JobExecutionContext var1, SysJob var2) throws Exception;
}
