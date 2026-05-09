/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.core.domain.robot;

public class RobotHttpCmd {
    private long robotId;
    private int state;

    public RobotHttpCmd() {
    }

    public RobotHttpCmd(long robotId, int state) {
        this.robotId = robotId;
        this.state = state;
    }

    public long getRobotId() {
        return this.robotId;
    }

    public int getState() {
        return this.state;
    }

    public void setRobotId(long robotId) {
        this.robotId = robotId;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RobotHttpCmd)) {
            return false;
        }
        RobotHttpCmd other = (RobotHttpCmd)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getRobotId() != other.getRobotId()) {
            return false;
        }
        return this.getState() == other.getState();
    }

    protected boolean canEqual(Object other) {
        return other instanceof RobotHttpCmd;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $robotId = this.getRobotId();
        result = result * 59 + (int)($robotId >>> 32 ^ $robotId);
        result = result * 59 + this.getState();
        return result;
    }

    public String toString() {
        return "RobotHttpCmd(robotId=" + this.getRobotId() + ", state=" + this.getState() + ")";
    }
}
