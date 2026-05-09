/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.robotmonitor.config.domain.deepglint.similar;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonSimilarImage {
    @JsonProperty(value="URL")
    private String url;
    @JsonProperty(value="BinData")
    private String binData;
    @JsonProperty(value="Feature")
    private String feature;
    @JsonProperty(value="FeatureType")
    private String featureType;

    public String getUrl() {
        return this.url;
    }

    public String getBinData() {
        return this.binData;
    }

    public String getFeature() {
        return this.feature;
    }

    public String getFeatureType() {
        return this.featureType;
    }

    @JsonProperty(value="URL")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty(value="BinData")
    public void setBinData(String binData) {
        this.binData = binData;
    }

    @JsonProperty(value="Feature")
    public void setFeature(String feature) {
        this.feature = feature;
    }

    @JsonProperty(value="FeatureType")
    public void setFeatureType(String featureType) {
        this.featureType = featureType;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PersonSimilarImage)) {
            return false;
        }
        PersonSimilarImage other = (PersonSimilarImage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$url = this.getUrl();
        String other$url = other.getUrl();
        if (this$url == null ? other$url != null : !this$url.equals(other$url)) {
            return false;
        }
        String this$binData = this.getBinData();
        String other$binData = other.getBinData();
        if (this$binData == null ? other$binData != null : !this$binData.equals(other$binData)) {
            return false;
        }
        String this$feature = this.getFeature();
        String other$feature = other.getFeature();
        if (this$feature == null ? other$feature != null : !this$feature.equals(other$feature)) {
            return false;
        }
        String this$featureType = this.getFeatureType();
        String other$featureType = other.getFeatureType();
        return !(this$featureType == null ? other$featureType != null : !this$featureType.equals(other$featureType));
    }

    protected boolean canEqual(Object other) {
        return other instanceof PersonSimilarImage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $url = this.getUrl();
        result = result * 59 + ($url == null ? 43 : $url.hashCode());
        String $binData = this.getBinData();
        result = result * 59 + ($binData == null ? 43 : $binData.hashCode());
        String $feature = this.getFeature();
        result = result * 59 + ($feature == null ? 43 : $feature.hashCode());
        String $featureType = this.getFeatureType();
        result = result * 59 + ($featureType == null ? 43 : $featureType.hashCode());
        return result;
    }

    public String toString() {
        return "PersonSimilarImage(url=" + this.getUrl() + ", binData=" + this.getBinData() + ", feature=" + this.getFeature() + ", featureType=" + this.getFeatureType() + ")";
    }
}
