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

    @JsonProperty(value="ObjectID1")
    public String getObjectID1() {
        return this.objectID1;
    }

    @JsonProperty(value="ObjectID1")
    public void setObjectID1(String objectID1) {
        this.objectID1 = objectID1;
    }

    @JsonProperty(value="ObjectID2")
    public String getObjectID2() {
        return this.objectID2;
    }

    @JsonProperty(value="ObjectID2")
    public void setObjectID2(String objectID2) {
        this.objectID2 = objectID2;
    }

    @JsonProperty(value="Relation")
    public String getRelation() {
        return this.relation;
    }

    @JsonProperty(value="Relation")
    public void setRelation(String relation) {
        this.relation = relation;
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
        sb.append(Relationship.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("objectID1");
        sb.append('=');
        sb.append(this.objectID1 == null ? "<null>" : this.objectID1);
        sb.append(',');
        sb.append("objectID2");
        sb.append('=');
        sb.append(this.objectID2 == null ? "<null>" : this.objectID2);
        sb.append(',');
        sb.append("relation");
        sb.append('=');
        sb.append(this.relation == null ? "<null>" : this.relation);
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
        result = result * 31 + (this.objectID1 == null ? 0 : this.objectID1.hashCode());
        result = result * 31 + (this.additionalProperties == null ? 0 : this.additionalProperties.hashCode());
        result = result * 31 + (this.objectID2 == null ? 0 : this.objectID2.hashCode());
        result = result * 31 + (this.relation == null ? 0 : this.relation.hashCode());
        return result;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Relationship)) {
            return false;
        }
        Relationship rhs = (Relationship)other;
        return (this.objectID1 == rhs.objectID1 || this.objectID1 != null && this.objectID1.equals(rhs.objectID1)) && (this.additionalProperties == rhs.additionalProperties || this.additionalProperties != null && this.additionalProperties.equals(rhs.additionalProperties)) && (this.objectID2 == rhs.objectID2 || this.objectID2 != null && this.objectID2.equals(rhs.objectID2)) && (this.relation == rhs.relation || this.relation != null && this.relation.equals(rhs.relation));
    }
}
