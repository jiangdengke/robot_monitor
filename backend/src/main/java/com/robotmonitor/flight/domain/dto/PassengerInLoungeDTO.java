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
import com.robotmonitor.flight.domain.PassengerWarningLog;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PassengerInLoungeDTO {
    @Excel(name="\u65c5\u5ba2id")
    private Long id;
    @Excel(name="\u65c5\u5ba2\u59d3\u540d")
    private String userName;
    @Excel(name="regionId")
    private Long regionId;
    @Excel(name="\u5750\u6807\u4fe1\u606f")
    private String coordinate;
    @Excel(name="\u822a\u73ed\u53f7")
    private String flightNo;
    @JsonFormat(pattern="yyyy-MM-dd")
    @Excel(name="\u822a\u73ed\u65e5\u671f", width=30.0, dateFormat="yyyy-MM-dd")
    private Date flightDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Excel(name="\u521b\u5efa\u65e5\u671f", width=30.0, dateFormat="yyyy-MM-dd")
    private Date createTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Excel(name="\u66f4\u65b0\u65e5\u671f", width=30.0, dateFormat="yyyy-MM-dd")
    private Date updateTime;
    @Excel(name="\u6293\u62cd\u56fe\u7247")
    private String oriImageUrl;
    @Excel(name="\u539f\u59cb\u56fe\u7247")
    private String registerImageUrl;
    private String roomCode;
    @Excel(name="\u6700\u65b0\u964d\u843d\u72b6\u6001SCH-\u8ba1\u5212\u4e2d ETA-\u9884\u8ba1\u5230\u8fbe ON-\u843d\u5730 IN-\u6ed1\u5165 OPN-\u5f00\u8231\u95e8 ")
    private String latestOnStatus;
    @JsonFormat(pattern="yyyy-MM-dd")
    @Excel(name="\u9884\u8ba1\u8d77\u98de\u65f6\u95f4", width=30.0, dateFormat="yyyy-MM-dd")
    private String estmTakeOffTime;
    private String flightId;
    private String isGuide;
    private Long areaId;
    private String areaName;
    private List<PassengerWarningLog> warningLogList = new ArrayList<PassengerWarningLog>();
    private Boolean isHaveNotice = false;

    public Long getId() {
        return this.id;
    }

    public String getUserName() {
        return this.userName;
    }

    public Long getRegionId() {
        return this.regionId;
    }

    public String getCoordinate() {
        return this.coordinate;
    }

    public String getFlightNo() {
        return this.flightNo;
    }

    public Date getFlightDate() {
        return this.flightDate;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public String getOriImageUrl() {
        return this.oriImageUrl;
    }

    public String getRegisterImageUrl() {
        return this.registerImageUrl;
    }

    public String getRoomCode() {
        return this.roomCode;
    }

    public String getLatestOnStatus() {
        return this.latestOnStatus;
    }

    public String getEstmTakeOffTime() {
        return this.estmTakeOffTime;
    }

    public String getFlightId() {
        return this.flightId;
    }

    public String getIsGuide() {
        return this.isGuide;
    }

    public Long getAreaId() {
        return this.areaId;
    }

    public String getAreaName() {
        return this.areaName;
    }

    public List<PassengerWarningLog> getWarningLogList() {
        return this.warningLogList;
    }

    public Boolean getIsHaveNotice() {
        return this.isHaveNotice;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    @JsonFormat(pattern="yyyy-MM-dd")
    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setOriImageUrl(String oriImageUrl) {
        this.oriImageUrl = oriImageUrl;
    }

    public void setRegisterImageUrl(String registerImageUrl) {
        this.registerImageUrl = registerImageUrl;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public void setLatestOnStatus(String latestOnStatus) {
        this.latestOnStatus = latestOnStatus;
    }

    @JsonFormat(pattern="yyyy-MM-dd")
    public void setEstmTakeOffTime(String estmTakeOffTime) {
        this.estmTakeOffTime = estmTakeOffTime;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public void setIsGuide(String isGuide) {
        this.isGuide = isGuide;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public void setWarningLogList(List<PassengerWarningLog> warningLogList) {
        this.warningLogList = warningLogList;
    }

    public void setIsHaveNotice(Boolean isHaveNotice) {
        this.isHaveNotice = isHaveNotice;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PassengerInLoungeDTO)) {
            return false;
        }
        PassengerInLoungeDTO other = (PassengerInLoungeDTO)o;
        if (!other.canEqual(this)) {
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
        Long this$areaId = this.getAreaId();
        Long other$areaId = other.getAreaId();
        if (this$areaId == null ? other$areaId != null : !((Object)this$areaId).equals(other$areaId)) {
            return false;
        }
        Boolean this$isHaveNotice = this.getIsHaveNotice();
        Boolean other$isHaveNotice = other.getIsHaveNotice();
        if (this$isHaveNotice == null ? other$isHaveNotice != null : !((Object)this$isHaveNotice).equals(other$isHaveNotice)) {
            return false;
        }
        String this$userName = this.getUserName();
        String other$userName = other.getUserName();
        if (this$userName == null ? other$userName != null : !this$userName.equals(other$userName)) {
            return false;
        }
        String this$coordinate = this.getCoordinate();
        String other$coordinate = other.getCoordinate();
        if (this$coordinate == null ? other$coordinate != null : !this$coordinate.equals(other$coordinate)) {
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
        String this$oriImageUrl = this.getOriImageUrl();
        String other$oriImageUrl = other.getOriImageUrl();
        if (this$oriImageUrl == null ? other$oriImageUrl != null : !this$oriImageUrl.equals(other$oriImageUrl)) {
            return false;
        }
        String this$registerImageUrl = this.getRegisterImageUrl();
        String other$registerImageUrl = other.getRegisterImageUrl();
        if (this$registerImageUrl == null ? other$registerImageUrl != null : !this$registerImageUrl.equals(other$registerImageUrl)) {
            return false;
        }
        String this$roomCode = this.getRoomCode();
        String other$roomCode = other.getRoomCode();
        if (this$roomCode == null ? other$roomCode != null : !this$roomCode.equals(other$roomCode)) {
            return false;
        }
        String this$latestOnStatus = this.getLatestOnStatus();
        String other$latestOnStatus = other.getLatestOnStatus();
        if (this$latestOnStatus == null ? other$latestOnStatus != null : !this$latestOnStatus.equals(other$latestOnStatus)) {
            return false;
        }
        String this$estmTakeOffTime = this.getEstmTakeOffTime();
        String other$estmTakeOffTime = other.getEstmTakeOffTime();
        if (this$estmTakeOffTime == null ? other$estmTakeOffTime != null : !this$estmTakeOffTime.equals(other$estmTakeOffTime)) {
            return false;
        }
        String this$flightId = this.getFlightId();
        String other$flightId = other.getFlightId();
        if (this$flightId == null ? other$flightId != null : !this$flightId.equals(other$flightId)) {
            return false;
        }
        String this$isGuide = this.getIsGuide();
        String other$isGuide = other.getIsGuide();
        if (this$isGuide == null ? other$isGuide != null : !this$isGuide.equals(other$isGuide)) {
            return false;
        }
        String this$areaName = this.getAreaName();
        String other$areaName = other.getAreaName();
        if (this$areaName == null ? other$areaName != null : !this$areaName.equals(other$areaName)) {
            return false;
        }
        List<PassengerWarningLog> this$warningLogList = this.getWarningLogList();
        List<PassengerWarningLog> other$warningLogList = other.getWarningLogList();
        return !(this$warningLogList == null ? other$warningLogList != null : !((Object)this$warningLogList).equals(other$warningLogList));
    }

    protected boolean canEqual(Object other) {
        return other instanceof PassengerInLoungeDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        Long $regionId = this.getRegionId();
        result = result * 59 + ($regionId == null ? 43 : ((Object)$regionId).hashCode());
        Long $areaId = this.getAreaId();
        result = result * 59 + ($areaId == null ? 43 : ((Object)$areaId).hashCode());
        Boolean $isHaveNotice = this.getIsHaveNotice();
        result = result * 59 + ($isHaveNotice == null ? 43 : ((Object)$isHaveNotice).hashCode());
        String $userName = this.getUserName();
        result = result * 59 + ($userName == null ? 43 : $userName.hashCode());
        String $coordinate = this.getCoordinate();
        result = result * 59 + ($coordinate == null ? 43 : $coordinate.hashCode());
        String $flightNo = this.getFlightNo();
        result = result * 59 + ($flightNo == null ? 43 : $flightNo.hashCode());
        Date $flightDate = this.getFlightDate();
        result = result * 59 + ($flightDate == null ? 43 : ((Object)$flightDate).hashCode());
        Date $createTime = this.getCreateTime();
        result = result * 59 + ($createTime == null ? 43 : ((Object)$createTime).hashCode());
        Date $updateTime = this.getUpdateTime();
        result = result * 59 + ($updateTime == null ? 43 : ((Object)$updateTime).hashCode());
        String $oriImageUrl = this.getOriImageUrl();
        result = result * 59 + ($oriImageUrl == null ? 43 : $oriImageUrl.hashCode());
        String $registerImageUrl = this.getRegisterImageUrl();
        result = result * 59 + ($registerImageUrl == null ? 43 : $registerImageUrl.hashCode());
        String $roomCode = this.getRoomCode();
        result = result * 59 + ($roomCode == null ? 43 : $roomCode.hashCode());
        String $latestOnStatus = this.getLatestOnStatus();
        result = result * 59 + ($latestOnStatus == null ? 43 : $latestOnStatus.hashCode());
        String $estmTakeOffTime = this.getEstmTakeOffTime();
        result = result * 59 + ($estmTakeOffTime == null ? 43 : $estmTakeOffTime.hashCode());
        String $flightId = this.getFlightId();
        result = result * 59 + ($flightId == null ? 43 : $flightId.hashCode());
        String $isGuide = this.getIsGuide();
        result = result * 59 + ($isGuide == null ? 43 : $isGuide.hashCode());
        String $areaName = this.getAreaName();
        result = result * 59 + ($areaName == null ? 43 : $areaName.hashCode());
        List<PassengerWarningLog> $warningLogList = this.getWarningLogList();
        result = result * 59 + ($warningLogList == null ? 43 : ((Object)$warningLogList).hashCode());
        return result;
    }

    public String toString() {
        return "PassengerInLoungeDTO(id=" + this.getId() + ", userName=" + this.getUserName() + ", regionId=" + this.getRegionId() + ", coordinate=" + this.getCoordinate() + ", flightNo=" + this.getFlightNo() + ", flightDate=" + this.getFlightDate() + ", createTime=" + this.getCreateTime() + ", updateTime=" + this.getUpdateTime() + ", oriImageUrl=" + this.getOriImageUrl() + ", registerImageUrl=" + this.getRegisterImageUrl() + ", roomCode=" + this.getRoomCode() + ", latestOnStatus=" + this.getLatestOnStatus() + ", estmTakeOffTime=" + this.getEstmTakeOffTime() + ", flightId=" + this.getFlightId() + ", isGuide=" + this.getIsGuide() + ", areaId=" + this.getAreaId() + ", areaName=" + this.getAreaName() + ", warningLogList=" + this.getWarningLogList() + ", isHaveNotice=" + this.getIsHaveNotice() + ")";
    }
}
