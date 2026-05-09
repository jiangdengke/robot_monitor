/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.robotmonitor.config.domain.deepglint.compare;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ListCompareRepoRequest {
    @JsonProperty(value="Limit")
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private Integer limit = 16;
    @JsonProperty(value="Offset")
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private Integer offset = 0;
    @JsonProperty(value="RepoIds")
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private List<String> repoIds;
    @JsonProperty(value="RepoNames")
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private List<String> repoNames;

    public ListCompareRepoRequest() {
    }

    public ListCompareRepoRequest(Integer limit, Integer offset) {
        this.limit = limit;
        this.offset = offset;
    }

    public Integer getLimit() {
        return this.limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return this.offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public List<String> getRepoIds() {
        return this.repoIds;
    }

    public void setRepoIds(List<String> repoIds) {
        this.repoIds = repoIds;
    }

    public List<String> getRepoNames() {
        return this.repoNames;
    }

    public void setRepoNames(List<String> repoNames) {
        this.repoNames = repoNames;
    }

    public String toString() {
        return "ListCompareRepoRequest{limit=" + this.limit + ", offset=" + this.offset + ", repoIds=" + this.repoIds + ", repoNames=" + this.repoNames + "}";
    }
}
