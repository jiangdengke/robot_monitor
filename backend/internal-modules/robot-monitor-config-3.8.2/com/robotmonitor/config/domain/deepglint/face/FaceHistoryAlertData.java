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
import com.robotmonitor.config.domain.deepglint.face.CaptureFace;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FaceHistoryAlertData {
    @JsonProperty(value="CaptureFaces")
    private List<CaptureFace> captureFaces;

    public FaceHistoryAlertData() {
    }

    public FaceHistoryAlertData(List<CaptureFace> captureFaces) {
        this.captureFaces = captureFaces;
    }

    public List<CaptureFace> getCaptureFaces() {
        return this.captureFaces;
    }

    public void setCaptureFaces(List<CaptureFace> captureFaces) {
        this.captureFaces = captureFaces;
    }
}
