/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.core.domain.robot;

public class SenseVoiceRequest {
    public static final String DEFAULT_SUFFIX = "wav";
    private String suffix = "wav";
    private String voice;

    public SenseVoiceRequest() {
    }

    public SenseVoiceRequest(String voice) {
        this.voice = voice;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public String getVoice() {
        return this.voice;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SenseVoiceRequest)) {
            return false;
        }
        SenseVoiceRequest other = (SenseVoiceRequest)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$suffix = this.getSuffix();
        String other$suffix = other.getSuffix();
        if (this$suffix == null ? other$suffix != null : !this$suffix.equals(other$suffix)) {
            return false;
        }
        String this$voice = this.getVoice();
        String other$voice = other.getVoice();
        return !(this$voice == null ? other$voice != null : !this$voice.equals(other$voice));
    }

    protected boolean canEqual(Object other) {
        return other instanceof SenseVoiceRequest;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $suffix = this.getSuffix();
        result = result * 59 + ($suffix == null ? 43 : $suffix.hashCode());
        String $voice = this.getVoice();
        result = result * 59 + ($voice == null ? 43 : $voice.hashCode());
        return result;
    }

    public String toString() {
        return "SenseVoiceRequest(suffix=" + this.getSuffix() + ", voice=" + this.getVoice() + ")";
    }
}
