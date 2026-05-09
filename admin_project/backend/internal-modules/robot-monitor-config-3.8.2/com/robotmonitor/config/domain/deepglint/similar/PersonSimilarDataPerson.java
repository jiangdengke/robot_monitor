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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value={"Cts", "Uts", "PersonID", "Confidence", "Age", "Gender", "ImageUrl", "HumanBodyImageUrl", "MergedPersonID", "OriginImageUrl", "OriginPersonIDs"})
public class PersonSimilarDataPerson {
    @JsonProperty(value="Cts")
    private Long cts;
    @JsonProperty(value="Uts")
    private Integer uts;
    @JsonProperty(value="PersonID")
    private String personID;
    @JsonProperty(value="Confidence")
    private Double confidence;
    @JsonProperty(value="Age")
    private Integer age;
    @JsonProperty(value="Gender")
    private String gender;
    @JsonProperty(value="ImageUrl")
    private String imageUrl;
    @JsonProperty(value="HumanBodyImageUrl")
    private String humanBodyImageUrl;
    @JsonProperty(value="MergedPersonID")
    private String mergedPersonID;
    @JsonProperty(value="OriginImageUrl")
    private String originImageUrl;
    @JsonProperty(value="OriginPersonIDs")
    private List<String> originPersonIDs = new ArrayList<String>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty(value="Cts")
    public Long getCts() {
        return this.cts;
    }

    @JsonProperty(value="Cts")
    public void setCts(Long cts) {
        this.cts = cts;
    }

    @JsonProperty(value="Uts")
    public Integer getUts() {
        return this.uts;
    }

    @JsonProperty(value="Uts")
    public void setUts(Integer uts) {
        this.uts = uts;
    }

    @JsonProperty(value="PersonID")
    public String getPersonID() {
        return this.personID;
    }

    @JsonProperty(value="PersonID")
    public void setPersonID(String personID) {
        this.personID = personID;
    }

    @JsonProperty(value="Confidence")
    public Double getConfidence() {
        return this.confidence;
    }

    @JsonProperty(value="Confidence")
    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    @JsonProperty(value="Age")
    public Integer getAge() {
        return this.age;
    }

    @JsonProperty(value="Age")
    public void setAge(Integer age) {
        this.age = age;
    }

    @JsonProperty(value="Gender")
    public String getGender() {
        return this.gender;
    }

