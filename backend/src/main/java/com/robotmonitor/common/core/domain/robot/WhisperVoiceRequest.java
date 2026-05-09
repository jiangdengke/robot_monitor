/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.core.domain.robot;

public class WhisperVoiceRequest {
    public static final String MODEL_TINY = "tiny";
    public static final String MODEL_BASE = "base";
    public static final String MODEL_SMALL = "small";
    public static final String MODEL_MEDIUM = "medium";
    public static final String MODEL_LARGE = "large";
    public static final String DEFAULT_SUFFIX = "wav";
    private String model = "base";
    private String suffix = "wav";
    private String voice;

    public WhisperVoiceRequest() {
    }

    public WhisperVoiceRequest(String voice) {
        this.voice = voice;
    }

    public String getModel() {
        return this.model;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public String getVoice() {
        return this.voice;
    }

    public void setModel(String model) {
        this.model = model;
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
        if (!(o instanceof WhisperVoiceRequest)) {
            return false;
        }
        WhisperVoiceRequest other = (WhisperVoiceRequest)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$model = this.getModel();
        String other$model = other.getModel();
        if (this$model == null ? other$model != null : !this$model.equals(other$model)) {
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
        return other instanceof WhisperVoiceRequest;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $model = this.getModel();
        result = result * 59 + ($model == null ? 43 : $model.hashCode());
        String $suffix = this.getSuffix();
        result = result * 59 + ($suffix == null ? 43 : $suffix.hashCode());
        String $voice = this.getVoice();
        result = result * 59 + ($voice == null ? 43 : $voice.hashCode());
        return result;
    }

    public String toString() {
        return "WhisperVoiceRequest(model=" + this.getModel() + ", suffix=" + this.getSuffix() + ", voice=" + this.getVoice() + ")";
    }
}
