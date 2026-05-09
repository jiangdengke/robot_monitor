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
import com.robotmonitor.config.domain.deepglint.facelist.FaceListFace;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value={"RepoID", "RepoName", "RegisterID", "RegisterUrl", "PersonID", "OriginRegisterPersonID", "Name", "ExternalID", "Confidence", "Face"})
public class FaceListTag {
    @JsonProperty(value="RepoID")
    private String repoID;
    @JsonProperty(value="RepoName")
    private String repoName;
    @JsonProperty(value="RegisterID")
    private String registerID;
    @JsonProperty(value="RegisterUrl")
    private String registerUrl;
    @JsonProperty(value="PersonID")
    private String personID;
    @JsonProperty(value="OriginRegisterPersonID")
    private String originRegisterPersonID;
    @JsonProperty(value="Name")
    private String name;
    @JsonProperty(value="ExternalID")
    private String externalID;
    @JsonProperty(value="Confidence")
    private Float confidence;
    @JsonProperty(value="Face")
    private FaceListFace face;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty(value="RepoID")
    public String getRepoID() {
        return this.repoID;
    }

    @JsonProperty(value="RepoID")
    public void setRepoID(String repoID) {
        this.repoID = repoID;
    }

    @JsonProperty(value="RepoName")
    public String getRepoName() {
        return this.repoName;
    }

    @JsonProperty(value="RepoName")
    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    @JsonProperty(value="RegisterID")
    public String getRegisterID() {
        return this.registerID;
    }

    @JsonProperty(value="RegisterID")
    public void setRegisterID(String registerID) {
        this.registerID = registerID;
    }

    @JsonProperty(value="RegisterUrl")
    public String getRegisterUrl() {
        return this.registerUrl;
    }

    @JsonProperty(value="RegisterUrl")
    public void setRegisterUrl(String registerUrl) {
        this.registerUrl = registerUrl;
    }

    @JsonProperty(value="PersonID")
    public String getPersonID() {
        return this.personID;
    }

    @JsonProperty(value="PersonID")
    public void setPersonID(String personID) {
        this.personID = personID;
    }

    @JsonProperty(value="OriginRegisterPersonID")
    public String getOriginRegisterPersonID() {
        return this.originRegisterPersonID;
    }

    @JsonProperty(value="OriginRegisterPersonID")
    public void setOriginRegisterPersonID(String originRegisterPersonID) {
        this.originRegisterPersonID = originRegisterPersonID;
    }

    @JsonProperty(value="Name")
    public String getName() {
        return this.name;
    }

    @JsonProperty(value="Name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty(value="ExternalID")
    public String getExternalID() {
        return this.externalID;
    }

    @JsonProperty(value="ExternalID")
    public void setExternalID(String externalID) {
        this.externalID = externalID;
    }

    @JsonProperty(value="Confidence")
    public Float getConfidence() {
        return this.confidence;
    }

    @JsonProperty(value="Confidence")
    public void setConfidence(Float confidence) {
        this.confidence = confidence;
    }

    @JsonProperty(value="Face")
    public FaceListFace getFace() {
        return this.face;
    }

    @JsonProperty(value="Face")
    public void setFace(FaceListFace face) {
        this.face = face;
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
        sb.append(FaceListTag.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("repoID");
        sb.append('=');
        sb.append(this.repoID == null ? "<null>" : this.repoID);
        sb.append(',');
        sb.append("repoName");
        sb.append('=');
        sb.append(this.repoName == null ? "<null>" : this.repoName);
        sb.append(',');
        sb.append("registerID");
        sb.append('=');
        sb.append(this.registerID == null ? "<null>" : this.registerID);
        sb.append(',');
        sb.append("registerUrl");
        sb.append('=');
        sb.append(this.registerUrl == null ? "<null>" : this.registerUrl);
        sb.append(',');
        sb.append("personID");
        sb.append('=');
        sb.append(this.personID == null ? "<null>" : this.personID);
        sb.append(',');
        sb.append("originRegisterPersonID");
        sb.append('=');
        sb.append(this.originRegisterPersonID == null ? "<null>" : this.originRegisterPersonID);
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(this.name == null ? "<null>" : this.name);
        sb.append(',');
        sb.append("externalID");
        sb.append('=');
        sb.append(this.externalID == null ? "<null>" : this.externalID);
        sb.append(',');
        sb.append("confidence");
        sb.append('=');
        sb.append(this.confidence == null ? "<null>" : this.confidence);
        sb.append(',');
        sb.append("face");
        sb.append('=');
        sb.append(this.face == null ? "<null>" : this.face);
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
        result = result * 31 + (this.repoID == null ? 0 : this.repoID.hashCode());
        result = result * 31 + (this.face == null ? 0 : this.face.hashCode());
        result = result * 31 + (this.registerUrl == null ? 0 : this.registerUrl.hashCode());
        result = result * 31 + (this.registerID == null ? 0 : this.registerID.hashCode());
        result = result * 31 + (this.repoName == null ? 0 : this.repoName.hashCode());
        result = result * 31 + (this.confidence == null ? 0 : this.confidence.hashCode());
        result = result * 31 + (this.name == null ? 0 : this.name.hashCode());
        result = result * 31 + (this.externalID == null ? 0 : this.externalID.hashCode());
        result = result * 31 + (this.personID == null ? 0 : this.personID.hashCode());
        result = result * 31 + (this.additionalProperties == null ? 0 : this.additionalProperties.hashCode());
        result = result * 31 + (this.originRegisterPersonID == null ? 0 : this.originRegisterPersonID.hashCode());
        return result;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof FaceListTag)) {
            return false;
        }
        FaceListTag rhs = (FaceListTag)other;
        return (this.repoID == rhs.repoID || this.repoID != null && this.repoID.equals(rhs.repoID)) && (this.face == rhs.face || this.face != null && this.face.equals(rhs.face)) && (this.registerUrl == rhs.registerUrl || this.registerUrl != null && this.registerUrl.equals(rhs.registerUrl)) && (this.registerID == rhs.registerID || this.registerID != null && this.registerID.equals(rhs.registerID)) && (this.repoName == rhs.repoName || this.repoName != null && this.repoName.equals(rhs.repoName)) && (this.confidence == rhs.confidence || this.confidence != null && this.confidence.equals(rhs.confidence)) && (this.name == rhs.name || this.name != null && this.name.equals(rhs.name)) && (this.externalID == rhs.externalID || this.externalID != null && this.externalID.equals(rhs.externalID)) && (this.personID == rhs.personID || this.personID != null && this.personID.equals(rhs.personID)) && (this.additionalProperties == rhs.additionalProperties || this.additionalProperties != null && this.additionalProperties.equals(rhs.additionalProperties)) && (this.originRegisterPersonID == rhs.originRegisterPersonID || this.originRegisterPersonID != null && this.originRegisterPersonID.equals(rhs.originRegisterPersonID));
    }
}
