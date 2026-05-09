/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.robotmonitor.config.domain.deepglint.face;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.robotmonitor.config.domain.deepglint.face.CaptureSearchData;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CaptureSearchResponse {
    @JsonProperty(value="Code")
    private Integer code;
    @JsonProperty(value="Msg")
    private String msg;
    @JsonProperty(value="Data")
    private CaptureSearchData data;

    public CaptureSearchResponse() {
    }

    public CaptureSearchResponse(Integer code, String msg, CaptureSearchData data) {
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

    public CaptureSearchData getData() {
        return this.data;
    }

    public void setData(CaptureSearchData data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return this.code != null && this.code == 1;
    }

    public String toString() {
        return "CaptureSearchResponse{code=" + this.code + ", msg='" + this.msg + "', data=" + this.data + "}";
    }
}
