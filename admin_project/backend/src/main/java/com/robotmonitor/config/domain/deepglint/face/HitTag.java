/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.robotmonitor.config.domain.deepglint.face;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.robotmonitor.config.domain.deepglint.face.FaceImage;

@JsonIgnoreProperties(ignoreUnknown=true)
public class HitTag {
    @JsonProperty(value="RepoID")
    private String repoId;
    @JsonProperty(value="RepoName")
    private String repoName;
    @JsonProperty(value="RegisterID")
    private String registerId;
    @JsonProperty(value="RegisterUrl")
    private String registerUrl;
    @JsonProperty(value="PersonID")
    private String personId;
    @JsonProperty(value="OriginRegisterPersonID")
    private String originRegisterPersonId;
    @JsonProperty(value="ExternalID")
    private String externalId;
    @JsonProperty(value="Name")
    private String name;
    @JsonProperty(value="Confidence")
    private Float confidence;
    @JsonProperty(value="Face")
    private FaceImage face;

    public HitTag() {
    }

    public HitTag(String repoId, String name, Float confidence) {
        this.repoId = repoId;
        this.name = name;
        this.confidence = confidence;
    }

    public String getRepoId() {
        return this.repoId;
    }

    public void setRepoId(String repoId) {
        this.repoId = repoId;
    }

    public String getRepoName() {
        return this.repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getRegisterId() {
        return this.registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    public String getRegisterUrl() {
        return this.registerUrl;
    }

    public void setRegisterUrl(String registerUrl) {
        this.registerUrl = registerUrl;
    }

    public String getPersonId() {
        return this.personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getOriginRegisterPersonId() {
        return this.originRegisterPersonId;
    }

    public void setOriginRegisterPersonId(String originRegisterPersonId) {
        this.originRegisterPersonId = originRegisterPersonId;
    }

    public String getExternalId() {
        return this.externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getConfidence() {
        return this.confidence;
    }

    public void setConfidence(Float confidence) {
        this.confidence = confidence;
    }

    public FaceImage getFace() {
        return this.face;
    }

    public void setFace(FaceImage face) {
        this.face = face;
    }
}
