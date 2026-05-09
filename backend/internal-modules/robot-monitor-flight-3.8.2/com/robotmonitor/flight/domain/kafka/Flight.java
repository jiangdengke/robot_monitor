/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.xml.bind.annotation.XmlElement
 *  javax.xml.bind.annotation.XmlAccessType
 *  javax.xml.bind.annotation.XmlAccessorType
 */
package com.robotmonitor.flight.domain.kafka;

import com.robotmonitor.flight.domain.kafka.BoardingGateGroup;
import com.robotmonitor.flight.domain.kafka.Runway;
import com.robotmonitor.flight.domain.kafka.StationGroup;
import com.robotmonitor.flight.domain.kafka.Terminal;
import jakarta.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(value=XmlAccessType.FIELD)
public class Flight {
    @XmlElement(name="flightId")
    private String flightId;
    @XmlElement(name="associateflightId")
    private String associateflightId;
    @XmlElement(name="associateReturnId")
    private String associateReturnId;
    @XmlElement(name="fmsId")
    private String fmsId;
    @XmlElement(name="ffid")
    private String ffid;
    @XmlElement(name="airlineIataCd")
    private String airlineIataCd;
    @XmlElement(name="airlineIcaoCd")
    private String airlineIcaoCd;
    @XmlElement(name="flightNo")
    private String flightNo;
    @XmlElement(name="flightSuffix")
    private String flightSuffix;
    @XmlElement(name="scheExecDate")
    private String scheExecDate;
    @XmlElement(name="flightScheBatchDate")
    private String flightScheBatchDate;
    @XmlElement(name="ioAttr")
    private String ioAttr;
    @XmlElement(name="flightTask")
    private String flightTask;
    @XmlElement(name="serviceType")
    private String serviceType;
    @XmlElement(name="flightAttr")
    private String flightAttr;
    @XmlElement(name="count")
    private String count;
    @XmlElement(name="agency")
    private String agency;
    @XmlElement(name="seatLayout")
    private String seatLayout;
    @XmlElement(name="cargoFlt")
    private String cargoFlt;
    @XmlElement(name="craftType")
    private String craftType;
    @XmlElement(name="craftNo")
    private String craftNo;
    @XmlElement(name="latestOffStatus")
    private String latestOffStatus;
    @XmlElement(name="latestOnStatus")
    private String latestOnStatus;
    @XmlElement(name="domFlightState")
    private String domFlightState;
    @XmlElement(name="intFlightState")
    private String intFlightState;
    @XmlElement(name="domFlightAbstate")
    private String domFlightAbstate;
    @XmlElement(name="intFlightAbstate")
    private String intFlightAbstate;
    @XmlElement(name="domFlightAbstateReason")
    private String domFlightAbstateReason;
    @XmlElement(name="intFlightAbstateReason")
    private String intFlightAbstateReason;
    @XmlElement(name="domInnerFlightAbstateReason")
    private String domInnerFlightAbstateReason;
    @XmlElement(name="intInnerFlightAbstateReason")
    private String intInnerFlightAbstateReason;
    @XmlElement(name="domBoardingStartTime")
    private String domBoardingStartTime;
    @XmlElement(name="intBoardingStartTime")
    private String intBoardingStartTime;
    @XmlElement(name="domPastStationBoardingTime")
    private String domPastStationBoardingTime;
    @XmlElement(name="intPastStationBoardingTime")
    private String intPastStationBoardingTime;
    @XmlElement(name="domLastCallTime")
    private String domLastCallTime;
    @XmlElement(name="intLastCallTime")
    private String intLastCallTime;
    @XmlElement(name="domBoardingCloseTime")
    private String domBoardingCloseTime;
    @XmlElement(name="intBoardingCloseTime")
    private String intBoardingCloseTime;
    @XmlElement(name="domEstmStartCheckInTime")
    private String domEstmStartCheckInTime;
    @XmlElement(name="domEstmEndCheckInTime")
    private String domEstmEndCheckInTime;
    @XmlElement(name="domStartCheckInTime")
    private String domStartCheckInTime;
    @XmlElement(name="domEndCheckInTime")
    private String domEndCheckInTime;
    @XmlElement(name="intEstmStartCheckInTime")
    private String intEstmStartCheckInTime;
    @XmlElement(name="intEstmEndCheckInTime")
    private String intEstmEndCheckInTime;
    @XmlElement(name="intStartCheckInTime")
    private String intStartCheckInTime;
    @XmlElement(name="intEndCheckInTime")
    private String intEndCheckInTime;
    @XmlElement(name="vip")
    private String vip;
    @XmlElement(name="cancelTime")
    private String cancelTime;
    @XmlElement(name="alternateGroup")
    private String alternateGroup;
    @XmlElement(name="sharingGroup")
    private String sharingGroup;
    @XmlElement(name="stationGroup")
    private StationGroup stationGroup;
    @XmlElement(name="terminal")
    private Terminal terminal;
    @XmlElement(name="runway")
    private Runway runway;
    @XmlElement(name="boardingGateGroup")
    private BoardingGateGroup boardingGateGroup;

    public String getFlightId() {
        return this.flightId;
    }

    public String getAssociateflightId() {
        return this.associateflightId;
    }

    public String getAssociateReturnId() {
        return this.associateReturnId;
    }

    public String getFmsId() {
        return this.fmsId;
    }

    public String getFfid() {
        return this.ffid;
    }

