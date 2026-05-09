/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.core.domain.insp;

import com.robotmonitor.common.core.domain.insp.InspectionResult;
import java.util.List;

public class InspectionSummary {
    private Long robot_id;
    private Long task_id;
    private Long robot_task_id;
    private List<InspectionResult> results;

    public String toString() {
        return "InspectionSummary{robot_id=" + this.robot_id + ", task_id=" + this.task_id + ", robot_task_id=" + this.robot_task_id + ", results=" + this.results + "}";
    }

    public Long getRobot_id() {
        return this.robot_id;
    }

    public Long getTask_id() {
        return this.task_id;
    }

    public Long getRobot_task_id() {
        return this.robot_task_id;
    }

    public List<InspectionResult> getResults() {
        return this.results;
    }

    public void setRobot_id(Long robot_id) {
        this.robot_id = robot_id;
    }

    public void setTask_id(Long task_id) {
        this.task_id = task_id;
    }

    public void setRobot_task_id(Long robot_task_id) {
        this.robot_task_id = robot_task_id;
    }

    public void setResults(List<InspectionResult> results) {
        this.results = results;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof InspectionSummary)) {
            return false;
        }
        InspectionSummary other = (InspectionSummary)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Long this$robot_id = this.getRobot_id();
        Long other$robot_id = other.getRobot_id();
        if (this$robot_id == null ? other$robot_id != null : !((Object)this$robot_id).equals(other$robot_id)) {
            return false;
        }
        Long this$task_id = this.getTask_id();
        Long other$task_id = other.getTask_id();
        if (this$task_id == null ? other$task_id != null : !((Object)this$task_id).equals(other$task_id)) {
            return false;
        }
        Long this$robot_task_id = this.getRobot_task_id();
        Long other$robot_task_id = other.getRobot_task_id();
        if (this$robot_task_id == null ? other$robot_task_id != null : !((Object)this$robot_task_id).equals(other$robot_task_id)) {
            return false;
        }
        List<InspectionResult> this$results = this.getResults();
        List<InspectionResult> other$results = other.getResults();
        return !(this$results == null ? other$results != null : !((Object)this$results).equals(other$results));
    }

    protected boolean canEqual(Object other) {
        return other instanceof InspectionSummary;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $robot_id = this.getRobot_id();
        result = result * 59 + ($robot_id == null ? 43 : ((Object)$robot_id).hashCode());
        Long $task_id = this.getTask_id();
        result = result * 59 + ($task_id == null ? 43 : ((Object)$task_id).hashCode());
        Long $robot_task_id = this.getRobot_task_id();
        result = result * 59 + ($robot_task_id == null ? 43 : ((Object)$robot_task_id).hashCode());
        List<InspectionResult> $results = this.getResults();
        result = result * 59 + ($results == null ? 43 : ((Object)$results).hashCode());
        return result;
    }
}
