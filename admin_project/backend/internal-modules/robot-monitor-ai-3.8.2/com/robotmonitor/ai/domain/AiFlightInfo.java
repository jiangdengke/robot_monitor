/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.robotmonitor.common.annotation.Excel
 *  com.robotmonitor.flight.domain.FlightInfo
 *  io.jsonwebtoken.lang.Collections
 */
package com.robotmonitor.ai.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.robotmonitor.ai.domain.AiFlightChange;
import com.robotmonitor.common.annotation.Excel;
import com.robotmonitor.flight.domain.FlightInfo;
import io.jsonwebtoken.lang.Collections;
import java.util.Date;
import java.util.List;

public class AiFlightInfo {
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
    private List<AiFlightChange> flightChanges;

    public AiFlightInfo(FlightInfo flightInfo, List<AiFlightChange> flightChanges) {
        this.flightId = flightInfo.getFlightId();
        this.sendTime = flightInfo.getSendTime();
        this.airlineCd = flightInfo.getAirlineCd();
        this.flightNo = flightInfo.getFlightNo();
        this.scheExecDate = flightInfo.getScheExecDate();
        this.flightAttr = flightInfo.getFlightAttr();
        this.craftType = flightInfo.getCraftType();
        this.craftNo = flightInfo.getCraftNo();
        this.latestOffStatus = flightInfo.getLatestOffStatus();
        this.latestOnStatus = flightInfo.getLatestOnStatus();
        this.domFlightState = flightInfo.getDomFlightState();
        this.intFlightState = flightInfo.getIntFlightState();
        this.domFlightAbstate = flightInfo.getDomFlightAbstate();
        this.intFlightAbstate = flightInfo.getIntFlightAbstate();
        this.domAbStateTime = flightInfo.getDomAbStateTime();
        this.intAbStateTime = flightInfo.getIntAbStateTime();
        this.domFlightAbstateReason = flightInfo.getDomFlightAbstateReason();
        this.intFlightAbstateReason = flightInfo.getIntFlightAbstateReason();
        this.domInnerFlightAbstateReason = flightInfo.getDomInnerFlightAbstateReason();
        this.intInnerFlightAbstateReason = flightInfo.getIntInnerFlightAbstateReason();
        this.domFlightAbstateReasonDesc = flightInfo.getDomFlightAbstateReasonDesc();
        this.intFlightAbstateReasonDesc = flightInfo.getIntFlightAbstateReasonDesc();
        this.airline = flightInfo.getAirline();
        this.station = flightInfo.getStation();
        this.stationCn = flightInfo.getStationCn();
        this.scheTakeOffTime = flightInfo.getScheTakeOffTime();
        this.estmTakeOffTime = flightInfo.getEstmTakeOffTime();
        this.actlTakeOffTime = flightInfo.getActlTakeOffTime();
        this.gateCd = flightInfo.getGateCd();
        this.gateAttr = flightInfo.getGateAttr();
        this.estmStartTime = flightInfo.getEstmStartTime();
        this.estmEndTime = flightInfo.getEstmEndTime();
        this.carouselCd = flightInfo.getCarouselCd();
        this.carouselClass = flightInfo.getCarouselClass();
        this.carouselAttr = flightInfo.getCarouselAttr();
        this.isDelete = flightInfo.getIsDelete();
        this.flightChanges = flightChanges;
    }

