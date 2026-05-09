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
package com.robotmonitor.flight.dto.kafka.deepglint.data.metadata;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.robotmonitor.flight.dto.kafka.deepglint.data.metadata.TagFace;
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
    private TagFace face;
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

    public String getLevel() {
        return this.level;
    }

    public String getRepoID() {
        return this.repoID;
    }

    public String getRepoName() {
        return this.repoName;
    }

    public String getRegisterID() {
        return this.registerID;
    }

    public String getPersonID() {
        return this.personID;
    }

    public String getOriginPersonID() {
        return this.originPersonID;
    }

    public String getName() {
        return this.name;
    }

    public String getExternalID() {
        return this.externalID;
    }

    public Double getConfidence() {
        return this.confidence;
    }

    public String getComment() {
        return this.comment;
    }

    public TagFace getFace() {
        return this.face;
    }

    @JsonProperty(value="Level")
    public void setLevel(String level) {
        this.level = level;
    }

    @JsonProperty(value="RepoID")
    public void setRepoID(String repoID) {
        this.repoID = repoID;
    }

    @JsonProperty(value="RepoName")
    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    @JsonProperty(value="RegisterID")
    public void setRegisterID(String registerID) {
        this.registerID = registerID;
    }

    @JsonProperty(value="PersonID")
    public void setPersonID(String personID) {
        this.personID = personID;
    }

    @JsonProperty(value="OriginPersonID")
    public void setOriginPersonID(String originPersonID) {
        this.originPersonID = originPersonID;
    }

    @JsonProperty(value="Name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty(value="ExternalID")
    public void setExternalID(String externalID) {
        this.externalID = externalID;
    }

    @JsonProperty(value="Confidence")
    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    @JsonProperty(value="Comment")
    public void setComment(String comment) {
        this.comment = comment;
    }

    @JsonProperty(value="Face")
    public void setFace(TagFace face) {
        this.face = face;
    }

    @JsonIgnore
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Tag)) {
            return false;
        }
        Tag other = (Tag)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Double this$confidence = this.getConfidence();
        Double other$confidence = other.getConfidence();
        if (this$confidence == null ? other$confidence != null : !((Object)this$confidence).equals(other$confidence)) {
            return false;
        }
        String this$level = this.getLevel();
        String other$level = other.getLevel();
        if (this$level == null ? other$level != null : !this$level.equals(other$level)) {
            return false;
        }
        String this$repoID = this.getRepoID();
        String other$repoID = other.getRepoID();
        if (this$repoID == null ? other$repoID != null : !this$repoID.equals(other$repoID)) {
            return false;
        }
        String this$repoName = this.getRepoName();
        String other$repoName = other.getRepoName();
        if (this$repoName == null ? other$repoName != null : !this$repoName.equals(other$repoName)) {
            return false;
        }
        String this$registerID = this.getRegisterID();
        String other$registerID = other.getRegisterID();
        if (this$registerID == null ? other$registerID != null : !this$registerID.equals(other$registerID)) {
            return false;
        }
        String this$personID = this.getPersonID();
        String other$personID = other.getPersonID();
        if (this$personID == null ? other$personID != null : !this$personID.equals(other$personID)) {
            return false;
        }
        String this$originPersonID = this.getOriginPersonID();
        String other$originPersonID = other.getOriginPersonID();
        if (this$originPersonID == null ? other$originPersonID != null : !this$originPersonID.equals(other$originPersonID)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        String this$externalID = this.getExternalID();
        String other$externalID = other.getExternalID();
        if (this$externalID == null ? other$externalID != null : !this$externalID.equals(other$externalID)) {
            return false;
        }
        String this$comment = this.getComment();
        String other$comment = other.getComment();
        if (this$comment == null ? other$comment != null : !this$comment.equals(other$comment)) {
            return false;
        }
        TagFace this$face = this.getFace();
        TagFace other$face = other.getFace();
        if (this$face == null ? other$face != null : !((Object)this$face).equals(other$face)) {
            return false;
        }
        Map<String, Object> this$additionalProperties = this.getAdditionalProperties();
        Map<String, Object> other$additionalProperties = other.getAdditionalProperties();
        return !(this$additionalProperties == null ? other$additionalProperties != null : !((Object)this$additionalProperties).equals(other$additionalProperties));
    }

    protected boolean canEqual(Object other) {
        return other instanceof Tag;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Double $confidence = this.getConfidence();
        result = result * 59 + ($confidence == null ? 43 : ((Object)$confidence).hashCode());
        String $level = this.getLevel();
        result = result * 59 + ($level == null ? 43 : $level.hashCode());
        String $repoID = this.getRepoID();
        result = result * 59 + ($repoID == null ? 43 : $repoID.hashCode());
        String $repoName = this.getRepoName();
        result = result * 59 + ($repoName == null ? 43 : $repoName.hashCode());
        String $registerID = this.getRegisterID();
        result = result * 59 + ($registerID == null ? 43 : $registerID.hashCode());
        String $personID = this.getPersonID();
        result = result * 59 + ($personID == null ? 43 : $personID.hashCode());
        String $originPersonID = this.getOriginPersonID();
        result = result * 59 + ($originPersonID == null ? 43 : $originPersonID.hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $externalID = this.getExternalID();
        result = result * 59 + ($externalID == null ? 43 : $externalID.hashCode());
        String $comment = this.getComment();
        result = result * 59 + ($comment == null ? 43 : $comment.hashCode());
        TagFace $face = this.getFace();
        result = result * 59 + ($face == null ? 43 : ((Object)$face).hashCode());
        Map<String, Object> $additionalProperties = this.getAdditionalProperties();
        result = result * 59 + ($additionalProperties == null ? 43 : ((Object)$additionalProperties).hashCode());
        return result;
    }

    public String toString() {
        return "Tag(level=" + this.getLevel() + ", repoID=" + this.getRepoID() + ", repoName=" + this.getRepoName() + ", registerID=" + this.getRegisterID() + ", personID=" + this.getPersonID() + ", originPersonID=" + this.getOriginPersonID() + ", name=" + this.getName() + ", externalID=" + this.getExternalID() + ", confidence=" + this.getConfidence() + ", comment=" + this.getComment() + ", face=" + this.getFace() + ", additionalProperties=" + this.getAdditionalProperties() + ")";
    }
}
