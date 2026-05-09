/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.core.domain.config;

import com.robotmonitor.common.annotation.Excel;
import com.robotmonitor.common.core.domain.BaseEntity;

public class ConfigAudio
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    @Excel(name="\u8bed\u97f3\u540d\u79f0")
    private String audioKey;
    @Excel(name="\u97f3\u9891")
    private String audioValue;
    @Excel(name="\u8bed\u97f3\u7c7b\u522b")
    private String languageType;
    @Excel(name="\u6587\u5b57\u5185\u5bb9")
    private String textInfo;
    @Excel(name="\u8d35\u5bbe\u5ba4CODE")
    private String roomCode;
    private String deptName;
    private String audioType;

    public Long getId() {
        return this.id;
    }

    public String getAudioKey() {
        return this.audioKey;
    }

    public String getAudioValue() {
        return this.audioValue;
    }

    public String getLanguageType() {
        return this.languageType;
    }

    public String getTextInfo() {
        return this.textInfo;
    }

    public String getRoomCode() {
        return this.roomCode;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public String getAudioType() {
        return this.audioType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAudioKey(String audioKey) {
        this.audioKey = audioKey;
    }

    public void setAudioValue(String audioValue) {
        this.audioValue = audioValue;
    }

    public void setLanguageType(String languageType) {
        this.languageType = languageType;
    }

    public void setTextInfo(String textInfo) {
        this.textInfo = textInfo;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setAudioType(String audioType) {
        this.audioType = audioType;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ConfigAudio)) {
            return false;
        }
        ConfigAudio other = (ConfigAudio)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Long this$id = this.getId();
        Long other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        String this$audioKey = this.getAudioKey();
        String other$audioKey = other.getAudioKey();
        if (this$audioKey == null ? other$audioKey != null : !this$audioKey.equals(other$audioKey)) {
            return false;
        }
        String this$audioValue = this.getAudioValue();
        String other$audioValue = other.getAudioValue();
        if (this$audioValue == null ? other$audioValue != null : !this$audioValue.equals(other$audioValue)) {
            return false;
        }
        String this$languageType = this.getLanguageType();
        String other$languageType = other.getLanguageType();
        if (this$languageType == null ? other$languageType != null : !this$languageType.equals(other$languageType)) {
            return false;
        }
        String this$textInfo = this.getTextInfo();
        String other$textInfo = other.getTextInfo();
        if (this$textInfo == null ? other$textInfo != null : !this$textInfo.equals(other$textInfo)) {
            return false;
        }
        String this$roomCode = this.getRoomCode();
        String other$roomCode = other.getRoomCode();
        if (this$roomCode == null ? other$roomCode != null : !this$roomCode.equals(other$roomCode)) {
            return false;
        }
        String this$deptName = this.getDeptName();
        String other$deptName = other.getDeptName();
        if (this$deptName == null ? other$deptName != null : !this$deptName.equals(other$deptName)) {
            return false;
        }
        String this$audioType = this.getAudioType();
        String other$audioType = other.getAudioType();
        return !(this$audioType == null ? other$audioType != null : !this$audioType.equals(other$audioType));
    }

    protected boolean canEqual(Object other) {
        return other instanceof ConfigAudio;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        String $audioKey = this.getAudioKey();
        result = result * 59 + ($audioKey == null ? 43 : $audioKey.hashCode());
        String $audioValue = this.getAudioValue();
        result = result * 59 + ($audioValue == null ? 43 : $audioValue.hashCode());
        String $languageType = this.getLanguageType();
        result = result * 59 + ($languageType == null ? 43 : $languageType.hashCode());
        String $textInfo = this.getTextInfo();
        result = result * 59 + ($textInfo == null ? 43 : $textInfo.hashCode());
        String $roomCode = this.getRoomCode();
        result = result * 59 + ($roomCode == null ? 43 : $roomCode.hashCode());
        String $deptName = this.getDeptName();
        result = result * 59 + ($deptName == null ? 43 : $deptName.hashCode());
        String $audioType = this.getAudioType();
        result = result * 59 + ($audioType == null ? 43 : $audioType.hashCode());
        return result;
    }

    public String toString() {
        return "ConfigAudio(id=" + this.getId() + ", audioKey=" + this.getAudioKey() + ", audioValue=" + this.getAudioValue() + ", languageType=" + this.getLanguageType() + ", textInfo=" + this.getTextInfo() + ", roomCode=" + this.getRoomCode() + ", deptName=" + this.getDeptName() + ", audioType=" + this.getAudioType() + ")";
    }
}