    public String getAirlineIataCd() {
        return this.airlineIataCd;
    }

    public String getAirlineIcaoCd() {
        return this.airlineIcaoCd;
    }

    public String getFlightNo() {
        return this.flightNo;
    }

    public String getFlightSuffix() {
        return this.flightSuffix;
    }

    public String getScheExecDate() {
        return this.scheExecDate;
    }

    public String getFlightScheBatchDate() {
        return this.flightScheBatchDate;
    }

    public String getIoAttr() {
        return this.ioAttr;
    }

    public String getFlightTask() {
        return this.flightTask;
    }

    public String getServiceType() {
        return this.serviceType;
    }

    public String getFlightAttr() {
        return this.flightAttr;
    }

    public String getCount() {
        return this.count;
    }

    public String getAgency() {
        return this.agency;
    }

    public String getSeatLayout() {
        return this.seatLayout;
    }

    public String getCargoFlt() {
        return this.cargoFlt;
    }

    public String getCraftType() {
        return this.craftType;
    }

    public String getCraftNo() {
        return this.craftNo;
    }

    public String getLatestOffStatus() {
        return this.latestOffStatus;
    }

    public String getLatestOnStatus() {
        return this.latestOnStatus;
    }

    public String getDomFlightState() {
        return this.domFlightState;
    }

    public String getIntFlightState() {
        return this.intFlightState;
    }

    public String getDomFlightAbstate() {
        return this.domFlightAbstate;
    }

    public String getIntFlightAbstate() {
        return this.intFlightAbstate;
    }

    public String getDomFlightAbstateReason() {
        return this.domFlightAbstateReason;
    }

    public String getIntFlightAbstateReason() {
        return this.intFlightAbstateReason;
    }

    public String getDomInnerFlightAbstateReason() {
        return this.domInnerFlightAbstateReason;
    }

    public String getIntInnerFlightAbstateReason() {
        return this.intInnerFlightAbstateReason;
    }

    public String getDomBoardingStartTime() {
        return this.domBoardingStartTime;
    }

    public String getIntBoardingStartTime() {
        return this.intBoardingStartTime;
    }

    public String getDomPastStationBoardingTime() {
        return this.domPastStationBoardingTime;
    }

    public String getIntPastStationBoardingTime() {
        return this.intPastStationBoardingTime;
    }

    public String getDomLastCallTime() {
        return this.domLastCallTime;
    }

    public String getIntLastCallTime() {
        return this.intLastCallTime;
    }

    public String getDomBoardingCloseTime() {
        return this.domBoardingCloseTime;
    }

    public String getIntBoardingCloseTime() {
        return this.intBoardingCloseTime;
    }

    public String getDomEstmStartCheckInTime() {
        return this.domEstmStartCheckInTime;
    }

    public String getDomEstmEndCheckInTime() {
        return this.domEstmEndCheckInTime;
    }

    public String getDomStartCheckInTime() {
        return this.domStartCheckInTime;
    }

    public String getDomEndCheckInTime() {
        return this.domEndCheckInTime;
    }

    public String getIntEstmStartCheckInTime() {
        return this.intEstmStartCheckInTime;
    }

    public String getIntEstmEndCheckInTime() {
        return this.intEstmEndCheckInTime;
    }

    public String getIntStartCheckInTime() {
        return this.intStartCheckInTime;
    }

    public String getIntEndCheckInTime() {
        return this.intEndCheckInTime;
    }

    public String getVip() {
        return this.vip;
    }

    public String getCancelTime() {
        return this.cancelTime;
    }

    public String getAlternateGroup() {
        return this.alternateGroup;
    }

    public String getSharingGroup() {
        return this.sharingGroup;
    }

    public StationGroup getStationGroup() {
        return this.stationGroup;
    }

    public Terminal getTerminal() {
        return this.terminal;
    }

    public Runway getRunway() {
        return this.runway;
    }

