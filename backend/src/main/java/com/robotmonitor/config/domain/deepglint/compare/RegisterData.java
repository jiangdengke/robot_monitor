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
import com.robotmonitor.config.domain.deepglint.compare.RegisterImageData;
import com.robotmonitor.config.domain.deepglint.compare.RegisterPerson;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RegisterData {
    @JsonProperty(value="Register")
    private RegisterPerson registerPerson;
    @JsonProperty(value="Images")
    private List<RegisterImageData> images;

    public RegisterData() {
    }

    public RegisterData(RegisterPerson registerPerson, List<RegisterImageData> images) {
        this.registerPerson = registerPerson;
        this.images = images;
    }

    public List<RegisterImageData> getImages() {
        return this.images;
    }

    public void setImages(List<RegisterImageData> images) {
        this.images = images;
    }

    public RegisterPerson getRegisterPerson() {
        return this.registerPerson;
    }

    public void setRegisterPerson(RegisterPerson registerPerson) {
        this.registerPerson = registerPerson;
    }
}
