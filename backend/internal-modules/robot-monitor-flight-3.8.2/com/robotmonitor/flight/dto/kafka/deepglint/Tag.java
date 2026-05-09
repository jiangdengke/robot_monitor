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
import com.robotmonitor.flight.dto.kafka.deepglint.Face;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value={"Level", "RepoID", "RepoName", "RegisterID", "PersonID", "OriginPersonID", "Name", "ExternalID", "Confidence", "Comment", "Face"})
public class Tag {
    @JsonProperty(value="Level")
    private String level;
    @JsonProperty(value="RepoID")
    private String repoID;
    @JsonProperty(value="RepoName")
    private String repoName;
    @JsonProperty(value="RegisterID")
    private String registerID;
    @JsonProperty(value="PersonID")
    private String personID;
    @JsonProperty(value="OriginPersonID")
    private String originPersonID;
    @JsonProperty(value="Name")
    private String name;
    @JsonProperty(value="ExternalID")
    private String externalID;
    @JsonProperty(value="Confidence")
    private Double confidence;
    @JsonProperty(value="Comment")
    private String comment;
    @JsonProperty(value="Face")
    private Face face;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty(value="Level")
    public String getLevel() {
        return this.level;
    }

    @JsonProperty(value="Level")
    public void setLevel(String level) {
        this.level = level;
    }

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

    @JsonProperty(value="PersonID")
    public String getPersonID() {
        return this.personID;
    }

    @JsonProperty(value="PersonID")
    public void setPersonID(String personID) {
        this.personID = personID;
    }

    @JsonProperty(value="OriginPersonID")
    public String getOriginPersonID() {
        return this.originPersonID;
    }

    @JsonProperty(value="OriginPersonID")
    public void setOriginPersonID(String originPersonID) {
        this.originPersonID = originPersonID;
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
    public Double getConfidence() {
        return this.confidence;
    }

    @JsonProperty(value="Confidence")
    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    @JsonProperty(value="Comment")
    public String getComment() {
        return this.comment;
    }

    @JsonProperty(value="Comment")
    public void setComment(String comment) {
        this.comment = comment;
    }

    @JsonProperty(value="Face")
    public Face getFace() {
        return this.face;
    }

    @JsonProperty(value="Face")
    public void setFace(Face face) {
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
        sb.append(Tag.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("level");
        sb.append('=');
        sb.append(this.level == null ? "<null>" : this.level);
        sb.append(',');
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
        sb.append("personID");
        sb.append('=');
        sb.append(this.personID == null ? "<null>" : this.personID);
        sb.append(',');
        sb.append("originPersonID");
        sb.append('=');
        sb.append(this.originPersonID == null ? "<null>" : this.originPersonID);
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
        sb.append("comment");
        sb.append('=');
        sb.append(this.comment == null ? "<null>" : this.comment);
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
        result = result * 31 + (this.level == null ? 0 : this.level.hashCode());
        result = result * 31 + (this.repoName == null ? 0 : this.repoName.hashCode());
        result = result * 31 + (this.confidence == null ? 0 : this.confidence.hashCode());
        result = result * 31 + (this.externalID == null ? 0 : this.externalID.hashCode());
        result = result * 31 + (this.face == null ? 0 : this.face.hashCode());
        result = result * 31 + (this.registerID == null ? 0 : this.registerID.hashCode());
        result = result * 31 + (this.name == null ? 0 : this.name.hashCode());
        result = result * 31 + (this.originPersonID == null ? 0 : this.originPersonID.hashCode());
        result = result * 31 + (this.personID == null ? 0 : this.personID.hashCode());
        result = result * 31 + (this.comment == null ? 0 : this.comment.hashCode());
        result = result * 31 + (this.additionalProperties == null ? 0 : this.additionalProperties.hashCode());
        return result;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Tag)) {
            return false;
        }
        Tag rhs = (Tag)other;
        return (this.repoID == rhs.repoID || this.repoID != null && this.repoID.equals(rhs.repoID)) && (this.level == rhs.level || this.level != null && this.level.equals(rhs.level)) && (this.repoName == rhs.repoName || this.repoName != null && this.repoName.equals(rhs.repoName)) && (this.confidence == rhs.confidence || this.confidence != null && this.confidence.equals(rhs.confidence)) && (this.externalID == rhs.externalID || this.externalID != null && this.externalID.equals(rhs.externalID)) && (this.face == rhs.face || this.face != null && this.face.equals(rhs.face)) && (this.registerID == rhs.registerID || this.registerID != null && this.registerID.equals(rhs.registerID)) && (this.name == rhs.name || this.name != null && this.name.equals(rhs.name)) && (this.originPersonID == rhs.originPersonID || this.originPersonID != null && this.originPersonID.equals(rhs.originPersonID)) && (this.personID == rhs.personID || this.personID != null && this.personID.equals(rhs.personID)) && (this.comment == rhs.comment || this.comment != null && this.comment.equals(rhs.comment)) && (this.additionalProperties == rhs.additionalProperties || this.additionalProperties != null && this.additionalProperties.equals(rhs.additionalProperties));
    }
}
