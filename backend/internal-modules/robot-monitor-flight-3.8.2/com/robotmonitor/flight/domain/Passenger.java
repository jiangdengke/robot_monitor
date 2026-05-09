/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.robotmonitor.common.annotation.Excel
 *  com.robotmonitor.common.core.domain.BaseEntity
 *  com.robotmonitor.common.core.domain.config.ConfigRegion
 */
package com.robotmonitor.flight.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.robotmonitor.common.annotation.Excel;
import com.robotmonitor.common.core.domain.BaseEntity;
import com.robotmonitor.common.core.domain.config.ConfigRegion;
import com.robotmonitor.flight.domain.FlightInfo;
import java.util.Date;

public class Passenger
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    @Excel(name="\u65c5\u5ba2\u59d3\u540d")
    private String userName;
    @Excel(name="\u4f11\u606f\u5ba4\u7f16\u7801")
    private String roomCode;
    @Excel(name="\u822a\u73ed\u53f7")
    private String flightNo;
    @Excel(name="\u822a\u73ed\u65e5\u671f")
    private String flightDate;
    @Excel(name="\u8d77\u59cb\u5730")
    private String orig;
    @Excel(name="\u76ee\u7684\u5730")
    private String dest;
    @Excel(name="\u8231\u4f4d")
    private String cabin;
    @Excel(name="\u5ea7\u4f4d\u53f7")
    private String seat;
    @Excel(name="\u5e8f\u53f7")
    private String seq;
    @Excel(name="\u53d1\u5361\u65b9")
    private String cardService;
    @Excel(name="\u5361\u53f7")
    private String cardNo;
    @Excel(name="\u4f1a\u5458\u7ea7\u522b")
    private String memLevel;
    @Excel(name="\u661f\u76df\u7ea7\u522b")
    private String starLevel;
    @Excel(name="\u51c6\u5165\u7c7b\u578b\u7f16\u7801")
    private String inType;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Excel(name="\u51c6\u5165\u65f6\u95f4", width=30.0, dateFormat="yyyy-MM-dd HH:mm:ss")
    private Date getInTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Excel(name="\u51c6\u51fa\u65f6\u95f4", width=30.0, dateFormat="yyyy-MM-dd HH:mm:ss")
    private Date getOutTime;
    @Excel(name="\u72b6\u6001\uff1a1\uff1a\u5728 0\uff1a\u4e0d\u5728")
    private String status;
    @Excel(name="reid")
    private String reid;
    @Excel(name="pid")
    private String pid;
    private String flightId;
    @Excel(name="photo")
    private String photo;
    @Excel(name="origImageUrl")
    private String origImageUrl;
    @Excel(name="registerImageUrl")
    private String registerImageUrl;
    @Excel(name="regionId")
    private Long regionId;
    @Excel(name="coordinate")
    private String coordinate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Excel(name="\u66f4\u65b0\u65f6\u95f4", width=30.0, dateFormat="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date getOutTimeStart;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date getOutTimeEnd;
    private String inTypeText;
    private String warningType;
    private FlightInfo flight = new FlightInfo();
    private ConfigRegion region = new ConfigRegion();
    private String robotId;
    private int followerNum;
    private String isMember;
    private String colledtId;

    public Long getId() {
        return this.id;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getRoomCode() {
        return this.roomCode;
    }

    public String getFlightNo() {
        return this.flightNo;
    }

    public String getFlightDate() {
        return this.flightDate;
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

    public String getSeq() {
        return this.seq;
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

    public Date getGetInTime() {
        return this.getInTime;
    }

    public Date getGetOutTime() {
        return this.getOutTime;
    }

    public String getStatus() {
        return this.status;
    }

    public String getReid() {
        return this.reid;
    }

    public String getPid() {
        return this.pid;
    }

    public String getFlightId() {
        return this.flightId;
    }

    public String getPhoto() {
        return this.photo;
    }

    public String getOrigImageUrl() {
        return this.origImageUrl;
    }

    public String getRegisterImageUrl() {
        return this.registerImageUrl;
    }

    public Long getRegionId() {
        return this.regionId;
    }

    public String getCoordinate() {
        return this.coordinate;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public Date getGetOutTimeStart() {
        return this.getOutTimeStart;
    }

    public Date getGetOutTimeEnd() {
        return this.getOutTimeEnd;
    }

    public String getInTypeText() {
        return this.inTypeText;
    }

    public String getWarningType() {
        return this.warningType;
    }

    public FlightInfo getFlight() {
        return this.flight;
    }

    public ConfigRegion getRegion() {
        return this.region;
    }

    public String getRobotId() {
        return this.robotId;
    }

    public int getFollowerNum() {
        return this.followerNum;
    }

    public String getIsMember() {
        return this.isMember;
    }

    public String getColledtId() {
        return this.colledtId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
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

    public void setSeq(String seq) {
        this.seq = seq;
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

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public void setGetInTime(Date getInTime) {
        this.getInTime = getInTime;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public void setGetOutTime(Date getOutTime) {
        this.getOutTime = getOutTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setReid(String reid) {
        this.reid = reid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setOrigImageUrl(String origImageUrl) {
        this.origImageUrl = origImageUrl;
    }

    public void setRegisterImageUrl(String registerImageUrl) {
        this.registerImageUrl = registerImageUrl;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public void setGetOutTimeStart(Date getOutTimeStart) {
        this.getOutTimeStart = getOutTimeStart;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public void setGetOutTimeEnd(Date getOutTimeEnd) {
        this.getOutTimeEnd = getOutTimeEnd;
    }

    public void setInTypeText(String inTypeText) {
        this.inTypeText = inTypeText;
    }

    public void setWarningType(String warningType) {
        this.warningType = warningType;
    }

    public void setFlight(FlightInfo flight) {
        this.flight = flight;
    }

    public void setRegion(ConfigRegion region) {
        this.region = region;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public void setFollowerNum(int followerNum) {
        this.followerNum = followerNum;
    }

    public void setIsMember(String isMember) {
        this.isMember = isMember;
    }

    public void setColledtId(String colledtId) {
        this.colledtId = colledtId;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Passenger)) {
            return false;
        }
        Passenger other = (Passenger)((Object)o);
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
        Long this$regionId = this.getRegionId();
        Long other$regionId = other.getRegionId();
        if (this$regionId == null ? other$regionId != null : !((Object)this$regionId).equals(other$regionId)) {
            return false;
        }
        String this$userName = this.getUserName();
        String other$userName = other.getUserName();
        if (this$userName == null ? other$userName != null : !this$userName.equals(other$userName)) {
            return false;
        }
        String this$roomCode = this.getRoomCode();
        String other$roomCode = other.getRoomCode();
        if (this$roomCode == null ? other$roomCode != null : !this$roomCode.equals(other$roomCode)) {
            return false;
        }
        String this$flightNo = this.getFlightNo();
        String other$flightNo = other.getFlightNo();
        if (this$flightNo == null ? other$flightNo != null : !this$flightNo.equals(other$flightNo)) {
            return false;
        }
        String this$flightDate = this.getFlightDate();
        String other$flightDate = other.getFlightDate();
        if (this$flightDate == null ? other$flightDate != null : !this$flightDate.equals(other$flightDate)) {
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
        String this$seq = this.getSeq();
        String other$seq = other.getSeq();
        if (this$seq == null ? other$seq != null : !this$seq.equals(other$seq)) {
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
        Date this$getInTime = this.getGetInTime();
        Date other$getInTime = other.getGetInTime();
        if (this$getInTime == null ? other$getInTime != null : !((Object)this$getInTime).equals(other$getInTime)) {
            return false;
        }
        Date this$getOutTime = this.getGetOutTime();
        Date other$getOutTime = other.getGetOutTime();
        if (this$getOutTime == null ? other$getOutTime != null : !((Object)this$getOutTime).equals(other$getOutTime)) {
            return false;
        }
        String this$status = this.getStatus();
        String other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) {
            return false;
        }
        String this$reid = this.getReid();
        String other$reid = other.getReid();
        if (this$reid == null ? other$reid != null : !this$reid.equals(other$reid)) {
            return false;
        }
        String this$pid = this.getPid();
        String other$pid = other.getPid();
        if (this$pid == null ? other$pid != null : !this$pid.equals(other$pid)) {
            return false;
        }
        String this$flightId = this.getFlightId();
        String other$flightId = other.getFlightId();
        if (this$flightId == null ? other$flightId != null : !this$flightId.equals(other$flightId)) {
            return false;
        }
        String this$photo = this.getPhoto();
        String other$photo = other.getPhoto();
        if (this$photo == null ? other$photo != null : !this$photo.equals(other$photo)) {
            return false;
        }
        String this$origImageUrl = this.getOrigImageUrl();
        String other$origImageUrl = other.getOrigImageUrl();
        if (this$origImageUrl == null ? other$origImageUrl != null : !this$origImageUrl.equals(other$origImageUrl)) {
            return false;
        }
        String this$registerImageUrl = this.getRegisterImageUrl();
        String other$registerImageUrl = other.getRegisterImageUrl();
        if (this$registerImageUrl == null ? other$registerImageUrl != null : !this$registerImageUrl.equals(other$registerImageUrl)) {
            return false;
        }
        String this$coordinate = this.getCoordinate();
        String other$coordinate = other.getCoordinate();
        if (this$coordinate == null ? other$coordinate != null : !this$coordinate.equals(other$coordinate)) {
            return false;
        }
        Date this$updateTime = this.getUpdateTime();
        Date other$updateTime = other.getUpdateTime();
        if (this$updateTime == null ? other$updateTime != null : !((Object)this$updateTime).equals(other$updateTime)) {
            return false;
        }
        Date this$getOutTimeStart = this.getGetOutTimeStart();
        Date other$getOutTimeStart = other.getGetOutTimeStart();
        if (this$getOutTimeStart == null ? other$getOutTimeStart != null : !((Object)this$getOutTimeStart).equals(other$getOutTimeStart)) {
            return false;
        }
        Date this$getOutTimeEnd = this.getGetOutTimeEnd();
        Date other$getOutTimeEnd = other.getGetOutTimeEnd();
        if (this$getOutTimeEnd == null ? other$getOutTimeEnd != null : !((Object)this$getOutTimeEnd).equals(other$getOutTimeEnd)) {
            return false;
        }
        String this$inTypeText = this.getInTypeText();
        String other$inTypeText = other.getInTypeText();
        if (this$inTypeText == null ? other$inTypeText != null : !this$inTypeText.equals(other$inTypeText)) {
            return false;
        }
        String this$warningType = this.getWarningType();
        String other$warningType = other.getWarningType();
        if (this$warningType == null ? other$warningType != null : !this$warningType.equals(other$warningType)) {
            return false;
        }
        FlightInfo this$flight = this.getFlight();
        FlightInfo other$flight = other.getFlight();
        if (this$flight == null ? other$flight != null : !((Object)((Object)this$flight)).equals((Object)other$flight)) {
            return false;
        }
        ConfigRegion this$region = this.getRegion();
        ConfigRegion other$region = other.getRegion();
        if (this$region == null ? other$region != null : !this$region.equals(other$region)) {
            return false;
        }
        String this$robotId = this.getRobotId();
        String other$robotId = other.getRobotId();
        if (this$robotId == null ? other$robotId != null : !this$robotId.equals(other$robotId)) {
            return false;
        }
        String this$isMember = this.getIsMember();
        String other$isMember = other.getIsMember();
        if (this$isMember == null ? other$isMember != null : !this$isMember.equals(other$isMember)) {
            return false;
        }
        String this$colledtId = this.getColledtId();
        String other$colledtId = other.getColledtId();
        return !(this$colledtId == null ? other$colledtId != null : !this$colledtId.equals(other$colledtId));
    }

    protected boolean canEqual(Object other) {
        return other instanceof Passenger;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getFollowerNum();
        Long $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        Long $regionId = this.getRegionId();
        result = result * 59 + ($regionId == null ? 43 : ((Object)$regionId).hashCode());
        String $userName = this.getUserName();
        result = result * 59 + ($userName == null ? 43 : $userName.hashCode());
        String $roomCode = this.getRoomCode();
        result = result * 59 + ($roomCode == null ? 43 : $roomCode.hashCode());
        String $flightNo = this.getFlightNo();
        result = result * 59 + ($flightNo == null ? 43 : $flightNo.hashCode());
        String $flightDate = this.getFlightDate();
        result = result * 59 + ($flightDate == null ? 43 : $flightDate.hashCode());
        String $orig = this.getOrig();
        result = result * 59 + ($orig == null ? 43 : $orig.hashCode());
        String $dest = this.getDest();
        result = result * 59 + ($dest == null ? 43 : $dest.hashCode());
        String $cabin = this.getCabin();
        result = result * 59 + ($cabin == null ? 43 : $cabin.hashCode());
        String $seat = this.getSeat();
        result = result * 59 + ($seat == null ? 43 : $seat.hashCode());
        String $seq = this.getSeq();
        result = result * 59 + ($seq == null ? 43 : $seq.hashCode());
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
        Date $getInTime = this.getGetInTime();
        result = result * 59 + ($getInTime == null ? 43 : ((Object)$getInTime).hashCode());
        Date $getOutTime = this.getGetOutTime();
        result = result * 59 + ($getOutTime == null ? 43 : ((Object)$getOutTime).hashCode());
        String $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        String $reid = this.getReid();
        result = result * 59 + ($reid == null ? 43 : $reid.hashCode());
        String $pid = this.getPid();
        result = result * 59 + ($pid == null ? 43 : $pid.hashCode());
        String $flightId = this.getFlightId();
        result = result * 59 + ($flightId == null ? 43 : $flightId.hashCode());
        String $photo = this.getPhoto();
        result = result * 59 + ($photo == null ? 43 : $photo.hashCode());
        String $origImageUrl = this.getOrigImageUrl();
        result = result * 59 + ($origImageUrl == null ? 43 : $origImageUrl.hashCode());
        String $registerImageUrl = this.getRegisterImageUrl();
        result = result * 59 + ($registerImageUrl == null ? 43 : $registerImageUrl.hashCode());
        String $coordinate = this.getCoordinate();
        result = result * 59 + ($coordinate == null ? 43 : $coordinate.hashCode());
        Date $updateTime = this.getUpdateTime();
        result = result * 59 + ($updateTime == null ? 43 : ((Object)$updateTime).hashCode());
        Date $getOutTimeStart = this.getGetOutTimeStart();
        result = result * 59 + ($getOutTimeStart == null ? 43 : ((Object)$getOutTimeStart).hashCode());
        Date $getOutTimeEnd = this.getGetOutTimeEnd();
        result = result * 59 + ($getOutTimeEnd == null ? 43 : ((Object)$getOutTimeEnd).hashCode());
        String $inTypeText = this.getInTypeText();
        result = result * 59 + ($inTypeText == null ? 43 : $inTypeText.hashCode());
        String $warningType = this.getWarningType();
        result = result * 59 + ($warningType == null ? 43 : $warningType.hashCode());
        FlightInfo $flight = this.getFlight();
        result = result * 59 + ($flight == null ? 43 : ((Object)((Object)$flight)).hashCode());
        ConfigRegion $region = this.getRegion();
        result = result * 59 + ($region == null ? 43 : $region.hashCode());
        String $robotId = this.getRobotId();
        result = result * 59 + ($robotId == null ? 43 : $robotId.hashCode());
        String $isMember = this.getIsMember();
        result = result * 59 + ($isMember == null ? 43 : $isMember.hashCode());
        String $colledtId = this.getColledtId();
        result = result * 59 + ($colledtId == null ? 43 : $colledtId.hashCode());
        return result;
    }

    public String toString() {
        return "Passenger(id=" + this.getId() + ", userName=" + this.getUserName() + ", roomCode=" + this.getRoomCode() + ", flightNo=" + this.getFlightNo() + ", flightDate=" + this.getFlightDate() + ", orig=" + this.getOrig() + ", dest=" + this.getDest() + ", cabin=" + this.getCabin() + ", seat=" + this.getSeat() + ", seq=" + this.getSeq() + ", cardService=" + this.getCardService() + ", cardNo=" + this.getCardNo() + ", memLevel=" + this.getMemLevel() + ", starLevel=" + this.getStarLevel() + ", inType=" + this.getInType() + ", getInTime=" + this.getGetInTime() + ", getOutTime=" + this.getGetOutTime() + ", status=" + this.getStatus() + ", reid=" + this.getReid() + ", pid=" + this.getPid() + ", flightId=" + this.getFlightId() + ", photo=" + this.getPhoto() + ", origImageUrl=" + this.getOrigImageUrl() + ", registerImageUrl=" + this.getRegisterImageUrl() + ", regionId=" + this.getRegionId() + ", coordinate=" + this.getCoordinate() + ", updateTime=" + this.getUpdateTime() + ", getOutTimeStart=" + this.getGetOutTimeStart() + ", getOutTimeEnd=" + this.getGetOutTimeEnd() + ", inTypeText=" + this.getInTypeText() + ", warningType=" + this.getWarningType() + ", flight=" + this.getFlight() + ", region=" + this.getRegion() + ", robotId=" + this.getRobotId() + ", followerNum=" + this.getFollowerNum() + ", isMember=" + this.getIsMember() + ", colledtId=" + this.getColledtId() + ")";
    }
}
