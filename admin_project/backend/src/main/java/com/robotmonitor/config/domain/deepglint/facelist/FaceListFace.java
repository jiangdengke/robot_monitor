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
import java.util.HashMap;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value={"FaceID", "Url"})
public class FaceListFace {
    @JsonProperty(value="FaceID")
    private String faceID;
    @JsonProperty(value="Url")
    private String url;
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

    @JsonProperty(value="Url")
    public String getUrl() {
        return this.url;
    }

    @JsonProperty(value="Url")
    public void setUrl(String url) {
        this.url = url;
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
        sb.append(FaceListFace.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("faceID");
        sb.append('=');
        sb.append(this.faceID == null ? "<null>" : this.faceID);
        sb.append(',');
        sb.append("url");
        sb.append('=');
        sb.append(this.url == null ? "<null>" : this.url);
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
        result = result * 31 + (this.faceID == null ? 0 : this.faceID.hashCode());
        result = result * 31 + (this.additionalProperties == null ? 0 : this.additionalProperties.hashCode());
        result = result * 31 + (this.url == null ? 0 : this.url.hashCode());
        return result;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof FaceListFace)) {
            return false;
        }
        FaceListFace rhs = (FaceListFace)other;
        return (this.faceID == rhs.faceID || this.faceID != null && this.faceID.equals(rhs.faceID)) && (this.additionalProperties == rhs.additionalProperties || this.additionalProperties != null && this.additionalProperties.equals(rhs.additionalProperties)) && (this.url == rhs.url || this.url != null && this.url.equals(rhs.url));
    }
}
