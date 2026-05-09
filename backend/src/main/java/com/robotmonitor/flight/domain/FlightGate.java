/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Excel
 *  com.robotmonitor.common.core.domain.BaseEntity
 */
package com.robotmonitor.flight.domain;

import com.robotmonitor.common.annotation.Excel;
import com.robotmonitor.common.core.domain.BaseEntity;

public class FlightGate
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String flightId;
    @Excel(name="\u767b\u673a\u95e8\u7f16\u53f7")
    private String gateCd;
    @Excel(name="\u767b\u673a\u95e8\u5c5e\u6027")
    private String gateAttr;
    @Excel(name="\u9884\u8ba1\u5f00\u59cb\u4f7f\u7528\u65f6\u95f4")
    private String estmStartTime;
    @Excel(name="\u9884\u8ba1\u7ed3\u675f\u4f7f\u7528\u65f6\u95f4")
    private String estmEndTime;
    @Excel(name="\u6240\u5c5e\u822a\u7ad9\u697c\u6807\u8bc6")
    private String terminalCd;
    @Excel(name="KAFKA\u53d1\u9001\u65f6\u95f4")
    private String sendTime;
    @Excel(name="\u822a\u73ed\u6267\u884c\u65e5\u671f")
    private String scheExecDate;

    public String getFlightId() {
        return this.flightId;
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

    public String getTerminalCd() {
        return this.terminalCd;
    }

    public String getSendTime() {
        return this.sendTime;
    }

    public String getScheExecDate() {
        return this.scheExecDate;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
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

    public void setTerminalCd(String terminalCd) {
        this.terminalCd = terminalCd;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public void setScheExecDate(String scheExecDate) {
        this.scheExecDate = scheExecDate;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FlightGate)) {
            return false;
        }
        FlightGate other = (FlightGate)((Object)o);
        if (!other.canEqual((Object)this)) {
            return false;
        }
        String this$flightId = this.getFlightId();
        String other$flightId = other.getFlightId();
        if (this$flightId == null ? other$flightId != null : !this$flightId.equals(other$flightId)) {
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
        String this$terminalCd = this.getTerminalCd();
        String other$terminalCd = other.getTerminalCd();
        if (this$terminalCd == null ? other$terminalCd != null : !this$terminalCd.equals(other$terminalCd)) {
            return false;
        }
        String this$sendTime = this.getSendTime();
        String other$sendTime = other.getSendTime();
        if (this$sendTime == null ? other$sendTime != null : !this$sendTime.equals(other$sendTime)) {
            return false;
        }
        String this$scheExecDate = this.getScheExecDate();
        String other$scheExecDate = other.getScheExecDate();
        return !(this$scheExecDate == null ? other$scheExecDate != null : !this$scheExecDate.equals(other$scheExecDate));
    }

    protected boolean canEqual(Object other) {
        return other instanceof FlightGate;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $flightId = this.getFlightId();
        result = result * 59 + ($flightId == null ? 43 : $flightId.hashCode());
        String $gateCd = this.getGateCd();
        result = result * 59 + ($gateCd == null ? 43 : $gateCd.hashCode());
        String $gateAttr = this.getGateAttr();
        result = result * 59 + ($gateAttr == null ? 43 : $gateAttr.hashCode());
        String $estmStartTime = this.getEstmStartTime();
        result = result * 59 + ($estmStartTime == null ? 43 : $estmStartTime.hashCode());
        String $estmEndTime = this.getEstmEndTime();
        result = result * 59 + ($estmEndTime == null ? 43 : $estmEndTime.hashCode());
        String $terminalCd = this.getTerminalCd();
        result = result * 59 + ($terminalCd == null ? 43 : $terminalCd.hashCode());
        String $sendTime = this.getSendTime();
        result = result * 59 + ($sendTime == null ? 43 : $sendTime.hashCode());
        String $scheExecDate = this.getScheExecDate();
        result = result * 59 + ($scheExecDate == null ? 43 : $scheExecDate.hashCode());
        return result;
    }

    public String toString() {
        return "FlightGate(flightId=" + this.getFlightId() + ", gateCd=" + this.getGateCd() + ", gateAttr=" + this.getGateAttr() + ", estmStartTime=" + this.getEstmStartTime() + ", estmEndTime=" + this.getEstmEndTime() + ", terminalCd=" + this.getTerminalCd() + ", sendTime=" + this.getSendTime() + ", scheExecDate=" + this.getScheExecDate() + ")";
    }
}
