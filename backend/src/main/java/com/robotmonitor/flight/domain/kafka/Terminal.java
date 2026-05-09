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
public class Terminal {
    @XmlElement(name="domTerminalCd")
    private String domTerminalCd;
    @XmlElement(name="intTerminalCd")
    private String intTerminalCd;

    public String getDomTerminalCd() {
        return this.domTerminalCd;
    }

    public String getIntTerminalCd() {
        return this.intTerminalCd;
    }

    public void setDomTerminalCd(String domTerminalCd) {
        this.domTerminalCd = domTerminalCd;
    }

    public void setIntTerminalCd(String intTerminalCd) {
        this.intTerminalCd = intTerminalCd;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Terminal)) {
            return false;
        }
        Terminal other = (Terminal)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$domTerminalCd = this.getDomTerminalCd();
        String other$domTerminalCd = other.getDomTerminalCd();
        if (this$domTerminalCd == null ? other$domTerminalCd != null : !this$domTerminalCd.equals(other$domTerminalCd)) {
            return false;
        }
        String this$intTerminalCd = this.getIntTerminalCd();
        String other$intTerminalCd = other.getIntTerminalCd();
        return !(this$intTerminalCd == null ? other$intTerminalCd != null : !this$intTerminalCd.equals(other$intTerminalCd));
    }

    protected boolean canEqual(Object other) {
        return other instanceof Terminal;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $domTerminalCd = this.getDomTerminalCd();
        result = result * 59 + ($domTerminalCd == null ? 43 : $domTerminalCd.hashCode());
        String $intTerminalCd = this.getIntTerminalCd();
        result = result * 59 + ($intTerminalCd == null ? 43 : $intTerminalCd.hashCode());
        return result;
    }

    public String toString() {
        return "Terminal(domTerminalCd=" + this.getDomTerminalCd() + ", intTerminalCd=" + this.getIntTerminalCd() + ")";
    }
}
