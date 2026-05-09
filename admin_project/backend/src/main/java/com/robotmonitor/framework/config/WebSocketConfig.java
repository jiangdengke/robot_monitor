/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.messaging.simp.config.ChannelRegistration
 *  org.springframework.messaging.simp.config.MessageBrokerRegistry
 *  org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
 *  org.springframework.web.socket.config.annotation.StompEndpointRegistry
 *  org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
 *  org.springframework.web.socket.config.annotation.WebSocketTransportRegistration
 *  org.springframework.web.socket.server.HandshakeHandler
 *  org.springframework.web.socket.server.HandshakeInterceptor
 */
package com.robotmonitor.framework.config;

import com.robotmonitor.framework.config.UserHandshakeHandler;
import com.robotmonitor.framework.config.UserHandshakeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig
implements WebSocketMessageBrokerConfigurer {
    @Autowired
    private UserHandshakeInterceptor userHandshakeInterceptor;

    public void configureClientInboundChannel(ChannelRegistration registration) {
    }

    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(new String[]{"/ws"}).setHandshakeHandler((HandshakeHandler)new UserHandshakeHandler()).addInterceptors(new HandshakeInterceptor[]{this.userHandshakeInterceptor}).setAllowedOriginPatterns(new String[]{"*"}).withSockJS();
    }

    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(new String[]{"/queue", "/topic"});
        registry.setApplicationDestinationPrefixes(new String[]{"/app"});
        registry.setUserDestinationPrefix("/ai");
    }

    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setMessageSizeLimit(0xA00000);
        registration.setSendTimeLimit(600000);
        registration.setSendBufferSizeLimit(0xA00000);
    }
}
