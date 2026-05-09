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

@JsonIgnoreProperties(ignoreUnknown=true)
public class CreateCompareRepoResponse {
    @JsonProperty(value="Code")
    private Integer code;
    @JsonProperty(value="Msg")
    private String msg;
    @JsonProperty(value="Data")
    private CompareRepoData data;

    public CreateCompareRepoResponse() {
    }

    public CreateCompareRepoResponse(Integer code, String msg, CompareRepoData data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CompareRepoData getData() {
        return this.data;
    }

    public void setData(CompareRepoData data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return this.code != null && this.code.equals(1);
    }
}
