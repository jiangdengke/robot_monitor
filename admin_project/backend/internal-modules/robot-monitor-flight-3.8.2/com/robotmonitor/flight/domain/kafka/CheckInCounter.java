/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain.kafka;

public class CheckInCounter {
    private String checkInCounterCd;
    private String checkInCounterAttr;
    private String chuteClass;
    private String checkInCounterIsland;
    private String estmStartTime;
    private String estmEndTime;
    private String actlStartTime;
    private String actlEndTime;
    private String terminalCd;

    public String getCheckInCounterCd() {
        return this.checkInCounterCd;
    }

    public String getCheckInCounterAttr() {
        return this.checkInCounterAttr;
    }

    public String getChuteClass() {
        return this.chuteClass;
    }

    public String getCheckInCounterIsland() {
        return this.checkInCounterIsland;
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

    public void setCheckInCounterCd(String checkInCounterCd) {
        this.checkInCounterCd = checkInCounterCd;
    }

    public void setCheckInCounterAttr(String checkInCounterAttr) {
        this.checkInCounterAttr = checkInCounterAttr;
    }

    public void setChuteClass(String chuteClass) {
        this.chuteClass = chuteClass;
    }

    public void setCheckInCounterIsland(String checkInCounterIsland) {
        this.checkInCounterIsland = checkInCounterIsland;
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
        if (!(o instanceof CheckInCounter)) {
            return false;
        }
        CheckInCounter other = (CheckInCounter)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$checkInCounterCd = this.getCheckInCounterCd();
        String other$checkInCounterCd = other.getCheckInCounterCd();
        if (this$checkInCounterCd == null ? other$checkInCounterCd != null : !this$checkInCounterCd.equals(other$checkInCounterCd)) {
            return false;
        }
        String this$checkInCounterAttr = this.getCheckInCounterAttr();
        String other$checkInCounterAttr = other.getCheckInCounterAttr();
        if (this$checkInCounterAttr == null ? other$checkInCounterAttr != null : !this$checkInCounterAttr.equals(other$checkInCounterAttr)) {
            return false;
        }
        String this$chuteClass = this.getChuteClass();
        String other$chuteClass = other.getChuteClass();
        if (this$chuteClass == null ? other$chuteClass != null : !this$chuteClass.equals(other$chuteClass)) {
            return false;
        }
        String this$checkInCounterIsland = this.getCheckInCounterIsland();
        String other$checkInCounterIsland = other.getCheckInCounterIsland();
        if (this$checkInCounterIsland == null ? other$checkInCounterIsland != null : !this$checkInCounterIsland.equals(other$checkInCounterIsland)) {
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
        return other instanceof CheckInCounter;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $checkInCounterCd = this.getCheckInCounterCd();
        result = result * 59 + ($checkInCounterCd == null ? 43 : $checkInCounterCd.hashCode());
        String $checkInCounterAttr = this.getCheckInCounterAttr();
        result = result * 59 + ($checkInCounterAttr == null ? 43 : $checkInCounterAttr.hashCode());
        String $chuteClass = this.getChuteClass();
        result = result * 59 + ($chuteClass == null ? 43 : $chuteClass.hashCode());
        String $checkInCounterIsland = this.getCheckInCounterIsland();
        result = result * 59 + ($checkInCounterIsland == null ? 43 : $checkInCounterIsland.hashCode());
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
        return "CheckInCounter(checkInCounterCd=" + this.getCheckInCounterCd() + ", checkInCounterAttr=" + this.getCheckInCounterAttr() + ", chuteClass=" + this.getChuteClass() + ", checkInCounterIsland=" + this.getCheckInCounterIsland() + ", estmStartTime=" + this.getEstmStartTime() + ", estmEndTime=" + this.getEstmEndTime() + ", actlStartTime=" + this.getActlStartTime() + ", actlEndTime=" + this.getActlEndTime() + ", terminalCd=" + this.getTerminalCd() + ")";
    }
}
