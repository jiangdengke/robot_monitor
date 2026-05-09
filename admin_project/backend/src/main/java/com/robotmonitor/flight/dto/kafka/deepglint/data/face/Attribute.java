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
package com.robotmonitor.flight.dto.kafka.deepglint.data.face;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value={"AttributeId", "Name", "ValueId", "Confidence", "Value", "ValueStr", "ValueType"})
public class Attribute {
    @JsonProperty(value="AttributeId")
    private Integer attributeId;
    @JsonProperty(value="Name")
    private String name;
    @JsonProperty(value="ValueId")
    private Integer valueId;
    @JsonProperty(value="Confidence")
    private Double confidence;
    @JsonProperty(value="Value")
    private Object value;
    @JsonProperty(value="ValueStr")
    private String valueStr;
    @JsonProperty(value="ValueType")
    private Integer valueType;
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

    public Integer getAttributeId() {
        return this.attributeId;
    }

    public String getName() {
        return this.name;
    }

    public Integer getValueId() {
        return this.valueId;
    }

    public Double getConfidence() {
        return this.confidence;
    }

    public Object getValue() {
        return this.value;
    }

    public String getValueStr() {
        return this.valueStr;
    }

    public Integer getValueType() {
        return this.valueType;
    }

    @JsonProperty(value="AttributeId")
    public void setAttributeId(Integer attributeId) {
        this.attributeId = attributeId;
    }

    @JsonProperty(value="Name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty(value="ValueId")
    public void setValueId(Integer valueId) {
        this.valueId = valueId;
    }

    @JsonProperty(value="Confidence")
    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    @JsonProperty(value="Value")
    public void setValue(Object value) {
        this.value = value;
    }

    @JsonProperty(value="ValueStr")
    public void setValueStr(String valueStr) {
        this.valueStr = valueStr;
    }

    @JsonProperty(value="ValueType")
    public void setValueType(Integer valueType) {
        this.valueType = valueType;
    }

    @JsonIgnore
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Attribute)) {
            return false;
        }
        Attribute other = (Attribute)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Integer this$attributeId = this.getAttributeId();
        Integer other$attributeId = other.getAttributeId();
        if (this$attributeId == null ? other$attributeId != null : !((Object)this$attributeId).equals(other$attributeId)) {
            return false;
        }
        Integer this$valueId = this.getValueId();
        Integer other$valueId = other.getValueId();
        if (this$valueId == null ? other$valueId != null : !((Object)this$valueId).equals(other$valueId)) {
            return false;
        }
        Double this$confidence = this.getConfidence();
        Double other$confidence = other.getConfidence();
        if (this$confidence == null ? other$confidence != null : !((Object)this$confidence).equals(other$confidence)) {
            return false;
        }
        Integer this$valueType = this.getValueType();
        Integer other$valueType = other.getValueType();
        if (this$valueType == null ? other$valueType != null : !((Object)this$valueType).equals(other$valueType)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        Object this$value = this.getValue();
        Object other$value = other.getValue();
        if (this$value == null ? other$value != null : !this$value.equals(other$value)) {
            return false;
        }
        String this$valueStr = this.getValueStr();
        String other$valueStr = other.getValueStr();
        if (this$valueStr == null ? other$valueStr != null : !this$valueStr.equals(other$valueStr)) {
            return false;
        }
        Map<String, Object> this$additionalProperties = this.getAdditionalProperties();
        Map<String, Object> other$additionalProperties = other.getAdditionalProperties();
        return !(this$additionalProperties == null ? other$additionalProperties != null : !((Object)this$additionalProperties).equals(other$additionalProperties));
    }

    protected boolean canEqual(Object other) {
        return other instanceof Attribute;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $attributeId = this.getAttributeId();
        result = result * 59 + ($attributeId == null ? 43 : ((Object)$attributeId).hashCode());
        Integer $valueId = this.getValueId();
        result = result * 59 + ($valueId == null ? 43 : ((Object)$valueId).hashCode());
        Double $confidence = this.getConfidence();
        result = result * 59 + ($confidence == null ? 43 : ((Object)$confidence).hashCode());
        Integer $valueType = this.getValueType();
        result = result * 59 + ($valueType == null ? 43 : ((Object)$valueType).hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        Object $value = this.getValue();
        result = result * 59 + ($value == null ? 43 : $value.hashCode());
        String $valueStr = this.getValueStr();
        result = result * 59 + ($valueStr == null ? 43 : $valueStr.hashCode());
        Map<String, Object> $additionalProperties = this.getAdditionalProperties();
        result = result * 59 + ($additionalProperties == null ? 43 : ((Object)$additionalProperties).hashCode());
        return result;
    }

    public String toString() {
        return "Attribute(attributeId=" + this.getAttributeId() + ", name=" + this.getName() + ", valueId=" + this.getValueId() + ", confidence=" + this.getConfidence() + ", value=" + this.getValue() + ", valueStr=" + this.getValueStr() + ", valueType=" + this.getValueType() + ", additionalProperties=" + this.getAdditionalProperties() + ")";
    }
}
