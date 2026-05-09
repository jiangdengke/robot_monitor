/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Excel
 */
package com.robotmonitor.flight.domain;

import com.robotmonitor.common.annotation.Excel;

public class BarCodeRespons {
    private String serviceCode;
    private String flightNo;
    private String orig;
    private String dest;
    private String username;
    private String cabin;
    private String seat;
    private String seg;
    private String cardService;
    private String cardNo;
    private String memLevel;
    private String starLevel;
    private String inType;
    private String gateCd;
    private String estmTakeOffTime;
    private String carouselCd;
    @Excel(name="\u673a\u578b")
    private String craftType;
    private int followerNum;

    public String getServiceCode() {
        return this.serviceCode;
    }

    public String getFlightNo() {
        return this.flightNo;
    }

    public String getOrig() {
        return this.orig;
    }

    public String getDest() {
        return this.dest;
    }

    public String getUsername() {
        return this.username;
    }

    public String getCabin() {
        return this.cabin;
    }

    public String getSeat() {
        return this.seat;
    }

    public String getSeg() {
        return this.seg;
    }

    public String getCardService() {
        return this.cardService;
    }

    public String getCardNo() {
        return this.cardNo;
    }

    public String getMemLevel() {
        return this.memLevel;
    }

    public String getStarLevel() {
        return this.starLevel;
    }

    public String getInType() {
        return this.inType;
    }

    public String getGateCd() {
        return this.gateCd;
    }

    public String getEstmTakeOffTime() {
        return this.estmTakeOffTime;
    }

    public String getCarouselCd() {
        return this.carouselCd;
    }

    public String getCraftType() {
        return this.craftType;
    }

