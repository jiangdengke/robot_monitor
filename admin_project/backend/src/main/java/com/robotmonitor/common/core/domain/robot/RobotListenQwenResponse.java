/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.core.domain.robot;

public class RobotListenQwenResponse {
    private String robotId;
    private String event = "RESPONSE_CONTEXT";
    private String content = "";

    public RobotListenQwenResponse() {
    }

    public RobotListenQwenResponse(String content) {
        this.content = content;
    }

    public String getRobotId() {
        return this.robotId;
    }

    public String getEvent() {
        return this.event;
    }

    public String getContent() {
        return this.content;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RobotListenQwenResponse)) {
            return false;
        }
        RobotListenQwenResponse other = (RobotListenQwenResponse)o;
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
        String this$content = this.getContent();
        String other$content = other.getContent();
        return !(this$content == null ? other$content != null : !this$content.equals(other$content));
    }

    protected boolean canEqual(Object other) {
        return other instanceof RobotListenQwenResponse;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $robotId = this.getRobotId();
        result = result * 59 + ($robotId == null ? 43 : $robotId.hashCode());
        String $event = this.getEvent();
        result = result * 59 + ($event == null ? 43 : $event.hashCode());
        String $content = this.getContent();
        result = result * 59 + ($content == null ? 43 : $content.hashCode());
        return result;
    }

    public String toString() {
        return "RobotListenQwenResponse(robotId=" + this.getRobotId() + ", event=" + this.getEvent() + ", content=" + this.getContent() + ")";
    }
}
