/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain.dto;

import com.robotmonitor.flight.domain.dto.PassengerPageResultDTO;

public class PassengerStatisticsDTO {
    private PassengerPageResultDTO passengers;

    public PassengerPageResultDTO getPassengers() {
        return this.passengers;
    }

    public void setPassengers(PassengerPageResultDTO passengers) {
        this.passengers = passengers;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PassengerStatisticsDTO)) {
            return false;
        }
        PassengerStatisticsDTO other = (PassengerStatisticsDTO)o;
        if (!other.canEqual(this)) {
            return false;
        }
        PassengerPageResultDTO this$passengers = this.getPassengers();
        PassengerPageResultDTO other$passengers = other.getPassengers();
        return !(this$passengers == null ? other$passengers != null : !((Object)this$passengers).equals(other$passengers));
    }

    protected boolean canEqual(Object other) {
        return other instanceof PassengerStatisticsDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        PassengerPageResultDTO $passengers = this.getPassengers();
        result = result * 59 + ($passengers == null ? 43 : ((Object)$passengers).hashCode());
        return result;
    }

    public String toString() {
        return "PassengerStatisticsDTO(passengers=" + this.getPassengers() + ")";
    }
}
