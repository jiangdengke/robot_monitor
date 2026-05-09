/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.robotmonitor.config.domain.deepglint.devicegroup;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteDeviceGroupRequest {
    @JsonProperty(value="GroupId")
    private String groupId;

    public DeleteDeviceGroupRequest() {
    }

    public DeleteDeviceGroupRequest(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String toString() {
        return "DeleteDeviceGroupRequest{groupId='" + this.groupId + "'}";
    }
}
