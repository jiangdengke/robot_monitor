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
import com.robotmonitor.flight.dto.kafka.deepglint.Attribute;
import com.robotmonitor.flight.dto.kafka.deepglint.Face;
import com.robotmonitor.flight.dto.kafka.deepglint.OrigImage;
import com.robotmonitor.flight.dto.kafka.deepglint.Position;
import com.robotmonitor.flight.dto.kafka.deepglint.Quality;
import com.robotmonitor.flight.dto.kafka.deepglint.Relationship;
import com.robotmonitor.flight.dto.kafka.deepglint.Tag;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value={"Type", "AppKey", "PersonID", "OriginPersonID", "Time", "PushTime", "Face", "Quality", "Attribute", "Position", "OrigImage", "Tags", "DeviceGroupID", "Relationships"})
public class FaceKafka {
    @JsonProperty(value="Type")
    private String type;
    @JsonProperty(value="AppKey")
    private String appKey;
    @JsonProperty(value="PersonID")
    private String personID;
    @JsonProperty(value="OriginPersonID")
    private String originPersonID;
    @JsonProperty(value="Time")
    private Long time;
    @JsonProperty(value="PushTime")
    private Long pushTime;
    @JsonProperty(value="Face")
    private Face face;
    @JsonProperty(value="Quality")
    private Quality quality;
    @JsonProperty(value="Attribute")
    private Attribute attribute;
    @JsonProperty(value="Position")
    private Position position;
    @JsonProperty(value="OrigImage")
    private OrigImage origImage;
    @JsonProperty(value="Tags")
    private List<Tag> tags = new ArrayList<Tag>();
    @JsonProperty(value="DeviceGroupID")
    private String deviceGroupID;
    @JsonProperty(value="Relationships")
    private List<Relationship> relationships = new ArrayList<Relationship>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty(value="Type")
    public String getType() {
        return this.type;
    }

