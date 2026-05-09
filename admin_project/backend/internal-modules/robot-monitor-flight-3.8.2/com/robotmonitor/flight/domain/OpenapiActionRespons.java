/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain;

public class OpenapiActionRespons {
    private Long actionId;
    private Integer actionType;
    private String actionName;
    private String actionUrl;
    private Long parentId;
    private Integer priority;
    private Integer needUserAuth;

    public Long getActionId() {
        return this.actionId;
    }

    public Integer getActionType() {
        return this.actionType;
    }

    public String getActionName() {
        return this.actionName;
    }

    public String getActionUrl() {
        return this.actionUrl;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public Integer getNeedUserAuth() {
        return this.needUserAuth;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public void setNeedUserAuth(Integer needUserAuth) {
        this.needUserAuth = needUserAuth;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OpenapiActionRespons)) {
            return false;
        }
        OpenapiActionRespons other = (OpenapiActionRespons)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Long this$actionId = this.getActionId();
        Long other$actionId = other.getActionId();
        if (this$actionId == null ? other$actionId != null : !((Object)this$actionId).equals(other$actionId)) {
            return false;
        }
        Integer this$actionType = this.getActionType();
        Integer other$actionType = other.getActionType();
        if (this$actionType == null ? other$actionType != null : !((Object)this$actionType).equals(other$actionType)) {
            return false;
        }
        Long this$parentId = this.getParentId();
        Long other$parentId = other.getParentId();
        if (this$parentId == null ? other$parentId != null : !((Object)this$parentId).equals(other$parentId)) {
            return false;
        }
        Integer this$priority = this.getPriority();
        Integer other$priority = other.getPriority();
        if (this$priority == null ? other$priority != null : !((Object)this$priority).equals(other$priority)) {
            return false;
        }
        Integer this$needUserAuth = this.getNeedUserAuth();
        Integer other$needUserAuth = other.getNeedUserAuth();
        if (this$needUserAuth == null ? other$needUserAuth != null : !((Object)this$needUserAuth).equals(other$needUserAuth)) {
            return false;
        }
        String this$actionName = this.getActionName();
        String other$actionName = other.getActionName();
        if (this$actionName == null ? other$actionName != null : !this$actionName.equals(other$actionName)) {
            return false;
        }
        String this$actionUrl = this.getActionUrl();
        String other$actionUrl = other.getActionUrl();
        return !(this$actionUrl == null ? other$actionUrl != null : !this$actionUrl.equals(other$actionUrl));
    }

    protected boolean canEqual(Object other) {
        return other instanceof OpenapiActionRespons;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $actionId = this.getActionId();
        result = result * 59 + ($actionId == null ? 43 : ((Object)$actionId).hashCode());
        Integer $actionType = this.getActionType();
        result = result * 59 + ($actionType == null ? 43 : ((Object)$actionType).hashCode());
        Long $parentId = this.getParentId();
        result = result * 59 + ($parentId == null ? 43 : ((Object)$parentId).hashCode());
        Integer $priority = this.getPriority();
        result = result * 59 + ($priority == null ? 43 : ((Object)$priority).hashCode());
        Integer $needUserAuth = this.getNeedUserAuth();
        result = result * 59 + ($needUserAuth == null ? 43 : ((Object)$needUserAuth).hashCode());
        String $actionName = this.getActionName();
        result = result * 59 + ($actionName == null ? 43 : $actionName.hashCode());
        String $actionUrl = this.getActionUrl();
        result = result * 59 + ($actionUrl == null ? 43 : $actionUrl.hashCode());
        return result;
    }

    public String toString() {
        return "OpenapiActionRespons(actionId=" + this.getActionId() + ", actionType=" + this.getActionType() + ", actionName=" + this.getActionName() + ", actionUrl=" + this.getActionUrl() + ", parentId=" + this.getParentId() + ", priority=" + this.getPriority() + ", needUserAuth=" + this.getNeedUserAuth() + ")";
    }
}
