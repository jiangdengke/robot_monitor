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
package com.robotmonitor.flight.dto.kafka.deepglint;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.robotmonitor.flight.dto.kafka.deepglint.data.Img;
import com.robotmonitor.flight.dto.kafka.deepglint.data.Metadata;
import com.robotmonitor.flight.dto.kafka.deepglint.data.RecFace;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value={"Metadata", "Img", "RecFaces"})
public class FaceKafkaBinData {
    @JsonProperty(value="Metadata")
    private Metadata metadata;
    @JsonProperty(value="Img")
    private Img img;
    @JsonProperty(value="RecFaces")
    private List<RecFace> recFaces;
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

    public Metadata getMetadata() {
        return this.metadata;
    }

    public Img getImg() {
        return this.img;
    }

    public List<RecFace> getRecFaces() {
        return this.recFaces;
    }

    @JsonProperty(value="Metadata")
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @JsonProperty(value="Img")
    public void setImg(Img img) {
        this.img = img;
    }

    @JsonProperty(value="RecFaces")
    public void setRecFaces(List<RecFace> recFaces) {
        this.recFaces = recFaces;
    }

    @JsonIgnore
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FaceKafkaBinData)) {
            return false;
        }
        FaceKafkaBinData other = (FaceKafkaBinData)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Metadata this$metadata = this.getMetadata();
        Metadata other$metadata = other.getMetadata();
        if (this$metadata == null ? other$metadata != null : !((Object)this$metadata).equals(other$metadata)) {
            return false;
        }
        Img this$img = this.getImg();
        Img other$img = other.getImg();
        if (this$img == null ? other$img != null : !((Object)this$img).equals(other$img)) {
            return false;
        }
        List<RecFace> this$recFaces = this.getRecFaces();
        List<RecFace> other$recFaces = other.getRecFaces();
        if (this$recFaces == null ? other$recFaces != null : !((Object)this$recFaces).equals(other$recFaces)) {
            return false;
        }
        Map<String, Object> this$additionalProperties = this.getAdditionalProperties();
        Map<String, Object> other$additionalProperties = other.getAdditionalProperties();
        return !(this$additionalProperties == null ? other$additionalProperties != null : !((Object)this$additionalProperties).equals(other$additionalProperties));
    }

    protected boolean canEqual(Object other) {
        return other instanceof FaceKafkaBinData;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Metadata $metadata = this.getMetadata();
        result = result * 59 + ($metadata == null ? 43 : ((Object)$metadata).hashCode());
        Img $img = this.getImg();
        result = result * 59 + ($img == null ? 43 : ((Object)$img).hashCode());
        List<RecFace> $recFaces = this.getRecFaces();
        result = result * 59 + ($recFaces == null ? 43 : ((Object)$recFaces).hashCode());
        Map<String, Object> $additionalProperties = this.getAdditionalProperties();
        result = result * 59 + ($additionalProperties == null ? 43 : ((Object)$additionalProperties).hashCode());
        return result;
    }

    public String toString() {
        return "FaceKafkaBinData(metadata=" + this.getMetadata() + ", img=" + this.getImg() + ", recFaces=" + this.getRecFaces() + ", additionalProperties=" + this.getAdditionalProperties() + ")";
    }
}
