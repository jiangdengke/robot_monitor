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
public class Station {
    @XmlElement(name="stationSequence")
    private String stationSequence;
    @XmlElement(name="stationIataCd")
    private String stationIataCd;
    @XmlElement(name="stationCn")
    private String stationCn;
    @XmlElement(name="alternateFlag")
    private String alternateFlag;
    @XmlElement(name="scheTakeOffTime")
    private String scheTakeOffTime;
    @XmlElement(name="estmTakeOffTime")
    private String estmTakeOffTime;
    @XmlElement(name="actlTakeOffTime")
    private String actlTakeOffTime;
    @XmlElement(name="scheLandInTime")
    private String scheLandInTime;
    @XmlElement(name="estmLandInTime")
    private String estmLandInTime;
    @XmlElement(name="actlLandInTime")
    private String actlLandInTime;

    public String getStationSequence() {
        return this.stationSequence;
    }

    public String getStationIataCd() {
        return this.stationIataCd;
    }

    public String getStationCn() {
        return this.stationCn;
    }

    public String getAlternateFlag() {
        return this.alternateFlag;
    }

    public String getScheTakeOffTime() {
        return this.scheTakeOffTime;
    }

    public String getEstmTakeOffTime() {
        return this.estmTakeOffTime;
    }

    public String getActlTakeOffTime() {
        return this.actlTakeOffTime;
    }

    public String getScheLandInTime() {
        return this.scheLandInTime;
    }

    public String getEstmLandInTime() {
        return this.estmLandInTime;
    }

    public String getActlLandInTime() {
        return this.actlLandInTime;
    }

    public void setStationSequence(String stationSequence) {
        this.stationSequence = stationSequence;
    }

    public void setStationIataCd(String stationIataCd) {
        this.stationIataCd = stationIataCd;
    }

    public void setStationCn(String stationCn) {
        this.stationCn = stationCn;
    }

    public void setAlternateFlag(String alternateFlag) {
        this.alternateFlag = alternateFlag;
    }

    public void setScheTakeOffTime(String scheTakeOffTime) {
        this.scheTakeOffTime = scheTakeOffTime;
    }

    public void setEstmTakeOffTime(String estmTakeOffTime) {
        this.estmTakeOffTime = estmTakeOffTime;
    }

    public void setActlTakeOffTime(String actlTakeOffTime) {
        this.actlTakeOffTime = actlTakeOffTime;
    }

    public void setScheLandInTime(String scheLandInTime) {
        this.scheLandInTime = scheLandInTime;
    }

    public void setEstmLandInTime(String estmLandInTime) {
        this.estmLandInTime = estmLandInTime;
    }