    public String toString() {
        StringBuilder toString = new StringBuilder("AiFlightInfo{flightId(\u822a\u73ed\u552f\u4e00\u7f16\u53f7)='" + this.flightId + "', sendTime(KAFKA\u53d1\u9001\u65f6\u95f4)='" + this.sendTime + "', airlineCd(\u822a\u7a7a\u516c\u53f8)='" + this.airlineCd + "', flightNo(\u822a\u73ed\u53f7)='" + this.flightNo + "', scheExecDate(\u822a\u73ed\u6267\u884c\u65e5\u671f)='" + this.scheExecDate + "', flightAttr(\u822a\u73ed\u5c5e\u6027 DOM\u56fd\u5185 INT\u56fd\u9645 MIX\u6df7\u5408 REG \u5730\u533aFATT)='" + this.flightAttr + "', craftType(\u673a\u578b)='" + this.craftType + "', craftNo(\u673a\u53f7)='" + this.craftNo + "', latestOffStatus(\u6700\u65b0\u8d77\u98de\u72b6\u6001SCH-\u8ba1\u5212\u4e2d ETD-\u9884\u8ba1\u8d77\u98de CLD-\u5173\u8231\u95e8 OUT-\u6ed1\u51fa OFF-\u8d77\u98de)='" + this.latestOffStatus + "', latestOnStatus(\u6700\u65b0\u964d\u843d\u72b6\u6001SCH-\u8ba1\u5212\u4e2d ETA-\u9884\u8ba1\u5230\u8fbe ON-\u843d\u5730 IN-\u6ed1\u5165 OPN-\u5f00\u8231\u95e8)='" + this.latestOnStatus + "', domFlightState(\u56fd\u5185\u822a\u73ed\u6b63\u5e38\u72b6\u6001)='" + this.domFlightState + "', intFlightState(\u56fd\u9645\u822a\u73ed\u6b63\u5e38\u72b6\u6001)='" + this.intFlightState + "', domFlightAbstate(\u56fd\u5185\u822a\u73ed\u5f02\u5e38\u72b6\u6001)='" + this.domFlightAbstate + "', intFlightAbstate(\u56fd\u9645\u822a\u73ed\u5f02\u5e38\u72b6\u6001)='" + this.intFlightAbstate + "', domAbStateTime(\u56fd\u5185\u822a\u73ed\u5f02\u5e38\u72b6\u6001\u65f6\u95f4)='" + this.domAbStateTime + "', intAbStateTime(\u56fd\u9645\u822a\u73ed\u5f02\u5e38\u72b6\u6001\u65f6\u95f4)='" + this.intAbStateTime + "', domFlightAbstateReason(\u56fd\u5185\u822a\u73ed\u5f02\u5e38\u539f\u56e0)='" + this.domFlightAbstateReason + "', intFlightAbstateReason(\u56fd\u9645\u822a\u73ed\u5f02\u5e38\u539f\u56e0)='" + this.intFlightAbstateReason + "', domFlightAbstateReasonDesc(\u56fd\u5185\u822a\u73ed\u5f02\u5e38\u539f\u56e0\u63cf\u8ff0)='" + this.domFlightAbstateReasonDesc + "', intFlightAbstateReasonDesc(\u56fd\u9645\u822a\u73ed\u5f02\u5e38\u539f\u56e0\u63cf\u8ff0)='" + this.intFlightAbstateReasonDesc + "', airline(\u822a\u7ebf)='" + this.airline + "', station(\u822a\u7ad9)='" + this.station + "', stationCn(\u822a\u7ad9\u4e2d\u6587\u540d)='" + this.stationCn + "', scheTakeOffTime(\u8ba1\u5212\u8d77\u98de\u65f6\u95f4)='" + this.scheTakeOffTime + "', estmTakeOffTime(\u9884\u8ba1\u8d77\u98de\u65f6\u95f4)='" + this.estmTakeOffTime + "', actlTakeOffTime(\u5b9e\u9645\u8d77\u98de\u65f6\u95f4)='" + this.actlTakeOffTime + "', gateCd(\u767b\u673a\u95e8\u7f16\u53f7)='" + this.gateCd + "', gateAttr(\u767b\u673a\u95e8\u5c5e\u6027)='" + this.gateAttr + "', estmStartTime(\u9884\u8ba1\u5f00\u59cb\u4f7f\u7528\u65f6\u95f4)=" + this.estmStartTime + ", estmEndTime(\u9884\u8ba1\u7ed3\u675f\u4f7f\u7528\u65f6\u95f4)=" + this.estmEndTime + ", carouselCd(\u884c\u674e\u63d0\u53d6\u8f6c\u76d8\u7f16\u53f7)='" + this.carouselCd + "', carouselClass(\u884c\u674e\u63d0\u53d6\u8f6c\u76d8\u7b49\u7ea7)='" + this.carouselClass + "', carouselAttr(\u884c\u674e\u63d0\u53d6\u8f6c\u76d8\u5c5e\u6027)='" + this.carouselAttr + "', isDelete(\u662f\u5426\u5220\u9664)='" + this.isDelete + "'");
        if (!Collections.isEmpty(this.flightChanges)) {
            toString.append("flightChanges={");
            for (AiFlightChange flightChange : this.flightChanges) {
                toString.append(flightChange.toString());
            }
            toString.append("}");
        }
        toString.append("}");
        return toString.toString();
    }
}
