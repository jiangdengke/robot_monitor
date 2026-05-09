/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.robotmonitor.common.annotation.Excel
 */
package com.robotmonitor.flight.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.robotmonitor.common.annotation.Excel;
import java.util.Date;

public class PassengerOutgoingDTO {
    @Excel(name="\u65c5\u5ba2\u59d3\u540d")
    private String userName;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Excel(name="\u51c6\u51fa\u65f6\u95f4", width=30.0, dateFormat="yyyy-MM-dd HH:mm:ss")
    private Date getOutTime;
    @Excel(name="\u822a\u73ed\u53f7")
    private String flightNo;
    @JsonFormat(pattern="yyyy-MM-dd")
    @Excel(name="\u822a\u73ed\u65e5\u671f", width=30.0, dateFormat="yyyy-MM-dd")
    private Date flightDate;
    @Excel(name="\u6293\u62cd\u56fe\u7247")
    private String oriImageUrl;
    @Excel(name="\u539f\u59cb\u56fe\u7247")
    private String registerImageUrl;

    public String getUserName() {
        return this.userName;
    }

    public Date getGetOutTime() {
        return this.getOutTime;
    }

    public String getFlightNo() {
        return this.flightNo;
    }

    public Date getFlightDate() {
        return this.flightDate;
    }

    public String getOriImageUrl() {
        return this.oriImageUrl;
    }

    public String getRegisterImageUrl() {
        return this.registerImageUrl;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public void setGetOutTime(Date getOutTime) {
        this.getOutTime = getOutTime;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    @JsonFormat(pattern="yyyy-MM-dd")
    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    public void setOriImageUrl(String oriImageUrl) {
        this.oriImageUrl = oriImageUrl;
    }

    public void setRegisterImageUrl(String registerImageUrl) {
        this.registerImageUrl = registerImageUrl;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PassengerOutgoingDTO)) {
            return false;
        }
        PassengerOutgoingDTO other = (PassengerOutgoingDTO)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$userName = this.getUserName();
        String other$userName = other.getUserName();
        if (this$userName == null ? other$userName != null : !this$userName.equals(other$userName)) {
            return false;
        }
        Date this$getOutTime = this.getGetOutTime();
        Date other$getOutTime = other.getGetOutTime();
        if (this$getOutTime == null ? other$getOutTime != null : !((Object)this$getOutTime).equals(other$getOutTime)) {
            return false;
        }
        String this$flightNo = this.getFlightNo();
        String other$flightNo = other.getFlightNo();
        if (this$flightNo == null ? other$flightNo != null : !this$flightNo.equals(other$flightNo)) {
            return false;
        }
        Date this$flightDate = this.getFlightDate();
        Date other$flightDate = other.getFlightDate();
        if (this$flightDate == null ? other$flightDate != null : !((Object)this$flightDate).equals(other$flightDate)) {
            return false;
        }
        String this$oriImageUrl = this.getOriImageUrl();
        String other$oriImageUrl = other.getOriImageUrl();
        if (this$oriImageUrl == null ? other$oriImageUrl != null : !this$oriImageUrl.equals(other$oriImageUrl)) {
            return false;
        }
        String this$registerImageUrl = this.getRegisterImageUrl();
        String other$registerImageUrl = other.getRegisterImageUrl();
        return !(this$registerImageUrl == null ? other$registerImageUrl != null : !this$registerImageUrl.equals(other$registerImageUrl));
    }

    protected boolean canEqual(Object other) {
        return other instanceof PassengerOutgoingDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $userName = this.getUserName();
        result = result * 59 + ($userName == null ? 43 : $userName.hashCode());
        Date $getOutTime = this.getGetOutTime();
        result = result * 59 + ($getOutTime == null ? 43 : ((Object)$getOutTime).hashCode());
        String $flightNo = this.getFlightNo();
        result = result * 59 + ($flightNo == null ? 43 : $flightNo.hashCode());
        Date $flightDate = this.getFlightDate();
        result = result * 59 + ($flightDate == null ? 43 : ((Object)$flightDate).hashCode());
        String $oriImageUrl = this.getOriImageUrl();
        result = result * 59 + ($oriImageUrl == null ? 43 : $oriImageUrl.hashCode());
        String $registerImageUrl = this.getRegisterImageUrl();
        result = result * 59 + ($registerImageUrl == null ? 43 : $registerImageUrl.hashCode());
        return result;
    }

    public String toString() {
        return "PassengerOutgoingDTO(userName=" + this.getUserName() + ", getOutTime=" + this.getGetOutTime() + ", flightNo=" + this.getFlightNo() + ", flightDate=" + this.getFlightDate() + ", oriImageUrl=" + this.getOriImageUrl() + ", registerImageUrl=" + this.getRegisterImageUrl() + ")";
    }
}
