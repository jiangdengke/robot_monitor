/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.robotmonitor.config.domain.deepglint.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ListDeviceRequest {
    @JsonProperty(value="Offset")
    private Integer offset = 0;
    @JsonProperty(value="Limit")
    private Integer limit = 16;
    @JsonProperty(value="LogicDeviceIDs")
    private List<String> logicDeviceIds;
    @JsonProperty(value="DeviceNames")
    private List<String> deviceNames;
    @JsonProperty(value="DeviceIDs")
    private List<String> deviceIds;

    public ListDeviceRequest() {
    }

    public ListDeviceRequest(Integer offset, Integer limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public Integer getOffset() {
        return this.offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return this.limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public List<String> getLogicDeviceIds() {
        return this.logicDeviceIds;
    }

    public void setLogicDeviceIds(List<String> logicDeviceIds) {
        this.logicDeviceIds = logicDeviceIds;
    }

    public List<String> getDeviceNames() {
        return this.deviceNames;
    }

    public void setDeviceNames(List<String> deviceNames) {
        this.deviceNames = deviceNames;
    }

    public List<String> getDeviceIds() {
        return this.deviceIds;
    }

    public void setDeviceIds(List<String> deviceIds) {
        this.deviceIds = deviceIds;
    }

    public String toString() {
        return "ListDeviceRequest{offset=" + this.offset + ", limit=" + this.limit + ", logicDeviceIds=" + this.logicDeviceIds + ", deviceNames=" + this.deviceNames + ", deviceIds=" + this.deviceIds + "}";
    }
}
