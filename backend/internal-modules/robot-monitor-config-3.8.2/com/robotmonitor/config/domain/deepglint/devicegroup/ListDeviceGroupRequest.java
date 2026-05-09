/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.robotmonitor.config.domain.deepglint.devicegroup;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ListDeviceGroupRequest {
    @JsonProperty(value="Offset")
    private Integer offset = 0;
    @JsonProperty(value="Limit")
    private Integer limit = 16;
    @JsonProperty(value="GroupIds")
    private List<String> groupIds;
    @JsonProperty(value="GroupNames")
    private List<String> groupNames;
    @JsonProperty(value="GroupType")
    private String groupType;

    public ListDeviceGroupRequest() {
    }

    public ListDeviceGroupRequest(Integer offset, Integer limit) {
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

    public List<String> getGroupIds() {
        return this.groupIds;
    }

    public void setGroupIds(List<String> groupIds) {
        this.groupIds = groupIds;
    }

    public List<String> getGroupNames() {
        return this.groupNames;
    }

    public void setGroupNames(List<String> groupNames) {
        this.groupNames = groupNames;
    }

    public String getGroupType() {
        return this.groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String toString() {
        return "ListDeviceGroupRequest{offset=" + this.offset + ", limit=" + this.limit + ", groupIds=" + this.groupIds + ", groupNames=" + this.groupNames + ", groupType='" + this.groupType + "'}";
    }
}
