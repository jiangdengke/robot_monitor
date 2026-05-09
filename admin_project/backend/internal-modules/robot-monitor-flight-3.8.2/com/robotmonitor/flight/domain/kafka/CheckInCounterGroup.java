/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain.kafka;

import com.robotmonitor.flight.domain.kafka.CheckInCounter;
import java.util.ArrayList;

public class CheckInCounterGroup {
    private ArrayList<CheckInCounter> checkInCounter;

    public ArrayList<CheckInCounter> getCheckInCounter() {
        return this.checkInCounter;
    }

    public void setCheckInCounter(ArrayList<CheckInCounter> checkInCounter) {
        this.checkInCounter = checkInCounter;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CheckInCounterGroup)) {
            return false;
        }
        CheckInCounterGroup other = (CheckInCounterGroup)o;
        if (!other.canEqual(this)) {
            return false;
        }
        ArrayList<CheckInCounter> this$checkInCounter = this.getCheckInCounter();
        ArrayList<CheckInCounter> other$checkInCounter = other.getCheckInCounter();
        return !(this$checkInCounter == null ? other$checkInCounter != null : !((Object)this$checkInCounter).equals(other$checkInCounter));
    }

    protected boolean canEqual(Object other) {
        return other instanceof CheckInCounterGroup;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        ArrayList<CheckInCounter> $checkInCounter = this.getCheckInCounter();
        result = result * 59 + ($checkInCounter == null ? 43 : ((Object)$checkInCounter).hashCode());
        return result;
    }

    public String toString() {
        return "CheckInCounterGroup(checkInCounter=" + this.getCheckInCounter() + ")";
    }
}
