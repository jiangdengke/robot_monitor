/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.ai.domain;

public class ParamPlaceName {
    private String placeName;

    public String getPlaceName() {
        return this.placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ParamPlaceName)) {
            return false;
        }
        ParamPlaceName other = (ParamPlaceName)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$placeName = this.getPlaceName();
        String other$placeName = other.getPlaceName();
        return !(this$placeName == null ? other$placeName != null : !this$placeName.equals(other$placeName));
    }

    protected boolean canEqual(Object other) {
        return other instanceof ParamPlaceName;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $placeName = this.getPlaceName();
        result = result * 59 + ($placeName == null ? 43 : $placeName.hashCode());
        return result;
    }

    public String toString() {
        return "ParamPlaceName(placeName=" + this.getPlaceName() + ")";
    }
}
