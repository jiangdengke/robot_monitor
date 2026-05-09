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
import com.robotmonitor.config.domain.deepglint.similar.PersonSimilarDataRequestImage;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value={"Image", "TopN", "Confidence"})
public class PersonSimilarDataRequest {
    @JsonProperty(value="Image")
    private PersonSimilarDataRequestImage image;
    @JsonProperty(value="TopN")
    private Integer topN;
    @JsonProperty(value="Confidence")
    private Double confidence;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty(value="Image")
    public PersonSimilarDataRequestImage getImage() {
        return this.image;
    }

    @JsonProperty(value="Image")
    public void setImage(PersonSimilarDataRequestImage image) {
        this.image = image;
    }

    @JsonProperty(value="TopN")
    public Integer getTopN() {
        return this.topN;
    }

    @JsonProperty(value="TopN")
    public void setTopN(Integer topN) {
        this.topN = topN;
    }

    @JsonProperty(value="Confidence")
    public Double getConfidence() {
        return this.confidence;
    }

    @JsonProperty(value="Confidence")
    public void setConfidence(Double confidence) {
        this.confidence = confidence;
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
        sb.append(PersonSimilarDataRequest.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("image");
        sb.append('=');
        sb.append(this.image == null ? "<null>" : this.image);
        sb.append(',');
        sb.append("topN");
        sb.append('=');
        sb.append(this.topN == null ? "<null>" : this.topN);
        sb.append(',');
        sb.append("confidence");
        sb.append('=');
        sb.append(this.confidence == null ? "<null>" : this.confidence);
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
        result = result * 31 + (this.image == null ? 0 : this.image.hashCode());
        result = result * 31 + (this.additionalProperties == null ? 0 : this.additionalProperties.hashCode());
        result = result * 31 + (this.topN == null ? 0 : this.topN.hashCode());
        result = result * 31 + (this.confidence == null ? 0 : this.confidence.hashCode());
        return result;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PersonSimilarDataRequest)) {
            return false;
        }
        PersonSimilarDataRequest rhs = (PersonSimilarDataRequest)other;
        return (this.image == rhs.image || this.image != null && this.image.equals(rhs.image)) && (this.additionalProperties == rhs.additionalProperties || this.additionalProperties != null && this.additionalProperties.equals(rhs.additionalProperties)) && (this.topN == rhs.topN || this.topN != null && this.topN.equals(rhs.topN)) && (this.confidence == rhs.confidence || this.confidence != null && this.confidence.equals(rhs.confidence));
    }
}
