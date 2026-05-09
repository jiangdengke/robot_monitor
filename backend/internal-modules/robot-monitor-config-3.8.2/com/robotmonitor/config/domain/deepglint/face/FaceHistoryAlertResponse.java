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
import com.robotmonitor.config.domain.deepglint.face.FaceHistoryAlertData;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FaceHistoryAlertResponse {
    @JsonProperty(value="Code")
    private Integer code;
    @JsonProperty(value="Msg")
    private String msg;
    @JsonProperty(value="Data")
    private FaceHistoryAlertData data;

    public FaceHistoryAlertResponse() {
    }

    public FaceHistoryAlertResponse(Integer code, String msg, FaceHistoryAlertData data) {
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

    public FaceHistoryAlertData getData() {
        return this.data;
    }

    public void setData(FaceHistoryAlertData data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return this.code != null && this.code.equals(1);
    }
}
