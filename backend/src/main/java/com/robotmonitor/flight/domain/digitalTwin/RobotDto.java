/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Excel
 */
package com.robotmonitor.flight.domain.digitalTwin;

import com.robotmonitor.common.annotation.Excel;

public class RobotDto {
    @Excel(name="\u673a\u5668\u4ebaID")
    private String robotId;
    @Excel(name="\u673a\u5668\u540d\u79f0")
    private String robotName;
    @Excel(name="\u673a\u5668\u4eba\u5f53\u524d\u4f4d\u7f6eid")
    private Long regionId = 0L;
    private String coordinate;

    public String getRobotId() {
        return this.robotId;
    }

    public String getRobotName() {
        return this.robotName;
    }

    public Long getRegionId() {
        return this.regionId;
    }

    public String getCoordinate() {
        return this.coordinate;
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

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RobotDto)) {
            return false;
        }
        RobotDto other = (RobotDto)o;
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
        String this$coordinate = this.getCoordinate();
        String other$coordinate = other.getCoordinate();
        return !(this$coordinate == null ? other$coordinate != null : !this$coordinate.equals(other$coordinate));
    }

    protected boolean canEqual(Object other) {
        return other instanceof RobotDto;
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
        String $coordinate = this.getCoordinate();
        result = result * 59 + ($coordinate == null ? 43 : $coordinate.hashCode());
        return result;
    }

    public String toString() {
        return "RobotDto(robotId=" + this.getRobotId() + ", robotName=" + this.getRobotName() + ", regionId=" + this.getRegionId() + ", coordinate=" + this.getCoordinate() + ")";
    }
}
