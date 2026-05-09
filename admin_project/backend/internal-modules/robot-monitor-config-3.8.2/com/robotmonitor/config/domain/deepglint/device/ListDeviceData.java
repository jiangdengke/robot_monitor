/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.robotmonitor.config.domain.deepglint.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.robotmonitor.config.domain.deepglint.device.DeviceData;
import java.util.List;

public class ListDeviceData {
    @JsonProperty(value="Count")
    private Integer count;
    @JsonProperty(value="Devices")
    private List<DeviceData> devices;

    public ListDeviceData() {
    }

    public ListDeviceData(Integer count, List<DeviceData> devices) {
        this.count = count;
        this.devices = devices;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<DeviceData> getDevices() {
        return this.devices;
    }

    public void setDevices(List<DeviceData> devices) {
        this.devices = devices;
    }
}
