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
import com.robotmonitor.config.domain.deepglint.face.BoundingBox;
import com.robotmonitor.config.domain.deepglint.face.Rectangle;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class IncludeImage {
    @JsonProperty(value="URL")
    private String url;
    @JsonProperty(value="BinData")
    private String binData;
    @JsonProperty(value="Feature")
    private String feature;
    @JsonProperty(value="Rectangles")
    private List<Rectangle> rectangles;
    @JsonProperty(value="BoundingBoxes")
    private List<BoundingBox> boundingBoxes;

    public IncludeImage() {
    }

    public IncludeImage(String url) {
        this.url = url;
    }

    public IncludeImage(String binData, boolean isBase64) {
        this.binData = binData;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBinData() {
        return this.binData;
    }

    public void setBinData(String binData) {
        this.binData = binData;
    }

    public String getFeature() {
        return this.feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public List<Rectangle> getRectangles() {
        return this.rectangles;
    }

    public void setRectangles(List<Rectangle> rectangles) {
        this.rectangles = rectangles;
    }

    public List<BoundingBox> getBoundingBoxes() {
        return this.boundingBoxes;
    }

    public void setBoundingBoxes(List<BoundingBox> boundingBoxes) {
        this.boundingBoxes = boundingBoxes;
    }

    public String toString() {
        return "IncludeImage{url='" + this.url + "', binData='" + (this.binData != null ? "[OMITTED]" : null) + "', feature='" + this.feature + "', rectangles=" + this.rectangles + ", boundingBoxes=" + this.boundingBoxes + "}";
    }
}
