/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonAnyGetter
 *  com.fasterxml.jackson.annotation.JsonAnySetter
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.fasterxml.jackson.annotation.JsonPropertyOrder
 */
package com.robotmonitor.flight.dto.kafka.deepglint.data;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.robotmonitor.flight.dto.kafka.deepglint.data.metadata.AdditionalInfos;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value={"Timestamp", "ObjType", "AdditionalInfos", "InnerTaskId"})
public class Metadata {
    @JsonProperty(value="Timestamp")
    private Long timestamp;
    @JsonProperty(value="ObjType")
    private Integer objType;
    @JsonProperty(value="AdditionalInfos")
    private AdditionalInfos additionalInfos;
    @JsonProperty(value="InnerTaskId")
    private String innerTaskId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Long getTimestamp() {
        return this.timestamp;
    }

    public Integer getObjType() {
        return this.objType;
    }

    public AdditionalInfos getAdditionalInfos() {
        return this.additionalInfos;
    }

    public String getInnerTaskId() {
        return this.innerTaskId;
    }

    @JsonProperty(value="Timestamp")
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @JsonProperty(value="ObjType")
    public void setObjType(Integer objType) {
        this.objType = objType;
    }

    @JsonProperty(value="AdditionalInfos")
    public void setAdditionalInfos(AdditionalInfos additionalInfos) {
        this.additionalInfos = additionalInfos;
    }

    @JsonProperty(value="InnerTaskId")
    public void setInnerTaskId(String innerTaskId) {
        this.innerTaskId = innerTaskId;
    }

    @JsonIgnore
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Metadata)) {
            return false;
        }
        Metadata other = (Metadata)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Long this$timestamp = this.getTimestamp();
        Long other$timestamp = other.getTimestamp();
        if (this$timestamp == null ? other$timestamp != null : !((Object)this$timestamp).equals(other$timestamp)) {
            return false;
        }
        Integer this$objType = this.getObjType();
        Integer other$objType = other.getObjType();
        if (this$objType == null ? other$objType != null : !((Object)this$objType).equals(other$objType)) {
            return false;
        }
        AdditionalInfos this$additionalInfos = this.getAdditionalInfos();
        AdditionalInfos other$additionalInfos = other.getAdditionalInfos();
        if (this$additionalInfos == null ? other$additionalInfos != null : !((Object)this$additionalInfos).equals(other$additionalInfos)) {
            return false;
        }
        String this$innerTaskId = this.getInnerTaskId();
        String other$innerTaskId = other.getInnerTaskId();
        if (this$innerTaskId == null ? other$innerTaskId != null : !this$innerTaskId.equals(other$innerTaskId)) {
            return false;
        }
        Map<String, Object> this$additionalProperties = this.getAdditionalProperties();
        Map<String, Object> other$additionalProperties = other.getAdditionalProperties();
        return !(this$additionalProperties == null ? other$additionalProperties != null : !((Object)this$additionalProperties).equals(other$additionalProperties));
    }

    protected boolean canEqual(Object other) {
        return other instanceof Metadata;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $timestamp = this.getTimestamp();
        result = result * 59 + ($timestamp == null ? 43 : ((Object)$timestamp).hashCode());
        Integer $objType = this.getObjType();
        result = result * 59 + ($objType == null ? 43 : ((Object)$objType).hashCode());
        AdditionalInfos $additionalInfos = this.getAdditionalInfos();
        result = result * 59 + ($additionalInfos == null ? 43 : ((Object)$additionalInfos).hashCode());
        String $innerTaskId = this.getInnerTaskId();
        result = result * 59 + ($innerTaskId == null ? 43 : $innerTaskId.hashCode());
        Map<String, Object> $additionalProperties = this.getAdditionalProperties();
        result = result * 59 + ($additionalProperties == null ? 43 : ((Object)$additionalProperties).hashCode());
        return result;
    }

    public String toString() {
        return "Metadata(timestamp=" + this.getTimestamp() + ", objType=" + this.getObjType() + ", additionalInfos=" + this.getAdditionalInfos() + ", innerTaskId=" + this.getInnerTaskId() + ", additionalProperties=" + this.getAdditionalProperties() + ")";
    }
}
