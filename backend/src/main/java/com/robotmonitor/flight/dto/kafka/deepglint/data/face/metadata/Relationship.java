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
package com.robotmonitor.flight.dto.kafka.deepglint.data.face.metadata;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value={"ObjectID1", "ObjectID2", "Relation"})
public class Relationship {
    @JsonProperty(value="ObjectID1")
    private String objectID1;
    @JsonProperty(value="ObjectID2")
    private String objectID2;
    @JsonProperty(value="Relation")
    private String relation;
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

    public String getObjectID1() {
        return this.objectID1;
    }

    public String getObjectID2() {
        return this.objectID2;
    }

    public String getRelation() {
        return this.relation;
    }

    @JsonProperty(value="ObjectID1")
    public void setObjectID1(String objectID1) {
        this.objectID1 = objectID1;
    }

    @JsonProperty(value="ObjectID2")
    public void setObjectID2(String objectID2) {
        this.objectID2 = objectID2;
    }

    @JsonProperty(value="Relation")
    public void setRelation(String relation) {
        this.relation = relation;
    }

    @JsonIgnore
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Relationship)) {
            return false;
        }
        Relationship other = (Relationship)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$objectID1 = this.getObjectID1();
        String other$objectID1 = other.getObjectID1();
        if (this$objectID1 == null ? other$objectID1 != null : !this$objectID1.equals(other$objectID1)) {
            return false;
        }
        String this$objectID2 = this.getObjectID2();
        String other$objectID2 = other.getObjectID2();
        if (this$objectID2 == null ? other$objectID2 != null : !this$objectID2.equals(other$objectID2)) {
            return false;
        }
        String this$relation = this.getRelation();
        String other$relation = other.getRelation();
        if (this$relation == null ? other$relation != null : !this$relation.equals(other$relation)) {
            return false;
        }
        Map<String, Object> this$additionalProperties = this.getAdditionalProperties();
        Map<String, Object> other$additionalProperties = other.getAdditionalProperties();
        return !(this$additionalProperties == null ? other$additionalProperties != null : !((Object)this$additionalProperties).equals(other$additionalProperties));
    }

    protected boolean canEqual(Object other) {
        return other instanceof Relationship;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $objectID1 = this.getObjectID1();
        result = result * 59 + ($objectID1 == null ? 43 : $objectID1.hashCode());
        String $objectID2 = this.getObjectID2();
        result = result * 59 + ($objectID2 == null ? 43 : $objectID2.hashCode());
        String $relation = this.getRelation();
        result = result * 59 + ($relation == null ? 43 : $relation.hashCode());
        Map<String, Object> $additionalProperties = this.getAdditionalProperties();
        result = result * 59 + ($additionalProperties == null ? 43 : ((Object)$additionalProperties).hashCode());
        return result;
    }

    public String toString() {
        return "Relationship(objectID1=" + this.getObjectID1() + ", objectID2=" + this.getObjectID2() + ", relation=" + this.getRelation() + ", additionalProperties=" + this.getAdditionalProperties() + ")";
    }
}
