/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain;

import com.robotmonitor.flight.domain.PassengerLocationLog;
import java.util.List;

public class PassengerStatistics {
    private int currentPassengerCount;
    private int departedPassengerCount;
    private int visitorCount;
    private List<PassengerLocationLog> currentPassengerDetails;
    private List<PassengerLocationLog> departedPassengerDetails;
    private List<PassengerLocationLog> visitorDetails;

    public int getCurrentPassengerCount() {
        return this.currentPassengerCount;
    }

    public void setCurrentPassengerCount(int currentPassengerCount) {
        this.currentPassengerCount = currentPassengerCount;
    }

    public int getDepartedPassengerCount() {
        return this.departedPassengerCount;
    }

    public void setDepartedPassengerCount(int departedPassengerCount) {
        this.departedPassengerCount = departedPassengerCount;
    }

    public int getVisitorCount() {
        return this.visitorCount;
    }

    public void setVisitorCount(int visitorCount) {
        this.visitorCount = visitorCount;
    }

    public List<PassengerLocationLog> getCurrentPassengerDetails() {
        return this.currentPassengerDetails;
    }

    public void setCurrentPassengerDetails(List<PassengerLocationLog> currentPassengerDetails) {
        this.currentPassengerDetails = currentPassengerDetails;
    }

    public List<PassengerLocationLog> getDepartedPassengerDetails() {
        return this.departedPassengerDetails;
    }

    public void setDepartedPassengerDetails(List<PassengerLocationLog> departedPassengerDetails) {
        this.departedPassengerDetails = departedPassengerDetails;
    }

    public List<PassengerLocationLog> getVisitorDetails() {
        return this.visitorDetails;
    }

    public void setVisitorDetails(List<PassengerLocationLog> visitorDetails) {
        this.visitorDetails = visitorDetails;
    }
}
