/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.food.dto;

import com.robotmonitor.food.dto.OrderDetailParamter;
import java.util.List;

public class OrderParamter {
    private String deskNo;
    private List<OrderDetailParamter> list;

    public String getDeskNo() {
        return this.deskNo;
    }

    public List<OrderDetailParamter> getList() {
        return this.list;
    }

    public void setDeskNo(String deskNo) {
        this.deskNo = deskNo;
    }

    public void setList(List<OrderDetailParamter> list) {
        this.list = list;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OrderParamter)) {
            return false;
        }
        OrderParamter other = (OrderParamter)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$deskNo = this.getDeskNo();
        String other$deskNo = other.getDeskNo();
        if (this$deskNo == null ? other$deskNo != null : !this$deskNo.equals(other$deskNo)) {
            return false;
        }
        List<OrderDetailParamter> this$list = this.getList();
        List<OrderDetailParamter> other$list = other.getList();
        return !(this$list == null ? other$list != null : !((Object)this$list).equals(other$list));
    }

    protected boolean canEqual(Object other) {
        return other instanceof OrderParamter;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $deskNo = this.getDeskNo();
        result = result * 59 + ($deskNo == null ? 43 : $deskNo.hashCode());
        List<OrderDetailParamter> $list = this.getList();
        result = result * 59 + ($list == null ? 43 : ((Object)$list).hashCode());
        return result;
    }

    public String toString() {
        return "OrderParamter(deskNo=" + this.getDeskNo() + ", list=" + this.getList() + ")";
    }
}
