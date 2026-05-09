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
package com.robotmonitor.flight.dto.kafka.deepglint.data.face.img;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value={"Id", "Width", "Height", "URI", "Sn"})
public class FaceImg {
    @JsonProperty(value="Id")
    private String id;
    @JsonProperty(value="Width")
    private Integer width;
    @JsonProperty(value="Height")
    private Integer height;
    @JsonProperty(value="URI")
    private String uri;
    @JsonProperty(value="Sn")
    private Integer sn;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public String getId() {
        return this.id;
    }

    public Integer getWidth() {
        return this.width;
    }

    public Integer getHeight() {
        return this.height;
    }

    public String getUri() {
        return this.uri;
    }

    public Integer getSn() {
        return this.sn;
    }

    @JsonProperty(value="Id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty(value="Width")
    public void setWidth(Integer width) {
        this.width = width;
    }

    @JsonProperty(value="Height")
    public void setHeight(Integer height) {
        this.height = height;
    }

    @JsonProperty(value="URI")
    public void setUri(String uri) {
        this.uri = uri;
    }

    @JsonProperty(value="Sn")
    public void setSn(Integer sn) {
        this.sn = sn;
    }

    @JsonIgnore
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FaceImg)) {
            return false;
        }
        FaceImg other = (FaceImg)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Integer this$width = this.getWidth();
        Integer other$width = other.getWidth();
        if (this$width == null ? other$width != null : !((Object)this$width).equals(other$width)) {
            return false;
        }
        Integer this$height = this.getHeight();
        Integer other$height = other.getHeight();
        if (this$height == null ? other$height != null : !((Object)this$height).equals(other$height)) {
            return false;
        }
        Integer this$sn = this.getSn();
        Integer other$sn = other.getSn();
        if (this$sn == null ? other$sn != null : !((Object)this$sn).equals(other$sn)) {
            return false;
        }
        String this$id = this.getId();
        String other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        String this$uri = this.getUri();
        String other$uri = other.getUri();
        if (this$uri == null ? other$uri != null : !this$uri.equals(other$uri)) {
            return false;
        }
        Map<String, Object> this$additionalProperties = this.getAdditionalProperties();
        Map<String, Object> other$additionalProperties = other.getAdditionalProperties();
        return !(this$additionalProperties == null ? other$additionalProperties != null : !((Object)this$additionalProperties).equals(other$additionalProperties));
    }

    protected boolean canEqual(Object other) {
        return other instanceof FaceImg;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $width = this.getWidth();
        result = result * 59 + ($width == null ? 43 : ((Object)$width).hashCode());
        Integer $height = this.getHeight();
        result = result * 59 + ($height == null ? 43 : ((Object)$height).hashCode());
        Integer $sn = this.getSn();
        result = result * 59 + ($sn == null ? 43 : ((Object)$sn).hashCode());
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        String $uri = this.getUri();
        result = result * 59 + ($uri == null ? 43 : $uri.hashCode());
        Map<String, Object> $additionalProperties = this.getAdditionalProperties();
        result = result * 59 + ($additionalProperties == null ? 43 : ((Object)$additionalProperties).hashCode());
        return result;
    }

    public String toString() {
        return "FaceImg(id=" + this.getId() + ", width=" + this.getWidth() + ", height=" + this.getHeight() + ", uri=" + this.getUri() + ", sn=" + this.getSn() + ", additionalProperties=" + this.getAdditionalProperties() + ")";
    }
}
