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
package com.robotmonitor.config.domain.deepglint.facelist;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.robotmonitor.config.domain.deepglint.facelist.FaceListTag;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value={"Cts", "FaceID", "LogicDeviceId", "AlignScore", "Gender", "GenderConfidence", "Age", "AgeConfidence", "Hat", "HatConfidence", "Glass", "GlassConfidence", "Helmet", "HelmetConfidence", "Mask", "MaskConfidence", "SkinColor", "SkinColorConfidence", "FaceExpression", "FaceExpressionConfidence", "FaceBeauty", "FaceBeautyConfidence", "Temperature", "TemperatureConfidence", "ImageUrl", "CutboardImageUrl", "CutboardX", "CutboardY", "CutboardWidth", "CutboardHeight", "PersonID", "OriginPersonID", "TripID", "StartTime", "EndTime", "IsNew", "CaptureID", "DeviceGroupID", "CaptureTime", "OrigImageUrl", "OrigImageWidth", "OrigImageHeight", "CaptureX", "CaptureY", "CaptureWidth", "CaptureHeight", "HumanBodyID", "HumanBodyImageUrl", "HumanBodyCutboardX", "HumanBodyCutboardY", "HumanBodyCutboardWidth", "HumanBodyCutboardHeight", "HumanBodyOrigImageUrl", "HumanBodyOrigImageWidth", "HumanBodyOrigImageHeight", "HumanBodyCaptureX", "HumanBodyCaptureY", "HumanBodyCaptureWidth", "HumanBodyCaptureHeight", "DeviceID", "Tags"})
public class FaceListCaptureFace {
    @JsonProperty(value="Cts")
    private Long cts;
    @JsonProperty(value="FaceID")
    private String faceID;
    @JsonProperty(value="LogicDeviceId")
    private String logicDeviceId;
    @JsonProperty(value="AlignScore")
    private Float alignScore;
    @JsonProperty(value="Gender")
    private String gender;
    @JsonProperty(value="GenderConfidence")
    private Float genderConfidence;
    @JsonProperty(value="Age")
    private Integer age;
    @JsonProperty(value="AgeConfidence")
    private Float ageConfidence;
    @JsonProperty(value="Hat")
    private String hat;
    @JsonProperty(value="HatConfidence")
    private Float hatConfidence;
    @JsonProperty(value="Glass")
    private String glass;
    @JsonProperty(value="GlassConfidence")
    private Float glassConfidence;
    @JsonProperty(value="Helmet")
    private String helmet;
    @JsonProperty(value="HelmetConfidence")
    private Float helmetConfidence;
    @JsonProperty(value="Mask")
    private String mask;
    @JsonProperty(value="MaskConfidence")
    private Float maskConfidence;
    @JsonProperty(value="SkinColor")
    private String skinColor;
    @JsonProperty(value="SkinColorConfidence")
    private Float skinColorConfidence;
    @JsonProperty(value="FaceExpression")
    private String faceExpression;
    @JsonProperty(value="FaceExpressionConfidence")
    private Float faceExpressionConfidence;
    @JsonProperty(value="FaceBeauty")
    private Float faceBeauty;
    @JsonProperty(value="FaceBeautyConfidence")
    private Float faceBeautyConfidence;
    @JsonProperty(value="Temperature")
    private Float temperature;
    @JsonProperty(value="TemperatureConfidence")
    private Float temperatureConfidence;
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
    private String personID;
    @JsonProperty(value="OriginPersonID")
    private String originPersonID;
    @JsonProperty(value="TripID")
    private String tripID;
    @JsonProperty(value="StartTime")
    private Long startTime;
    @JsonProperty(value="EndTime")
    private Long endTime;
    @JsonProperty(value="IsNew")
    private Integer isNew;
    @JsonProperty(value="CaptureID")
    private String captureID;
    @JsonProperty(value="DeviceGroupID")
    private String deviceGroupID;
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
    @JsonProperty(value="HumanBodyID")
    private String humanBodyID;
    @JsonProperty(value="HumanBodyImageUrl")
    private String humanBodyImageUrl;
    @JsonProperty(value="HumanBodyCutboardX")
    private Integer humanBodyCutboardX;
    @JsonProperty(value="HumanBodyCutboardY")
    private Integer humanBodyCutboardY;
    @JsonProperty(value="HumanBodyCutboardWidth")
    private Integer humanBodyCutboardWidth;
    @JsonProperty(value="HumanBodyCutboardHeight")
    private Integer humanBodyCutboardHeight;
    @JsonProperty(value="HumanBodyOrigImageUrl")
    private String humanBodyOrigImageUrl;
    @JsonProperty(value="HumanBodyOrigImageWidth")
    private Integer humanBodyOrigImageWidth;
    @JsonProperty(value="HumanBodyOrigImageHeight")
    private Integer humanBodyOrigImageHeight;
    @JsonProperty(value="HumanBodyCaptureX")
    private Integer humanBodyCaptureX;
    @JsonProperty(value="HumanBodyCaptureY")
    private Integer humanBodyCaptureY;
    @JsonProperty(value="HumanBodyCaptureWidth")
    private Integer humanBodyCaptureWidth;
    @JsonProperty(value="HumanBodyCaptureHeight")
    private Integer humanBodyCaptureHeight;
    @JsonProperty(value="DeviceID")
    private String deviceID;
    @JsonProperty(value="Tags")
    private List<FaceListTag> tags = new ArrayList<FaceListTag>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty(value="Cts")
    public Long getCts() {
        return this.cts;
    }

    @JsonProperty(value="Cts")
    public void setCts(Long cts) {
        this.cts = cts;
    }

    @JsonProperty(value="FaceID")
    public String getFaceID() {
        return this.faceID;
    }

    @JsonProperty(value="FaceID")
    public void setFaceID(String faceID) {
        this.faceID = faceID;
    }

    @JsonProperty(value="LogicDeviceId")
    public String getLogicDeviceId() {
        return this.logicDeviceId;
    }

