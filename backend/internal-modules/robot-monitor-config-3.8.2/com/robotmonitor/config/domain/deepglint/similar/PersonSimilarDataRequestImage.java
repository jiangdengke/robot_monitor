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
package com.robotmonitor.config.domain.deepglint.similar;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value={"ImageID", "URL", "BinData", "Feature"})
public class PersonSimilarDataRequestImage {
    @JsonProperty(value="ImageID")
    private String imageID;
    @JsonProperty(value="URL")
    private String url;
    @JsonProperty(value="BinData")
    private String binData;
    @JsonProperty(value="Feature")
    private String feature;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty(value="ImageID")
    public String getImageID() {
        return this.imageID;
    }

    @JsonProperty(value="ImageID")
    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    @JsonProperty(value="URL")
    public String getUrl() {
        return this.url;
    }

    @JsonProperty(value="URL")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty(value="BinData")
    public String getBinData() {
        return this.binData;
    }

    @JsonProperty(value="BinData")
    public void setBinData(String binData) {
        this.binData = binData;
    }

    @JsonProperty(value="Feature")
    public String getFeature() {
        return this.feature;
    }

    @JsonProperty(value="Feature")
    public void setFeature(String feature) {
        this.feature = feature;
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
        sb.append(PersonSimilarDataRequestImage.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("imageID");
        sb.append('=');
        sb.append(this.imageID == null ? "<null>" : this.imageID);
        sb.append(',');
        sb.append("url");
        sb.append('=');
        sb.append(this.url == null ? "<null>" : this.url);
        sb.append(',');
        sb.append("binData");
        sb.append('=');
        sb.append(this.binData == null ? "<null>" : this.binData);
        sb.append(',');
        sb.append("feature");
        sb.append('=');
        sb.append(this.feature == null ? "<null>" : this.feature);
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
        result = result * 31 + (this.imageID == null ? 0 : this.imageID.hashCode());
        result = result * 31 + (this.additionalProperties == null ? 0 : this.additionalProperties.hashCode());
        result = result * 31 + (this.feature == null ? 0 : this.feature.hashCode());
        result = result * 31 + (this.url == null ? 0 : this.url.hashCode());
        result = result * 31 + (this.binData == null ? 0 : this.binData.hashCode());
        return result;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PersonSimilarDataRequestImage)) {
            return false;
        }
        PersonSimilarDataRequestImage rhs = (PersonSimilarDataRequestImage)other;
        return (this.imageID == rhs.imageID || this.imageID != null && this.imageID.equals(rhs.imageID)) && (this.additionalProperties == rhs.additionalProperties || this.additionalProperties != null && this.additionalProperties.equals(rhs.additionalProperties)) && (this.feature == rhs.feature || this.feature != null && this.feature.equals(rhs.feature)) && (this.url == rhs.url || this.url != null && this.url.equals(rhs.url)) && (this.binData == rhs.binData || this.binData != null && this.binData.equals(rhs.binData));
    }
}
