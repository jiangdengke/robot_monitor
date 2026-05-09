/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonAnyGetter
 *  com.fasterxml.jackson.annotation.JsonAnySetter
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.fasterxml.jackson.annotation.JsonPropertyOrder
 */
package com.robotmonitor.config.domain.deepglint.changelist;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value={"Uts", "OriginPersonID", "MergedPersonID", "OriginPersonImageURL", "MergedPersonImageURL", "MergedAge", "MergedGender"})
@JsonIgnoreProperties(ignoreUnknown=true)
public class ChangeListPerson {
    @JsonProperty(value="Uts")
    private Long uts;
    @JsonProperty(value="OriginPersonID")
    private String originPersonID;
    @JsonProperty(value="MergedPersonID")
    private String mergedPersonID;
    @JsonProperty(value="OriginPersonImageURL")
    private String originPersonImageURL;
    @JsonProperty(value="MergedPersonImageURL")
    private String mergedPersonImageURL;
    @JsonProperty(value="MergedAge")
    private Integer mergedAge;
    @JsonProperty(value="MergedGender")
    private Integer mergedGender;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty(value="Uts")
    public Long getUts() {
        return this.uts;
    }

    @JsonProperty(value="Uts")
    public void setUts(Long uts) {
        this.uts = uts;
    }

    @JsonProperty(value="OriginPersonID")
    public String getOriginPersonID() {
        return this.originPersonID;
    }

    @JsonProperty(value="OriginPersonID")
    public void setOriginPersonID(String originPersonID) {
        this.originPersonID = originPersonID;
    }

    @JsonProperty(value="MergedPersonID")
    public String getMergedPersonID() {
        return this.mergedPersonID;
    }

    @JsonProperty(value="MergedPersonID")
    public void setMergedPersonID(String mergedPersonID) {
        this.mergedPersonID = mergedPersonID;
    }

    @JsonProperty(value="OriginPersonImageURL")
    public String getOriginPersonImageURL() {
        return this.originPersonImageURL;
    }

    @JsonProperty(value="OriginPersonImageURL")
    public void setOriginPersonImageURL(String originPersonImageURL) {
        this.originPersonImageURL = originPersonImageURL;
    }

    @JsonProperty(value="MergedPersonImageURL")
    public String getMergedPersonImageURL() {
        return this.mergedPersonImageURL;
    }

    @JsonProperty(value="MergedPersonImageURL")
    public void setMergedPersonImageURL(String mergedPersonImageURL) {
        this.mergedPersonImageURL = mergedPersonImageURL;
    }

    @JsonProperty(value="MergedAge")
    public Integer getMergedAge() {
        return this.mergedAge;
    }

    @JsonProperty(value="MergedAge")
    public void setMergedAge(Integer mergedAge) {
        this.mergedAge = mergedAge;
    }

    @JsonProperty(value="MergedGender")
    public Integer getMergedGender() {
        return this.mergedGender;
    }

    @JsonProperty(value="MergedGender")
    public void setMergedGender(Integer mergedGender) {
        this.mergedGender = mergedGender;
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
        sb.append(ChangeListPerson.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("uts");
        sb.append('=');
        sb.append(this.uts == null ? "<null>" : this.uts);
        sb.append(',');
        sb.append("originPersonID");
        sb.append('=');
        sb.append(this.originPersonID == null ? "<null>" : this.originPersonID);
        sb.append(',');
        sb.append("mergedPersonID");
        sb.append('=');
        sb.append(this.mergedPersonID == null ? "<null>" : this.mergedPersonID);
        sb.append(',');
        sb.append("originPersonImageURL");
        sb.append('=');
        sb.append(this.originPersonImageURL == null ? "<null>" : this.originPersonImageURL);
        sb.append(',');
        sb.append("mergedPersonImageURL");
        sb.append('=');
        sb.append(this.mergedPersonImageURL == null ? "<null>" : this.mergedPersonImageURL);
        sb.append(',');
        sb.append("mergedAge");
        sb.append('=');
        sb.append(this.mergedAge == null ? "<null>" : this.mergedAge);
        sb.append(',');
        sb.append("mergedGender");
        sb.append('=');
        sb.append(this.mergedGender == null ? "<null>" : this.mergedGender);
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
        result = result * 31 + (this.mergedPersonImageURL == null ? 0 : this.mergedPersonImageURL.hashCode());
        result = result * 31 + (this.mergedAge == null ? 0 : this.mergedAge.hashCode());
        result = result * 31 + (this.originPersonImageURL == null ? 0 : this.originPersonImageURL.hashCode());
        result = result * 31 + (this.uts == null ? 0 : this.uts.hashCode());
        result = result * 31 + (this.mergedPersonID == null ? 0 : this.mergedPersonID.hashCode());
        result = result * 31 + (this.mergedGender == null ? 0 : this.mergedGender.hashCode());
        result = result * 31 + (this.originPersonID == null ? 0 : this.originPersonID.hashCode());
        result = result * 31 + (this.additionalProperties == null ? 0 : this.additionalProperties.hashCode());
        return result;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ChangeListPerson)) {
            return false;
        }
        ChangeListPerson rhs = (ChangeListPerson)other;
        return (this.mergedPersonImageURL == rhs.mergedPersonImageURL || this.mergedPersonImageURL != null && this.mergedPersonImageURL.equals(rhs.mergedPersonImageURL)) && (this.mergedAge == rhs.mergedAge || this.mergedAge != null && this.mergedAge.equals(rhs.mergedAge)) && (this.originPersonImageURL == rhs.originPersonImageURL || this.originPersonImageURL != null && this.originPersonImageURL.equals(rhs.originPersonImageURL)) && (this.uts == rhs.uts || this.uts != null && this.uts.equals(rhs.uts)) && (this.mergedPersonID == rhs.mergedPersonID || this.mergedPersonID != null && this.mergedPersonID.equals(rhs.mergedPersonID)) && (this.mergedGender == rhs.mergedGender || this.mergedGender != null && this.mergedGender.equals(rhs.mergedGender)) && (this.originPersonID == rhs.originPersonID || this.originPersonID != null && this.originPersonID.equals(rhs.originPersonID)) && (this.additionalProperties == rhs.additionalProperties || this.additionalProperties != null && this.additionalProperties.equals(rhs.additionalProperties));
    }
}
