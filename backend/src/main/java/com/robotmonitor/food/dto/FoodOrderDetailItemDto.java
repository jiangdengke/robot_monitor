/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.robotmonitor.common.core.serializer.DoubleJsonSerializer
 */
package com.robotmonitor.food.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.robotmonitor.common.core.serializer.DoubleJsonSerializer;

public class FoodOrderDetailItemDto {
    private String foodName;
    @JsonSerialize(using=DoubleJsonSerializer.class)
    private Double price;
    private Integer num;

    public String getFoodName() {
        return this.foodName;
    }

    public Double getPrice() {
        return this.price;
    }

    public Integer getNum() {
        return this.num;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FoodOrderDetailItemDto)) {
            return false;
        }
        FoodOrderDetailItemDto other = (FoodOrderDetailItemDto)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Double this$price = this.getPrice();
        Double other$price = other.getPrice();
        if (this$price == null ? other$price != null : !((Object)this$price).equals(other$price)) {
            return false;
        }
        Integer this$num = this.getNum();
        Integer other$num = other.getNum();
        if (this$num == null ? other$num != null : !((Object)this$num).equals(other$num)) {
            return false;
        }
        String this$foodName = this.getFoodName();
        String other$foodName = other.getFoodName();
        return !(this$foodName == null ? other$foodName != null : !this$foodName.equals(other$foodName));
    }

    protected boolean canEqual(Object other) {
        return other instanceof FoodOrderDetailItemDto;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Double $price = this.getPrice();
        result = result * 59 + ($price == null ? 43 : ((Object)$price).hashCode());
        Integer $num = this.getNum();
        result = result * 59 + ($num == null ? 43 : ((Object)$num).hashCode());
        String $foodName = this.getFoodName();
        result = result * 59 + ($foodName == null ? 43 : $foodName.hashCode());
        return result;
    }

    public String toString() {
        return "FoodOrderDetailItemDto(foodName=" + this.getFoodName() + ", price=" + this.getPrice() + ", num=" + this.getNum() + ")";
    }
}
