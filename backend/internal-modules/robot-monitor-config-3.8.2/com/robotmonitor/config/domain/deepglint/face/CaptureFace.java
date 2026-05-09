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
import com.robotmonitor.config.domain.deepglint.face.HitTag;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CaptureFace {
    @JsonProperty(value="Cts")
    private Long cts;
    @JsonProperty(value="FaceID")
    private String faceId;
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
    private String personId;
    @JsonProperty(value="OriginPersonID")
    private String originPersonId;
    @JsonProperty(value="TripID")
    private String tripId;
    @JsonProperty(value="StartTime")
    private Long startTime;
    @JsonProperty(value="EndTime")
    private Long endTime;
    @JsonProperty(value="IsNew")
    private Integer isNew;
    @JsonProperty(value="CaptureID")
    private String captureId;
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
    @JsonProperty(value="HumanBodyID")
    private String humanBodyId;
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
    @JsonProperty(value="Tags")
    private List<HitTag> tags;

    public CaptureFace() {
    }

    public CaptureFace(String faceId, String logicDeviceId) {
        this.faceId = faceId;
        this.logicDeviceId = logicDeviceId;
    }

    public Long getCts() {
        return this.cts;
    }

    public void setCts(Long cts) {
        this.cts = cts;
    }

    public String getFaceId() {
        return this.faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public String getLogicDeviceId() {
        return this.logicDeviceId;
    }

    public void setLogicDeviceId(String logicDeviceId) {
        this.logicDeviceId = logicDeviceId;
    }

    public Float getAlignScore() {
        return this.alignScore;
    }

    public void setAlignScore(Float alignScore) {
        this.alignScore = alignScore;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Float getGenderConfidence() {
        return this.genderConfidence;
    }

    public void setGenderConfidence(Float genderConfidence) {
        this.genderConfidence = genderConfidence;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Float getAgeConfidence() {
        return this.ageConfidence;
    }

    public void setAgeConfidence(Float ageConfidence) {
        this.ageConfidence = ageConfidence;
    }

    public String getHat() {
        return this.hat;
    }

    public void setHat(String hat) {
        this.hat = hat;
    }

    public Float getHatConfidence() {
        return this.hatConfidence;
    }

    public void setHatConfidence(Float hatConfidence) {
        this.hatConfidence = hatConfidence;
    }

    public String getGlass() {
        return this.glass;
    }

    public void setGlass(String glass) {
        this.glass = glass;
    }

    public Float getGlassConfidence() {
        return this.glassConfidence;
    }

    public void setGlassConfidence(Float glassConfidence) {
        this.glassConfidence = glassConfidence;
    }

    public String getHelmet() {
        return this.helmet;
    }

    public void setHelmet(String helmet) {
        this.helmet = helmet;
    }

    public Float getHelmetConfidence() {
        return this.helmetConfidence;
    }

    public void setHelmetConfidence(Float helmetConfidence) {
        this.helmetConfidence = helmetConfidence;
    }

    public String getMask() {
        return this.mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public Float getMaskConfidence() {
        return this.maskConfidence;
    }

    public void setMaskConfidence(Float maskConfidence) {
        this.maskConfidence = maskConfidence;
    }

    public String getSkinColor() {
        return this.skinColor;
    }

    public void setSkinColor(String skinColor) {
        this.skinColor = skinColor;
    }

    public Float getSkinColorConfidence() {
        return this.skinColorConfidence;
    }

    public void setSkinColorConfidence(Float skinColorConfidence) {
        this.skinColorConfidence = skinColorConfidence;
    }

    public String getFaceExpression() {
        return this.faceExpression;
    }

    public void setFaceExpression(String faceExpression) {
        this.faceExpression = faceExpression;
    }

    public Float getFaceExpressionConfidence() {
        return this.faceExpressionConfidence;
    }

    public void setFaceExpressionConfidence(Float faceExpressionConfidence) {
        this.faceExpressionConfidence = faceExpressionConfidence;
    }

    public Float getFaceBeauty() {
        return this.faceBeauty;
    }

    public void setFaceBeauty(Float faceBeauty) {
        this.faceBeauty = faceBeauty;
    }

    public Float getFaceBeautyConfidence() {
        return this.faceBeautyConfidence;
    }

    public void setFaceBeautyConfidence(Float faceBeautyConfidence) {
        this.faceBeautyConfidence = faceBeautyConfidence;
    }

    public Float getTemperature() {
        return this.temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getTemperatureConfidence() {
        return this.temperatureConfidence;
    }

    public void setTemperatureConfidence(Float temperatureConfidence) {
        this.temperatureConfidence = temperatureConfidence;
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

    public String getOriginPersonId() {
        return this.originPersonId;
    }

    public void setOriginPersonId(String originPersonId) {
        this.originPersonId = originPersonId;
    }

    public String getTripId() {
        return this.tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public Long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getIsNew() {
        return this.isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    public String getCaptureId() {
        return this.captureId;
    }

    public void setCaptureId(String captureId) {
        this.captureId = captureId;
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

    public String getHumanBodyId() {
        return this.humanBodyId;
    }

    public void setHumanBodyId(String humanBodyId) {
        this.humanBodyId = humanBodyId;
    }

    public String getHumanBodyImageUrl() {
        return this.humanBodyImageUrl;
    }

    public void setHumanBodyImageUrl(String humanBodyImageUrl) {
        this.humanBodyImageUrl = humanBodyImageUrl;
    }

    public Integer getHumanBodyCutboardX() {
        return this.humanBodyCutboardX;
    }

    public void setHumanBodyCutboardX(Integer humanBodyCutboardX) {
        this.humanBodyCutboardX = humanBodyCutboardX;
    }

    public Integer getHumanBodyCutboardY() {
        return this.humanBodyCutboardY;
    }

    public void setHumanBodyCutboardY(Integer humanBodyCutboardY) {
        this.humanBodyCutboardY = humanBodyCutboardY;
    }

    public Integer getHumanBodyCutboardWidth() {
        return this.humanBodyCutboardWidth;
    }

    public void setHumanBodyCutboardWidth(Integer humanBodyCutboardWidth) {
        this.humanBodyCutboardWidth = humanBodyCutboardWidth;
    }

    public Integer getHumanBodyCutboardHeight() {
        return this.humanBodyCutboardHeight;
    }

    public void setHumanBodyCutboardHeight(Integer humanBodyCutboardHeight) {
        this.humanBodyCutboardHeight = humanBodyCutboardHeight;
    }

    public String getHumanBodyOrigImageUrl() {
        return this.humanBodyOrigImageUrl;
    }

    public void setHumanBodyOrigImageUrl(String humanBodyOrigImageUrl) {
        this.humanBodyOrigImageUrl = humanBodyOrigImageUrl;
    }

    public Integer getHumanBodyOrigImageWidth() {
        return this.humanBodyOrigImageWidth;
    }

    public void setHumanBodyOrigImageWidth(Integer humanBodyOrigImageWidth) {
        this.humanBodyOrigImageWidth = humanBodyOrigImageWidth;
    }

    public Integer getHumanBodyOrigImageHeight() {
        return this.humanBodyOrigImageHeight;
    }

    public void setHumanBodyOrigImageHeight(Integer humanBodyOrigImageHeight) {
        this.humanBodyOrigImageHeight = humanBodyOrigImageHeight;
    }

    public Integer getHumanBodyCaptureX() {
        return this.humanBodyCaptureX;
    }

    public void setHumanBodyCaptureX(Integer humanBodyCaptureX) {
        this.humanBodyCaptureX = humanBodyCaptureX;
    }

    public Integer getHumanBodyCaptureY() {
        return this.humanBodyCaptureY;
    }

    public void setHumanBodyCaptureY(Integer humanBodyCaptureY) {
        this.humanBodyCaptureY = humanBodyCaptureY;
    }

    public Integer getHumanBodyCaptureWidth() {
        return this.humanBodyCaptureWidth;
    }

    public void setHumanBodyCaptureWidth(Integer humanBodyCaptureWidth) {
        this.humanBodyCaptureWidth = humanBodyCaptureWidth;
    }

    public Integer getHumanBodyCaptureHeight() {
        return this.humanBodyCaptureHeight;
    }

    public void setHumanBodyCaptureHeight(Integer humanBodyCaptureHeight) {
        this.humanBodyCaptureHeight = humanBodyCaptureHeight;
    }

    public List<HitTag> getTags() {
        return this.tags;
    }

    public void setTags(List<HitTag> tags) {
        this.tags = tags;
    }
}
