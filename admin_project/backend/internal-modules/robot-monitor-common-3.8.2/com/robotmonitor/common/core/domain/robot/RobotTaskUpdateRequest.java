/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.robotmonitor.common.core.domain.robot;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RobotTaskUpdateRequest {
    private int robot_id;
    private long robot_task_id;
    @JsonProperty(value="is_ok")
    private boolean is_ok;
    private String reason;

    public int getRobot_id() {
        return this.robot_id;
    }

    public long getRobot_task_id() {
        return this.robot_task_id;
    }

    public boolean is_ok() {
        return this.is_ok;
    }

    public String getReason() {
        return this.reason;
    }

    public void setRobot_id(int robot_id) {
        this.robot_id = robot_id;
    }

    public void setRobot_task_id(long robot_task_id) {
        this.robot_task_id = robot_task_id;
    }

    @JsonProperty(value="is_ok")
    public void set_ok(boolean is_ok) {
        this.is_ok = is_ok;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RobotTaskUpdateRequest)) {
            return false;
        }
        RobotTaskUpdateRequest other = (RobotTaskUpdateRequest)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getRobot_id() != other.getRobot_id()) {
            return false;
        }
        if (this.getRobot_task_id() != other.getRobot_task_id()) {
            return false;
        }
        if (this.is_ok() != other.is_ok()) {
            return false;
        }
        String this$reason = this.getReason();
        String other$reason = other.getReason();
        return !(this$reason == null ? other$reason != null : !this$reason.equals(other$reason));
    }

    protected boolean canEqual(Object other) {
        return other instanceof RobotTaskUpdateRequest;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getRobot_id();
        long $robot_task_id = this.getRobot_task_id();
        result = result * 59 + (int)($robot_task_id >>> 32 ^ $robot_task_id);
        result = result * 59 + (this.is_ok() ? 79 : 97);
        String $reason = this.getReason();
        result = result * 59 + ($reason == null ? 43 : $reason.hashCode());
        return result;
    }

    public String toString() {
        return "RobotTaskUpdateRequest(robot_id=" + this.getRobot_id() + ", robot_task_id=" + this.getRobot_task_id() + ", is_ok=" + this.is_ok() + ", reason=" + this.getReason() + ")";
    }
}
