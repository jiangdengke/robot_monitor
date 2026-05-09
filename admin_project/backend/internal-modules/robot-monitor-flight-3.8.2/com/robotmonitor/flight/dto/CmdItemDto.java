/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 */
package com.robotmonitor.flight.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CmdItemDto {
    private String btnName;
    private String dataType;
    private String taskType;
    @JsonIgnore
    private String name;
    @JsonIgnore
    private String type;
    @JsonIgnore
    private int dataSort;

    public String getBtnName() {
        return this.btnName;
    }

    public String getDataType() {
        return this.dataType;
    }

    public String getTaskType() {
        return this.taskType;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public int getDataSort() {
        return this.dataSort;
    }

    public void setBtnName(String btnName) {
        this.btnName = btnName;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    @JsonIgnore
    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public void setType(String type) {
        this.type = type;
    }

    @JsonIgnore
    public void setDataSort(int dataSort) {
        this.dataSort = dataSort;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CmdItemDto)) {
            return false;
        }
        CmdItemDto other = (CmdItemDto)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getDataSort() != other.getDataSort()) {
            return false;
        }
        String this$btnName = this.getBtnName();
        String other$btnName = other.getBtnName();
        if (this$btnName == null ? other$btnName != null : !this$btnName.equals(other$btnName)) {
            return false;
        }
        String this$dataType = this.getDataType();
        String other$dataType = other.getDataType();
        if (this$dataType == null ? other$dataType != null : !this$dataType.equals(other$dataType)) {
            return false;
        }
        String this$taskType = this.getTaskType();
        String other$taskType = other.getTaskType();
        if (this$taskType == null ? other$taskType != null : !this$taskType.equals(other$taskType)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        String this$type = this.getType();
        String other$type = other.getType();
        return !(this$type == null ? other$type != null : !this$type.equals(other$type));
    }

    protected boolean canEqual(Object other) {
        return other instanceof CmdItemDto;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getDataSort();
        String $btnName = this.getBtnName();
        result = result * 59 + ($btnName == null ? 43 : $btnName.hashCode());
        String $dataType = this.getDataType();
        result = result * 59 + ($dataType == null ? 43 : $dataType.hashCode());
        String $taskType = this.getTaskType();
        result = result * 59 + ($taskType == null ? 43 : $taskType.hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $type = this.getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        return result;
    }

    public String toString() {
        return "CmdItemDto(btnName=" + this.getBtnName() + ", dataType=" + this.getDataType() + ", taskType=" + this.getTaskType() + ", name=" + this.getName() + ", type=" + this.getType() + ", dataSort=" + this.getDataSort() + ")";
    }
}
