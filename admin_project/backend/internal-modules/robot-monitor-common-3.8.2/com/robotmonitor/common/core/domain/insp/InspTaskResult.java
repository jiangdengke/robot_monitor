/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.builder.ToStringBuilder
 *  org.apache.commons.lang3.builder.ToStringStyle
 */
package com.robotmonitor.common.core.domain.insp;

import com.robotmonitor.common.annotation.Excel;
import com.robotmonitor.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class InspTaskResult
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    @Excel(name="\u5de1\u68c0\u4efb\u52a1ID")
    private Long inspTaskId;
    @Excel(name="\u673a\u5668\u4ebaID")
    private String robotId;
    @Excel(name="\u7c7b\u578b\uff1a0\uff1a\u603b\u7ed3\uff1b1\uff1a\u544a\u8b66")
    private String type;
    @Excel(name="\u70b9\u4f4d\u5e8f\u53f7")
    private String point;
    @Excel(name="\u662f\u5426\u6709\u5f02\u5e38\uff1a0\uff1a\u65e0\u5f02\u5e38\uff1b1\uff1a\u6709\u5f02\u5e38")
    private String abnormal;
    @Excel(name="\u5f02\u5e38\u4fe1\u606f")
    private String abnormalInfo;
    @Excel(name="\u5f02\u5e38\u65f6\u7684\u56fe\u7247")
    private String imageBase64;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setInspTaskId(Long inspTaskId) {
        this.inspTaskId = inspTaskId;
    }

    public Long getInspTaskId() {
        return this.inspTaskId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public String getRobotId() {
        return this.robotId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getPoint() {
        return this.point;
    }

    public void setAbnormal(String abnormal) {
        this.abnormal = abnormal;
    }

    public String getAbnormal() {
        return this.abnormal;
    }

    public void setAbnormalInfo(String abnormalInfo) {
        this.abnormalInfo = abnormalInfo;
    }

    public String getAbnormalInfo() {
        return this.abnormalInfo;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getImageBase64() {
        return this.imageBase64;
    }

    public String toString() {
        return new ToStringBuilder((Object)this, ToStringStyle.MULTI_LINE_STYLE).append("id", (Object)this.getId()).append("inspTaskId", (Object)this.getInspTaskId()).append("robotId", (Object)this.getRobotId()).append("type", (Object)this.getType()).append("point", (Object)this.getPoint()).append("abnormal", (Object)this.getAbnormal()).append("abnormalInfo", (Object)this.getAbnormalInfo()).append("imageBase64", (Object)this.getImageBase64()).append("createTime", (Object)this.getCreateTime()).toString();
    }
}
