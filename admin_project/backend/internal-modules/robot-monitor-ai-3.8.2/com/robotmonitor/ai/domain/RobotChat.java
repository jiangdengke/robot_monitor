/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.robot.RobotChatImage
 *  com.robotmonitor.common.core.domain.robot.RobotChatResponse
 */
package com.robotmonitor.ai.domain;

import com.robotmonitor.common.core.domain.robot.RobotChatImage;
import com.robotmonitor.common.core.domain.robot.RobotChatResponse;
import java.util.List;

public class RobotChat {
    private String role;
    private String content;
    private String eventType;
    private List<RobotChatImage> pictures;
    private String buttonName;
    private String locationInfo;
    private String language;
    private String areaId;
    private String areaName;

    public RobotChat() {
    }

    public RobotChat(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public RobotChat(String role, RobotChatResponse robotChatResponse) {
        this.role = role;
        this.content = robotChatResponse.getMessage();
        this.eventType = robotChatResponse.getEventType();
        this.pictures = robotChatResponse.getPictures();
        this.buttonName = robotChatResponse.getButtonName();
        this.locationInfo = robotChatResponse.getLocationInfo();
        this.language = robotChatResponse.getLanguage();
        this.areaId = robotChatResponse.getAreaId();
        this.areaName = robotChatResponse.getAreaName();
    }

    public String getRole() {
        return this.role;
    }

    public String getContent() {
        return this.content;
    }

    public String getEventType() {
        return this.eventType;
    }

    public List<RobotChatImage> getPictures() {
        return this.pictures;
    }

    public String getButtonName() {
        return this.buttonName;
    }

    public String getLocationInfo() {
        return this.locationInfo;
    }

    public String getLanguage() {
        return this.language;
    }

    public String getAreaId() {
        return this.areaId;
    }

    public String getAreaName() {
        return this.areaName;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setPictures(List<RobotChatImage> pictures) {
        this.pictures = pictures;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public void setLocationInfo(String locationInfo) {
        this.locationInfo = locationInfo;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RobotChat)) {
            return false;
        }
        RobotChat other = (RobotChat)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$role = this.getRole();
        String other$role = other.getRole();
        if (this$role == null ? other$role != null : !this$role.equals(other$role)) {
            return false;
        }
        String this$content = this.getContent();
        String other$content = other.getContent();
        if (this$content == null ? other$content != null : !this$content.equals(other$content)) {
            return false;
        }
        String this$eventType = this.getEventType();
        String other$eventType = other.getEventType();
        if (this$eventType == null ? other$eventType != null : !this$eventType.equals(other$eventType)) {
            return false;
        }
        List<RobotChatImage> this$pictures = this.getPictures();
        List<RobotChatImage> other$pictures = other.getPictures();
        if (this$pictures == null ? other$pictures != null : !((Object)this$pictures).equals(other$pictures)) {
            return false;
        }
        String this$buttonName = this.getButtonName();
        String other$buttonName = other.getButtonName();
        if (this$buttonName == null ? other$buttonName != null : !this$buttonName.equals(other$buttonName)) {
            return false;
        }
        String this$locationInfo = this.getLocationInfo();
        String other$locationInfo = other.getLocationInfo();
        if (this$locationInfo == null ? other$locationInfo != null : !this$locationInfo.equals(other$locationInfo)) {
            return false;
        }
        String this$language = this.getLanguage();
        String other$language = other.getLanguage();
        if (this$language == null ? other$language != null : !this$language.equals(other$language)) {
            return false;
        }
        String this$areaId = this.getAreaId();
        String other$areaId = other.getAreaId();
        if (this$areaId == null ? other$areaId != null : !this$areaId.equals(other$areaId)) {
            return false;
        }
        String this$areaName = this.getAreaName();
        String other$areaName = other.getAreaName();
        return !(this$areaName == null ? other$areaName != null : !this$areaName.equals(other$areaName));
    }

    protected boolean canEqual(Object other) {
        return other instanceof RobotChat;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $role = this.getRole();
        result = result * 59 + ($role == null ? 43 : $role.hashCode());
        String $content = this.getContent();
        result = result * 59 + ($content == null ? 43 : $content.hashCode());
        String $eventType = this.getEventType();
        result = result * 59 + ($eventType == null ? 43 : $eventType.hashCode());
        List<RobotChatImage> $pictures = this.getPictures();
        result = result * 59 + ($pictures == null ? 43 : ((Object)$pictures).hashCode());
        String $buttonName = this.getButtonName();
        result = result * 59 + ($buttonName == null ? 43 : $buttonName.hashCode());
        String $locationInfo = this.getLocationInfo();
        result = result * 59 + ($locationInfo == null ? 43 : $locationInfo.hashCode());
        String $language = this.getLanguage();
        result = result * 59 + ($language == null ? 43 : $language.hashCode());
        String $areaId = this.getAreaId();
        result = result * 59 + ($areaId == null ? 43 : $areaId.hashCode());
        String $areaName = this.getAreaName();
        result = result * 59 + ($areaName == null ? 43 : $areaName.hashCode());
        return result;
    }

    public String toString() {
        return "RobotChat(role=" + this.getRole() + ", content=" + this.getContent() + ", eventType=" + this.getEventType() + ", pictures=" + this.getPictures() + ", buttonName=" + this.getButtonName() + ", locationInfo=" + this.getLocationInfo() + ", language=" + this.getLanguage() + ", areaId=" + this.getAreaId() + ", areaName=" + this.getAreaName() + ")";
    }
}
