/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.robotmonitor.config.domain.deepglint.device;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteDeviceRequest {
    @JsonProperty(value="DeviceId")
    private String deviceId;

    public DeleteDeviceRequest() {
    }

    public DeleteDeviceRequest(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String toString() {
        return "DeleteDeviceRequest{deviceId='" + this.deviceId + "'}";
    }
}
