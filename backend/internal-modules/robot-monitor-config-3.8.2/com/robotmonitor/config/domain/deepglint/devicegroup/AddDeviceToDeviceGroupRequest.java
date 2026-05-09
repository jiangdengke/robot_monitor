/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.robotmonitor.config.domain.deepglint.devicegroup;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddDeviceToDeviceGroupRequest {
    @JsonProperty(value="GroupId")
    private String groupId;
    @JsonProperty(value="LogicDeviceId")
    private String logicDeviceId;

    public AddDeviceToDeviceGroupRequest() {
    }

    public AddDeviceToDeviceGroupRequest(String groupId, String logicDeviceId) {
        this.groupId = groupId;
        this.logicDeviceId = logicDeviceId;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getLogicDeviceId() {
        return this.logicDeviceId;
    }

    public void setLogicDeviceId(String logicDeviceId) {
        this.logicDeviceId = logicDeviceId;
    }

    public String toString() {
        return "AddDeviceToDeviceGroupRequest{groupId='" + this.groupId + "', logicDeviceId='" + this.logicDeviceId + "'}";
    }
}