    public int getFollowerNum() {
        return this.followerNum;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public void setOrig(String orig) {
        this.orig = orig;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCabin(String cabin) {
        this.cabin = cabin;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public void setSeg(String seg) {
        this.seg = seg;
    }

    public void setCardService(String cardService) {
        this.cardService = cardService;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public void setMemLevel(String memLevel) {
        this.memLevel = memLevel;
    }

    public void setStarLevel(String starLevel) {
        this.starLevel = starLevel;
    }

    public void setInType(String inType) {
        this.inType = inType;
    }

    public void setGateCd(String gateCd) {
        this.gateCd = gateCd;
    }

    public void setEstmTakeOffTime(String estmTakeOffTime) {
        this.estmTakeOffTime = estmTakeOffTime;
    }

    public void setCarouselCd(String carouselCd) {
        this.carouselCd = carouselCd;
    }

    public void setCraftType(String craftType) {
        this.craftType = craftType;
    }

    public void setFollowerNum(int followerNum) {
        this.followerNum = followerNum;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BarCodeRespons)) {
            return false;
        }
        BarCodeRespons other = (BarCodeRespons)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getFollowerNum() != other.getFollowerNum()) {
            return false;
        }
        String this$serviceCode = this.getServiceCode();
        String other$serviceCode = other.getServiceCode();
        if (this$serviceCode == null ? other$serviceCode != null : !this$serviceCode.equals(other$serviceCode)) {
            return false;
        }
        String this$flightNo = this.getFlightNo();
        String other$flightNo = other.getFlightNo();
        if (this$flightNo == null ? other$flightNo != null : !this$flightNo.equals(other$flightNo)) {
            return false;
        }
        String this$orig = this.getOrig();
        String other$orig = other.getOrig();
        if (this$orig == null ? other$orig != null : !this$orig.equals(other$orig)) {
            return false;
        }
        String this$dest = this.getDest();
        String other$dest = other.getDest();
        if (this$dest == null ? other$dest != null : !this$dest.equals(other$dest)) {
            return false;
        }
        String this$username = this.getUsername();
        String other$username = other.getUsername();
        if (this$username == null ? other$username != null : !this$username.equals(other$username)) {
            return false;
        }
        String this$cabin = this.getCabin();
        String other$cabin = other.getCabin();
        if (this$cabin == null ? other$cabin != null : !this$cabin.equals(other$cabin)) {
            return false;
        }
        String this$seat = this.getSeat();
        String other$seat = other.getSeat();
        if (this$seat == null ? other$seat != null : !this$seat.equals(other$seat)) {
            return false;
        }
        String this$seg = this.getSeg();
        String other$seg = other.getSeg();
        if (this$seg == null ? other$seg != null : !this$seg.equals(other$seg)) {
            return false;
        }
        String this$cardService = this.getCardService();
        String other$cardService = other.getCardService();
        if (this$cardService == null ? other$cardService != null : !this$cardService.equals(other$cardService)) {
            return false;
        }
        String this$cardNo = this.getCardNo();
        String other$cardNo = other.getCardNo();
        if (this$cardNo == null ? other$cardNo != null : !this$cardNo.equals(other$cardNo)) {
            return false;
        }
        String this$memLevel = this.getMemLevel();
        String other$memLevel = other.getMemLevel();
        if (this$memLevel == null ? other$memLevel != null : !this$memLevel.equals(other$memLevel)) {
            return false;
        }
        String this$starLevel = this.getStarLevel();
        String other$starLevel = other.getStarLevel();
        if (this$starLevel == null ? other$starLevel != null : !this$starLevel.equals(other$starLevel)) {
            return false;
        }
        String this$inType = this.getInType();
        String other$inType = other.getInType();
        if (this$inType == null ? other$inType != null : !this$inType.equals(other$inType)) {
            return false;
        }
        String this$gateCd = this.getGateCd();
        String other$gateCd = other.getGateCd();
        if (this$gateCd == null ? other$gateCd != null : !this$gateCd.equals(other$gateCd)) {
            return false;
        }
        String this$estmTakeOffTime = this.getEstmTakeOffTime();
        String other$estmTakeOffTime = other.getEstmTakeOffTime();
        if (this$estmTakeOffTime == null ? other$estmTakeOffTime != null : !this$estmTakeOffTime.equals(other$estmTakeOffTime)) {
            return false;
        }
        String this$carouselCd = this.getCarouselCd();
        String other$carouselCd = other.getCarouselCd();
        if (this$carouselCd == null ? other$carouselCd != null : !this$carouselCd.equals(other$carouselCd)) {
            return false;
        }
        String this$craftType = this.getCraftType();
        String other$craftType = other.getCraftType();
        return !(this$craftType == null ? other$craftType != null : !this$craftType.equals(other$craftType));
    }

    protected boolean canEqual(Object other) {
        return other instanceof BarCodeRespons;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getFollowerNum();
        String $serviceCode = this.getServiceCode();
        result = result * 59 + ($serviceCode == null ? 43 : $serviceCode.hashCode());
        String $flightNo = this.getFlightNo();
        result = result * 59 + ($flightNo == null ? 43 : $flightNo.hashCode());
        String $orig = this.getOrig();
        result = result * 59 + ($orig == null ? 43 : $orig.hashCode());
        String $dest = this.getDest();
        result = result * 59 + ($dest == null ? 43 : $dest.hashCode());
        String $username = this.getUsername();
        result = result * 59 + ($username == null ? 43 : $username.hashCode());
        String $cabin = this.getCabin();
        result = result * 59 + ($cabin == null ? 43 : $cabin.hashCode());
        String $seat = this.getSeat();
        result = result * 59 + ($seat == null ? 43 : $seat.hashCode());
        String $seg = this.getSeg();
        result = result * 59 + ($seg == null ? 43 : $seg.hashCode());
        String $cardService = this.getCardService();
        result = result * 59 + ($cardService == null ? 43 : $cardService.hashCode());
        String $cardNo = this.getCardNo();
        result = result * 59 + ($cardNo == null ? 43 : $cardNo.hashCode());
        String $memLevel = this.getMemLevel();
        result = result * 59 + ($memLevel == null ? 43 : $memLevel.hashCode());
        String $starLevel = this.getStarLevel();
        result = result * 59 + ($starLevel == null ? 43 : $starLevel.hashCode());
        String $inType = this.getInType();
        result = result * 59 + ($inType == null ? 43 : $inType.hashCode());
        String $gateCd = this.getGateCd();
        result = result * 59 + ($gateCd == null ? 43 : $gateCd.hashCode());
        String $estmTakeOffTime = this.getEstmTakeOffTime();
        result = result * 59 + ($estmTakeOffTime == null ? 43 : $estmTakeOffTime.hashCode());
        String $carouselCd = this.getCarouselCd();
        result = result * 59 + ($carouselCd == null ? 43 : $carouselCd.hashCode());
        String $craftType = this.getCraftType();
        result = result * 59 + ($craftType == null ? 43 : $craftType.hashCode());
        return result;
    }

    public String toString() {
        return "BarCodeRespons(serviceCode=" + this.getServiceCode() + ", flightNo=" + this.getFlightNo() + ", orig=" + this.getOrig() + ", dest=" + this.getDest() + ", username=" + this.getUsername() + ", cabin=" + this.getCabin() + ", seat=" + this.getSeat() + ", seg=" + this.getSeg() + ", cardService=" + this.getCardService() + ", cardNo=" + this.getCardNo() + ", memLevel=" + this.getMemLevel() + ", starLevel=" + this.getStarLevel() + ", inType=" + this.getInType() + ", gateCd=" + this.getGateCd() + ", estmTakeOffTime=" + this.getEstmTakeOffTime() + ", carouselCd=" + this.getCarouselCd() + ", craftType=" + this.getCraftType() + ", followerNum=" + this.getFollowerNum() + ")";
    }
}
