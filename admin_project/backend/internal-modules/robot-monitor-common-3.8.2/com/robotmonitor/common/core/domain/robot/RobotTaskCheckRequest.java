/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.core.domain.robot;

public class RobotTaskCheckRequest {
    private String robotId;

    public RobotTaskCheckRequest(String robotId) {
        this.robotId = robotId;
    }

    public RobotTaskCheckRequest() {
    }

    public String getRobotId() {
        return this.robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RobotTaskCheckRequest)) {
            return false;
        }
        RobotTaskCheckRequest other = (RobotTaskCheckRequest)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$robotId = this.getRobotId();
        String other$robotId = other.getRobotId();
        return !(this$robotId == null ? other$robotId != null : !this$robotId.equals(other$robotId));
    }

    protected boolean canEqual(Object other) {
        return other instanceof RobotTaskCheckRequest;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $robotId = this.getRobotId();
        result = result * 59 + ($robotId == null ? 43 : $robotId.hashCode());
        return result;
    }

    public String toString() {
        return "RobotTaskCheckRequest(robotId=" + this.getRobotId() + ")";
    }
}
