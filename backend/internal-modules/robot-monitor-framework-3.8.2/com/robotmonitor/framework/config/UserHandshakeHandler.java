/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.server.ServerHttpRequest
 *  org.springframework.web.socket.WebSocketHandler
 *  org.springframework.web.socket.server.support.DefaultHandshakeHandler
 */
package com.robotmonitor.framework.config;

import java.security.Principal;
import java.util.Map;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

public class UserHandshakeHandler
extends DefaultHandshakeHandler {
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        Principal principal = (Principal)attributes.get("principal");
        if (principal != null) {
            return principal;
        }
        return super.determineUser(request, wsHandler, attributes);
    }
}
