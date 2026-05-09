/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Excel
 *  com.robotmonitor.common.core.domain.BaseEntity
 */
package com.robotmonitor.food.domain;

import com.robotmonitor.common.annotation.Excel;
import com.robotmonitor.common.core.domain.BaseEntity;

public class FoodPlan
extends BaseEntity {
    private Long id;
    @Excel(name="\u5f00\u59cb\u65e5\u671f")
    private String startDay;
    @Excel(name="\u7ed3\u675f\u65e5\u671f")
    private String endDay;
    @Excel(name="\u8d35\u5bbe\u5ba4code")
    private String roomCode;
    private String deptName;
    private String foodIds;
    private String foodNames;

    public Long getId() {
        return this.id;
    }

    public String getStartDay() {
        return this.startDay;
    }

    public String getEndDay() {
        return this.endDay;
    }

    public String getRoomCode() {
        return this.roomCode;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public String getFoodIds() {
        return this.foodIds;
    }

    public String getFoodNames() {
        return this.foodNames;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setFoodIds(String foodIds) {
        this.foodIds = foodIds;
    }

    public void setFoodNames(String foodNames) {
        this.foodNames = foodNames;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FoodPlan)) {
            return false;
        }
        FoodPlan other = (FoodPlan)((Object)o);
        if (!other.canEqual((Object)this)) {
            return false;
        }
        Long this$id = this.getId();
        Long other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        String this$startDay = this.getStartDay();
        String other$startDay = other.getStartDay();
        if (this$startDay == null ? other$startDay != null : !this$startDay.equals(other$startDay)) {
            return false;
        }
        String this$endDay = this.getEndDay();
        String other$endDay = other.getEndDay();
        if (this$endDay == null ? other$endDay != null : !this$endDay.equals(other$endDay)) {
            return false;
        }
        String this$roomCode = this.getRoomCode();
        String other$roomCode = other.getRoomCode();
        if (this$roomCode == null ? other$roomCode != null : !this$roomCode.equals(other$roomCode)) {
            return false;
        }
        String this$deptName = this.getDeptName();
        String other$deptName = other.getDeptName();
        if (this$deptName == null ? other$deptName != null : !this$deptName.equals(other$deptName)) {
            return false;
        }
        String this$foodIds = this.getFoodIds();
        String other$foodIds = other.getFoodIds();
        if (this$foodIds == null ? other$foodIds != null : !this$foodIds.equals(other$foodIds)) {
            return false;
        }
        String this$foodNames = this.getFoodNames();
        String other$foodNames = other.getFoodNames();
        return !(this$foodNames == null ? other$foodNames != null : !this$foodNames.equals(other$foodNames));
    }

    protected boolean canEqual(Object other) {
        return other instanceof FoodPlan;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        String $startDay = this.getStartDay();
        result = result * 59 + ($startDay == null ? 43 : $startDay.hashCode());
        String $endDay = this.getEndDay();
        result = result * 59 + ($endDay == null ? 43 : $endDay.hashCode());
        String $roomCode = this.getRoomCode();
        result = result * 59 + ($roomCode == null ? 43 : $roomCode.hashCode());
        String $deptName = this.getDeptName();
        result = result * 59 + ($deptName == null ? 43 : $deptName.hashCode());
        String $foodIds = this.getFoodIds();
        result = result * 59 + ($foodIds == null ? 43 : $foodIds.hashCode());
        String $foodNames = this.getFoodNames();
        result = result * 59 + ($foodNames == null ? 43 : $foodNames.hashCode());
        return result;
    }

    public String toString() {
        return "FoodPlan(id=" + this.getId() + ", startDay=" + this.getStartDay() + ", endDay=" + this.getEndDay() + ", roomCode=" + this.getRoomCode() + ", deptName=" + this.getDeptName() + ", foodIds=" + this.getFoodIds() + ", foodNames=" + this.getFoodNames() + ")";
    }
}
