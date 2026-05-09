/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.core.domain.robot;

public class RobotPosition {
    private String robotId;
    private String locationInformation;

    public RobotPosition() {
    }

    public RobotPosition(String robotId) {
        this.robotId = robotId;
    }

    public RobotPosition(String robotId, String locationInformation) {
        this.robotId = robotId;
        this.locationInformation = locationInformation;
    }

    public String getRobotId() {
        return this.robotId;
    }

    public String getLocationInformation() {
        return this.locationInformation;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public void setLocationInformation(String locationInformation) {
        this.locationInformation = locationInformation;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RobotPosition)) {
            return false;
        }
        RobotPosition other = (RobotPosition)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$robotId = this.getRobotId();
        String other$robotId = other.getRobotId();
        if (this$robotId == null ? other$robotId != null : !this$robotId.equals(other$robotId)) {
            return false;
        }
        String this$locationInformation = this.getLocationInformation();
        String other$locationInformation = other.getLocationInformation();
        return !(this$locationInformation == null ? other$locationInformation != null : !this$locationInformation.equals(other$locationInformation));
    }

    protected boolean canEqual(Object other) {
        return other instanceof RobotPosition;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $robotId = this.getRobotId();
        result = result * 59 + ($robotId == null ? 43 : $robotId.hashCode());
        String $locationInformation = this.getLocationInformation();
        result = result * 59 + ($locationInformation == null ? 43 : $locationInformation.hashCode());
        return result;
    }

    public String toString() {
        return "RobotPosition(robotId=" + this.getRobotId() + ", locationInformation=" + this.getLocationInformation() + ")";
    }
}
