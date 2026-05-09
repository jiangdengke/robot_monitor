/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Excel
 */
package com.robotmonitor.flight.domain.digitalTwin;

import com.robotmonitor.common.annotation.Excel;

public class WarningDto {
    private Long id;
    @Excel(name="\u65c5\u5ba2ID")
    private Long passengerId;
    @Excel(name="\u822a\u73edID")
    private String flightId;
    @Excel(name="\u822a\u73ed\u63d0\u9192ID")
    private Long flightWarningId;
    @Excel(name="\u63d0\u9192\u7c7b\u578b")
    private String warningType;
    @Excel(name="\u63d0\u9192\u65b9\u5f0f 1\u4eba\u5de5\u63d0\u9192 2\u673a\u5668\u4eba\u63d0\u9192")
    private String noticeType;
    @Excel(name="\u5f53\u65f6\u7684\u533a\u57dfID")
    private Long regionId;
    @Excel(name="\u63d0\u9192\u5185\u5bb9")
    private String warningInfo;
    @Excel(name="\u63d0\u9192\u53cd\u9988 1\u6210\u529f 0 \u5931\u8d25 99 \u6b63\u5728\u63d0\u9192")
    private String isSuccess = "-1";

    public Long getId() {
        return this.id;
    }

    public Long getPassengerId() {
        return this.passengerId;
    }

    public String getFlightId() {
        return this.flightId;
    }

    public Long getFlightWarningId() {
        return this.flightWarningId;
    }

    public String getWarningType() {
        return this.warningType;
    }

    public String getNoticeType() {
        return this.noticeType;
    }

    public Long getRegionId() {
        return this.regionId;
    }

    public String getWarningInfo() {
        return this.warningInfo;
    }

    public String getIsSuccess() {
        return this.isSuccess;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public void setFlightWarningId(Long flightWarningId) {
        this.flightWarningId = flightWarningId;
    }

    public void setWarningType(String warningType) {
        this.warningType = warningType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public void setWarningInfo(String warningInfo) {
        this.warningInfo = warningInfo;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof WarningDto)) {
            return false;
        }
        WarningDto other = (WarningDto)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Long this$id = this.getId();
        Long other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        Long this$passengerId = this.getPassengerId();
        Long other$passengerId = other.getPassengerId();
        if (this$passengerId == null ? other$passengerId != null : !((Object)this$passengerId).equals(other$passengerId)) {
            return false;
        }
        Long this$flightWarningId = this.getFlightWarningId();
        Long other$flightWarningId = other.getFlightWarningId();
        if (this$flightWarningId == null ? other$flightWarningId != null : !((Object)this$flightWarningId).equals(other$flightWarningId)) {
            return false;
        }
        Long this$regionId = this.getRegionId();
        Long other$regionId = other.getRegionId();
        if (this$regionId == null ? other$regionId != null : !((Object)this$regionId).equals(other$regionId)) {
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
        String this$noticeType = this.getNoticeType();
        String other$noticeType = other.getNoticeType();
        if (this$noticeType == null ? other$noticeType != null : !this$noticeType.equals(other$noticeType)) {
            return false;
        }
        String this$warningInfo = this.getWarningInfo();
        String other$warningInfo = other.getWarningInfo();
        if (this$warningInfo == null ? other$warningInfo != null : !this$warningInfo.equals(other$warningInfo)) {
            return false;
        }
        String this$isSuccess = this.getIsSuccess();
        String other$isSuccess = other.getIsSuccess();
        return !(this$isSuccess == null ? other$isSuccess != null : !this$isSuccess.equals(other$isSuccess));
    }

    protected boolean canEqual(Object other) {
        return other instanceof WarningDto;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        Long $passengerId = this.getPassengerId();
        result = result * 59 + ($passengerId == null ? 43 : ((Object)$passengerId).hashCode());
        Long $flightWarningId = this.getFlightWarningId();
        result = result * 59 + ($flightWarningId == null ? 43 : ((Object)$flightWarningId).hashCode());
        Long $regionId = this.getRegionId();
        result = result * 59 + ($regionId == null ? 43 : ((Object)$regionId).hashCode());
        String $flightId = this.getFlightId();
        result = result * 59 + ($flightId == null ? 43 : $flightId.hashCode());
        String $warningType = this.getWarningType();
        result = result * 59 + ($warningType == null ? 43 : $warningType.hashCode());
        String $noticeType = this.getNoticeType();
        result = result * 59 + ($noticeType == null ? 43 : $noticeType.hashCode());
        String $warningInfo = this.getWarningInfo();
        result = result * 59 + ($warningInfo == null ? 43 : $warningInfo.hashCode());
        String $isSuccess = this.getIsSuccess();
        result = result * 59 + ($isSuccess == null ? 43 : $isSuccess.hashCode());
        return result;
    }

    public String toString() {
        return "WarningDto(id=" + this.getId() + ", passengerId=" + this.getPassengerId() + ", flightId=" + this.getFlightId() + ", flightWarningId=" + this.getFlightWarningId() + ", warningType=" + this.getWarningType() + ", noticeType=" + this.getNoticeType() + ", regionId=" + this.getRegionId() + ", warningInfo=" + this.getWarningInfo() + ", isSuccess=" + this.getIsSuccess() + ")";
    }
}
