/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain.kafka;

public class Alternate {
    private String alternateNo;
    private String alternateIataCd;
    private String divEstmTakeOffTime;
    private String divActlTakeOffTime;
    private String divEstmLandInTime;
    private String divActlLandInTime;

    public String getAlternateNo() {
        return this.alternateNo;
    }

    public String getAlternateIataCd() {
        return this.alternateIataCd;
    }

    public String getDivEstmTakeOffTime() {
        return this.divEstmTakeOffTime;
    }

    public String getDivActlTakeOffTime() {
        return this.divActlTakeOffTime;
    }

    public String getDivEstmLandInTime() {
        return this.divEstmLandInTime;
    }

    public String getDivActlLandInTime() {
        return this.divActlLandInTime;
    }

    public void setAlternateNo(String alternateNo) {
        this.alternateNo = alternateNo;
    }

    public void setAlternateIataCd(String alternateIataCd) {
        this.alternateIataCd = alternateIataCd;
    }

    public void setDivEstmTakeOffTime(String divEstmTakeOffTime) {
        this.divEstmTakeOffTime = divEstmTakeOffTime;
    }

    public void setDivActlTakeOffTime(String divActlTakeOffTime) {
        this.divActlTakeOffTime = divActlTakeOffTime;
    }

    public void setDivEstmLandInTime(String divEstmLandInTime) {
        this.divEstmLandInTime = divEstmLandInTime;
    }

    public void setDivActlLandInTime(String divActlLandInTime) {
        this.divActlLandInTime = divActlLandInTime;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Alternate)) {
            return false;
        }
        Alternate other = (Alternate)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$alternateNo = this.getAlternateNo();
        String other$alternateNo = other.getAlternateNo();
        if (this$alternateNo == null ? other$alternateNo != null : !this$alternateNo.equals(other$alternateNo)) {
            return false;
        }
        String this$alternateIataCd = this.getAlternateIataCd();
        String other$alternateIataCd = other.getAlternateIataCd();
        if (this$alternateIataCd == null ? other$alternateIataCd != null : !this$alternateIataCd.equals(other$alternateIataCd)) {
            return false;
        }
        String this$divEstmTakeOffTime = this.getDivEstmTakeOffTime();
        String other$divEstmTakeOffTime = other.getDivEstmTakeOffTime();
        if (this$divEstmTakeOffTime == null ? other$divEstmTakeOffTime != null : !this$divEstmTakeOffTime.equals(other$divEstmTakeOffTime)) {
            return false;
        }
        String this$divActlTakeOffTime = this.getDivActlTakeOffTime();
        String other$divActlTakeOffTime = other.getDivActlTakeOffTime();
        if (this$divActlTakeOffTime == null ? other$divActlTakeOffTime != null : !this$divActlTakeOffTime.equals(other$divActlTakeOffTime)) {
            return false;
        }
        String this$divEstmLandInTime = this.getDivEstmLandInTime();
        String other$divEstmLandInTime = other.getDivEstmLandInTime();
        if (this$divEstmLandInTime == null ? other$divEstmLandInTime != null : !this$divEstmLandInTime.equals(other$divEstmLandInTime)) {
            return false;
        }
        String this$divActlLandInTime = this.getDivActlLandInTime();
        String other$divActlLandInTime = other.getDivActlLandInTime();
        return !(this$divActlLandInTime == null ? other$divActlLandInTime != null : !this$divActlLandInTime.equals(other$divActlLandInTime));
    }

    protected boolean canEqual(Object other) {
        return other instanceof Alternate;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $alternateNo = this.getAlternateNo();
        result = result * 59 + ($alternateNo == null ? 43 : $alternateNo.hashCode());
        String $alternateIataCd = this.getAlternateIataCd();
        result = result * 59 + ($alternateIataCd == null ? 43 : $alternateIataCd.hashCode());
        String $divEstmTakeOffTime = this.getDivEstmTakeOffTime();
        result = result * 59 + ($divEstmTakeOffTime == null ? 43 : $divEstmTakeOffTime.hashCode());
        String $divActlTakeOffTime = this.getDivActlTakeOffTime();
        result = result * 59 + ($divActlTakeOffTime == null ? 43 : $divActlTakeOffTime.hashCode());
        String $divEstmLandInTime = this.getDivEstmLandInTime();
        result = result * 59 + ($divEstmLandInTime == null ? 43 : $divEstmLandInTime.hashCode());
        String $divActlLandInTime = this.getDivActlLandInTime();
        result = result * 59 + ($divActlLandInTime == null ? 43 : $divActlLandInTime.hashCode());
        return result;
    }

    public String toString() {
        return "Alternate(alternateNo=" + this.getAlternateNo() + ", alternateIataCd=" + this.getAlternateIataCd() + ", divEstmTakeOffTime=" + this.getDivEstmTakeOffTime() + ", divActlTakeOffTime=" + this.getDivActlTakeOffTime() + ", divEstmLandInTime=" + this.getDivEstmLandInTime() + ", divActlLandInTime=" + this.getDivActlLandInTime() + ")";
    }
}
