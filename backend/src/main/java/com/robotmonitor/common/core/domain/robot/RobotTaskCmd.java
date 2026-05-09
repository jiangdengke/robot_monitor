/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.core.domain.robot;

import com.robotmonitor.common.core.domain.config.ConfigTask;
import com.robotmonitor.common.core.domain.robot.RobotCmd;

public class RobotTaskCmd
extends RobotCmd {
    private String voiceText;
    private String voiceLanguage;
    private String voiceUrl;
    private String imgIds;
    private String videoId;

    public RobotTaskCmd() {
    }

    public RobotTaskCmd(long robot_id, long task_id, String execution_time, String task_priority, String location_information, Boolean is_return, String supplementary_information, String voiceText, String voiceLanguage, String voiceUrl) {
        super(robot_id, task_id, execution_time, task_priority, location_information, is_return, supplementary_information);
        this.voiceText = voiceText;
        this.voiceLanguage = voiceLanguage;
        this.voiceUrl = voiceUrl;
    }

    public RobotTaskCmd(ConfigTask configTask, long robot_id) {
        super(configTask, robot_id);
    }

    public String getVoiceText() {
        return this.voiceText;
    }

    public String getVoiceLanguage() {
        return this.voiceLanguage;
    }

    public String getVoiceUrl() {
        return this.voiceUrl;
    }

    public String getImgIds() {
        return this.imgIds;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public void setVoiceText(String voiceText) {
        this.voiceText = voiceText;
    }

    public void setVoiceLanguage(String voiceLanguage) {
        this.voiceLanguage = voiceLanguage;
    }

    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }

    public void setImgIds(String imgIds) {
        this.imgIds = imgIds;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RobotTaskCmd)) {
            return false;
        }
        RobotTaskCmd other = (RobotTaskCmd)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$voiceText = this.getVoiceText();
        String other$voiceText = other.getVoiceText();
        if (this$voiceText == null ? other$voiceText != null : !this$voiceText.equals(other$voiceText)) {
            return false;
        }
        String this$voiceLanguage = this.getVoiceLanguage();
        String other$voiceLanguage = other.getVoiceLanguage();
        if (this$voiceLanguage == null ? other$voiceLanguage != null : !this$voiceLanguage.equals(other$voiceLanguage)) {
            return false;
        }
        String this$voiceUrl = this.getVoiceUrl();
        String other$voiceUrl = other.getVoiceUrl();
        if (this$voiceUrl == null ? other$voiceUrl != null : !this$voiceUrl.equals(other$voiceUrl)) {
            return false;
        }
        String this$imgIds = this.getImgIds();
        String other$imgIds = other.getImgIds();
        if (this$imgIds == null ? other$imgIds != null : !this$imgIds.equals(other$imgIds)) {
            return false;
        }
        String this$videoId = this.getVideoId();
        String other$videoId = other.getVideoId();
        return !(this$videoId == null ? other$videoId != null : !this$videoId.equals(other$videoId));
    }

    protected boolean canEqual(Object other) {
        return other instanceof RobotTaskCmd;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $voiceText = this.getVoiceText();
        result = result * 59 + ($voiceText == null ? 43 : $voiceText.hashCode());
        String $voiceLanguage = this.getVoiceLanguage();
        result = result * 59 + ($voiceLanguage == null ? 43 : $voiceLanguage.hashCode());
        String $voiceUrl = this.getVoiceUrl();
        result = result * 59 + ($voiceUrl == null ? 43 : $voiceUrl.hashCode());
        String $imgIds = this.getImgIds();
        result = result * 59 + ($imgIds == null ? 43 : $imgIds.hashCode());
        String $videoId = this.getVideoId();
        result = result * 59 + ($videoId == null ? 43 : $videoId.hashCode());
        return result;
    }

    public String toString() {
        return "RobotTaskCmd(voiceText=" + this.getVoiceText() + ", voiceLanguage=" + this.getVoiceLanguage() + ", voiceUrl=" + this.getVoiceUrl() + ", imgIds=" + this.getImgIds() + ", videoId=" + this.getVideoId() + ")";
    }
}
