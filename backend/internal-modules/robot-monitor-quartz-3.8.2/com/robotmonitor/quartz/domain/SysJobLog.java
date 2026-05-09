/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Excel
 *  com.robotmonitor.common.core.domain.BaseEntity
 *  org.apache.commons.lang3.builder.ToStringBuilder
 *  org.apache.commons.lang3.builder.ToStringStyle
 */
package com.robotmonitor.quartz.domain;

import com.robotmonitor.common.annotation.Excel;
import com.robotmonitor.common.core.domain.BaseEntity;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SysJobLog
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @Excel(name="\u65e5\u5fd7\u5e8f\u53f7")
    private Long jobLogId;
    @Excel(name="\u4efb\u52a1\u540d\u79f0")
    private String jobName;
    @Excel(name="\u4efb\u52a1\u7ec4\u540d")
    private String jobGroup;
    @Excel(name="\u8c03\u7528\u76ee\u6807\u5b57\u7b26\u4e32")
    private String invokeTarget;
    @Excel(name="\u65e5\u5fd7\u4fe1\u606f")
    private String jobMessage;
    @Excel(name="\u6267\u884c\u72b6\u6001", readConverterExp="0=\u6b63\u5e38,1=\u5931\u8d25")
    private String status;
    @Excel(name="\u5f02\u5e38\u4fe1\u606f")
    private String exceptionInfo;
    private Date startTime;
    private Date stopTime;

    public Long getJobLogId() {
        return this.jobLogId;
    }

    public void setJobLogId(Long jobLogId) {
        this.jobLogId = jobLogId;
    }

    public String getJobName() {
        return this.jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return this.jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getInvokeTarget() {
        return this.invokeTarget;
    }

    public void setInvokeTarget(String invokeTarget) {
        this.invokeTarget = invokeTarget;
    }

    public String getJobMessage() {
        return this.jobMessage;
    }

    public void setJobMessage(String jobMessage) {
        this.jobMessage = jobMessage;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExceptionInfo() {
        return this.exceptionInfo;
    }

    public void setExceptionInfo(String exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStopTime() {
        return this.stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    public String toString() {
        return new ToStringBuilder((Object)this, ToStringStyle.MULTI_LINE_STYLE).append("jobLogId", (Object)this.getJobLogId()).append("jobName", (Object)this.getJobName()).append("jobGroup", (Object)this.getJobGroup()).append("jobMessage", (Object)this.getJobMessage()).append("status", (Object)this.getStatus()).append("exceptionInfo", (Object)this.getExceptionInfo()).append("startTime", (Object)this.getStartTime()).append("stopTime", (Object)this.getStopTime()).toString();
    }
}
