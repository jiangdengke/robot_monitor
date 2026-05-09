/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.core.domain.config;

import com.robotmonitor.common.annotation.Excel;
import com.robotmonitor.common.core.domain.BaseEntity;
import com.robotmonitor.common.core.domain.config.ConfigRegion;

public class ConfigRobot
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    @Excel(name="\u673a\u5668\u4ebaID")
    private String robotId;
    @Excel(name="\u673a\u5668\u540d\u79f0")
    private String robotName;
    @Excel(name="mac\u5730\u5740")
    private String mac;
    @Excel(name="ip\u5730\u5740")
    private String robotIp;
    @Excel(name="\u5145\u7535\u72b6\u6001 1\uff1a\u5145\u7535 0\uff1a\u4e0d\u5145\u7535")
    private String chargingState;
    @Excel(name="\u5de5\u4f5c\u72b6\u6001")
    private String workingState;
    @Excel(name="\u5f85\u673a\u72b6\u6001")
    private String standbyState;
    @Excel(name="\u5b9a\u4f4d\u72b6\u6001")
    private String positioningState;
    @Excel(name="\u673a\u5668\u4eba\u5f53\u524d\u4f4d\u7f6eid")
    private Long regionId = 0L;
    @Excel(name="\u5f53\u524d\u7535\u91cf")
    private Long batteryState;
    @Excel(name="\u7f51\u7edc\u72b6\u51b5")
    private Long network;
    private String networkStr;
    @Excel(name="\u673a\u5668\u4eba\u5f02\u5e38\u62a5\u8b66")
    private String robotError;
    @Excel(name="\u5f02\u5e38\u62a5\u8b66\u8be6\u60c5")
    private String errorMessages;
    @Excel(name="\u673a\u5668\u4eba\u578b\u53f7")
    private String robotType;
    @Excel(name="\u6240\u5c5e\u516c\u53f8")
    private String belongedCompany;
    @Excel(name="\u673a\u5668\u72b6\u6001 1-\u542f\u7528 0-\u505c\u7528")
    private Long enable;
    @Excel(name="\u8d35\u5bbe\u5ba4CODE")
    private String roomCode;
    private String deptName;
    @Excel(name="\u5f53\u524d\u6b63\u5728\u6267\u884c\u4efb\u52a1\u6216\u4e0a\u4e00\u4e2a\u6267\u884c\u5b8c\u6210\u7684\u4efb\u52a1")
    private Long taskId;
    @Excel(name="\u4efb\u52a1\u72b6\u6001")
    private String taskStatus;
    @Excel(name="\u662f\u5426\u5220\u9664")
    private String isDelete = "0";
    @Excel(name="\u56fe\u7247")
    private String imgIds;
    @Excel(name="\u97f3\u9891")
    private String auditKeys;
    private String employeeNo;
    private String accountId;
    private String oriCoordinate;
    private String adminMode;
    private ConfigRegion region = new ConfigRegion();

    public Long getId() {
        return this.id;
    }

    public String getRobotId() {
        return this.robotId;
    }

    public String getRobotName() {
        return this.robotName;
    }

    public String getMac() {
        return this.mac;
    }

    public String getRobotIp() {
        return this.robotIp;
    }

    public String getChargingState() {
        return this.chargingState;
    }

    public String getWorkingState() {
        return this.workingState;
    }

    public String getStandbyState() {
        return this.standbyState;
    }

    public String getPositioningState() {
        return this.positioningState;
    }

    public Long getRegionId() {
        return this.regionId;
    }

    public Long getBatteryState() {
        return this.batteryState;
    }

    public Long getNetwork() {
        return this.network;
    }

    public String getNetworkStr() {
        return this.networkStr;
    }

    public String getRobotError() {
        return this.robotError;
    }

    public String getErrorMessages() {
        return this.errorMessages;
    }

    public String getRobotType() {
        return this.robotType;
    }

    public String getBelongedCompany() {
        return this.belongedCompany;
    }

    public Long getEnable() {
        return this.enable;
    }

    public String getRoomCode() {
        return this.roomCode;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public Long getTaskId() {
        return this.taskId;
    }

    public String getTaskStatus() {
        return this.taskStatus;
    }

    public String getIsDelete() {
        return this.isDelete;
    }

    public String getImgIds() {
        return this.imgIds;
    }

    public String getAuditKeys() {
        return this.auditKeys;
    }

    public String getEmployeeNo() {
        return this.employeeNo;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public String getOriCoordinate() {
        return this.oriCoordinate;
    }

    public String getAdminMode() {
        return this.adminMode;
    }

    public ConfigRegion getRegion() {
        return this.region;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public void setRobotName(String robotName) {
        this.robotName = robotName;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public void setRobotIp(String robotIp) {
        this.robotIp = robotIp;
    }

    public void setChargingState(String chargingState) {
        this.chargingState = chargingState;
    }

    public void setWorkingState(String workingState) {
        this.workingState = workingState;
    }

    public void setStandbyState(String standbyState) {
        this.standbyState = standbyState;
    }

    public void setPositioningState(String positioningState) {
        this.positioningState = positioningState;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public void setBatteryState(Long batteryState) {
        this.batteryState = batteryState;
    }

    public void setNetwork(Long network) {
        this.network = network;
    }

    public void setNetworkStr(String networkStr) {
        this.networkStr = networkStr;
    }

    public void setRobotError(String robotError) {
        this.robotError = robotError;
    }

    public void setErrorMessages(String errorMessages) {
        this.errorMessages = errorMessages;
    }

    public void setRobotType(String robotType) {
        this.robotType = robotType;
    }

    public void setBelongedCompany(String belongedCompany) {
        this.belongedCompany = belongedCompany;
    }

    public void setEnable(Long enable) {
        this.enable = enable;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public void setImgIds(String imgIds) {
        this.imgIds = imgIds;
    }

    public void setAuditKeys(String auditKeys) {
        this.auditKeys = auditKeys;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setOriCoordinate(String oriCoordinate) {
        this.oriCoordinate = oriCoordinate;
    }

    public void setAdminMode(String adminMode) {
        this.adminMode = adminMode;
    }

    public void setRegion(ConfigRegion region) {
        this.region = region;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ConfigRobot)) {
            return false;
        }
        ConfigRobot other = (ConfigRobot)o;
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
        Long this$batteryState = this.getBatteryState();
        Long other$batteryState = other.getBatteryState();
        if (this$batteryState == null ? other$batteryState != null : !((Object)this$batteryState).equals(other$batteryState)) {
            return false;
        }
        Long this$network = this.getNetwork();
        Long other$network = other.getNetwork();
        if (this$network == null ? other$network != null : !((Object)this$network).equals(other$network)) {
            return false;
        }
        Long this$enable = this.getEnable();
        Long other$enable = other.getEnable();
        if (this$enable == null ? other$enable != null : !((Object)this$enable).equals(other$enable)) {
            return false;
        }
        Long this$taskId = this.getTaskId();
        Long other$taskId = other.getTaskId();
        if (this$taskId == null ? other$taskId != null : !((Object)this$taskId).equals(other$taskId)) {
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
        String this$mac = this.getMac();
        String other$mac = other.getMac();
        if (this$mac == null ? other$mac != null : !this$mac.equals(other$mac)) {
            return false;
        }
        String this$robotIp = this.getRobotIp();
        String other$robotIp = other.getRobotIp();
        if (this$robotIp == null ? other$robotIp != null : !this$robotIp.equals(other$robotIp)) {
            return false;
        }
        String this$chargingState = this.getChargingState();
        String other$chargingState = other.getChargingState();
        if (this$chargingState == null ? other$chargingState != null : !this$chargingState.equals(other$chargingState)) {
            return false;
        }
        String this$workingState = this.getWorkingState();
        String other$workingState = other.getWorkingState();
        if (this$workingState == null ? other$workingState != null : !this$workingState.equals(other$workingState)) {
            return false;
        }
        String this$standbyState = this.getStandbyState();
        String other$standbyState = other.getStandbyState();
        if (this$standbyState == null ? other$standbyState != null : !this$standbyState.equals(other$standbyState)) {
            return false;
        }
        String this$positioningState = this.getPositioningState();
        String other$positioningState = other.getPositioningState();
        if (this$positioningState == null ? other$positioningState != null : !this$positioningState.equals(other$positioningState)) {
            return false;
        }
        String this$networkStr = this.getNetworkStr();
        String other$networkStr = other.getNetworkStr();
        if (this$networkStr == null ? other$networkStr != null : !this$networkStr.equals(other$networkStr)) {
            return false;
        }
        String this$robotError = this.getRobotError();
        String other$robotError = other.getRobotError();
        if (this$robotError == null ? other$robotError != null : !this$robotError.equals(other$robotError)) {
            return false;
        }
        String this$errorMessages = this.getErrorMessages();
        String other$errorMessages = other.getErrorMessages();
        if (this$errorMessages == null ? other$errorMessages != null : !this$errorMessages.equals(other$errorMessages)) {
            return false;
        }
        String this$robotType = this.getRobotType();
        String other$robotType = other.getRobotType();
        if (this$robotType == null ? other$robotType != null : !this$robotType.equals(other$robotType)) {
            return false;
        }
        String this$belongedCompany = this.getBelongedCompany();
        String other$belongedCompany = other.getBelongedCompany();
        if (this$belongedCompany == null ? other$belongedCompany != null : !this$belongedCompany.equals(other$belongedCompany)) {
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
        String this$taskStatus = this.getTaskStatus();
        String other$taskStatus = other.getTaskStatus();
        if (this$taskStatus == null ? other$taskStatus != null : !this$taskStatus.equals(other$taskStatus)) {
            return false;
        }
        String this$isDelete = this.getIsDelete();
        String other$isDelete = other.getIsDelete();
        if (this$isDelete == null ? other$isDelete != null : !this$isDelete.equals(other$isDelete)) {
            return false;
        }
        String this$imgIds = this.getImgIds();
        String other$imgIds = other.getImgIds();
        if (this$imgIds == null ? other$imgIds != null : !this$imgIds.equals(other$imgIds)) {
            return false;
        }
        String this$auditKeys = this.getAuditKeys();
        String other$auditKeys = other.getAuditKeys();
        if (this$auditKeys == null ? other$auditKeys != null : !this$auditKeys.equals(other$auditKeys)) {
            return false;
        }
        String this$employeeNo = this.getEmployeeNo();
        String other$employeeNo = other.getEmployeeNo();
        if (this$employeeNo == null ? other$employeeNo != null : !this$employeeNo.equals(other$employeeNo)) {
            return false;
        }
        String this$accountId = this.getAccountId();
        String other$accountId = other.getAccountId();
        if (this$accountId == null ? other$accountId != null : !this$accountId.equals(other$accountId)) {
            return false;
        }
        String this$oriCoordinate = this.getOriCoordinate();
        String other$oriCoordinate = other.getOriCoordinate();
        if (this$oriCoordinate == null ? other$oriCoordinate != null : !this$oriCoordinate.equals(other$oriCoordinate)) {
            return false;
        }
        String this$adminMode = this.getAdminMode();
        String other$adminMode = other.getAdminMode();
        if (this$adminMode == null ? other$adminMode != null : !this$adminMode.equals(other$adminMode)) {
            return false;
        }
        ConfigRegion this$region = this.getRegion();
        ConfigRegion other$region = other.getRegion();
        return !(this$region == null ? other$region != null : !((Object)this$region).equals(other$region));
    }

    protected boolean canEqual(Object other) {
        return other instanceof ConfigRobot;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        Long $regionId = this.getRegionId();
        result = result * 59 + ($regionId == null ? 43 : ((Object)$regionId).hashCode());
        Long $batteryState = this.getBatteryState();
        result = result * 59 + ($batteryState == null ? 43 : ((Object)$batteryState).hashCode());
        Long $network = this.getNetwork();
        result = result * 59 + ($network == null ? 43 : ((Object)$network).hashCode());
        Long $enable = this.getEnable();
        result = result * 59 + ($enable == null ? 43 : ((Object)$enable).hashCode());
        Long $taskId = this.getTaskId();
        result = result * 59 + ($taskId == null ? 43 : ((Object)$taskId).hashCode());
        String $robotId = this.getRobotId();
        result = result * 59 + ($robotId == null ? 43 : $robotId.hashCode());
        String $robotName = this.getRobotName();
        result = result * 59 + ($robotName == null ? 43 : $robotName.hashCode());
        String $mac = this.getMac();
        result = result * 59 + ($mac == null ? 43 : $mac.hashCode());
        String $robotIp = this.getRobotIp();
        result = result * 59 + ($robotIp == null ? 43 : $robotIp.hashCode());
        String $chargingState = this.getChargingState();
        result = result * 59 + ($chargingState == null ? 43 : $chargingState.hashCode());
        String $workingState = this.getWorkingState();
        result = result * 59 + ($workingState == null ? 43 : $workingState.hashCode());
        String $standbyState = this.getStandbyState();
        result = result * 59 + ($standbyState == null ? 43 : $standbyState.hashCode());
        String $positioningState = this.getPositioningState();
        result = result * 59 + ($positioningState == null ? 43 : $positioningState.hashCode());
        String $networkStr = this.getNetworkStr();
        result = result * 59 + ($networkStr == null ? 43 : $networkStr.hashCode());
        String $robotError = this.getRobotError();
        result = result * 59 + ($robotError == null ? 43 : $robotError.hashCode());
        String $errorMessages = this.getErrorMessages();
        result = result * 59 + ($errorMessages == null ? 43 : $errorMessages.hashCode());
        String $robotType = this.getRobotType();
        result = result * 59 + ($robotType == null ? 43 : $robotType.hashCode());
        String $belongedCompany = this.getBelongedCompany();
        result = result * 59 + ($belongedCompany == null ? 43 : $belongedCompany.hashCode());
        String $roomCode = this.getRoomCode();
        result = result * 59 + ($roomCode == null ? 43 : $roomCode.hashCode());
        String $deptName = this.getDeptName();
        result = result * 59 + ($deptName == null ? 43 : $deptName.hashCode());
        String $taskStatus = this.getTaskStatus();
        result = result * 59 + ($taskStatus == null ? 43 : $taskStatus.hashCode());
        String $isDelete = this.getIsDelete();
        result = result * 59 + ($isDelete == null ? 43 : $isDelete.hashCode());
        String $imgIds = this.getImgIds();
        result = result * 59 + ($imgIds == null ? 43 : $imgIds.hashCode());
        String $auditKeys = this.getAuditKeys();
        result = result * 59 + ($auditKeys == null ? 43 : $auditKeys.hashCode());
        String $employeeNo = this.getEmployeeNo();
        result = result * 59 + ($employeeNo == null ? 43 : $employeeNo.hashCode());
        String $accountId = this.getAccountId();
        result = result * 59 + ($accountId == null ? 43 : $accountId.hashCode());
        String $oriCoordinate = this.getOriCoordinate();
        result = result * 59 + ($oriCoordinate == null ? 43 : $oriCoordinate.hashCode());
        String $adminMode = this.getAdminMode();
        result = result * 59 + ($adminMode == null ? 43 : $adminMode.hashCode());
        ConfigRegion $region = this.getRegion();
        result = result * 59 + ($region == null ? 43 : ((Object)$region).hashCode());
        return result;
    }

    public String toString() {
        return "ConfigRobot(id=" + this.getId() + ", robotId=" + this.getRobotId() + ", robotName=" + this.getRobotName() + ", mac=" + this.getMac() + ", robotIp=" + this.getRobotIp() + ", chargingState=" + this.getChargingState() + ", workingState=" + this.getWorkingState() + ", standbyState=" + this.getStandbyState() + ", positioningState=" + this.getPositioningState() + ", regionId=" + this.getRegionId() + ", batteryState=" + this.getBatteryState() + ", network=" + this.getNetwork() + ", networkStr=" + this.getNetworkStr() + ", robotError=" + this.getRobotError() + ", errorMessages=" + this.getErrorMessages() + ", robotType=" + this.getRobotType() + ", belongedCompany=" + this.getBelongedCompany() + ", enable=" + this.getEnable() + ", roomCode=" + this.getRoomCode() + ", deptName=" + this.getDeptName() + ", taskId=" + this.getTaskId() + ", taskStatus=" + this.getTaskStatus() + ", isDelete=" + this.getIsDelete() + ", imgIds=" + this.getImgIds() + ", auditKeys=" + this.getAuditKeys() + ", employeeNo=" + this.getEmployeeNo() + ", accountId=" + this.getAccountId() + ", oriCoordinate=" + this.getOriCoordinate() + ", adminMode=" + this.getAdminMode() + ", region=" + this.getRegion() + ")";
    }
}
