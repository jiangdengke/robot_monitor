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
import com.robotmonitor.config.domain.deepglint.face.ExcludeImage;
import com.robotmonitor.config.domain.deepglint.face.IncludeImage;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CaptureSearchRequest {
    @JsonProperty(value="Type")
    private String type;
    @JsonProperty(value="IncludeImages")
    private List<IncludeImage> includeImages;
    @JsonProperty(value="ExcludeImages")
    private List<ExcludeImage> excludeImages;
    @JsonProperty(value="TopN")
    private Integer topN = 1;
    @JsonProperty(value="Confidence")
    private Float confidence;
    @JsonProperty(value="LogicDeviceIDs")
    private List<String> logicDeviceIDs;
    @JsonProperty(value="DeviceGroupIDs")
    private List<String> deviceGroupIDs;
    @JsonProperty(value="StartTime")
    private Long startTime;
    @JsonProperty(value="EndTime")
    private Long endTime;

    public CaptureSearchRequest() {
    }

    public CaptureSearchRequest(String type, List<IncludeImage> includeImages, Float confidence) {
        this.type = type;
        this.includeImages = includeImages;
        this.confidence = confidence;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<IncludeImage> getIncludeImages() {
        return this.includeImages;
    }

    public void setIncludeImages(List<IncludeImage> includeImages) {
        this.includeImages = includeImages;
    }

    public List<ExcludeImage> getExcludeImages() {
        return this.excludeImages;
    }

    public void setExcludeImages(List<ExcludeImage> excludeImages) {
        this.excludeImages = excludeImages;
    }

    public Integer getTopN() {
        return this.topN;
    }

    public void setTopN(Integer topN) {
        this.topN = topN;
    }

    public Float getConfidence() {
        return this.confidence;
    }

    public void setConfidence(Float confidence) {
        this.confidence = confidence;
    }

    public List<String> getLogicDeviceIDs() {
        return this.logicDeviceIDs;
    }

    public void setLogicDeviceIDs(List<String> logicDeviceIDs) {
        this.logicDeviceIDs = logicDeviceIDs;
    }

    public List<String> getDeviceGroupIDs() {
        return this.deviceGroupIDs;
    }

    public void setDeviceGroupIDs(List<String> deviceGroupIDs) {
        this.deviceGroupIDs = deviceGroupIDs;
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

    public String toString() {
        return "CaptureSearchRequest{type='" + this.type + "', includeImages=" + this.includeImages + ", excludeImages=" + this.excludeImages + ", topN=" + this.topN + ", confidence=" + this.confidence + ", logicDeviceIDs=" + this.logicDeviceIDs + ", deviceGroupIDs=" + this.deviceGroupIDs + ", startTime=" + this.startTime + ", endTime=" + this.endTime + "}";
    }
}
