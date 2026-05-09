/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain;

public class CollectInResponse {
    private String forceIn;

    public String getForceIn() {
        return this.forceIn;
    }

    public void setForceIn(String forceIn) {
        this.forceIn = forceIn;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CollectInResponse)) {
            return false;
        }
        CollectInResponse other = (CollectInResponse)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$forceIn = this.getForceIn();
        String other$forceIn = other.getForceIn();
        return !(this$forceIn == null ? other$forceIn != null : !this$forceIn.equals(other$forceIn));
    }

    protected boolean canEqual(Object other) {
        return other instanceof CollectInResponse;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $forceIn = this.getForceIn();
        result = result * 59 + ($forceIn == null ? 43 : $forceIn.hashCode());
        return result;
    }

    public String toString() {
        return "CollectInResponse(forceIn=" + this.getForceIn() + ")";
    }
}
