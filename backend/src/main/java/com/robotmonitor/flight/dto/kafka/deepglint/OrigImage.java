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
import com.robotmonitor.flight.dto.kafka.deepglint.CapturePosition;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value={"Url", "Width", "Height", "CapturePosition"})
public class OrigImage {
    @JsonProperty(value="Url")
    private String url;
    @JsonProperty(value="Width")
    private Integer width;
    @JsonProperty(value="Height")
    private Integer height;
    @JsonProperty(value="CapturePosition")
    private CapturePosition capturePosition;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty(value="Url")
    public String getUrl() {
        return this.url;
    }

    @JsonProperty(value="Url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty(value="Width")
    public Integer getWidth() {
        return this.width;
    }

    @JsonProperty(value="Width")
    public void setWidth(Integer width) {
        this.width = width;
    }

    @JsonProperty(value="Height")
    public Integer getHeight() {
        return this.height;
    }

    @JsonProperty(value="Height")
    public void setHeight(Integer height) {
        this.height = height;
    }

    @JsonProperty(value="CapturePosition")
    public CapturePosition getCapturePosition() {
        return this.capturePosition;
    }

    @JsonProperty(value="CapturePosition")
    public void setCapturePosition(CapturePosition capturePosition) {
        this.capturePosition = capturePosition;
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
        sb.append(OrigImage.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("url");
        sb.append('=');
        sb.append(this.url == null ? "<null>" : this.url);
        sb.append(',');
        sb.append("width");
        sb.append('=');
        sb.append(this.width == null ? "<null>" : this.width);
        sb.append(',');
        sb.append("height");
        sb.append('=');
        sb.append(this.height == null ? "<null>" : this.height);
        sb.append(',');
        sb.append("capturePosition");
        sb.append('=');
        sb.append(this.capturePosition == null ? "<null>" : this.capturePosition);
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
        result = result * 31 + (this.width == null ? 0 : this.width.hashCode());
        result = result * 31 + (this.capturePosition == null ? 0 : this.capturePosition.hashCode());
        result = result * 31 + (this.additionalProperties == null ? 0 : this.additionalProperties.hashCode());
        result = result * 31 + (this.url == null ? 0 : this.url.hashCode());
        result = result * 31 + (this.height == null ? 0 : this.height.hashCode());
        return result;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof OrigImage)) {
            return false;
        }
        OrigImage rhs = (OrigImage)other;
        return (this.width == rhs.width || this.width != null && this.width.equals(rhs.width)) && (this.capturePosition == rhs.capturePosition || this.capturePosition != null && this.capturePosition.equals(rhs.capturePosition)) && (this.additionalProperties == rhs.additionalProperties || this.additionalProperties != null && this.additionalProperties.equals(rhs.additionalProperties)) && (this.url == rhs.url || this.url != null && this.url.equals(rhs.url)) && (this.height == rhs.height || this.height != null && this.height.equals(rhs.height));
    }
}
