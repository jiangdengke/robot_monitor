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
package com.robotmonitor.flight.dto.kafka.deepglint;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value={"FaceID", "CaptureID", "Url", "Time", "DeviceID", "LogicDeviceID"})
public class Face {
    @JsonProperty(value="FaceID")
    private String faceID;
    @JsonProperty(value="CaptureID")
    private String captureID;
    @JsonProperty(value="Url")
    private String url;
    @JsonProperty(value="Time")
    private Long time;
    @JsonProperty(value="DeviceID")
    private String deviceID;
    @JsonProperty(value="LogicDeviceID")
    private String logicDeviceID;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty(value="FaceID")
    public String getFaceID() {
        return this.faceID;
    }

    @JsonProperty(value="FaceID")
    public void setFaceID(String faceID) {
        this.faceID = faceID;
    }

    @JsonProperty(value="CaptureID")
    public String getCaptureID() {
        return this.captureID;
    }

    @JsonProperty(value="CaptureID")
    public void setCaptureID(String captureID) {
        this.captureID = captureID;
    }

    @JsonProperty(value="Url")
    public String getUrl() {
        return this.url;
    }

    @JsonProperty(value="Url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty(value="Time")
    public Long getTime() {
        return this.time;
    }

    @JsonProperty(value="Time")
    public void setTime(Long time) {
        this.time = time;
    }

    @JsonProperty(value="DeviceID")
    public String getDeviceID() {
        return this.deviceID;
    }

    @JsonProperty(value="DeviceID")
    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    @JsonProperty(value="LogicDeviceID")
    public String getLogicDeviceID() {
        return this.logicDeviceID;
    }

    @JsonProperty(value="LogicDeviceID")
    public void setLogicDeviceID(String logicDeviceID) {
        this.logicDeviceID = logicDeviceID;
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
        sb.append(Face.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("faceID");
        sb.append('=');
        sb.append(this.faceID == null ? "<null>" : this.faceID);
        sb.append(',');
        sb.append("captureID");
        sb.append('=');
        sb.append(this.captureID == null ? "<null>" : this.captureID);
        sb.append(',');
        sb.append("url");
        sb.append('=');
        sb.append(this.url == null ? "<null>" : this.url);
        sb.append(',');
        sb.append("time");
        sb.append('=');
        sb.append(this.time == null ? "<null>" : this.time);
        sb.append(',');
        sb.append("deviceID");
        sb.append('=');
        sb.append(this.deviceID == null ? "<null>" : this.deviceID);
        sb.append(',');
        sb.append("logicDeviceID");
        sb.append('=');
        sb.append(this.logicDeviceID == null ? "<null>" : this.logicDeviceID);
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
        result = result * 31 + (this.logicDeviceID == null ? 0 : this.logicDeviceID.hashCode());
        result = result * 31 + (this.captureID == null ? 0 : this.captureID.hashCode());
        result = result * 31 + (this.faceID == null ? 0 : this.faceID.hashCode());
        result = result * 31 + (this.time == null ? 0 : this.time.hashCode());
        result = result * 31 + (this.additionalProperties == null ? 0 : this.additionalProperties.hashCode());
        result = result * 31 + (this.deviceID == null ? 0 : this.deviceID.hashCode());
        result = result * 31 + (this.url == null ? 0 : this.url.hashCode());
        return result;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Face)) {
            return false;
        }
        Face rhs = (Face)other;
        return (this.logicDeviceID == rhs.logicDeviceID || this.logicDeviceID != null && this.logicDeviceID.equals(rhs.logicDeviceID)) && (this.captureID == rhs.captureID || this.captureID != null && this.captureID.equals(rhs.captureID)) && (this.faceID == rhs.faceID || this.faceID != null && this.faceID.equals(rhs.faceID)) && (this.time == rhs.time || this.time != null && this.time.equals(rhs.time)) && (this.additionalProperties == rhs.additionalProperties || this.additionalProperties != null && this.additionalProperties.equals(rhs.additionalProperties)) && (this.deviceID == rhs.deviceID || this.deviceID != null && this.deviceID.equals(rhs.deviceID)) && (this.url == rhs.url || this.url != null && this.url.equals(rhs.url));
    }
}
