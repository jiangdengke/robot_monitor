/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.core.domain.robot;

public class RobotVoiceResponse {
    private String text;
    private String language;

    public String getText() {
        return this.text;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RobotVoiceResponse)) {
            return false;
        }
        RobotVoiceResponse other = (RobotVoiceResponse)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$text = this.getText();
        String other$text = other.getText();
        if (this$text == null ? other$text != null : !this$text.equals(other$text)) {
            return false;
        }
        String this$language = this.getLanguage();
        String other$language = other.getLanguage();
        return !(this$language == null ? other$language != null : !this$language.equals(other$language));
    }

    protected boolean canEqual(Object other) {
        return other instanceof RobotVoiceResponse;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $text = this.getText();
        result = result * 59 + ($text == null ? 43 : $text.hashCode());
        String $language = this.getLanguage();
        result = result * 59 + ($language == null ? 43 : $language.hashCode());
        return result;
    }

    public String toString() {
        return "RobotVoiceResponse(text=" + this.getText() + ", language=" + this.getLanguage() + ")";
    }
}
