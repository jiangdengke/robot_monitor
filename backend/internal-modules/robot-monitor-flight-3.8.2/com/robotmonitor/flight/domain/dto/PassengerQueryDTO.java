/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain.dto;

import java.util.Date;

public class PassengerQueryDTO {
    private String userName;
    private String flightNo;
    private Date flightDate;
    private String inType;
    private Integer pageNum = 1;
    private Integer pageSize = 10;

    public String getUserName() {
        return this.userName;
    }

    public String getFlightNo() {
        return this.flightNo;
    }

    public Date getFlightDate() {
        return this.flightDate;
    }

    public String getInType() {
        return this.inType;
    }

    public Integer getPageNum() {
        return this.pageNum;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    public void setInType(String inType) {
        this.inType = inType;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PassengerQueryDTO)) {
            return false;
        }
        PassengerQueryDTO other = (PassengerQueryDTO)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Integer this$pageNum = this.getPageNum();
        Integer other$pageNum = other.getPageNum();
        if (this$pageNum == null ? other$pageNum != null : !((Object)this$pageNum).equals(other$pageNum)) {
            return false;
        }
        Integer this$pageSize = this.getPageSize();
        Integer other$pageSize = other.getPageSize();
        if (this$pageSize == null ? other$pageSize != null : !((Object)this$pageSize).equals(other$pageSize)) {
            return false;
        }
        String this$userName = this.getUserName();
        String other$userName = other.getUserName();
        if (this$userName == null ? other$userName != null : !this$userName.equals(other$userName)) {
            return false;
        }
        String this$flightNo = this.getFlightNo();
        String other$flightNo = other.getFlightNo();
        if (this$flightNo == null ? other$flightNo != null : !this$flightNo.equals(other$flightNo)) {
            return false;
        }
        Date this$flightDate = this.getFlightDate();
        Date other$flightDate = other.getFlightDate();
        if (this$flightDate == null ? other$flightDate != null : !((Object)this$flightDate).equals(other$flightDate)) {
            return false;
        }
        String this$inType = this.getInType();
        String other$inType = other.getInType();
        return !(this$inType == null ? other$inType != null : !this$inType.equals(other$inType));
    }

    protected boolean canEqual(Object other) {
        return other instanceof PassengerQueryDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $pageNum = this.getPageNum();
        result = result * 59 + ($pageNum == null ? 43 : ((Object)$pageNum).hashCode());
        Integer $pageSize = this.getPageSize();
        result = result * 59 + ($pageSize == null ? 43 : ((Object)$pageSize).hashCode());
        String $userName = this.getUserName();
        result = result * 59 + ($userName == null ? 43 : $userName.hashCode());
        String $flightNo = this.getFlightNo();
        result = result * 59 + ($flightNo == null ? 43 : $flightNo.hashCode());
        Date $flightDate = this.getFlightDate();
        result = result * 59 + ($flightDate == null ? 43 : ((Object)$flightDate).hashCode());
        String $inType = this.getInType();
        result = result * 59 + ($inType == null ? 43 : $inType.hashCode());
        return result;
    }

    public String toString() {
        return "PassengerQueryDTO(userName=" + this.getUserName() + ", flightNo=" + this.getFlightNo() + ", flightDate=" + this.getFlightDate() + ", inType=" + this.getInType() + ", pageNum=" + this.getPageNum() + ", pageSize=" + this.getPageSize() + ")";
    }
}
