/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.bot.domain;

import com.robotmonitor.bot.domain.RobotRequest;

public class RobotSimpleCmdRequest
extends RobotRequest {
    private long taskId;

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RobotSimpleCmdRequest)) {
            return false;
        }
        RobotSimpleCmdRequest other = (RobotSimpleCmdRequest)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return this.getTaskId() == other.getTaskId();
    }

    @Override
    protected boolean canEqual(Object other) {
        return other instanceof RobotSimpleCmdRequest;
    }

    @Override
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        long $taskId = this.getTaskId();
        result = result * 59 + (int)($taskId >>> 32 ^ $taskId);
        return result;
    }

    public long getTaskId() {
        return this.taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "RobotSimpleCmdRequest(taskId=" + this.getTaskId() + ")";
    }
}
