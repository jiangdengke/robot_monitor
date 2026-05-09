/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.core.domain.insp;

import com.robotmonitor.common.utils.StringUtils;

public class InspectionAlarm {
    private Long robot_id;
    private String point;
    private Long robot_task_id;
    private String abnormal_info;
    private String image_base64;

    public String toString() {
        return "InspectionAlarm{robot_id=" + this.robot_id + ", point='" + this.point + "', robot_task_id=" + this.robot_task_id + ", abnormal_info='" + this.abnormal_info + "', image_base64='" + StringUtils.abbreviate((String)this.image_base64, (int)50) + "'}";
    }

    public Long getRobot_id() {
        return this.robot_id;
    }

    public String getPoint() {
        return this.point;
    }

    public Long getRobot_task_id() {
        return this.robot_task_id;
    }

    public String getAbnormal_info() {
        return this.abnormal_info;
    }

    public String getImage_base64() {
        return this.image_base64;
    }

    public void setRobot_id(Long robot_id) {
        this.robot_id = robot_id;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public void setRobot_task_id(Long robot_task_id) {
        this.robot_task_id = robot_task_id;
    }

    public void setAbnormal_info(String abnormal_info) {
        this.abnormal_info = abnormal_info;
    }

    public void setImage_base64(String image_base64) {
        this.image_base64 = image_base64;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof InspectionAlarm)) {
            return false;
        }
        InspectionAlarm other = (InspectionAlarm)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Long this$robot_id = this.getRobot_id();
        Long other$robot_id = other.getRobot_id();
        if (this$robot_id == null ? other$robot_id != null : !((Object)this$robot_id).equals(other$robot_id)) {
            return false;
        }
        Long this$robot_task_id = this.getRobot_task_id();
        Long other$robot_task_id = other.getRobot_task_id();
        if (this$robot_task_id == null ? other$robot_task_id != null : !((Object)this$robot_task_id).equals(other$robot_task_id)) {
            return false;
        }
        String this$point = this.getPoint();
        String other$point = other.getPoint();
        if (this$point == null ? other$point != null : !this$point.equals(other$point)) {
            return false;
        }
        String this$abnormal_info = this.getAbnormal_info();
        String other$abnormal_info = other.getAbnormal_info();
        if (this$abnormal_info == null ? other$abnormal_info != null : !this$abnormal_info.equals(other$abnormal_info)) {
            return false;
        }
        String this$image_base64 = this.getImage_base64();
        String other$image_base64 = other.getImage_base64();
        return !(this$image_base64 == null ? other$image_base64 != null : !this$image_base64.equals(other$image_base64));
    }

    protected boolean canEqual(Object other) {
        return other instanceof InspectionAlarm;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $robot_id = this.getRobot_id();
        result = result * 59 + ($robot_id == null ? 43 : ((Object)$robot_id).hashCode());
        Long $robot_task_id = this.getRobot_task_id();
        result = result * 59 + ($robot_task_id == null ? 43 : ((Object)$robot_task_id).hashCode());
        String $point = this.getPoint();
        result = result * 59 + ($point == null ? 43 : $point.hashCode());
        String $abnormal_info = this.getAbnormal_info();
        result = result * 59 + ($abnormal_info == null ? 43 : $abnormal_info.hashCode());
        String $image_base64 = this.getImage_base64();
        result = result * 59 + ($image_base64 == null ? 43 : $image_base64.hashCode());
        return result;
    }
}
