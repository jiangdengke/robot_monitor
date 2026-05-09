/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Excel
 */
package com.robotmonitor.food.dto;

import com.robotmonitor.common.annotation.Excel;
import java.time.LocalDate;
import java.util.List;

public class FoodDailyDto {
    private Long id;
    @Excel(name="${comment}", readConverterExp="$column.readConverterExp()")
    private LocalDate foodDate;
    @Excel(name="${comment}", readConverterExp="$column.readConverterExp()")
    private Long foodId;
    @Excel(name="\u72b6\u6001 1\uff1a\u5728\u552e 0\uff1a\u552e\u7f44")
    private String status;
    private String roomCode;
    private String foodName;
    private String foodType;
    private String deptName;
    private String calorie;
    private String remark;
    private String imgIds;
    private List<String> imgUrlList;

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

    public String getFoodName() {
        return this.foodName;
    }

    public String getFoodType() {
        return this.foodType;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public String getCalorie() {
        return this.calorie;
    }

    public String getRemark() {
        return this.remark;
    }

    public String getImgIds() {
        return this.imgIds;
    }

    public List<String> getImgUrlList() {
        return this.imgUrlList;
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

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setImgIds(String imgIds) {
        this.imgIds = imgIds;
    }

    public void setImgUrlList(List<String> imgUrlList) {
        this.imgUrlList = imgUrlList;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FoodDailyDto)) {
            return false;
        }
        FoodDailyDto other = (FoodDailyDto)o;
        if (!other.canEqual(this)) {
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
        String this$foodName = this.getFoodName();
        String other$foodName = other.getFoodName();
        if (this$foodName == null ? other$foodName != null : !this$foodName.equals(other$foodName)) {
            return false;
        }
        String this$foodType = this.getFoodType();
        String other$foodType = other.getFoodType();
        if (this$foodType == null ? other$foodType != null : !this$foodType.equals(other$foodType)) {
            return false;
        }
        String this$deptName = this.getDeptName();
        String other$deptName = other.getDeptName();
        if (this$deptName == null ? other$deptName != null : !this$deptName.equals(other$deptName)) {
            return false;
        }
        String this$calorie = this.getCalorie();
        String other$calorie = other.getCalorie();
        if (this$calorie == null ? other$calorie != null : !this$calorie.equals(other$calorie)) {
            return false;
        }
        String this$remark = this.getRemark();
        String other$remark = other.getRemark();
        if (this$remark == null ? other$remark != null : !this$remark.equals(other$remark)) {
            return false;
        }
        String this$imgIds = this.getImgIds();
        String other$imgIds = other.getImgIds();
        if (this$imgIds == null ? other$imgIds != null : !this$imgIds.equals(other$imgIds)) {
            return false;
        }
        List<String> this$imgUrlList = this.getImgUrlList();
        List<String> other$imgUrlList = other.getImgUrlList();
        return !(this$imgUrlList == null ? other$imgUrlList != null : !((Object)this$imgUrlList).equals(other$imgUrlList));
    }

    protected boolean canEqual(Object other) {
        return other instanceof FoodDailyDto;
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
        String $foodName = this.getFoodName();
        result = result * 59 + ($foodName == null ? 43 : $foodName.hashCode());
        String $foodType = this.getFoodType();
        result = result * 59 + ($foodType == null ? 43 : $foodType.hashCode());
        String $deptName = this.getDeptName();
        result = result * 59 + ($deptName == null ? 43 : $deptName.hashCode());
        String $calorie = this.getCalorie();
        result = result * 59 + ($calorie == null ? 43 : $calorie.hashCode());
        String $remark = this.getRemark();
        result = result * 59 + ($remark == null ? 43 : $remark.hashCode());
        String $imgIds = this.getImgIds();
        result = result * 59 + ($imgIds == null ? 43 : $imgIds.hashCode());
        List<String> $imgUrlList = this.getImgUrlList();
        result = result * 59 + ($imgUrlList == null ? 43 : ((Object)$imgUrlList).hashCode());
        return result;
    }

    public String toString() {
        return "FoodDailyDto(id=" + this.getId() + ", foodDate=" + this.getFoodDate() + ", foodId=" + this.getFoodId() + ", status=" + this.getStatus() + ", roomCode=" + this.getRoomCode() + ", foodName=" + this.getFoodName() + ", foodType=" + this.getFoodType() + ", deptName=" + this.getDeptName() + ", calorie=" + this.getCalorie() + ", remark=" + this.getRemark() + ", imgIds=" + this.getImgIds() + ", imgUrlList=" + this.getImgUrlList() + ")";
    }
}