    @JsonProperty(value="LogicDeviceId")
    public void setLogicDeviceId(String logicDeviceId) {
        this.logicDeviceId = logicDeviceId;
    }

    @JsonProperty(value="AlignScore")
    public Float getAlignScore() {
        return this.alignScore;
    }

    @JsonProperty(value="AlignScore")
    public void setAlignScore(Float alignScore) {
        this.alignScore = alignScore;
    }

    @JsonProperty(value="Gender")
    public String getGender() {
        return this.gender;
    }

    @JsonProperty(value="Gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    @JsonProperty(value="GenderConfidence")
    public Float getGenderConfidence() {
        return this.genderConfidence;
    }

    @JsonProperty(value="GenderConfidence")
    public void setGenderConfidence(Float genderConfidence) {
        this.genderConfidence = genderConfidence;
    }

    @JsonProperty(value="Age")
    public Integer getAge() {
        return this.age;
    }

    @JsonProperty(value="Age")
    public void setAge(Integer age) {
        this.age = age;
    }

    @JsonProperty(value="AgeConfidence")
    public Float getAgeConfidence() {
        return this.ageConfidence;
    }

    @JsonProperty(value="AgeConfidence")
    public void setAgeConfidence(Float ageConfidence) {
        this.ageConfidence = ageConfidence;
    }

    @JsonProperty(value="Hat")
    public String getHat() {
        return this.hat;
    }

    @JsonProperty(value="Hat")
    public void setHat(String hat) {
        this.hat = hat;
    }

    @JsonProperty(value="HatConfidence")
    public Float getHatConfidence() {
        return this.hatConfidence;
    }

    @JsonProperty(value="HatConfidence")
    public void setHatConfidence(Float hatConfidence) {
        this.hatConfidence = hatConfidence;
    }

    @JsonProperty(value="Glass")
    public String getGlass() {
        return this.glass;
    }

    @JsonProperty(value="Glass")
    public void setGlass(String glass) {
        this.glass = glass;
    }

    @JsonProperty(value="GlassConfidence")
    public Float getGlassConfidence() {
        return this.glassConfidence;
    }

    @JsonProperty(value="GlassConfidence")
    public void setGlassConfidence(Float glassConfidence) {
        this.glassConfidence = glassConfidence;
    }

    @JsonProperty(value="Helmet")
    public String getHelmet() {
        return this.helmet;
    }

    @JsonProperty(value="Helmet")
    public void setHelmet(String helmet) {
        this.helmet = helmet;
    }

    @JsonProperty(value="HelmetConfidence")
    public Float getHelmetConfidence() {
        return this.helmetConfidence;
    }

    @JsonProperty(value="HelmetConfidence")
    public void setHelmetConfidence(Float helmetConfidence) {
        this.helmetConfidence = helmetConfidence;
    }

    @JsonProperty(value="Mask")
    public String getMask() {
        return this.mask;
    }

    @JsonProperty(value="Mask")
    public void setMask(String mask) {
        this.mask = mask;
    }

    @JsonProperty(value="MaskConfidence")
    public Float getMaskConfidence() {
        return this.maskConfidence;
    }

    @JsonProperty(value="MaskConfidence")
    public void setMaskConfidence(Float maskConfidence) {
        this.maskConfidence = maskConfidence;
    }

    @JsonProperty(value="SkinColor")
    public String getSkinColor() {
        return this.skinColor;
    }

    @JsonProperty(value="SkinColor")
    public void setSkinColor(String skinColor) {
        this.skinColor = skinColor;
    }

    @JsonProperty(value="SkinColorConfidence")
    public Float getSkinColorConfidence() {
        return this.skinColorConfidence;
    }

    @JsonProperty(value="SkinColorConfidence")
    public void setSkinColorConfidence(Float skinColorConfidence) {
        this.skinColorConfidence = skinColorConfidence;
    }

    @JsonProperty(value="FaceExpression")
    public String getFaceExpression() {
        return this.faceExpression;
    }

    @JsonProperty(value="FaceExpression")
    public void setFaceExpression(String faceExpression) {
        this.faceExpression = faceExpression;
    }

    @JsonProperty(value="FaceExpressionConfidence")
    public Float getFaceExpressionConfidence() {
        return this.faceExpressionConfidence;
    }

    @JsonProperty(value="FaceExpressionConfidence")
    public void setFaceExpressionConfidence(Float faceExpressionConfidence) {
        this.faceExpressionConfidence = faceExpressionConfidence;
    }

    @JsonProperty(value="FaceBeauty")
    public Float getFaceBeauty() {
        return this.faceBeauty;
    }

    @JsonProperty(value="FaceBeauty")
    public void setFaceBeauty(Float faceBeauty) {
        this.faceBeauty = faceBeauty;
    }

    @JsonProperty(value="FaceBeautyConfidence")
    public Float getFaceBeautyConfidence() {
        return this.faceBeautyConfidence;
    }

    @JsonProperty(value="FaceBeautyConfidence")
    public void setFaceBeautyConfidence(Float faceBeautyConfidence) {
        this.faceBeautyConfidence = faceBeautyConfidence;
    }

    @JsonProperty(value="Temperature")
    public Float getTemperature() {
        return this.temperature;
    }

    @JsonProperty(value="Temperature")
    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    @JsonProperty(value="TemperatureConfidence")
    public Float getTemperatureConfidence() {
        return this.temperatureConfidence;
    }

    @JsonProperty(value="TemperatureConfidence")
    public void setTemperatureConfidence(Float temperatureConfidence) {
        this.temperatureConfidence = temperatureConfidence;
    }

    @JsonProperty(value="ImageUrl")
    public String getImageUrl() {
        return this.imageUrl;
    }

    @JsonProperty(value="ImageUrl")
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @JsonProperty(value="CutboardImageUrl")
    public String getCutboardImageUrl() {
        return this.cutboardImageUrl;
    }

    @JsonProperty(value="CutboardImageUrl")
    public void setCutboardImageUrl(String cutboardImageUrl) {
        this.cutboardImageUrl = cutboardImageUrl;
    }

    @JsonProperty(value="CutboardX")
    public Integer getCutboardX() {
        return this.cutboardX;
    }

    @JsonProperty(value="CutboardX")
    public void setCutboardX(Integer cutboardX) {
        this.cutboardX = cutboardX;
    }

    @JsonProperty(value="CutboardY")
    public Integer getCutboardY() {
        return this.cutboardY;
    }

    @JsonProperty(value="CutboardY")
    public void setCutboardY(Integer cutboardY) {
        this.cutboardY = cutboardY;
    }

    @JsonProperty(value="CutboardWidth")
    public Integer getCutboardWidth() {
        return this.cutboardWidth;
    }

    @JsonProperty(value="CutboardWidth")
    public void setCutboardWidth(Integer cutboardWidth) {
        this.cutboardWidth = cutboardWidth;
    }

    @JsonProperty(value="CutboardHeight")
    public Integer getCutboardHeight() {
        return this.cutboardHeight;
    }

    @JsonProperty(value="CutboardHeight")
    public void setCutboardHeight(Integer cutboardHeight) {
        this.cutboardHeight = cutboardHeight;
    }

    @JsonProperty(value="PersonID")
    public String getPersonID() {
        return this.personID;
    }

    @JsonProperty(value="PersonID")
    public void setPersonID(String personID) {
        this.personID = personID;
    }

    @JsonProperty(value="OriginPersonID")
    public String getOriginPersonID() {
        return this.originPersonID;
    }

    @JsonProperty(value="OriginPersonID")
    public void setOriginPersonID(String originPersonID) {
        this.originPersonID = originPersonID;
    }

    @JsonProperty(value="TripID")
    public String getTripID() {
        return this.tripID;
    }

    @JsonProperty(value="TripID")
    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    @JsonProperty(value="StartTime")
    public Long getStartTime() {
        return this.startTime;
    }

    @JsonProperty(value="StartTime")
    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    @JsonProperty(value="EndTime")
    public Long getEndTime() {
        return this.endTime;
    }

    @JsonProperty(value="EndTime")
    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    @JsonProperty(value="IsNew")
    public Integer getIsNew() {
        return this.isNew;
    }

    @JsonProperty(value="IsNew")
    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    @JsonProperty(value="CaptureID")
    public String getCaptureID() {
        return this.captureID;
    }

    @JsonProperty(value="CaptureID")
    public void setCaptureID(String captureID) {
        this.captureID = captureID;
    }

    @JsonProperty(value="DeviceGroupID")
    public String getDeviceGroupID() {
        return this.deviceGroupID;
    }

    @JsonProperty(value="DeviceGroupID")
    public void setDeviceGroupID(String deviceGroupID) {
        this.deviceGroupID = deviceGroupID;
    }

    @JsonProperty(value="CaptureTime")
    public Long getCaptureTime() {
        return this.captureTime;
    }

    @JsonProperty(value="CaptureTime")
    public void setCaptureTime(Long captureTime) {
        this.captureTime = captureTime;
    }

    @JsonProperty(value="OrigImageUrl")
    public String getOrigImageUrl() {
        return this.origImageUrl;
    }

    @JsonProperty(value="OrigImageUrl")
    public void setOrigImageUrl(String origImageUrl) {
        this.origImageUrl = origImageUrl;
    }

    @JsonProperty(value="OrigImageWidth")
    public Integer getOrigImageWidth() {
        return this.origImageWidth;
    }

    @JsonProperty(value="OrigImageWidth")
    public void setOrigImageWidth(Integer origImageWidth) {
        this.origImageWidth = origImageWidth;
    }

    @JsonProperty(value="OrigImageHeight")
    public Integer getOrigImageHeight() {
        return this.origImageHeight;
    }

    @JsonProperty(value="OrigImageHeight")
    public void setOrigImageHeight(Integer origImageHeight) {
        this.origImageHeight = origImageHeight;
    }

    @JsonProperty(value="CaptureX")
    public Integer getCaptureX() {
        return this.captureX;
    }

    @JsonProperty(value="CaptureX")
    public void setCaptureX(Integer captureX) {
        this.captureX = captureX;
    }

    @JsonProperty(value="CaptureY")
    public Integer getCaptureY() {
        return this.captureY;
    }

    @JsonProperty(value="CaptureY")
    public void setCaptureY(Integer captureY) {
        this.captureY = captureY;
    }

    @JsonProperty(value="CaptureWidth")
    public Integer getCaptureWidth() {
        return this.captureWidth;
    }

    @JsonProperty(value="CaptureWidth")
    public void setCaptureWidth(Integer captureWidth) {
        this.captureWidth = captureWidth;
    }

    @JsonProperty(value="CaptureHeight")
    public Integer getCaptureHeight() {
        return this.captureHeight;
    }

    @JsonProperty(value="CaptureHeight")
    public void setCaptureHeight(Integer captureHeight) {
        this.captureHeight = captureHeight;
    }

    @JsonProperty(value="HumanBodyID")
    public String getHumanBodyID() {
        return this.humanBodyID;
    }

    @JsonProperty(value="HumanBodyID")
    public void setHumanBodyID(String humanBodyID) {
        this.humanBodyID = humanBodyID;
    }

    @JsonProperty(value="HumanBodyImageUrl")
    public String getHumanBodyImageUrl() {
        return this.humanBodyImageUrl;
    }

    @JsonProperty(value="HumanBodyImageUrl")
    public void setHumanBodyImageUrl(String humanBodyImageUrl) {
        this.humanBodyImageUrl = humanBodyImageUrl;
    }

    @JsonProperty(value="HumanBodyCutboardX")
    public Integer getHumanBodyCutboardX() {
        return this.humanBodyCutboardX;
    }

    @JsonProperty(value="HumanBodyCutboardX")
    public void setHumanBodyCutboardX(Integer humanBodyCutboardX) {
        this.humanBodyCutboardX = humanBodyCutboardX;
    }

    @JsonProperty(value="HumanBodyCutboardY")
    public Integer getHumanBodyCutboardY() {
        return this.humanBodyCutboardY;
    }

    @JsonProperty(value="HumanBodyCutboardY")
    public void setHumanBodyCutboardY(Integer humanBodyCutboardY) {
        this.humanBodyCutboardY = humanBodyCutboardY;
    }

    @JsonProperty(value="HumanBodyCutboardWidth")
    public Integer getHumanBodyCutboardWidth() {
        return this.humanBodyCutboardWidth;
    }

    @JsonProperty(value="HumanBodyCutboardWidth")
    public void setHumanBodyCutboardWidth(Integer humanBodyCutboardWidth) {
        this.humanBodyCutboardWidth = humanBodyCutboardWidth;
    }

    @JsonProperty(value="HumanBodyCutboardHeight")
    public Integer getHumanBodyCutboardHeight() {
        return this.humanBodyCutboardHeight;
    }

    @JsonProperty(value="HumanBodyCutboardHeight")
    public void setHumanBodyCutboardHeight(Integer humanBodyCutboardHeight) {
        this.humanBodyCutboardHeight = humanBodyCutboardHeight;
    }

    @JsonProperty(value="HumanBodyOrigImageUrl")
    public String getHumanBodyOrigImageUrl() {
        return this.humanBodyOrigImageUrl;
    }

    @JsonProperty(value="HumanBodyOrigImageUrl")
    public void setHumanBodyOrigImageUrl(String humanBodyOrigImageUrl) {
        this.humanBodyOrigImageUrl = humanBodyOrigImageUrl;
    }

    @JsonProperty(value="HumanBodyOrigImageWidth")
    public Integer getHumanBodyOrigImageWidth() {
        return this.humanBodyOrigImageWidth;
    }

    @JsonProperty(value="HumanBodyOrigImageWidth")
    public void setHumanBodyOrigImageWidth(Integer humanBodyOrigImageWidth) {
        this.humanBodyOrigImageWidth = humanBodyOrigImageWidth;
    }

    @JsonProperty(value="HumanBodyOrigImageHeight")
    public Integer getHumanBodyOrigImageHeight() {
        return this.humanBodyOrigImageHeight;
    }

    @JsonProperty(value="HumanBodyOrigImageHeight")
    public void setHumanBodyOrigImageHeight(Integer humanBodyOrigImageHeight) {
        this.humanBodyOrigImageHeight = humanBodyOrigImageHeight;
    }

    @JsonProperty(value="HumanBodyCaptureX")
    public Integer getHumanBodyCaptureX() {
        return this.humanBodyCaptureX;
    }

    @JsonProperty(value="HumanBodyCaptureX")
    public void setHumanBodyCaptureX(Integer humanBodyCaptureX) {
        this.humanBodyCaptureX = humanBodyCaptureX;
    }

    @JsonProperty(value="HumanBodyCaptureY")
    public Integer getHumanBodyCaptureY() {
        return this.humanBodyCaptureY;
    }

    @JsonProperty(value="HumanBodyCaptureY")
    public void setHumanBodyCaptureY(Integer humanBodyCaptureY) {
        this.humanBodyCaptureY = humanBodyCaptureY;
    }

    @JsonProperty(value="HumanBodyCaptureWidth")
    public Integer getHumanBodyCaptureWidth() {
        return this.humanBodyCaptureWidth;
    }

    @JsonProperty(value="HumanBodyCaptureWidth")
    public void setHumanBodyCaptureWidth(Integer humanBodyCaptureWidth) {
        this.humanBodyCaptureWidth = humanBodyCaptureWidth;
    }

    @JsonProperty(value="HumanBodyCaptureHeight")
    public Integer getHumanBodyCaptureHeight() {
        return this.humanBodyCaptureHeight;
    }

    @JsonProperty(value="HumanBodyCaptureHeight")
    public void setHumanBodyCaptureHeight(Integer humanBodyCaptureHeight) {
        this.humanBodyCaptureHeight = humanBodyCaptureHeight;
    }

    @JsonProperty(value="DeviceID")
    public String getDeviceID() {
        return this.deviceID;
    }

    @JsonProperty(value="DeviceID")
    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    @JsonProperty(value="Tags")
    public List<FaceListTag> getTags() {
        return this.tags;
    }

    @JsonProperty(value="Tags")
    public void setTags(List<FaceListTag> tags) {
        this.tags = tags;
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
        sb.append(FaceListCaptureFace.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("cts");
        sb.append('=');
        sb.append(this.cts == null ? "<null>" : this.cts);
        sb.append(',');
        sb.append("faceID");
        sb.append('=');
        sb.append(this.faceID == null ? "<null>" : this.faceID);
        sb.append(',');
        sb.append("logicDeviceId");
        sb.append('=');
        sb.append(this.logicDeviceId == null ? "<null>" : this.logicDeviceId);
        sb.append(',');
        sb.append("alignScore");
        sb.append('=');
        sb.append(this.alignScore == null ? "<null>" : this.alignScore);
        sb.append(',');
        sb.append("gender");
        sb.append('=');
        sb.append(this.gender == null ? "<null>" : this.gender);
        sb.append(',');
        sb.append("genderConfidence");
        sb.append('=');
        sb.append(this.genderConfidence == null ? "<null>" : this.genderConfidence);
        sb.append(',');
        sb.append("age");
        sb.append('=');
        sb.append(this.age == null ? "<null>" : this.age);
        sb.append(',');
        sb.append("ageConfidence");
        sb.append('=');
        sb.append(this.ageConfidence == null ? "<null>" : this.ageConfidence);
        sb.append(',');
        sb.append("hat");
        sb.append('=');
        sb.append(this.hat == null ? "<null>" : this.hat);
        sb.append(',');
        sb.append("hatConfidence");
        sb.append('=');
        sb.append(this.hatConfidence == null ? "<null>" : this.hatConfidence);
        sb.append(',');
        sb.append("glass");
        sb.append('=');
        sb.append(this.glass == null ? "<null>" : this.glass);
        sb.append(',');
        sb.append("glassConfidence");
        sb.append('=');
        sb.append(this.glassConfidence == null ? "<null>" : this.glassConfidence);
        sb.append(',');
        sb.append("helmet");
        sb.append('=');
        sb.append(this.helmet == null ? "<null>" : this.helmet);
        sb.append(',');
        sb.append("helmetConfidence");
        sb.append('=');
        sb.append(this.helmetConfidence == null ? "<null>" : this.helmetConfidence);
        sb.append(',');
        sb.append("mask");
        sb.append('=');
        sb.append(this.mask == null ? "<null>" : this.mask);
        sb.append(',');
        sb.append("maskConfidence");
        sb.append('=');
        sb.append(this.maskConfidence == null ? "<null>" : this.maskConfidence);
        sb.append(',');
        sb.append("skinColor");
        sb.append('=');
        sb.append(this.skinColor == null ? "<null>" : this.skinColor);
        sb.append(',');
        sb.append("skinColorConfidence");
        sb.append('=');
        sb.append(this.skinColorConfidence == null ? "<null>" : this.skinColorConfidence);
        sb.append(',');
        sb.append("faceExpression");
        sb.append('=');
        sb.append(this.faceExpression == null ? "<null>" : this.faceExpression);
        sb.append(',');
        sb.append("faceExpressionConfidence");
        sb.append('=');
        sb.append(this.faceExpressionConfidence == null ? "<null>" : this.faceExpressionConfidence);
        sb.append(',');
        sb.append("faceBeauty");
        sb.append('=');
        sb.append(this.faceBeauty == null ? "<null>" : this.faceBeauty);
        sb.append(',');
        sb.append("faceBeautyConfidence");
        sb.append('=');
        sb.append(this.faceBeautyConfidence == null ? "<null>" : this.faceBeautyConfidence);
        sb.append(',');
        sb.append("temperature");
        sb.append('=');
        sb.append(this.temperature == null ? "<null>" : this.temperature);
        sb.append(',');
        sb.append("temperatureConfidence");
        sb.append('=');
        sb.append(this.temperatureConfidence == null ? "<null>" : this.temperatureConfidence);
        sb.append(',');
        sb.append("imageUrl");
        sb.append('=');
        sb.append(this.imageUrl == null ? "<null>" : this.imageUrl);
        sb.append(',');
        sb.append("cutboardImageUrl");
        sb.append('=');
        sb.append(this.cutboardImageUrl == null ? "<null>" : this.cutboardImageUrl);
        sb.append(',');
        sb.append("cutboardX");
        sb.append('=');
        sb.append(this.cutboardX == null ? "<null>" : this.cutboardX);
        sb.append(',');
        sb.append("cutboardY");
        sb.append('=');
        sb.append(this.cutboardY == null ? "<null>" : this.cutboardY);
        sb.append(',');
        sb.append("cutboardWidth");
        sb.append('=');
        sb.append(this.cutboardWidth == null ? "<null>" : this.cutboardWidth);
        sb.append(',');
        sb.append("cutboardHeight");
        sb.append('=');
        sb.append(this.cutboardHeight == null ? "<null>" : this.cutboardHeight);
        sb.append(',');
        sb.append("personID");
        sb.append('=');
        sb.append(this.personID == null ? "<null>" : this.personID);
        sb.append(',');
        sb.append("originPersonID");
        sb.append('=');
        sb.append(this.originPersonID == null ? "<null>" : this.originPersonID);
        sb.append(',');
        sb.append("tripID");
        sb.append('=');
        sb.append(this.tripID == null ? "<null>" : this.tripID);
        sb.append(',');
        sb.append("startTime");
        sb.append('=');
        sb.append(this.startTime == null ? "<null>" : this.startTime);
        sb.append(',');
        sb.append("endTime");
        sb.append('=');
        sb.append(this.endTime == null ? "<null>" : this.endTime);
        sb.append(',');
        sb.append("isNew");
        sb.append('=');
        sb.append(this.isNew == null ? "<null>" : this.isNew);
        sb.append(',');
        sb.append("captureID");
        sb.append('=');
        sb.append(this.captureID == null ? "<null>" : this.captureID);
        sb.append(',');
        sb.append("deviceGroupID");
        sb.append('=');
        sb.append(this.deviceGroupID == null ? "<null>" : this.deviceGroupID);
        sb.append(',');
        sb.append("captureTime");
        sb.append('=');
        sb.append(this.captureTime == null ? "<null>" : this.captureTime);
        sb.append(',');
        sb.append("origImageUrl");
        sb.append('=');
        sb.append(this.origImageUrl == null ? "<null>" : this.origImageUrl);
        sb.append(',');
        sb.append("origImageWidth");
        sb.append('=');
        sb.append(this.origImageWidth == null ? "<null>" : this.origImageWidth);
        sb.append(',');
        sb.append("origImageHeight");
        sb.append('=');
        sb.append(this.origImageHeight == null ? "<null>" : this.origImageHeight);
        sb.append(',');
        sb.append("captureX");
        sb.append('=');
        sb.append(this.captureX == null ? "<null>" : this.captureX);
        sb.append(',');
        sb.append("captureY");
        sb.append('=');
        sb.append(this.captureY == null ? "<null>" : this.captureY);
        sb.append(',');
        sb.append("captureWidth");
        sb.append('=');
        sb.append(this.captureWidth == null ? "<null>" : this.captureWidth);
        sb.append(',');
        sb.append("captureHeight");
        sb.append('=');
        sb.append(this.captureHeight == null ? "<null>" : this.captureHeight);
        sb.append(',');
        sb.append("humanBodyID");
        sb.append('=');
        sb.append(this.humanBodyID == null ? "<null>" : this.humanBodyID);
        sb.append(',');
        sb.append("humanBodyImageUrl");
        sb.append('=');
        sb.append(this.humanBodyImageUrl == null ? "<null>" : this.humanBodyImageUrl);
        sb.append(',');
        sb.append("humanBodyCutboardX");
        sb.append('=');
        sb.append(this.humanBodyCutboardX == null ? "<null>" : this.humanBodyCutboardX);
        sb.append(',');
        sb.append("humanBodyCutboardY");
        sb.append('=');
        sb.append(this.humanBodyCutboardY == null ? "<null>" : this.humanBodyCutboardY);
        sb.append(',');
        sb.append("humanBodyCutboardWidth");
        sb.append('=');
        sb.append(this.humanBodyCutboardWidth == null ? "<null>" : this.humanBodyCutboardWidth);
        sb.append(',');
        sb.append("humanBodyCutboardHeight");
        sb.append('=');
        sb.append(this.humanBodyCutboardHeight == null ? "<null>" : this.humanBodyCutboardHeight);
        sb.append(',');
        sb.append("humanBodyOrigImageUrl");
        sb.append('=');
        sb.append(this.humanBodyOrigImageUrl == null ? "<null>" : this.humanBodyOrigImageUrl);
        sb.append(',');
        sb.append("humanBodyOrigImageWidth");
        sb.append('=');
        sb.append(this.humanBodyOrigImageWidth == null ? "<null>" : this.humanBodyOrigImageWidth);
        sb.append(',');
        sb.append("humanBodyOrigImageHeight");
        sb.append('=');
        sb.append(this.humanBodyOrigImageHeight == null ? "<null>" : this.humanBodyOrigImageHeight);
        sb.append(',');
        sb.append("humanBodyCaptureX");
        sb.append('=');
        sb.append(this.humanBodyCaptureX == null ? "<null>" : this.humanBodyCaptureX);
        sb.append(',');
        sb.append("humanBodyCaptureY");
        sb.append('=');
        sb.append(this.humanBodyCaptureY == null ? "<null>" : this.humanBodyCaptureY);
        sb.append(',');
        sb.append("humanBodyCaptureWidth");
        sb.append('=');
        sb.append(this.humanBodyCaptureWidth == null ? "<null>" : this.humanBodyCaptureWidth);
        sb.append(',');
        sb.append("humanBodyCaptureHeight");
        sb.append('=');
        sb.append(this.humanBodyCaptureHeight == null ? "<null>" : this.humanBodyCaptureHeight);
        sb.append(',');
        sb.append("deviceID");
        sb.append('=');
        sb.append(this.deviceID == null ? "<null>" : this.deviceID);
        sb.append(',');
        sb.append("tags");
        sb.append('=');
        sb.append(this.tags == null ? "<null>" : this.tags);
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
        result = result * 31 + (this.temperatureConfidence == null ? 0 : this.temperatureConfidence.hashCode());
        result = result * 31 + (this.skinColor == null ? 0 : this.skinColor.hashCode());
        result = result * 31 + (this.captureTime == null ? 0 : this.captureTime.hashCode());
        result = result * 31 + (this.humanBodyCutboardHeight == null ? 0 : this.humanBodyCutboardHeight.hashCode());
        result = result * 31 + (this.cutboardImageUrl == null ? 0 : this.cutboardImageUrl.hashCode());
        result = result * 31 + (this.origImageWidth == null ? 0 : this.origImageWidth.hashCode());
        result = result * 31 + (this.humanBodyID == null ? 0 : this.humanBodyID.hashCode());
        result = result * 31 + (this.hat == null ? 0 : this.hat.hashCode());
        result = result * 31 + (this.maskConfidence == null ? 0 : this.maskConfidence.hashCode());
        result = result * 31 + (this.glass == null ? 0 : this.glass.hashCode());
        result = result * 31 + (this.cts == null ? 0 : this.cts.hashCode());
        result = result * 31 + (this.alignScore == null ? 0 : this.alignScore.hashCode());
        result = result * 31 + (this.humanBodyImageUrl == null ? 0 : this.humanBodyImageUrl.hashCode());
        result = result * 31 + (this.faceID == null ? 0 : this.faceID.hashCode());
        result = result * 31 + (this.humanBodyOrigImageUrl == null ? 0 : this.humanBodyOrigImageUrl.hashCode());
        result = result * 31 + (this.humanBodyOrigImageHeight == null ? 0 : this.humanBodyOrigImageHeight.hashCode());
        result = result * 31 + (this.tags == null ? 0 : this.tags.hashCode());
        result = result * 31 + (this.humanBodyCutboardY == null ? 0 : this.humanBodyCutboardY.hashCode());
        result = result * 31 + (this.humanBodyCaptureX == null ? 0 : this.humanBodyCaptureX.hashCode());
        result = result * 31 + (this.humanBodyCutboardX == null ? 0 : this.humanBodyCutboardX.hashCode());
        result = result * 31 + (this.humanBodyCaptureY == null ? 0 : this.humanBodyCaptureY.hashCode());
        result = result * 31 + (this.humanBodyCaptureHeight == null ? 0 : this.humanBodyCaptureHeight.hashCode());
        result = result * 31 + (this.additionalProperties == null ? 0 : this.additionalProperties.hashCode());
        result = result * 31 + (this.genderConfidence == null ? 0 : this.genderConfidence.hashCode());
        result = result * 31 + (this.gender == null ? 0 : this.gender.hashCode());
        result = result * 31 + (this.faceBeautyConfidence == null ? 0 : this.faceBeautyConfidence.hashCode());
        result = result * 31 + (this.humanBodyCutboardWidth == null ? 0 : this.humanBodyCutboardWidth.hashCode());
        result = result * 31 + (this.skinColorConfidence == null ? 0 : this.skinColorConfidence.hashCode());
        result = result * 31 + (this.faceExpressionConfidence == null ? 0 : this.faceExpressionConfidence.hashCode());
        result = result * 31 + (this.cutboardX == null ? 0 : this.cutboardX.hashCode());
        result = result * 31 + (this.cutboardY == null ? 0 : this.cutboardY.hashCode());
        result = result * 31 + (this.faceBeauty == null ? 0 : this.faceBeauty.hashCode());
        result = result * 31 + (this.logicDeviceId == null ? 0 : this.logicDeviceId.hashCode());
        result = result * 31 + (this.origImageUrl == null ? 0 : this.origImageUrl.hashCode());
        result = result * 31 + (this.helmet == null ? 0 : this.helmet.hashCode());
        result = result * 31 + (this.imageUrl == null ? 0 : this.imageUrl.hashCode());
        result = result * 31 + (this.temperature == null ? 0 : this.temperature.hashCode());
        result = result * 31 + (this.originPersonID == null ? 0 : this.originPersonID.hashCode());
        result = result * 31 + (this.personID == null ? 0 : this.personID.hashCode());
        result = result * 31 + (this.startTime == null ? 0 : this.startTime.hashCode());
        result = result * 31 + (this.captureY == null ? 0 : this.captureY.hashCode());
        result = result * 31 + (this.captureX == null ? 0 : this.captureX.hashCode());
        result = result * 31 + (this.captureHeight == null ? 0 : this.captureHeight.hashCode());
        result = result * 31 + (this.mask == null ? 0 : this.mask.hashCode());
        result = result * 31 + (this.cutboardHeight == null ? 0 : this.cutboardHeight.hashCode());
        result = result * 31 + (this.glassConfidence == null ? 0 : this.glassConfidence.hashCode());
        result = result * 31 + (this.captureWidth == null ? 0 : this.captureWidth.hashCode());
        result = result * 31 + (this.humanBodyCaptureWidth == null ? 0 : this.humanBodyCaptureWidth.hashCode());
        result = result * 31 + (this.tripID == null ? 0 : this.tripID.hashCode());
        result = result * 31 + (this.faceExpression == null ? 0 : this.faceExpression.hashCode());
        result = result * 31 + (this.isNew == null ? 0 : this.isNew.hashCode());
        result = result * 31 + (this.deviceID == null ? 0 : this.deviceID.hashCode());
        result = result * 31 + (this.hatConfidence == null ? 0 : this.hatConfidence.hashCode());
        result = result * 31 + (this.ageConfidence == null ? 0 : this.ageConfidence.hashCode());
        result = result * 31 + (this.helmetConfidence == null ? 0 : this.helmetConfidence.hashCode());
        result = result * 31 + (this.humanBodyOrigImageWidth == null ? 0 : this.humanBodyOrigImageWidth.hashCode());
        result = result * 31 + (this.origImageHeight == null ? 0 : this.origImageHeight.hashCode());
        result = result * 31 + (this.deviceGroupID == null ? 0 : this.deviceGroupID.hashCode());
        result = result * 31 + (this.captureID == null ? 0 : this.captureID.hashCode());
        result = result * 31 + (this.endTime == null ? 0 : this.endTime.hashCode());
        result = result * 31 + (this.cutboardWidth == null ? 0 : this.cutboardWidth.hashCode());
        result = result * 31 + (this.age == null ? 0 : this.age.hashCode());
        return result;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof FaceListCaptureFace)) {
            return false;
        }
        FaceListCaptureFace rhs = (FaceListCaptureFace)other;
        return (this.temperatureConfidence == rhs.temperatureConfidence || this.temperatureConfidence != null && this.temperatureConfidence.equals(rhs.temperatureConfidence)) && (this.skinColor == rhs.skinColor || this.skinColor != null && this.skinColor.equals(rhs.skinColor)) && (this.captureTime == rhs.captureTime || this.captureTime != null && this.captureTime.equals(rhs.captureTime)) && (this.humanBodyCutboardHeight == rhs.humanBodyCutboardHeight || this.humanBodyCutboardHeight != null && this.humanBodyCutboardHeight.equals(rhs.humanBodyCutboardHeight)) && (this.cutboardImageUrl == rhs.cutboardImageUrl || this.cutboardImageUrl != null && this.cutboardImageUrl.equals(rhs.cutboardImageUrl)) && (this.origImageWidth == rhs.origImageWidth || this.origImageWidth != null && this.origImageWidth.equals(rhs.origImageWidth)) && (this.humanBodyID == rhs.humanBodyID || this.humanBodyID != null && this.humanBodyID.equals(rhs.humanBodyID)) && (this.hat == rhs.hat || this.hat != null && this.hat.equals(rhs.hat)) && (this.maskConfidence == rhs.maskConfidence || this.maskConfidence != null && this.maskConfidence.equals(rhs.maskConfidence)) && (this.glass == rhs.glass || this.glass != null && this.glass.equals(rhs.glass)) && (this.cts == rhs.cts || this.cts != null && this.cts.equals(rhs.cts)) && (this.alignScore == rhs.alignScore || this.alignScore != null && this.alignScore.equals(rhs.alignScore)) && (this.humanBodyImageUrl == rhs.humanBodyImageUrl || this.humanBodyImageUrl != null && this.humanBodyImageUrl.equals(rhs.humanBodyImageUrl)) && (this.faceID == rhs.faceID || this.faceID != null && this.faceID.equals(rhs.faceID)) && (this.humanBodyOrigImageUrl == rhs.humanBodyOrigImageUrl || this.humanBodyOrigImageUrl != null && this.humanBodyOrigImageUrl.equals(rhs.humanBodyOrigImageUrl)) && (this.humanBodyOrigImageHeight == rhs.humanBodyOrigImageHeight || this.humanBodyOrigImageHeight != null && this.humanBodyOrigImageHeight.equals(rhs.humanBodyOrigImageHeight)) && (this.tags == rhs.tags || this.tags != null && this.tags.equals(rhs.tags)) && (this.humanBodyCutboardY == rhs.humanBodyCutboardY || this.humanBodyCutboardY != null && this.humanBodyCutboardY.equals(rhs.humanBodyCutboardY)) && (this.humanBodyCaptureX == rhs.humanBodyCaptureX || this.humanBodyCaptureX != null && this.humanBodyCaptureX.equals(rhs.humanBodyCaptureX)) && (this.humanBodyCutboardX == rhs.humanBodyCutboardX || this.humanBodyCutboardX != null && this.humanBodyCutboardX.equals(rhs.humanBodyCutboardX)) && (this.humanBodyCaptureY == rhs.humanBodyCaptureY || this.humanBodyCaptureY != null && this.humanBodyCaptureY.equals(rhs.humanBodyCaptureY)) && (this.humanBodyCaptureHeight == rhs.humanBodyCaptureHeight || this.humanBodyCaptureHeight != null && this.humanBodyCaptureHeight.equals(rhs.humanBodyCaptureHeight)) && (this.additionalProperties == rhs.additionalProperties || this.additionalProperties != null && this.additionalProperties.equals(rhs.additionalProperties)) && (this.genderConfidence == rhs.genderConfidence || this.genderConfidence != null && this.genderConfidence.equals(rhs.genderConfidence)) && (this.gender == rhs.gender || this.gender != null && this.gender.equals(rhs.gender)) && (this.faceBeautyConfidence == rhs.faceBeautyConfidence || this.faceBeautyConfidence != null && this.faceBeautyConfidence.equals(rhs.faceBeautyConfidence)) && (this.humanBodyCutboardWidth == rhs.humanBodyCutboardWidth || this.humanBodyCutboardWidth != null && this.humanBodyCutboardWidth.equals(rhs.humanBodyCutboardWidth)) && (this.skinColorConfidence == rhs.skinColorConfidence || this.skinColorConfidence != null && this.skinColorConfidence.equals(rhs.skinColorConfidence)) && (this.faceExpressionConfidence == rhs.faceExpressionConfidence || this.faceExpressionConfidence != null && this.faceExpressionConfidence.equals(rhs.faceExpressionConfidence)) && (this.cutboardX == rhs.cutboardX || this.cutboardX != null && this.cutboardX.equals(rhs.cutboardX)) && (this.cutboardY == rhs.cutboardY || this.cutboardY != null && this.cutboardY.equals(rhs.cutboardY)) && (this.faceBeauty == rhs.faceBeauty || this.faceBeauty != null && this.faceBeauty.equals(rhs.faceBeauty)) && (this.logicDeviceId == rhs.logicDeviceId || this.logicDeviceId != null && this.logicDeviceId.equals(rhs.logicDeviceId)) && (this.origImageUrl == rhs.origImageUrl || this.origImageUrl != null && this.origImageUrl.equals(rhs.origImageUrl)) && (this.helmet == rhs.helmet || this.helmet != null && this.helmet.equals(rhs.helmet)) && (this.imageUrl == rhs.imageUrl || this.imageUrl != null && this.imageUrl.equals(rhs.imageUrl)) && (this.temperature == rhs.temperature || this.temperature != null && this.temperature.equals(rhs.temperature)) && (this.originPersonID == rhs.originPersonID || this.originPersonID != null && this.originPersonID.equals(rhs.originPersonID)) && (this.personID == rhs.personID || this.personID != null && this.personID.equals(rhs.personID)) && (this.startTime == rhs.startTime || this.startTime != null && this.startTime.equals(rhs.startTime)) && (this.captureY == rhs.captureY || this.captureY != null && this.captureY.equals(rhs.captureY)) && (this.captureX == rhs.captureX || this.captureX != null && this.captureX.equals(rhs.captureX)) && (this.captureHeight == rhs.captureHeight || this.captureHeight != null && this.captureHeight.equals(rhs.captureHeight)) && (this.mask == rhs.mask || this.mask != null && this.mask.equals(rhs.mask)) && (this.cutboardHeight == rhs.cutboardHeight || this.cutboardHeight != null && this.cutboardHeight.equals(rhs.cutboardHeight)) && (this.glassConfidence == rhs.glassConfidence || this.glassConfidence != null && this.glassConfidence.equals(rhs.glassConfidence)) && (this.captureWidth == rhs.captureWidth || this.captureWidth != null && this.captureWidth.equals(rhs.captureWidth)) && (this.humanBodyCaptureWidth == rhs.humanBodyCaptureWidth || this.humanBodyCaptureWidth != null && this.humanBodyCaptureWidth.equals(rhs.humanBodyCaptureWidth)) && (this.tripID == rhs.tripID || this.tripID != null && this.tripID.equals(rhs.tripID)) && (this.faceExpression == rhs.faceExpression || this.faceExpression != null && this.faceExpression.equals(rhs.faceExpression)) && (this.isNew == rhs.isNew || this.isNew != null && this.isNew.equals(rhs.isNew)) && (this.deviceID == rhs.deviceID || this.deviceID != null && this.deviceID.equals(rhs.deviceID)) && (this.hatConfidence == rhs.hatConfidence || this.hatConfidence != null && this.hatConfidence.equals(rhs.hatConfidence)) && (this.ageConfidence == rhs.ageConfidence || this.ageConfidence != null && this.ageConfidence.equals(rhs.ageConfidence)) && (this.helmetConfidence == rhs.helmetConfidence || this.helmetConfidence != null && this.helmetConfidence.equals(rhs.helmetConfidence)) && (this.humanBodyOrigImageWidth == rhs.humanBodyOrigImageWidth || this.humanBodyOrigImageWidth != null && this.humanBodyOrigImageWidth.equals(rhs.humanBodyOrigImageWidth)) && (this.origImageHeight == rhs.origImageHeight || this.origImageHeight != null && this.origImageHeight.equals(rhs.origImageHeight)) && (this.deviceGroupID == rhs.deviceGroupID || this.deviceGroupID != null && this.deviceGroupID.equals(rhs.deviceGroupID)) && (this.captureID == rhs.captureID || this.captureID != null && this.captureID.equals(rhs.captureID)) && (this.endTime == rhs.endTime || this.endTime != null && this.endTime.equals(rhs.endTime)) && (this.cutboardWidth == rhs.cutboardWidth || this.cutboardWidth != null && this.cutboardWidth.equals(rhs.cutboardWidth)) && (this.age == rhs.age || this.age != null && this.age.equals(rhs.age));
    }
}
