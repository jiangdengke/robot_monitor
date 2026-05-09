/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 */
package com.robotmonitor.bot.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class GuideLogInfoRequest {
    private String robotName;
    private String roomCode;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    public String getRobotName() {
        return this.robotName;
    }

    public String getRoomCode() {
        return this.roomCode;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setRobotName(String robotName) {
        this.robotName = robotName;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GuideLogInfoRequest)) {
            return false;
        }
        GuideLogInfoRequest other = (GuideLogInfoRequest)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$robotName = this.getRobotName();
        String other$robotName = other.getRobotName();
        if (this$robotName == null ? other$robotName != null : !this$robotName.equals(other$robotName)) {
            return false;
        }
        String this$roomCode = this.getRoomCode();
        String other$roomCode = other.getRoomCode();
        if (this$roomCode == null ? other$roomCode != null : !this$roomCode.equals(other$roomCode)) {
            return false;
        }
        Date this$startTime = this.getStartTime();
        Date other$startTime = other.getStartTime();
        if (this$startTime == null ? other$startTime != null : !((Object)this$startTime).equals(other$startTime)) {
            return false;
        }
        Date this$endTime = this.getEndTime();
        Date other$endTime = other.getEndTime();
        return !(this$endTime == null ? other$endTime != null : !((Object)this$endTime).equals(other$endTime));
    }

    protected boolean canEqual(Object other) {
        return other instanceof GuideLogInfoRequest;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $robotName = this.getRobotName();
        result = result * 59 + ($robotName == null ? 43 : $robotName.hashCode());
        String $roomCode = this.getRoomCode();
        result = result * 59 + ($roomCode == null ? 43 : $roomCode.hashCode());
        Date $startTime = this.getStartTime();
        result = result * 59 + ($startTime == null ? 43 : ((Object)$startTime).hashCode());
        Date $endTime = this.getEndTime();
        result = result * 59 + ($endTime == null ? 43 : ((Object)$endTime).hashCode());
        return result;
    }

    public String toString() {
        return "GuideLogInfoRequest(robotName=" + this.getRobotName() + ", roomCode=" + this.getRoomCode() + ", startTime=" + this.getStartTime() + ", endTime=" + this.getEndTime() + ")";
    }
}
