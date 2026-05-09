/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.robotmonitor.config.domain.deepglint.compare;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DeleteRegisterRequest {
    @JsonProperty(value="RepoID")
    private String repoId;
    @JsonProperty(value="RegisterID")
    private String registerId;

    public DeleteRegisterRequest() {
    }

    public DeleteRegisterRequest(String repoId, String registerId) {
        this.repoId = repoId;
        this.registerId = registerId;
    }

    public String getRepoId() {
        return this.repoId;
    }

    public void setRepoId(String repoId) {
        this.repoId = repoId;
    }

    public String getRegisterId() {
        return this.registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    public String toString() {
        return "DeleteRegisterRequest{repoId='" + this.repoId + "', registerId='" + this.registerId + "'}";
    }
}
