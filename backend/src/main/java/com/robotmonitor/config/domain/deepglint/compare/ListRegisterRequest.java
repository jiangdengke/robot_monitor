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
public class ListRegisterRequest {
    @JsonProperty(value="RepoID")
    private String repoId;
    @JsonProperty(value="Limit")
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private Integer limit;
    @JsonProperty(value="Offset")
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private Integer offset;
    @JsonProperty(value="RegisterIDs")
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private List<String> registerIds;
    @JsonProperty(value="ExternalIDs")
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private List<String> externalIds;

    public ListRegisterRequest() {
    }

    public ListRegisterRequest(String repoId) {
        this.repoId = repoId;
    }

    public ListRegisterRequest(String repoId, Integer limit, Integer offset) {
        this.repoId = repoId;
        this.limit = limit;
        this.offset = offset;
    }

    public String getRepoId() {
        return this.repoId;
    }

    public void setRepoId(String repoId) {
        this.repoId = repoId;
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

    public List<String> getRegisterIds() {
        return this.registerIds;
    }

    public void setRegisterIds(List<String> registerIds) {
        this.registerIds = registerIds;
    }

    public List<String> getExternalIds() {
        return this.externalIds;
    }

    public void setExternalIds(List<String> externalIds) {
        this.externalIds = externalIds;
    }

    public String toString() {
        return "ListRegisterRequest{repoId='" + this.repoId + "', limit=" + this.limit + ", offset=" + this.offset + ", registerIds=" + this.registerIds + ", externalIds=" + this.externalIds + "}";
    }
}
