/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.robotmonitor.config.domain.deepglint.relation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.robotmonitor.config.domain.deepglint.relation.DeviceGroupRepoAssociationData;

public class DeviceGroupRepoAssociationResponse {
    @JsonProperty(value="Code")
    private Integer code;
    @JsonProperty(value="Msg")
    private String msg;
    @JsonProperty(value="Data")
    private DeviceGroupRepoAssociationData data;

    public DeviceGroupRepoAssociationResponse() {
    }

    public DeviceGroupRepoAssociationResponse(Integer code, String msg, DeviceGroupRepoAssociationData data) {
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

    public DeviceGroupRepoAssociationData getData() {
        return this.data;
    }

    public void setData(DeviceGroupRepoAssociationData data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return this.code != null && this.code.equals(1);
    }
}
