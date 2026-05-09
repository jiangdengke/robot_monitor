/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.robotmonitor.common.annotation.Excel
 */
package com.robotmonitor.bot.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.robotmonitor.common.annotation.Excel;
import java.util.Date;

public class GuideLogInfo {
    @Excel(name="\u673a\u5668\u4ebaID")
    private String robotId;
    private String robotName;
    @Excel(name="\u533a\u57dfID")
    private Long regionId;
    @Excel(name="\u533a\u57df\u540d\u79f0")
    private String regionName;
    private String roomCode;
    @Excel(name="\u8d35\u5bbe\u5ba4\u540d\u79f0")
    private String deptName;
    @Excel(name="\u5750\u6807")
    private String coordinate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public String getRobotId() {
        return this.robotId;
    }

    public String getRobotName() {
        return this.robotName;
    }

    public Long getRegionId() {
        return this.regionId;
    }

    public String getRegionName() {
        return this.regionName;
    }

    public String getRoomCode() {
        return this.roomCode;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public String getCoordinate() {
        return this.coordinate;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public void setRobotName(String robotName) {
        this.robotName = robotName;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GuideLogInfo)) {
            return false;
        }
        GuideLogInfo other = (GuideLogInfo)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Long this$regionId = this.getRegionId();
        Long other$regionId = other.getRegionId();
        if (this$regionId == null ? other$regionId != null : !((Object)this$regionId).equals(other$regionId)) {
            return false;
        }
        String this$robotId = this.getRobotId();
        String other$robotId = other.getRobotId();
        if (this$robotId == null ? other$robotId != null : !this$robotId.equals(other$robotId)) {
            return false;
        }
        String this$robotName = this.getRobotName();
        String other$robotName = other.getRobotName();
        if (this$robotName == null ? other$robotName != null : !this$robotName.equals(other$robotName)) {
            return false;
        }
        String this$regionName = this.getRegionName();
        String other$regionName = other.getRegionName();
        if (this$regionName == null ? other$regionName != null : !this$regionName.equals(other$regionName)) {
            return false;
        }
        String this$roomCode = this.getRoomCode();
        String other$roomCode = other.getRoomCode();
        if (this$roomCode == null ? other$roomCode != null : !this$roomCode.equals(other$roomCode)) {
            return false;
        }
        String this$deptName = this.getDeptName();
        String other$deptName = other.getDeptName();
        if (this$deptName == null ? other$deptName != null : !this$deptName.equals(other$deptName)) {
            return false;
        }
        String this$coordinate = this.getCoordinate();
        String other$coordinate = other.getCoordinate();
        if (this$coordinate == null ? other$coordinate != null : !this$coordinate.equals(other$coordinate)) {
            return false;
        }
        Date this$createTime = this.getCreateTime();
        Date other$createTime = other.getCreateTime();
        return !(this$createTime == null ? other$createTime != null : !((Object)this$createTime).equals(other$createTime));
    }

    protected boolean canEqual(Object other) {
        return other instanceof GuideLogInfo;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $regionId = this.getRegionId();
        result = result * 59 + ($regionId == null ? 43 : ((Object)$regionId).hashCode());
        String $robotId = this.getRobotId();
        result = result * 59 + ($robotId == null ? 43 : $robotId.hashCode());
        String $robotName = this.getRobotName();
        result = result * 59 + ($robotName == null ? 43 : $robotName.hashCode());
        String $regionName = this.getRegionName();
        result = result * 59 + ($regionName == null ? 43 : $regionName.hashCode());
        String $roomCode = this.getRoomCode();
        result = result * 59 + ($roomCode == null ? 43 : $roomCode.hashCode());
        String $deptName = this.getDeptName();
        result = result * 59 + ($deptName == null ? 43 : $deptName.hashCode());
        String $coordinate = this.getCoordinate();
        result = result * 59 + ($coordinate == null ? 43 : $coordinate.hashCode());
        Date $createTime = this.getCreateTime();
        result = result * 59 + ($createTime == null ? 43 : ((Object)$createTime).hashCode());
        return result;
    }

    public String toString() {
        return "GuideLogInfo(robotId=" + this.getRobotId() + ", robotName=" + this.getRobotName() + ", regionId=" + this.getRegionId() + ", regionName=" + this.getRegionName() + ", roomCode=" + this.getRoomCode() + ", deptName=" + this.getDeptName() + ", coordinate=" + this.getCoordinate() + ", createTime=" + this.getCreateTime() + ")";
    }
}
