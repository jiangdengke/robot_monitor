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
import com.robotmonitor.config.domain.deepglint.face.CaptureSearchEntity;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CaptureSearchData {
    @JsonProperty(value="Count")
    private Integer count;
    @JsonProperty(value="Entities")
    private List<CaptureSearchEntity> entities;

    public CaptureSearchData() {
    }

    public CaptureSearchData(Integer count, List<CaptureSearchEntity> entities) {
        this.count = count;
        this.entities = entities;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<CaptureSearchEntity> getEntities() {
        return this.entities;
    }

    public void setEntities(List<CaptureSearchEntity> entities) {
        this.entities = entities;
    }

    public String toString() {
        return "CaptureSearchData{count=" + this.count + ", entities=" + this.entities + "}";
    }
}
