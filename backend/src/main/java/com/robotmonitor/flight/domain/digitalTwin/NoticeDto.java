/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Excel
 */
package com.robotmonitor.flight.domain.digitalTwin;

import com.robotmonitor.common.annotation.Excel;

public class NoticeDto {
    private Long warningId;
    @Excel(name="\u65c5\u5ba2ID")
    private Long passengerId;
    private String coordinate;
    @Excel(name="\u63d0\u9192\u65b9\u5f0f 1\u4eba\u5de5\u63d0\u9192 2\u673a\u5668\u4eba\u63d0\u9192")
    private String noticeType;
    @Excel(name="\u63d0\u9192\u5185\u5bb9")
    private String warningInfo;
    private String roomCode = "-1";

    public Long getWarningId() {
        return this.warningId;
    }

    public Long getPassengerId() {
        return this.passengerId;
    }

    public String getCoordinate() {
        return this.coordinate;
    }

    public String getNoticeType() {
        return this.noticeType;
    }

    public String getWarningInfo() {
        return this.warningInfo;
    }

    public String getRoomCode() {
        return this.roomCode;
    }

    public void setWarningId(Long warningId) {
        this.warningId = warningId;
    }

    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public void setWarningInfo(String warningInfo) {
        this.warningInfo = warningInfo;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof NoticeDto)) {
            return false;
        }
        NoticeDto other = (NoticeDto)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Long this$warningId = this.getWarningId();
        Long other$warningId = other.getWarningId();
        if (this$warningId == null ? other$warningId != null : !((Object)this$warningId).equals(other$warningId)) {
            return false;
        }
        Long this$passengerId = this.getPassengerId();
        Long other$passengerId = other.getPassengerId();
        if (this$passengerId == null ? other$passengerId != null : !((Object)this$passengerId).equals(other$passengerId)) {
            return false;
        }
        String this$coordinate = this.getCoordinate();
        String other$coordinate = other.getCoordinate();
        if (this$coordinate == null ? other$coordinate != null : !this$coordinate.equals(other$coordinate)) {
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
        String this$roomCode = this.getRoomCode();
        String other$roomCode = other.getRoomCode();
        return !(this$roomCode == null ? other$roomCode != null : !this$roomCode.equals(other$roomCode));
    }

    protected boolean canEqual(Object other) {
        return other instanceof NoticeDto;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $warningId = this.getWarningId();
        result = result * 59 + ($warningId == null ? 43 : ((Object)$warningId).hashCode());
        Long $passengerId = this.getPassengerId();
        result = result * 59 + ($passengerId == null ? 43 : ((Object)$passengerId).hashCode());
        String $coordinate = this.getCoordinate();
        result = result * 59 + ($coordinate == null ? 43 : $coordinate.hashCode());
        String $noticeType = this.getNoticeType();
        result = result * 59 + ($noticeType == null ? 43 : $noticeType.hashCode());
        String $warningInfo = this.getWarningInfo();
        result = result * 59 + ($warningInfo == null ? 43 : $warningInfo.hashCode());
        String $roomCode = this.getRoomCode();
        result = result * 59 + ($roomCode == null ? 43 : $roomCode.hashCode());
        return result;
    }

    public String toString() {
        return "NoticeDto(warningId=" + this.getWarningId() + ", passengerId=" + this.getPassengerId() + ", coordinate=" + this.getCoordinate() + ", noticeType=" + this.getNoticeType() + ", warningInfo=" + this.getWarningInfo() + ", roomCode=" + this.getRoomCode() + ")";
    }
}
