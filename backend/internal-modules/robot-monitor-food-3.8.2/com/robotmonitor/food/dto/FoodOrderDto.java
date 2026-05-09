/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.robotmonitor.common.annotation.Excel
 */
package com.robotmonitor.food.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.robotmonitor.common.annotation.Excel;
import com.robotmonitor.food.domain.FoodOrderDetail;
import java.util.Date;
import java.util.List;

public class FoodOrderDto {
    private Long id;
    private String orderCode;
    private Long tableId;
    private String status;
    private String remark;
    private String cardNo;
    private String passengerName;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String orderPrice;
    private String roomCode;
    private List<FoodOrderDetail> orderDetailList;
    @Excel(name="\u684c\u5b50\u7f16\u53f7")
    private String deskNo;

    public Long getId() {
        return this.id;
    }

    public String getOrderCode() {
        return this.orderCode;
    }

    public Long getTableId() {
        return this.tableId;
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

    public String getPassengerName() {
        return this.passengerName;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public String getOrderPrice() {
        return this.orderPrice;
    }

    public String getRoomCode() {
        return this.roomCode;
    }

    public List<FoodOrderDetail> getOrderDetailList() {
        return this.orderDetailList;
    }

    public String getDeskNo() {
        return this.deskNo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
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

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public void setOrderDetailList(List<FoodOrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public void setDeskNo(String deskNo) {
        this.deskNo = deskNo;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FoodOrderDto)) {
            return false;
        }
        FoodOrderDto other = (FoodOrderDto)o;
        if (!other.canEqual(this)) {
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
        String this$passengerName = this.getPassengerName();
        String other$passengerName = other.getPassengerName();
        if (this$passengerName == null ? other$passengerName != null : !this$passengerName.equals(other$passengerName)) {
            return false;
        }
        Date this$createTime = this.getCreateTime();
        Date other$createTime = other.getCreateTime();
        if (this$createTime == null ? other$createTime != null : !((Object)this$createTime).equals(other$createTime)) {
            return false;
        }
        String this$orderPrice = this.getOrderPrice();
        String other$orderPrice = other.getOrderPrice();
        if (this$orderPrice == null ? other$orderPrice != null : !this$orderPrice.equals(other$orderPrice)) {
            return false;
        }
        String this$roomCode = this.getRoomCode();
        String other$roomCode = other.getRoomCode();
        if (this$roomCode == null ? other$roomCode != null : !this$roomCode.equals(other$roomCode)) {
            return false;
        }
        List<FoodOrderDetail> this$orderDetailList = this.getOrderDetailList();
        List<FoodOrderDetail> other$orderDetailList = other.getOrderDetailList();
        if (this$orderDetailList == null ? other$orderDetailList != null : !((Object)this$orderDetailList).equals(other$orderDetailList)) {
            return false;
        }
        String this$deskNo = this.getDeskNo();
        String other$deskNo = other.getDeskNo();
        return !(this$deskNo == null ? other$deskNo != null : !this$deskNo.equals(other$deskNo));
    }

    protected boolean canEqual(Object other) {
        return other instanceof FoodOrderDto;
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
        String $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        String $remark = this.getRemark();
        result = result * 59 + ($remark == null ? 43 : $remark.hashCode());
        String $cardNo = this.getCardNo();
        result = result * 59 + ($cardNo == null ? 43 : $cardNo.hashCode());
        String $passengerName = this.getPassengerName();
        result = result * 59 + ($passengerName == null ? 43 : $passengerName.hashCode());
        Date $createTime = this.getCreateTime();
        result = result * 59 + ($createTime == null ? 43 : ((Object)$createTime).hashCode());
        String $orderPrice = this.getOrderPrice();
        result = result * 59 + ($orderPrice == null ? 43 : $orderPrice.hashCode());
        String $roomCode = this.getRoomCode();
        result = result * 59 + ($roomCode == null ? 43 : $roomCode.hashCode());
        List<FoodOrderDetail> $orderDetailList = this.getOrderDetailList();
        result = result * 59 + ($orderDetailList == null ? 43 : ((Object)$orderDetailList).hashCode());
        String $deskNo = this.getDeskNo();
        result = result * 59 + ($deskNo == null ? 43 : $deskNo.hashCode());
        return result;
    }

    public String toString() {
        return "FoodOrderDto(id=" + this.getId() + ", orderCode=" + this.getOrderCode() + ", tableId=" + this.getTableId() + ", status=" + this.getStatus() + ", remark=" + this.getRemark() + ", cardNo=" + this.getCardNo() + ", passengerName=" + this.getPassengerName() + ", createTime=" + this.getCreateTime() + ", orderPrice=" + this.getOrderPrice() + ", roomCode=" + this.getRoomCode() + ", orderDetailList=" + this.getOrderDetailList() + ", deskNo=" + this.getDeskNo() + ")";
    }
}
