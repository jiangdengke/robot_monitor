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
import java.util.HashMap;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value={"Pitch", "Roll", "Yaw", "Blur", "Border", "AlignScore", "MouthOcclusion", "EyeOcclusion"})
public class Quality {
    @JsonProperty(value="Pitch")
    private Double pitch;
    @JsonProperty(value="Roll")
    private Double roll;
    @JsonProperty(value="Yaw")
    private Double yaw;
    @JsonProperty(value="Blur")
    private Double blur;
    @JsonProperty(value="Border")
    private Integer border;
    @JsonProperty(value="AlignScore")
    private Double alignScore;
    @JsonProperty(value="MouthOcclusion")
    private Double mouthOcclusion;
    @JsonProperty(value="EyeOcclusion")
    private Double eyeOcclusion;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty(value="Pitch")
    public Double getPitch() {
        return this.pitch;
    }

    @JsonProperty(value="Pitch")
    public void setPitch(Double pitch) {
        this.pitch = pitch;
    }

    @JsonProperty(value="Roll")
    public Double getRoll() {
        return this.roll;
    }

    @JsonProperty(value="Roll")
    public void setRoll(Double roll) {
        this.roll = roll;
    }

    @JsonProperty(value="Yaw")
    public Double getYaw() {
        return this.yaw;
    }

    @JsonProperty(value="Yaw")
    public void setYaw(Double yaw) {
        this.yaw = yaw;
    }

    @JsonProperty(value="Blur")
    public Double getBlur() {
        return this.blur;
    }

    @JsonProperty(value="Blur")
    public void setBlur(Double blur) {
        this.blur = blur;
    }

    @JsonProperty(value="Border")
    public Integer getBorder() {
        return this.border;
    }

    @JsonProperty(value="Border")
    public void setBorder(Integer border) {
        this.border = border;
    }

    @JsonProperty(value="AlignScore")
    public Double getAlignScore() {
        return this.alignScore;
    }

    @JsonProperty(value="AlignScore")
    public void setAlignScore(Double alignScore) {
        this.alignScore = alignScore;
    }

    @JsonProperty(value="MouthOcclusion")
    public Double getMouthOcclusion() {
        return this.mouthOcclusion;
    }

    @JsonProperty(value="MouthOcclusion")
    public void setMouthOcclusion(Double mouthOcclusion) {
        this.mouthOcclusion = mouthOcclusion;
    }

    @JsonProperty(value="EyeOcclusion")
    public Double getEyeOcclusion() {
        return this.eyeOcclusion;
    }

    @JsonProperty(value="EyeOcclusion")
    public void setEyeOcclusion(Double eyeOcclusion) {
        this.eyeOcclusion = eyeOcclusion;
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
        sb.append(Quality.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("pitch");
        sb.append('=');
        sb.append(this.pitch == null ? "<null>" : this.pitch);
        sb.append(',');
        sb.append("roll");
        sb.append('=');
        sb.append(this.roll == null ? "<null>" : this.roll);
        sb.append(',');
        sb.append("yaw");
        sb.append('=');
        sb.append(this.yaw == null ? "<null>" : this.yaw);
        sb.append(',');
        sb.append("blur");
        sb.append('=');
        sb.append(this.blur == null ? "<null>" : this.blur);
        sb.append(',');
        sb.append("border");
        sb.append('=');
        sb.append(this.border == null ? "<null>" : this.border);
        sb.append(',');
        sb.append("alignScore");
        sb.append('=');
        sb.append(this.alignScore == null ? "<null>" : this.alignScore);
        sb.append(',');
        sb.append("mouthOcclusion");
        sb.append('=');
        sb.append(this.mouthOcclusion == null ? "<null>" : this.mouthOcclusion);
        sb.append(',');
        sb.append("eyeOcclusion");
        sb.append('=');
        sb.append(this.eyeOcclusion == null ? "<null>" : this.eyeOcclusion);
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
        result = result * 31 + (this.border == null ? 0 : this.border.hashCode());
        result = result * 31 + (this.mouthOcclusion == null ? 0 : this.mouthOcclusion.hashCode());
        result = result * 31 + (this.roll == null ? 0 : this.roll.hashCode());
        result = result * 31 + (this.alignScore == null ? 0 : this.alignScore.hashCode());
        result = result * 31 + (this.blur == null ? 0 : this.blur.hashCode());
        result = result * 31 + (this.eyeOcclusion == null ? 0 : this.eyeOcclusion.hashCode());
        result = result * 31 + (this.pitch == null ? 0 : this.pitch.hashCode());
        result = result * 31 + (this.additionalProperties == null ? 0 : this.additionalProperties.hashCode());
        result = result * 31 + (this.yaw == null ? 0 : this.yaw.hashCode());
        return result;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Quality)) {
            return false;
        }
        Quality rhs = (Quality)other;
        return (this.border == rhs.border || this.border != null && this.border.equals(rhs.border)) && (this.mouthOcclusion == rhs.mouthOcclusion || this.mouthOcclusion != null && this.mouthOcclusion.equals(rhs.mouthOcclusion)) && (this.roll == rhs.roll || this.roll != null && this.roll.equals(rhs.roll)) && (this.alignScore == rhs.alignScore || this.alignScore != null && this.alignScore.equals(rhs.alignScore)) && (this.blur == rhs.blur || this.blur != null && this.blur.equals(rhs.blur)) && (this.eyeOcclusion == rhs.eyeOcclusion || this.eyeOcclusion != null && this.eyeOcclusion.equals(rhs.eyeOcclusion)) && (this.pitch == rhs.pitch || this.pitch != null && this.pitch.equals(rhs.pitch)) && (this.additionalProperties == rhs.additionalProperties || this.additionalProperties != null && this.additionalProperties.equals(rhs.additionalProperties)) && (this.yaw == rhs.yaw || this.yaw != null && this.yaw.equals(rhs.yaw));
    }
}
