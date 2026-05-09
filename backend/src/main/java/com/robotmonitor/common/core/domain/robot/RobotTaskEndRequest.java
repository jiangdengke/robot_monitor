/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.core.domain.robot;

public class RobotTaskEndRequest {
    private Long robot_id;
    private Long robot_task_id;
    private String message;
    private boolean is_ok;

    public RobotTaskEndRequest(int robotId, long robotTaskId, String s) {
        this.robot_id = robotId;
        this.robot_task_id = robotTaskId;
        this.message = s;
    }

    public RobotTaskEndRequest() {
    }

    public Long getRobot_id() {
        return this.robot_id;
    }

    public Long getRobot_task_id() {
        return this.robot_task_id;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean is_ok() {
        return this.is_ok;
    }

    public void setRobot_id(Long robot_id) {
        this.robot_id = robot_id;
    }

    public void setRobot_task_id(Long robot_task_id) {
        this.robot_task_id = robot_task_id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void set_ok(boolean is_ok) {
        this.is_ok = is_ok;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RobotTaskEndRequest)) {
            return false;
        }
        RobotTaskEndRequest other = (RobotTaskEndRequest)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.is_ok() != other.is_ok()) {
            return false;
        }
        Long this$robot_id = this.getRobot_id();
        Long other$robot_id = other.getRobot_id();
        if (this$robot_id == null ? other$robot_id != null : !((Object)this$robot_id).equals(other$robot_id)) {
            return false;
        }
        Long this$robot_task_id = this.getRobot_task_id();
        Long other$robot_task_id = other.getRobot_task_id();
        if (this$robot_task_id == null ? other$robot_task_id != null : !((Object)this$robot_task_id).equals(other$robot_task_id)) {
            return false;
        }
        String this$message = this.getMessage();
        String other$message = other.getMessage();
        return !(this$message == null ? other$message != null : !this$message.equals(other$message));
    }

    protected boolean canEqual(Object other) {
        return other instanceof RobotTaskEndRequest;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.is_ok() ? 79 : 97);
        Long $robot_id = this.getRobot_id();
        result = result * 59 + ($robot_id == null ? 43 : ((Object)$robot_id).hashCode());
        Long $robot_task_id = this.getRobot_task_id();
        result = result * 59 + ($robot_task_id == null ? 43 : ((Object)$robot_task_id).hashCode());
        String $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        return result;
    }

    public String toString() {
        return "RobotTaskEndRequest(robot_id=" + this.getRobot_id() + ", robot_task_id=" + this.getRobot_task_id() + ", message=" + this.getMessage() + ", is_ok=" + this.is_ok() + ")";
    }
}
