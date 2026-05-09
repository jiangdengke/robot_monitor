/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.ai.domain;

public class FlightRequestInfo {
    private String flightNo;
    private String flightDate;

    public String getFlightNo() {
        return this.flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getFlightDate() {
        return this.flightDate;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public String toString() {
        return "FlightRequestInfo{flightNo='" + this.flightNo + "', flightDate='" + this.flightDate + "'}";
    }
}
