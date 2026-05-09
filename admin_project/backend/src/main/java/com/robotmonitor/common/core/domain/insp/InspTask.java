/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  org.apache.commons.lang3.builder.ToStringBuilder
 *  org.apache.commons.lang3.builder.ToStringStyle
 */
package com.robotmonitor.common.core.domain.insp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.robotmonitor.common.annotation.Excel;
import com.robotmonitor.common.core.domain.BaseEntity;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class InspTask
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    @Excel(name="\u673a\u5668\u4ebaID")
    private String robotId;
    @Excel(name="\u5de1\u68c0\u72b6\u6001\uff0c0\uff1a\u5f00\u59cb\u5de1\u68c0\uff1b1\uff1a\u5de1\u68c0\u7ed3\u675f")
    private String status;
    @Excel(name="\u5de1\u68c0\u673a\u5668\u4eba\u6307\u4ee4ID")
    private Long taskId;
    @JsonFormat(pattern="yyyy-MM-dd")
    @Excel(name="\u5de1\u68c0\u7ed3\u675f\u65f6\u95f4", width=30.0, dateFormat="yyyy-MM-dd")
    private Date endTime;

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

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getTaskId() {
        return this.taskId;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public String toString() {
        return new ToStringBuilder((Object)this, ToStringStyle.MULTI_LINE_STYLE).append("id", (Object)this.getId()).append("robotId", (Object)this.getRobotId()).append("status", (Object)this.getStatus()).append("taskId", (Object)this.getTaskId()).append("createTime", (Object)this.getCreateTime()).append("endTime", (Object)this.getEndTime()).toString();
    }
}
