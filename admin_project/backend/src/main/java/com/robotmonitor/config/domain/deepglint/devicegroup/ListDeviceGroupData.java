/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.config.domain.deepglint.devicegroup;

import com.robotmonitor.config.domain.deepglint.devicegroup.DeviceGroupData;
import java.util.List;

public class ListDeviceGroupData {
    private Integer groupCount;
    private List<DeviceGroupData> deviceGroups;

    public ListDeviceGroupData() {
    }

    public ListDeviceGroupData(Integer groupCount, List<DeviceGroupData> deviceGroups) {
        this.groupCount = groupCount;
        this.deviceGroups = deviceGroups;
    }

    public Integer getGroupCount() {
        return this.groupCount;
    }

    public void setGroupCount(Integer groupCount) {
        this.groupCount = groupCount;
    }

    public List<DeviceGroupData> getDeviceGroups() {
        return this.deviceGroups;
    }

    public void setDeviceGroups(List<DeviceGroupData> deviceGroups) {
        this.deviceGroups = deviceGroups;
    }
}
