/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.ai.domain;

public class ParamFlightInfo {
    private String flightNo;
    private String flightDate;

    public String getFlightNo() {
        return this.flightNo;
    }

    public String getFlightDate() {
        return this.flightDate;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ParamFlightInfo)) {
            return false;
        }
        ParamFlightInfo other = (ParamFlightInfo)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$flightNo = this.getFlightNo();
        String other$flightNo = other.getFlightNo();
        if (this$flightNo == null ? other$flightNo != null : !this$flightNo.equals(other$flightNo)) {
            return false;
        }
        String this$flightDate = this.getFlightDate();
        String other$flightDate = other.getFlightDate();
        return !(this$flightDate == null ? other$flightDate != null : !this$flightDate.equals(other$flightDate));
    }

    protected boolean canEqual(Object other) {
        return other instanceof ParamFlightInfo;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $flightNo = this.getFlightNo();
        result = result * 59 + ($flightNo == null ? 43 : $flightNo.hashCode());
        String $flightDate = this.getFlightDate();
        result = result * 59 + ($flightDate == null ? 43 : $flightDate.hashCode());
        return result;
    }

    public String toString() {
        return "ParamFlightInfo(flightNo=" + this.getFlightNo() + ", flightDate=" + this.getFlightDate() + ")";
    }
}
