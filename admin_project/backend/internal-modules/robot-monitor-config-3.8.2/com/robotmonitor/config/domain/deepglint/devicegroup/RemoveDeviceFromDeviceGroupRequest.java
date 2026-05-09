/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.robotmonitor.config.domain.deepglint.devicegroup;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RemoveDeviceFromDeviceGroupRequest {
    @JsonProperty(value="LogicDeviceId")
    private String logicDeviceId;

    public RemoveDeviceFromDeviceGroupRequest() {
    }

    public RemoveDeviceFromDeviceGroupRequest(String logicDeviceId) {
        this.logicDeviceId = logicDeviceId;
    }

    public String getLogicDeviceId() {
        return this.logicDeviceId;
    }

    public void setLogicDeviceId(String logicDeviceId) {
        this.logicDeviceId = logicDeviceId;
    }

    public String toString() {
        return "RemoveDeviceFromDeviceGroupRequest{logicDeviceId='" + this.logicDeviceId + "'}";
    }
}
