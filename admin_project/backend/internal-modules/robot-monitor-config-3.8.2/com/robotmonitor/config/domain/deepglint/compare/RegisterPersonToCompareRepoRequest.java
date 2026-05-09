/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.robotmonitor.config.domain.deepglint.compare;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.robotmonitor.config.domain.deepglint.compare.RegisterImage;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RegisterPersonToCompareRepoRequest {
    @JsonProperty(value="RepoId")
    private String repoId;
    @JsonProperty(value="Name")
    private String name;
    @JsonProperty(value="ExternalId")
    private String externalId;
    @JsonProperty(value="Comment")
    private String comment;
    @JsonProperty(value="Status")
    private String status;
    @JsonProperty(value="Images")
    private List<RegisterImage> images;
    @JsonProperty(value="ForcePersonId")
    private String forcePersonId;

    public RegisterPersonToCompareRepoRequest() {
    }

    public RegisterPersonToCompareRepoRequest(String repoId, String name, List<RegisterImage> images) {
        this.repoId = repoId;
        this.name = name;
        this.images = images;
    }

    public String getRepoId() {
        return this.repoId;
    }

    public void setRepoId(String repoId) {
        this.repoId = repoId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExternalId() {
        return this.externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<RegisterImage> getImages() {
        return this.images;
    }

    public void setImages(List<RegisterImage> images) {
        this.images = images;
    }

    public String getForcePersonId() {
        return this.forcePersonId;
    }

    public void setForcePersonId(String forcePersonId) {
        this.forcePersonId = forcePersonId;
    }

    public String toString() {
        return "RegisterPersonToCompareRepoRequest{repoId='" + this.repoId + "', name='" + this.name + "', externalId='" + this.externalId + "', comment='" + this.comment + "', status='" + this.status + "', images=" + this.images + ", forcePersonId='" + this.forcePersonId + "'}";
    }
}
