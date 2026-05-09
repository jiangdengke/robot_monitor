/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Excel
 *  com.robotmonitor.common.core.domain.BaseEntity
 */
package com.robotmonitor.flight.domain;

import com.robotmonitor.common.annotation.Excel;
import com.robotmonitor.common.core.domain.BaseEntity;

public class FlightWarning
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    @Excel(name="\u822a\u73ed\u552f\u4e00\u6807\u8bc6")
    private String flightId;
    @Excel(name="\u9884\u8b66\u7c7b\u522b \u89c1\u5b57\u5178\u8868flightwarningtype")
    private String warningType;
    @Excel(name="\u53d8\u66f4\u524d")
    private String changeBefore;
    @Excel(name="\u53d8\u66f4\u540e")
    private String changeAfter;
    private String status = "0";
    private String flightNo;
    private String userName;
    private Long passengerId;
    private Long regionId;

    public Long getId() {
        return this.id;
    }

    public String getFlightId() {
        return this.flightId;
    }

    public String getWarningType() {
        return this.warningType;
    }

    public String getChangeBefore() {
        return this.changeBefore;
    }

    public String getChangeAfter() {
        return this.changeAfter;
    }

    public String getStatus() {
        return this.status;
    }

    public String getFlightNo() {
        return this.flightNo;
    }

    public String getUserName() {
        return this.userName;
    }

    public Long getPassengerId() {
        return this.passengerId;
    }

    public Long getRegionId() {
        return this.regionId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public void setWarningType(String warningType) {
        this.warningType = warningType;
    }

    public void setChangeBefore(String changeBefore) {
        this.changeBefore = changeBefore;
    }

    public void setChangeAfter(String changeAfter) {
        this.changeAfter = changeAfter;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FlightWarning)) {
            return false;
        }
        FlightWarning other = (FlightWarning)((Object)o);
        if (!other.canEqual((Object)this)) {
            return false;
        }
        Long this$id = this.getId();
        Long other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        Long this$passengerId = this.getPassengerId();
        Long other$passengerId = other.getPassengerId();
        if (this$passengerId == null ? other$passengerId != null : !((Object)this$passengerId).equals(other$passengerId)) {
            return false;
        }
        Long this$regionId = this.getRegionId();
        Long other$regionId = other.getRegionId();
        if (this$regionId == null ? other$regionId != null : !((Object)this$regionId).equals(other$regionId)) {
            return false;
        }
        String this$flightId = this.getFlightId();
        String other$flightId = other.getFlightId();
        if (this$flightId == null ? other$flightId != null : !this$flightId.equals(other$flightId)) {
            return false;
        }
        String this$warningType = this.getWarningType();
        String other$warningType = other.getWarningType();
        if (this$warningType == null ? other$warningType != null : !this$warningType.equals(other$warningType)) {
            return false;
        }
        String this$changeBefore = this.getChangeBefore();
        String other$changeBefore = other.getChangeBefore();
        if (this$changeBefore == null ? other$changeBefore != null : !this$changeBefore.equals(other$changeBefore)) {
            return false;
        }
        String this$changeAfter = this.getChangeAfter();
        String other$changeAfter = other.getChangeAfter();
        if (this$changeAfter == null ? other$changeAfter != null : !this$changeAfter.equals(other$changeAfter)) {
            return false;
        }
        String this$status = this.getStatus();
        String other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) {
            return false;
        }
        String this$flightNo = this.getFlightNo();
        String other$flightNo = other.getFlightNo();
        if (this$flightNo == null ? other$flightNo != null : !this$flightNo.equals(other$flightNo)) {
            return false;
        }
        String this$userName = this.getUserName();
        String other$userName = other.getUserName();
        return !(this$userName == null ? other$userName != null : !this$userName.equals(other$userName));
    }

    protected boolean canEqual(Object other) {
        return other instanceof FlightWarning;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        Long $passengerId = this.getPassengerId();
        result = result * 59 + ($passengerId == null ? 43 : ((Object)$passengerId).hashCode());
        Long $regionId = this.getRegionId();
        result = result * 59 + ($regionId == null ? 43 : ((Object)$regionId).hashCode());
        String $flightId = this.getFlightId();
        result = result * 59 + ($flightId == null ? 43 : $flightId.hashCode());
        String $warningType = this.getWarningType();
        result = result * 59 + ($warningType == null ? 43 : $warningType.hashCode());
        String $changeBefore = this.getChangeBefore();
        result = result * 59 + ($changeBefore == null ? 43 : $changeBefore.hashCode());
        String $changeAfter = this.getChangeAfter();
        result = result * 59 + ($changeAfter == null ? 43 : $changeAfter.hashCode());
        String $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        String $flightNo = this.getFlightNo();
        result = result * 59 + ($flightNo == null ? 43 : $flightNo.hashCode());
        String $userName = this.getUserName();
        result = result * 59 + ($userName == null ? 43 : $userName.hashCode());
        return result;
    }

    public String toString() {
        return "FlightWarning(id=" + this.getId() + ", flightId=" + this.getFlightId() + ", warningType=" + this.getWarningType() + ", changeBefore=" + this.getChangeBefore() + ", changeAfter=" + this.getChangeAfter() + ", status=" + this.getStatus() + ", flightNo=" + this.getFlightNo() + ", userName=" + this.getUserName() + ", passengerId=" + this.getPassengerId() + ", regionId=" + this.getRegionId() + ")";
    }
}
