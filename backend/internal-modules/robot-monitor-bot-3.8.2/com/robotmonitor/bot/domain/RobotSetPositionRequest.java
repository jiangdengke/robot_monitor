/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.bot.domain;

import com.robotmonitor.bot.domain.RobotRequest;

public class RobotSetPositionRequest
extends RobotRequest {
    private String positionInfo;

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RobotSetPositionRequest)) {
            return false;
        }
        RobotSetPositionRequest other = (RobotSetPositionRequest)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        String this$positionInfo = this.getPositionInfo();
        String other$positionInfo = other.getPositionInfo();
        return !(this$positionInfo == null ? other$positionInfo != null : !this$positionInfo.equals(other$positionInfo));
    }

    @Override
    protected boolean canEqual(Object other) {
        return other instanceof RobotSetPositionRequest;
    }

    @Override
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        String $positionInfo = this.getPositionInfo();
        result = result * 59 + ($positionInfo == null ? 43 : $positionInfo.hashCode());
        return result;
    }

    public String getPositionInfo() {
        return this.positionInfo;
    }

    public void setPositionInfo(String positionInfo) {
        this.positionInfo = positionInfo;
    }

    @Override
    public String toString() {
        return "RobotSetPositionRequest(positionInfo=" + this.getPositionInfo() + ")";
    }
}
