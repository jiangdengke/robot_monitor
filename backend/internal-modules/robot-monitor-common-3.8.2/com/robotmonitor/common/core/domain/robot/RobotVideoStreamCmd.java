/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.core.domain.robot;

public class RobotVideoStreamCmd {
    private String robotId;
    private String cmd;

    public RobotVideoStreamCmd() {
    }

    public RobotVideoStreamCmd(String robotId, String cmd) {
        this.robotId = robotId;
        this.cmd = cmd;
    }

    public String getRobotId() {
        return this.robotId;
    }

    public String getCmd() {
        return this.cmd;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RobotVideoStreamCmd)) {
            return false;
        }
        RobotVideoStreamCmd other = (RobotVideoStreamCmd)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$robotId = this.getRobotId();
        String other$robotId = other.getRobotId();
        if (this$robotId == null ? other$robotId != null : !this$robotId.equals(other$robotId)) {
            return false;
        }
        String this$cmd = this.getCmd();
        String other$cmd = other.getCmd();
        return !(this$cmd == null ? other$cmd != null : !this$cmd.equals(other$cmd));
    }

    protected boolean canEqual(Object other) {
        return other instanceof RobotVideoStreamCmd;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $robotId = this.getRobotId();
        result = result * 59 + ($robotId == null ? 43 : $robotId.hashCode());
        String $cmd = this.getCmd();
        result = result * 59 + ($cmd == null ? 43 : $cmd.hashCode());
        return result;
    }

    public String toString() {
        return "RobotVideoStreamCmd(robotId=" + this.getRobotId() + ", cmd=" + this.getCmd() + ")";
    }
}
