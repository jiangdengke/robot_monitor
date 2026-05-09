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

public class FlightChangePassengerDTO {
    @Excel(name="\u65c5\u5ba2id")
    private Long id;
    @Excel(name="\u65c5\u5ba2\u59d3\u540d")
    private String userName;
    @Excel(name="\u4f11\u606f\u5ba4\u7f16\u7801")
    private String roomCode;
    @Excel(name="\u822a\u73ed\u53f7")
    private String flightNo;
    @JsonFormat(pattern="yyyy-MM-dd")
    @Excel(name="\u822a\u73ed\u65e5\u671f", width=30.0, dateFormat="yyyy-MM-dd")
    private Date flightDate;
    @Excel(name="\u822a\u73ed\u552f\u4e00\u7f16\u53f7")
    private String flightId;
    @Excel(name="\u822a\u53d8\u7c7b\u578b")
    private String warningType;
    @Excel(name="\u822a\u53d8\u53d8\u66f4\u524d")
    private String changeBefore;
    @Excel(name="\u822a\u53d8\u53d8\u66f4\u540e")
    private String changeAfter;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Excel(name="\u521b\u5efa\u65f6\u95f4", width=30.0, dateFormat="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Excel(name="\u66f4\u65b0\u65f6\u95f4", width=30.0, dateFormat="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    @Excel(name="\u6293\u62cd\u56fe\u7247")
    private String origImageUrl;
    @Excel(name="\u539f\u59cb\u56fe\u7247")
    private String registerImageUrl;
    @Excel(name="\u8d77\u59cb\u5730")
    private String orig;
    @Excel(name="\u76ee\u7684\u5730")
    private String dest;
    @Excel(name="\u8231\u4f4d")
    private String cabin;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Excel(name="\u51c6\u5165\u65f6\u95f4", width=30.0, dateFormat="yyyy-MM-dd HH:mm:ss")
    private Date getInTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Excel(name="\u51c6\u51fa\u65f6\u95f4", width=30.0, dateFormat="yyyy-MM-dd HH:mm:ss")
    private Date getOutTime;
    @Excel(name="\u72b6\u6001")
    private String status;
    @Excel(name="\u5750\u6807\u4fe1\u606f")
    private String coordinate;
    @Excel(name="\u533a\u57dfid")
    private String regionId;
    @Excel(name="\u533a\u57df\u540d\u79f0")
    private String regionName;
    @Excel(name="\u533a\u57df\u4fe1\u606f")
    private String remark;

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

    public Date getFlightDate() {
        return this.flightDate;
    }

    public String getFlightId() {
        return this.flightId;
    }

    public String getWarningType() {
        return this.warningType;
    }

    public String getChangeBefore() {
        return this.changeBefore;
    }

    public String getChangeAfter() {
        return this.changeAfter;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public String getOrigImageUrl() {
        return this.origImageUrl;
    }

    public String getRegisterImageUrl() {
        return this.registerImageUrl;
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

    public Date getGetInTime() {
        return this.getInTime;
    }

    public Date getGetOutTime() {
        return this.getOutTime;
    }

    public String getStatus() {
        return this.status;
    }

    public String getCoordinate() {
        return this.coordinate;
    }

    public String getRegionId() {
        return this.regionId;
    }

    public String getRegionName() {
        return this.regionName;
    }

    public String getRemark() {
        return this.remark;
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

    @JsonFormat(pattern="yyyy-MM-dd")
    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public void setWarningType(String warningType) {
        this.warningType = warningType;
    }

    public void setChangeBefore(String changeBefore) {
        this.changeBefore = changeBefore;
    }

    public void setChangeAfter(String changeAfter) {
        this.changeAfter = changeAfter;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setOrigImageUrl(String origImageUrl) {
        this.origImageUrl = origImageUrl;
    }

    public void setRegisterImageUrl(String registerImageUrl) {
        this.registerImageUrl = registerImageUrl;
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

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FlightChangePassengerDTO)) {
            return false;
        }
        FlightChangePassengerDTO other = (FlightChangePassengerDTO)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Long this$id = this.getId();
        Long other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
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
        Date this$flightDate = this.getFlightDate();
        Date other$flightDate = other.getFlightDate();
        if (this$flightDate == null ? other$flightDate != null : !((Object)this$flightDate).equals(other$flightDate)) {
            return false;
        }
        String this$flightId = this.getFlightId();
        String other$flightId = other.getFlightId();
        if (this$flightId == null ? other$flightId != null : !this$flightId.equals(other$flightId)) {
            return false;
        }
        String this$warningType = this.getWarningType();
        String other$warningType = other.getWarningType();
        if (this$warningType == null ? other$warningType != null : !this$warningType.equals(other$warningType)) {
            return false;
        }
        String this$changeBefore = this.getChangeBefore();
        String other$changeBefore = other.getChangeBefore();
        if (this$changeBefore == null ? other$changeBefore != null : !this$changeBefore.equals(other$changeBefore)) {
            return false;
        }
        String this$changeAfter = this.getChangeAfter();
        String other$changeAfter = other.getChangeAfter();
        if (this$changeAfter == null ? other$changeAfter != null : !this$changeAfter.equals(other$changeAfter)) {
            return false;
        }
        Date this$createTime = this.getCreateTime();
        Date other$createTime = other.getCreateTime();
        if (this$createTime == null ? other$createTime != null : !((Object)this$createTime).equals(other$createTime)) {
            return false;
        }
        Date this$updateTime = this.getUpdateTime();
        Date other$updateTime = other.getUpdateTime();
        if (this$updateTime == null ? other$updateTime != null : !((Object)this$updateTime).equals(other$updateTime)) {
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
        String this$coordinate = this.getCoordinate();
        String other$coordinate = other.getCoordinate();
        if (this$coordinate == null ? other$coordinate != null : !this$coordinate.equals(other$coordinate)) {
            return false;
        }
        String this$regionId = this.getRegionId();
        String other$regionId = other.getRegionId();
        if (this$regionId == null ? other$regionId != null : !this$regionId.equals(other$regionId)) {
            return false;
        }
        String this$regionName = this.getRegionName();
        String other$regionName = other.getRegionName();
        if (this$regionName == null ? other$regionName != null : !this$regionName.equals(other$regionName)) {
            return false;
        }
        String this$remark = this.getRemark();
        String other$remark = other.getRemark();
        return !(this$remark == null ? other$remark != null : !this$remark.equals(other$remark));
    }

    protected boolean canEqual(Object other) {
        return other instanceof FlightChangePassengerDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        String $userName = this.getUserName();
        result = result * 59 + ($userName == null ? 43 : $userName.hashCode());
        String $roomCode = this.getRoomCode();
        result = result * 59 + ($roomCode == null ? 43 : $roomCode.hashCode());
        String $flightNo = this.getFlightNo();
        result = result * 59 + ($flightNo == null ? 43 : $flightNo.hashCode());
        Date $flightDate = this.getFlightDate();
        result = result * 59 + ($flightDate == null ? 43 : ((Object)$flightDate).hashCode());
        String $flightId = this.getFlightId();
        result = result * 59 + ($flightId == null ? 43 : $flightId.hashCode());
        String $warningType = this.getWarningType();
        result = result * 59 + ($warningType == null ? 43 : $warningType.hashCode());
        String $changeBefore = this.getChangeBefore();
        result = result * 59 + ($changeBefore == null ? 43 : $changeBefore.hashCode());
        String $changeAfter = this.getChangeAfter();
        result = result * 59 + ($changeAfter == null ? 43 : $changeAfter.hashCode());
        Date $createTime = this.getCreateTime();
        result = result * 59 + ($createTime == null ? 43 : ((Object)$createTime).hashCode());
        Date $updateTime = this.getUpdateTime();
        result = result * 59 + ($updateTime == null ? 43 : ((Object)$updateTime).hashCode());
        String $origImageUrl = this.getOrigImageUrl();
        result = result * 59 + ($origImageUrl == null ? 43 : $origImageUrl.hashCode());
        String $registerImageUrl = this.getRegisterImageUrl();
        result = result * 59 + ($registerImageUrl == null ? 43 : $registerImageUrl.hashCode());
        String $orig = this.getOrig();
        result = result * 59 + ($orig == null ? 43 : $orig.hashCode());
        String $dest = this.getDest();
        result = result * 59 + ($dest == null ? 43 : $dest.hashCode());
        String $cabin = this.getCabin();
        result = result * 59 + ($cabin == null ? 43 : $cabin.hashCode());
        Date $getInTime = this.getGetInTime();
        result = result * 59 + ($getInTime == null ? 43 : ((Object)$getInTime).hashCode());
        Date $getOutTime = this.getGetOutTime();
        result = result * 59 + ($getOutTime == null ? 43 : ((Object)$getOutTime).hashCode());
        String $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        String $coordinate = this.getCoordinate();
        result = result * 59 + ($coordinate == null ? 43 : $coordinate.hashCode());
        String $regionId = this.getRegionId();
        result = result * 59 + ($regionId == null ? 43 : $regionId.hashCode());
        String $regionName = this.getRegionName();
        result = result * 59 + ($regionName == null ? 43 : $regionName.hashCode());
        String $remark = this.getRemark();
        result = result * 59 + ($remark == null ? 43 : $remark.hashCode());
        return result;
    }

    public String toString() {
        return "FlightChangePassengerDTO(id=" + this.getId() + ", userName=" + this.getUserName() + ", roomCode=" + this.getRoomCode() + ", flightNo=" + this.getFlightNo() + ", flightDate=" + this.getFlightDate() + ", flightId=" + this.getFlightId() + ", warningType=" + this.getWarningType() + ", changeBefore=" + this.getChangeBefore() + ", changeAfter=" + this.getChangeAfter() + ", createTime=" + this.getCreateTime() + ", updateTime=" + this.getUpdateTime() + ", origImageUrl=" + this.getOrigImageUrl() + ", registerImageUrl=" + this.getRegisterImageUrl() + ", orig=" + this.getOrig() + ", dest=" + this.getDest() + ", cabin=" + this.getCabin() + ", getInTime=" + this.getGetInTime() + ", getOutTime=" + this.getGetOutTime() + ", status=" + this.getStatus() + ", coordinate=" + this.getCoordinate() + ", regionId=" + this.getRegionId() + ", regionName=" + this.getRegionName() + ", remark=" + this.getRemark() + ")";
    }
}
