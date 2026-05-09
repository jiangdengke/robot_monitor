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
import com.robotmonitor.flight.dto.kafka.deepglint.data.face.AlignResult;
import com.robotmonitor.flight.dto.kafka.deepglint.data.face.Attribute;
import com.robotmonitor.flight.dto.kafka.deepglint.data.face.Img;
import com.robotmonitor.flight.dto.kafka.deepglint.data.face.Metadata;
import com.robotmonitor.flight.dto.kafka.deepglint.data.face.OriginImg;
import com.robotmonitor.flight.dto.kafka.deepglint.data.face.Qualities;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value={"Confidence", "Img", "AlignResult", "Qualities", "Features", "Attributes", "Metadata", "OriginImg", "UId"})
public class RecFace {
    @JsonProperty(value="Confidence")
    private Double confidence;
    @JsonProperty(value="Img")
    private Img img;
    @JsonProperty(value="AlignResult")
    private AlignResult alignResult;
    @JsonProperty(value="Qualities")
    private Qualities qualities;
    @JsonProperty(value="Features")
    private String features;
    @JsonProperty(value="Attributes")
    private List<Attribute> attributes;
    @JsonProperty(value="Metadata")
    private Metadata metadata;
    @JsonProperty(value="OriginImg")
    private OriginImg originImg;
    @JsonProperty(value="UId")
    private String uId;
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

    public Double getConfidence() {
        return this.confidence;
    }

    public Img getImg() {
        return this.img;
    }

    public AlignResult getAlignResult() {
        return this.alignResult;
    }

    public Qualities getQualities() {
        return this.qualities;
    }

    public String getFeatures() {
        return this.features;
    }

    public List<Attribute> getAttributes() {
        return this.attributes;
    }

    public Metadata getMetadata() {
        return this.metadata;
    }

    public OriginImg getOriginImg() {
        return this.originImg;
    }

    public String getUId() {
        return this.uId;
    }

    @JsonProperty(value="Confidence")
    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    @JsonProperty(value="Img")
    public void setImg(Img img) {
        this.img = img;
    }

    @JsonProperty(value="AlignResult")
    public void setAlignResult(AlignResult alignResult) {
        this.alignResult = alignResult;
    }

    @JsonProperty(value="Qualities")
    public void setQualities(Qualities qualities) {
        this.qualities = qualities;
    }

    @JsonProperty(value="Features")
    public void setFeatures(String features) {
        this.features = features;
    }

    @JsonProperty(value="Attributes")
    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    @JsonProperty(value="Metadata")
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @JsonProperty(value="OriginImg")
    public void setOriginImg(OriginImg originImg) {
        this.originImg = originImg;
    }

    @JsonProperty(value="UId")
    public void setUId(String uId) {
        this.uId = uId;
    }

    @JsonIgnore
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RecFace)) {
            return false;
        }
        RecFace other = (RecFace)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Double this$confidence = this.getConfidence();
        Double other$confidence = other.getConfidence();
        if (this$confidence == null ? other$confidence != null : !((Object)this$confidence).equals(other$confidence)) {
            return false;
        }
        Img this$img = this.getImg();
        Img other$img = other.getImg();
        if (this$img == null ? other$img != null : !((Object)this$img).equals(other$img)) {
            return false;
        }
        AlignResult this$alignResult = this.getAlignResult();
        AlignResult other$alignResult = other.getAlignResult();
        if (this$alignResult == null ? other$alignResult != null : !((Object)this$alignResult).equals(other$alignResult)) {
            return false;
        }
        Qualities this$qualities = this.getQualities();
        Qualities other$qualities = other.getQualities();
        if (this$qualities == null ? other$qualities != null : !((Object)this$qualities).equals(other$qualities)) {
            return false;
        }
        String this$features = this.getFeatures();
        String other$features = other.getFeatures();
        if (this$features == null ? other$features != null : !this$features.equals(other$features)) {
            return false;
        }
        List<Attribute> this$attributes = this.getAttributes();
        List<Attribute> other$attributes = other.getAttributes();
        if (this$attributes == null ? other$attributes != null : !((Object)this$attributes).equals(other$attributes)) {
            return false;
        }
        Metadata this$metadata = this.getMetadata();
        Metadata other$metadata = other.getMetadata();
        if (this$metadata == null ? other$metadata != null : !((Object)this$metadata).equals(other$metadata)) {
            return false;
        }
        OriginImg this$originImg = this.getOriginImg();
        OriginImg other$originImg = other.getOriginImg();
        if (this$originImg == null ? other$originImg != null : !((Object)this$originImg).equals(other$originImg)) {
            return false;
        }
        String this$uId = this.getUId();
        String other$uId = other.getUId();
        if (this$uId == null ? other$uId != null : !this$uId.equals(other$uId)) {
            return false;
        }
        Map<String, Object> this$additionalProperties = this.getAdditionalProperties();
        Map<String, Object> other$additionalProperties = other.getAdditionalProperties();
        return !(this$additionalProperties == null ? other$additionalProperties != null : !((Object)this$additionalProperties).equals(other$additionalProperties));
    }

    protected boolean canEqual(Object other) {
        return other instanceof RecFace;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Double $confidence = this.getConfidence();
        result = result * 59 + ($confidence == null ? 43 : ((Object)$confidence).hashCode());
        Img $img = this.getImg();
        result = result * 59 + ($img == null ? 43 : ((Object)$img).hashCode());
        AlignResult $alignResult = this.getAlignResult();
        result = result * 59 + ($alignResult == null ? 43 : ((Object)$alignResult).hashCode());
        Qualities $qualities = this.getQualities();
        result = result * 59 + ($qualities == null ? 43 : ((Object)$qualities).hashCode());
        String $features = this.getFeatures();
        result = result * 59 + ($features == null ? 43 : $features.hashCode());
        List<Attribute> $attributes = this.getAttributes();
        result = result * 59 + ($attributes == null ? 43 : ((Object)$attributes).hashCode());
        Metadata $metadata = this.getMetadata();
        result = result * 59 + ($metadata == null ? 43 : ((Object)$metadata).hashCode());
        OriginImg $originImg = this.getOriginImg();
        result = result * 59 + ($originImg == null ? 43 : ((Object)$originImg).hashCode());
        String $uId = this.getUId();
        result = result * 59 + ($uId == null ? 43 : $uId.hashCode());
        Map<String, Object> $additionalProperties = this.getAdditionalProperties();
        result = result * 59 + ($additionalProperties == null ? 43 : ((Object)$additionalProperties).hashCode());
        return result;
    }

    public String toString() {
        return "RecFace(confidence=" + this.getConfidence() + ", img=" + this.getImg() + ", alignResult=" + this.getAlignResult() + ", qualities=" + this.getQualities() + ", features=" + this.getFeatures() + ", attributes=" + this.getAttributes() + ", metadata=" + this.getMetadata() + ", originImg=" + this.getOriginImg() + ", uId=" + this.getUId() + ", additionalProperties=" + this.getAdditionalProperties() + ")";
    }
}
