/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.xml.bind.annotation.XmlRootElement
 */
package com.robotmonitor.flight.domain.kafka;

import com.robotmonitor.flight.domain.kafka.Flight;
import com.robotmonitor.flight.domain.kafka.Meta;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="msg")
public class Msg {
    private Meta meta;
    private Flight flight;

    public Meta getMeta() {
        return this.meta;
    }

    public Flight getFlight() {
        return this.flight;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Msg)) {
            return false;
        }
        Msg other = (Msg)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Meta this$meta = this.getMeta();
        Meta other$meta = other.getMeta();
        if (this$meta == null ? other$meta != null : !((Object)this$meta).equals(other$meta)) {
            return false;
        }
        Flight this$flight = this.getFlight();
        Flight other$flight = other.getFlight();
        return !(this$flight == null ? other$flight != null : !((Object)this$flight).equals(other$flight));
    }

    protected boolean canEqual(Object other) {
        return other instanceof Msg;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Meta $meta = this.getMeta();
        result = result * 59 + ($meta == null ? 43 : ((Object)$meta).hashCode());
        Flight $flight = this.getFlight();
        result = result * 59 + ($flight == null ? 43 : ((Object)$flight).hashCode());
        return result;
    }

    public String toString() {
        return "Msg(meta=" + this.getMeta() + ", flight=" + this.getFlight() + ")";
    }
}
