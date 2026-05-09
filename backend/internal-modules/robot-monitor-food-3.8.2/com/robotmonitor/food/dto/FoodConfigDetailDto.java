/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 */
package com.robotmonitor.food.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

public class FoodConfigDetailDto {
    private Long foodId;
    private String name;
    private String price;
    private String calorie;
    private String imgIds;
    private String remark;
    @JsonIgnore
    private String dicTypeCode;
    private List<String> imgUrlList;

    public Long getFoodId() {
        return this.foodId;
    }

    public String getName() {
        return this.name;
    }

    public String getPrice() {
        return this.price;
    }

    public String getCalorie() {
        return this.calorie;
    }

    public String getImgIds() {
        return this.imgIds;
    }

    public String getRemark() {
        return this.remark;
    }

    public String getDicTypeCode() {
        return this.dicTypeCode;
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

    public void setPrice(String price) {
        this.price = price;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public void setImgIds(String imgIds) {
        this.imgIds = imgIds;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @JsonIgnore
    public void setDicTypeCode(String dicTypeCode) {
        this.dicTypeCode = dicTypeCode;
    }

    public void setImgUrlList(List<String> imgUrlList) {
        this.imgUrlList = imgUrlList;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FoodConfigDetailDto)) {
            return false;
        }
        FoodConfigDetailDto other = (FoodConfigDetailDto)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Long this$foodId = this.getFoodId();
        Long other$foodId = other.getFoodId();
        if (this$foodId == null ? other$foodId != null : !((Object)this$foodId).equals(other$foodId)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        String this$price = this.getPrice();
        String other$price = other.getPrice();
        if (this$price == null ? other$price != null : !this$price.equals(other$price)) {
            return false;
        }
        String this$calorie = this.getCalorie();
        String other$calorie = other.getCalorie();
        if (this$calorie == null ? other$calorie != null : !this$calorie.equals(other$calorie)) {
            return false;
        }
        String this$imgIds = this.getImgIds();
        String other$imgIds = other.getImgIds();
        if (this$imgIds == null ? other$imgIds != null : !this$imgIds.equals(other$imgIds)) {
            return false;
        }
        String this$remark = this.getRemark();
        String other$remark = other.getRemark();
        if (this$remark == null ? other$remark != null : !this$remark.equals(other$remark)) {
            return false;
        }
        String this$dicTypeCode = this.getDicTypeCode();
        String other$dicTypeCode = other.getDicTypeCode();
        if (this$dicTypeCode == null ? other$dicTypeCode != null : !this$dicTypeCode.equals(other$dicTypeCode)) {
            return false;
        }
        List<String> this$imgUrlList = this.getImgUrlList();
        List<String> other$imgUrlList = other.getImgUrlList();
        return !(this$imgUrlList == null ? other$imgUrlList != null : !((Object)this$imgUrlList).equals(other$imgUrlList));
    }

    protected boolean canEqual(Object other) {
        return other instanceof FoodConfigDetailDto;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $foodId = this.getFoodId();
        result = result * 59 + ($foodId == null ? 43 : ((Object)$foodId).hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $price = this.getPrice();
        result = result * 59 + ($price == null ? 43 : $price.hashCode());
        String $calorie = this.getCalorie();
        result = result * 59 + ($calorie == null ? 43 : $calorie.hashCode());
        String $imgIds = this.getImgIds();
        result = result * 59 + ($imgIds == null ? 43 : $imgIds.hashCode());
        String $remark = this.getRemark();
        result = result * 59 + ($remark == null ? 43 : $remark.hashCode());
        String $dicTypeCode = this.getDicTypeCode();
        result = result * 59 + ($dicTypeCode == null ? 43 : $dicTypeCode.hashCode());
        List<String> $imgUrlList = this.getImgUrlList();
        result = result * 59 + ($imgUrlList == null ? 43 : ((Object)$imgUrlList).hashCode());
        return result;
    }

    public String toString() {
        return "FoodConfigDetailDto(foodId=" + this.getFoodId() + ", name=" + this.getName() + ", price=" + this.getPrice() + ", calorie=" + this.getCalorie() + ", imgIds=" + this.getImgIds() + ", remark=" + this.getRemark() + ", dicTypeCode=" + this.getDicTypeCode() + ", imgUrlList=" + this.getImgUrlList() + ")";
    }
}
