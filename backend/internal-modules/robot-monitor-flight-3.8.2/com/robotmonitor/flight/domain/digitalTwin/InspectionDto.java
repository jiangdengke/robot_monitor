/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Excel
 */
package com.robotmonitor.flight.domain.digitalTwin;

import com.robotmonitor.common.annotation.Excel;

public class InspectionDto {
    @Excel(name="\u5de1\u68c0\u4efb\u52a1ID")
    private Long inspTaskId;
    @Excel(name="\u673a\u5668\u4ebaID")
    private String robotId;
    @Excel(name="\u7c7b\u578b\uff1a0\uff1a\u603b\u7ed3\uff1b1\uff1a\u544a\u8b66")
    private String type;
    @Excel(name="\u70b9\u4f4d\u5e8f\u53f7")
    private String point;
    @Excel(name="\u662f\u5426\u6709\u5f02\u5e38\uff1a0\uff1a\u65e0\u5f02\u5e38\uff1b1\uff1a\u6709\u5f02\u5e38")
    private String abnormal;
    @Excel(name="\u5f02\u5e38\u4fe1\u606f")
    private String abnormalInfo;
    @Excel(name="\u5f02\u5e38\u65f6\u7684\u56fe\u7247")
    private String imageBase64;
    private String areaName;
    private String region;
    private String coordinate;

    public Long getInspTaskId() {
        return this.inspTaskId;
    }

    public String getRobotId() {
        return this.robotId;
    }

    public String getType() {
        return this.type;
    }

    public String getPoint() {
        return this.point;
    }

    public String getAbnormal() {
        return this.abnormal;
    }

    public String getAbnormalInfo() {
        return this.abnormalInfo;
    }

    public String getImageBase64() {
        return this.imageBase64;
    }

    public String getAreaName() {
        return this.areaName;
    }

    public String getRegion() {
        return this.region;
    }

    public String getCoordinate() {
        return this.coordinate;
    }

    public void setInspTaskId(Long inspTaskId) {
        this.inspTaskId = inspTaskId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public void setAbnormal(String abnormal) {
        this.abnormal = abnormal;
    }

    public void setAbnormalInfo(String abnormalInfo) {
        this.abnormalInfo = abnormalInfo;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof InspectionDto)) {
            return false;
        }
        InspectionDto other = (InspectionDto)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Long this$inspTaskId = this.getInspTaskId();
        Long other$inspTaskId = other.getInspTaskId();
        if (this$inspTaskId == null ? other$inspTaskId != null : !((Object)this$inspTaskId).equals(other$inspTaskId)) {
            return false;
        }
        String this$robotId = this.getRobotId();
        String other$robotId = other.getRobotId();
        if (this$robotId == null ? other$robotId != null : !this$robotId.equals(other$robotId)) {
            return false;
        }
        String this$type = this.getType();
        String other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) {
            return false;
        }
        String this$point = this.getPoint();
        String other$point = other.getPoint();
        if (this$point == null ? other$point != null : !this$point.equals(other$point)) {
            return false;
        }
        String this$abnormal = this.getAbnormal();
        String other$abnormal = other.getAbnormal();
        if (this$abnormal == null ? other$abnormal != null : !this$abnormal.equals(other$abnormal)) {
            return false;
        }
        String this$abnormalInfo = this.getAbnormalInfo();
        String other$abnormalInfo = other.getAbnormalInfo();
        if (this$abnormalInfo == null ? other$abnormalInfo != null : !this$abnormalInfo.equals(other$abnormalInfo)) {
            return false;
        }
        String this$imageBase64 = this.getImageBase64();
        String other$imageBase64 = other.getImageBase64();
        if (this$imageBase64 == null ? other$imageBase64 != null : !this$imageBase64.equals(other$imageBase64)) {
            return false;
        }
        String this$areaName = this.getAreaName();
        String other$areaName = other.getAreaName();
        if (this$areaName == null ? other$areaName != null : !this$areaName.equals(other$areaName)) {
            return false;
        }
        String this$region = this.getRegion();
        String other$region = other.getRegion();
        if (this$region == null ? other$region != null : !this$region.equals(other$region)) {
            return false;
        }
        String this$coordinate = this.getCoordinate();
        String other$coordinate = other.getCoordinate();
        return !(this$coordinate == null ? other$coordinate != null : !this$coordinate.equals(other$coordinate));
    }

    protected boolean canEqual(Object other) {
        return other instanceof InspectionDto;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $inspTaskId = this.getInspTaskId();
        result = result * 59 + ($inspTaskId == null ? 43 : ((Object)$inspTaskId).hashCode());
        String $robotId = this.getRobotId();
        result = result * 59 + ($robotId == null ? 43 : $robotId.hashCode());
        String $type = this.getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        String $point = this.getPoint();
        result = result * 59 + ($point == null ? 43 : $point.hashCode());
        String $abnormal = this.getAbnormal();
        result = result * 59 + ($abnormal == null ? 43 : $abnormal.hashCode());
        String $abnormalInfo = this.getAbnormalInfo();
        result = result * 59 + ($abnormalInfo == null ? 43 : $abnormalInfo.hashCode());
        String $imageBase64 = this.getImageBase64();
        result = result * 59 + ($imageBase64 == null ? 43 : $imageBase64.hashCode());
        String $areaName = this.getAreaName();
        result = result * 59 + ($areaName == null ? 43 : $areaName.hashCode());
        String $region = this.getRegion();
        result = result * 59 + ($region == null ? 43 : $region.hashCode());
        String $coordinate = this.getCoordinate();
        result = result * 59 + ($coordinate == null ? 43 : $coordinate.hashCode());
        return result;
    }

    public String toString() {
        return "InspectionDto(inspTaskId=" + this.getInspTaskId() + ", robotId=" + this.getRobotId() + ", type=" + this.getType() + ", point=" + this.getPoint() + ", abnormal=" + this.getAbnormal() + ", abnormalInfo=" + this.getAbnormalInfo() + ", imageBase64=" + this.getImageBase64() + ", areaName=" + this.getAreaName() + ", region=" + this.getRegion() + ", coordinate=" + this.getCoordinate() + ")";
    }
}