    @JsonProperty(value="Gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    @JsonProperty(value="ImageUrl")
    public String getImageUrl() {
        return this.imageUrl;
    }

    @JsonProperty(value="ImageUrl")
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @JsonProperty(value="HumanBodyImageUrl")
    public String getHumanBodyImageUrl() {
        return this.humanBodyImageUrl;
    }

    @JsonProperty(value="HumanBodyImageUrl")
    public void setHumanBodyImageUrl(String humanBodyImageUrl) {
        this.humanBodyImageUrl = humanBodyImageUrl;
    }

    @JsonProperty(value="MergedPersonID")
    public String getMergedPersonID() {
        return this.mergedPersonID;
    }

    @JsonProperty(value="MergedPersonID")
    public void setMergedPersonID(String mergedPersonID) {
        this.mergedPersonID = mergedPersonID;
    }

    @JsonProperty(value="OriginImageUrl")
    public String getOriginImageUrl() {
        return this.originImageUrl;
    }

    @JsonProperty(value="OriginImageUrl")
    public void setOriginImageUrl(String originImageUrl) {
        this.originImageUrl = originImageUrl;
    }

    @JsonProperty(value="OriginPersonIDs")
    public List<String> getOriginPersonIDs() {
        return this.originPersonIDs;
    }

    @JsonProperty(value="OriginPersonIDs")
    public void setOriginPersonIDs(List<String> originPersonIDs) {
        this.originPersonIDs = originPersonIDs;
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
        sb.append(PersonSimilarDataPerson.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("cts");
        sb.append('=');
        sb.append(this.cts == null ? "<null>" : this.cts);
        sb.append(',');
        sb.append("uts");
        sb.append('=');
        sb.append(this.uts == null ? "<null>" : this.uts);
        sb.append(',');
        sb.append("personID");
        sb.append('=');
        sb.append(this.personID == null ? "<null>" : this.personID);
        sb.append(',');
        sb.append("confidence");
        sb.append('=');
        sb.append(this.confidence == null ? "<null>" : this.confidence);
        sb.append(',');
        sb.append("age");
        sb.append('=');
        sb.append(this.age == null ? "<null>" : this.age);
        sb.append(',');
        sb.append("gender");
        sb.append('=');
        sb.append(this.gender == null ? "<null>" : this.gender);
        sb.append(',');
        sb.append("imageUrl");
        sb.append('=');
        sb.append(this.imageUrl == null ? "<null>" : this.imageUrl);
        sb.append(',');
        sb.append("humanBodyImageUrl");
        sb.append('=');
        sb.append(this.humanBodyImageUrl == null ? "<null>" : this.humanBodyImageUrl);
        sb.append(',');
        sb.append("mergedPersonID");
        sb.append('=');
        sb.append(this.mergedPersonID == null ? "<null>" : this.mergedPersonID);
        sb.append(',');
        sb.append("originImageUrl");
        sb.append('=');
        sb.append(this.originImageUrl == null ? "<null>" : this.originImageUrl);
        sb.append(',');
        sb.append("originPersonIDs");
        sb.append('=');
        sb.append(this.originPersonIDs == null ? "<null>" : this.originPersonIDs);
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
        result = result * 31 + (this.cts == null ? 0 : this.cts.hashCode());
        result = result * 31 + (this.gender == null ? 0 : this.gender.hashCode());
        result = result * 31 + (this.mergedPersonID == null ? 0 : this.mergedPersonID.hashCode());
        result = result * 31 + (this.confidence == null ? 0 : this.confidence.hashCode());
        result = result * 31 + (this.humanBodyImageUrl == null ? 0 : this.humanBodyImageUrl.hashCode());
        result = result * 31 + (this.originImageUrl == null ? 0 : this.originImageUrl.hashCode());
        result = result * 31 + (this.uts == null ? 0 : this.uts.hashCode());
        result = result * 31 + (this.imageUrl == null ? 0 : this.imageUrl.hashCode());
        result = result * 31 + (this.personID == null ? 0 : this.personID.hashCode());
        result = result * 31 + (this.originPersonIDs == null ? 0 : this.originPersonIDs.hashCode());
        result = result * 31 + (this.additionalProperties == null ? 0 : this.additionalProperties.hashCode());
        result = result * 31 + (this.age == null ? 0 : this.age.hashCode());
        return result;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PersonSimilarDataPerson)) {
            return false;
        }
        PersonSimilarDataPerson rhs = (PersonSimilarDataPerson)other;
        return (this.cts == rhs.cts || this.cts != null && this.cts.equals(rhs.cts)) && (this.gender == rhs.gender || this.gender != null && this.gender.equals(rhs.gender)) && (this.mergedPersonID == rhs.mergedPersonID || this.mergedPersonID != null && this.mergedPersonID.equals(rhs.mergedPersonID)) && (this.confidence == rhs.confidence || this.confidence != null && this.confidence.equals(rhs.confidence)) && (this.humanBodyImageUrl == rhs.humanBodyImageUrl || this.humanBodyImageUrl != null && this.humanBodyImageUrl.equals(rhs.humanBodyImageUrl)) && (this.originImageUrl == rhs.originImageUrl || this.originImageUrl != null && this.originImageUrl.equals(rhs.originImageUrl)) && (this.uts == rhs.uts || this.uts != null && this.uts.equals(rhs.uts)) && (this.imageUrl == rhs.imageUrl || this.imageUrl != null && this.imageUrl.equals(rhs.imageUrl)) && (this.personID == rhs.personID || this.personID != null && this.personID.equals(rhs.personID)) && (this.originPersonIDs == rhs.originPersonIDs || this.originPersonIDs != null && this.originPersonIDs.equals(rhs.originPersonIDs)) && (this.additionalProperties == rhs.additionalProperties || this.additionalProperties != null && this.additionalProperties.equals(rhs.additionalProperties)) && (this.age == rhs.age || this.age != null && this.age.equals(rhs.age));
    }
}
