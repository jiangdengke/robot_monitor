/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.bot.domain;

public class RobotRequest {
    private long robotId;

    public RobotRequest() {
    }

    public RobotRequest(long robotId) {
        this.robotId = robotId;
    }

    public long getRobotId() {
        return this.robotId;
    }

    public void setRobotId(long robotId) {
        this.robotId = robotId;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RobotRequest)) {
            return false;
        }
        RobotRequest other = (RobotRequest)o;
        if (!other.canEqual(this)) {
            return false;
        }
        return this.getRobotId() == other.getRobotId();
    }

    protected boolean canEqual(Object other) {
        return other instanceof RobotRequest;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $robotId = this.getRobotId();
        result = result * 59 + (int)($robotId >>> 32 ^ $robotId);
        return result;
    }

    public String toString() {
        return "RobotRequest(robotId=" + this.getRobotId() + ")";
    }
}
