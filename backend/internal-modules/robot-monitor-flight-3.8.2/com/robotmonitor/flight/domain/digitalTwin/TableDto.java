/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Excel
 */
package com.robotmonitor.flight.domain.digitalTwin;

import com.robotmonitor.common.annotation.Excel;

public class TableDto {
    private Long id;
    @Excel(name="\u684c\u53f7")
    private String tableNo;
    @Excel(name="\u533a\u57dfID")
    private Long regionId;
    @Excel(name="\u662f\u5426\u53ef\u7528")
    private String isEnable = "1";
    @Excel(name="\u6444\u50cf\u5934ID")
    private Long deviceId;
    @Excel(name="\u6444\u50cf\u5934\u5750\u6807")
    private String cameraCoordinates;
    private String areaName;
    private String status;

    public Long getId() {
        return this.id;
    }

    public String getTableNo() {
        return this.tableNo;
    }

    public Long getRegionId() {
        return this.regionId;
    }

    public String getIsEnable() {
        return this.isEnable;
    }

    public Long getDeviceId() {
        return this.deviceId;
    }

    public String getCameraCoordinates() {
        return this.cameraCoordinates;
    }

    public String getAreaName() {
        return this.areaName;
    }

    public String getStatus() {
        return this.status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public void setCameraCoordinates(String cameraCoordinates) {
        this.cameraCoordinates = cameraCoordinates;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TableDto)) {
            return false;
        }
        TableDto other = (TableDto)o;
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
        Long this$deviceId = this.getDeviceId();
        Long other$deviceId = other.getDeviceId();
        if (this$deviceId == null ? other$deviceId != null : !((Object)this$deviceId).equals(other$deviceId)) {
            return false;
        }
        String this$tableNo = this.getTableNo();
        String other$tableNo = other.getTableNo();
        if (this$tableNo == null ? other$tableNo != null : !this$tableNo.equals(other$tableNo)) {
            return false;
        }
        String this$isEnable = this.getIsEnable();
        String other$isEnable = other.getIsEnable();
        if (this$isEnable == null ? other$isEnable != null : !this$isEnable.equals(other$isEnable)) {
            return false;
        }
        String this$cameraCoordinates = this.getCameraCoordinates();
        String other$cameraCoordinates = other.getCameraCoordinates();
        if (this$cameraCoordinates == null ? other$cameraCoordinates != null : !this$cameraCoordinates.equals(other$cameraCoordinates)) {
            return false;
        }
        String this$areaName = this.getAreaName();
        String other$areaName = other.getAreaName();
        if (this$areaName == null ? other$areaName != null : !this$areaName.equals(other$areaName)) {
            return false;
        }
        String this$status = this.getStatus();
        String other$status = other.getStatus();
        return !(this$status == null ? other$status != null : !this$status.equals(other$status));
    }

    protected boolean canEqual(Object other) {
        return other instanceof TableDto;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        Long $regionId = this.getRegionId();
        result = result * 59 + ($regionId == null ? 43 : ((Object)$regionId).hashCode());
        Long $deviceId = this.getDeviceId();
        result = result * 59 + ($deviceId == null ? 43 : ((Object)$deviceId).hashCode());
        String $tableNo = this.getTableNo();
        result = result * 59 + ($tableNo == null ? 43 : $tableNo.hashCode());
        String $isEnable = this.getIsEnable();
        result = result * 59 + ($isEnable == null ? 43 : $isEnable.hashCode());
        String $cameraCoordinates = this.getCameraCoordinates();
        result = result * 59 + ($cameraCoordinates == null ? 43 : $cameraCoordinates.hashCode());
        String $areaName = this.getAreaName();
        result = result * 59 + ($areaName == null ? 43 : $areaName.hashCode());
        String $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        return result;
    }

    public String toString() {
        return "TableDto(id=" + this.getId() + ", tableNo=" + this.getTableNo() + ", regionId=" + this.getRegionId() + ", isEnable=" + this.getIsEnable() + ", deviceId=" + this.getDeviceId() + ", cameraCoordinates=" + this.getCameraCoordinates() + ", areaName=" + this.getAreaName() + ", status=" + this.getStatus() + ")";
    }
}
