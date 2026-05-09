/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.builder.ToStringBuilder
 *  org.apache.commons.lang3.builder.ToStringStyle
 */
package com.robotmonitor.common.core.domain.config;

import com.robotmonitor.common.annotation.Excel;
import com.robotmonitor.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ConfigRobotAudio
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    @Excel(name="\u8bed\u97f3\u5173\u952e\u5b57")
    private String audioKey;
    @Excel(name="\u8bed\u97f3\u5185\u5bb9")
    private String audioValue;
    @Excel(name="\u8bed\u97f3\u7c7b\u578bCN/EN/RU")
    private String languageType;
    @Excel(name="\u6587\u5b57\u5185\u5bb9")
    private String textInfo;
    @Excel(name="\u8d35\u5bbe\u5ba4CODE")
    private String roomCode;
    private String deptName;

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

    public void setAudioKey(String audioKey) {
        this.audioKey = audioKey;
    }

    public String getAudioKey() {
        return this.audioKey;
    }

    public void setAudioValue(String audioValue) {
        this.audioValue = audioValue;
    }

    public String getAudioValue() {
        return this.audioValue;
    }

    public void setLanguageType(String languageType) {
        this.languageType = languageType;
    }

    public String getLanguageType() {
        return this.languageType;
    }

    public void setTextInfo(String textInfo) {
        this.textInfo = textInfo;
    }

    public String getTextInfo() {
        return this.textInfo;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getRoomCode() {
        return this.roomCode;
    }

    public String toString() {
        return new ToStringBuilder((Object)this, ToStringStyle.MULTI_LINE_STYLE).append("id", (Object)this.getId()).append("audioKey", (Object)this.getAudioKey()).append("audioValue", (Object)this.getAudioValue()).append("languageType", (Object)this.getLanguageType()).append("textInfo", (Object)this.getTextInfo()).append("remark", (Object)this.getRemark()).append("createBy", (Object)this.getCreateBy()).append("createTime", (Object)this.getCreateTime()).append("updateBy", (Object)this.getUpdateBy()).append("updateTime", (Object)this.getUpdateTime()).append("roomCode", (Object)this.getRoomCode()).toString();
    }
}
