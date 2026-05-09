/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.config.dto;

public class RobotAudioRequest {
    private String robotId;
    private String lastUpdateTime;

    public String getRobotId() {
        return this.robotId;
    }

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RobotAudioRequest)) {
            return false;
        }
        RobotAudioRequest other = (RobotAudioRequest)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$robotId = this.getRobotId();
        String other$robotId = other.getRobotId();
        if (this$robotId == null ? other$robotId != null : !this$robotId.equals(other$robotId)) {
            return false;
        }
        String this$lastUpdateTime = this.getLastUpdateTime();
        String other$lastUpdateTime = other.getLastUpdateTime();
        return !(this$lastUpdateTime == null ? other$lastUpdateTime != null : !this$lastUpdateTime.equals(other$lastUpdateTime));
    }

    protected boolean canEqual(Object other) {
        return other instanceof RobotAudioRequest;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $robotId = this.getRobotId();
        result = result * 59 + ($robotId == null ? 43 : $robotId.hashCode());
        String $lastUpdateTime = this.getLastUpdateTime();
        result = result * 59 + ($lastUpdateTime == null ? 43 : $lastUpdateTime.hashCode());
        return result;
    }

    public String toString() {
        return "RobotAudioRequest(robotId=" + this.getRobotId() + ", lastUpdateTime=" + this.getLastUpdateTime() + ")";
    }
}
