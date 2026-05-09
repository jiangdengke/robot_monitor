/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.core.domain.robot;

@Deprecated
public class RobotInfo {
    private Long robotId;
    private String robotName;
    private String location;
    private Integer network;
    private String belongedCompany;
    private String robotState;
    private Integer powerPercent;
    private Long taskId;
    private Integer taskType;
    private Integer taskStatus;
    private String dataType;

    public Long getRobotId() {
        return this.robotId;
    }

    public String getRobotName() {
        return this.robotName;
    }

    public String getLocation() {
        return this.location;
    }

    public Integer getNetwork() {
        return this.network;
    }

    public String getBelongedCompany() {
        return this.belongedCompany;
    }

    public String getRobotState() {
        return this.robotState;
    }

    public Integer getPowerPercent() {
        return this.powerPercent;
    }

    public Long getTaskId() {
        return this.taskId;
    }

    public Integer getTaskType() {
        return this.taskType;
    }

    public Integer getTaskStatus() {
        return this.taskStatus;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setRobotId(Long robotId) {
        this.robotId = robotId;
    }

    public void setRobotName(String robotName) {
        this.robotName = robotName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setNetwork(Integer network) {
        this.network = network;
    }

    public void setBelongedCompany(String belongedCompany) {
        this.belongedCompany = belongedCompany;
    }

    public void setRobotState(String robotState) {
        this.robotState = robotState;
    }

    public void setPowerPercent(Integer powerPercent) {
        this.powerPercent = powerPercent;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RobotInfo)) {
            return false;
        }
        RobotInfo other = (RobotInfo)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Long this$robotId = this.getRobotId();
        Long other$robotId = other.getRobotId();
        if (this$robotId == null ? other$robotId != null : !((Object)this$robotId).equals(other$robotId)) {
            return false;
        }
        Integer this$network = this.getNetwork();
        Integer other$network = other.getNetwork();
        if (this$network == null ? other$network != null : !((Object)this$network).equals(other$network)) {
            return false;
        }
        Integer this$powerPercent = this.getPowerPercent();
        Integer other$powerPercent = other.getPowerPercent();
        if (this$powerPercent == null ? other$powerPercent != null : !((Object)this$powerPercent).equals(other$powerPercent)) {
            return false;
        }
        Long this$taskId = this.getTaskId();
        Long other$taskId = other.getTaskId();
        if (this$taskId == null ? other$taskId != null : !((Object)this$taskId).equals(other$taskId)) {
            return false;
        }
        Integer this$taskType = this.getTaskType();
        Integer other$taskType = other.getTaskType();
        if (this$taskType == null ? other$taskType != null : !((Object)this$taskType).equals(other$taskType)) {
            return false;
        }
        Integer this$taskStatus = this.getTaskStatus();
        Integer other$taskStatus = other.getTaskStatus();
        if (this$taskStatus == null ? other$taskStatus != null : !((Object)this$taskStatus).equals(other$taskStatus)) {
            return false;
        }
        String this$robotName = this.getRobotName();
        String other$robotName = other.getRobotName();
        if (this$robotName == null ? other$robotName != null : !this$robotName.equals(other$robotName)) {
            return false;
        }
        String this$location = this.getLocation();
        String other$location = other.getLocation();
        if (this$location == null ? other$location != null : !this$location.equals(other$location)) {
            return false;
        }
        String this$belongedCompany = this.getBelongedCompany();
        String other$belongedCompany = other.getBelongedCompany();
        if (this$belongedCompany == null ? other$belongedCompany != null : !this$belongedCompany.equals(other$belongedCompany)) {
            return false;
        }
        String this$robotState = this.getRobotState();
        String other$robotState = other.getRobotState();
        if (this$robotState == null ? other$robotState != null : !this$robotState.equals(other$robotState)) {
            return false;
        }
        String this$dataType = this.getDataType();
        String other$dataType = other.getDataType();
        return !(this$dataType == null ? other$dataType != null : !this$dataType.equals(other$dataType));
    }

    protected boolean canEqual(Object other) {
        return other instanceof RobotInfo;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $robotId = this.getRobotId();
        result = result * 59 + ($robotId == null ? 43 : ((Object)$robotId).hashCode());
        Integer $network = this.getNetwork();
        result = result * 59 + ($network == null ? 43 : ((Object)$network).hashCode());
        Integer $powerPercent = this.getPowerPercent();
        result = result * 59 + ($powerPercent == null ? 43 : ((Object)$powerPercent).hashCode());
        Long $taskId = this.getTaskId();
        result = result * 59 + ($taskId == null ? 43 : ((Object)$taskId).hashCode());
        Integer $taskType = this.getTaskType();
        result = result * 59 + ($taskType == null ? 43 : ((Object)$taskType).hashCode());
        Integer $taskStatus = this.getTaskStatus();
        result = result * 59 + ($taskStatus == null ? 43 : ((Object)$taskStatus).hashCode());
        String $robotName = this.getRobotName();
        result = result * 59 + ($robotName == null ? 43 : $robotName.hashCode());
        String $location = this.getLocation();
        result = result * 59 + ($location == null ? 43 : $location.hashCode());
        String $belongedCompany = this.getBelongedCompany();
        result = result * 59 + ($belongedCompany == null ? 43 : $belongedCompany.hashCode());
        String $robotState = this.getRobotState();
        result = result * 59 + ($robotState == null ? 43 : $robotState.hashCode());
        String $dataType = this.getDataType();
        result = result * 59 + ($dataType == null ? 43 : $dataType.hashCode());
        return result;
    }

    public String toString() {
        return "RobotInfo(robotId=" + this.getRobotId() + ", robotName=" + this.getRobotName() + ", location=" + this.getLocation() + ", network=" + this.getNetwork() + ", belongedCompany=" + this.getBelongedCompany() + ", robotState=" + this.getRobotState() + ", powerPercent=" + this.getPowerPercent() + ", taskId=" + this.getTaskId() + ", taskType=" + this.getTaskType() + ", taskStatus=" + this.getTaskStatus() + ", dataType=" + this.getDataType() + ")";
    }
}
