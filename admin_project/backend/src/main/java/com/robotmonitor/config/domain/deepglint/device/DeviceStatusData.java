/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.robotmonitor.config.domain.deepglint.device;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceStatusData {
    @JsonProperty(value="DeviceID")
    private String deviceId;
    @JsonProperty(value="Status")
    private String status;
    @JsonProperty(value="ConnectTime")
    private String connectTime;
    @JsonProperty(value="LastHeartbeat")
    private String lastHeartbeat;

    public DeviceStatusData() {
    }

    public DeviceStatusData(String deviceId, String status) {
        this.deviceId = deviceId;
        this.status = status;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConnectTime() {
        return this.connectTime;
    }

    public void setConnectTime(String connectTime) {
        this.connectTime = connectTime;
    }

    public String getLastHeartbeat() {
        return this.lastHeartbeat;
    }

    public void setLastHeartbeat(String lastHeartbeat) {
        this.lastHeartbeat = lastHeartbeat;
    }
}
