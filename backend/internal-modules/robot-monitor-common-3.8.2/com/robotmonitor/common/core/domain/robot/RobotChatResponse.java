/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.core.domain.robot;

import com.robotmonitor.common.core.domain.robot.RobotChatImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class RobotChatResponse {
    private String eventType;
    private String robotId;
    private String message;
    private List<RobotChatImage> pictures;
    private String audioUrl;
    private String extraInfo;
    private String buttonName;
    private String locationInfo;
    private Long cmd;
    private String language;
    private String areaId;
    private String areaName;
    private boolean isNeedVoice;

    public RobotChatResponse() {
    }

    public RobotChatResponse(String eventType) {
        this.eventType = eventType;
    }

    public RobotChatResponse(String eventType, String message) {
        this.eventType = eventType;
        this.message = message;
    }

    public RobotChatResponse(String eventType, String message, Long cmd) {
        this.eventType = eventType;
        this.message = message;
        this.cmd = cmd;
    }

    public RobotChatResponse(String eventType, String message, List<RobotChatImage> pictures, String audioUrl, String buttonName, String locationInfo, Long cmd) {
        this.eventType = eventType;
        this.message = message;
        this.pictures = pictures;
        this.audioUrl = audioUrl;
        this.buttonName = buttonName;
        this.locationInfo = locationInfo;
        this.cmd = cmd;
    }

    public RobotChatResponse(Map<String, Object> map) {
        this.setRobotId(map.containsKey("robotId") ? (String)map.get("robotId") : null);
        this.setEventType(map.containsKey("eventType") ? (String)map.get("eventType") : null);
        this.setMessage(map.containsKey("message") ? (String)map.get("message") : null);
        if (map.containsKey("pictures") && null != map.get("pictures")) {
            ArrayList<RobotChatImage> pictures = new ArrayList<RobotChatImage>();
            for (Object pictureObject : (Collection)map.get("pictures")) {
                Map pictureMap = (Map)pictureObject;
                RobotChatImage picture = new RobotChatImage();
                pictures.add(picture);
                picture.setImageName(pictureMap.containsKey("imageName") ? (String)pictureMap.get("imageName") : null);
                picture.setImageData(pictureMap.containsKey("imageData") ? (String)pictureMap.get("imageData") : null);
            }
            this.setPictures(pictures);
        }
        this.setAudioUrl(map.containsKey("audioUrl") ? (String)map.get("audioUrl") : null);
        this.setButtonName(map.containsKey("buttonName") ? (String)map.get("buttonName") : null);
        this.setLocationInfo(map.containsKey("locationInfo") ? (String)map.get("locationInfo") : null);
        this.setCmd(map.containsKey("cmd") && null != map.get("cmd") ? Long.valueOf(((Integer)map.get("cmd")).longValue()) : null);
        this.setLanguage(map.containsKey("language") ? (String)map.get("language") : null);
        this.setExtraInfo(map.containsKey("extraInfo") ? (String)map.get("extraInfo") : null);
        this.setAreaId(map.containsKey("areaId") ? (String)map.get("areaId") : null);
        this.setAreaName(map.containsKey("areaName") ? (String)map.get("areaName") : null);
    }

    public String getEventType() {
        return this.eventType;
    }

    public String getRobotId() {
        return this.robotId;
    }

    public String getMessage() {
        return this.message;
    }

    public List<RobotChatImage> getPictures() {
        return this.pictures;
    }

    public String getAudioUrl() {
        return this.audioUrl;
    }

    public String getExtraInfo() {
        return this.extraInfo;
    }

    public String getButtonName() {
        return this.buttonName;
    }

    public String getLocationInfo() {
        return this.locationInfo;
    }

    public Long getCmd() {
        return this.cmd;
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

    public boolean isNeedVoice() {
        return this.isNeedVoice;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPictures(List<RobotChatImage> pictures) {
        this.pictures = pictures;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public void setLocationInfo(String locationInfo) {
        this.locationInfo = locationInfo;
    }

    public void setCmd(Long cmd) {
        this.cmd = cmd;
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

    public void setNeedVoice(boolean isNeedVoice) {
        this.isNeedVoice = isNeedVoice;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RobotChatResponse)) {
            return false;
        }
        RobotChatResponse other = (RobotChatResponse)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.isNeedVoice() != other.isNeedVoice()) {
            return false;
        }
        Long this$cmd = this.getCmd();
        Long other$cmd = other.getCmd();
        if (this$cmd == null ? other$cmd != null : !((Object)this$cmd).equals(other$cmd)) {
            return false;
        }
        String this$eventType = this.getEventType();
        String other$eventType = other.getEventType();
        if (this$eventType == null ? other$eventType != null : !this$eventType.equals(other$eventType)) {
            return false;
        }
        String this$robotId = this.getRobotId();
        String other$robotId = other.getRobotId();
        if (this$robotId == null ? other$robotId != null : !this$robotId.equals(other$robotId)) {
            return false;
        }
        String this$message = this.getMessage();
        String other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) {
            return false;
        }
        List<RobotChatImage> this$pictures = this.getPictures();
        List<RobotChatImage> other$pictures = other.getPictures();
        if (this$pictures == null ? other$pictures != null : !((Object)this$pictures).equals(other$pictures)) {
            return false;
        }
        String this$audioUrl = this.getAudioUrl();
        String other$audioUrl = other.getAudioUrl();
        if (this$audioUrl == null ? other$audioUrl != null : !this$audioUrl.equals(other$audioUrl)) {
            return false;
        }
        String this$extraInfo = this.getExtraInfo();
        String other$extraInfo = other.getExtraInfo();
        if (this$extraInfo == null ? other$extraInfo != null : !this$extraInfo.equals(other$extraInfo)) {
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
        return other instanceof RobotChatResponse;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isNeedVoice() ? 79 : 97);
        Long $cmd = this.getCmd();
        result = result * 59 + ($cmd == null ? 43 : ((Object)$cmd).hashCode());
        String $eventType = this.getEventType();
        result = result * 59 + ($eventType == null ? 43 : $eventType.hashCode());
        String $robotId = this.getRobotId();
        result = result * 59 + ($robotId == null ? 43 : $robotId.hashCode());
        String $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        List<RobotChatImage> $pictures = this.getPictures();
        result = result * 59 + ($pictures == null ? 43 : ((Object)$pictures).hashCode());
        String $audioUrl = this.getAudioUrl();
        result = result * 59 + ($audioUrl == null ? 43 : $audioUrl.hashCode());
        String $extraInfo = this.getExtraInfo();
        result = result * 59 + ($extraInfo == null ? 43 : $extraInfo.hashCode());
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
        return "RobotChatResponse(eventType=" + this.getEventType() + ", robotId=" + this.getRobotId() + ", message=" + this.getMessage() + ", pictures=" + this.getPictures() + ", audioUrl=" + this.getAudioUrl() + ", extraInfo=" + this.getExtraInfo() + ", buttonName=" + this.getButtonName() + ", locationInfo=" + this.getLocationInfo() + ", cmd=" + this.getCmd() + ", language=" + this.getLanguage() + ", areaId=" + this.getAreaId() + ", areaName=" + this.getAreaName() + ", isNeedVoice=" + this.isNeedVoice() + ")";
    }
}
