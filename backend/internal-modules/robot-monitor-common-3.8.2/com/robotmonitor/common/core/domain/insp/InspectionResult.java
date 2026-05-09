/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.core.domain.insp;

import com.robotmonitor.common.utils.StringUtils;

public class InspectionResult {
    private String point;
    private boolean abnormal;
    private String abnormal_info;
    private String image_base64;

    public String toString() {
        return "InspectionResult{point='" + this.point + "', abnormal=" + this.abnormal + ", abnormal_info='" + this.abnormal_info + "', image_base64='" + StringUtils.abbreviate((String)this.image_base64, (int)50) + "'}";
    }

    public String getPoint() {
        return this.point;
    }

    public boolean isAbnormal() {
        return this.abnormal;
    }

    public String getAbnormal_info() {
        return this.abnormal_info;
    }

    public String getImage_base64() {
        return this.image_base64;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public void setAbnormal(boolean abnormal) {
        this.abnormal = abnormal;
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
        if (!(o instanceof InspectionResult)) {
            return false;
        }
        InspectionResult other = (InspectionResult)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.isAbnormal() != other.isAbnormal()) {
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
        return other instanceof InspectionResult;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isAbnormal() ? 79 : 97);
        String $point = this.getPoint();
        result = result * 59 + ($point == null ? 43 : $point.hashCode());
        String $abnormal_info = this.getAbnormal_info();
        result = result * 59 + ($abnormal_info == null ? 43 : $abnormal_info.hashCode());
        String $image_base64 = this.getImage_base64();
        result = result * 59 + ($image_base64 == null ? 43 : $image_base64.hashCode());
        return result;
    }
}
