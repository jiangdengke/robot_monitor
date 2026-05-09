/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  org.apache.commons.lang3.builder.ToStringBuilder
 *  org.apache.commons.lang3.builder.ToStringStyle
 */
package com.robotmonitor.common.core.domain.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.robotmonitor.common.annotation.Excel;
import com.robotmonitor.common.core.domain.BaseEntity;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ConfigTask
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    @Excel(name="\u4efb\u52a1\u540d\u79f0")
    private String taskName;
    @Excel(name="\u673a\u5668\u4ebaID")
    private Long robotId;
    @Excel(name="\u6307\u4ee4\u82f1\u6587")
    private Long command;
    @Excel(name="\u6307\u4ee4\u4e2d\u6587")
    private String commandCn;
    @Excel(name="\u6267\u884c\u533a\u57df")
    private String region;
    @Excel(name="\u4f18\u5148\u7ea7")
    private String priority;
    @Excel(name="\u70b9\u4f4d\u72b6\u6001 1-\u542f\u7528 0-\u505c\u7528")
    private Long enable;
    @Excel(name="\u6267\u884c\u7c7b\u578b\uff1aimmediately,day,week,month")
    private String executeType;
    @Excel(name="\u6267\u884c\u65e5\u671f\uff0cweek\u548cmonth\u65f6\u9700\u8981 1,2,3")
    private String executeDay;
    @JsonFormat(pattern="yyyy-MM-dd")
    @Excel(name="\u6267\u884c\u65f6\u95f4 12:00,1:30", width=30.0, dateFormat="yyyy-MM-dd")
    private Date executeTime;
    @Excel(name="\u662f\u5426\u9700\u8981\u8fd4\u56de\u6267\u884c\u7ed3\u679c")
    private String isReturn;
    @Excel(name="\u8d35\u5bbe\u5ba4CODE")
    private String roomCode;
    private String deptName;
    @Excel(name="\u662f\u5426\u5220\u9664")
    private String isDelete;
    @Excel(name="\u6267\u884c\u4efb\u52a1\u65f6\u64ad\u653e\u7684\u56fe\u7247")
    private String imgIds;
    @Excel(name="\u6267\u884c\u4efb\u52a1\u65f6\u64ad\u653e\u7684\u97f3\u9891")
    private String auditIds;
    @Excel(name="\u4efb\u52a1\u7c7b\u522b", readConverterExp="0=\uff1a\u4e00\u6b21\u6027\u6307\u4ee4\u4efb\u52a1\uff1b1\uff1a\u6301\u7eed\u6307\u4ee4\u4efb\u52a1")
    private String taskType;
    @Excel(name="\u4efb\u52a1\u5b50\u7c7b\u578b(0: \u6307\u4ee4\u4efb\u52a1 / 1: \u8bed\u97f3\u4efb\u52a1 / 2: \u89c6\u9891\u6d41\u4efb\u52a1 / 3: HTTP\u4efb\u52a1)")
    private String taskSubtype;
    @Excel(name="\u4efb\u52a1\u6a21\u5f0f", readConverterExp="0=\uff1a\u540e\u53f0\u6a21\u5f0f\uff1b1\uff1a\u524d\u53f0\u6a21\u5f0f")
    private String taskMode;
    @Excel(name="\u6392\u961f\u5c5e\u6027", readConverterExp="0=:,\u9700=\u8981\u6392\u961f,/=,1=:,\u76f4=\u63a5\u6267\u884c")
    private String directExecution;

    public String getDeptName() {
        return this.deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setRobotId(Long robotId) {
        this.robotId = robotId;
    }

    public Long getRobotId() {
        return this.robotId;
    }

    public void setCommand(Long command) {
        this.command = command;
    }

    public Long getCommand() {
        return this.command;
    }

    public void setCommandCn(String commandCn) {
        this.commandCn = commandCn;
    }

    public String getCommandCn() {
        return this.commandCn;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegion() {
        return this.region;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getPriority() {
        return this.priority;
    }

    public void setEnable(Long enable) {
        this.enable = enable;
    }

    public Long getEnable() {
        return this.enable;
    }

    public void setExecuteType(String executeType) {
        this.executeType = executeType;
    }

    public String getExecuteType() {
        return this.executeType;
    }

    public void setExecuteDay(String executeDay) {
        this.executeDay = executeDay;
    }

    public String getExecuteDay() {
        return this.executeDay;
    }

    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }

    public Date getExecuteTime() {
        return this.executeTime;
    }

    public void setIsReturn(String isReturn) {
        this.isReturn = isReturn;
    }

    public String getIsReturn() {
        return this.isReturn;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getRoomCode() {
        return this.roomCode;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getIsDelete() {
        return this.isDelete;
    }

    public void setImgIds(String imgIds) {
        this.imgIds = imgIds;
    }

    public String getImgIds() {
        return this.imgIds;
    }

    public void setAuditIds(String auditIds) {
        this.auditIds = auditIds;
    }

    public String getAuditIds() {
        return this.auditIds;
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

    public void setDirectExecution(String directExecution) {
        this.directExecution = directExecution;
    }

    public String getDirectExecution() {
        return this.directExecution;
    }

    public String toString() {
        return new ToStringBuilder((Object)this, ToStringStyle.MULTI_LINE_STYLE).append("id", (Object)this.getId()).append("taskName", (Object)this.getTaskName()).append("robotId", (Object)this.getRobotId()).append("command", (Object)this.getCommand()).append("commandCn", (Object)this.getCommandCn()).append("region", (Object)this.getRegion()).append("priority", (Object)this.getPriority()).append("enable", (Object)this.getEnable()).append("executeType", (Object)this.getExecuteType()).append("executeDay", (Object)this.getExecuteDay()).append("executeTime", (Object)this.getExecuteTime()).append("isReturn", (Object)this.getIsReturn()).append("remark", (Object)this.getRemark()).append("createBy", (Object)this.getCreateBy()).append("createTime", (Object)this.getCreateTime()).append("updateBy", (Object)this.getUpdateBy()).append("updateTime", (Object)this.getUpdateTime()).append("roomCode", (Object)this.getRoomCode()).append("isDelete", (Object)this.getIsDelete()).append("imgIds", (Object)this.getImgIds()).append("auditIds", (Object)this.getAuditIds()).append("taskType", (Object)this.getTaskType()).append("taskSubtype", (Object)this.getTaskSubtype()).append("taskMode", (Object)this.getTaskMode()).append("directExecution", (Object)this.getDirectExecution()).toString();
    }
}
