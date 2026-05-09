/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 */
package com.robotmonitor.common.core.domain.robot;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class RobotStatus {
    private int robot_id;
    private boolean charging_state;
    private boolean working_state;
    private boolean standby_state;
    private String positioning_state;
    private String location;
    private int battery_state;
    private boolean robot_error;
    private String error_messages;
    private long task_id;
    private Long robot_task_id;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date update_time;

    public int getRobot_id() {
        return this.robot_id;
    }

    public boolean isCharging_state() {
        return this.charging_state;
    }

    public boolean isWorking_state() {
        return this.working_state;
    }

    public boolean isStandby_state() {
        return this.standby_state;
    }

    public String getPositioning_state() {
        return this.positioning_state;
    }

    public String getLocation() {
        return this.location;
    }

    public int getBattery_state() {
        return this.battery_state;
    }

    public boolean isRobot_error() {
        return this.robot_error;
    }

    public String getError_messages() {
        return this.error_messages;
    }

    public long getTask_id() {
        return this.task_id;
    }

    public Long getRobot_task_id() {
        return this.robot_task_id;
    }

    public Date getUpdate_time() {
        return this.update_time;
    }

    public void setRobot_id(int robot_id) {
        this.robot_id = robot_id;
    }

    public void setCharging_state(boolean charging_state) {
        this.charging_state = charging_state;
    }

    public void setWorking_state(boolean working_state) {
        this.working_state = working_state;
    }

    public void setStandby_state(boolean standby_state) {
        this.standby_state = standby_state;
    }

    public void setPositioning_state(String positioning_state) {
        this.positioning_state = positioning_state;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setBattery_state(int battery_state) {
        this.battery_state = battery_state;
    }

    public void setRobot_error(boolean robot_error) {
        this.robot_error = robot_error;
    }

    public void setError_messages(String error_messages) {
        this.error_messages = error_messages;
    }

    public void setTask_id(long task_id) {
        this.task_id = task_id;
    }

    public void setRobot_task_id(Long robot_task_id) {
        this.robot_task_id = robot_task_id;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RobotStatus)) {
            return false;
        }
        RobotStatus other = (RobotStatus)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getRobot_id() != other.getRobot_id()) {
            return false;
        }
        if (this.isCharging_state() != other.isCharging_state()) {
            return false;
        }
        if (this.isWorking_state() != other.isWorking_state()) {
            return false;
        }
        if (this.isStandby_state() != other.isStandby_state()) {
            return false;
        }
        if (this.getBattery_state() != other.getBattery_state()) {
            return false;
        }
        if (this.isRobot_error() != other.isRobot_error()) {
            return false;
        }
        if (this.getTask_id() != other.getTask_id()) {
            return false;
        }
        Long this$robot_task_id = this.getRobot_task_id();
        Long other$robot_task_id = other.getRobot_task_id();
        if (this$robot_task_id == null ? other$robot_task_id != null : !((Object)this$robot_task_id).equals(other$robot_task_id)) {
            return false;
        }
        String this$positioning_state = this.getPositioning_state();
        String other$positioning_state = other.getPositioning_state();
        if (this$positioning_state == null ? other$positioning_state != null : !this$positioning_state.equals(other$positioning_state)) {
            return false;
        }
        String this$location = this.getLocation();
        String other$location = other.getLocation();
        if (this$location == null ? other$location != null : !this$location.equals(other$location)) {
            return false;
        }
        String this$error_messages = this.getError_messages();
        String other$error_messages = other.getError_messages();
        if (this$error_messages == null ? other$error_messages != null : !this$error_messages.equals(other$error_messages)) {
            return false;
        }
        Date this$update_time = this.getUpdate_time();
        Date other$update_time = other.getUpdate_time();
        return !(this$update_time == null ? other$update_time != null : !((Object)this$update_time).equals(other$update_time));
    }

    protected boolean canEqual(Object other) {
        return other instanceof RobotStatus;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getRobot_id();
        result = result * 59 + (this.isCharging_state() ? 79 : 97);
        result = result * 59 + (this.isWorking_state() ? 79 : 97);
        result = result * 59 + (this.isStandby_state() ? 79 : 97);
        result = result * 59 + this.getBattery_state();
        result = result * 59 + (this.isRobot_error() ? 79 : 97);
        long $task_id = this.getTask_id();
        result = result * 59 + (int)($task_id >>> 32 ^ $task_id);
        Long $robot_task_id = this.getRobot_task_id();
        result = result * 59 + ($robot_task_id == null ? 43 : ((Object)$robot_task_id).hashCode());
        String $positioning_state = this.getPositioning_state();
        result = result * 59 + ($positioning_state == null ? 43 : $positioning_state.hashCode());
        String $location = this.getLocation();
        result = result * 59 + ($location == null ? 43 : $location.hashCode());
        String $error_messages = this.getError_messages();
        result = result * 59 + ($error_messages == null ? 43 : $error_messages.hashCode());
        Date $update_time = this.getUpdate_time();
        result = result * 59 + ($update_time == null ? 43 : ((Object)$update_time).hashCode());
        return result;
    }

    public String toString() {
        return "RobotStatus(robot_id=" + this.getRobot_id() + ", charging_state=" + this.isCharging_state() + ", working_state=" + this.isWorking_state() + ", standby_state=" + this.isStandby_state() + ", positioning_state=" + this.getPositioning_state() + ", location=" + this.getLocation() + ", battery_state=" + this.getBattery_state() + ", robot_error=" + this.isRobot_error() + ", error_messages=" + this.getError_messages() + ", task_id=" + this.getTask_id() + ", robot_task_id=" + this.getRobot_task_id() + ", update_time=" + this.getUpdate_time() + ")";
    }
}
