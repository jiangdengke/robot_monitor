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

public class ConfigDevice
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    @Excel(name="\u8bbe\u5907\u540d\u79f0")
    private String deviceName;
    @Excel(name="${comment}", readConverterExp="$column.readConverterExp()")
    private String deviceType;
    @Excel(name="\u72b6\u6001 1-\u542f\u7528 0-\u505c\u7528")
    private Long enable;
    @Excel(name="\u8d35\u5bbe\u5ba4CODE")
    private String roomCode;
    private String deptName;
    @Excel(name="\u662f\u5426\u5220\u9664")
    private String isDelete;
    @Excel(name="\u683c\u7075\u6444\u50cf\u5934ID")
    private String deepGlintDeviceId;

    public Long getId() {
        return this.id;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public String getDeviceType() {
        return this.deviceType;
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

    public String getIsDelete() {
        return this.isDelete;
    }

    public String getDeepGlintDeviceId() {
        return this.deepGlintDeviceId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
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

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public void setDeepGlintDeviceId(String deepGlintDeviceId) {
        this.deepGlintDeviceId = deepGlintDeviceId;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ConfigDevice)) {
            return false;
        }
        ConfigDevice other = (ConfigDevice)((Object)o);
        if (!other.canEqual((Object)this)) {
            return false;
        }
        Long this$id = this.getId();
        Long other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        Long this$enable = this.getEnable();
        Long other$enable = other.getEnable();
        if (this$enable == null ? other$enable != null : !((Object)this$enable).equals(other$enable)) {
            return false;
        }
        String this$deviceName = this.getDeviceName();
        String other$deviceName = other.getDeviceName();
        if (this$deviceName == null ? other$deviceName != null : !this$deviceName.equals(other$deviceName)) {
            return false;
        }
        String this$deviceType = this.getDeviceType();
        String other$deviceType = other.getDeviceType();
        if (this$deviceType == null ? other$deviceType != null : !this$deviceType.equals(other$deviceType)) {
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
        String this$isDelete = this.getIsDelete();
        String other$isDelete = other.getIsDelete();
        if (this$isDelete == null ? other$isDelete != null : !this$isDelete.equals(other$isDelete)) {
            return false;
        }
        String this$deepGlintDeviceId = this.getDeepGlintDeviceId();
        String other$deepGlintDeviceId = other.getDeepGlintDeviceId();
        return !(this$deepGlintDeviceId == null ? other$deepGlintDeviceId != null : !this$deepGlintDeviceId.equals(other$deepGlintDeviceId));
    }

    protected boolean canEqual(Object other) {
        return other instanceof ConfigDevice;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        Long $enable = this.getEnable();
        result = result * 59 + ($enable == null ? 43 : ((Object)$enable).hashCode());
        String $deviceName = this.getDeviceName();
        result = result * 59 + ($deviceName == null ? 43 : $deviceName.hashCode());
        String $deviceType = this.getDeviceType();
        result = result * 59 + ($deviceType == null ? 43 : $deviceType.hashCode());
        String $roomCode = this.getRoomCode();
        result = result * 59 + ($roomCode == null ? 43 : $roomCode.hashCode());
        String $deptName = this.getDeptName();
        result = result * 59 + ($deptName == null ? 43 : $deptName.hashCode());
        String $isDelete = this.getIsDelete();
        result = result * 59 + ($isDelete == null ? 43 : $isDelete.hashCode());
        String $deepGlintDeviceId = this.getDeepGlintDeviceId();
        result = result * 59 + ($deepGlintDeviceId == null ? 43 : $deepGlintDeviceId.hashCode());
        return result;
    }

    public String toString() {
        return "ConfigDevice(id=" + this.getId() + ", deviceName=" + this.getDeviceName() + ", deviceType=" + this.getDeviceType() + ", enable=" + this.getEnable() + ", roomCode=" + this.getRoomCode() + ", deptName=" + this.getDeptName() + ", isDelete=" + this.getIsDelete() + ", deepGlintDeviceId=" + this.getDeepGlintDeviceId() + ")";
    }
}
