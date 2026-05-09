/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.core.domain.robot;

public class RobotVoiceMonitor {
    private String robotId;
    private String status;

    public String getRobotId() {
        return this.robotId;
    }

    public String getStatus() {
        return this.status;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RobotVoiceMonitor)) {
            return false;
        }
        RobotVoiceMonitor other = (RobotVoiceMonitor)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$robotId = this.getRobotId();
        String other$robotId = other.getRobotId();
        if (this$robotId == null ? other$robotId != null : !this$robotId.equals(other$robotId)) {
            return false;
        }
        String this$status = this.getStatus();
        String other$status = other.getStatus();
        return !(this$status == null ? other$status != null : !this$status.equals(other$status));
    }

    protected boolean canEqual(Object other) {
        return other instanceof RobotVoiceMonitor;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $robotId = this.getRobotId();
        result = result * 59 + ($robotId == null ? 43 : $robotId.hashCode());
        String $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        return result;
    }

    public String toString() {
        return "RobotVoiceMonitor(robotId=" + this.getRobotId() + ", status=" + this.getStatus() + ")";
    }
}
