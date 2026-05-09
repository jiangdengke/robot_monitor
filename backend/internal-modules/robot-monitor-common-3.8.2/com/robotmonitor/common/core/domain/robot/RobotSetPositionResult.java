/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.core.domain.robot;

public class RobotSetPositionResult {
    private String robotId;
    private boolean setPositionResult;

    public RobotSetPositionResult() {
    }

    public RobotSetPositionResult(String robotId, boolean setPositionResult) {
        this.robotId = robotId;
        this.setPositionResult = setPositionResult;
    }

    public String getRobotId() {
        return this.robotId;
    }

    public boolean isSetPositionResult() {
        return this.setPositionResult;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public void setSetPositionResult(boolean setPositionResult) {
        this.setPositionResult = setPositionResult;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RobotSetPositionResult)) {
            return false;
        }
        RobotSetPositionResult other = (RobotSetPositionResult)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.isSetPositionResult() != other.isSetPositionResult()) {
            return false;
        }
        String this$robotId = this.getRobotId();
        String other$robotId = other.getRobotId();
        return !(this$robotId == null ? other$robotId != null : !this$robotId.equals(other$robotId));
    }

    protected boolean canEqual(Object other) {
        return other instanceof RobotSetPositionResult;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isSetPositionResult() ? 79 : 97);
        String $robotId = this.getRobotId();
        result = result * 59 + ($robotId == null ? 43 : $robotId.hashCode());
        return result;
    }

    public String toString() {
        return "RobotSetPositionResult(robotId=" + this.getRobotId() + ", setPositionResult=" + this.isSetPositionResult() + ")";
    }
}
