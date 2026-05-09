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
package com.robotmonitor.flight.dto.kafka.deepglint.data.face.metadata;

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
public class TagFace {
    @JsonProperty(value="FaceID")
    private String faceID;
    @JsonProperty(value="Url")
    private String url;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public String getFaceID() {
        return this.faceID;
    }

    public String getUrl() {
        return this.url;
    }

    @JsonProperty(value="FaceID")
    public void setFaceID(String faceID) {
        this.faceID = faceID;
    }

    @JsonProperty(value="Url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonIgnore
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TagFace)) {
            return false;
        }
        TagFace other = (TagFace)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$faceID = this.getFaceID();
        String other$faceID = other.getFaceID();
        if (this$faceID == null ? other$faceID != null : !this$faceID.equals(other$faceID)) {
            return false;
        }
        String this$url = this.getUrl();
        String other$url = other.getUrl();
        if (this$url == null ? other$url != null : !this$url.equals(other$url)) {
            return false;
        }
        Map<String, Object> this$additionalProperties = this.getAdditionalProperties();
        Map<String, Object> other$additionalProperties = other.getAdditionalProperties();
        return !(this$additionalProperties == null ? other$additionalProperties != null : !((Object)this$additionalProperties).equals(other$additionalProperties));
    }

    protected boolean canEqual(Object other) {
        return other instanceof TagFace;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $faceID = this.getFaceID();
        result = result * 59 + ($faceID == null ? 43 : $faceID.hashCode());
        String $url = this.getUrl();
        result = result * 59 + ($url == null ? 43 : $url.hashCode());
        Map<String, Object> $additionalProperties = this.getAdditionalProperties();
        result = result * 59 + ($additionalProperties == null ? 43 : ((Object)$additionalProperties).hashCode());
        return result;
    }

    public String toString() {
        return "TagFace(faceID=" + this.getFaceID() + ", url=" + this.getUrl() + ", additionalProperties=" + this.getAdditionalProperties() + ")";
    }
}
