/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain;

import com.robotmonitor.flight.domain.OpenapiActionRespons;
import java.util.List;

public class AuthResponse {
    private String accountId;
    private String token;
    private List<OpenapiActionRespons> actions;

    public String getAccountId() {
        return this.accountId;
    }

    public String getToken() {
        return this.token;
    }

    public List<OpenapiActionRespons> getActions() {
        return this.actions;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setActions(List<OpenapiActionRespons> actions) {
        this.actions = actions;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AuthResponse)) {
            return false;
        }
        AuthResponse other = (AuthResponse)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$accountId = this.getAccountId();
        String other$accountId = other.getAccountId();
        if (this$accountId == null ? other$accountId != null : !this$accountId.equals(other$accountId)) {
            return false;
        }
        String this$token = this.getToken();
        String other$token = other.getToken();
        if (this$token == null ? other$token != null : !this$token.equals(other$token)) {
            return false;
        }
        List<OpenapiActionRespons> this$actions = this.getActions();
        List<OpenapiActionRespons> other$actions = other.getActions();
        return !(this$actions == null ? other$actions != null : !((Object)this$actions).equals(other$actions));
    }

    protected boolean canEqual(Object other) {
        return other instanceof AuthResponse;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $accountId = this.getAccountId();
        result = result * 59 + ($accountId == null ? 43 : $accountId.hashCode());
        String $token = this.getToken();
        result = result * 59 + ($token == null ? 43 : $token.hashCode());
        List<OpenapiActionRespons> $actions = this.getActions();
        result = result * 59 + ($actions == null ? 43 : ((Object)$actions).hashCode());
        return result;
    }

    public String toString() {
        return "AuthResponse(accountId=" + this.getAccountId() + ", token=" + this.getToken() + ", actions=" + this.getActions() + ")";
    }
}
