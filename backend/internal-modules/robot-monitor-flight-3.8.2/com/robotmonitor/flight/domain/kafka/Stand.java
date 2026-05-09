/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain.kafka;

public class Stand {
    private String standSequence;
    private String standCd;
    private String estmStartTime;
    private String estmEndTime;
    private String actlStartTime;
    private String actlEndTime;
    private String currentStandFlag;
    private String terminalCd;

    public String getStandSequence() {
        return this.standSequence;
    }

    public String getStandCd() {
        return this.standCd;
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

    public String getCurrentStandFlag() {
        return this.currentStandFlag;
    }

    public String getTerminalCd() {
        return this.terminalCd;
    }

    public void setStandSequence(String standSequence) {
        this.standSequence = standSequence;
    }

    public void setStandCd(String standCd) {
        this.standCd = standCd;
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

    public void setCurrentStandFlag(String currentStandFlag) {
        this.currentStandFlag = currentStandFlag;
    }

    public void setTerminalCd(String terminalCd) {
        this.terminalCd = terminalCd;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Stand)) {
            return false;
        }
        Stand other = (Stand)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$standSequence = this.getStandSequence();
        String other$standSequence = other.getStandSequence();
        if (this$standSequence == null ? other$standSequence != null : !this$standSequence.equals(other$standSequence)) {
            return false;
        }
        String this$standCd = this.getStandCd();
        String other$standCd = other.getStandCd();
        if (this$standCd == null ? other$standCd != null : !this$standCd.equals(other$standCd)) {
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
        String this$currentStandFlag = this.getCurrentStandFlag();
        String other$currentStandFlag = other.getCurrentStandFlag();
        if (this$currentStandFlag == null ? other$currentStandFlag != null : !this$currentStandFlag.equals(other$currentStandFlag)) {
            return false;
        }
        String this$terminalCd = this.getTerminalCd();
        String other$terminalCd = other.getTerminalCd();
        return !(this$terminalCd == null ? other$terminalCd != null : !this$terminalCd.equals(other$terminalCd));
    }

    protected boolean canEqual(Object other) {
        return other instanceof Stand;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $standSequence = this.getStandSequence();
        result = result * 59 + ($standSequence == null ? 43 : $standSequence.hashCode());
        String $standCd = this.getStandCd();
        result = result * 59 + ($standCd == null ? 43 : $standCd.hashCode());
        String $estmStartTime = this.getEstmStartTime();
        result = result * 59 + ($estmStartTime == null ? 43 : $estmStartTime.hashCode());
        String $estmEndTime = this.getEstmEndTime();
        result = result * 59 + ($estmEndTime == null ? 43 : $estmEndTime.hashCode());
        String $actlStartTime = this.getActlStartTime();
        result = result * 59 + ($actlStartTime == null ? 43 : $actlStartTime.hashCode());
        String $actlEndTime = this.getActlEndTime();
        result = result * 59 + ($actlEndTime == null ? 43 : $actlEndTime.hashCode());
        String $currentStandFlag = this.getCurrentStandFlag();
        result = result * 59 + ($currentStandFlag == null ? 43 : $currentStandFlag.hashCode());
        String $terminalCd = this.getTerminalCd();
        result = result * 59 + ($terminalCd == null ? 43 : $terminalCd.hashCode());
        return result;
    }

    public String toString() {
        return "Stand(standSequence=" + this.getStandSequence() + ", standCd=" + this.getStandCd() + ", estmStartTime=" + this.getEstmStartTime() + ", estmEndTime=" + this.getEstmEndTime() + ", actlStartTime=" + this.getActlStartTime() + ", actlEndTime=" + this.getActlEndTime() + ", currentStandFlag=" + this.getCurrentStandFlag() + ", terminalCd=" + this.getTerminalCd() + ")";
    }
}
