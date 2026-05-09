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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value={"Limit", "DeviceGroupIDs", "LogicDeviceIDs", "StartTime", "EndTime", "Direction", "DirectionTime", "PersonID"})
public class FaceListRequest {
    @JsonProperty(value="Limit")
    private Integer limit;
    @JsonProperty(value="DeviceGroupIDs")
    private List<String> deviceGroupIDs = new ArrayList<String>();
    @JsonProperty(value="LogicDeviceIDs")
    private List<String> logicDeviceIDs = new ArrayList<String>();
    @JsonProperty(value="StartTime")
    private Long startTime;
    @JsonProperty(value="EndTime")
    private Long endTime;
    @JsonProperty(value="Direction")
    private Integer direction;
    @JsonProperty(value="DirectionTime")
    private Long directionTime;
    @JsonProperty(value="PersonID")
    private String personID;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty(value="Limit")
    public Integer getLimit() {
        return this.limit;
    }

    @JsonProperty(value="Limit")
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @JsonProperty(value="DeviceGroupIDs")
    public List<String> getDeviceGroupIDs() {
        return this.deviceGroupIDs;
    }

    @JsonProperty(value="DeviceGroupIDs")
    public void setDeviceGroupIDs(List<String> deviceGroupIDs) {
        this.deviceGroupIDs = deviceGroupIDs;
    }

    @JsonProperty(value="LogicDeviceIDs")
    public List<String> getLogicDeviceIDs() {
        return this.logicDeviceIDs;
    }

    @JsonProperty(value="LogicDeviceIDs")
    public void setLogicDeviceIDs(List<String> logicDeviceIDs) {
        this.logicDeviceIDs = logicDeviceIDs;
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

    @JsonProperty(value="Direction")
    public Integer getDirection() {
        return this.direction;
    }

    @JsonProperty(value="Direction")
    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    @JsonProperty(value="DirectionTime")
    public Long getDirectionTime() {
        return this.directionTime;
    }

    @JsonProperty(value="DirectionTime")
    public void setDirectionTime(Long directionTime) {
        this.directionTime = directionTime;
    }

    @JsonProperty(value="PersonID")
    public String getPersonID() {
        return this.personID;
    }

    @JsonProperty(value="PersonID")
    public void setPersonID(String personID) {
        this.personID = personID;
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
        sb.append(FaceListRequest.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("limit");
        sb.append('=');
        sb.append(this.limit == null ? "<null>" : this.limit);
        sb.append(',');
        sb.append("deviceGroupIDs");
        sb.append('=');
        sb.append(this.deviceGroupIDs == null ? "<null>" : this.deviceGroupIDs);
        sb.append(',');
        sb.append("logicDeviceIDs");
        sb.append('=');
        sb.append(this.logicDeviceIDs == null ? "<null>" : this.logicDeviceIDs);
        sb.append(',');
        sb.append("startTime");
        sb.append('=');
        sb.append(this.startTime == null ? "<null>" : this.startTime);
        sb.append(',');
        sb.append("endTime");
        sb.append('=');
        sb.append(this.endTime == null ? "<null>" : this.endTime);
        sb.append(',');
        sb.append("direction");
        sb.append('=');
        sb.append(this.direction == null ? "<null>" : this.direction);
        sb.append(',');
        sb.append("directionTime");
        sb.append('=');
        sb.append(this.directionTime == null ? "<null>" : this.directionTime);
        sb.append(',');
        sb.append("personID");
        sb.append('=');
        sb.append(this.personID == null ? "<null>" : this.personID);
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
        result = result * 31 + (this.logicDeviceIDs == null ? 0 : this.logicDeviceIDs.hashCode());
        result = result * 31 + (this.directionTime == null ? 0 : this.directionTime.hashCode());
        result = result * 31 + (this.limit == null ? 0 : this.limit.hashCode());
        result = result * 31 + (this.startTime == null ? 0 : this.startTime.hashCode());
        result = result * 31 + (this.personID == null ? 0 : this.personID.hashCode());
        result = result * 31 + (this.endTime == null ? 0 : this.endTime.hashCode());
        result = result * 31 + (this.additionalProperties == null ? 0 : this.additionalProperties.hashCode());
        result = result * 31 + (this.deviceGroupIDs == null ? 0 : this.deviceGroupIDs.hashCode());
        result = result * 31 + (this.direction == null ? 0 : this.direction.hashCode());
        return result;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof FaceListRequest)) {
            return false;
        }
        FaceListRequest rhs = (FaceListRequest)other;
        return (this.logicDeviceIDs == rhs.logicDeviceIDs || this.logicDeviceIDs != null && this.logicDeviceIDs.equals(rhs.logicDeviceIDs)) && (this.directionTime == rhs.directionTime || this.directionTime != null && this.directionTime.equals(rhs.directionTime)) && (this.limit == rhs.limit || this.limit != null && this.limit.equals(rhs.limit)) && (this.startTime == rhs.startTime || this.startTime != null && this.startTime.equals(rhs.startTime)) && (this.personID == rhs.personID || this.personID != null && this.personID.equals(rhs.personID)) && (this.endTime == rhs.endTime || this.endTime != null && this.endTime.equals(rhs.endTime)) && (this.additionalProperties == rhs.additionalProperties || this.additionalProperties != null && this.additionalProperties.equals(rhs.additionalProperties)) && (this.deviceGroupIDs == rhs.deviceGroupIDs || this.deviceGroupIDs != null && this.deviceGroupIDs.equals(rhs.deviceGroupIDs)) && (this.direction == rhs.direction || this.direction != null && this.direction.equals(rhs.direction));
    }
}
