/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain.kafka;

public class BoardingGate {
    private String gateSequence;
    private String gateCd;
    private String gateAttr;
    private String estmStartTime;
    private String estmEndTime;
    private String actlStartTime;
    private String actlEndTime;
    private String terminalCd;

    public String getGateSequence() {
        return this.gateSequence;
    }

    public String getGateCd() {
        return this.gateCd;
    }

    public String getGateAttr() {
        return this.gateAttr;
    }

    public String getEstmStartTime() {
        return this.estmStartTime;
    }

    public String getEstmEndTime() {
        return this.estmEndTime;
    }

    public String getActlStartTime() {
        return this.actlStartTime;
    }

    public String getActlEndTime() {
        return this.actlEndTime;
    }

    public String getTerminalCd() {
        return this.terminalCd;
    }

    public void setGateSequence(String gateSequence) {
        this.gateSequence = gateSequence;
    }

    public void setGateCd(String gateCd) {
        this.gateCd = gateCd;
    }

    public void setGateAttr(String gateAttr) {
        this.gateAttr = gateAttr;
    }

    public void setEstmStartTime(String estmStartTime) {
        this.estmStartTime = estmStartTime;
    }

    public void setEstmEndTime(String estmEndTime) {
        this.estmEndTime = estmEndTime;
    }

    public void setActlStartTime(String actlStartTime) {
        this.actlStartTime = actlStartTime;
    }

    public void setActlEndTime(String actlEndTime) {
        this.actlEndTime = actlEndTime;
    }

    public void setTerminalCd(String terminalCd) {
        this.terminalCd = terminalCd;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BoardingGate)) {
            return false;
        }
        BoardingGate other = (BoardingGate)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$gateSequence = this.getGateSequence();
        String other$gateSequence = other.getGateSequence();
        if (this$gateSequence == null ? other$gateSequence != null : !this$gateSequence.equals(other$gateSequence)) {
            return false;
        }
        String this$gateCd = this.getGateCd();
        String other$gateCd = other.getGateCd();
        if (this$gateCd == null ? other$gateCd != null : !this$gateCd.equals(other$gateCd)) {
            return false;
        }
        String this$gateAttr = this.getGateAttr();
        String other$gateAttr = other.getGateAttr();
        if (this$gateAttr == null ? other$gateAttr != null : !this$gateAttr.equals(other$gateAttr)) {
            return false;
        }
        String this$estmStartTime = this.getEstmStartTime();
        String other$estmStartTime = other.getEstmStartTime();
        if (this$estmStartTime == null ? other$estmStartTime != null : !this$estmStartTime.equals(other$estmStartTime)) {
            return false;
        }
        String this$estmEndTime = this.getEstmEndTime();
        String other$estmEndTime = other.getEstmEndTime();
        if (this$estmEndTime == null ? other$estmEndTime != null : !this$estmEndTime.equals(other$estmEndTime)) {
            return false;
        }
        String this$actlStartTime = this.getActlStartTime();
        String other$actlStartTime = other.getActlStartTime();
        if (this$actlStartTime == null ? other$actlStartTime != null : !this$actlStartTime.equals(other$actlStartTime)) {
            return false;
        }
        String this$actlEndTime = this.getActlEndTime();
        String other$actlEndTime = other.getActlEndTime();
        if (this$actlEndTime == null ? other$actlEndTime != null : !this$actlEndTime.equals(other$actlEndTime)) {
            return false;
        }
        String this$terminalCd = this.getTerminalCd();
        String other$terminalCd = other.getTerminalCd();
        return !(this$terminalCd == null ? other$terminalCd != null : !this$terminalCd.equals(other$terminalCd));
    }

    protected boolean canEqual(Object other) {
        return other instanceof BoardingGate;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $gateSequence = this.getGateSequence();
        result = result * 59 + ($gateSequence == null ? 43 : $gateSequence.hashCode());
        String $gateCd = this.getGateCd();
        result = result * 59 + ($gateCd == null ? 43 : $gateCd.hashCode());
        String $gateAttr = this.getGateAttr();
        result = result * 59 + ($gateAttr == null ? 43 : $gateAttr.hashCode());
        String $estmStartTime = this.getEstmStartTime();
        result = result * 59 + ($estmStartTime == null ? 43 : $estmStartTime.hashCode());
        String $estmEndTime = this.getEstmEndTime();
        result = result * 59 + ($estmEndTime == null ? 43 : $estmEndTime.hashCode());
        String $actlStartTime = this.getActlStartTime();
        result = result * 59 + ($actlStartTime == null ? 43 : $actlStartTime.hashCode());
        String $actlEndTime = this.getActlEndTime();
        result = result * 59 + ($actlEndTime == null ? 43 : $actlEndTime.hashCode());
        String $terminalCd = this.getTerminalCd();
        result = result * 59 + ($terminalCd == null ? 43 : $terminalCd.hashCode());
        return result;
    }

    public String toString() {
        return "BoardingGate(gateSequence=" + this.getGateSequence() + ", gateCd=" + this.getGateCd() + ", gateAttr=" + this.getGateAttr() + ", estmStartTime=" + this.getEstmStartTime() + ", estmEndTime=" + this.getEstmEndTime() + ", actlStartTime=" + this.getActlStartTime() + ", actlEndTime=" + this.getActlEndTime() + ", terminalCd=" + this.getTerminalCd() + ")";
    }
}
