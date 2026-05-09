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
 *  com.robotmonitor.common.utils.JsonUtils
 *  com.robotmonitor.common.utils.StringUtils
 */
package com.robotmonitor.flight.dto.kafka.deepglint.data.metadata;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.robotmonitor.common.utils.JsonUtils;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.flight.dto.kafka.deepglint.data.metadata.Relationship;
import com.robotmonitor.flight.dto.kafka.deepglint.data.metadata.Tag;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value={"AppKey", "FeatureID", "PersonID", "ReceiveTime", "Relationships", "RequestID", "Tags", "TaskType", "UniqueSensorId", "footprints", "inputType"})
public class AdditionalInfos {
    @JsonProperty(value="AppKey")
    private String appKey;
    @JsonProperty(value="FeatureID")
    private String featureID;
    @JsonProperty(value="PersonID")
    private String personID;
    @JsonProperty(value="ReceiveTime")
    private String receiveTime;
    @JsonProperty(value="Relationships")
    private String relationships;
    @JsonProperty(value="RequestID")
    private String requestID;
    @JsonProperty(value="Tags")
    private String tags;
    @JsonProperty(value="TaskType")
    private String taskType;
    @JsonProperty(value="UniqueSensorId")
    private String uniqueSensorId;
    @JsonProperty(value="footprints")
    private String footprints;
    @JsonProperty(value="inputType")
    private String inputType;
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

    public List<Relationship> getRelationshipsObj() {
        if (StringUtils.isNotBlank((CharSequence)this.relationships)) {
            return (List)JsonUtils.string2Obj((String)this.relationships, ArrayList.class, (Class[])new Class[]{Relationship.class});
        }
        return null;
    }

    public List<Tag> getTagsObj() {
        if (StringUtils.isNotBlank((CharSequence)this.tags)) {
            return (List)JsonUtils.string2Obj((String)this.tags, ArrayList.class, (Class[])new Class[]{Tag.class});
        }
        return null;
    }

    public String getAppKey() {
        return this.appKey;
    }

    public String getFeatureID() {
        return this.featureID;
    }

    public String getPersonID() {
        return this.personID;
    }

    public String getReceiveTime() {
        return this.receiveTime;
    }

    public String getRelationships() {
        return this.relationships;
    }

    public String getRequestID() {
        return this.requestID;
    }

    public String getTags() {
        return this.tags;
    }

    public String getTaskType() {
        return this.taskType;
    }

    public String getUniqueSensorId() {
        return this.uniqueSensorId;
    }

    public String getFootprints() {
        return this.footprints;
    }

    public String getInputType() {
        return this.inputType;
    }

    @JsonProperty(value="AppKey")
    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    @JsonProperty(value="FeatureID")
    public void setFeatureID(String featureID) {
        this.featureID = featureID;
    }

    @JsonProperty(value="PersonID")
    public void setPersonID(String personID) {
        this.personID = personID;
    }

    @JsonProperty(value="ReceiveTime")
    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    @JsonProperty(value="Relationships")
    public void setRelationships(String relationships) {
        this.relationships = relationships;
    }

    @JsonProperty(value="RequestID")
    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    @JsonProperty(value="Tags")
    public void setTags(String tags) {
        this.tags = tags;
    }

    @JsonProperty(value="TaskType")
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    @JsonProperty(value="UniqueSensorId")
    public void setUniqueSensorId(String uniqueSensorId) {
        this.uniqueSensorId = uniqueSensorId;
    }

    @JsonProperty(value="footprints")
    public void setFootprints(String footprints) {
        this.footprints = footprints;
    }

