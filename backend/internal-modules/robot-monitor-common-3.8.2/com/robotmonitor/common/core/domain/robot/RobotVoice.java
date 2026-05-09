/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.core.domain.robot;

public class RobotVoice {
    private String id;
    private String voice;

    public String getId() {
        return this.id;
    }

    public String getVoice() {
        return this.voice;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RobotVoice)) {
            return false;
        }
        RobotVoice other = (RobotVoice)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$id = this.getId();
        String other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        String this$voice = this.getVoice();
        String other$voice = other.getVoice();
        return !(this$voice == null ? other$voice != null : !this$voice.equals(other$voice));
    }

    protected boolean canEqual(Object other) {
        return other instanceof RobotVoice;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        String $voice = this.getVoice();
        result = result * 59 + ($voice == null ? 43 : $voice.hashCode());
        return result;
    }

    public String toString() {
        return "RobotVoice(id=" + this.getId() + ", voice=" + this.getVoice() + ")";
    }
}
