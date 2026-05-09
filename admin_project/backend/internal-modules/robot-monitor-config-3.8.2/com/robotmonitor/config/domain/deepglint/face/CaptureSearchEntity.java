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
import com.robotmonitor.config.domain.deepglint.face.CaptureHumanBody;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CaptureSearchEntity {
    @JsonProperty(value="Type")
    private String type;
    @JsonProperty(value="Face")
    private CaptureFace face;
    @JsonProperty(value="HumanBody")
    private CaptureHumanBody humanBody;
    @JsonProperty(value="MotorVehicle")
    private Object motorVehicle;
    @JsonProperty(value="NonMotorVehicle")
    private Object nonMotorVehicle;
    @JsonProperty(value="Confidence")
    private Float confidence;

    public CaptureSearchEntity() {
    }

    public CaptureSearchEntity(String type, CaptureFace face, Float confidence) {
        this.type = type;
        this.face = face;
        this.confidence = confidence;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CaptureFace getFace() {
        return this.face;
    }

    public void setFace(CaptureFace face) {
        this.face = face;
    }

    public CaptureHumanBody getHumanBody() {
        return this.humanBody;
    }

    public void setHumanBody(CaptureHumanBody humanBody) {
        this.humanBody = humanBody;
    }

    public Object getMotorVehicle() {
        return this.motorVehicle;
    }

    public void setMotorVehicle(Object motorVehicle) {
        this.motorVehicle = motorVehicle;
    }

    public Object getNonMotorVehicle() {
        return this.nonMotorVehicle;
    }

    public void setNonMotorVehicle(Object nonMotorVehicle) {
        this.nonMotorVehicle = nonMotorVehicle;
    }

    public Float getConfidence() {
        return this.confidence;
    }

    public void setConfidence(Float confidence) {
        this.confidence = confidence;
    }

    public String toString() {
        return "CaptureSearchEntity{type='" + this.type + "', face=" + this.face + ", humanBody=" + this.humanBody + ", confidence=" + this.confidence + "}";
    }
}
