/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.robotmonitor.common.annotation.Excel
 *  com.robotmonitor.common.core.domain.BaseEntity
 */
package com.robotmonitor.flight.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.robotmonitor.common.annotation.Excel;
import com.robotmonitor.common.core.domain.BaseEntity;
import java.util.Date;

public class FlightInfo
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String flightId;
    @JsonFormat(pattern="yyyy-MM-dd")
    @Excel(name="KAFKA\u53d1\u9001\u65f6\u95f4", width=30.0, dateFormat="yyyy-MM-dd")
    private String sendTime;
    @Excel(name="\u822a\u7a7a\u516c\u53f8")
    private String airlineCd;
    @Excel(name="\u822a\u73ed\u53f7")
    private String flightNo;
    @Excel(name="\u822a\u73ed\u6267\u884c\u65e5\u671f")
    private String scheExecDate;
    @Excel(name="\u822a\u73ed\u5c5e\u6027 DOM\u56fd\u5185 INT\u56fd\u9645 MIX\u6df7\u5408 REG \u5730\u533aFATT")
    private String flightAttr;
    @Excel(name="\u673a\u578b")
    private String craftType;
    @Excel(name="\u673a\u53f7")
    private String craftNo;
    @Excel(name="\u6700\u65b0\u8d77\u98de\u72b6\u6001SCH-\u8ba1\u5212\u4e2d ETD-\u9884\u8ba1\u8d77\u98de CLD-\u5173\u8231\u95e8 OUT-\u6ed1\u51fa OFF-\u8d77\u98de ")
    private String latestOffStatus;
    @Excel(name="\u6700\u65b0\u964d\u843d\u72b6\u6001SCH-\u8ba1\u5212\u4e2d ETA-\u9884\u8ba1\u5230\u8fbe ON-\u843d\u5730 IN-\u6ed1\u5165 OPN-\u5f00\u8231\u95e8 ")
    private String latestOnStatus;
    @Excel(name="\u56fd\u5185\u822a\u73ed\u6b63\u5e38\u72b6\u6001")
    private String domFlightState;
    @Excel(name="\u56fd\u9645\u822a\u73ed\u6b63\u5e38\u72b6\u6001")
    private String intFlightState;
    @Excel(name="\u56fd\u5185\u822a\u73ed\u5f02\u5e38\u72b6\u6001")
    private String domFlightAbstate;
    @Excel(name="\u56fd\u9645\u822a\u73ed\u5f02\u5e38\u72b6\u6001")
    private String intFlightAbstate;
    @JsonFormat(pattern="yyyy-MM-dd")
    @Excel(name="\u56fd\u5185\u822a\u73ed\u5f02\u5e38\u72b6\u6001\u65f6\u95f4", width=30.0, dateFormat="yyyy-MM-dd")
    private String domAbStateTime;
    @JsonFormat(pattern="yyyy-MM-dd")
    @Excel(name="\u56fd\u9645\u822a\u73ed\u5f02\u5e38\u72b6\u6001\u65f6\u95f4", width=30.0, dateFormat="yyyy-MM-dd")
    private String intAbStateTime;
    @Excel(name="\u56fd\u5185\u822a\u73ed\u5f02\u5e38\u539f\u56e0")
    private String domFlightAbstateReason;
    @Excel(name="\u56fd\u9645\u822a\u73ed\u5f02\u5e38\u539f\u56e0")
    private String intFlightAbstateReason;
    @Excel(name="\u56fd\u5185\u822a\u73ed\u5185\u90e8\u5f02\u5e38\u539f\u56e0")
    private String domInnerFlightAbstateReason;
    @Excel(name="\u56fd\u9645\u822a\u5185\u90e8\u73ed\u5f02\u5e38\u539f\u56e0")
    private String intInnerFlightAbstateReason;
    @Excel(name="\u56fd\u5185\u822a\u73ed\u5f02\u5e38\u539f\u56e0\u63cf\u8ff0")
    private String domFlightAbstateReasonDesc;
    @Excel(name="\u56fd\u9645\u822a\u73ed\u5f02\u5e38\u539f\u56e0\u63cf\u8ff0")
    private String intFlightAbstateReasonDesc;
    @Excel(name="\u822a\u7ebf")
    private String airline;
    @Excel(name="\u822a\u7ad9")
    private String station;
    private String stationCn;
    @JsonFormat(pattern="yyyy-MM-dd")
    @Excel(name="\u8ba1\u5212\u8d77\u98de\u65f6\u95f4", width=30.0, dateFormat="yyyy-MM-dd")
    private String scheTakeOffTime;
    @JsonFormat(pattern="yyyy-MM-dd")
    @Excel(name="\u9884\u8ba1\u8d77\u98de\u65f6\u95f4", width=30.0, dateFormat="yyyy-MM-dd")
    private String estmTakeOffTime;
    @JsonFormat(pattern="yyyy-MM-dd")
    @Excel(name="\u5b9e\u9645\u8d77\u98de\u65f6\u95f4", width=30.0, dateFormat="yyyy-MM-dd")
    private String actlTakeOffTime;
    @Excel(name="\u767b\u673a\u95e8\u7f16\u53f7")
    private String gateCd;
    @Excel(name="\u767b\u673a\u95e8\u5c5e\u6027")
    private String gateAttr;
    @JsonFormat(pattern="yyyy-MM-dd")
    @Excel(name="\u9884\u8ba1\u5f00\u59cb\u4f7f\u7528\u65f6\u95f4", width=30.0, dateFormat="yyyy-MM-dd")
    private Date estmStartTime;
    @JsonFormat(pattern="yyyy-MM-dd")
    @Excel(name="\u9884\u8ba1\u7ed3\u675f\u4f7f\u7528\u65f6\u95f4", width=30.0, dateFormat="yyyy-MM-dd")
    private Date estmEndTime;
    @Excel(name="\u884c\u674e\u63d0\u53d6\u8f6c\u76d8\u7f16\u53f7")
    private String carouselCd;
    @Excel(name="\u884c\u674e\u63d0\u53d6\u8f6c\u76d8\u7b49\u7ea7")
    private String carouselClass;
    @Excel(name="\u884c\u674e\u63d0\u53d6\u8f6c\u76d8\u5c5e\u6027")
    private String carouselAttr;
    @Excel(name="\u662f\u5426\u5220\u9664")
    private String isDelete = "0";

    public String getFlightId() {
        return this.flightId;
    }

    public String getSendTime() {
        return this.sendTime;
    }

    public String getAirlineCd() {
        return this.airlineCd;
    }

    public String getFlightNo() {
        return this.flightNo;
    }

    public String getScheExecDate() {
        return this.scheExecDate;
    }

    public String getFlightAttr() {
        return this.flightAttr;
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

    public String getDomAbStateTime() {
        return this.domAbStateTime;
    }

    public String getIntAbStateTime() {
        return this.intAbStateTime;
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

    public String getDomFlightAbstateReasonDesc() {
        return this.domFlightAbstateReasonDesc;
    }

    public String getIntFlightAbstateReasonDesc() {
        return this.intFlightAbstateReasonDesc;
    }

    public String getAirline() {
        return this.airline;
    }

    public String getStation() {
        return this.station;
    }

    public String getStationCn() {
        return this.stationCn;
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

    public String getGateCd() {
        return this.gateCd;
    }

    public String getGateAttr() {
        return this.gateAttr;
    }

    public Date getEstmStartTime() {
        return this.estmStartTime;
    }

    public Date getEstmEndTime() {
        return this.estmEndTime;
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

    public String getIsDelete() {
        return this.isDelete;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    @JsonFormat(pattern="yyyy-MM-dd")
    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public void setAirlineCd(String airlineCd) {
        this.airlineCd = airlineCd;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public void setScheExecDate(String scheExecDate) {
        this.scheExecDate = scheExecDate;
    }

    public void setFlightAttr(String flightAttr) {
        this.flightAttr = flightAttr;
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

    @JsonFormat(pattern="yyyy-MM-dd")
    public void setDomAbStateTime(String domAbStateTime) {
        this.domAbStateTime = domAbStateTime;
    }

    @JsonFormat(pattern="yyyy-MM-dd")
    public void setIntAbStateTime(String intAbStateTime) {
        this.intAbStateTime = intAbStateTime;
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

    public void setDomFlightAbstateReasonDesc(String domFlightAbstateReasonDesc) {
        this.domFlightAbstateReasonDesc = domFlightAbstateReasonDesc;
    }

    public void setIntFlightAbstateReasonDesc(String intFlightAbstateReasonDesc) {
        this.intFlightAbstateReasonDesc = intFlightAbstateReasonDesc;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public void setStationCn(String stationCn) {
        this.stationCn = stationCn;
    }

    @JsonFormat(pattern="yyyy-MM-dd")
    public void setScheTakeOffTime(String scheTakeOffTime) {
        this.scheTakeOffTime = scheTakeOffTime;
    }

    @JsonFormat(pattern="yyyy-MM-dd")
    public void setEstmTakeOffTime(String estmTakeOffTime) {
        this.estmTakeOffTime = estmTakeOffTime;
    }

    @JsonFormat(pattern="yyyy-MM-dd")
    public void setActlTakeOffTime(String actlTakeOffTime) {
        this.actlTakeOffTime = actlTakeOffTime;
    }

    public void setGateCd(String gateCd) {
        this.gateCd = gateCd;
    }

    public void setGateAttr(String gateAttr) {
        this.gateAttr = gateAttr;
    }

    @JsonFormat(pattern="yyyy-MM-dd")
    public void setEstmStartTime(Date estmStartTime) {
        this.estmStartTime = estmStartTime;
    }

    @JsonFormat(pattern="yyyy-MM-dd")
    public void setEstmEndTime(Date estmEndTime) {
        this.estmEndTime = estmEndTime;
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

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FlightInfo)) {
            return false;
        }
        FlightInfo other = (FlightInfo)((Object)o);
        if (!other.canEqual((Object)this)) {
            return false;
        }
        String this$flightId = this.getFlightId();
        String other$flightId = other.getFlightId();
        if (this$flightId == null ? other$flightId != null : !this$flightId.equals(other$flightId)) {
            return false;
        }
        String this$sendTime = this.getSendTime();
        String other$sendTime = other.getSendTime();
        if (this$sendTime == null ? other$sendTime != null : !this$sendTime.equals(other$sendTime)) {
            return false;
        }
        String this$airlineCd = this.getAirlineCd();
        String other$airlineCd = other.getAirlineCd();
        if (this$airlineCd == null ? other$airlineCd != null : !this$airlineCd.equals(other$airlineCd)) {
            return false;
        }
        String this$flightNo = this.getFlightNo();
        String other$flightNo = other.getFlightNo();
        if (this$flightNo == null ? other$flightNo != null : !this$flightNo.equals(other$flightNo)) {
            return false;
        }
        String this$scheExecDate = this.getScheExecDate();
        String other$scheExecDate = other.getScheExecDate();
        if (this$scheExecDate == null ? other$scheExecDate != null : !this$scheExecDate.equals(other$scheExecDate)) {
            return false;
        }
        String this$flightAttr = this.getFlightAttr();
        String other$flightAttr = other.getFlightAttr();
        if (this$flightAttr == null ? other$flightAttr != null : !this$flightAttr.equals(other$flightAttr)) {
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
        String this$domAbStateTime = this.getDomAbStateTime();
        String other$domAbStateTime = other.getDomAbStateTime();
        if (this$domAbStateTime == null ? other$domAbStateTime != null : !this$domAbStateTime.equals(other$domAbStateTime)) {
            return false;
        }
        String this$intAbStateTime = this.getIntAbStateTime();
        String other$intAbStateTime = other.getIntAbStateTime();
        if (this$intAbStateTime == null ? other$intAbStateTime != null : !this$intAbStateTime.equals(other$intAbStateTime)) {
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
        String this$domFlightAbstateReasonDesc = this.getDomFlightAbstateReasonDesc();
        String other$domFlightAbstateReasonDesc = other.getDomFlightAbstateReasonDesc();
        if (this$domFlightAbstateReasonDesc == null ? other$domFlightAbstateReasonDesc != null : !this$domFlightAbstateReasonDesc.equals(other$domFlightAbstateReasonDesc)) {
            return false;
        }
        String this$intFlightAbstateReasonDesc = this.getIntFlightAbstateReasonDesc();
        String other$intFlightAbstateReasonDesc = other.getIntFlightAbstateReasonDesc();
        if (this$intFlightAbstateReasonDesc == null ? other$intFlightAbstateReasonDesc != null : !this$intFlightAbstateReasonDesc.equals(other$intFlightAbstateReasonDesc)) {
            return false;
        }
        String this$airline = this.getAirline();
        String other$airline = other.getAirline();
        if (this$airline == null ? other$airline != null : !this$airline.equals(other$airline)) {
            return false;
        }
        String this$station = this.getStation();
        String other$station = other.getStation();
        if (this$station == null ? other$station != null : !this$station.equals(other$station)) {
            return false;
        }
        String this$stationCn = this.getStationCn();
        String other$stationCn = other.getStationCn();
        if (this$stationCn == null ? other$stationCn != null : !this$stationCn.equals(other$stationCn)) {
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
        Date this$estmStartTime = this.getEstmStartTime();
        Date other$estmStartTime = other.getEstmStartTime();
        if (this$estmStartTime == null ? other$estmStartTime != null : !((Object)this$estmStartTime).equals(other$estmStartTime)) {
            return false;
        }
        Date this$estmEndTime = this.getEstmEndTime();
        Date other$estmEndTime = other.getEstmEndTime();
        if (this$estmEndTime == null ? other$estmEndTime != null : !((Object)this$estmEndTime).equals(other$estmEndTime)) {
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
        String this$isDelete = this.getIsDelete();
        String other$isDelete = other.getIsDelete();
        return !(this$isDelete == null ? other$isDelete != null : !this$isDelete.equals(other$isDelete));
    }

    protected boolean canEqual(Object other) {
        return other instanceof FlightInfo;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $flightId = this.getFlightId();
        result = result * 59 + ($flightId == null ? 43 : $flightId.hashCode());
        String $sendTime = this.getSendTime();
        result = result * 59 + ($sendTime == null ? 43 : $sendTime.hashCode());
        String $airlineCd = this.getAirlineCd();
        result = result * 59 + ($airlineCd == null ? 43 : $airlineCd.hashCode());
        String $flightNo = this.getFlightNo();
        result = result * 59 + ($flightNo == null ? 43 : $flightNo.hashCode());
        String $scheExecDate = this.getScheExecDate();
        result = result * 59 + ($scheExecDate == null ? 43 : $scheExecDate.hashCode());
        String $flightAttr = this.getFlightAttr();
        result = result * 59 + ($flightAttr == null ? 43 : $flightAttr.hashCode());
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
        String $domAbStateTime = this.getDomAbStateTime();
        result = result * 59 + ($domAbStateTime == null ? 43 : $domAbStateTime.hashCode());
        String $intAbStateTime = this.getIntAbStateTime();
        result = result * 59 + ($intAbStateTime == null ? 43 : $intAbStateTime.hashCode());
        String $domFlightAbstateReason = this.getDomFlightAbstateReason();
        result = result * 59 + ($domFlightAbstateReason == null ? 43 : $domFlightAbstateReason.hashCode());
        String $intFlightAbstateReason = this.getIntFlightAbstateReason();
        result = result * 59 + ($intFlightAbstateReason == null ? 43 : $intFlightAbstateReason.hashCode());
        String $domInnerFlightAbstateReason = this.getDomInnerFlightAbstateReason();
        result = result * 59 + ($domInnerFlightAbstateReason == null ? 43 : $domInnerFlightAbstateReason.hashCode());
        String $intInnerFlightAbstateReason = this.getIntInnerFlightAbstateReason();
        result = result * 59 + ($intInnerFlightAbstateReason == null ? 43 : $intInnerFlightAbstateReason.hashCode());
        String $domFlightAbstateReasonDesc = this.getDomFlightAbstateReasonDesc();
        result = result * 59 + ($domFlightAbstateReasonDesc == null ? 43 : $domFlightAbstateReasonDesc.hashCode());
        String $intFlightAbstateReasonDesc = this.getIntFlightAbstateReasonDesc();
        result = result * 59 + ($intFlightAbstateReasonDesc == null ? 43 : $intFlightAbstateReasonDesc.hashCode());
        String $airline = this.getAirline();
        result = result * 59 + ($airline == null ? 43 : $airline.hashCode());
        String $station = this.getStation();
        result = result * 59 + ($station == null ? 43 : $station.hashCode());
        String $stationCn = this.getStationCn();
        result = result * 59 + ($stationCn == null ? 43 : $stationCn.hashCode());
        String $scheTakeOffTime = this.getScheTakeOffTime();
        result = result * 59 + ($scheTakeOffTime == null ? 43 : $scheTakeOffTime.hashCode());
        String $estmTakeOffTime = this.getEstmTakeOffTime();
        result = result * 59 + ($estmTakeOffTime == null ? 43 : $estmTakeOffTime.hashCode());
        String $actlTakeOffTime = this.getActlTakeOffTime();
        result = result * 59 + ($actlTakeOffTime == null ? 43 : $actlTakeOffTime.hashCode());
        String $gateCd = this.getGateCd();
        result = result * 59 + ($gateCd == null ? 43 : $gateCd.hashCode());
        String $gateAttr = this.getGateAttr();
        result = result * 59 + ($gateAttr == null ? 43 : $gateAttr.hashCode());
        Date $estmStartTime = this.getEstmStartTime();
        result = result * 59 + ($estmStartTime == null ? 43 : ((Object)$estmStartTime).hashCode());
        Date $estmEndTime = this.getEstmEndTime();
        result = result * 59 + ($estmEndTime == null ? 43 : ((Object)$estmEndTime).hashCode());
        String $carouselCd = this.getCarouselCd();
        result = result * 59 + ($carouselCd == null ? 43 : $carouselCd.hashCode());
        String $carouselClass = this.getCarouselClass();
        result = result * 59 + ($carouselClass == null ? 43 : $carouselClass.hashCode());
        String $carouselAttr = this.getCarouselAttr();
        result = result * 59 + ($carouselAttr == null ? 43 : $carouselAttr.hashCode());
        String $isDelete = this.getIsDelete();
        result = result * 59 + ($isDelete == null ? 43 : $isDelete.hashCode());
        return result;
    }

    public String toString() {
        return "FlightInfo(flightId=" + this.getFlightId() + ", sendTime=" + this.getSendTime() + ", airlineCd=" + this.getAirlineCd() + ", flightNo=" + this.getFlightNo() + ", scheExecDate=" + this.getScheExecDate() + ", flightAttr=" + this.getFlightAttr() + ", craftType=" + this.getCraftType() + ", craftNo=" + this.getCraftNo() + ", latestOffStatus=" + this.getLatestOffStatus() + ", latestOnStatus=" + this.getLatestOnStatus() + ", domFlightState=" + this.getDomFlightState() + ", intFlightState=" + this.getIntFlightState() + ", domFlightAbstate=" + this.getDomFlightAbstate() + ", intFlightAbstate=" + this.getIntFlightAbstate() + ", domAbStateTime=" + this.getDomAbStateTime() + ", intAbStateTime=" + this.getIntAbStateTime() + ", domFlightAbstateReason=" + this.getDomFlightAbstateReason() + ", intFlightAbstateReason=" + this.getIntFlightAbstateReason() + ", domInnerFlightAbstateReason=" + this.getDomInnerFlightAbstateReason() + ", intInnerFlightAbstateReason=" + this.getIntInnerFlightAbstateReason() + ", domFlightAbstateReasonDesc=" + this.getDomFlightAbstateReasonDesc() + ", intFlightAbstateReasonDesc=" + this.getIntFlightAbstateReasonDesc() + ", airline=" + this.getAirline() + ", station=" + this.getStation() + ", stationCn=" + this.getStationCn() + ", scheTakeOffTime=" + this.getScheTakeOffTime() + ", estmTakeOffTime=" + this.getEstmTakeOffTime() + ", actlTakeOffTime=" + this.getActlTakeOffTime() + ", gateCd=" + this.getGateCd() + ", gateAttr=" + this.getGateAttr() + ", estmStartTime=" + this.getEstmStartTime() + ", estmEndTime=" + this.getEstmEndTime() + ", carouselCd=" + this.getCarouselCd() + ", carouselClass=" + this.getCarouselClass() + ", carouselAttr=" + this.getCarouselAttr() + ", isDelete=" + this.getIsDelete() + ")";
    }
}
