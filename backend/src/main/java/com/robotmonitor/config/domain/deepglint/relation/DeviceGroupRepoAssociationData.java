/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.config.domain.deepglint.relation;

import java.util.List;

public class DeviceGroupRepoAssociationData {
    private String deviceGroupId;
    private List<String> repoIds;

    public DeviceGroupRepoAssociationData() {
    }

    public DeviceGroupRepoAssociationData(String deviceGroupId, List<String> repoIds) {
        this.deviceGroupId = deviceGroupId;
        this.repoIds = repoIds;
    }

    public String getDeviceGroupId() {
        return this.deviceGroupId;
    }

    public void setDeviceGroupId(String deviceGroupId) {
        this.deviceGroupId = deviceGroupId;
    }

    public List<String> getRepoIds() {
        return this.repoIds;
    }

    public void setRepoIds(List<String> repoIds) {
        this.repoIds = repoIds;
    }
}
