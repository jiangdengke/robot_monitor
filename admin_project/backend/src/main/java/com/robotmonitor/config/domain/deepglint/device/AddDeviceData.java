/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.robotmonitor.config.domain.deepglint.device;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddDeviceData {
    @JsonProperty(value="Cts")
    private Long cts;
    @JsonProperty(value="Uts")
    private Long uts;
    @JsonProperty(value="GroupID")
    private String groupId;
    @JsonProperty(value="DeviceType")
    private String deviceType;
    @JsonProperty(value="LogicDeviceID")
    private String logicDeviceId;
    @JsonProperty(value="DeviceID")
    private String deviceId;
    @JsonProperty(value="DeviceName")
    private String deviceName;
    @JsonProperty(value="Comment")
    private String comment;

    public AddDeviceData() {
    }

    public AddDeviceData(Long cts, Long uts, String groupId, String deviceType, String logicDeviceId, String deviceId, String deviceName, String comment) {
        this.cts = cts;
        this.uts = uts;
        this.groupId = groupId;
        this.deviceType = deviceType;
        this.logicDeviceId = logicDeviceId;
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.comment = comment;
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
