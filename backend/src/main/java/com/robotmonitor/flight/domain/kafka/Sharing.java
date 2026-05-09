/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain.kafka;

public class Sharing {
    private String shareAirlineIataCd;
    private String shareFlightNo;

    public String getShareAirlineIataCd() {
        return this.shareAirlineIataCd;
    }

    public String getShareFlightNo() {
        return this.shareFlightNo;
    }

    public void setShareAirlineIataCd(String shareAirlineIataCd) {
        this.shareAirlineIataCd = shareAirlineIataCd;
    }

    public void setShareFlightNo(String shareFlightNo) {
        this.shareFlightNo = shareFlightNo;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Sharing)) {
            return false;
        }
        Sharing other = (Sharing)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$shareAirlineIataCd = this.getShareAirlineIataCd();
        String other$shareAirlineIataCd = other.getShareAirlineIataCd();
        if (this$shareAirlineIataCd == null ? other$shareAirlineIataCd != null : !this$shareAirlineIataCd.equals(other$shareAirlineIataCd)) {
            return false;
        }
        String this$shareFlightNo = this.getShareFlightNo();
        String other$shareFlightNo = other.getShareFlightNo();
        return !(this$shareFlightNo == null ? other$shareFlightNo != null : !this$shareFlightNo.equals(other$shareFlightNo));
    }

    protected boolean canEqual(Object other) {
        return other instanceof Sharing;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $shareAirlineIataCd = this.getShareAirlineIataCd();
        result = result * 59 + ($shareAirlineIataCd == null ? 43 : $shareAirlineIataCd.hashCode());
        String $shareFlightNo = this.getShareFlightNo();
        result = result * 59 + ($shareFlightNo == null ? 43 : $shareFlightNo.hashCode());
        return result;
    }

    public String toString() {
        return "Sharing(shareAirlineIataCd=" + this.getShareAirlineIataCd() + ", shareFlightNo=" + this.getShareFlightNo() + ")";
    }
}
