/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.robotmonitor.config.domain.deepglint.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.robotmonitor.config.domain.deepglint.device.ListDeviceData;

public class ListDeviceResponse {
    @JsonProperty(value="Code")
    private Integer code;
    @JsonProperty(value="Msg")
    private String msg;
    @JsonProperty(value="Data")
    private ListDeviceData data;

    public ListDeviceResponse() {
    }

    public ListDeviceResponse(Integer code, String msg, ListDeviceData data) {
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

    public ListDeviceData getData() {
        return this.data;
    }

    public void setData(ListDeviceData data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return this.code != null && this.code.equals(1);
    }
}
