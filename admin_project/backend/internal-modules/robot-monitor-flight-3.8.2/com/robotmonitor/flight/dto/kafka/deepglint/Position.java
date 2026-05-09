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
@JsonPropertyOrder(value={"X", "Y", "Width", "Height"})
public class Position {
    @JsonProperty(value="X")
    private Integer x;
    @JsonProperty(value="Y")
    private Integer y;
    @JsonProperty(value="Width")
    private Integer width;
    @JsonProperty(value="Height")
    private Integer height;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty(value="X")
    public Integer getX() {
        return this.x;
    }

    @JsonProperty(value="X")
    public void setX(Integer x) {
        this.x = x;
    }

    @JsonProperty(value="Y")
    public Integer getY() {
        return this.y;
    }

    @JsonProperty(value="Y")
    public void setY(Integer y) {
        this.y = y;
    }

    @JsonProperty(value="Width")
    public Integer getWidth() {
        return this.width;
    }

    @JsonProperty(value="Width")
    public void setWidth(Integer width) {
        this.width = width;
    }

    @JsonProperty(value="Height")
    public Integer getHeight() {
        return this.height;
    }

    @JsonProperty(value="Height")
    public void setHeight(Integer height) {
        this.height = height;
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
        sb.append(Position.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("x");
        sb.append('=');
        sb.append(this.x == null ? "<null>" : this.x);
        sb.append(',');
        sb.append("y");
        sb.append('=');
        sb.append(this.y == null ? "<null>" : this.y);
        sb.append(',');
        sb.append("width");
        sb.append('=');
        sb.append(this.width == null ? "<null>" : this.width);
        sb.append(',');
        sb.append("height");
        sb.append('=');
        sb.append(this.height == null ? "<null>" : this.height);
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
        result = result * 31 + (this.x == null ? 0 : this.x.hashCode());
        result = result * 31 + (this.width == null ? 0 : this.width.hashCode());
        result = result * 31 + (this.y == null ? 0 : this.y.hashCode());
        result = result * 31 + (this.additionalProperties == null ? 0 : this.additionalProperties.hashCode());
        result = result * 31 + (this.height == null ? 0 : this.height.hashCode());
        return result;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Position)) {
            return false;
        }
        Position rhs = (Position)other;
        return (this.x == rhs.x || this.x != null && this.x.equals(rhs.x)) && (this.width == rhs.width || this.width != null && this.width.equals(rhs.width)) && (this.y == rhs.y || this.y != null && this.y.equals(rhs.y)) && (this.additionalProperties == rhs.additionalProperties || this.additionalProperties != null && this.additionalProperties.equals(rhs.additionalProperties)) && (this.height == rhs.height || this.height != null && this.height.equals(rhs.height));
    }
}
