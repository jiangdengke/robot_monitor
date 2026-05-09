/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  org.apache.commons.lang3.builder.ToStringBuilder
 *  org.apache.commons.lang3.builder.ToStringStyle
 */
package com.robotmonitor.common.core.domain.robot;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.robotmonitor.common.annotation.Excel;
import com.robotmonitor.common.core.domain.BaseEntity;
import com.robotmonitor.common.core.domain.config.ConfigTask;
import com.robotmonitor.common.core.domain.robot.RobotTaskCmd;
import com.robotmonitor.common.utils.JsonUtils;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RobotTask
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    @Excel(name="\u673a\u5668\u4ebaID")
    private String robotId;
    @Excel(name="\u4efb\u52a1ID")
    private Long taskId;
    @Excel(name="\u4efb\u52a1\u540d")
    private String taskName;
    @Excel(name="\u4efb\u52a1\u7c7b\u522b", readConverterExp="0=\uff1a\u4e00\u6b21\u6027\u6307\u4ee4\u4efb\u52a1\uff1b1\uff1a\u6301\u7eed\u6307\u4ee4\u4efb\u52a1")
    private String taskType;
    @Excel(name="\u4efb\u52a1\u5b50\u7c7b\u578b(0: \u6307\u4ee4\u4efb\u52a1 / 1: \u8bed\u97f3\u4efb\u52a1 / 2: \u89c6\u9891\u6d41\u4efb\u52a1 / 3: HTTP\u4efb\u52a1)")
    private String taskSubtype;
    @Excel(name="\u4efb\u52a1\u6a21\u5f0f", readConverterExp="0=\uff1a\u540e\u53f0\u6a21\u5f0f\uff1b1\uff1a\u524d\u53f0\u6a21\u5f0f")
    private String taskMode;
    @Excel(name="\u4efb\u52a1\u72b6\u6001", readConverterExp="0=\uff1a\u65b0\u5efa\uff1b1\uff1a\u6392\u961f\u4e2d\uff1b2\uff1a\u6267\u884c\u4e2d\uff1b3\uff1a\u4e2d\u65ad\uff1a4\uff1a\u7ec8\u6b62\uff1a9\uff1a\u7ed3\u675f\uff1b")
    private String taskStatus;
    @Excel(name="\u6392\u961f\u5c5e\u6027", readConverterExp="0=:,\u9700=\u8981\u6392\u961f,/=,1=:,\u76f4=\u63a5\u6267\u884c")
    private String directExecution;
    @JsonFormat(pattern="yyyy-MM-dd")
    @Excel(name="\u4efb\u52a1\u5f00\u59cb\u6267\u884c\u65f6\u95f4", width=30.0, dateFormat="yyyy-MM-dd")
    private Date startTime;
    @JsonFormat(pattern="yyyy-MM-dd")
    @Excel(name="\u4efb\u52a1\u7ed3\u675f\u65f6\u95f4", width=30.0, dateFormat="yyyy-MM-dd")
    private Date endTime;
    @Excel(name="\u8fd4\u56de\u4fe1\u606f")
    private String returnInfo;
    @Excel(name="\u673a\u5668\u4eba\u6307\u4ee4")
    private String cmd;

    public RobotTask() {
    }

    public RobotTask(String robotId, ConfigTask configTask, RobotTaskCmd robotTaskCmd) {
        this.robotId = robotId;
        this.init(configTask);
        this.setCmd(JsonUtils.obj2String(robotTaskCmd));
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public String getRobotId() {
        return this.robotId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getTaskId() {
        return this.taskId;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskType() {
        return this.taskType;
    }

    public void setTaskSubtype(String taskSubtype) {
        this.taskSubtype = taskSubtype;
    }

    public String getTaskSubtype() {
        return this.taskSubtype;
    }

    public void setTaskMode(String taskMode) {
        this.taskMode = taskMode;
    }

    public String getTaskMode() {
        return this.taskMode;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskStatus() {
        return this.taskStatus;
    }

    public void setDirectExecution(String directExecution) {
        this.directExecution = directExecution;
    }

    public String getDirectExecution() {
        return this.directExecution;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setReturnInfo(String returnInfo) {
        this.returnInfo = returnInfo;
    }

    public String getReturnInfo() {
        return this.returnInfo;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getCmd() {
        return this.cmd;
    }

    public void init(ConfigTask configTask) {
        this.setTaskId(configTask.getId());
        this.setTaskName(configTask.getTaskName());
        this.setTaskType(configTask.getTaskType());
        this.setTaskMode(configTask.getTaskMode());
        this.setTaskStatus("0");
        this.setTaskSubtype(configTask.getTaskSubtype());
        this.setDirectExecution(configTask.getDirectExecution());
    }

    public String toString() {
        return new ToStringBuilder((Object)this, ToStringStyle.MULTI_LINE_STYLE).append("id", (Object)this.getId()).append("robotId", (Object)this.getRobotId()).append("taskId", (Object)this.getTaskId()).append("taskName", (Object)this.getTaskName()).append("taskType", (Object)this.getTaskType()).append("taskSubtype", (Object)this.getTaskSubtype()).append("taskMode", (Object)this.getTaskMode()).append("taskStatus", (Object)this.getTaskStatus()).append("directExecution", (Object)this.getDirectExecution()).append("createTime", (Object)this.getCreateTime()).append("startTime", (Object)this.getStartTime()).append("endTime", (Object)this.getEndTime()).append("returnInfo", (Object)this.getReturnInfo()).append("cmd", (Object)this.getCmd()).toString();
    }
}
