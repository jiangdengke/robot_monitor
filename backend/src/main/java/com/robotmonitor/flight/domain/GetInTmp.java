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

public class GetInTmp
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    @Excel(name="\u8eab\u4efd\u8bc1\u53f7\u3001\u767b\u673a\u724c\u53f7\u3001\u4eba\u8138base64")
    private String code;
    @Excel(name="\u7528\u6237\u540d")
    private String userName;
    @Excel(name="\u822a\u73ed\u53f7")
    private String flightNo;
    @Excel(name="\u8d77\u98de\u673a\u573a")
    private String orig;
    @Excel(name="\u5230\u8fbe\u673a\u573a")
    private String dest;
    @Excel(name="\u8231\u4f4d")
    private String cabin;
    @Excel(name="\u5ea7\u4f4d\u53f7")
    private String seat;
    @Excel(name="\u822a\u6bb5")
    private String seg;
    @Excel(name="\u53d1\u5361\u65b9")
    private String cardService;
    @Excel(name="\u4f1a\u5458\u7b49\u7ea7")
    private String starLevel;
    @Excel(name="\u51c6\u5165\u7c7b\u578bcard barcode face")
    private String inType;
    private String cardNo;
    private int followerNum;
    private String isMember;
    private String colledId;

    public Long getId() {
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public String getUserName() {
        return this.userName;
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

    public String getStarLevel() {
        return this.starLevel;
    }

    public String getInType() {
        return this.inType;
    }

    public String getCardNo() {
        return this.cardNo;
    }

    public int getFollowerNum() {
        return this.followerNum;
    }

    public String getIsMember() {
        return this.isMember;
    }

    public String getColledId() {
        return this.colledId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public void setStarLevel(String starLevel) {
        this.starLevel = starLevel;
    }

    public void setInType(String inType) {
        this.inType = inType;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public void setFollowerNum(int followerNum) {
        this.followerNum = followerNum;
    }

    public void setIsMember(String isMember) {
        this.isMember = isMember;
    }

    public void setColledId(String colledId) {
        this.colledId = colledId;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GetInTmp)) {
            return false;
        }
        GetInTmp other = (GetInTmp)((Object)o);
        if (!other.canEqual((Object)this)) {
            return false;
        }
        if (this.getFollowerNum() != other.getFollowerNum()) {
            return false;
        }
        Long this$id = this.getId();
        Long other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        String this$code = this.getCode();
        String other$code = other.getCode();
        if (this$code == null ? other$code != null : !this$code.equals(other$code)) {
            return false;
        }
        String this$userName = this.getUserName();
        String other$userName = other.getUserName();
        if (this$userName == null ? other$userName != null : !this$userName.equals(other$userName)) {
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
        String this$cardNo = this.getCardNo();
        String other$cardNo = other.getCardNo();
        if (this$cardNo == null ? other$cardNo != null : !this$cardNo.equals(other$cardNo)) {
            return false;
        }
        String this$isMember = this.getIsMember();
        String other$isMember = other.getIsMember();
        if (this$isMember == null ? other$isMember != null : !this$isMember.equals(other$isMember)) {
            return false;
        }
        String this$colledId = this.getColledId();
        String other$colledId = other.getColledId();
        return !(this$colledId == null ? other$colledId != null : !this$colledId.equals(other$colledId));
    }

    protected boolean canEqual(Object other) {
        return other instanceof GetInTmp;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getFollowerNum();
        Long $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        String $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        String $userName = this.getUserName();
        result = result * 59 + ($userName == null ? 43 : $userName.hashCode());
        String $flightNo = this.getFlightNo();
        result = result * 59 + ($flightNo == null ? 43 : $flightNo.hashCode());
        String $orig = this.getOrig();
        result = result * 59 + ($orig == null ? 43 : $orig.hashCode());
        String $dest = this.getDest();
        result = result * 59 + ($dest == null ? 43 : $dest.hashCode());
        String $cabin = this.getCabin();
        result = result * 59 + ($cabin == null ? 43 : $cabin.hashCode());
        String $seat = this.getSeat();
        result = result * 59 + ($seat == null ? 43 : $seat.hashCode());
        String $seg = this.getSeg();
        result = result * 59 + ($seg == null ? 43 : $seg.hashCode());
        String $cardService = this.getCardService();
        result = result * 59 + ($cardService == null ? 43 : $cardService.hashCode());
        String $starLevel = this.getStarLevel();
        result = result * 59 + ($starLevel == null ? 43 : $starLevel.hashCode());
        String $inType = this.getInType();
        result = result * 59 + ($inType == null ? 43 : $inType.hashCode());
        String $cardNo = this.getCardNo();
        result = result * 59 + ($cardNo == null ? 43 : $cardNo.hashCode());
        String $isMember = this.getIsMember();
        result = result * 59 + ($isMember == null ? 43 : $isMember.hashCode());
        String $colledId = this.getColledId();
        result = result * 59 + ($colledId == null ? 43 : $colledId.hashCode());
        return result;
    }

    public String toString() {
        return "GetInTmp(id=" + this.getId() + ", code=" + this.getCode() + ", userName=" + this.getUserName() + ", flightNo=" + this.getFlightNo() + ", orig=" + this.getOrig() + ", dest=" + this.getDest() + ", cabin=" + this.getCabin() + ", seat=" + this.getSeat() + ", seg=" + this.getSeg() + ", cardService=" + this.getCardService() + ", starLevel=" + this.getStarLevel() + ", inType=" + this.getInType() + ", cardNo=" + this.getCardNo() + ", followerNum=" + this.getFollowerNum() + ", isMember=" + this.getIsMember() + ", colledId=" + this.getColledId() + ")";
    }
}
