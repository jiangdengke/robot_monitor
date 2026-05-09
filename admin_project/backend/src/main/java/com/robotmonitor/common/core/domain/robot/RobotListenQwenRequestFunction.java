/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.core.domain.robot;

public class RobotListenQwenRequestFunction {
    private String name;
    private String param;

    public RobotListenQwenRequestFunction() {
    }

    public RobotListenQwenRequestFunction(String name, String param) {
        this.name = name;
        this.param = param;
    }

    public String getName() {
        return this.name;
    }

    public String getParam() {
        return this.param;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RobotListenQwenRequestFunction)) {
            return false;
        }
        RobotListenQwenRequestFunction other = (RobotListenQwenRequestFunction)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        String this$param = this.getParam();
        String other$param = other.getParam();
        return !(this$param == null ? other$param != null : !this$param.equals(other$param));
    }

    protected boolean canEqual(Object other) {
        return other instanceof RobotListenQwenRequestFunction;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $param = this.getParam();
        result = result * 59 + ($param == null ? 43 : $param.hashCode());
        return result;
    }

    public String toString() {
        return "RobotListenQwenRequestFunction(name=" + this.getName() + ", param=" + this.getParam() + ")";
    }
}
