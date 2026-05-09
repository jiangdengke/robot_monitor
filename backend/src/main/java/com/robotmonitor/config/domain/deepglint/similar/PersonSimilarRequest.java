/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.robotmonitor.config.domain.deepglint.similar;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.robotmonitor.config.domain.deepglint.similar.PersonSimilarImage;

public class PersonSimilarRequest {
    @JsonProperty(value="Image")
    private PersonSimilarImage image;
    @JsonProperty(value="TopN")
    private Integer topN = 1;
    @JsonProperty(value="Confidence")
    private Double confidence;
    @JsonProperty(value="CaptureTime")
    private Long captureTime;
    @JsonProperty(value="DeviceID")
    private String deviceID;

    public PersonSimilarImage getImage() {
        return this.image;
    }

    public Integer getTopN() {
        return this.topN;
    }

    public Double getConfidence() {
        return this.confidence;
    }

    public Long getCaptureTime() {
        return this.captureTime;
    }

    public String getDeviceID() {
        return this.deviceID;
    }

    @JsonProperty(value="Image")
    public void setImage(PersonSimilarImage image) {
        this.image = image;
    }

    @JsonProperty(value="TopN")
    public void setTopN(Integer topN) {
        this.topN = topN;
    }

    @JsonProperty(value="Confidence")
    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    @JsonProperty(value="CaptureTime")
    public void setCaptureTime(Long captureTime) {
        this.captureTime = captureTime;
    }

    @JsonProperty(value="DeviceID")
    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PersonSimilarRequest)) {
            return false;
        }
        PersonSimilarRequest other = (PersonSimilarRequest)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Integer this$topN = this.getTopN();
        Integer other$topN = other.getTopN();
        if (this$topN == null ? other$topN != null : !((Object)this$topN).equals(other$topN)) {
            return false;
        }
        Double this$confidence = this.getConfidence();
        Double other$confidence = other.getConfidence();
        if (this$confidence == null ? other$confidence != null : !((Object)this$confidence).equals(other$confidence)) {
            return false;
        }
        Long this$captureTime = this.getCaptureTime();
        Long other$captureTime = other.getCaptureTime();
        if (this$captureTime == null ? other$captureTime != null : !((Object)this$captureTime).equals(other$captureTime)) {
            return false;
        }
        PersonSimilarImage this$image = this.getImage();
        PersonSimilarImage other$image = other.getImage();
        if (this$image == null ? other$image != null : !((Object)this$image).equals(other$image)) {
            return false;
        }
        String this$deviceID = this.getDeviceID();
        String other$deviceID = other.getDeviceID();
        return !(this$deviceID == null ? other$deviceID != null : !this$deviceID.equals(other$deviceID));
    }

    protected boolean canEqual(Object other) {
        return other instanceof PersonSimilarRequest;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $topN = this.getTopN();
        result = result * 59 + ($topN == null ? 43 : ((Object)$topN).hashCode());
        Double $confidence = this.getConfidence();
        result = result * 59 + ($confidence == null ? 43 : ((Object)$confidence).hashCode());
        Long $captureTime = this.getCaptureTime();
        result = result * 59 + ($captureTime == null ? 43 : ((Object)$captureTime).hashCode());
        PersonSimilarImage $image = this.getImage();
        result = result * 59 + ($image == null ? 43 : ((Object)$image).hashCode());
        String $deviceID = this.getDeviceID();
        result = result * 59 + ($deviceID == null ? 43 : $deviceID.hashCode());
        return result;
    }

    public String toString() {
        return "PersonSimilarRequest(image=" + this.getImage() + ", topN=" + this.getTopN() + ", confidence=" + this.getConfidence() + ", captureTime=" + this.getCaptureTime() + ", deviceID=" + this.getDeviceID() + ")";
    }
}
