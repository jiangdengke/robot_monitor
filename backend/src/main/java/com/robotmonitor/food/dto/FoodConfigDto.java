/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 */
package com.robotmonitor.food.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.robotmonitor.food.dto.FoodConfigDetailDto;
import java.util.List;

public class FoodConfigDto {
    private String typeName;
    @JsonIgnore
    private String value;
    private List<FoodConfigDetailDto> list;

    public String getTypeName() {
        return this.typeName;
    }

    public String getValue() {
        return this.value;
    }

    public List<FoodConfigDetailDto> getList() {
        return this.list;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @JsonIgnore
    public void setValue(String value) {
        this.value = value;
    }

    public void setList(List<FoodConfigDetailDto> list) {
        this.list = list;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FoodConfigDto)) {
            return false;
        }
        FoodConfigDto other = (FoodConfigDto)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$typeName = this.getTypeName();
        String other$typeName = other.getTypeName();
        if (this$typeName == null ? other$typeName != null : !this$typeName.equals(other$typeName)) {
            return false;
        }
        String this$value = this.getValue();
        String other$value = other.getValue();
        if (this$value == null ? other$value != null : !this$value.equals(other$value)) {
            return false;
        }
        List<FoodConfigDetailDto> this$list = this.getList();
        List<FoodConfigDetailDto> other$list = other.getList();
        return !(this$list == null ? other$list != null : !((Object)this$list).equals(other$list));
    }

    protected boolean canEqual(Object other) {
        return other instanceof FoodConfigDto;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $typeName = this.getTypeName();
        result = result * 59 + ($typeName == null ? 43 : $typeName.hashCode());
        String $value = this.getValue();
        result = result * 59 + ($value == null ? 43 : $value.hashCode());
        List<FoodConfigDetailDto> $list = this.getList();
        result = result * 59 + ($list == null ? 43 : ((Object)$list).hashCode());
        return result;
    }

    public String toString() {
        return "FoodConfigDto(typeName=" + this.getTypeName() + ", value=" + this.getValue() + ", list=" + this.getList() + ")";
    }
}
