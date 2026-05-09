/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.bot.domain;

import com.robotmonitor.bot.domain.RobotRequest;

public class RobotMoveRequest
extends RobotRequest {
    private String locationInfo;
    private String areaId;
    private boolean needVoice;
    private String language = "CN";

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RobotMoveRequest)) {
            return false;
        }
        RobotMoveRequest other = (RobotMoveRequest)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        if (this.isNeedVoice() != other.isNeedVoice()) {
            return false;
        }
        String this$locationInfo = this.getLocationInfo();
        String other$locationInfo = other.getLocationInfo();
        if (this$locationInfo == null ? other$locationInfo != null : !this$locationInfo.equals(other$locationInfo)) {
            return false;
        }
        String this$areaId = this.getAreaId();
        String other$areaId = other.getAreaId();
        if (this$areaId == null ? other$areaId != null : !this$areaId.equals(other$areaId)) {
            return false;
        }
        String this$language = this.getLanguage();
        String other$language = other.getLanguage();
        return !(this$language == null ? other$language != null : !this$language.equals(other$language));
    }

    @Override
    protected boolean canEqual(Object other) {
        return other instanceof RobotMoveRequest;
    }

    @Override
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        result = result * 59 + (this.isNeedVoice() ? 79 : 97);
        String $locationInfo = this.getLocationInfo();
        result = result * 59 + ($locationInfo == null ? 43 : $locationInfo.hashCode());
        String $areaId = this.getAreaId();
        result = result * 59 + ($areaId == null ? 43 : $areaId.hashCode());
        String $language = this.getLanguage();
        result = result * 59 + ($language == null ? 43 : $language.hashCode());
        return result;
    }

    public String getLocationInfo() {
        return this.locationInfo;
    }

    public String getAreaId() {
        return this.areaId;
    }

    public boolean isNeedVoice() {
        return this.needVoice;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLocationInfo(String locationInfo) {
        this.locationInfo = locationInfo;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public void setNeedVoice(boolean needVoice) {
        this.needVoice = needVoice;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "RobotMoveRequest(locationInfo=" + this.getLocationInfo() + ", areaId=" + this.getAreaId() + ", needVoice=" + this.isNeedVoice() + ", language=" + this.getLanguage() + ")";
    }
}
