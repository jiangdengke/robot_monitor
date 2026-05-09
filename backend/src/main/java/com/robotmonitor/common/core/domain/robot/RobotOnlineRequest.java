/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.core.domain.robot;

public class RobotOnlineRequest {
    private String robotId;
    private boolean isOnline;
    private long mode;

    public RobotOnlineRequest() {
    }

    public RobotOnlineRequest(String robotId, boolean isOnline, long mode) {
        this.robotId = robotId;
        this.isOnline = isOnline;
        this.mode = mode;
    }

    public String getRobotId() {
        return this.robotId;
    }

    public boolean isOnline() {
        return this.isOnline;
    }

    public long getMode() {
        return this.mode;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public void setMode(long mode) {
        this.mode = mode;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RobotOnlineRequest)) {
            return false;
        }
        RobotOnlineRequest other = (RobotOnlineRequest)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.isOnline() != other.isOnline()) {
            return false;
        }
        if (this.getMode() != other.getMode()) {
            return false;
        }
        String this$robotId = this.getRobotId();
        String other$robotId = other.getRobotId();
        return !(this$robotId == null ? other$robotId != null : !this$robotId.equals(other$robotId));
    }

    protected boolean canEqual(Object other) {
        return other instanceof RobotOnlineRequest;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isOnline() ? 79 : 97);
        long $mode = this.getMode();
        result = result * 59 + (int)($mode >>> 32 ^ $mode);
        String $robotId = this.getRobotId();
        result = result * 59 + ($robotId == null ? 43 : $robotId.hashCode());
        return result;
    }

    public String toString() {
        return "RobotOnlineRequest(robotId=" + this.getRobotId() + ", isOnline=" + this.isOnline() + ", mode=" + this.getMode() + ")";
    }
}
