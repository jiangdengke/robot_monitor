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
public class CreateCompareRepoRequest {
    @JsonProperty(value="RepoName")
    private String repoName;
    @JsonProperty(value="Capacity")
    private Integer capacity;
    @JsonProperty(value="Comment")
    private String comment;
    @JsonProperty(value="FseRepoSwitch")
    private Boolean fseRepoSwitch;

    public CreateCompareRepoRequest() {
    }

    public CreateCompareRepoRequest(String repoName, Integer capacity) {
        this.repoName = repoName;
        this.capacity = capacity;
    }

    public String getRepoName() {
        return this.repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public Integer getCapacity() {
        return this.capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getFseRepoSwitch() {
        return this.fseRepoSwitch;
    }

    public void setFseRepoSwitch(Boolean fseRepoSwitch) {
        this.fseRepoSwitch = fseRepoSwitch;
    }

    public String toString() {
        return "CreateCompareRepoRequest{repoName='" + this.repoName + "', capacity=" + this.capacity + ", comment='" + this.comment + "', fseRepoSwitch=" + this.fseRepoSwitch + "}";
    }
}