    public void setActlLandInTime(String actlLandInTime) {
        this.actlLandInTime = actlLandInTime;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Station)) {
            return false;
        }
        Station other = (Station)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$stationSequence = this.getStationSequence();
        String other$stationSequence = other.getStationSequence();
        if (this$stationSequence == null ? other$stationSequence != null : !this$stationSequence.equals(other$stationSequence)) {
            return false;
        }
        String this$stationIataCd = this.getStationIataCd();
        String other$stationIataCd = other.getStationIataCd();
        if (this$stationIataCd == null ? other$stationIataCd != null : !this$stationIataCd.equals(other$stationIataCd)) {
            return false;
        }
        String this$stationCn = this.getStationCn();
        String other$stationCn = other.getStationCn();
        if (this$stationCn == null ? other$stationCn != null : !this$stationCn.equals(other$stationCn)) {
            return false;
        }
        String this$alternateFlag = this.getAlternateFlag();
        String other$alternateFlag = other.getAlternateFlag();
        if (this$alternateFlag == null ? other$alternateFlag != null : !this$alternateFlag.equals(other$alternateFlag)) {
            return false;
        }
        String this$scheTakeOffTime = this.getScheTakeOffTime();
        String other$scheTakeOffTime = other.getScheTakeOffTime();
        if (this$scheTakeOffTime == null ? other$scheTakeOffTime != null : !this$scheTakeOffTime.equals(other$scheTakeOffTime)) {
            return false;
        }
        String this$estmTakeOffTime = this.getEstmTakeOffTime();
        String other$estmTakeOffTime = other.getEstmTakeOffTime();
        if (this$estmTakeOffTime == null ? other$estmTakeOffTime != null : !this$estmTakeOffTime.equals(other$estmTakeOffTime)) {
            return false;
        }
        String this$actlTakeOffTime = this.getActlTakeOffTime();
        String other$actlTakeOffTime = other.getActlTakeOffTime();
        if (this$actlTakeOffTime == null ? other$actlTakeOffTime != null : !this$actlTakeOffTime.equals(other$actlTakeOffTime)) {
            return false;
        }
        String this$scheLandInTime = this.getScheLandInTime();
        String other$scheLandInTime = other.getScheLandInTime();
        if (this$scheLandInTime == null ? other$scheLandInTime != null : !this$scheLandInTime.equals(other$scheLandInTime)) {
            return false;
        }
        String this$estmLandInTime = this.getEstmLandInTime();
        String other$estmLandInTime = other.getEstmLandInTime();
        if (this$estmLandInTime == null ? other$estmLandInTime != null : !this$estmLandInTime.equals(other$estmLandInTime)) {
            return false;
        }
        String this$actlLandInTime = this.getActlLandInTime();
        String other$actlLandInTime = other.getActlLandInTime();
        return !(this$actlLandInTime == null ? other$actlLandInTime != null : !this$actlLandInTime.equals(other$actlLandInTime));
    }

    protected boolean canEqual(Object other) {
        return other instanceof Station;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $stationSequence = this.getStationSequence();
        result = result * 59 + ($stationSequence == null ? 43 : $stationSequence.hashCode());
        String $stationIataCd = this.getStationIataCd();
        result = result * 59 + ($stationIataCd == null ? 43 : $stationIataCd.hashCode());
        String $stationCn = this.getStationCn();
        result = result * 59 + ($stationCn == null ? 43 : $stationCn.hashCode());
        String $alternateFlag = this.getAlternateFlag();
        result = result * 59 + ($alternateFlag == null ? 43 : $alternateFlag.hashCode());
        String $scheTakeOffTime = this.getScheTakeOffTime();
        result = result * 59 + ($scheTakeOffTime == null ? 43 : $scheTakeOffTime.hashCode());
        String $estmTakeOffTime = this.getEstmTakeOffTime();
        result = result * 59 + ($estmTakeOffTime == null ? 43 : $estmTakeOffTime.hashCode());
        String $actlTakeOffTime = this.getActlTakeOffTime();
        result = result * 59 + ($actlTakeOffTime == null ? 43 : $actlTakeOffTime.hashCode());
        String $scheLandInTime = this.getScheLandInTime();
        result = result * 59 + ($scheLandInTime == null ? 43 : $scheLandInTime.hashCode());
        String $estmLandInTime = this.getEstmLandInTime();
        result = result * 59 + ($estmLandInTime == null ? 43 : $estmLandInTime.hashCode());
        String $actlLandInTime = this.getActlLandInTime();
        result = result * 59 + ($actlLandInTime == null ? 43 : $actlLandInTime.hashCode());
        return result;
    }

    public String toString() {
        return "Station(stationSequence=" + this.getStationSequence() + ", stationIataCd=" + this.getStationIataCd() + ", stationCn=" + this.getStationCn() + ", alternateFlag=" + this.getAlternateFlag() + ", scheTakeOffTime=" + this.getScheTakeOffTime() + ", estmTakeOffTime=" + this.getEstmTakeOffTime() + ", actlTakeOffTime=" + this.getActlTakeOffTime() + ", scheLandInTime=" + this.getScheLandInTime() + ", estmLandInTime=" + this.getEstmLandInTime() + ", actlLandInTime=" + this.getActlLandInTime() + ")";
    }
}
