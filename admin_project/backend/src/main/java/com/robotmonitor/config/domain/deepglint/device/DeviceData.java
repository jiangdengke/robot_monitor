/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.robotmonitor.config.domain.deepglint.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class DeviceData {
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
    @JsonProperty(value="Options")
    private Map<String, Object> options;
    @JsonProperty(value="Comment")
    private String comment;
    @JsonProperty(value="LastTimeDetectFace")
    private Integer lastTimeDetectFace;
    @JsonProperty(value="LastEndTime")
    private Integer lastEndTime;
    @JsonProperty(value="Online")
    private Integer online;
    @JsonProperty(value="OnlineTime")
    private Integer onlineTime;
    @JsonProperty(value="LastSyncConfigTime")
    private Integer lastSyncConfigTime;

    public DeviceData() {
    }

    public DeviceData(String logicDeviceId, String deviceName) {
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

    public Map<String, Object> getOptions() {
        return this.options;
    }

    public void setOptions(Map<String, Object> options) {
        this.options = options;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getLastTimeDetectFace() {
        return this.lastTimeDetectFace;
    }

    public void setLastTimeDetectFace(Integer lastTimeDetectFace) {
        this.lastTimeDetectFace = lastTimeDetectFace;
    }

    public Integer getLastEndTime() {
        return this.lastEndTime;
    }

    public void setLastEndTime(Integer lastEndTime) {
        this.lastEndTime = lastEndTime;
    }

    public Integer getOnline() {
        return this.online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public Integer getOnlineTime() {
        return this.onlineTime;
    }

    public void setOnlineTime(Integer onlineTime) {
        this.onlineTime = onlineTime;
    }

    public Integer getLastSyncConfigTime() {
        return this.lastSyncConfigTime;
    }

    public void setLastSyncConfigTime(Integer lastSyncConfigTime) {
        this.lastSyncConfigTime = lastSyncConfigTime;
    }
}
