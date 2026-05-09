/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.robotmonitor.config.domain.deepglint.relation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.robotmonitor.config.domain.deepglint.relation.RepoAssociation;
import java.util.List;

public class AddDeviceGroupRepoAssociationRequest {
    @JsonProperty(value="DeviceGroupId")
    private String deviceGroupId;
    @JsonProperty(value="RepoIds")
    private List<String> repoIds;
    @JsonProperty(value="LogicDeviceId")
    private String logicDeviceId;
    @JsonProperty(value="Repos")
    private List<RepoAssociation> repos;

    public AddDeviceGroupRepoAssociationRequest() {
    }

    public AddDeviceGroupRepoAssociationRequest(String deviceGroupId, List<String> repoIds) {
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

    public String getLogicDeviceId() {
        return this.logicDeviceId;
    }

    public void setLogicDeviceId(String logicDeviceId) {
        this.logicDeviceId = logicDeviceId;
    }

    public List<RepoAssociation> getRepos() {
        return this.repos;
    }

    public void setRepos(List<RepoAssociation> repos) {
        this.repos = repos;
    }

    public String toString() {
        return "AddDeviceGroupRepoAssociationRequest{deviceGroupId='" + this.deviceGroupId + "', repoIds=" + this.repoIds + ", logicDeviceId='" + this.logicDeviceId + "', repos=" + this.repos + "}";
    }
}
