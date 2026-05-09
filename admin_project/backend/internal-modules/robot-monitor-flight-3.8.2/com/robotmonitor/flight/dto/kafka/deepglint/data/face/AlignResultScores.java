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
package com.robotmonitor.flight.dto.kafka.deepglint.data.face;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value={"global_front_face", "global_is_face", "local_is_face"})
public class AlignResultScores {
    @JsonProperty(value="global_front_face")
    private Integer globalFrontFace;
    @JsonProperty(value="global_is_face")
    private Integer globalIsFace;
    @JsonProperty(value="local_is_face")
    private Double localIsFace;
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

    public Integer getGlobalFrontFace() {
        return this.globalFrontFace;
    }

    public Integer getGlobalIsFace() {
        return this.globalIsFace;
    }

    public Double getLocalIsFace() {
        return this.localIsFace;
    }

    @JsonProperty(value="global_front_face")
    public void setGlobalFrontFace(Integer globalFrontFace) {
        this.globalFrontFace = globalFrontFace;
    }

    @JsonProperty(value="global_is_face")
    public void setGlobalIsFace(Integer globalIsFace) {
        this.globalIsFace = globalIsFace;
    }

    @JsonProperty(value="local_is_face")
    public void setLocalIsFace(Double localIsFace) {
        this.localIsFace = localIsFace;
    }

    @JsonIgnore
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AlignResultScores)) {
            return false;
        }
        AlignResultScores other = (AlignResultScores)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Integer this$globalFrontFace = this.getGlobalFrontFace();
        Integer other$globalFrontFace = other.getGlobalFrontFace();
        if (this$globalFrontFace == null ? other$globalFrontFace != null : !((Object)this$globalFrontFace).equals(other$globalFrontFace)) {
            return false;
        }
        Integer this$globalIsFace = this.getGlobalIsFace();
        Integer other$globalIsFace = other.getGlobalIsFace();
        if (this$globalIsFace == null ? other$globalIsFace != null : !((Object)this$globalIsFace).equals(other$globalIsFace)) {
            return false;
        }
        Double this$localIsFace = this.getLocalIsFace();
        Double other$localIsFace = other.getLocalIsFace();
        if (this$localIsFace == null ? other$localIsFace != null : !((Object)this$localIsFace).equals(other$localIsFace)) {
            return false;
        }
        Map<String, Object> this$additionalProperties = this.getAdditionalProperties();
        Map<String, Object> other$additionalProperties = other.getAdditionalProperties();
        return !(this$additionalProperties == null ? other$additionalProperties != null : !((Object)this$additionalProperties).equals(other$additionalProperties));
    }

    protected boolean canEqual(Object other) {
        return other instanceof AlignResultScores;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $globalFrontFace = this.getGlobalFrontFace();
        result = result * 59 + ($globalFrontFace == null ? 43 : ((Object)$globalFrontFace).hashCode());
        Integer $globalIsFace = this.getGlobalIsFace();
        result = result * 59 + ($globalIsFace == null ? 43 : ((Object)$globalIsFace).hashCode());
        Double $localIsFace = this.getLocalIsFace();
        result = result * 59 + ($localIsFace == null ? 43 : ((Object)$localIsFace).hashCode());
        Map<String, Object> $additionalProperties = this.getAdditionalProperties();
        result = result * 59 + ($additionalProperties == null ? 43 : ((Object)$additionalProperties).hashCode());
        return result;
    }

    public String toString() {
        return "AlignResultScores(globalFrontFace=" + this.getGlobalFrontFace() + ", globalIsFace=" + this.getGlobalIsFace() + ", localIsFace=" + this.getLocalIsFace() + ", additionalProperties=" + this.getAdditionalProperties() + ")";
    }
}
