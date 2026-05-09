/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain.dto;

import com.robotmonitor.flight.domain.Passenger;
import java.util.List;

public class PassengerPageResultDTO {
    private Integer pageNum;
    private Integer pageSize;
    private Integer pages;
    private Long total;
    private List<Passenger> passengerList;

    public Integer getPageNum() {
        return this.pageNum;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public Integer getPages() {
        return this.pages;
    }

    public Long getTotal() {
        return this.total;
    }

    public List<Passenger> getPassengerList() {
        return this.passengerList;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public void setPassengerList(List<Passenger> passengerList) {
        this.passengerList = passengerList;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PassengerPageResultDTO)) {
            return false;
        }
        PassengerPageResultDTO other = (PassengerPageResultDTO)o;
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
        Integer this$pages = this.getPages();
        Integer other$pages = other.getPages();
        if (this$pages == null ? other$pages != null : !((Object)this$pages).equals(other$pages)) {
            return false;
        }
        Long this$total = this.getTotal();
        Long other$total = other.getTotal();
        if (this$total == null ? other$total != null : !((Object)this$total).equals(other$total)) {
            return false;
        }
        List<Passenger> this$passengerList = this.getPassengerList();
        List<Passenger> other$passengerList = other.getPassengerList();
        return !(this$passengerList == null ? other$passengerList != null : !((Object)this$passengerList).equals(other$passengerList));
    }

    protected boolean canEqual(Object other) {
        return other instanceof PassengerPageResultDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $pageNum = this.getPageNum();
        result = result * 59 + ($pageNum == null ? 43 : ((Object)$pageNum).hashCode());
        Integer $pageSize = this.getPageSize();
        result = result * 59 + ($pageSize == null ? 43 : ((Object)$pageSize).hashCode());
        Integer $pages = this.getPages();
        result = result * 59 + ($pages == null ? 43 : ((Object)$pages).hashCode());
        Long $total = this.getTotal();
        result = result * 59 + ($total == null ? 43 : ((Object)$total).hashCode());
        List<Passenger> $passengerList = this.getPassengerList();
        result = result * 59 + ($passengerList == null ? 43 : ((Object)$passengerList).hashCode());
        return result;
    }

    public String toString() {
        return "PassengerPageResultDTO(pageNum=" + this.getPageNum() + ", pageSize=" + this.getPageSize() + ", pages=" + this.getPages() + ", total=" + this.getTotal() + ", passengerList=" + this.getPassengerList() + ")";
    }
}
