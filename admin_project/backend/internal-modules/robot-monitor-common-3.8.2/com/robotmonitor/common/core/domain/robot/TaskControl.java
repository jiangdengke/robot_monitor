/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.core.domain.robot;

public class TaskControl {
    private Integer device_id;
    private String speed;
    private String command;

    public Integer getDevice_id() {
        return this.device_id;
    }

    public String getSpeed() {
        return this.speed;
    }

    public String getCommand() {
        return this.command;
    }

    public void setDevice_id(Integer device_id) {
        this.device_id = device_id;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TaskControl)) {
            return false;
        }
        TaskControl other = (TaskControl)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Integer this$device_id = this.getDevice_id();
        Integer other$device_id = other.getDevice_id();
        if (this$device_id == null ? other$device_id != null : !((Object)this$device_id).equals(other$device_id)) {
            return false;
        }
        String this$speed = this.getSpeed();
        String other$speed = other.getSpeed();
        if (this$speed == null ? other$speed != null : !this$speed.equals(other$speed)) {
            return false;
        }
        String this$command = this.getCommand();
        String other$command = other.getCommand();
        return !(this$command == null ? other$command != null : !this$command.equals(other$command));
    }

    protected boolean canEqual(Object other) {
        return other instanceof TaskControl;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $device_id = this.getDevice_id();
        result = result * 59 + ($device_id == null ? 43 : ((Object)$device_id).hashCode());
        String $speed = this.getSpeed();
        result = result * 59 + ($speed == null ? 43 : $speed.hashCode());
        String $command = this.getCommand();
        result = result * 59 + ($command == null ? 43 : $command.hashCode());
        return result;
    }

    public String toString() {
        return "TaskControl(device_id=" + this.getDevice_id() + ", speed=" + this.getSpeed() + ", command=" + this.getCommand() + ")";
    }
}
