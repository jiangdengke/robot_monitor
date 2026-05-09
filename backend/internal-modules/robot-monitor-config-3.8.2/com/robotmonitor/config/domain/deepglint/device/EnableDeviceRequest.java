/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.robotmonitor.config.domain.deepglint.device;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnableDeviceRequest {
    @JsonProperty(value="DeviceId")
    private String deviceId;
    @JsonProperty(value="Enable")
    private Boolean enable;

    public EnableDeviceRequest() {
    }

    public EnableDeviceRequest(String deviceId, Boolean enable) {
        this.deviceId = deviceId;
        this.enable = enable;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Boolean getEnable() {
        return this.enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String toString() {
        return "EnableDeviceRequest{deviceId='" + this.deviceId + "', enable=" + this.enable + "}";
    }
}
