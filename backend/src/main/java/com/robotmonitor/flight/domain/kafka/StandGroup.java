/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.xml.bind.annotation.XmlAccessType
 *  javax.xml.bind.annotation.XmlAccessorType
 */
package com.robotmonitor.flight.domain.kafka;

import com.robotmonitor.flight.domain.kafka.Stand;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(value=XmlAccessType.FIELD)
public class StandGroup {
    private ArrayList<Stand> stand;

    public ArrayList<Stand> getStand() {
        return this.stand;
    }

    public void setStand(ArrayList<Stand> stand) {
        this.stand = stand;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof StandGroup)) {
            return false;
        }
        StandGroup other = (StandGroup)o;
        if (!other.canEqual(this)) {
            return false;
        }
        ArrayList<Stand> this$stand = this.getStand();
        ArrayList<Stand> other$stand = other.getStand();
        return !(this$stand == null ? other$stand != null : !((Object)this$stand).equals(other$stand));
    }

    protected boolean canEqual(Object other) {
        return other instanceof StandGroup;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        ArrayList<Stand> $stand = this.getStand();
        result = result * 59 + ($stand == null ? 43 : ((Object)$stand).hashCode());
        return result;
    }

    public String toString() {
        return "StandGroup(stand=" + this.getStand() + ")";
    }
}
