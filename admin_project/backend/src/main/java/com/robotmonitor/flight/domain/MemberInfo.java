/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain;

public class MemberInfo {
    private String memberId;
    private String memberCardService;

    public String getMemberId() {
        return this.memberId;
    }

    public String getMemberCardService() {
        return this.memberCardService;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public void setMemberCardService(String memberCardService) {
        this.memberCardService = memberCardService;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MemberInfo)) {
            return false;
        }
        MemberInfo other = (MemberInfo)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$memberId = this.getMemberId();
        String other$memberId = other.getMemberId();
        if (this$memberId == null ? other$memberId != null : !this$memberId.equals(other$memberId)) {
            return false;
        }
        String this$memberCardService = this.getMemberCardService();
        String other$memberCardService = other.getMemberCardService();
        return !(this$memberCardService == null ? other$memberCardService != null : !this$memberCardService.equals(other$memberCardService));
    }

    protected boolean canEqual(Object other) {
        return other instanceof MemberInfo;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $memberId = this.getMemberId();
        result = result * 59 + ($memberId == null ? 43 : $memberId.hashCode());
        String $memberCardService = this.getMemberCardService();
        result = result * 59 + ($memberCardService == null ? 43 : $memberCardService.hashCode());
        return result;
    }

    public String toString() {
        return "MemberInfo(memberId=" + this.getMemberId() + ", memberCardService=" + this.getMemberCardService() + ")";
    }
}
