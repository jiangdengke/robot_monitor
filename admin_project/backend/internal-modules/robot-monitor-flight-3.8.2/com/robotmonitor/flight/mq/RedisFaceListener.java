/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.context.annotation.Bean
 *  org.springframework.data.redis.connection.Message
 *  org.springframework.data.redis.connection.MessageListener
 *  org.springframework.data.redis.connection.RedisConnectionFactory
 *  org.springframework.data.redis.core.RedisTemplate
 *  org.springframework.data.redis.listener.ChannelTopic
 *  org.springframework.data.redis.listener.RedisMessageListenerContainer
 *  org.springframework.data.redis.listener.Topic
 *  org.springframework.stereotype.Component
 */
package com.robotmonitor.flight.mq;

import com.robotmonitor.flight.mq.FaceConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Component;

@Component
public class RedisFaceListener
implements MessageListener {
    private static final Logger log = LoggerFactory.getLogger(RedisFaceListener.class);
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private FaceConsumer faceConsumer;

    public void onMessage(Message message, byte[] pattern) {
        Object msg = this.redisTemplate.getValueSerializer().deserialize(message.getBody());
        String channel = new String(message.getChannel());
        log.info("face listener {} \u6536\u5230\u6d88\u606f: {}", (Object)channel, msg);
        try {
            if (msg != null) {
                String msgStr = msg.toString();
                if (msgStr.startsWith("\"") && msgStr.endsWith("\"")) {
                    msgStr = msgStr.substring(1, msgStr.length() - 1);
                }
                msgStr = msgStr.replace("\\\"", "\"");
                this.faceConsumer.faceListenerOld(msgStr);
            }
        }
        catch (Exception e) {
            log.error("\u5904\u7406face\u6d88\u606f\u5931\u8d25: {}", (Object)e.getMessage(), (Object)e);
        }
    }

    @Bean(value={"faceContainer"})
    public RedisMessageListenerContainer container(RedisConnectionFactory factory, RedisFaceListener listener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener((MessageListener)listener, (Topic)new ChannelTopic("redis.glint.face"));
        return container;
    }
}
