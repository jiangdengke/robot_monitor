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
public class CompareRepoData {
    @JsonProperty(value="RepoID")
    private String repoId;
    @JsonProperty(value="Cts")
    private Long cts;
    @JsonProperty(value="Uts")
    private Long uts;
    @JsonProperty(value="RepoName")
    private String repoName;
    @JsonProperty(value="Capacity")
    private Integer capacity;
    @JsonProperty(value="Size")
    private Integer size;
    @JsonProperty(value="Comment")
    private String comment;

    public CompareRepoData() {
    }

    public CompareRepoData(String repoId, String repoName, Integer capacity) {
        this.repoId = repoId;
        this.repoName = repoName;
        this.capacity = capacity;
    }

    public String getRepoId() {
        return this.repoId;
    }

    public void setRepoId(String repoId) {
        this.repoId = repoId;
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

    public Integer getSize() {
        return this.size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
