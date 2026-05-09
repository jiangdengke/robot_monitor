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

public class FoodOrderDetail
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    @Excel(name="\u98df\u54c1\u8ba2\u5355id")
    private Long orderId;
    @Excel(name="\u98df\u54c1\u540d\u79f0")
    private String foodName;
    @Excel(name="\u98df\u54c1id")
    private Long foodId;
    @Excel(name="\u98df\u54c1\u6570\u91cf")
    private Long num;
    @Excel(name="\u98df\u54c1\u5355\u4ef7")
    private Double price;

    public Long getId() {
        return this.id;
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public String getFoodName() {
        return this.foodName;
    }

    public Long getFoodId() {
        return this.foodId;
    }

    public Long getNum() {
        return this.num;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FoodOrderDetail)) {
            return false;
        }
        FoodOrderDetail other = (FoodOrderDetail)((Object)o);
        if (!other.canEqual((Object)this)) {
            return false;
        }
        Long this$id = this.getId();
        Long other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        Long this$orderId = this.getOrderId();
        Long other$orderId = other.getOrderId();
        if (this$orderId == null ? other$orderId != null : !((Object)this$orderId).equals(other$orderId)) {
            return false;
        }
        Long this$foodId = this.getFoodId();
        Long other$foodId = other.getFoodId();
        if (this$foodId == null ? other$foodId != null : !((Object)this$foodId).equals(other$foodId)) {
            return false;
        }
        Long this$num = this.getNum();
        Long other$num = other.getNum();
        if (this$num == null ? other$num != null : !((Object)this$num).equals(other$num)) {
            return false;
        }
        Double this$price = this.getPrice();
        Double other$price = other.getPrice();
        if (this$price == null ? other$price != null : !((Object)this$price).equals(other$price)) {
            return false;
        }
        String this$foodName = this.getFoodName();
        String other$foodName = other.getFoodName();
        return !(this$foodName == null ? other$foodName != null : !this$foodName.equals(other$foodName));
    }

    protected boolean canEqual(Object other) {
        return other instanceof FoodOrderDetail;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        Long $orderId = this.getOrderId();
        result = result * 59 + ($orderId == null ? 43 : ((Object)$orderId).hashCode());
        Long $foodId = this.getFoodId();
        result = result * 59 + ($foodId == null ? 43 : ((Object)$foodId).hashCode());
        Long $num = this.getNum();
        result = result * 59 + ($num == null ? 43 : ((Object)$num).hashCode());
        Double $price = this.getPrice();
        result = result * 59 + ($price == null ? 43 : ((Object)$price).hashCode());
        String $foodName = this.getFoodName();
        result = result * 59 + ($foodName == null ? 43 : $foodName.hashCode());
        return result;
    }

    public String toString() {
        return "FoodOrderDetail(id=" + this.getId() + ", orderId=" + this.getOrderId() + ", foodName=" + this.getFoodName() + ", foodId=" + this.getFoodId() + ", num=" + this.getNum() + ", price=" + this.getPrice() + ")";
    }
}
