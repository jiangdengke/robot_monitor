/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Excel
 *  com.robotmonitor.common.core.domain.BaseEntity
 */
package com.robotmonitor.config.domain;

import com.robotmonitor.common.annotation.Excel;
import com.robotmonitor.common.core.domain.BaseEntity;

public class ConfigDeviceRegion
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long deviceId;
    private Long regionId;
    @Excel(name="\u56fe\u7247")
    private Long imgId;
    @Excel(name="\u5750\u6807")
    private String coordinate;
    private String regionName;
    private String remark;

    public Long getDeviceId() {
        return this.deviceId;
    }

    public Long getRegionId() {
        return this.regionId;
    }

    public Long getImgId() {
        return this.imgId;
    }

    public String getCoordinate() {
        return this.coordinate;
    }

    public String getRegionName() {
        return this.regionName;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public void setImgId(Long imgId) {
        this.imgId = imgId;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
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
        if (!(o instanceof ConfigDeviceRegion)) {
            return false;
        }
        ConfigDeviceRegion other = (ConfigDeviceRegion)((Object)o);
        if (!other.canEqual((Object)this)) {
            return false;
        }
        Long this$deviceId = this.getDeviceId();
        Long other$deviceId = other.getDeviceId();
        if (this$deviceId == null ? other$deviceId != null : !((Object)this$deviceId).equals(other$deviceId)) {
            return false;
        }
        Long this$regionId = this.getRegionId();
        Long other$regionId = other.getRegionId();
        if (this$regionId == null ? other$regionId != null : !((Object)this$regionId).equals(other$regionId)) {
            return false;
        }
        Long this$imgId = this.getImgId();
        Long other$imgId = other.getImgId();
        if (this$imgId == null ? other$imgId != null : !((Object)this$imgId).equals(other$imgId)) {
            return false;
        }
        String this$coordinate = this.getCoordinate();
        String other$coordinate = other.getCoordinate();
        if (this$coordinate == null ? other$coordinate != null : !this$coordinate.equals(other$coordinate)) {
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
        return other instanceof ConfigDeviceRegion;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $deviceId = this.getDeviceId();
        result = result * 59 + ($deviceId == null ? 43 : ((Object)$deviceId).hashCode());
        Long $regionId = this.getRegionId();
        result = result * 59 + ($regionId == null ? 43 : ((Object)$regionId).hashCode());
        Long $imgId = this.getImgId();
        result = result * 59 + ($imgId == null ? 43 : ((Object)$imgId).hashCode());
        String $coordinate = this.getCoordinate();
        result = result * 59 + ($coordinate == null ? 43 : $coordinate.hashCode());
        String $regionName = this.getRegionName();
        result = result * 59 + ($regionName == null ? 43 : $regionName.hashCode());
        String $remark = this.getRemark();
        result = result * 59 + ($remark == null ? 43 : $remark.hashCode());
        return result;
    }

    public String toString() {
        return "ConfigDeviceRegion(deviceId=" + this.getDeviceId() + ", regionId=" + this.getRegionId() + ", imgId=" + this.getImgId() + ", coordinate=" + this.getCoordinate() + ", regionName=" + this.getRegionName() + ", remark=" + this.getRemark() + ")";
    }
}
