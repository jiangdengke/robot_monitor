/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.constant.PushMessageConstants
 *  com.robotmonitor.common.core.domain.ai.PushMessage
 *  com.robotmonitor.common.utils.JsonUtils
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
 *  org.springframework.messaging.simp.SimpMessagingTemplate
 *  org.springframework.stereotype.Component
 */
package com.robotmonitor.ai.config;

import com.robotmonitor.common.constant.PushMessageConstants;
import com.robotmonitor.common.core.domain.ai.PushMessage;
import com.robotmonitor.common.utils.JsonUtils;
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
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisMessageListener
implements MessageListener {
    private static final Logger log = LoggerFactory.getLogger(RedisMessageListener.class);
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void onMessage(Message message, byte[] pattern) {
        byte[] messageBody = message.getBody();
        Object msg = this.redisTemplate.getValueSerializer().deserialize(messageBody);
        byte[] channelByte = message.getChannel();
        Object channel = this.redisTemplate.getStringSerializer().deserialize(channelByte);
        String patternStr = new String(pattern);
        log.info("\u9891\u9053 \uff1a {} \uff0c \u6536\u5230\u7684\u6d88\u606f \uff1a {}", channel, (Object)(null == msg ? "NULL" : JsonUtils.obj2String((Object)msg)));
        try {
            if (null != msg) {
                PushMessage pushMessage = (PushMessage)JsonUtils.string2Obj((String)msg.toString(), PushMessage.class);
                log.info("\u63a8\u9001\u7684ID\uff1a{}\uff0c \u6d88\u606f\u63a8\u9001\u5230\uff1a{}\uff0c \u63a8\u9001\u7684\u5185\u5bb9\u662f\uff1a{}", new Object[]{pushMessage.getUserId(), PushMessageConstants.TYPE_WEB_SOCKET_DESTINATION_MAP.get(pushMessage.getType()), pushMessage.getContent()});
                this.messagingTemplate.convertAndSendToUser(pushMessage.getUserId(), (String)PushMessageConstants.TYPE_WEB_SOCKET_DESTINATION_MAP.get(pushMessage.getType()), (Object)pushMessage.getContent());
            }
        }
        catch (Exception e) {
            log.error("\u89e3\u6790\u6d88\u606f\u5931\u8d25: {}", (Object)e.getMessage(), (Object)e);
        }
    }

    @Bean(value={"webSocketContainer"})
    public RedisMessageListenerContainer container(RedisConnectionFactory factory, RedisMessageListener listener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener((MessageListener)listener, (Topic)new ChannelTopic("redis.websocket.push"));
        return container;
    }
}