    public BoardingGateGroup getBoardingGateGroup() {
        return this.boardingGateGroup;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public void setAssociateflightId(String associateflightId) {
        this.associateflightId = associateflightId;
    }

    public void setAssociateReturnId(String associateReturnId) {
        this.associateReturnId = associateReturnId;
    }

    public void setFmsId(String fmsId) {
        this.fmsId = fmsId;
    }

    public void setFfid(String ffid) {
        this.ffid = ffid;
    }

    public void setAirlineIataCd(String airlineIataCd) {
        this.airlineIataCd = airlineIataCd;
    }

    public void setAirlineIcaoCd(String airlineIcaoCd) {
        this.airlineIcaoCd = airlineIcaoCd;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public void setFlightSuffix(String flightSuffix) {
        this.flightSuffix = flightSuffix;
    }

    public void setScheExecDate(String scheExecDate) {
        this.scheExecDate = scheExecDate;
    }

    public void setFlightScheBatchDate(String flightScheBatchDate) {
        this.flightScheBatchDate = flightScheBatchDate;
    }

    public void setIoAttr(String ioAttr) {
        this.ioAttr = ioAttr;
    }

    public void setFlightTask(String flightTask) {
        this.flightTask = flightTask;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public void setFlightAttr(String flightAttr) {
        this.flightAttr = flightAttr;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public void setSeatLayout(String seatLayout) {
        this.seatLayout = seatLayout;
    }

    public void setCargoFlt(String cargoFlt) {
        this.cargoFlt = cargoFlt;
    }

    public void setCraftType(String craftType) {
        this.craftType = craftType;
    }

    public void setCraftNo(String craftNo) {
        this.craftNo = craftNo;
    }

    public void setLatestOffStatus(String latestOffStatus) {
        this.latestOffStatus = latestOffStatus;
    }

    public void setLatestOnStatus(String latestOnStatus) {
        this.latestOnStatus = latestOnStatus;
    }

    public void setDomFlightState(String domFlightState) {
        this.domFlightState = domFlightState;
    }

    public void setIntFlightState(String intFlightState) {
        this.intFlightState = intFlightState;
    }

    public void setDomFlightAbstate(String domFlightAbstate) {
        this.domFlightAbstate = domFlightAbstate;
    }

    public void setIntFlightAbstate(String intFlightAbstate) {
        this.intFlightAbstate = intFlightAbstate;
    }

    public void setDomFlightAbstateReason(String domFlightAbstateReason) {
        this.domFlightAbstateReason = domFlightAbstateReason;
    }

    public void setIntFlightAbstateReason(String intFlightAbstateReason) {
        this.intFlightAbstateReason = intFlightAbstateReason;
    }

    public void setDomInnerFlightAbstateReason(String domInnerFlightAbstateReason) {
        this.domInnerFlightAbstateReason = domInnerFlightAbstateReason;
    }

    public void setIntInnerFlightAbstateReason(String intInnerFlightAbstateReason) {
        this.intInnerFlightAbstateReason = intInnerFlightAbstateReason;
    }

    public void setDomBoardingStartTime(String domBoardingStartTime) {
        this.domBoardingStartTime = domBoardingStartTime;
    }

    public void setIntBoardingStartTime(String intBoardingStartTime) {
        this.intBoardingStartTime = intBoardingStartTime;
    }

    public void setDomPastStationBoardingTime(String domPastStationBoardingTime) {
        this.domPastStationBoardingTime = domPastStationBoardingTime;
    }

    public void setIntPastStationBoardingTime(String intPastStationBoardingTime) {
        this.intPastStationBoardingTime = intPastStationBoardingTime;
    }

    public void setDomLastCallTime(String domLastCallTime) {
        this.domLastCallTime = domLastCallTime;
    }

    public void setIntLastCallTime(String intLastCallTime) {
        this.intLastCallTime = intLastCallTime;
    }

    public void setDomBoardingCloseTime(String domBoardingCloseTime) {
        this.domBoardingCloseTime = domBoardingCloseTime;
    }

    public void setIntBoardingCloseTime(String intBoardingCloseTime) {
        this.intBoardingCloseTime = intBoardingCloseTime;
    }

    public void setDomEstmStartCheckInTime(String domEstmStartCheckInTime) {
        this.domEstmStartCheckInTime = domEstmStartCheckInTime;
    }

    public void setDomEstmEndCheckInTime(String domEstmEndCheckInTime) {
        this.domEstmEndCheckInTime = domEstmEndCheckInTime;
    }

    public void setDomStartCheckInTime(String domStartCheckInTime) {
        this.domStartCheckInTime = domStartCheckInTime;
    }

    public void setDomEndCheckInTime(String domEndCheckInTime) {
        this.domEndCheckInTime = domEndCheckInTime;
    }

    public void setIntEstmStartCheckInTime(String intEstmStartCheckInTime) {
        this.intEstmStartCheckInTime = intEstmStartCheckInTime;
    }

    public void setIntEstmEndCheckInTime(String intEstmEndCheckInTime) {
        this.intEstmEndCheckInTime = intEstmEndCheckInTime;
    }

    public void setIntStartCheckInTime(String intStartCheckInTime) {
        this.intStartCheckInTime = intStartCheckInTime;
    }

    public void setIntEndCheckInTime(String intEndCheckInTime) {
        this.intEndCheckInTime = intEndCheckInTime;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public void setAlternateGroup(String alternateGroup) {
        this.alternateGroup = alternateGroup;
    }

    public void setSharingGroup(String sharingGroup) {
        this.sharingGroup = sharingGroup;
    }

    public void setStationGroup(StationGroup stationGroup) {
        this.stationGroup = stationGroup;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public void setRunway(Runway runway) {
        this.runway = runway;
    }

    public void setBoardingGateGroup(BoardingGateGroup boardingGateGroup) {
        this.boardingGateGroup = boardingGateGroup;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Flight)) {
            return false;
        }
        Flight other = (Flight)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$flightId = this.getFlightId();
        String other$flightId = other.getFlightId();
        if (this$flightId == null ? other$flightId != null : !this$flightId.equals(other$flightId)) {
            return false;
        }
        String this$associateflightId = this.getAssociateflightId();
        String other$associateflightId = other.getAssociateflightId();
        if (this$associateflightId == null ? other$associateflightId != null : !this$associateflightId.equals(other$associateflightId)) {
            return false;
        }
        String this$associateReturnId = this.getAssociateReturnId();
        String other$associateReturnId = other.getAssociateReturnId();
        if (this$associateReturnId == null ? other$associateReturnId != null : !this$associateReturnId.equals(other$associateReturnId)) {
            return false;
        }
        String this$fmsId = this.getFmsId();
        String other$fmsId = other.getFmsId();
        if (this$fmsId == null ? other$fmsId != null : !this$fmsId.equals(other$fmsId)) {
            return false;
        }
        String this$ffid = this.getFfid();
        String other$ffid = other.getFfid();
        if (this$ffid == null ? other$ffid != null : !this$ffid.equals(other$ffid)) {
            return false;
        }
        String this$airlineIataCd = this.getAirlineIataCd();
        String other$airlineIataCd = other.getAirlineIataCd();
        if (this$airlineIataCd == null ? other$airlineIataCd != null : !this$airlineIataCd.equals(other$airlineIataCd)) {
            return false;
        }
        String this$airlineIcaoCd = this.getAirlineIcaoCd();
        String other$airlineIcaoCd = other.getAirlineIcaoCd();
        if (this$airlineIcaoCd == null ? other$airlineIcaoCd != null : !this$airlineIcaoCd.equals(other$airlineIcaoCd)) {
            return false;
        }
        String this$flightNo = this.getFlightNo();
        String other$flightNo = other.getFlightNo();
        if (this$flightNo == null ? other$flightNo != null : !this$flightNo.equals(other$flightNo)) {
            return false;
        }
        String this$flightSuffix = this.getFlightSuffix();
        String other$flightSuffix = other.getFlightSuffix();
        if (this$flightSuffix == null ? other$flightSuffix != null : !this$flightSuffix.equals(other$flightSuffix)) {
            return false;
        }
        String this$scheExecDate = this.getScheExecDate();
        String other$scheExecDate = other.getScheExecDate();
        if (this$scheExecDate == null ? other$scheExecDate != null : !this$scheExecDate.equals(other$scheExecDate)) {
            return false;
        }
        String this$flightScheBatchDate = this.getFlightScheBatchDate();
        String other$flightScheBatchDate = other.getFlightScheBatchDate();
        if (this$flightScheBatchDate == null ? other$flightScheBatchDate != null : !this$flightScheBatchDate.equals(other$flightScheBatchDate)) {
            return false;
        }
        String this$ioAttr = this.getIoAttr();
        String other$ioAttr = other.getIoAttr();
        if (this$ioAttr == null ? other$ioAttr != null : !this$ioAttr.equals(other$ioAttr)) {
            return false;
        }
        String this$flightTask = this.getFlightTask();
        String other$flightTask = other.getFlightTask();
        if (this$flightTask == null ? other$flightTask != null : !this$flightTask.equals(other$flightTask)) {
            return false;
        }
        String this$serviceType = this.getServiceType();
        String other$serviceType = other.getServiceType();
        if (this$serviceType == null ? other$serviceType != null : !this$serviceType.equals(other$serviceType)) {
            return false;
        }
        String this$flightAttr = this.getFlightAttr();
        String other$flightAttr = other.getFlightAttr();
        if (this$flightAttr == null ? other$flightAttr != null : !this$flightAttr.equals(other$flightAttr)) {
            return false;
        }
        String this$count = this.getCount();
        String other$count = other.getCount();
        if (this$count == null ? other$count != null : !this$count.equals(other$count)) {
            return false;
        }
        String this$agency = this.getAgency();
        String other$agency = other.getAgency();
        if (this$agency == null ? other$agency != null : !this$agency.equals(other$agency)) {
            return false;
        }
        String this$seatLayout = this.getSeatLayout();
        String other$seatLayout = other.getSeatLayout();
        if (this$seatLayout == null ? other$seatLayout != null : !this$seatLayout.equals(other$seatLayout)) {
            return false;
        }
        String this$cargoFlt = this.getCargoFlt();
        String other$cargoFlt = other.getCargoFlt();
        if (this$cargoFlt == null ? other$cargoFlt != null : !this$cargoFlt.equals(other$cargoFlt)) {
            return false;
        }
        String this$craftType = this.getCraftType();
        String other$craftType = other.getCraftType();
        if (this$craftType == null ? other$craftType != null : !this$craftType.equals(other$craftType)) {
            return false;
        }
        String this$craftNo = this.getCraftNo();
        String other$craftNo = other.getCraftNo();
        if (this$craftNo == null ? other$craftNo != null : !this$craftNo.equals(other$craftNo)) {
            return false;
        }
        String this$latestOffStatus = this.getLatestOffStatus();
        String other$latestOffStatus = other.getLatestOffStatus();
        if (this$latestOffStatus == null ? other$latestOffStatus != null : !this$latestOffStatus.equals(other$latestOffStatus)) {
            return false;
        }
        String this$latestOnStatus = this.getLatestOnStatus();
        String other$latestOnStatus = other.getLatestOnStatus();
        if (this$latestOnStatus == null ? other$latestOnStatus != null : !this$latestOnStatus.equals(other$latestOnStatus)) {
            return false;
        }
        String this$domFlightState = this.getDomFlightState();
        String other$domFlightState = other.getDomFlightState();
        if (this$domFlightState == null ? other$domFlightState != null : !this$domFlightState.equals(other$domFlightState)) {
            return false;
        }
        String this$intFlightState = this.getIntFlightState();
        String other$intFlightState = other.getIntFlightState();
        if (this$intFlightState == null ? other$intFlightState != null : !this$intFlightState.equals(other$intFlightState)) {
            return false;
        }
        String this$domFlightAbstate = this.getDomFlightAbstate();
        String other$domFlightAbstate = other.getDomFlightAbstate();
        if (this$domFlightAbstate == null ? other$domFlightAbstate != null : !this$domFlightAbstate.equals(other$domFlightAbstate)) {
            return false;
        }
        String this$intFlightAbstate = this.getIntFlightAbstate();
        String other$intFlightAbstate = other.getIntFlightAbstate();
        if (this$intFlightAbstate == null ? other$intFlightAbstate != null : !this$intFlightAbstate.equals(other$intFlightAbstate)) {
            return false;
        }
        String this$domFlightAbstateReason = this.getDomFlightAbstateReason();
        String other$domFlightAbstateReason = other.getDomFlightAbstateReason();
        if (this$domFlightAbstateReason == null ? other$domFlightAbstateReason != null : !this$domFlightAbstateReason.equals(other$domFlightAbstateReason)) {
            return false;
        }
        String this$intFlightAbstateReason = this.getIntFlightAbstateReason();
        String other$intFlightAbstateReason = other.getIntFlightAbstateReason();
        if (this$intFlightAbstateReason == null ? other$intFlightAbstateReason != null : !this$intFlightAbstateReason.equals(other$intFlightAbstateReason)) {
            return false;
        }
        String this$domInnerFlightAbstateReason = this.getDomInnerFlightAbstateReason();
        String other$domInnerFlightAbstateReason = other.getDomInnerFlightAbstateReason();
        if (this$domInnerFlightAbstateReason == null ? other$domInnerFlightAbstateReason != null : !this$domInnerFlightAbstateReason.equals(other$domInnerFlightAbstateReason)) {
            return false;
        }
        String this$intInnerFlightAbstateReason = this.getIntInnerFlightAbstateReason();
        String other$intInnerFlightAbstateReason = other.getIntInnerFlightAbstateReason();
        if (this$intInnerFlightAbstateReason == null ? other$intInnerFlightAbstateReason != null : !this$intInnerFlightAbstateReason.equals(other$intInnerFlightAbstateReason)) {
            return false;
        }
        String this$domBoardingStartTime = this.getDomBoardingStartTime();
        String other$domBoardingStartTime = other.getDomBoardingStartTime();
        if (this$domBoardingStartTime == null ? other$domBoardingStartTime != null : !this$domBoardingStartTime.equals(other$domBoardingStartTime)) {
            return false;
        }
        String this$intBoardingStartTime = this.getIntBoardingStartTime();
        String other$intBoardingStartTime = other.getIntBoardingStartTime();
        if (this$intBoardingStartTime == null ? other$intBoardingStartTime != null : !this$intBoardingStartTime.equals(other$intBoardingStartTime)) {
            return false;
        }
        String this$domPastStationBoardingTime = this.getDomPastStationBoardingTime();
        String other$domPastStationBoardingTime = other.getDomPastStationBoardingTime();
        if (this$domPastStationBoardingTime == null ? other$domPastStationBoardingTime != null : !this$domPastStationBoardingTime.equals(other$domPastStationBoardingTime)) {
            return false;
        }
        String this$intPastStationBoardingTime = this.getIntPastStationBoardingTime();
        String other$intPastStationBoardingTime = other.getIntPastStationBoardingTime();
        if (this$intPastStationBoardingTime == null ? other$intPastStationBoardingTime != null : !this$intPastStationBoardingTime.equals(other$intPastStationBoardingTime)) {
            return false;
        }
        String this$domLastCallTime = this.getDomLastCallTime();
        String other$domLastCallTime = other.getDomLastCallTime();
        if (this$domLastCallTime == null ? other$domLastCallTime != null : !this$domLastCallTime.equals(other$domLastCallTime)) {
            return false;
        }
        String this$intLastCallTime = this.getIntLastCallTime();
        String other$intLastCallTime = other.getIntLastCallTime();
        if (this$intLastCallTime == null ? other$intLastCallTime != null : !this$intLastCallTime.equals(other$intLastCallTime)) {
            return false;
        }
        String this$domBoardingCloseTime = this.getDomBoardingCloseTime();
        String other$domBoardingCloseTime = other.getDomBoardingCloseTime();
        if (this$domBoardingCloseTime == null ? other$domBoardingCloseTime != null : !this$domBoardingCloseTime.equals(other$domBoardingCloseTime)) {
            return false;
        }
        String this$intBoardingCloseTime = this.getIntBoardingCloseTime();
        String other$intBoardingCloseTime = other.getIntBoardingCloseTime();
        if (this$intBoardingCloseTime == null ? other$intBoardingCloseTime != null : !this$intBoardingCloseTime.equals(other$intBoardingCloseTime)) {
            return false;
        }
        String this$domEstmStartCheckInTime = this.getDomEstmStartCheckInTime();
        String other$domEstmStartCheckInTime = other.getDomEstmStartCheckInTime();
        if (this$domEstmStartCheckInTime == null ? other$domEstmStartCheckInTime != null : !this$domEstmStartCheckInTime.equals(other$domEstmStartCheckInTime)) {
            return false;
        }
        String this$domEstmEndCheckInTime = this.getDomEstmEndCheckInTime();
        String other$domEstmEndCheckInTime = other.getDomEstmEndCheckInTime();
        if (this$domEstmEndCheckInTime == null ? other$domEstmEndCheckInTime != null : !this$domEstmEndCheckInTime.equals(other$domEstmEndCheckInTime)) {
            return false;
        }
        String this$domStartCheckInTime = this.getDomStartCheckInTime();
        String other$domStartCheckInTime = other.getDomStartCheckInTime();
        if (this$domStartCheckInTime == null ? other$domStartCheckInTime != null : !this$domStartCheckInTime.equals(other$domStartCheckInTime)) {
            return false;
        }
        String this$domEndCheckInTime = this.getDomEndCheckInTime();
        String other$domEndCheckInTime = other.getDomEndCheckInTime();
        if (this$domEndCheckInTime == null ? other$domEndCheckInTime != null : !this$domEndCheckInTime.equals(other$domEndCheckInTime)) {
            return false;
        }
        String this$intEstmStartCheckInTime = this.getIntEstmStartCheckInTime();
        String other$intEstmStartCheckInTime = other.getIntEstmStartCheckInTime();
        if (this$intEstmStartCheckInTime == null ? other$intEstmStartCheckInTime != null : !this$intEstmStartCheckInTime.equals(other$intEstmStartCheckInTime)) {
            return false;
        }
        String this$intEstmEndCheckInTime = this.getIntEstmEndCheckInTime();
        String other$intEstmEndCheckInTime = other.getIntEstmEndCheckInTime();
        if (this$intEstmEndCheckInTime == null ? other$intEstmEndCheckInTime != null : !this$intEstmEndCheckInTime.equals(other$intEstmEndCheckInTime)) {
            return false;
        }
        String this$intStartCheckInTime = this.getIntStartCheckInTime();
        String other$intStartCheckInTime = other.getIntStartCheckInTime();
        if (this$intStartCheckInTime == null ? other$intStartCheckInTime != null : !this$intStartCheckInTime.equals(other$intStartCheckInTime)) {
            return false;
        }
        String this$intEndCheckInTime = this.getIntEndCheckInTime();
        String other$intEndCheckInTime = other.getIntEndCheckInTime();
        if (this$intEndCheckInTime == null ? other$intEndCheckInTime != null : !this$intEndCheckInTime.equals(other$intEndCheckInTime)) {
            return false;
        }
        String this$vip = this.getVip();
        String other$vip = other.getVip();
        if (this$vip == null ? other$vip != null : !this$vip.equals(other$vip)) {
            return false;
        }
        String this$cancelTime = this.getCancelTime();
        String other$cancelTime = other.getCancelTime();
        if (this$cancelTime == null ? other$cancelTime != null : !this$cancelTime.equals(other$cancelTime)) {
            return false;
        }
        String this$alternateGroup = this.getAlternateGroup();
        String other$alternateGroup = other.getAlternateGroup();
        if (this$alternateGroup == null ? other$alternateGroup != null : !this$alternateGroup.equals(other$alternateGroup)) {
            return false;
        }
        String this$sharingGroup = this.getSharingGroup();
        String other$sharingGroup = other.getSharingGroup();
        if (this$sharingGroup == null ? other$sharingGroup != null : !this$sharingGroup.equals(other$sharingGroup)) {
            return false;
        }
        StationGroup this$stationGroup = this.getStationGroup();
        StationGroup other$stationGroup = other.getStationGroup();
        if (this$stationGroup == null ? other$stationGroup != null : !((Object)this$stationGroup).equals(other$stationGroup)) {
            return false;
        }
        Terminal this$terminal = this.getTerminal();
        Terminal other$terminal = other.getTerminal();
        if (this$terminal == null ? other$terminal != null : !((Object)this$terminal).equals(other$terminal)) {
            return false;
        }
        Runway this$runway = this.getRunway();
        Runway other$runway = other.getRunway();
        if (this$runway == null ? other$runway != null : !((Object)this$runway).equals(other$runway)) {
            return false;
        }
        BoardingGateGroup this$boardingGateGroup = this.getBoardingGateGroup();
        BoardingGateGroup other$boardingGateGroup = other.getBoardingGateGroup();
        return !(this$boardingGateGroup == null ? other$boardingGateGroup != null : !((Object)this$boardingGateGroup).equals(other$boardingGateGroup));
    }

    protected boolean canEqual(Object other) {
        return other instanceof Flight;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $flightId = this.getFlightId();
        result = result * 59 + ($flightId == null ? 43 : $flightId.hashCode());
        String $associateflightId = this.getAssociateflightId();
        result = result * 59 + ($associateflightId == null ? 43 : $associateflightId.hashCode());
        String $associateReturnId = this.getAssociateReturnId();
        result = result * 59 + ($associateReturnId == null ? 43 : $associateReturnId.hashCode());
        String $fmsId = this.getFmsId();
        result = result * 59 + ($fmsId == null ? 43 : $fmsId.hashCode());
        String $ffid = this.getFfid();
        result = result * 59 + ($ffid == null ? 43 : $ffid.hashCode());
        String $airlineIataCd = this.getAirlineIataCd();
        result = result * 59 + ($airlineIataCd == null ? 43 : $airlineIataCd.hashCode());
        String $airlineIcaoCd = this.getAirlineIcaoCd();
        result = result * 59 + ($airlineIcaoCd == null ? 43 : $airlineIcaoCd.hashCode());
        String $flightNo = this.getFlightNo();
        result = result * 59 + ($flightNo == null ? 43 : $flightNo.hashCode());
        String $flightSuffix = this.getFlightSuffix();
        result = result * 59 + ($flightSuffix == null ? 43 : $flightSuffix.hashCode());
        String $scheExecDate = this.getScheExecDate();
        result = result * 59 + ($scheExecDate == null ? 43 : $scheExecDate.hashCode());
        String $flightScheBatchDate = this.getFlightScheBatchDate();
        result = result * 59 + ($flightScheBatchDate == null ? 43 : $flightScheBatchDate.hashCode());
        String $ioAttr = this.getIoAttr();
        result = result * 59 + ($ioAttr == null ? 43 : $ioAttr.hashCode());
        String $flightTask = this.getFlightTask();
        result = result * 59 + ($flightTask == null ? 43 : $flightTask.hashCode());
        String $serviceType = this.getServiceType();
        result = result * 59 + ($serviceType == null ? 43 : $serviceType.hashCode());
        String $flightAttr = this.getFlightAttr();
        result = result * 59 + ($flightAttr == null ? 43 : $flightAttr.hashCode());
        String $count = this.getCount();
        result = result * 59 + ($count == null ? 43 : $count.hashCode());
        String $agency = this.getAgency();
        result = result * 59 + ($agency == null ? 43 : $agency.hashCode());
        String $seatLayout = this.getSeatLayout();
        result = result * 59 + ($seatLayout == null ? 43 : $seatLayout.hashCode());
        String $cargoFlt = this.getCargoFlt();
        result = result * 59 + ($cargoFlt == null ? 43 : $cargoFlt.hashCode());
        String $craftType = this.getCraftType();
        result = result * 59 + ($craftType == null ? 43 : $craftType.hashCode());
        String $craftNo = this.getCraftNo();
        result = result * 59 + ($craftNo == null ? 43 : $craftNo.hashCode());
        String $latestOffStatus = this.getLatestOffStatus();
        result = result * 59 + ($latestOffStatus == null ? 43 : $latestOffStatus.hashCode());
        String $latestOnStatus = this.getLatestOnStatus();
        result = result * 59 + ($latestOnStatus == null ? 43 : $latestOnStatus.hashCode());
        String $domFlightState = this.getDomFlightState();
        result = result * 59 + ($domFlightState == null ? 43 : $domFlightState.hashCode());
        String $intFlightState = this.getIntFlightState();
        result = result * 59 + ($intFlightState == null ? 43 : $intFlightState.hashCode());
        String $domFlightAbstate = this.getDomFlightAbstate();
        result = result * 59 + ($domFlightAbstate == null ? 43 : $domFlightAbstate.hashCode());
        String $intFlightAbstate = this.getIntFlightAbstate();
        result = result * 59 + ($intFlightAbstate == null ? 43 : $intFlightAbstate.hashCode());
        String $domFlightAbstateReason = this.getDomFlightAbstateReason();
        result = result * 59 + ($domFlightAbstateReason == null ? 43 : $domFlightAbstateReason.hashCode());
        String $intFlightAbstateReason = this.getIntFlightAbstateReason();
        result = result * 59 + ($intFlightAbstateReason == null ? 43 : $intFlightAbstateReason.hashCode());
        String $domInnerFlightAbstateReason = this.getDomInnerFlightAbstateReason();
        result = result * 59 + ($domInnerFlightAbstateReason == null ? 43 : $domInnerFlightAbstateReason.hashCode());
        String $intInnerFlightAbstateReason = this.getIntInnerFlightAbstateReason();
        result = result * 59 + ($intInnerFlightAbstateReason == null ? 43 : $intInnerFlightAbstateReason.hashCode());
        String $domBoardingStartTime = this.getDomBoardingStartTime();
        result = result * 59 + ($domBoardingStartTime == null ? 43 : $domBoardingStartTime.hashCode());
        String $intBoardingStartTime = this.getIntBoardingStartTime();
        result = result * 59 + ($intBoardingStartTime == null ? 43 : $intBoardingStartTime.hashCode());
        String $domPastStationBoardingTime = this.getDomPastStationBoardingTime();
        result = result * 59 + ($domPastStationBoardingTime == null ? 43 : $domPastStationBoardingTime.hashCode());
        String $intPastStationBoardingTime = this.getIntPastStationBoardingTime();
        result = result * 59 + ($intPastStationBoardingTime == null ? 43 : $intPastStationBoardingTime.hashCode());
        String $domLastCallTime = this.getDomLastCallTime();
        result = result * 59 + ($domLastCallTime == null ? 43 : $domLastCallTime.hashCode());
        String $intLastCallTime = this.getIntLastCallTime();
        result = result * 59 + ($intLastCallTime == null ? 43 : $intLastCallTime.hashCode());
        String $domBoardingCloseTime = this.getDomBoardingCloseTime();
        result = result * 59 + ($domBoardingCloseTime == null ? 43 : $domBoardingCloseTime.hashCode());
        String $intBoardingCloseTime = this.getIntBoardingCloseTime();
        result = result * 59 + ($intBoardingCloseTime == null ? 43 : $intBoardingCloseTime.hashCode());
        String $domEstmStartCheckInTime = this.getDomEstmStartCheckInTime();
        result = result * 59 + ($domEstmStartCheckInTime == null ? 43 : $domEstmStartCheckInTime.hashCode());
        String $domEstmEndCheckInTime = this.getDomEstmEndCheckInTime();
        result = result * 59 + ($domEstmEndCheckInTime == null ? 43 : $domEstmEndCheckInTime.hashCode());
        String $domStartCheckInTime = this.getDomStartCheckInTime();
        result = result * 59 + ($domStartCheckInTime == null ? 43 : $domStartCheckInTime.hashCode());
        String $domEndCheckInTime = this.getDomEndCheckInTime();
        result = result * 59 + ($domEndCheckInTime == null ? 43 : $domEndCheckInTime.hashCode());
        String $intEstmStartCheckInTime = this.getIntEstmStartCheckInTime();
        result = result * 59 + ($intEstmStartCheckInTime == null ? 43 : $intEstmStartCheckInTime.hashCode());
        String $intEstmEndCheckInTime = this.getIntEstmEndCheckInTime();
        result = result * 59 + ($intEstmEndCheckInTime == null ? 43 : $intEstmEndCheckInTime.hashCode());
        String $intStartCheckInTime = this.getIntStartCheckInTime();
        result = result * 59 + ($intStartCheckInTime == null ? 43 : $intStartCheckInTime.hashCode());
        String $intEndCheckInTime = this.getIntEndCheckInTime();
        result = result * 59 + ($intEndCheckInTime == null ? 43 : $intEndCheckInTime.hashCode());
        String $vip = this.getVip();
        result = result * 59 + ($vip == null ? 43 : $vip.hashCode());
        String $cancelTime = this.getCancelTime();
        result = result * 59 + ($cancelTime == null ? 43 : $cancelTime.hashCode());
        String $alternateGroup = this.getAlternateGroup();
        result = result * 59 + ($alternateGroup == null ? 43 : $alternateGroup.hashCode());
        String $sharingGroup = this.getSharingGroup();
        result = result * 59 + ($sharingGroup == null ? 43 : $sharingGroup.hashCode());
        StationGroup $stationGroup = this.getStationGroup();
        result = result * 59 + ($stationGroup == null ? 43 : ((Object)$stationGroup).hashCode());
        Terminal $terminal = this.getTerminal();
        result = result * 59 + ($terminal == null ? 43 : ((Object)$terminal).hashCode());
        Runway $runway = this.getRunway();
        result = result * 59 + ($runway == null ? 43 : ((Object)$runway).hashCode());
        BoardingGateGroup $boardingGateGroup = this.getBoardingGateGroup();
        result = result * 59 + ($boardingGateGroup == null ? 43 : ((Object)$boardingGateGroup).hashCode());
        return result;
    }

    public String toString() {
        return "Flight(flightId=" + this.getFlightId() + ", associateflightId=" + this.getAssociateflightId() + ", associateReturnId=" + this.getAssociateReturnId() + ", fmsId=" + this.getFmsId() + ", ffid=" + this.getFfid() + ", airlineIataCd=" + this.getAirlineIataCd() + ", airlineIcaoCd=" + this.getAirlineIcaoCd() + ", flightNo=" + this.getFlightNo() + ", flightSuffix=" + this.getFlightSuffix() + ", scheExecDate=" + this.getScheExecDate() + ", flightScheBatchDate=" + this.getFlightScheBatchDate() + ", ioAttr=" + this.getIoAttr() + ", flightTask=" + this.getFlightTask() + ", serviceType=" + this.getServiceType() + ", flightAttr=" + this.getFlightAttr() + ", count=" + this.getCount() + ", agency=" + this.getAgency() + ", seatLayout=" + this.getSeatLayout() + ", cargoFlt=" + this.getCargoFlt() + ", craftType=" + this.getCraftType() + ", craftNo=" + this.getCraftNo() + ", latestOffStatus=" + this.getLatestOffStatus() + ", latestOnStatus=" + this.getLatestOnStatus() + ", domFlightState=" + this.getDomFlightState() + ", intFlightState=" + this.getIntFlightState() + ", domFlightAbstate=" + this.getDomFlightAbstate() + ", intFlightAbstate=" + this.getIntFlightAbstate() + ", domFlightAbstateReason=" + this.getDomFlightAbstateReason() + ", intFlightAbstateReason=" + this.getIntFlightAbstateReason() + ", domInnerFlightAbstateReason=" + this.getDomInnerFlightAbstateReason() + ", intInnerFlightAbstateReason=" + this.getIntInnerFlightAbstateReason() + ", domBoardingStartTime=" + this.getDomBoardingStartTime() + ", intBoardingStartTime=" + this.getIntBoardingStartTime() + ", domPastStationBoardingTime=" + this.getDomPastStationBoardingTime() + ", intPastStationBoardingTime=" + this.getIntPastStationBoardingTime() + ", domLastCallTime=" + this.getDomLastCallTime() + ", intLastCallTime=" + this.getIntLastCallTime() + ", domBoardingCloseTime=" + this.getDomBoardingCloseTime() + ", intBoardingCloseTime=" + this.getIntBoardingCloseTime() + ", domEstmStartCheckInTime=" + this.getDomEstmStartCheckInTime() + ", domEstmEndCheckInTime=" + this.getDomEstmEndCheckInTime() + ", domStartCheckInTime=" + this.getDomStartCheckInTime() + ", domEndCheckInTime=" + this.getDomEndCheckInTime() + ", intEstmStartCheckInTime=" + this.getIntEstmStartCheckInTime() + ", intEstmEndCheckInTime=" + this.getIntEstmEndCheckInTime() + ", intStartCheckInTime=" + this.getIntStartCheckInTime() + ", intEndCheckInTime=" + this.getIntEndCheckInTime() + ", vip=" + this.getVip() + ", cancelTime=" + this.getCancelTime() + ", alternateGroup=" + this.getAlternateGroup() + ", sharingGroup=" + this.getSharingGroup() + ", stationGroup=" + this.getStationGroup() + ", terminal=" + this.getTerminal() + ", runway=" + this.getRunway() + ", boardingGateGroup=" + this.getBoardingGateGroup() + ")";
    }
}
