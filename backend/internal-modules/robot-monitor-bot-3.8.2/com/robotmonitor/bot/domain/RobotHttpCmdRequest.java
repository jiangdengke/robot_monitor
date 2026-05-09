/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.bot.domain;

import com.robotmonitor.bot.domain.RobotRequest;

public class RobotHttpCmdRequest
extends RobotRequest {
    private int state;

    public RobotHttpCmdRequest() {
    }

    public RobotHttpCmdRequest(long robotId, int state) {
        super(robotId);
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RobotHttpCmdRequest)) {
            return false;
        }
        RobotHttpCmdRequest other = (RobotHttpCmdRequest)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return this.getState() == other.getState();
    }

    @Override
    protected boolean canEqual(Object other) {
        return other instanceof RobotHttpCmdRequest;
    }

    @Override
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        result = result * 59 + this.getState();
        return result;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "RobotHttpCmdRequest(state=" + this.getState() + ")";
    }
}
