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
import java.time.LocalDate;

public class FoodDaily
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    @Excel(name="${comment}", readConverterExp="$column.readConverterExp()")
    private LocalDate foodDate;
    @Excel(name="${comment}", readConverterExp="$column.readConverterExp()")
    private Long foodId;
    @Excel(name="\u72b6\u6001 1\uff1a\u5728\u552e 0\uff1a\u552e\u7f44")
    private String status;
    private String roomCode;
    private String deptName;

    public Long getId() {
        return this.id;
    }

    public LocalDate getFoodDate() {
        return this.foodDate;
    }

    public Long getFoodId() {
        return this.foodId;
    }

    public String getStatus() {
        return this.status;
    }

    public String getRoomCode() {
        return this.roomCode;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFoodDate(LocalDate foodDate) {
        this.foodDate = foodDate;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FoodDaily)) {
            return false;
        }
        FoodDaily other = (FoodDaily)((Object)o);
        if (!other.canEqual((Object)this)) {
            return false;
        }
        Long this$id = this.getId();
        Long other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        Long this$foodId = this.getFoodId();
        Long other$foodId = other.getFoodId();
        if (this$foodId == null ? other$foodId != null : !((Object)this$foodId).equals(other$foodId)) {
            return false;
        }
        LocalDate this$foodDate = this.getFoodDate();
        LocalDate other$foodDate = other.getFoodDate();
        if (this$foodDate == null ? other$foodDate != null : !((Object)this$foodDate).equals(other$foodDate)) {
            return false;
        }
        String this$status = this.getStatus();
        String other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) {
            return false;
        }
        String this$roomCode = this.getRoomCode();
        String other$roomCode = other.getRoomCode();
        if (this$roomCode == null ? other$roomCode != null : !this$roomCode.equals(other$roomCode)) {
            return false;
        }
        String this$deptName = this.getDeptName();
        String other$deptName = other.getDeptName();
        return !(this$deptName == null ? other$deptName != null : !this$deptName.equals(other$deptName));
    }

    protected boolean canEqual(Object other) {
        return other instanceof FoodDaily;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        Long $foodId = this.getFoodId();
        result = result * 59 + ($foodId == null ? 43 : ((Object)$foodId).hashCode());
        LocalDate $foodDate = this.getFoodDate();
        result = result * 59 + ($foodDate == null ? 43 : ((Object)$foodDate).hashCode());
        String $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        String $roomCode = this.getRoomCode();
        result = result * 59 + ($roomCode == null ? 43 : $roomCode.hashCode());
        String $deptName = this.getDeptName();
        result = result * 59 + ($deptName == null ? 43 : $deptName.hashCode());
        return result;
    }

    public String toString() {
        return "FoodDaily(id=" + this.getId() + ", foodDate=" + this.getFoodDate() + ", foodId=" + this.getFoodId() + ", status=" + this.getStatus() + ", roomCode=" + this.getRoomCode() + ", deptName=" + this.getDeptName() + ")";
    }
}
