/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.config.ConfigRegion
 */
package com.robotmonitor.config.domain;

import com.robotmonitor.common.core.domain.config.ConfigRegion;
import com.robotmonitor.config.domain.ConfigDevice;

public class RecognitionResult {
    private ConfigRegion region;
    private ConfigDevice configDevice;
    private String recognitionType;
    private String cts;
    private String origImageUrl;
    private String registerImageUrl;

    public ConfigRegion getRegion() {
        return this.region;
    }

    public void setRegion(ConfigRegion region) {
        this.region = region;
    }

    public ConfigDevice getConfigDevice() {
        return this.configDevice;
    }

    public void setConfigDevice(ConfigDevice configDevice) {
        this.configDevice = configDevice;
    }

    public String getRecognitionType() {
        return this.recognitionType;
    }

    public void setRecognitionType(String recognitionType) {
        this.recognitionType = recognitionType;
    }

    public String getCts() {
        return this.cts;
    }

    public void setCts(String cts) {
        this.cts = cts;
    }

    public String getOrigImageUrl() {
        return this.origImageUrl;
    }

    public void setOrigImageUrl(String origImageUrl) {
        this.origImageUrl = origImageUrl;
    }

    public String getRegisterImageUrl() {
        return this.registerImageUrl;
    }

    public void setRegisterImageUrl(String registerImageUrl) {
        this.registerImageUrl = registerImageUrl;
    }
}
