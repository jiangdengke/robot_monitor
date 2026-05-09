/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.config.domain.deepglint.devicegroup;

import com.robotmonitor.config.domain.deepglint.devicegroup.DeviceInGroup;
import java.util.List;

public class DeviceGroupData {
    private Long cts;
    private Long uts;
    private String groupId;
    private String groupName;
    private List<DeviceInGroup> devices;
    private String groupType;
    private String comment;

    public DeviceGroupData() {
    }

    public DeviceGroupData(String groupId, String groupName, String groupType) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupType = groupType;
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

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<DeviceInGroup> getDevices() {
        return this.devices;
    }

    public void setDevices(List<DeviceInGroup> devices) {
        this.devices = devices;
    }

    public String getGroupType() {
        return this.groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
