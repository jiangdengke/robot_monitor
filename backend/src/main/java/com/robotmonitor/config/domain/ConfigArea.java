/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Excel
 *  com.robotmonitor.common.core.domain.BaseEntity
 *  org.springframework.data.annotation.Id
 */
package com.robotmonitor.config.domain;

import com.robotmonitor.common.annotation.Excel;
import com.robotmonitor.common.core.domain.BaseEntity;
import com.robotmonitor.config.domain.ConfigAreaDetail;
import java.util.List;
import org.springframework.data.annotation.Id;

public class ConfigArea
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @Excel(name="\u8d35\u5bbe\u5ba4CODE")
    private String roomCode;
    private String deptName;
    @Excel(name="\u56fe\u7247ID")
    private String imgIds;
    @Excel(name="\u662f\u5426\u5c55\u793a")
    private String isShow;
    @Excel(name="\u662f\u5426\u652f\u6301\u5f15\u5bfc")
    private String isGuide;
    @Excel(name="\u6700\u5927\u5bb9\u91cf")
    private Long maxCapacity;
    @Excel(name="\u5750\u6807")
    private String coordinate;
    private Long curCapacity = Long.getLong("0");
    private double perCapacity = 0.0;
    private String areaName;
    private List<String> imgUrlList;
    private List<ConfigAreaDetail> configAreaDetailList;

    public Long getId() {
        return this.id;
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

    public String getIsShow() {
        return this.isShow;
    }

    public String getIsGuide() {
        return this.isGuide;
    }

    public Long getMaxCapacity() {
        return this.maxCapacity;
    }

    public String getCoordinate() {
        return this.coordinate;
    }

    public Long getCurCapacity() {
        return this.curCapacity;
    }

    public double getPerCapacity() {
        return this.perCapacity;
    }

    public String getAreaName() {
        return this.areaName;
    }

    public List<String> getImgUrlList() {
        return this.imgUrlList;
    }

    public List<ConfigAreaDetail> getConfigAreaDetailList() {
        return this.configAreaDetailList;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public void setIsGuide(String isGuide) {
        this.isGuide = isGuide;
    }

    public void setMaxCapacity(Long maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public void setCurCapacity(Long curCapacity) {
        this.curCapacity = curCapacity;
    }

    public void setPerCapacity(double perCapacity) {
        this.perCapacity = perCapacity;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public void setImgUrlList(List<String> imgUrlList) {
        this.imgUrlList = imgUrlList;
    }

    public void setConfigAreaDetailList(List<ConfigAreaDetail> configAreaDetailList) {
        this.configAreaDetailList = configAreaDetailList;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ConfigArea)) {
            return false;
        }
        ConfigArea other = (ConfigArea)((Object)o);
        if (!other.canEqual((Object)this)) {
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
        String this$isShow = this.getIsShow();
        String other$isShow = other.getIsShow();
        if (this$isShow == null ? other$isShow != null : !this$isShow.equals(other$isShow)) {
            return false;
        }
        String this$isGuide = this.getIsGuide();
        String other$isGuide = other.getIsGuide();
        if (this$isGuide == null ? other$isGuide != null : !this$isGuide.equals(other$isGuide)) {
            return false;
        }
        String this$coordinate = this.getCoordinate();
        String other$coordinate = other.getCoordinate();
        if (this$coordinate == null ? other$coordinate != null : !this$coordinate.equals(other$coordinate)) {
            return false;
        }
        String this$areaName = this.getAreaName();
        String other$areaName = other.getAreaName();
        if (this$areaName == null ? other$areaName != null : !this$areaName.equals(other$areaName)) {
            return false;
        }
        List<String> this$imgUrlList = this.getImgUrlList();
        List<String> other$imgUrlList = other.getImgUrlList();
        if (this$imgUrlList == null ? other$imgUrlList != null : !((Object)this$imgUrlList).equals(other$imgUrlList)) {
            return false;
        }
        List<ConfigAreaDetail> this$configAreaDetailList = this.getConfigAreaDetailList();
        List<ConfigAreaDetail> other$configAreaDetailList = other.getConfigAreaDetailList();
        return !(this$configAreaDetailList == null ? other$configAreaDetailList != null : !((Object)this$configAreaDetailList).equals(other$configAreaDetailList));
    }

    protected boolean canEqual(Object other) {
        return other instanceof ConfigArea;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $perCapacity = Double.doubleToLongBits(this.getPerCapacity());
        result = result * 59 + (int)($perCapacity >>> 32 ^ $perCapacity);
        Long $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        Long $maxCapacity = this.getMaxCapacity();
        result = result * 59 + ($maxCapacity == null ? 43 : ((Object)$maxCapacity).hashCode());
        Long $curCapacity = this.getCurCapacity();
        result = result * 59 + ($curCapacity == null ? 43 : ((Object)$curCapacity).hashCode());
        String $roomCode = this.getRoomCode();
        result = result * 59 + ($roomCode == null ? 43 : $roomCode.hashCode());
        String $deptName = this.getDeptName();
        result = result * 59 + ($deptName == null ? 43 : $deptName.hashCode());
        String $imgIds = this.getImgIds();
        result = result * 59 + ($imgIds == null ? 43 : $imgIds.hashCode());
        String $isShow = this.getIsShow();
        result = result * 59 + ($isShow == null ? 43 : $isShow.hashCode());
        String $isGuide = this.getIsGuide();
        result = result * 59 + ($isGuide == null ? 43 : $isGuide.hashCode());
        String $coordinate = this.getCoordinate();
        result = result * 59 + ($coordinate == null ? 43 : $coordinate.hashCode());
        String $areaName = this.getAreaName();
        result = result * 59 + ($areaName == null ? 43 : $areaName.hashCode());
        List<String> $imgUrlList = this.getImgUrlList();
        result = result * 59 + ($imgUrlList == null ? 43 : ((Object)$imgUrlList).hashCode());
        List<ConfigAreaDetail> $configAreaDetailList = this.getConfigAreaDetailList();
        result = result * 59 + ($configAreaDetailList == null ? 43 : ((Object)$configAreaDetailList).hashCode());
        return result;
    }

    public String toString() {
        return "ConfigArea(id=" + this.getId() + ", roomCode=" + this.getRoomCode() + ", deptName=" + this.getDeptName() + ", imgIds=" + this.getImgIds() + ", isShow=" + this.getIsShow() + ", isGuide=" + this.getIsGuide() + ", maxCapacity=" + this.getMaxCapacity() + ", coordinate=" + this.getCoordinate() + ", curCapacity=" + this.getCurCapacity() + ", perCapacity=" + this.getPerCapacity() + ", areaName=" + this.getAreaName() + ", imgUrlList=" + this.getImgUrlList() + ", configAreaDetailList=" + this.getConfigAreaDetailList() + ")";
    }
}
