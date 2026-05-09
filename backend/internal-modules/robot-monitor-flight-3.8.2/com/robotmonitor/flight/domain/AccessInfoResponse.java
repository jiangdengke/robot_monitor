/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain;

public class AccessInfoResponse {
    private String flag;
    private String followerNum;
    private String serviceName;
    private String inType;

    public String getFlag() {
        return this.flag;
    }

    public String getFollowerNum() {
        return this.followerNum;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public String getInType() {
        return this.inType;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setFollowerNum(String followerNum) {
        this.followerNum = followerNum;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setInType(String inType) {
        this.inType = inType;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AccessInfoResponse)) {
            return false;
        }
        AccessInfoResponse other = (AccessInfoResponse)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$flag = this.getFlag();
        String other$flag = other.getFlag();
        if (this$flag == null ? other$flag != null : !this$flag.equals(other$flag)) {
            return false;
        }
        String this$followerNum = this.getFollowerNum();
        String other$followerNum = other.getFollowerNum();
        if (this$followerNum == null ? other$followerNum != null : !this$followerNum.equals(other$followerNum)) {
            return false;
        }
        String this$serviceName = this.getServiceName();
        String other$serviceName = other.getServiceName();
        if (this$serviceName == null ? other$serviceName != null : !this$serviceName.equals(other$serviceName)) {
            return false;
        }
        String this$inType = this.getInType();
        String other$inType = other.getInType();
        return !(this$inType == null ? other$inType != null : !this$inType.equals(other$inType));
    }

    protected boolean canEqual(Object other) {
        return other instanceof AccessInfoResponse;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $flag = this.getFlag();
        result = result * 59 + ($flag == null ? 43 : $flag.hashCode());
        String $followerNum = this.getFollowerNum();
        result = result * 59 + ($followerNum == null ? 43 : $followerNum.hashCode());
        String $serviceName = this.getServiceName();
        result = result * 59 + ($serviceName == null ? 43 : $serviceName.hashCode());
        String $inType = this.getInType();
        result = result * 59 + ($inType == null ? 43 : $inType.hashCode());
        return result;
    }

    public String toString() {
        return "AccessInfoResponse(flag=" + this.getFlag() + ", followerNum=" + this.getFollowerNum() + ", serviceName=" + this.getServiceName() + ", inType=" + this.getInType() + ")";
    }
}
