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

@JsonIgnoreProperties(ignoreUnknown=true)
public class RegisteredImage {
    @JsonProperty(value="ImageID")
    private String imageId;
    @JsonProperty(value="Cts")
    private Long cts;
    @JsonProperty(value="Uts")
    private Long uts;
    @JsonProperty(value="ImageUrl")
    private String imageUrl;
    @JsonProperty(value="RegisterID")
    private String registerId;
    @JsonProperty(value="RepoID")
    private String repoId;

    public RegisteredImage() {
    }

    public RegisteredImage(String imageId, String imageUrl) {
        this.imageId = imageId;
        this.imageUrl = imageUrl;
    }

    public String getImageId() {
        return this.imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
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

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRegisterId() {
        return this.registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    public String getRepoId() {
        return this.repoId;
    }

    public void setRepoId(String repoId) {
        this.repoId = repoId;
    }
}
