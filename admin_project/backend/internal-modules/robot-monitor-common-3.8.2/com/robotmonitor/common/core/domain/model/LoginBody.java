/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.core.domain.model;

public class LoginBody {
    private String username;
    private String password;
    private String code;
    private String uuid;
    private String robotId;

    public String getRobotId() {
        return this.robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
