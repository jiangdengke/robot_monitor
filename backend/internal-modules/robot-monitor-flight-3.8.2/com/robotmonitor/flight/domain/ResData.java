/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain;

import com.robotmonitor.flight.domain.MemberInfo;
import com.robotmonitor.flight.domain.MemberName;

public class ResData {
    private String msg;
    private String err_msg;
    private int followerNum;
    private String collectId;
    private String credentialsType;
    private Boolean isMember;
    private String err_code;
    private String view_msg;
    private MemberName memberName;
    private MemberInfo memberInfo;

    public String getMsg() {
        return this.msg;
    }

    public String getErr_msg() {
        return this.err_msg;
    }

    public int getFollowerNum() {
        return this.followerNum;
    }

    public String getCollectId() {
        return this.collectId;
    }

    public String getCredentialsType() {
        return this.credentialsType;
    }

    public Boolean getIsMember() {
        return this.isMember;
    }

    public String getErr_code() {
        return this.err_code;
    }

    public String getView_msg() {
        return this.view_msg;
    }

    public MemberName getMemberName() {
        return this.memberName;
    }

    public MemberInfo getMemberInfo() {
        return this.memberInfo;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }

    public void setFollowerNum(int followerNum) {
        this.followerNum = followerNum;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public void setCredentialsType(String credentialsType) {
        this.credentialsType = credentialsType;
    }

    public void setIsMember(Boolean isMember) {
        this.isMember = isMember;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public void setView_msg(String view_msg) {
        this.view_msg = view_msg;
    }

    public void setMemberName(MemberName memberName) {
        this.memberName = memberName;
    }

    public void setMemberInfo(MemberInfo memberInfo) {
        this.memberInfo = memberInfo;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ResData)) {
            return false;
        }
        ResData other = (ResData)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getFollowerNum() != other.getFollowerNum()) {
            return false;
        }
        Boolean this$isMember = this.getIsMember();
        Boolean other$isMember = other.getIsMember();
        if (this$isMember == null ? other$isMember != null : !((Object)this$isMember).equals(other$isMember)) {
            return false;
        }
        String this$msg = this.getMsg();
        String other$msg = other.getMsg();
        if (this$msg == null ? other$msg != null : !this$msg.equals(other$msg)) {
            return false;
        }
        String this$err_msg = this.getErr_msg();
        String other$err_msg = other.getErr_msg();
        if (this$err_msg == null ? other$err_msg != null : !this$err_msg.equals(other$err_msg)) {
            return false;
        }
        String this$collectId = this.getCollectId();
        String other$collectId = other.getCollectId();
        if (this$collectId == null ? other$collectId != null : !this$collectId.equals(other$collectId)) {
            return false;
        }
        String this$credentialsType = this.getCredentialsType();
        String other$credentialsType = other.getCredentialsType();
        if (this$credentialsType == null ? other$credentialsType != null : !this$credentialsType.equals(other$credentialsType)) {
            return false;
        }
        String this$err_code = this.getErr_code();
        String other$err_code = other.getErr_code();
        if (this$err_code == null ? other$err_code != null : !this$err_code.equals(other$err_code)) {
            return false;
        }
        String this$view_msg = this.getView_msg();
        String other$view_msg = other.getView_msg();
        if (this$view_msg == null ? other$view_msg != null : !this$view_msg.equals(other$view_msg)) {
            return false;
        }
        MemberName this$memberName = this.getMemberName();
        MemberName other$memberName = other.getMemberName();
        if (this$memberName == null ? other$memberName != null : !((Object)this$memberName).equals(other$memberName)) {
            return false;
        }
        MemberInfo this$memberInfo = this.getMemberInfo();
        MemberInfo other$memberInfo = other.getMemberInfo();
        return !(this$memberInfo == null ? other$memberInfo != null : !((Object)this$memberInfo).equals(other$memberInfo));
    }

    protected boolean canEqual(Object other) {
        return other instanceof ResData;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getFollowerNum();
        Boolean $isMember = this.getIsMember();
        result = result * 59 + ($isMember == null ? 43 : ((Object)$isMember).hashCode());
        String $msg = this.getMsg();
        result = result * 59 + ($msg == null ? 43 : $msg.hashCode());
        String $err_msg = this.getErr_msg();
        result = result * 59 + ($err_msg == null ? 43 : $err_msg.hashCode());
        String $collectId = this.getCollectId();
        result = result * 59 + ($collectId == null ? 43 : $collectId.hashCode());
        String $credentialsType = this.getCredentialsType();
        result = result * 59 + ($credentialsType == null ? 43 : $credentialsType.hashCode());
        String $err_code = this.getErr_code();
        result = result * 59 + ($err_code == null ? 43 : $err_code.hashCode());
        String $view_msg = this.getView_msg();
        result = result * 59 + ($view_msg == null ? 43 : $view_msg.hashCode());
        MemberName $memberName = this.getMemberName();
        result = result * 59 + ($memberName == null ? 43 : ((Object)$memberName).hashCode());
        MemberInfo $memberInfo = this.getMemberInfo();
        result = result * 59 + ($memberInfo == null ? 43 : ((Object)$memberInfo).hashCode());
        return result;
    }

    public String toString() {
        return "ResData(msg=" + this.getMsg() + ", err_msg=" + this.getErr_msg() + ", followerNum=" + this.getFollowerNum() + ", collectId=" + this.getCollectId() + ", credentialsType=" + this.getCredentialsType() + ", isMember=" + this.getIsMember() + ", err_code=" + this.getErr_code() + ", view_msg=" + this.getView_msg() + ", memberName=" + this.getMemberName() + ", memberInfo=" + this.getMemberInfo() + ")";
    }
}
