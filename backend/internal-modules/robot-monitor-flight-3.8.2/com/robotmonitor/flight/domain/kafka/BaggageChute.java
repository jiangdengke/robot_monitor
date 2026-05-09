/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain.kafka;

public class BaggageChute {
    private String chuteSequence;
    private String chuteCd;
    private String chuteClass;
    private String chuteAttr;
    private String estmStartTime;
    private String estmEndTime;
    private String actlStartTime;
    private String actlEndTime;
    private String terminalCd;

    public String getChuteSequence() {
        return this.chuteSequence;
    }

    public String getChuteCd() {
        return this.chuteCd;
    }

    public String getChuteClass() {
        return this.chuteClass;
    }

    public String getChuteAttr() {
        return this.chuteAttr;
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

    public void setChuteSequence(String chuteSequence) {
        this.chuteSequence = chuteSequence;
    }

    public void setChuteCd(String chuteCd) {
        this.chuteCd = chuteCd;
    }

    public void setChuteClass(String chuteClass) {
        this.chuteClass = chuteClass;
    }

    public void setChuteAttr(String chuteAttr) {
        this.chuteAttr = chuteAttr;
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
        if (!(o instanceof BaggageChute)) {
            return false;
        }
        BaggageChute other = (BaggageChute)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$chuteSequence = this.getChuteSequence();
        String other$chuteSequence = other.getChuteSequence();
        if (this$chuteSequence == null ? other$chuteSequence != null : !this$chuteSequence.equals(other$chuteSequence)) {
            return false;
        }
        String this$chuteCd = this.getChuteCd();
        String other$chuteCd = other.getChuteCd();
        if (this$chuteCd == null ? other$chuteCd != null : !this$chuteCd.equals(other$chuteCd)) {
            return false;
        }
        String this$chuteClass = this.getChuteClass();
        String other$chuteClass = other.getChuteClass();
        if (this$chuteClass == null ? other$chuteClass != null : !this$chuteClass.equals(other$chuteClass)) {
            return false;
        }
        String this$chuteAttr = this.getChuteAttr();
        String other$chuteAttr = other.getChuteAttr();
        if (this$chuteAttr == null ? other$chuteAttr != null : !this$chuteAttr.equals(other$chuteAttr)) {
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
        return other instanceof BaggageChute;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $chuteSequence = this.getChuteSequence();
        result = result * 59 + ($chuteSequence == null ? 43 : $chuteSequence.hashCode());
        String $chuteCd = this.getChuteCd();
        result = result * 59 + ($chuteCd == null ? 43 : $chuteCd.hashCode());
        String $chuteClass = this.getChuteClass();
        result = result * 59 + ($chuteClass == null ? 43 : $chuteClass.hashCode());
        String $chuteAttr = this.getChuteAttr();
        result = result * 59 + ($chuteAttr == null ? 43 : $chuteAttr.hashCode());
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
        return "BaggageChute(chuteSequence=" + this.getChuteSequence() + ", chuteCd=" + this.getChuteCd() + ", chuteClass=" + this.getChuteClass() + ", chuteAttr=" + this.getChuteAttr() + ", estmStartTime=" + this.getEstmStartTime() + ", estmEndTime=" + this.getEstmEndTime() + ", actlStartTime=" + this.getActlStartTime() + ", actlEndTime=" + this.getActlEndTime() + ", terminalCd=" + this.getTerminalCd() + ")";
    }
}
