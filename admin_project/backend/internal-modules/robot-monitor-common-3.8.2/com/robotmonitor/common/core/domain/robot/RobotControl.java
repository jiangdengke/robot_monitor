/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.validation.constraints.NotBlank
 */
package com.robotmonitor.common.core.domain.robot;

import jakarta.validation.constraints.NotBlank;

public class RobotControl {
    private String speed;
    @NotBlank(message="\u673a\u5668\u6307\u4ee4\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotBlank(message="\u673a\u5668\u6307\u4ee4\u4e0d\u80fd\u4e3a\u7a7a") String command;

    public String getSpeed() {
        return this.speed;
    }

    public String getCommand() {
        return this.command;
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
        if (!(o instanceof RobotControl)) {
            return false;
        }
        RobotControl other = (RobotControl)o;
        if (!other.canEqual(this)) {
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
        return other instanceof RobotControl;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $speed = this.getSpeed();
        result = result * 59 + ($speed == null ? 43 : $speed.hashCode());
        String $command = this.getCommand();
        result = result * 59 + ($command == null ? 43 : $command.hashCode());
        return result;
    }

    public String toString() {
        return "RobotControl(speed=" + this.getSpeed() + ", command=" + this.getCommand() + ")";
    }
}
