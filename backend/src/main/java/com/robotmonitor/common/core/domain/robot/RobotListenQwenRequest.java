/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.core.domain.robot;

import com.robotmonitor.common.core.domain.robot.RobotListenQwenRequestFunction;
import com.robotmonitor.common.utils.JsonUtils;

public class RobotListenQwenRequest {
    private String robotId;
    private String event;
    private String language;
    private String content;
    private String sessionId;
    private RobotListenQwenRequestFunction function;

    public static void main(String[] args) {
        RobotListenQwenRequest request = new RobotListenQwenRequest();
        request.setRobotId("1");
        request.setEvent("SPEECH_CONTEXT");
        request.setLanguage("CN");
        request.setContent("\u4f60\u597d");
        request.setFunction(new RobotListenQwenRequestFunction("FLIGHT", "{\"flightNo\":\"MU5121\",\"flightDate\":\"20260101\"}"));
        System.out.println(JsonUtils.obj2String(request));
    }

    public String getRobotId() {
        return this.robotId;
    }

    public String getEvent() {
        return this.event;
    }

    public String getLanguage() {
        return this.language;
    }

    public String getContent() {
        return this.content;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public RobotListenQwenRequestFunction getFunction() {
        return this.function;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setFunction(RobotListenQwenRequestFunction function) {
        this.function = function;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RobotListenQwenRequest)) {
            return false;
        }
        RobotListenQwenRequest other = (RobotListenQwenRequest)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$robotId = this.getRobotId();
        String other$robotId = other.getRobotId();
        if (this$robotId == null ? other$robotId != null : !this$robotId.equals(other$robotId)) {
            return false;
        }
        String this$event = this.getEvent();
        String other$event = other.getEvent();
        if (this$event == null ? other$event != null : !this$event.equals(other$event)) {
            return false;
        }
        String this$language = this.getLanguage();
        String other$language = other.getLanguage();
        if (this$language == null ? other$language != null : !this$language.equals(other$language)) {
            return false;
        }
        String this$content = this.getContent();
        String other$content = other.getContent();
        if (this$content == null ? other$content != null : !this$content.equals(other$content)) {
            return false;
        }
        String this$sessionId = this.getSessionId();
        String other$sessionId = other.getSessionId();
        if (this$sessionId == null ? other$sessionId != null : !this$sessionId.equals(other$sessionId)) {
            return false;
        }
        RobotListenQwenRequestFunction this$function = this.getFunction();
        RobotListenQwenRequestFunction other$function = other.getFunction();
        return !(this$function == null ? other$function != null : !((Object)this$function).equals(other$function));
    }

    protected boolean canEqual(Object other) {
        return other instanceof RobotListenQwenRequest;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $robotId = this.getRobotId();
        result = result * 59 + ($robotId == null ? 43 : $robotId.hashCode());
        String $event = this.getEvent();
        result = result * 59 + ($event == null ? 43 : $event.hashCode());
        String $language = this.getLanguage();
        result = result * 59 + ($language == null ? 43 : $language.hashCode());
        String $content = this.getContent();
        result = result * 59 + ($content == null ? 43 : $content.hashCode());
        String $sessionId = this.getSessionId();
        result = result * 59 + ($sessionId == null ? 43 : $sessionId.hashCode());
        RobotListenQwenRequestFunction $function = this.getFunction();
        result = result * 59 + ($function == null ? 43 : ((Object)$function).hashCode());
        return result;
    }

    public String toString() {
        return "RobotListenQwenRequest(robotId=" + this.getRobotId() + ", event=" + this.getEvent() + ", language=" + this.getLanguage() + ", content=" + this.getContent() + ", sessionId=" + this.getSessionId() + ", function=" + this.getFunction() + ")";
    }
}
