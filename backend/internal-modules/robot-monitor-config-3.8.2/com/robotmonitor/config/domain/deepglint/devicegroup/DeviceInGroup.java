/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.config.domain.deepglint.devicegroup;

public class DeviceInGroup {
    private Long cts;
    private Long uts;
    private String groupId;
    private String deviceType;
    private String logicDeviceId;
    private String deviceId;
    private String deviceName;
    private String comment;

    public DeviceInGroup() {
    }

    public DeviceInGroup(String logicDeviceId, String deviceName) {
        this.logicDeviceId = logicDeviceId;
        this.deviceName = deviceName;
    }

    public Long getCts() {
        return this.cts;
    }

    public void setCts(Long cts) {
        this.cts = cts;
    }

    public Long getUts() {
        return this.uts;
    }

    public void setUts(Long uts) {
        this.uts = uts;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getLogicDeviceId() {
        return this.logicDeviceId;
    }

    public void setLogicDeviceId(String logicDeviceId) {
        this.logicDeviceId = logicDeviceId;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
