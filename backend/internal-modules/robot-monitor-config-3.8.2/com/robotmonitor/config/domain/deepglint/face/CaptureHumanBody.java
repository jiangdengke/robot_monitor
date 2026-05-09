/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.robotmonitor.config.domain.deepglint.face;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CaptureHumanBody {
    @JsonProperty(value="Cts")
    private Long cts;
    @JsonProperty(value="HumanBodyID")
    private String humanBodyId;
    @JsonProperty(value="LogicDeviceID")
    private String logicDeviceId;
    @JsonProperty(value="UpperColor")
    private String upperColor;
    @JsonProperty(value="LowerColor")
    private String lowerColor;
    @JsonProperty(value="AgeRange")
    private String ageRange;
    @JsonProperty(value="HumanBodyGender")
    private String humanBodyGender;
    @JsonProperty(value="HairStyle")
    private String hairStyle;
    @JsonProperty(value="SleeveStyle")
    private String sleeveStyle;
    @JsonProperty(value="ImageUrl")
    private String imageUrl;
    @JsonProperty(value="CutboardImageUrl")
    private String cutboardImageUrl;
    @JsonProperty(value="CutboardX")
    private Integer cutboardX;
    @JsonProperty(value="CutboardY")
    private Integer cutboardY;
    @JsonProperty(value="CutboardWidth")
    private Integer cutboardWidth;
    @JsonProperty(value="CutboardHeight")
    private Integer cutboardHeight;
    @JsonProperty(value="PersonID")
    private String personId;
    @JsonProperty(value="DeviceGroupID")
    private String deviceGroupId;
    @JsonProperty(value="CaptureTime")
    private Long captureTime;
    @JsonProperty(value="OrigImageUrl")
    private String origImageUrl;
    @JsonProperty(value="OrigImageWidth")
    private Integer origImageWidth;
    @JsonProperty(value="OrigImageHeight")
    private Integer origImageHeight;
    @JsonProperty(value="CaptureX")
    private Integer captureX;
    @JsonProperty(value="CaptureY")
    private Integer captureY;
    @JsonProperty(value="CaptureWidth")
    private Integer captureWidth;
    @JsonProperty(value="CaptureHeight")
    private Integer captureHeight;

    public Long getCts() {
        return this.cts;
    }

    public void setCts(Long cts) {
        this.cts = cts;
    }

    public String getHumanBodyId() {
        return this.humanBodyId;
    }

    public void setHumanBodyId(String humanBodyId) {
        this.humanBodyId = humanBodyId;
    }

    public String getLogicDeviceId() {
        return this.logicDeviceId;
    }

    public void setLogicDeviceId(String logicDeviceId) {
        this.logicDeviceId = logicDeviceId;
    }

    public String getUpperColor() {
        return this.upperColor;
    }

    public void setUpperColor(String upperColor) {
        this.upperColor = upperColor;
    }

    public String getLowerColor() {
        return this.lowerColor;
    }

    public void setLowerColor(String lowerColor) {
        this.lowerColor = lowerColor;
    }

    public String getAgeRange() {
        return this.ageRange;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
    }

    public String getHumanBodyGender() {
        return this.humanBodyGender;
    }

    public void setHumanBodyGender(String humanBodyGender) {
        this.humanBodyGender = humanBodyGender;
    }

    public String getHairStyle() {
        return this.hairStyle;
    }

    public void setHairStyle(String hairStyle) {
        this.hairStyle = hairStyle;
    }

    public String getSleeveStyle() {
        return this.sleeveStyle;
    }

    public void setSleeveStyle(String sleeveStyle) {
        this.sleeveStyle = sleeveStyle;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCutboardImageUrl() {
        return this.cutboardImageUrl;
    }

    public void setCutboardImageUrl(String cutboardImageUrl) {
        this.cutboardImageUrl = cutboardImageUrl;
    }

    public Integer getCutboardX() {
        return this.cutboardX;
    }

    public void setCutboardX(Integer cutboardX) {
        this.cutboardX = cutboardX;
    }

    public Integer getCutboardY() {
        return this.cutboardY;
    }

    public void setCutboardY(Integer cutboardY) {
        this.cutboardY = cutboardY;
    }

    public Integer getCutboardWidth() {
        return this.cutboardWidth;
    }

    public void setCutboardWidth(Integer cutboardWidth) {
        this.cutboardWidth = cutboardWidth;
    }

    public Integer getCutboardHeight() {
        return this.cutboardHeight;
    }

    public void setCutboardHeight(Integer cutboardHeight) {
        this.cutboardHeight = cutboardHeight;
    }

    public String getPersonId() {
        return this.personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getDeviceGroupId() {
        return this.deviceGroupId;
    }

    public void setDeviceGroupId(String deviceGroupId) {
        this.deviceGroupId = deviceGroupId;
    }

    public Long getCaptureTime() {
        return this.captureTime;
    }

    public void setCaptureTime(Long captureTime) {
        this.captureTime = captureTime;
    }

    public String getOrigImageUrl() {
        return this.origImageUrl;
    }

    public void setOrigImageUrl(String origImageUrl) {
        this.origImageUrl = origImageUrl;
    }

    public Integer getOrigImageWidth() {
        return this.origImageWidth;
    }

    public void setOrigImageWidth(Integer origImageWidth) {
        this.origImageWidth = origImageWidth;
    }

    public Integer getOrigImageHeight() {
        return this.origImageHeight;
    }

    public void setOrigImageHeight(Integer origImageHeight) {
        this.origImageHeight = origImageHeight;
    }

    public Integer getCaptureX() {
        return this.captureX;
    }

    public void setCaptureX(Integer captureX) {
        this.captureX = captureX;
    }

    public Integer getCaptureY() {
        return this.captureY;
    }

    public void setCaptureY(Integer captureY) {
        this.captureY = captureY;
    }

    public Integer getCaptureWidth() {
        return this.captureWidth;
    }

    public void setCaptureWidth(Integer captureWidth) {
        this.captureWidth = captureWidth;
    }

    public Integer getCaptureHeight() {
        return this.captureHeight;
    }

    public void setCaptureHeight(Integer captureHeight) {
        this.captureHeight = captureHeight;
    }
}
