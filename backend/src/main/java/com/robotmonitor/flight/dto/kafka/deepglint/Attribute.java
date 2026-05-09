/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonAnyGetter
 *  com.fasterxml.jackson.annotation.JsonAnySetter
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.fasterxml.jackson.annotation.JsonPropertyOrder
 */
package com.robotmonitor.flight.dto.kafka.deepglint;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value={"New", "Hat", "Glass", "Age", "Gender", "Helmet", "Mask"})
public class Attribute {
    @JsonProperty(value="New")
    private Boolean _new;
    @JsonProperty(value="Hat")
    private String hat;
    @JsonProperty(value="Glass")
    private String glass;
    @JsonProperty(value="Age")
    private Integer age;
    @JsonProperty(value="Gender")
    private String gender;
    @JsonProperty(value="Helmet")
    private String helmet;
    @JsonProperty(value="Mask")
    private String mask;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty(value="New")
    public Boolean getNew() {
        return this._new;
    }

    @JsonProperty(value="New")
    public void setNew(Boolean _new) {
        this._new = _new;
    }

    @JsonProperty(value="Hat")
    public String getHat() {
        return this.hat;
    }

    @JsonProperty(value="Hat")
    public void setHat(String hat) {
        this.hat = hat;
    }

    @JsonProperty(value="Glass")
    public String getGlass() {
        return this.glass;
    }

    @JsonProperty(value="Glass")
    public void setGlass(String glass) {
        this.glass = glass;
    }

    @JsonProperty(value="Age")
    public Integer getAge() {
        return this.age;
    }

    @JsonProperty(value="Age")
    public void setAge(Integer age) {
        this.age = age;
    }

    @JsonProperty(value="Gender")
    public String getGender() {
        return this.gender;
    }

    @JsonProperty(value="Gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    @JsonProperty(value="Helmet")
    public String getHelmet() {
        return this.helmet;
    }

    @JsonProperty(value="Helmet")
    public void setHelmet(String helmet) {
        this.helmet = helmet;
    }

    @JsonProperty(value="Mask")
    public String getMask() {
        return this.mask;
    }

    @JsonProperty(value="Mask")
    public void setMask(String mask) {
        this.mask = mask;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Attribute.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("_new");
        sb.append('=');
        sb.append(this._new == null ? "<null>" : this._new);
        sb.append(',');
        sb.append("hat");
        sb.append('=');
        sb.append(this.hat == null ? "<null>" : this.hat);
        sb.append(',');
        sb.append("glass");
        sb.append('=');
        sb.append(this.glass == null ? "<null>" : this.glass);
        sb.append(',');
        sb.append("age");
        sb.append('=');
        sb.append(this.age == null ? "<null>" : this.age);
        sb.append(',');
        sb.append("gender");
        sb.append('=');
        sb.append(this.gender == null ? "<null>" : this.gender);
        sb.append(',');
        sb.append("helmet");
        sb.append('=');
        sb.append(this.helmet == null ? "<null>" : this.helmet);
        sb.append(',');
        sb.append("mask");
        sb.append('=');
        sb.append(this.mask == null ? "<null>" : this.mask);
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(this.additionalProperties == null ? "<null>" : this.additionalProperties);
        sb.append(',');
        if (sb.charAt(sb.length() - 1) == ',') {
            sb.setCharAt(sb.length() - 1, ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public int hashCode() {
        int result = 1;
        result = result * 31 + (this.glass == null ? 0 : this.glass.hashCode());
        result = result * 31 + (this.gender == null ? 0 : this.gender.hashCode());
        result = result * 31 + (this.helmet == null ? 0 : this.helmet.hashCode());
        result = result * 31 + (this.hat == null ? 0 : this.hat.hashCode());
        result = result * 31 + (this.additionalProperties == null ? 0 : this.additionalProperties.hashCode());
        result = result * 31 + (this._new == null ? 0 : this._new.hashCode());
        result = result * 31 + (this.age == null ? 0 : this.age.hashCode());
        result = result * 31 + (this.mask == null ? 0 : this.mask.hashCode());
        return result;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Attribute)) {
            return false;
        }
        Attribute rhs = (Attribute)other;
        return (this.glass == rhs.glass || this.glass != null && this.glass.equals(rhs.glass)) && (this.gender == rhs.gender || this.gender != null && this.gender.equals(rhs.gender)) && (this.helmet == rhs.helmet || this.helmet != null && this.helmet.equals(rhs.helmet)) && (this.hat == rhs.hat || this.hat != null && this.hat.equals(rhs.hat)) && (this.additionalProperties == rhs.additionalProperties || this.additionalProperties != null && this.additionalProperties.equals(rhs.additionalProperties)) && (this._new == rhs._new || this._new != null && this._new.equals(rhs._new)) && (this.age == rhs.age || this.age != null && this.age.equals(rhs.age)) && (this.mask == rhs.mask || this.mask != null && this.mask.equals(rhs.mask));
    }
}
