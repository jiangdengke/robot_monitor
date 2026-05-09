/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.framework.config;

import java.security.Principal;

public static class UserHandshakeInterceptor.StompPrincipal
implements Principal {
    private final String name;

    public UserHandshakeInterceptor.StompPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
