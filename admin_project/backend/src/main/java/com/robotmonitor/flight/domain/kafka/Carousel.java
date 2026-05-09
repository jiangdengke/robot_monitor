/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain.kafka;

public class Carousel {
    private String carouselSequence;
    private String carouselCd;
    private String carouselClass;
    private String carouselAttr;
    private String estmStartTime;
    private String estmEndTime;
    private String actlStartTime;
    private String actlEndTime;
    private String firstLuggageTime;
    private String lastLuggageTime;
    private String terminalCd;
    private String exitNo;

    public String getCarouselSequence() {
        return this.carouselSequence;
    }

    public String getCarouselCd() {
        return this.carouselCd;
    }

    public String getCarouselClass() {
        return this.carouselClass;
    }

    public String getCarouselAttr() {
        return this.carouselAttr;
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

    public String getFirstLuggageTime() {
        return this.firstLuggageTime;
    }

    public String getLastLuggageTime() {
        return this.lastLuggageTime;
    }

    public String getTerminalCd() {
        return this.terminalCd;
    }

    public String getExitNo() {
        return this.exitNo;
    }

    public void setCarouselSequence(String carouselSequence) {
        this.carouselSequence = carouselSequence;
    }

    public void setCarouselCd(String carouselCd) {
        this.carouselCd = carouselCd;
    }

    public void setCarouselClass(String carouselClass) {
        this.carouselClass = carouselClass;
    }

    public void setCarouselAttr(String carouselAttr) {
        this.carouselAttr = carouselAttr;
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

    public void setFirstLuggageTime(String firstLuggageTime) {
        this.firstLuggageTime = firstLuggageTime;
    }

    public void setLastLuggageTime(String lastLuggageTime) {
        this.lastLuggageTime = lastLuggageTime;
    }

    public void setTerminalCd(String terminalCd) {
        this.terminalCd = terminalCd;
    }

    public void setExitNo(String exitNo) {
        this.exitNo = exitNo;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Carousel)) {
            return false;
        }
        Carousel other = (Carousel)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$carouselSequence = this.getCarouselSequence();
        String other$carouselSequence = other.getCarouselSequence();
        if (this$carouselSequence == null ? other$carouselSequence != null : !this$carouselSequence.equals(other$carouselSequence)) {
            return false;
        }
        String this$carouselCd = this.getCarouselCd();
        String other$carouselCd = other.getCarouselCd();
        if (this$carouselCd == null ? other$carouselCd != null : !this$carouselCd.equals(other$carouselCd)) {
            return false;
        }
        String this$carouselClass = this.getCarouselClass();
        String other$carouselClass = other.getCarouselClass();
        if (this$carouselClass == null ? other$carouselClass != null : !this$carouselClass.equals(other$carouselClass)) {
            return false;
        }
        String this$carouselAttr = this.getCarouselAttr();
        String other$carouselAttr = other.getCarouselAttr();
        if (this$carouselAttr == null ? other$carouselAttr != null : !this$carouselAttr.equals(other$carouselAttr)) {
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
        String this$firstLuggageTime = this.getFirstLuggageTime();
        String other$firstLuggageTime = other.getFirstLuggageTime();
        if (this$firstLuggageTime == null ? other$firstLuggageTime != null : !this$firstLuggageTime.equals(other$firstLuggageTime)) {
            return false;
        }
        String this$lastLuggageTime = this.getLastLuggageTime();
        String other$lastLuggageTime = other.getLastLuggageTime();
        if (this$lastLuggageTime == null ? other$lastLuggageTime != null : !this$lastLuggageTime.equals(other$lastLuggageTime)) {
            return false;
        }
        String this$terminalCd = this.getTerminalCd();
        String other$terminalCd = other.getTerminalCd();
        if (this$terminalCd == null ? other$terminalCd != null : !this$terminalCd.equals(other$terminalCd)) {
            return false;
        }
        String this$exitNo = this.getExitNo();
        String other$exitNo = other.getExitNo();
        return !(this$exitNo == null ? other$exitNo != null : !this$exitNo.equals(other$exitNo));
    }

    protected boolean canEqual(Object other) {
        return other instanceof Carousel;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $carouselSequence = this.getCarouselSequence();
        result = result * 59 + ($carouselSequence == null ? 43 : $carouselSequence.hashCode());
        String $carouselCd = this.getCarouselCd();
        result = result * 59 + ($carouselCd == null ? 43 : $carouselCd.hashCode());
        String $carouselClass = this.getCarouselClass();
        result = result * 59 + ($carouselClass == null ? 43 : $carouselClass.hashCode());
        String $carouselAttr = this.getCarouselAttr();
        result = result * 59 + ($carouselAttr == null ? 43 : $carouselAttr.hashCode());
        String $estmStartTime = this.getEstmStartTime();
        result = result * 59 + ($estmStartTime == null ? 43 : $estmStartTime.hashCode());
        String $estmEndTime = this.getEstmEndTime();
        result = result * 59 + ($estmEndTime == null ? 43 : $estmEndTime.hashCode());
        String $actlStartTime = this.getActlStartTime();
        result = result * 59 + ($actlStartTime == null ? 43 : $actlStartTime.hashCode());
        String $actlEndTime = this.getActlEndTime();
        result = result * 59 + ($actlEndTime == null ? 43 : $actlEndTime.hashCode());
        String $firstLuggageTime = this.getFirstLuggageTime();
        result = result * 59 + ($firstLuggageTime == null ? 43 : $firstLuggageTime.hashCode());
        String $lastLuggageTime = this.getLastLuggageTime();
        result = result * 59 + ($lastLuggageTime == null ? 43 : $lastLuggageTime.hashCode());
        String $terminalCd = this.getTerminalCd();
        result = result * 59 + ($terminalCd == null ? 43 : $terminalCd.hashCode());
        String $exitNo = this.getExitNo();
        result = result * 59 + ($exitNo == null ? 43 : $exitNo.hashCode());
        return result;
    }

    public String toString() {
        return "Carousel(carouselSequence=" + this.getCarouselSequence() + ", carouselCd=" + this.getCarouselCd() + ", carouselClass=" + this.getCarouselClass() + ", carouselAttr=" + this.getCarouselAttr() + ", estmStartTime=" + this.getEstmStartTime() + ", estmEndTime=" + this.getEstmEndTime() + ", actlStartTime=" + this.getActlStartTime() + ", actlEndTime=" + this.getActlEndTime() + ", firstLuggageTime=" + this.getFirstLuggageTime() + ", lastLuggageTime=" + this.getLastLuggageTime() + ", terminalCd=" + this.getTerminalCd() + ", exitNo=" + this.getExitNo() + ")";
    }
}
