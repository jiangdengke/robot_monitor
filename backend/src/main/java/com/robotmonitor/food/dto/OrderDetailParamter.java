/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.food.dto;

public class OrderDetailParamter {
    private Long foodId;
    private int num;

    public Long getFoodId() {
        return this.foodId;
    }

    public int getNum() {
        return this.num;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OrderDetailParamter)) {
            return false;
        }
        OrderDetailParamter other = (OrderDetailParamter)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getNum() != other.getNum()) {
            return false;
        }
        Long this$foodId = this.getFoodId();
        Long other$foodId = other.getFoodId();
        return !(this$foodId == null ? other$foodId != null : !((Object)this$foodId).equals(other$foodId));
    }

    protected boolean canEqual(Object other) {
        return other instanceof OrderDetailParamter;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getNum();
        Long $foodId = this.getFoodId();
        result = result * 59 + ($foodId == null ? 43 : ((Object)$foodId).hashCode());
        return result;
    }

    public String toString() {
        return "OrderDetailParamter(foodId=" + this.getFoodId() + ", num=" + this.getNum() + ")";
    }
}
