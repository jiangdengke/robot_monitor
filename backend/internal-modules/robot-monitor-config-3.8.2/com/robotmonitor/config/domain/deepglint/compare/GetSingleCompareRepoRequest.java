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
public class GetSingleCompareRepoRequest {
    @JsonProperty(value="RepoId")
    private String repoId;

    public GetSingleCompareRepoRequest() {
    }

    public GetSingleCompareRepoRequest(String repoId) {
        this.repoId = repoId;
    }

    public String getRepoId() {
        return this.repoId;
    }

    public void setRepoId(String repoId) {
        this.repoId = repoId;
    }

    public String toString() {
        return "GetSingleCompareRepoRequest{repoId='" + this.repoId + "'}";
    }
}
