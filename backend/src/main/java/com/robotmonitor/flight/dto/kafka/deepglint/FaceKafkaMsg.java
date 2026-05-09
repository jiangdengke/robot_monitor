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
 *  com.robotmonitor.common.utils.JsonUtils
 *  com.robotmonitor.common.utils.StringUtils
 */
package com.robotmonitor.flight.dto.kafka.deepglint;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.robotmonitor.common.utils.JsonUtils;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.flight.dto.kafka.deepglint.FaceKafkaBinData;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value={"ObjType", "FmtType", "BinData"})
public class FaceKafkaMsg {
    @JsonProperty(value="ObjType")
    private String objType;
    @JsonProperty(value="FmtType")
    private String fmtType;
    @JsonProperty(value="BinData")
    private String binData;
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

    public FaceKafkaBinData getBinDataObj() {
        if (StringUtils.isNotBlank((CharSequence)this.binData)) {
            return (FaceKafkaBinData)JsonUtils.string2Obj((String)new String(Base64.getDecoder().decode(this.binData)), FaceKafkaBinData.class);
        }
        return null;
    }

    public String getObjType() {
        return this.objType;
    }

    public String getFmtType() {
        return this.fmtType;
    }

    public String getBinData() {
        return this.binData;
    }

    @JsonProperty(value="ObjType")
    public void setObjType(String objType) {
        this.objType = objType;
    }

    @JsonProperty(value="FmtType")
    public void setFmtType(String fmtType) {
        this.fmtType = fmtType;
    }

    @JsonProperty(value="BinData")
    public void setBinData(String binData) {
        this.binData = binData;
    }

    @JsonIgnore
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FaceKafkaMsg)) {
            return false;
        }
        FaceKafkaMsg other = (FaceKafkaMsg)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$objType = this.getObjType();
        String other$objType = other.getObjType();
        if (this$objType == null ? other$objType != null : !this$objType.equals(other$objType)) {
            return false;
        }
        String this$fmtType = this.getFmtType();
        String other$fmtType = other.getFmtType();
        if (this$fmtType == null ? other$fmtType != null : !this$fmtType.equals(other$fmtType)) {
            return false;
        }
        String this$binData = this.getBinData();
        String other$binData = other.getBinData();
        if (this$binData == null ? other$binData != null : !this$binData.equals(other$binData)) {
            return false;
        }
        Map<String, Object> this$additionalProperties = this.getAdditionalProperties();
        Map<String, Object> other$additionalProperties = other.getAdditionalProperties();
        return !(this$additionalProperties == null ? other$additionalProperties != null : !((Object)this$additionalProperties).equals(other$additionalProperties));
    }

    protected boolean canEqual(Object other) {
        return other instanceof FaceKafkaMsg;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $objType = this.getObjType();
        result = result * 59 + ($objType == null ? 43 : $objType.hashCode());
        String $fmtType = this.getFmtType();
        result = result * 59 + ($fmtType == null ? 43 : $fmtType.hashCode());
        String $binData = this.getBinData();
        result = result * 59 + ($binData == null ? 43 : $binData.hashCode());
        Map<String, Object> $additionalProperties = this.getAdditionalProperties();
        result = result * 59 + ($additionalProperties == null ? 43 : ((Object)$additionalProperties).hashCode());
        return result;
    }

    public String toString() {
        return "FaceKafkaMsg(objType=" + this.getObjType() + ", fmtType=" + this.getFmtType() + ", binData=" + this.getBinData() + ", additionalProperties=" + this.getAdditionalProperties() + ")";
    }
}
