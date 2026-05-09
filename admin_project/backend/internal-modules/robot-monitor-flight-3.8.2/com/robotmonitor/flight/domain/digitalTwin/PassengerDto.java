/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.robotmonitor.common.annotation.Excel
 */
package com.robotmonitor.flight.domain.digitalTwin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.robotmonitor.common.annotation.Excel;
import com.robotmonitor.flight.domain.digitalTwin.WarningDto;
import java.util.List;

public class PassengerDto {
    private Long id;
    @Excel(name="\u65c5\u5ba2\u59d3\u540d")
    private String userName;
    @Excel(name="\u4f11\u606f\u5ba4\u7f16\u7801")
    private String roomCode;
    @Excel(name="\u822a\u73ed\u53f7")
    private String flightNo;
    @Excel(name="\u822a\u73ed\u65e5\u671f")
    private String flightDate;
    @Excel(name="\u4f1a\u5458\u7ea7\u522b")
    private String memLevel;
    @Excel(name="pid")
    private String pid;
    private String flightId;
    @Excel(name="regionId")
    private Long regionId;
    @Excel(name="coordinate")
    private String coordinate;
    @Excel(name="\u6700\u65b0\u8d77\u98de\u72b6\u6001SCH-\u8ba1\u5212\u4e2d ETD-\u9884\u8ba1\u8d77\u98de CLD-\u5173\u8231\u95e8 OUT-\u6ed1\u51fa OFF-\u8d77\u98de ")
    private String latestOffStatus;
    @JsonFormat(pattern="yyyy-MM-dd")
    @Excel(name="\u9884\u8ba1\u8d77\u98de\u65f6\u95f4", width=30.0, dateFormat="yyyy-MM-dd")
    private String estmTakeOffTime;
    List<WarningDto> warningLogList;
    private Boolean isHaveNotice = false;

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

    public String getMemLevel() {
        return this.memLevel;
    }

    public String getPid() {
        return this.pid;
    }

    public String getFlightId() {
        return this.flightId;
    }

    public Long getRegionId() {
        return this.regionId;
    }

    public String getCoordinate() {
        return this.coordinate;
    }

    public String getLatestOffStatus() {
        return this.latestOffStatus;
    }

    public String getEstmTakeOffTime() {
        return this.estmTakeOffTime;
    }

    public List<WarningDto> getWarningLogList() {
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

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public void setMemLevel(String memLevel) {
        this.memLevel = memLevel;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public void setLatestOffStatus(String latestOffStatus) {
        this.latestOffStatus = latestOffStatus;
    }

    @JsonFormat(pattern="yyyy-MM-dd")
    public void setEstmTakeOffTime(String estmTakeOffTime) {
        this.estmTakeOffTime = estmTakeOffTime;
    }

    public void setWarningLogList(List<WarningDto> warningLogList) {
        this.warningLogList = warningLogList;
    }

    public void setIsHaveNotice(Boolean isHaveNotice) {
        this.isHaveNotice = isHaveNotice;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PassengerDto)) {
            return false;
        }
        PassengerDto other = (PassengerDto)o;
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
        String this$memLevel = this.getMemLevel();
        String other$memLevel = other.getMemLevel();
        if (this$memLevel == null ? other$memLevel != null : !this$memLevel.equals(other$memLevel)) {
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
        String this$coordinate = this.getCoordinate();
        String other$coordinate = other.getCoordinate();
        if (this$coordinate == null ? other$coordinate != null : !this$coordinate.equals(other$coordinate)) {
            return false;
        }
        String this$latestOffStatus = this.getLatestOffStatus();
        String other$latestOffStatus = other.getLatestOffStatus();
        if (this$latestOffStatus == null ? other$latestOffStatus != null : !this$latestOffStatus.equals(other$latestOffStatus)) {
            return false;
        }
        String this$estmTakeOffTime = this.getEstmTakeOffTime();
        String other$estmTakeOffTime = other.getEstmTakeOffTime();
        if (this$estmTakeOffTime == null ? other$estmTakeOffTime != null : !this$estmTakeOffTime.equals(other$estmTakeOffTime)) {
            return false;
        }
        List<WarningDto> this$warningLogList = this.getWarningLogList();
        List<WarningDto> other$warningLogList = other.getWarningLogList();
        return !(this$warningLogList == null ? other$warningLogList != null : !((Object)this$warningLogList).equals(other$warningLogList));
    }

    protected boolean canEqual(Object other) {
        return other instanceof PassengerDto;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        Long $regionId = this.getRegionId();
        result = result * 59 + ($regionId == null ? 43 : ((Object)$regionId).hashCode());
        Boolean $isHaveNotice = this.getIsHaveNotice();
        result = result * 59 + ($isHaveNotice == null ? 43 : ((Object)$isHaveNotice).hashCode());
        String $userName = this.getUserName();
        result = result * 59 + ($userName == null ? 43 : $userName.hashCode());
        String $roomCode = this.getRoomCode();
        result = result * 59 + ($roomCode == null ? 43 : $roomCode.hashCode());
        String $flightNo = this.getFlightNo();
        result = result * 59 + ($flightNo == null ? 43 : $flightNo.hashCode());
        String $flightDate = this.getFlightDate();
        result = result * 59 + ($flightDate == null ? 43 : $flightDate.hashCode());
        String $memLevel = this.getMemLevel();
        result = result * 59 + ($memLevel == null ? 43 : $memLevel.hashCode());
        String $pid = this.getPid();
        result = result * 59 + ($pid == null ? 43 : $pid.hashCode());
        String $flightId = this.getFlightId();
        result = result * 59 + ($flightId == null ? 43 : $flightId.hashCode());
        String $coordinate = this.getCoordinate();
        result = result * 59 + ($coordinate == null ? 43 : $coordinate.hashCode());
        String $latestOffStatus = this.getLatestOffStatus();
        result = result * 59 + ($latestOffStatus == null ? 43 : $latestOffStatus.hashCode());
        String $estmTakeOffTime = this.getEstmTakeOffTime();
        result = result * 59 + ($estmTakeOffTime == null ? 43 : $estmTakeOffTime.hashCode());
        List<WarningDto> $warningLogList = this.getWarningLogList();
        result = result * 59 + ($warningLogList == null ? 43 : ((Object)$warningLogList).hashCode());
        return result;
    }

    public String toString() {
        return "PassengerDto(id=" + this.getId() + ", userName=" + this.getUserName() + ", roomCode=" + this.getRoomCode() + ", flightNo=" + this.getFlightNo() + ", flightDate=" + this.getFlightDate() + ", memLevel=" + this.getMemLevel() + ", pid=" + this.getPid() + ", flightId=" + this.getFlightId() + ", regionId=" + this.getRegionId() + ", coordinate=" + this.getCoordinate() + ", latestOffStatus=" + this.getLatestOffStatus() + ", estmTakeOffTime=" + this.getEstmTakeOffTime() + ", warningLogList=" + this.getWarningLogList() + ", isHaveNotice=" + this.getIsHaveNotice() + ")";
    }
}
