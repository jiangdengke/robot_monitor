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
import java.util.List;

public class FoodConfig
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long foodId;
    @Excel(name="\u9910\u98df\u540d\u79f0")
    private String name;
    @Excel(name="\u9910\u98df\u56fe\u7247id")
    private String imgIds;
    @Excel(name="\u9910\u98df\u4ef7\u683c")
    private Double price = 0.0;
    @Excel(name="\u5361\u8def\u91cc")
    private String calorie;
    @Excel(name="\u9910\u98df\u7c7b\u578b")
    private String dicTypeCode;
    @Excel(name="\u8d35\u5bbe\u5ba4CODE")
    private String roomCode;
    private String deptName;
    private List<String> imgUrlList;

    public Long getFoodId() {
        return this.foodId;
    }

    public String getName() {
        return this.name;
    }

    public String getImgIds() {
        return this.imgIds;
    }

    public Double getPrice() {
        return this.price;
    }

    public String getCalorie() {
        return this.calorie;
    }

    public String getDicTypeCode() {
        return this.dicTypeCode;
    }

    public String getRoomCode() {
        return this.roomCode;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public List<String> getImgUrlList() {
        return this.imgUrlList;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImgIds(String imgIds) {
        this.imgIds = imgIds;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public void setDicTypeCode(String dicTypeCode) {
        this.dicTypeCode = dicTypeCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setImgUrlList(List<String> imgUrlList) {
        this.imgUrlList = imgUrlList;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FoodConfig)) {
            return false;
        }
        FoodConfig other = (FoodConfig)((Object)o);
        if (!other.canEqual((Object)this)) {
            return false;
        }
        Long this$foodId = this.getFoodId();
        Long other$foodId = other.getFoodId();
        if (this$foodId == null ? other$foodId != null : !((Object)this$foodId).equals(other$foodId)) {
            return false;
        }
        Double this$price = this.getPrice();
        Double other$price = other.getPrice();
        if (this$price == null ? other$price != null : !((Object)this$price).equals(other$price)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        String this$imgIds = this.getImgIds();
        String other$imgIds = other.getImgIds();
        if (this$imgIds == null ? other$imgIds != null : !this$imgIds.equals(other$imgIds)) {
            return false;
        }
        String this$calorie = this.getCalorie();
        String other$calorie = other.getCalorie();
        if (this$calorie == null ? other$calorie != null : !this$calorie.equals(other$calorie)) {
            return false;
        }
        String this$dicTypeCode = this.getDicTypeCode();
        String other$dicTypeCode = other.getDicTypeCode();
        if (this$dicTypeCode == null ? other$dicTypeCode != null : !this$dicTypeCode.equals(other$dicTypeCode)) {
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
        List<String> this$imgUrlList = this.getImgUrlList();
        List<String> other$imgUrlList = other.getImgUrlList();
        return !(this$imgUrlList == null ? other$imgUrlList != null : !((Object)this$imgUrlList).equals(other$imgUrlList));
    }

    protected boolean canEqual(Object other) {
        return other instanceof FoodConfig;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $foodId = this.getFoodId();
        result = result * 59 + ($foodId == null ? 43 : ((Object)$foodId).hashCode());
        Double $price = this.getPrice();
        result = result * 59 + ($price == null ? 43 : ((Object)$price).hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $imgIds = this.getImgIds();
        result = result * 59 + ($imgIds == null ? 43 : $imgIds.hashCode());
        String $calorie = this.getCalorie();
        result = result * 59 + ($calorie == null ? 43 : $calorie.hashCode());
        String $dicTypeCode = this.getDicTypeCode();
        result = result * 59 + ($dicTypeCode == null ? 43 : $dicTypeCode.hashCode());
        String $roomCode = this.getRoomCode();
        result = result * 59 + ($roomCode == null ? 43 : $roomCode.hashCode());
        String $deptName = this.getDeptName();
        result = result * 59 + ($deptName == null ? 43 : $deptName.hashCode());
        List<String> $imgUrlList = this.getImgUrlList();
        result = result * 59 + ($imgUrlList == null ? 43 : ((Object)$imgUrlList).hashCode());
        return result;
    }

    public String toString() {
        return "FoodConfig(foodId=" + this.getFoodId() + ", name=" + this.getName() + ", imgIds=" + this.getImgIds() + ", price=" + this.getPrice() + ", calorie=" + this.getCalorie() + ", dicTypeCode=" + this.getDicTypeCode() + ", roomCode=" + this.getRoomCode() + ", deptName=" + this.getDeptName() + ", imgUrlList=" + this.getImgUrlList() + ")";
    }
}
