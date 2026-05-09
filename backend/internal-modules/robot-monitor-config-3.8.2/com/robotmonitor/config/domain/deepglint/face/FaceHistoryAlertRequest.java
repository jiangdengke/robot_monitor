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
public class FaceHistoryAlertRequest {
    @JsonProperty(value="Limit")
    private Integer limit = 16;
    @JsonProperty(value="StartTime")
    private Long startTime;
    @JsonProperty(value="EndTime")
    private Long endTime;
    @JsonProperty(value="Direction")
    private Integer direction;
    @JsonProperty(value="DirectionTime")
    private Integer directionTime;
    @JsonProperty(value="DeviceGroupId")
    private String deviceGroupId;
    @JsonProperty(value="RepoID")
    private String repoId;
    @JsonProperty(value="RegisterPersonID")
    private String registerPersonId;
    @JsonProperty(value="RegisterID")
    private String registerId;

    public FaceHistoryAlertRequest() {
    }

    public FaceHistoryAlertRequest(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return this.limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
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

    public Integer getDirection() {
        return this.direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Integer getDirectionTime() {
        return this.directionTime;
    }

    public void setDirectionTime(Integer directionTime) {
        this.directionTime = directionTime;
    }

    public String getDeviceGroupId() {
        return this.deviceGroupId;
    }

    public void setDeviceGroupId(String deviceGroupId) {
        this.deviceGroupId = deviceGroupId;
    }

    public String getRepoId() {
        return this.repoId;
    }

    public void setRepoId(String repoId) {
        this.repoId = repoId;
    }

    public String getRegisterPersonId() {
        return this.registerPersonId;
    }

    public void setRegisterPersonId(String registerPersonId) {
        this.registerPersonId = registerPersonId;
    }

    public String getRegisterId() {
        return this.registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    public String toString() {
        return "FaceHistoryAlertRequest{limit=" + this.limit + ", startTime=" + this.startTime + ", endTime=" + this.endTime + ", direction=" + this.direction + ", directionTime=" + this.directionTime + ", deviceGroupId='" + this.deviceGroupId + "', repoId='" + this.repoId + "', registerPersonId='" + this.registerPersonId + "', registerId='" + this.registerId + "'}";
    }
}
