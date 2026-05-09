/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.util.Strings
 */
package com.robotmonitor.common.core.domain.robot;

import org.apache.logging.log4j.util.Strings;

public class RobotChatRequest {
    private String robotId;
    private String message;
    private boolean isNeedVoice;
    private String language;
    private String remark;
    private String sessionId;

    public RobotChatRequest() {
    }

    public RobotChatRequest(String robotId, String message) {
        this.robotId = robotId;
        this.message = message;
    }

    public String getPrompt() {
        return "- \u8865\u5145\u4fe1\u606f\uff08%s\uff09\u4ec5\u4f5c\u4e3a\u4e0a\u4e0b\u6587\u53c2\u8003\uff1b\n\u7528\u6237\u7684\u63d0\u95ee\u662f\uff1a\n%s\n".formatted(Strings.isNotBlank((String)this.remark) ? this.remark : "\u65e0", this.message);
    }

    public String getLanguage() {
        return Strings.isNotBlank((String)this.language) ? this.language : "CN";
    }

    public String toString() {
        return "RobotChatRequest{robotId='" + this.robotId + "', message='" + this.message + "', isNeedVoice=" + this.isNeedVoice + ", language='" + this.language + "', remark='" + this.remark + "'}";
    }

    public String getRobotId() {
        return this.robotId;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isNeedVoice() {
        return this.isNeedVoice;
    }

    public String getRemark() {
        return this.remark;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setNeedVoice(boolean isNeedVoice) {
        this.isNeedVoice = isNeedVoice;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RobotChatRequest)) {
            return false;
        }
        RobotChatRequest other = (RobotChatRequest)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.isNeedVoice() != other.isNeedVoice()) {
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
        String this$language = this.getLanguage();
        String other$language = other.getLanguage();
        if (this$language == null ? other$language != null : !this$language.equals(other$language)) {
            return false;
        }
        String this$remark = this.getRemark();
        String other$remark = other.getRemark();
        if (this$remark == null ? other$remark != null : !this$remark.equals(other$remark)) {
            return false;
        }
        String this$sessionId = this.getSessionId();
        String other$sessionId = other.getSessionId();
        return !(this$sessionId == null ? other$sessionId != null : !this$sessionId.equals(other$sessionId));
    }

    protected boolean canEqual(Object other) {
        return other instanceof RobotChatRequest;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isNeedVoice() ? 79 : 97);
        String $robotId = this.getRobotId();
        result = result * 59 + ($robotId == null ? 43 : $robotId.hashCode());
        String $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        String $language = this.getLanguage();
        result = result * 59 + ($language == null ? 43 : $language.hashCode());
        String $remark = this.getRemark();
        result = result * 59 + ($remark == null ? 43 : $remark.hashCode());
        String $sessionId = this.getSessionId();
        result = result * 59 + ($sessionId == null ? 43 : $sessionId.hashCode());
        return result;
    }
}