    @JsonProperty(value="Type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty(value="AppKey")
    public String getAppKey() {
        return this.appKey;
    }

    @JsonProperty(value="AppKey")
    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    @JsonProperty(value="PersonID")
    public String getPersonID() {
        return this.personID;
    }

    @JsonProperty(value="PersonID")
    public void setPersonID(String personID) {
        this.personID = personID;
    }

    @JsonProperty(value="OriginPersonID")
    public String getOriginPersonID() {
        return this.originPersonID;
    }

    @JsonProperty(value="OriginPersonID")
    public void setOriginPersonID(String originPersonID) {
        this.originPersonID = originPersonID;
    }

    @JsonProperty(value="Time")
    public Long getTime() {
        return this.time;
    }

    @JsonProperty(value="Time")
    public void setTime(Long time) {
        this.time = time;
    }

    @JsonProperty(value="PushTime")
    public Long getPushTime() {
        return this.pushTime;
    }

    @JsonProperty(value="PushTime")
    public void setPushTime(Long pushTime) {
        this.pushTime = pushTime;
    }

    @JsonProperty(value="Face")
    public Face getFace() {
        return this.face;
    }

    @JsonProperty(value="Face")
    public void setFace(Face face) {
        this.face = face;
    }

    @JsonProperty(value="Quality")
    public Quality getQuality() {
        return this.quality;
    }

    @JsonProperty(value="Quality")
    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    @JsonProperty(value="Attribute")
    public Attribute getAttribute() {
        return this.attribute;
    }

    @JsonProperty(value="Attribute")
    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    @JsonProperty(value="Position")
    public Position getPosition() {
        return this.position;
    }

    @JsonProperty(value="Position")
    public void setPosition(Position position) {
        this.position = position;
    }

    @JsonProperty(value="OrigImage")
    public OrigImage getOrigImage() {
        return this.origImage;
    }

    @JsonProperty(value="OrigImage")
    public void setOrigImage(OrigImage origImage) {
        this.origImage = origImage;
    }

    @JsonProperty(value="Tags")
    public List<Tag> getTags() {
        return this.tags;
    }

    @JsonProperty(value="Tags")
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @JsonProperty(value="DeviceGroupID")
    public String getDeviceGroupID() {
        return this.deviceGroupID;
    }

    @JsonProperty(value="DeviceGroupID")
    public void setDeviceGroupID(String deviceGroupID) {
        this.deviceGroupID = deviceGroupID;
    }

    @JsonProperty(value="Relationships")
    public List<Relationship> getRelationships() {
        return this.relationships;
    }

    @JsonProperty(value="Relationships")
    public void setRelationships(List<Relationship> relationships) {
        this.relationships = relationships;
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
        sb.append(FaceKafka.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("type");
        sb.append('=');
        sb.append(this.type == null ? "<null>" : this.type);
        sb.append(',');
        sb.append("appKey");
        sb.append('=');
        sb.append(this.appKey == null ? "<null>" : this.appKey);
        sb.append(',');
        sb.append("personID");
        sb.append('=');
        sb.append(this.personID == null ? "<null>" : this.personID);
        sb.append(',');
        sb.append("originPersonID");
        sb.append('=');
        sb.append(this.originPersonID == null ? "<null>" : this.originPersonID);
        sb.append(',');
        sb.append("time");
        sb.append('=');
        sb.append(this.time == null ? "<null>" : this.time);
        sb.append(',');
        sb.append("pushTime");
        sb.append('=');
        sb.append(this.pushTime == null ? "<null>" : this.pushTime);
        sb.append(',');
        sb.append("face");
        sb.append('=');
        sb.append(this.face == null ? "<null>" : this.face);
        sb.append(',');
        sb.append("quality");
        sb.append('=');
        sb.append(this.quality == null ? "<null>" : this.quality);
        sb.append(',');
        sb.append("attribute");
        sb.append('=');
        sb.append(this.attribute == null ? "<null>" : this.attribute);
        sb.append(',');
        sb.append("position");
        sb.append('=');
        sb.append(this.position == null ? "<null>" : this.position);
        sb.append(',');
        sb.append("origImage");
        sb.append('=');
        sb.append(this.origImage == null ? "<null>" : this.origImage);
        sb.append(',');
        sb.append("tags");
        sb.append('=');
        sb.append(this.tags == null ? "<null>" : this.tags);
        sb.append(',');
        sb.append("deviceGroupID");
        sb.append('=');
        sb.append(this.deviceGroupID == null ? "<null>" : this.deviceGroupID);
        sb.append(',');
        sb.append("relationships");
        sb.append('=');
        sb.append(this.relationships == null ? "<null>" : this.relationships);
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
        result = result * 31 + (this.origImage == null ? 0 : this.origImage.hashCode());
        result = result * 31 + (this.type == null ? 0 : this.type.hashCode());
        result = result * 31 + (this.quality == null ? 0 : this.quality.hashCode());
        result = result * 31 + (this.tags == null ? 0 : this.tags.hashCode());
        result = result * 31 + (this.relationships == null ? 0 : this.relationships.hashCode());
        result = result * 31 + (this.face == null ? 0 : this.face.hashCode());
        result = result * 31 + (this.deviceGroupID == null ? 0 : this.deviceGroupID.hashCode());
        result = result * 31 + (this.originPersonID == null ? 0 : this.originPersonID.hashCode());
        result = result * 31 + (this.appKey == null ? 0 : this.appKey.hashCode());
        result = result * 31 + (this.personID == null ? 0 : this.personID.hashCode());
        result = result * 31 + (this.time == null ? 0 : this.time.hashCode());
        result = result * 31 + (this.attribute == null ? 0 : this.attribute.hashCode());
        result = result * 31 + (this.position == null ? 0 : this.position.hashCode());
        result = result * 31 + (this.additionalProperties == null ? 0 : this.additionalProperties.hashCode());
        result = result * 31 + (this.pushTime == null ? 0 : this.pushTime.hashCode());
        return result;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof FaceKafka)) {
            return false;
        }
        FaceKafka rhs = (FaceKafka)other;
        return (this.origImage == rhs.origImage || this.origImage != null && this.origImage.equals(rhs.origImage)) && (this.type == rhs.type || this.type != null && this.type.equals(rhs.type)) && (this.quality == rhs.quality || this.quality != null && this.quality.equals(rhs.quality)) && (this.tags == rhs.tags || this.tags != null && this.tags.equals(rhs.tags)) && (this.relationships == rhs.relationships || this.relationships != null && this.relationships.equals(rhs.relationships)) && (this.face == rhs.face || this.face != null && this.face.equals(rhs.face)) && (this.deviceGroupID == rhs.deviceGroupID || this.deviceGroupID != null && this.deviceGroupID.equals(rhs.deviceGroupID)) && (this.originPersonID == rhs.originPersonID || this.originPersonID != null && this.originPersonID.equals(rhs.originPersonID)) && (this.appKey == rhs.appKey || this.appKey != null && this.appKey.equals(rhs.appKey)) && (this.personID == rhs.personID || this.personID != null && this.personID.equals(rhs.personID)) && (this.time == rhs.time || this.time != null && this.time.equals(rhs.time)) && (this.attribute == rhs.attribute || this.attribute != null && this.attribute.equals(rhs.attribute)) && (this.position == rhs.position || this.position != null && this.position.equals(rhs.position)) && (this.additionalProperties == rhs.additionalProperties || this.additionalProperties != null && this.additionalProperties.equals(rhs.additionalProperties)) && (this.pushTime == rhs.pushTime || this.pushTime != null && this.pushTime.equals(rhs.pushTime));
    }
}