    @JsonProperty(value="inputType")
    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    @JsonIgnore
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AdditionalInfos)) {
            return false;
        }
        AdditionalInfos other = (AdditionalInfos)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$appKey = this.getAppKey();
        String other$appKey = other.getAppKey();
        if (this$appKey == null ? other$appKey != null : !this$appKey.equals(other$appKey)) {
            return false;
        }
        String this$featureID = this.getFeatureID();
        String other$featureID = other.getFeatureID();
        if (this$featureID == null ? other$featureID != null : !this$featureID.equals(other$featureID)) {
            return false;
        }
        String this$personID = this.getPersonID();
        String other$personID = other.getPersonID();
        if (this$personID == null ? other$personID != null : !this$personID.equals(other$personID)) {
            return false;
        }
        String this$receiveTime = this.getReceiveTime();
        String other$receiveTime = other.getReceiveTime();
        if (this$receiveTime == null ? other$receiveTime != null : !this$receiveTime.equals(other$receiveTime)) {
            return false;
        }
        String this$relationships = this.getRelationships();
        String other$relationships = other.getRelationships();
        if (this$relationships == null ? other$relationships != null : !this$relationships.equals(other$relationships)) {
            return false;
        }
        String this$requestID = this.getRequestID();
        String other$requestID = other.getRequestID();
        if (this$requestID == null ? other$requestID != null : !this$requestID.equals(other$requestID)) {
            return false;
        }
        String this$tags = this.getTags();
        String other$tags = other.getTags();
        if (this$tags == null ? other$tags != null : !this$tags.equals(other$tags)) {
            return false;
        }
        String this$taskType = this.getTaskType();
        String other$taskType = other.getTaskType();
        if (this$taskType == null ? other$taskType != null : !this$taskType.equals(other$taskType)) {
            return false;
        }
        String this$uniqueSensorId = this.getUniqueSensorId();
        String other$uniqueSensorId = other.getUniqueSensorId();
        if (this$uniqueSensorId == null ? other$uniqueSensorId != null : !this$uniqueSensorId.equals(other$uniqueSensorId)) {
            return false;
        }
        String this$footprints = this.getFootprints();
        String other$footprints = other.getFootprints();
        if (this$footprints == null ? other$footprints != null : !this$footprints.equals(other$footprints)) {
            return false;
        }
        String this$inputType = this.getInputType();
        String other$inputType = other.getInputType();
        if (this$inputType == null ? other$inputType != null : !this$inputType.equals(other$inputType)) {
            return false;
        }
        Map<String, Object> this$additionalProperties = this.getAdditionalProperties();
        Map<String, Object> other$additionalProperties = other.getAdditionalProperties();
        return !(this$additionalProperties == null ? other$additionalProperties != null : !((Object)this$additionalProperties).equals(other$additionalProperties));
    }

    protected boolean canEqual(Object other) {
        return other instanceof AdditionalInfos;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $appKey = this.getAppKey();
        result = result * 59 + ($appKey == null ? 43 : $appKey.hashCode());
        String $featureID = this.getFeatureID();
        result = result * 59 + ($featureID == null ? 43 : $featureID.hashCode());
        String $personID = this.getPersonID();
        result = result * 59 + ($personID == null ? 43 : $personID.hashCode());
        String $receiveTime = this.getReceiveTime();
        result = result * 59 + ($receiveTime == null ? 43 : $receiveTime.hashCode());
        String $relationships = this.getRelationships();
        result = result * 59 + ($relationships == null ? 43 : $relationships.hashCode());
        String $requestID = this.getRequestID();
        result = result * 59 + ($requestID == null ? 43 : $requestID.hashCode());
        String $tags = this.getTags();
        result = result * 59 + ($tags == null ? 43 : $tags.hashCode());
        String $taskType = this.getTaskType();
        result = result * 59 + ($taskType == null ? 43 : $taskType.hashCode());
        String $uniqueSensorId = this.getUniqueSensorId();
        result = result * 59 + ($uniqueSensorId == null ? 43 : $uniqueSensorId.hashCode());
        String $footprints = this.getFootprints();
        result = result * 59 + ($footprints == null ? 43 : $footprints.hashCode());
        String $inputType = this.getInputType();
        result = result * 59 + ($inputType == null ? 43 : $inputType.hashCode());
        Map<String, Object> $additionalProperties = this.getAdditionalProperties();
        result = result * 59 + ($additionalProperties == null ? 43 : ((Object)$additionalProperties).hashCode());
        return result;
    }

    public String toString() {
        return "AdditionalInfos(appKey=" + this.getAppKey() + ", featureID=" + this.getFeatureID() + ", personID=" + this.getPersonID() + ", receiveTime=" + this.getReceiveTime() + ", relationships=" + this.getRelationships() + ", requestID=" + this.getRequestID() + ", tags=" + this.getTags() + ", taskType=" + this.getTaskType() + ", uniqueSensorId=" + this.getUniqueSensorId() + ", footprints=" + this.getFootprints() + ", inputType=" + this.getInputType() + ", additionalProperties=" + this.getAdditionalProperties() + ")";
    }
}
