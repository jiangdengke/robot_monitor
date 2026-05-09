/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.robotmonitor.config.domain.deepglint.devicegroup;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class AddDeviceGroupRequest {
    @JsonProperty(value="GroupName")
    private String groupName;
    @JsonProperty(value="GroupId")
    private String groupId;
    @JsonProperty(value="LogicDeviceIds")
    private List<String> logicDeviceIds;
    @JsonProperty(value="Comment")
    private String comment;

    public AddDeviceGroupRequest() {
    }

    public AddDeviceGroupRequest(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<String> getLogicDeviceIds() {
        return this.logicDeviceIds;
    }

    public void setLogicDeviceIds(List<String> logicDeviceIds) {
        this.logicDeviceIds = logicDeviceIds;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String toString() {
        return "AddDeviceGroupRequest{groupName='" + this.groupName + "', groupId='" + this.groupId + "', logicDeviceIds=" + this.logicDeviceIds + ", comment='" + this.comment + "'}";
    }
}
