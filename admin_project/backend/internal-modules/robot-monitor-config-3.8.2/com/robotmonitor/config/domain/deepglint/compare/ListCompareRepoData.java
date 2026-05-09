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
import com.robotmonitor.config.domain.deepglint.compare.CompareRepoData;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ListCompareRepoData {
    @JsonProperty(value="Count")
    private Integer count;
    @JsonProperty(value="Repos")
    private List<CompareRepoData> repos;

    public ListCompareRepoData() {
    }

    public ListCompareRepoData(Integer count, List<CompareRepoData> repos) {
        this.count = count;
        this.repos = repos;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<CompareRepoData> getRepos() {
        return this.repos;
    }

    public void setRepos(List<CompareRepoData> repos) {
        this.repos = repos;
    }
}
