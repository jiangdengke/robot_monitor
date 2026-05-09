/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  org.springframework.ai.chat.memory.ChatMemory
 *  org.springframework.ai.chat.memory.ChatMemoryRepository
 *  org.springframework.ai.chat.memory.MessageWindowChatMemory
 *  org.springframework.context.annotation.Bean
 *  org.springframework.data.redis.core.StringRedisTemplate
 *  org.springframework.stereotype.Component
 */
package com.robotmonitor.ai.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robotmonitor.ai.config.RedisChatMemoryRepository;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class ChatConfig {
    private static final int MAX_MEMORY_SIZE = 20;

    @Bean
    public ChatMemoryRepository chatMemoryRepository(StringRedisTemplate stringRedisTemplate) {
        return new RedisChatMemoryRepository(stringRedisTemplate, new ObjectMapper());
    }

    @Bean
    public ChatMemory chatMemory(ChatMemoryRepository chatMemoryRepository) {
        return MessageWindowChatMemory.builder().chatMemoryRepository(chatMemoryRepository).maxMessages(20).build();
    }
}
