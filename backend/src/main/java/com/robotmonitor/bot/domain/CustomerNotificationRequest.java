/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.bot.domain;

public class CustomerNotificationRequest {
    private String robotId;
    private String coordinate;
    private String text;
    private String language = "CN";

    public String getRobotId() {
        return this.robotId;
    }

    public String getCoordinate() {
        return this.coordinate;
    }

    public String getText() {
        return this.text;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
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
        if (!(o instanceof CustomerNotificationRequest)) {
            return false;
        }
        CustomerNotificationRequest other = (CustomerNotificationRequest)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$robotId = this.getRobotId();
        String other$robotId = other.getRobotId();
        if (this$robotId == null ? other$robotId != null : !this$robotId.equals(other$robotId)) {
            return false;
        }
        String this$coordinate = this.getCoordinate();
        String other$coordinate = other.getCoordinate();
        if (this$coordinate == null ? other$coordinate != null : !this$coordinate.equals(other$coordinate)) {
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
        return other instanceof CustomerNotificationRequest;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $robotId = this.getRobotId();
        result = result * 59 + ($robotId == null ? 43 : $robotId.hashCode());
        String $coordinate = this.getCoordinate();
        result = result * 59 + ($coordinate == null ? 43 : $coordinate.hashCode());
        String $text = this.getText();
        result = result * 59 + ($text == null ? 43 : $text.hashCode());
        String $language = this.getLanguage();
        result = result * 59 + ($language == null ? 43 : $language.hashCode());
        return result;
    }

    public String toString() {
        return "CustomerNotificationRequest(robotId=" + this.getRobotId() + ", coordinate=" + this.getCoordinate() + ", text=" + this.getText() + ", language=" + this.getLanguage() + ")";
    }
}
