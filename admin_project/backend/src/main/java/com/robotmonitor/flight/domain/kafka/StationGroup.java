/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.xml.bind.annotation.XmlElement
 *  javax.xml.bind.annotation.XmlAccessType
 *  javax.xml.bind.annotation.XmlAccessorType
 */
package com.robotmonitor.flight.domain.kafka;

import com.robotmonitor.flight.domain.kafka.Station;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(value=XmlAccessType.FIELD)
public class StationGroup {
    @XmlElement(name="station")
    private ArrayList<Station> station;

    public ArrayList<Station> getStation() {
        return this.station;
    }

    public void setStation(ArrayList<Station> station) {
        this.station = station;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof StationGroup)) {
            return false;
        }
        StationGroup other = (StationGroup)o;
        if (!other.canEqual(this)) {
            return false;
        }
        ArrayList<Station> this$station = this.getStation();
        ArrayList<Station> other$station = other.getStation();
        return !(this$station == null ? other$station != null : !((Object)this$station).equals(other$station));
    }

    protected boolean canEqual(Object other) {
        return other instanceof StationGroup;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        ArrayList<Station> $station = this.getStation();
        result = result * 59 + ($station == null ? 43 : ((Object)$station).hashCode());
        return result;
    }

    public String toString() {
        return "StationGroup(station=" + this.getStation() + ")";
    }
}
