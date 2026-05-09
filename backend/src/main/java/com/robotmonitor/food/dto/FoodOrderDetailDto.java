/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.robotmonitor.common.core.serializer.DoubleJsonSerializer
 */
package com.robotmonitor.food.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.robotmonitor.common.core.serializer.DoubleJsonSerializer;
import com.robotmonitor.food.dto.FoodOrderDetailItemDto;
import java.util.Date;
import java.util.List;

public class FoodOrderDetailDto {
    private Long orderId;
    private String foodName;
    private String foodId;
    @JsonSerialize(using=DoubleJsonSerializer.class)
    private Double price;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private List<FoodOrderDetailItemDto> detailList;

    public Long getOrderId() {
        return this.orderId;
    }

    public String getFoodName() {
        return this.foodName;
    }

    public String getFoodId() {
        return this.foodId;
    }

    public Double getPrice() {
        return this.price;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public List<FoodOrderDetailItemDto> getDetailList() {
        return this.detailList;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setDetailList(List<FoodOrderDetailItemDto> detailList) {
        this.detailList = detailList;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FoodOrderDetailDto)) {
            return false;
        }
        FoodOrderDetailDto other = (FoodOrderDetailDto)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Long this$orderId = this.getOrderId();
        Long other$orderId = other.getOrderId();
        if (this$orderId == null ? other$orderId != null : !((Object)this$orderId).equals(other$orderId)) {
            return false;
        }
        Double this$price = this.getPrice();
        Double other$price = other.getPrice();
        if (this$price == null ? other$price != null : !((Object)this$price).equals(other$price)) {
            return false;
        }
        String this$foodName = this.getFoodName();
        String other$foodName = other.getFoodName();
        if (this$foodName == null ? other$foodName != null : !this$foodName.equals(other$foodName)) {
            return false;
        }
        String this$foodId = this.getFoodId();
        String other$foodId = other.getFoodId();
        if (this$foodId == null ? other$foodId != null : !this$foodId.equals(other$foodId)) {
            return false;
        }
        Date this$createTime = this.getCreateTime();
        Date other$createTime = other.getCreateTime();
        if (this$createTime == null ? other$createTime != null : !((Object)this$createTime).equals(other$createTime)) {
            return false;
        }
        List<FoodOrderDetailItemDto> this$detailList = this.getDetailList();
        List<FoodOrderDetailItemDto> other$detailList = other.getDetailList();
        return !(this$detailList == null ? other$detailList != null : !((Object)this$detailList).equals(other$detailList));
    }

    protected boolean canEqual(Object other) {
        return other instanceof FoodOrderDetailDto;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $orderId = this.getOrderId();
        result = result * 59 + ($orderId == null ? 43 : ((Object)$orderId).hashCode());
        Double $price = this.getPrice();
        result = result * 59 + ($price == null ? 43 : ((Object)$price).hashCode());
        String $foodName = this.getFoodName();
        result = result * 59 + ($foodName == null ? 43 : $foodName.hashCode());
        String $foodId = this.getFoodId();
        result = result * 59 + ($foodId == null ? 43 : $foodId.hashCode());
        Date $createTime = this.getCreateTime();
        result = result * 59 + ($createTime == null ? 43 : ((Object)$createTime).hashCode());
        List<FoodOrderDetailItemDto> $detailList = this.getDetailList();
        result = result * 59 + ($detailList == null ? 43 : ((Object)$detailList).hashCode());
        return result;
    }

    public String toString() {
        return "FoodOrderDetailDto(orderId=" + this.getOrderId() + ", foodName=" + this.getFoodName() + ", foodId=" + this.getFoodId() + ", price=" + this.getPrice() + ", createTime=" + this.getCreateTime() + ", detailList=" + this.getDetailList() + ")";
    }
}
