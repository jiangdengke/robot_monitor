/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.food.dto;

import com.robotmonitor.food.dto.FoodOrderDetailParamter;
import java.util.List;

public class FoodOrderParamter {
    private String deskNo;
    private String cardNo;
    private List<FoodOrderDetailParamter> list;

    public String getDeskNo() {
        return this.deskNo;
    }

    public String getCardNo() {
        return this.cardNo;
    }

    public List<FoodOrderDetailParamter> getList() {
        return this.list;
    }

    public void setDeskNo(String deskNo) {
        this.deskNo = deskNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public void setList(List<FoodOrderDetailParamter> list) {
        this.list = list;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FoodOrderParamter)) {
            return false;
        }
        FoodOrderParamter other = (FoodOrderParamter)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$deskNo = this.getDeskNo();
        String other$deskNo = other.getDeskNo();
        if (this$deskNo == null ? other$deskNo != null : !this$deskNo.equals(other$deskNo)) {
            return false;
        }
        String this$cardNo = this.getCardNo();
        String other$cardNo = other.getCardNo();
        if (this$cardNo == null ? other$cardNo != null : !this$cardNo.equals(other$cardNo)) {
            return false;
        }
        List<FoodOrderDetailParamter> this$list = this.getList();
        List<FoodOrderDetailParamter> other$list = other.getList();
        return !(this$list == null ? other$list != null : !((Object)this$list).equals(other$list));
    }

    protected boolean canEqual(Object other) {
        return other instanceof FoodOrderParamter;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $deskNo = this.getDeskNo();
        result = result * 59 + ($deskNo == null ? 43 : $deskNo.hashCode());
        String $cardNo = this.getCardNo();
        result = result * 59 + ($cardNo == null ? 43 : $cardNo.hashCode());
        List<FoodOrderDetailParamter> $list = this.getList();
        result = result * 59 + ($list == null ? 43 : ((Object)$list).hashCode());
        return result;
    }

    public String toString() {
        return "FoodOrderParamter(deskNo=" + this.getDeskNo() + ", cardNo=" + this.getCardNo() + ", list=" + this.getList() + ")";
    }
}
