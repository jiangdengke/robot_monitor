/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.core.domain.robot;

public class CameraControl {
    private Integer speed;
    private String command;

    public Integer getSpeed() {
        return this.speed;
    }

    public String getCommand() {
        return this.command;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CameraControl)) {
            return false;
        }
        CameraControl other = (CameraControl)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Integer this$speed = this.getSpeed();
        Integer other$speed = other.getSpeed();
        if (this$speed == null ? other$speed != null : !((Object)this$speed).equals(other$speed)) {
            return false;
        }
        String this$command = this.getCommand();
        String other$command = other.getCommand();
        return !(this$command == null ? other$command != null : !this$command.equals(other$command));
    }

    protected boolean canEqual(Object other) {
        return other instanceof CameraControl;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $speed = this.getSpeed();
        result = result * 59 + ($speed == null ? 43 : ((Object)$speed).hashCode());
        String $command = this.getCommand();
        result = result * 59 + ($command == null ? 43 : $command.hashCode());
        return result;
    }

    public String toString() {
        return "CameraControl(speed=" + this.getSpeed() + ", command=" + this.getCommand() + ")";
    }
}
