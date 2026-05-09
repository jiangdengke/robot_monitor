/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.robotmonitor.config.domain.deepglint.device;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateDeviceRequest {
    @JsonProperty(value="DeviceId")
    private String deviceId;
    @JsonProperty(value="DeviceName")
    private String deviceName;
    @JsonProperty(value="DeviceType")
    private String deviceType;
    @JsonProperty(value="DeviceIp")
    private String deviceIp;
    @JsonProperty(value="Manufacturer")
    private String manufacturer;
    @JsonProperty(value="Model")
    private String model;
    @JsonProperty(value="FirmwareVersion")
    private String firmwareVersion;
    @JsonProperty(value="Location")
    private String location;
    @JsonProperty(value="Description")
    private String description;

    public UpdateDeviceRequest() {
    }

    public UpdateDeviceRequest(String deviceId, String deviceName) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
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

    public String getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceIp() {
        return this.deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFirmwareVersion() {
        return this.firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return "UpdateDeviceRequest{deviceId='" + this.deviceId + "', deviceName='" + this.deviceName + "', deviceType='" + this.deviceType + "', deviceIp='" + this.deviceIp + "', manufacturer='" + this.manufacturer + "', model='" + this.model + "', firmwareVersion='" + this.firmwareVersion + "', location='" + this.location + "', description='" + this.description + "'}";
    }
}
