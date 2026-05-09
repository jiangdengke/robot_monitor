/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain.digitalTwin;

import com.robotmonitor.flight.domain.digitalTwin.InspectionDto;
import com.robotmonitor.flight.domain.digitalTwin.PassengerDto;
import com.robotmonitor.flight.domain.digitalTwin.RobotDto;
import com.robotmonitor.flight.domain.digitalTwin.TableDto;
import java.util.List;

public class DigitalTwinDto {
    List<PassengerDto> passengerList;
    List<InspectionDto> inspectionList;
    List<TableDto> tableList;
    List<RobotDto> robotList;

    public List<PassengerDto> getPassengerList() {
        return this.passengerList;
    }

    public List<InspectionDto> getInspectionList() {
        return this.inspectionList;
    }

    public List<TableDto> getTableList() {
        return this.tableList;
    }

    public List<RobotDto> getRobotList() {
        return this.robotList;
    }

    public void setPassengerList(List<PassengerDto> passengerList) {
        this.passengerList = passengerList;
    }

    public void setInspectionList(List<InspectionDto> inspectionList) {
        this.inspectionList = inspectionList;
    }

    public void setTableList(List<TableDto> tableList) {
        this.tableList = tableList;
    }

    public void setRobotList(List<RobotDto> robotList) {
        this.robotList = robotList;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DigitalTwinDto)) {
            return false;
        }
        DigitalTwinDto other = (DigitalTwinDto)o;
        if (!other.canEqual(this)) {
            return false;
        }
        List<PassengerDto> this$passengerList = this.getPassengerList();
        List<PassengerDto> other$passengerList = other.getPassengerList();
        if (this$passengerList == null ? other$passengerList != null : !((Object)this$passengerList).equals(other$passengerList)) {
            return false;
        }
        List<InspectionDto> this$inspectionList = this.getInspectionList();
        List<InspectionDto> other$inspectionList = other.getInspectionList();
        if (this$inspectionList == null ? other$inspectionList != null : !((Object)this$inspectionList).equals(other$inspectionList)) {
            return false;
        }
        List<TableDto> this$tableList = this.getTableList();
        List<TableDto> other$tableList = other.getTableList();
        if (this$tableList == null ? other$tableList != null : !((Object)this$tableList).equals(other$tableList)) {
            return false;
        }
        List<RobotDto> this$robotList = this.getRobotList();
        List<RobotDto> other$robotList = other.getRobotList();
        return !(this$robotList == null ? other$robotList != null : !((Object)this$robotList).equals(other$robotList));
    }

    protected boolean canEqual(Object other) {
        return other instanceof DigitalTwinDto;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        List<PassengerDto> $passengerList = this.getPassengerList();
        result = result * 59 + ($passengerList == null ? 43 : ((Object)$passengerList).hashCode());
        List<InspectionDto> $inspectionList = this.getInspectionList();
        result = result * 59 + ($inspectionList == null ? 43 : ((Object)$inspectionList).hashCode());
        List<TableDto> $tableList = this.getTableList();
        result = result * 59 + ($tableList == null ? 43 : ((Object)$tableList).hashCode());
        List<RobotDto> $robotList = this.getRobotList();
        result = result * 59 + ($robotList == null ? 43 : ((Object)$robotList).hashCode());
        return result;
    }

    public String toString() {
        return "DigitalTwinDto(passengerList=" + this.getPassengerList() + ", inspectionList=" + this.getInspectionList() + ", tableList=" + this.getTableList() + ", robotList=" + this.getRobotList() + ")";
    }
}
