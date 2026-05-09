/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.core.domain.config;

import com.robotmonitor.common.annotation.Excel;
import com.robotmonitor.common.core.domain.BaseEntity;

public class ConfigRegion
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    @Excel(name="\u533a\u57df\u540d\u79f0")
    private String regionName;
    @Excel(name="\u5750\u6807")
    private String coordinate;
    @Excel(name="\u70b9\u4f4d\u72b6\u6001 1-\u542f\u7528 0-\u505c\u7528")
    private int enable;
    @Excel(name="\u8d35\u5bbe\u5ba4CODE")
    private String roomCode;
    private String deptName;
    @Excel(name="\u56fe\u7247ID")
    private String imgIds;
    @Excel(name="\u97f3\u9891key")
    private String audioKeys;
    @Excel(name="\u662f\u5426\u652f\u6301\u5f15\u5bfc")
    private String isGuide;
    @Excel(name="\u662f\u5426\u5c55\u793a")
    private String isShow;
    private Long maxCapacity = Long.getLong("0");
    private Long curCapacity = Long.getLong("0");
    private double perCapacity = 0.0;
    private Long areaId;
    private String areaName;

    public Long getId() {
        return this.id;
    }

    public String getRegionName() {
        return this.regionName;
    }

    public String getCoordinate() {
        return this.coordinate;
    }

    public int getEnable() {
        return this.enable;
    }

    public String getRoomCode() {
        return this.roomCode;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public String getImgIds() {
        return this.imgIds;
    }

    public String getAudioKeys() {
        return this.audioKeys;
    }

    public String getIsGuide() {
        return this.isGuide;
    }

    public String getIsShow() {
        return this.isShow;
    }

    public Long getMaxCapacity() {
        return this.maxCapacity;
    }

    public Long getCurCapacity() {
        return this.curCapacity;
    }

    public double getPerCapacity() {
        return this.perCapacity;
    }

    public Long getAreaId() {
        return this.areaId;
    }

    public String getAreaName() {
        return this.areaName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setImgIds(String imgIds) {
        this.imgIds = imgIds;
    }

    public void setAudioKeys(String audioKeys) {
        this.audioKeys = audioKeys;
    }

    public void setIsGuide(String isGuide) {
        this.isGuide = isGuide;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public void setMaxCapacity(Long maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void setCurCapacity(Long curCapacity) {
        this.curCapacity = curCapacity;
    }

    public void setPerCapacity(double perCapacity) {
        this.perCapacity = perCapacity;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ConfigRegion)) {
            return false;
        }
        ConfigRegion other = (ConfigRegion)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getEnable() != other.getEnable()) {
            return false;
        }
        if (Double.compare(this.getPerCapacity(), other.getPerCapacity()) != 0) {
            return false;
        }
        Long this$id = this.getId();
        Long other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        Long this$maxCapacity = this.getMaxCapacity();
        Long other$maxCapacity = other.getMaxCapacity();
        if (this$maxCapacity == null ? other$maxCapacity != null : !((Object)this$maxCapacity).equals(other$maxCapacity)) {
            return false;
        }
        Long this$curCapacity = this.getCurCapacity();
        Long other$curCapacity = other.getCurCapacity();
        if (this$curCapacity == null ? other$curCapacity != null : !((Object)this$curCapacity).equals(other$curCapacity)) {
            return false;
        }
        Long this$areaId = this.getAreaId();
        Long other$areaId = other.getAreaId();
        if (this$areaId == null ? other$areaId != null : !((Object)this$areaId).equals(other$areaId)) {
            return false;
        }
        String this$regionName = this.getRegionName();
        String other$regionName = other.getRegionName();
        if (this$regionName == null ? other$regionName != null : !this$regionName.equals(other$regionName)) {
            return false;
        }
        String this$coordinate = this.getCoordinate();
        String other$coordinate = other.getCoordinate();
        if (this$coordinate == null ? other$coordinate != null : !this$coordinate.equals(other$coordinate)) {
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
        String this$imgIds = this.getImgIds();
        String other$imgIds = other.getImgIds();
        if (this$imgIds == null ? other$imgIds != null : !this$imgIds.equals(other$imgIds)) {
            return false;
        }
        String this$audioKeys = this.getAudioKeys();
        String other$audioKeys = other.getAudioKeys();
        if (this$audioKeys == null ? other$audioKeys != null : !this$audioKeys.equals(other$audioKeys)) {
            return false;
        }
        String this$isGuide = this.getIsGuide();
        String other$isGuide = other.getIsGuide();
        if (this$isGuide == null ? other$isGuide != null : !this$isGuide.equals(other$isGuide)) {
            return false;
        }
        String this$isShow = this.getIsShow();
        String other$isShow = other.getIsShow();
        if (this$isShow == null ? other$isShow != null : !this$isShow.equals(other$isShow)) {
            return false;
        }
        String this$areaName = this.getAreaName();
        String other$areaName = other.getAreaName();
        return !(this$areaName == null ? other$areaName != null : !this$areaName.equals(other$areaName));
    }

    protected boolean canEqual(Object other) {
        return other instanceof ConfigRegion;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getEnable();
        long $perCapacity = Double.doubleToLongBits(this.getPerCapacity());
        result = result * 59 + (int)($perCapacity >>> 32 ^ $perCapacity);
        Long $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        Long $maxCapacity = this.getMaxCapacity();
        result = result * 59 + ($maxCapacity == null ? 43 : ((Object)$maxCapacity).hashCode());
        Long $curCapacity = this.getCurCapacity();
        result = result * 59 + ($curCapacity == null ? 43 : ((Object)$curCapacity).hashCode());
        Long $areaId = this.getAreaId();
        result = result * 59 + ($areaId == null ? 43 : ((Object)$areaId).hashCode());
        String $regionName = this.getRegionName();
        result = result * 59 + ($regionName == null ? 43 : $regionName.hashCode());
        String $coordinate = this.getCoordinate();
        result = result * 59 + ($coordinate == null ? 43 : $coordinate.hashCode());
        String $roomCode = this.getRoomCode();
        result = result * 59 + ($roomCode == null ? 43 : $roomCode.hashCode());
        String $deptName = this.getDeptName();
        result = result * 59 + ($deptName == null ? 43 : $deptName.hashCode());
        String $imgIds = this.getImgIds();
        result = result * 59 + ($imgIds == null ? 43 : $imgIds.hashCode());
        String $audioKeys = this.getAudioKeys();
        result = result * 59 + ($audioKeys == null ? 43 : $audioKeys.hashCode());
        String $isGuide = this.getIsGuide();
        result = result * 59 + ($isGuide == null ? 43 : $isGuide.hashCode());
        String $isShow = this.getIsShow();
        result = result * 59 + ($isShow == null ? 43 : $isShow.hashCode());
        String $areaName = this.getAreaName();
        result = result * 59 + ($areaName == null ? 43 : $areaName.hashCode());
        return result;
    }

    public String toString() {
        return "ConfigRegion(id=" + this.getId() + ", regionName=" + this.getRegionName() + ", coordinate=" + this.getCoordinate() + ", enable=" + this.getEnable() + ", roomCode=" + this.getRoomCode() + ", deptName=" + this.getDeptName() + ", imgIds=" + this.getImgIds() + ", audioKeys=" + this.getAudioKeys() + ", isGuide=" + this.getIsGuide() + ", isShow=" + this.getIsShow() + ", maxCapacity=" + this.getMaxCapacity() + ", curCapacity=" + this.getCurCapacity() + ", perCapacity=" + this.getPerCapacity() + ", areaId=" + this.getAreaId() + ", areaName=" + this.getAreaName() + ")";
    }
}
