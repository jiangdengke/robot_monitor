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
@JsonPropertyOrder(value={"AlignScore", "AlignScore_G_frontface", "AlignScore_G_isface", "Aspect", "Blur", "Border", "DetectScore", "Eye_occlusion", "IsFace", "Mouth_occlusion", "Pitch", "Roll", "Size", "Yaw"})
public class Qualities {
    @JsonProperty(value="AlignScore")
    private Double alignScore;
    @JsonProperty(value="AlignScore_G_frontface")
    private Integer alignScoreGFrontface;
    @JsonProperty(value="AlignScore_G_isface")
    private Integer alignScoreGIsface;
    @JsonProperty(value="Aspect")
    private Integer aspect;
    @JsonProperty(value="Blur")
    private Double blur;
    @JsonProperty(value="Border")
    private Integer border;
    @JsonProperty(value="DetectScore")
    private Double detectScore;
    @JsonProperty(value="Eye_occlusion")
    private Double eyeOcclusion;
    @JsonProperty(value="IsFace")
    private Integer isFace;
    @JsonProperty(value="Mouth_occlusion")
    private Double mouthOcclusion;
    @JsonProperty(value="Pitch")
    private Double pitch;
    @JsonProperty(value="Roll")
    private Double roll;
    @JsonProperty(value="Size")
    private Integer size;
    @JsonProperty(value="Yaw")
    private Double yaw;
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

    public Double getAlignScore() {
        return this.alignScore;
    }

    public Integer getAlignScoreGFrontface() {
        return this.alignScoreGFrontface;
    }

    public Integer getAlignScoreGIsface() {
        return this.alignScoreGIsface;
    }

    public Integer getAspect() {
        return this.aspect;
    }

    public Double getBlur() {
        return this.blur;
    }

    public Integer getBorder() {
        return this.border;
    }

    public Double getDetectScore() {
        return this.detectScore;
    }

    public Double getEyeOcclusion() {
        return this.eyeOcclusion;
    }

    public Integer getIsFace() {
        return this.isFace;
    }

    public Double getMouthOcclusion() {
        return this.mouthOcclusion;
    }

    public Double getPitch() {
        return this.pitch;
    }

    public Double getRoll() {
        return this.roll;
    }

    public Integer getSize() {
        return this.size;
    }

    public Double getYaw() {
        return this.yaw;
    }

    @JsonProperty(value="AlignScore")
    public void setAlignScore(Double alignScore) {
        this.alignScore = alignScore;
    }

    @JsonProperty(value="AlignScore_G_frontface")
    public void setAlignScoreGFrontface(Integer alignScoreGFrontface) {
        this.alignScoreGFrontface = alignScoreGFrontface;
    }

    @JsonProperty(value="AlignScore_G_isface")
    public void setAlignScoreGIsface(Integer alignScoreGIsface) {
        this.alignScoreGIsface = alignScoreGIsface;
    }

    @JsonProperty(value="Aspect")
    public void setAspect(Integer aspect) {
        this.aspect = aspect;
    }

    @JsonProperty(value="Blur")
    public void setBlur(Double blur) {
        this.blur = blur;
    }

    @JsonProperty(value="Border")
    public void setBorder(Integer border) {
        this.border = border;
    }

    @JsonProperty(value="DetectScore")
    public void setDetectScore(Double detectScore) {
        this.detectScore = detectScore;
    }

    @JsonProperty(value="Eye_occlusion")
    public void setEyeOcclusion(Double eyeOcclusion) {
        this.eyeOcclusion = eyeOcclusion;
    }

    @JsonProperty(value="IsFace")
    public void setIsFace(Integer isFace) {
        this.isFace = isFace;
    }

    @JsonProperty(value="Mouth_occlusion")
    public void setMouthOcclusion(Double mouthOcclusion) {
        this.mouthOcclusion = mouthOcclusion;
    }

    @JsonProperty(value="Pitch")
    public void setPitch(Double pitch) {
        this.pitch = pitch;
    }

    @JsonProperty(value="Roll")
    public void setRoll(Double roll) {
        this.roll = roll;
    }

    @JsonProperty(value="Size")
    public void setSize(Integer size) {
        this.size = size;
    }

    @JsonProperty(value="Yaw")
    public void setYaw(Double yaw) {
        this.yaw = yaw;
    }

