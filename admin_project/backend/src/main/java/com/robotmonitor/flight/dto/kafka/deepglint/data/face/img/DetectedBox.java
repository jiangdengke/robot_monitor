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
package com.robotmonitor.flight.dto.kafka.deepglint.data.face.img;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value={"X", "Y", "Width", "Height", "Confidence"})
public class DetectedBox {
    @JsonProperty(value="X")
    private Integer x;
    @JsonProperty(value="Y")
    private Integer y;
    @JsonProperty(value="Width")
    private Integer width;
    @JsonProperty(value="Height")
    private Integer height;
    @JsonProperty(value="Confidence")
    private Double confidence;
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

    public Integer getX() {
        return this.x;
    }

    public Integer getY() {
        return this.y;
    }

    public Integer getWidth() {
        return this.width;
    }

    public Integer getHeight() {
        return this.height;
    }

    public Double getConfidence() {
        return this.confidence;
    }

    @JsonProperty(value="X")
    public void setX(Integer x) {
        this.x = x;
    }

    @JsonProperty(value="Y")
    public void setY(Integer y) {
        this.y = y;
    }

    @JsonProperty(value="Width")
    public void setWidth(Integer width) {
        this.width = width;
    }

    @JsonProperty(value="Height")
    public void setHeight(Integer height) {
        this.height = height;
    }

    @JsonProperty(value="Confidence")
    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    @JsonIgnore
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DetectedBox)) {
            return false;
        }
        DetectedBox other = (DetectedBox)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Integer this$x = this.getX();
        Integer other$x = other.getX();
        if (this$x == null ? other$x != null : !((Object)this$x).equals(other$x)) {
            return false;
        }
        Integer this$y = this.getY();
        Integer other$y = other.getY();
        if (this$y == null ? other$y != null : !((Object)this$y).equals(other$y)) {
            return false;
        }
        Integer this$width = this.getWidth();
        Integer other$width = other.getWidth();
        if (this$width == null ? other$width != null : !((Object)this$width).equals(other$width)) {
            return false;
        }
        Integer this$height = this.getHeight();
        Integer other$height = other.getHeight();
        if (this$height == null ? other$height != null : !((Object)this$height).equals(other$height)) {
            return false;
        }
        Double this$confidence = this.getConfidence();
        Double other$confidence = other.getConfidence();
        if (this$confidence == null ? other$confidence != null : !((Object)this$confidence).equals(other$confidence)) {
            return false;
        }
        Map<String, Object> this$additionalProperties = this.getAdditionalProperties();
        Map<String, Object> other$additionalProperties = other.getAdditionalProperties();
        return !(this$additionalProperties == null ? other$additionalProperties != null : !((Object)this$additionalProperties).equals(other$additionalProperties));
    }

    protected boolean canEqual(Object other) {
        return other instanceof DetectedBox;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $x = this.getX();
        result = result * 59 + ($x == null ? 43 : ((Object)$x).hashCode());
        Integer $y = this.getY();
        result = result * 59 + ($y == null ? 43 : ((Object)$y).hashCode());
        Integer $width = this.getWidth();
        result = result * 59 + ($width == null ? 43 : ((Object)$width).hashCode());
        Integer $height = this.getHeight();
        result = result * 59 + ($height == null ? 43 : ((Object)$height).hashCode());
        Double $confidence = this.getConfidence();
        result = result * 59 + ($confidence == null ? 43 : ((Object)$confidence).hashCode());
        Map<String, Object> $additionalProperties = this.getAdditionalProperties();
        result = result * 59 + ($additionalProperties == null ? 43 : ((Object)$additionalProperties).hashCode());
        return result;
    }

    public String toString() {
        return "DetectedBox(x=" + this.getX() + ", y=" + this.getY() + ", width=" + this.getWidth() + ", height=" + this.getHeight() + ", confidence=" + this.getConfidence() + ", additionalProperties=" + this.getAdditionalProperties() + ")";
    }
}
