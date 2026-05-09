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
package com.robotmonitor.config.domain.deepglint.similar;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.robotmonitor.config.domain.deepglint.similar.PersonSimilarDataPerson;
import com.robotmonitor.config.domain.deepglint.similar.PersonSimilarDataRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value={"Request", "Persons"})
public class PersonSimilarData {
    @JsonProperty(value="Request")
    private PersonSimilarDataRequest request;
    @JsonProperty(value="Persons")
    private List<PersonSimilarDataPerson> persons = new ArrayList<PersonSimilarDataPerson>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty(value="Request")
    public PersonSimilarDataRequest getRequest() {
        return this.request;
    }

    @JsonProperty(value="Request")
    public void setRequest(PersonSimilarDataRequest request) {
        this.request = request;
    }

    @JsonProperty(value="Persons")
    public List<PersonSimilarDataPerson> getPersons() {
        return this.persons;
    }

    @JsonProperty(value="Persons")
    public void setPersons(List<PersonSimilarDataPerson> persons) {
        this.persons = persons;
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
        sb.append(PersonSimilarData.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("request");
        sb.append('=');
        sb.append(this.request == null ? "<null>" : this.request);
        sb.append(',');
        sb.append("persons");
        sb.append('=');
        sb.append(this.persons == null ? "<null>" : this.persons);
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
        result = result * 31 + (this.request == null ? 0 : this.request.hashCode());
        result = result * 31 + (this.persons == null ? 0 : this.persons.hashCode());
        result = result * 31 + (this.additionalProperties == null ? 0 : this.additionalProperties.hashCode());
        return result;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PersonSimilarData)) {
            return false;
        }
        PersonSimilarData rhs = (PersonSimilarData)other;
        return (this.request == rhs.request || this.request != null && this.request.equals(rhs.request)) && (this.persons == rhs.persons || this.persons != null && this.persons.equals(rhs.persons)) && (this.additionalProperties == rhs.additionalProperties || this.additionalProperties != null && this.additionalProperties.equals(rhs.additionalProperties));
    }
}
