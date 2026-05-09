/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain;

public class FlightParam {
    private String flightDate;
    private Long currentTime;
    private Long earlyTime;

    public String getFlightDate() {
        return this.flightDate;
    }

    public Long getCurrentTime() {
        return this.currentTime;
    }

    public Long getEarlyTime() {
        return this.earlyTime;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public void setCurrentTime(Long currentTime) {
        this.currentTime = currentTime;
    }

    public void setEarlyTime(Long earlyTime) {
        this.earlyTime = earlyTime;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FlightParam)) {
            return false;
        }
        FlightParam other = (FlightParam)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Long this$currentTime = this.getCurrentTime();
        Long other$currentTime = other.getCurrentTime();
        if (this$currentTime == null ? other$currentTime != null : !((Object)this$currentTime).equals(other$currentTime)) {
            return false;
        }
        Long this$earlyTime = this.getEarlyTime();
        Long other$earlyTime = other.getEarlyTime();
        if (this$earlyTime == null ? other$earlyTime != null : !((Object)this$earlyTime).equals(other$earlyTime)) {
            return false;
        }
        String this$flightDate = this.getFlightDate();
        String other$flightDate = other.getFlightDate();
        return !(this$flightDate == null ? other$flightDate != null : !this$flightDate.equals(other$flightDate));
    }

    protected boolean canEqual(Object other) {
        return other instanceof FlightParam;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $currentTime = this.getCurrentTime();
        result = result * 59 + ($currentTime == null ? 43 : ((Object)$currentTime).hashCode());
        Long $earlyTime = this.getEarlyTime();
        result = result * 59 + ($earlyTime == null ? 43 : ((Object)$earlyTime).hashCode());
        String $flightDate = this.getFlightDate();
        result = result * 59 + ($flightDate == null ? 43 : $flightDate.hashCode());
        return result;
    }

    public String toString() {
        return "FlightParam(flightDate=" + this.getFlightDate() + ", currentTime=" + this.getCurrentTime() + ", earlyTime=" + this.getEarlyTime() + ")";
    }
}
