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
import com.robotmonitor.config.domain.deepglint.compare.RegisteredImage;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RegisterPersonData {
    @JsonProperty(value="RegisterID")
    private String registerId;
    @JsonProperty(value="Cts")
    private Long cts;
    @JsonProperty(value="Uts")
    private Long uts;
    @JsonProperty(value="RepoID")
    private String repoId;
    @JsonProperty(value="Name")
    private String name;
    @JsonProperty(value="ExternalID")
    private String externalId;
    @JsonProperty(value="PersonID")
    private String personId;
    @JsonProperty(value="Status")
    private String status;
    @JsonProperty(value="Images")
    private List<RegisteredImage> images;
    @JsonProperty(value="Comment")
    private String comment;
    @JsonProperty(value="OriginPersonID")
    private String originPersonId;

    public RegisterPersonData() {
    }

    public RegisterPersonData(String registerId, String name, String status) {
        this.registerId = registerId;
        this.name = name;
        this.status = status;
    }

    public String getRegisterId() {
        return this.registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    public Long getCts() {
        return this.cts;
    }

    public void setCts(Long cts) {
        this.cts = cts;
    }

    public Long getUts() {
        return this.uts;
    }

    public void setUts(Long uts) {
        this.uts = uts;
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

    public String getPersonId() {
        return this.personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<RegisteredImage> getImages() {
        return this.images;
    }

    public void setImages(List<RegisteredImage> images) {
        this.images = images;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOriginPersonId() {
        return this.originPersonId;
    }

    public void setOriginPersonId(String originPersonId) {
        this.originPersonId = originPersonId;
    }
}
