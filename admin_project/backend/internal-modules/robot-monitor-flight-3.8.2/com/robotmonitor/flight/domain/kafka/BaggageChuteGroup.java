/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain.kafka;

import com.robotmonitor.flight.domain.kafka.BaggageChute;
import java.util.ArrayList;

public class BaggageChuteGroup {
    private ArrayList<BaggageChute> baggageChute;

    public ArrayList<BaggageChute> getBaggageChute() {
        return this.baggageChute;
    }

    public void setBaggageChute(ArrayList<BaggageChute> baggageChute) {
        this.baggageChute = baggageChute;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BaggageChuteGroup)) {
            return false;
        }
        BaggageChuteGroup other = (BaggageChuteGroup)o;
        if (!other.canEqual(this)) {
            return false;
        }
        ArrayList<BaggageChute> this$baggageChute = this.getBaggageChute();
        ArrayList<BaggageChute> other$baggageChute = other.getBaggageChute();
        return !(this$baggageChute == null ? other$baggageChute != null : !((Object)this$baggageChute).equals(other$baggageChute));
    }

    protected boolean canEqual(Object other) {
        return other instanceof BaggageChuteGroup;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        ArrayList<BaggageChute> $baggageChute = this.getBaggageChute();
        result = result * 59 + ($baggageChute == null ? 43 : ((Object)$baggageChute).hashCode());
        return result;
    }

    public String toString() {
        return "BaggageChuteGroup(baggageChute=" + this.getBaggageChute() + ")";
    }
}
