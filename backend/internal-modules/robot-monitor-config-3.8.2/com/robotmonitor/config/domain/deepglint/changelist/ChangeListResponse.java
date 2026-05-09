/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonAnyGetter
 *  com.fasterxml.jackson.annotation.JsonAnySetter
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.fasterxml.jackson.annotation.JsonPropertyOrder
 */
package com.robotmonitor.config.domain.deepglint.changelist;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.robotmonitor.config.domain.deepglint.changelist.ChangeListData;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value={"Code", "Msg", "Data"})
@JsonIgnoreProperties(ignoreUnknown=true)
public class ChangeListResponse {
    @JsonProperty(value="Code")
    private Integer code;
    @JsonProperty(value="Msg")
    private String msg;
    @JsonProperty(value="Data")
    private ChangeListData data;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty(value="Code")
    public Integer getCode() {
        return this.code;
    }

    @JsonProperty(value="Code")
    public void setCode(Integer code) {
        this.code = code;
    }

    @JsonProperty(value="Msg")
    public String getMsg() {
        return this.msg;
    }

    @JsonProperty(value="Msg")
    public void setMsg(String msg) {
        this.msg = msg;
    }

    @JsonProperty(value="Data")
    public ChangeListData getData() {
        return this.data;
    }

    @JsonProperty(value="Data")
    public void setData(ChangeListData data) {
        this.data = data;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ChangeListResponse.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("code");
        sb.append('=');
        sb.append(this.code == null ? "<null>" : this.code);
        sb.append(',');
        sb.append("msg");
        sb.append('=');
        sb.append(this.msg == null ? "<null>" : this.msg);
        sb.append(',');
        sb.append("data");
        sb.append('=');
        sb.append(this.data == null ? "<null>" : this.data);
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(this.additionalProperties == null ? "<null>" : this.additionalProperties);
        sb.append(',');
        if (sb.charAt(sb.length() - 1) == ',') {
            sb.setCharAt(sb.length() - 1, ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public int hashCode() {
        int result = 1;
        result = result * 31 + (this.msg == null ? 0 : this.msg.hashCode());
        result = result * 31 + (this.code == null ? 0 : this.code.hashCode());
        result = result * 31 + (this.additionalProperties == null ? 0 : this.additionalProperties.hashCode());
        result = result * 31 + (this.data == null ? 0 : this.data.hashCode());
        return result;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ChangeListResponse)) {
            return false;
        }
        ChangeListResponse rhs = (ChangeListResponse)other;
        return (this.msg == rhs.msg || this.msg != null && this.msg.equals(rhs.msg)) && (this.code == rhs.code || this.code != null && this.code.equals(rhs.code)) && (this.additionalProperties == rhs.additionalProperties || this.additionalProperties != null && this.additionalProperties.equals(rhs.additionalProperties)) && (this.data == rhs.data || this.data != null && this.data.equals(rhs.data));
    }
}
