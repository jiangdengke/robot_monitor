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
import com.robotmonitor.flight.dto.kafka.deepglint.data.face.img.Cutboard;
import com.robotmonitor.flight.dto.kafka.deepglint.data.face.img.DetectedBox;
import com.robotmonitor.flight.dto.kafka.deepglint.data.face.img.FaceImg;
import com.robotmonitor.flight.dto.kafka.deepglint.data.face.img.SnapBox;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value={"Cutboard", "DetectedBox", "SnapBox", "Img", "CutboardSpeed"})
public class Img {
    @JsonProperty(value="Cutboard")
    private Cutboard cutboard;
    @JsonProperty(value="DetectedBox")
    private DetectedBox detectedBox;
    @JsonProperty(value="SnapBox")
    private SnapBox snapBox;
    @JsonProperty(value="Img")
    private FaceImg img;
    @JsonProperty(value="CutboardSpeed")
    private Integer cutboardSpeed;
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

    public Cutboard getCutboard() {
        return this.cutboard;
    }

    public DetectedBox getDetectedBox() {
        return this.detectedBox;
    }

    public SnapBox getSnapBox() {
        return this.snapBox;
    }

    public FaceImg getImg() {
        return this.img;
    }

    public Integer getCutboardSpeed() {
        return this.cutboardSpeed;
    }

    @JsonProperty(value="Cutboard")
    public void setCutboard(Cutboard cutboard) {
        this.cutboard = cutboard;
    }

    @JsonProperty(value="DetectedBox")
    public void setDetectedBox(DetectedBox detectedBox) {
        this.detectedBox = detectedBox;
    }

    @JsonProperty(value="SnapBox")
    public void setSnapBox(SnapBox snapBox) {
        this.snapBox = snapBox;
    }

    @JsonProperty(value="Img")
    public void setImg(FaceImg img) {
        this.img = img;
    }

    @JsonProperty(value="CutboardSpeed")
    public void setCutboardSpeed(Integer cutboardSpeed) {
        this.cutboardSpeed = cutboardSpeed;
    }

    @JsonIgnore
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Img)) {
            return false;
        }
        Img other = (Img)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Integer this$cutboardSpeed = this.getCutboardSpeed();
        Integer other$cutboardSpeed = other.getCutboardSpeed();
        if (this$cutboardSpeed == null ? other$cutboardSpeed != null : !((Object)this$cutboardSpeed).equals(other$cutboardSpeed)) {
            return false;
        }
        Cutboard this$cutboard = this.getCutboard();
        Cutboard other$cutboard = other.getCutboard();
        if (this$cutboard == null ? other$cutboard != null : !((Object)this$cutboard).equals(other$cutboard)) {
            return false;
        }
        DetectedBox this$detectedBox = this.getDetectedBox();
        DetectedBox other$detectedBox = other.getDetectedBox();
        if (this$detectedBox == null ? other$detectedBox != null : !((Object)this$detectedBox).equals(other$detectedBox)) {
            return false;
        }
        SnapBox this$snapBox = this.getSnapBox();
        SnapBox other$snapBox = other.getSnapBox();
        if (this$snapBox == null ? other$snapBox != null : !((Object)this$snapBox).equals(other$snapBox)) {
            return false;
        }
        FaceImg this$img = this.getImg();
        FaceImg other$img = other.getImg();
        if (this$img == null ? other$img != null : !((Object)this$img).equals(other$img)) {
            return false;
        }
        Map<String, Object> this$additionalProperties = this.getAdditionalProperties();
        Map<String, Object> other$additionalProperties = other.getAdditionalProperties();
        return !(this$additionalProperties == null ? other$additionalProperties != null : !((Object)this$additionalProperties).equals(other$additionalProperties));
    }

    protected boolean canEqual(Object other) {
        return other instanceof Img;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $cutboardSpeed = this.getCutboardSpeed();
        result = result * 59 + ($cutboardSpeed == null ? 43 : ((Object)$cutboardSpeed).hashCode());
        Cutboard $cutboard = this.getCutboard();
        result = result * 59 + ($cutboard == null ? 43 : ((Object)$cutboard).hashCode());
        DetectedBox $detectedBox = this.getDetectedBox();
        result = result * 59 + ($detectedBox == null ? 43 : ((Object)$detectedBox).hashCode());
        SnapBox $snapBox = this.getSnapBox();
        result = result * 59 + ($snapBox == null ? 43 : ((Object)$snapBox).hashCode());
        FaceImg $img = this.getImg();
        result = result * 59 + ($img == null ? 43 : ((Object)$img).hashCode());
        Map<String, Object> $additionalProperties = this.getAdditionalProperties();
        result = result * 59 + ($additionalProperties == null ? 43 : ((Object)$additionalProperties).hashCode());
        return result;
    }

    public String toString() {
        return "Img(cutboard=" + this.getCutboard() + ", detectedBox=" + this.getDetectedBox() + ", snapBox=" + this.getSnapBox() + ", img=" + this.getImg() + ", cutboardSpeed=" + this.getCutboardSpeed() + ", additionalProperties=" + this.getAdditionalProperties() + ")";
    }
}
