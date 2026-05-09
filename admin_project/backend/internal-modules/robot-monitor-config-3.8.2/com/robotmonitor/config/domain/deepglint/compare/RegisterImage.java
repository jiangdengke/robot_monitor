/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.robotmonitor.common.utils.StringUtils
 */
package com.robotmonitor.config.domain.deepglint.compare;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.robotmonitor.common.utils.StringUtils;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RegisterImage {
    @JsonProperty(value="URL")
    private String url;
    @JsonProperty(value="BinData")
    private String binData;
    @JsonProperty(value="ImgCompressTriggerVal")
    private Integer imgCompressTriggerVal;
    @JsonProperty(value="Feature")
    private String feature;

    public RegisterImage() {
    }

    public RegisterImage(String url) {
        this.url = url;
    }

    public RegisterImage(String url, String binData, Integer imgCompressTriggerVal, String feature) {
        this.url = url;
        this.binData = binData;
        this.imgCompressTriggerVal = imgCompressTriggerVal;
        this.feature = feature;
    }

    public static RegisterImage fromUrl(String url) {
        RegisterImage image = new RegisterImage();
        image.setUrl(url);
        return image;
    }

    public static RegisterImage fromBinData(String binData) {
        RegisterImage image = new RegisterImage();
        image.setBinData(binData);
        return image;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBinData() {
        return this.binData;
    }

    public void setBinData(String binData) {
        this.binData = binData;
    }

    public Integer getImgCompressTriggerVal() {
        return this.imgCompressTriggerVal;
    }

    public void setImgCompressTriggerVal(Integer imgCompressTriggerVal) {
        this.imgCompressTriggerVal = imgCompressTriggerVal;
    }

    public String getFeature() {
        return this.feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String toString() {
        return "RegisterImage{url='" + this.url + "', binData='" + StringUtils.abbreviate((String)this.binData, (int)50) + "', imgCompressTriggerVal=" + this.imgCompressTriggerVal + ", feature='" + this.feature + "'}";
    }
}
