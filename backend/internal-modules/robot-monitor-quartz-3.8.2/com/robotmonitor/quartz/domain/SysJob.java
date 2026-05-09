/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.robotmonitor.common.annotation.Excel
 *  com.robotmonitor.common.annotation.Excel$ColumnType
 *  com.robotmonitor.common.core.domain.BaseEntity
 *  com.robotmonitor.common.utils.StringUtils
 *  jakarta.validation.constraints.NotBlank
 *  jakarta.validation.constraints.Size
 *  org.apache.commons.lang3.builder.ToStringBuilder
 *  org.apache.commons.lang3.builder.ToStringStyle
 */
package com.robotmonitor.quartz.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.robotmonitor.common.annotation.Excel;
import com.robotmonitor.common.core.domain.BaseEntity;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.quartz.util.CronUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SysJob
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @Excel(name="\u4efb\u52a1\u5e8f\u53f7", cellType=Excel.ColumnType.NUMERIC)
    private Long jobId;
    @Excel(name="\u4efb\u52a1\u540d\u79f0")
    private String jobName;
    @Excel(name="\u4efb\u52a1\u7ec4\u540d")
    private String jobGroup;
    @Excel(name="\u8c03\u7528\u76ee\u6807\u5b57\u7b26\u4e32")
    private String invokeTarget;
    @Excel(name="\u6267\u884c\u8868\u8fbe\u5f0f ")
    private String cronExpression;
    @Excel(name="\u8ba1\u5212\u7b56\u7565 ", readConverterExp="0=\u9ed8\u8ba4,1=\u7acb\u5373\u89e6\u53d1\u6267\u884c,2=\u89e6\u53d1\u4e00\u6b21\u6267\u884c,3=\u4e0d\u89e6\u53d1\u7acb\u5373\u6267\u884c")
    private String misfirePolicy = "0";
    @Excel(name="\u5e76\u53d1\u6267\u884c", readConverterExp="0=\u5141\u8bb8,1=\u7981\u6b62")
    private String concurrent;
    @Excel(name="\u4efb\u52a1\u72b6\u6001", readConverterExp="0=\u6b63\u5e38,1=\u6682\u505c")
    private String status;

    public Long getJobId() {
        return this.jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    @NotBlank(message="\u4efb\u52a1\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a")
    @Size(min=0, max=64, message="\u4efb\u52a1\u540d\u79f0\u4e0d\u80fd\u8d85\u8fc764\u4e2a\u5b57\u7b26")
    public @NotBlank(message="\u4efb\u52a1\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a") @Size(min=0, max=64, message="\u4efb\u52a1\u540d\u79f0\u4e0d\u80fd\u8d85\u8fc764\u4e2a\u5b57\u7b26") String getJobName() {
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

    @NotBlank(message="\u8c03\u7528\u76ee\u6807\u5b57\u7b26\u4e32\u4e0d\u80fd\u4e3a\u7a7a")
    @Size(min=0, max=500, message="\u8c03\u7528\u76ee\u6807\u5b57\u7b26\u4e32\u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc7500\u4e2a\u5b57\u7b26")
    public @NotBlank(message="\u8c03\u7528\u76ee\u6807\u5b57\u7b26\u4e32\u4e0d\u80fd\u4e3a\u7a7a") @Size(min=0, max=500, message="\u8c03\u7528\u76ee\u6807\u5b57\u7b26\u4e32\u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc7500\u4e2a\u5b57\u7b26") String getInvokeTarget() {
        return this.invokeTarget;
    }

    public void setInvokeTarget(String invokeTarget) {
        this.invokeTarget = invokeTarget;
    }

    @NotBlank(message="Cron\u6267\u884c\u8868\u8fbe\u5f0f\u4e0d\u80fd\u4e3a\u7a7a")
    @Size(min=0, max=255, message="Cron\u6267\u884c\u8868\u8fbe\u5f0f\u4e0d\u80fd\u8d85\u8fc7255\u4e2a\u5b57\u7b26")
    public @NotBlank(message="Cron\u6267\u884c\u8868\u8fbe\u5f0f\u4e0d\u80fd\u4e3a\u7a7a") @Size(min=0, max=255, message="Cron\u6267\u884c\u8868\u8fbe\u5f0f\u4e0d\u80fd\u8d85\u8fc7255\u4e2a\u5b57\u7b26") String getCronExpression() {
        return this.cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public Date getNextValidTime() {
        if (StringUtils.isNotEmpty((String)this.cronExpression)) {
            return CronUtils.getNextExecution(this.cronExpression);
        }
        return null;
    }

    public String getMisfirePolicy() {
        return this.misfirePolicy;
    }

    public void setMisfirePolicy(String misfirePolicy) {
        this.misfirePolicy = misfirePolicy;
    }

    public String getConcurrent() {
        return this.concurrent;
    }

    public void setConcurrent(String concurrent) {
        this.concurrent = concurrent;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return new ToStringBuilder((Object)this, ToStringStyle.MULTI_LINE_STYLE).append("jobId", (Object)this.getJobId()).append("jobName", (Object)this.getJobName()).append("jobGroup", (Object)this.getJobGroup()).append("cronExpression", (Object)this.getCronExpression()).append("nextValidTime", (Object)this.getNextValidTime()).append("misfirePolicy", (Object)this.getMisfirePolicy()).append("concurrent", (Object)this.getConcurrent()).append("status", (Object)this.getStatus()).append("createBy", (Object)this.getCreateBy()).append("createTime", (Object)this.getCreateTime()).append("updateBy", (Object)this.getUpdateBy()).append("updateTime", (Object)this.getUpdateTime()).append("remark", (Object)this.getRemark()).toString();
    }
}
