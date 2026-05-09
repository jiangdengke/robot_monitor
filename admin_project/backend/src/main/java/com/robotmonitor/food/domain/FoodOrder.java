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

public class FoodOrder
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    @Excel(name="\u8ba2\u5355\u7f16\u53f7")
    private String orderCode;
    @Excel(name="\u684c\u5b50\u7f16\u53f7")
    private String deskNo;
    @Excel(name="\u8ba2\u5355\u72b6\u6001 0 \u5220\u9664\uff0c1 \u4e0b\u5355 \uff0c2 \u6536\u5230\uff0c 3\u5b8c\u6210")
    private String status;
    private String remark;
    @Excel(name="\u4f1a\u5458\u5361\u53f7")
    private String cardNo;
    @Excel(name="\u8d35\u5bbe\u5ba4CODE")
    private String roomCode;
    private String deptName;
    private Long tableId;

    public Long getId() {
        return this.id;
    }

    public String getOrderCode() {
        return this.orderCode;
    }

    public String getDeskNo() {
        return this.deskNo;
    }

    public String getStatus() {
        return this.status;
    }

    public String getRemark() {
        return this.remark;
    }

    public String getCardNo() {
        return this.cardNo;
    }

    public String getRoomCode() {
        return this.roomCode;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public Long getTableId() {
        return this.tableId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public void setDeskNo(String deskNo) {
        this.deskNo = deskNo;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FoodOrder)) {
            return false;
        }
        FoodOrder other = (FoodOrder)((Object)o);
        if (!other.canEqual((Object)this)) {
            return false;
        }
        Long this$id = this.getId();
        Long other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        Long this$tableId = this.getTableId();
        Long other$tableId = other.getTableId();
        if (this$tableId == null ? other$tableId != null : !((Object)this$tableId).equals(other$tableId)) {
            return false;
        }
        String this$orderCode = this.getOrderCode();
        String other$orderCode = other.getOrderCode();
        if (this$orderCode == null ? other$orderCode != null : !this$orderCode.equals(other$orderCode)) {
            return false;
        }
        String this$deskNo = this.getDeskNo();
        String other$deskNo = other.getDeskNo();
        if (this$deskNo == null ? other$deskNo != null : !this$deskNo.equals(other$deskNo)) {
            return false;
        }
        String this$status = this.getStatus();
        String other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) {
            return false;
        }
        String this$remark = this.getRemark();
        String other$remark = other.getRemark();
        if (this$remark == null ? other$remark != null : !this$remark.equals(other$remark)) {
            return false;
        }
        String this$cardNo = this.getCardNo();
        String other$cardNo = other.getCardNo();
        if (this$cardNo == null ? other$cardNo != null : !this$cardNo.equals(other$cardNo)) {
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
        return other instanceof FoodOrder;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        Long $tableId = this.getTableId();
        result = result * 59 + ($tableId == null ? 43 : ((Object)$tableId).hashCode());
        String $orderCode = this.getOrderCode();
        result = result * 59 + ($orderCode == null ? 43 : $orderCode.hashCode());
        String $deskNo = this.getDeskNo();
        result = result * 59 + ($deskNo == null ? 43 : $deskNo.hashCode());
        String $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        String $remark = this.getRemark();
        result = result * 59 + ($remark == null ? 43 : $remark.hashCode());
        String $cardNo = this.getCardNo();
        result = result * 59 + ($cardNo == null ? 43 : $cardNo.hashCode());
        String $roomCode = this.getRoomCode();
        result = result * 59 + ($roomCode == null ? 43 : $roomCode.hashCode());
        String $deptName = this.getDeptName();
        result = result * 59 + ($deptName == null ? 43 : $deptName.hashCode());
        return result;
    }

    public String toString() {
        return "FoodOrder(id=" + this.getId() + ", orderCode=" + this.getOrderCode() + ", deskNo=" + this.getDeskNo() + ", status=" + this.getStatus() + ", remark=" + this.getRemark() + ", cardNo=" + this.getCardNo() + ", roomCode=" + this.getRoomCode() + ", deptName=" + this.getDeptName() + ", tableId=" + this.getTableId() + ")";
    }
}
