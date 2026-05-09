/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.xml.bind.annotation.XmlElement
 *  javax.xml.bind.annotation.XmlAccessType
 *  javax.xml.bind.annotation.XmlAccessorType
 */
package com.robotmonitor.flight.domain.kafka;

import jakarta.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(value=XmlAccessType.FIELD)
public class Runway {
    @XmlElement(name="runwayCd")
    private String runwayCd;

    public String getRunwayCd() {
        return this.runwayCd;
    }

    public void setRunwayCd(String runwayCd) {
        this.runwayCd = runwayCd;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Runway)) {
            return false;
        }
        Runway other = (Runway)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$runwayCd = this.getRunwayCd();
        String other$runwayCd = other.getRunwayCd();
        return !(this$runwayCd == null ? other$runwayCd != null : !this$runwayCd.equals(other$runwayCd));
    }

    protected boolean canEqual(Object other) {
        return other instanceof Runway;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $runwayCd = this.getRunwayCd();
        result = result * 59 + ($runwayCd == null ? 43 : $runwayCd.hashCode());
        return result;
    }

    public String toString() {
        return "Runway(runwayCd=" + this.getRunwayCd() + ")";
    }
}