    @JsonIgnore
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Qualities)) {
            return false;
        }
        Qualities other = (Qualities)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Double this$alignScore = this.getAlignScore();
        Double other$alignScore = other.getAlignScore();
        if (this$alignScore == null ? other$alignScore != null : !((Object)this$alignScore).equals(other$alignScore)) {
            return false;
        }
        Integer this$alignScoreGFrontface = this.getAlignScoreGFrontface();
        Integer other$alignScoreGFrontface = other.getAlignScoreGFrontface();
        if (this$alignScoreGFrontface == null ? other$alignScoreGFrontface != null : !((Object)this$alignScoreGFrontface).equals(other$alignScoreGFrontface)) {
            return false;
        }
        Integer this$alignScoreGIsface = this.getAlignScoreGIsface();
        Integer other$alignScoreGIsface = other.getAlignScoreGIsface();
        if (this$alignScoreGIsface == null ? other$alignScoreGIsface != null : !((Object)this$alignScoreGIsface).equals(other$alignScoreGIsface)) {
            return false;
        }
        Integer this$aspect = this.getAspect();
        Integer other$aspect = other.getAspect();
        if (this$aspect == null ? other$aspect != null : !((Object)this$aspect).equals(other$aspect)) {
            return false;
        }
        Double this$blur = this.getBlur();
        Double other$blur = other.getBlur();
        if (this$blur == null ? other$blur != null : !((Object)this$blur).equals(other$blur)) {
            return false;
        }
        Integer this$border = this.getBorder();
        Integer other$border = other.getBorder();
        if (this$border == null ? other$border != null : !((Object)this$border).equals(other$border)) {
            return false;
        }
        Double this$detectScore = this.getDetectScore();
        Double other$detectScore = other.getDetectScore();
        if (this$detectScore == null ? other$detectScore != null : !((Object)this$detectScore).equals(other$detectScore)) {
            return false;
        }
        Double this$eyeOcclusion = this.getEyeOcclusion();
        Double other$eyeOcclusion = other.getEyeOcclusion();
        if (this$eyeOcclusion == null ? other$eyeOcclusion != null : !((Object)this$eyeOcclusion).equals(other$eyeOcclusion)) {
            return false;
        }
        Integer this$isFace = this.getIsFace();
        Integer other$isFace = other.getIsFace();
        if (this$isFace == null ? other$isFace != null : !((Object)this$isFace).equals(other$isFace)) {
            return false;
        }
        Double this$mouthOcclusion = this.getMouthOcclusion();
        Double other$mouthOcclusion = other.getMouthOcclusion();
        if (this$mouthOcclusion == null ? other$mouthOcclusion != null : !((Object)this$mouthOcclusion).equals(other$mouthOcclusion)) {
            return false;
        }
        Double this$pitch = this.getPitch();
        Double other$pitch = other.getPitch();
        if (this$pitch == null ? other$pitch != null : !((Object)this$pitch).equals(other$pitch)) {
            return false;
        }
        Double this$roll = this.getRoll();
        Double other$roll = other.getRoll();
        if (this$roll == null ? other$roll != null : !((Object)this$roll).equals(other$roll)) {
            return false;
        }
        Integer this$size = this.getSize();
        Integer other$size = other.getSize();
        if (this$size == null ? other$size != null : !((Object)this$size).equals(other$size)) {
            return false;
        }
        Double this$yaw = this.getYaw();
        Double other$yaw = other.getYaw();
        if (this$yaw == null ? other$yaw != null : !((Object)this$yaw).equals(other$yaw)) {
            return false;
        }
        Map<String, Object> this$additionalProperties = this.getAdditionalProperties();
        Map<String, Object> other$additionalProperties = other.getAdditionalProperties();
        return !(this$additionalProperties == null ? other$additionalProperties != null : !((Object)this$additionalProperties).equals(other$additionalProperties));
    }

    protected boolean canEqual(Object other) {
        return other instanceof Qualities;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Double $alignScore = this.getAlignScore();
        result = result * 59 + ($alignScore == null ? 43 : ((Object)$alignScore).hashCode());
        Integer $alignScoreGFrontface = this.getAlignScoreGFrontface();
        result = result * 59 + ($alignScoreGFrontface == null ? 43 : ((Object)$alignScoreGFrontface).hashCode());
        Integer $alignScoreGIsface = this.getAlignScoreGIsface();
        result = result * 59 + ($alignScoreGIsface == null ? 43 : ((Object)$alignScoreGIsface).hashCode());
        Integer $aspect = this.getAspect();
        result = result * 59 + ($aspect == null ? 43 : ((Object)$aspect).hashCode());
        Double $blur = this.getBlur();
        result = result * 59 + ($blur == null ? 43 : ((Object)$blur).hashCode());
        Integer $border = this.getBorder();
        result = result * 59 + ($border == null ? 43 : ((Object)$border).hashCode());
        Double $detectScore = this.getDetectScore();
        result = result * 59 + ($detectScore == null ? 43 : ((Object)$detectScore).hashCode());
        Double $eyeOcclusion = this.getEyeOcclusion();
        result = result * 59 + ($eyeOcclusion == null ? 43 : ((Object)$eyeOcclusion).hashCode());
        Integer $isFace = this.getIsFace();
        result = result * 59 + ($isFace == null ? 43 : ((Object)$isFace).hashCode());
        Double $mouthOcclusion = this.getMouthOcclusion();
        result = result * 59 + ($mouthOcclusion == null ? 43 : ((Object)$mouthOcclusion).hashCode());
        Double $pitch = this.getPitch();
        result = result * 59 + ($pitch == null ? 43 : ((Object)$pitch).hashCode());
        Double $roll = this.getRoll();
        result = result * 59 + ($roll == null ? 43 : ((Object)$roll).hashCode());
        Integer $size = this.getSize();
        result = result * 59 + ($size == null ? 43 : ((Object)$size).hashCode());
        Double $yaw = this.getYaw();
        result = result * 59 + ($yaw == null ? 43 : ((Object)$yaw).hashCode());
        Map<String, Object> $additionalProperties = this.getAdditionalProperties();
        result = result * 59 + ($additionalProperties == null ? 43 : ((Object)$additionalProperties).hashCode());
        return result;
    }

    public String toString() {
        return "Qualities(alignScore=" + this.getAlignScore() + ", alignScoreGFrontface=" + this.getAlignScoreGFrontface() + ", alignScoreGIsface=" + this.getAlignScoreGIsface() + ", aspect=" + this.getAspect() + ", blur=" + this.getBlur() + ", border=" + this.getBorder() + ", detectScore=" + this.getDetectScore() + ", eyeOcclusion=" + this.getEyeOcclusion() + ", isFace=" + this.getIsFace() + ", mouthOcclusion=" + this.getMouthOcclusion() + ", pitch=" + this.getPitch() + ", roll=" + this.getRoll() + ", size=" + this.getSize() + ", yaw=" + this.getYaw() + ", additionalProperties=" + this.getAdditionalProperties() + ")";
    }
}
