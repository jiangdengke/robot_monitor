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
import java.util.HashMap;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value={"OriginPersonID", "Operation"})
@JsonIgnoreProperties(ignoreUnknown=true)
public class ChangeListRequest {
    public static final String ALL = "all";
    public static final String NEW = "new";
    public static final String MERGE = "merge";
    public static final String UPDATE = "update";
    @JsonProperty(value="OriginPersonID")
    private String originPersonID;
    @JsonProperty(value="Operation")
    private String operation = "merge";
    @JsonProperty(value="Limit")
    private Integer limit = 200;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty(value="OriginPersonID")
    public String getOriginPersonID() {
        return this.originPersonID;
    }

    @JsonProperty(value="OriginPersonID")
    public void setOriginPersonID(String originPersonID) {
        this.originPersonID = originPersonID;
    }

    @JsonProperty(value="Operation")
    public String getOperation() {
        return this.operation;
    }

    @JsonProperty(value="Operation")
    public void setOperation(String operation) {
        this.operation = operation;
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
        sb.append(ChangeListRequest.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("originPersonID");
        sb.append('=');
        sb.append(this.originPersonID == null ? "<null>" : this.originPersonID);
        sb.append(',');
        sb.append("operation");
        sb.append('=');
        sb.append(this.operation == null ? "<null>" : this.operation);
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
        result = result * 31 + (this.originPersonID == null ? 0 : this.originPersonID.hashCode());
        result = result * 31 + (this.additionalProperties == null ? 0 : this.additionalProperties.hashCode());
        result = result * 31 + (this.operation == null ? 0 : this.operation.hashCode());
        return result;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ChangeListRequest)) {
            return false;
        }
        ChangeListRequest rhs = (ChangeListRequest)other;
        return (this.originPersonID == rhs.originPersonID || this.originPersonID != null && this.originPersonID.equals(rhs.originPersonID)) && (this.additionalProperties == rhs.additionalProperties || this.additionalProperties != null && this.additionalProperties.equals(rhs.additionalProperties)) && (this.operation == rhs.operation || this.operation != null && this.operation.equals(rhs.operation));
    }

    public Integer getLimit() {
        return this.limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
