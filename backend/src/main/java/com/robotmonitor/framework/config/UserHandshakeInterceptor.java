/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.config.HandshakeBusinessLogic
 *  jakarta.servlet.http.HttpServletRequest
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.http.server.ServerHttpRequest
 *  org.springframework.http.server.ServerHttpResponse
 *  org.springframework.http.server.ServletServerHttpRequest
 *  org.springframework.lang.Nullable
 *  org.springframework.stereotype.Component
 *  org.springframework.web.socket.WebSocketHandler
 *  org.springframework.web.socket.server.HandshakeInterceptor
 */
package com.robotmonitor.framework.config;

import com.robotmonitor.common.config.HandshakeBusinessLogic;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Component
public class UserHandshakeInterceptor
implements HandshakeInterceptor {
    private final ObjectProvider<HandshakeBusinessLogic> logicProvider;

    public UserHandshakeInterceptor(ObjectProvider<HandshakeBusinessLogic> logicProvider) {
        this.logicProvider = logicProvider;
    }

    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest)request;
            HttpServletRequest servletReq = servletRequest.getServletRequest();
            String robotId = servletReq.getParameter("robotId");
            String roomCode = servletReq.getParameter("roomCode");
            if ((robotId == null || robotId.isEmpty()) && (roomCode == null || roomCode.isEmpty())) {
                return false;
            }
            HandshakeBusinessLogic logic = (HandshakeBusinessLogic)this.logicProvider.getIfAvailable();
            if (logic != null) {
                logic.handle(robotId);
            }
            if (null != robotId) {
                attributes.put("principal", new StompPrincipal(robotId));
            }
            if (null != roomCode) {
                attributes.put("principal", new StompPrincipal(roomCode));
            }
        }
        return true;
    }

    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, @Nullable Exception exception) {
    }

    public static class StompPrincipal
    implements Principal {
        private final String name;

        public StompPrincipal(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }
}
