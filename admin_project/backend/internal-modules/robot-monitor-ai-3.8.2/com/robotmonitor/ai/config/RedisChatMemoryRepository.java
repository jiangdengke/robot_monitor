/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  org.springframework.ai.chat.memory.ChatMemoryRepository
 *  org.springframework.ai.chat.messages.AssistantMessage
 *  org.springframework.ai.chat.messages.AssistantMessage$ToolCall
 *  org.springframework.ai.chat.messages.Message
 *  org.springframework.ai.chat.messages.MessageType
 *  org.springframework.ai.chat.messages.SystemMessage
 *  org.springframework.ai.chat.messages.UserMessage
 *  org.springframework.ai.content.Media
 *  org.springframework.ai.content.Media$Builder
 *  org.springframework.data.redis.core.StringRedisTemplate
 *  org.springframework.util.MimeType
 */
package com.robotmonitor.ai.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.content.Media;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.MimeType;

public class RedisChatMemoryRepository
implements ChatMemoryRepository {
    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;
    private final String PREFIX;
    private final String CONVERSATION_IDS_SET;
    private static final String REDIS_CHART_MEMORY_PREFIX = "chat:conversation";
    private static final String REDIS_CHART_MEMORY_CONVERSATION_IDS_SET = "chat:all_conversation_ids";

    public RedisChatMemoryRepository(StringRedisTemplate stringRedisTemplate, ObjectMapper objectMapper) {
        this(stringRedisTemplate, objectMapper, REDIS_CHART_MEMORY_PREFIX, REDIS_CHART_MEMORY_CONVERSATION_IDS_SET);
    }

    public RedisChatMemoryRepository(StringRedisTemplate stringRedisTemplate, ObjectMapper objectMapper, String PREFIX) {
        this(stringRedisTemplate, objectMapper, PREFIX, REDIS_CHART_MEMORY_CONVERSATION_IDS_SET);
    }

    public RedisChatMemoryRepository(StringRedisTemplate stringRedisTemplate, ObjectMapper objectMapper, String PREFIX, String CONVERSATION_IDS_SET) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.objectMapper = objectMapper;
        this.PREFIX = PREFIX;
        this.CONVERSATION_IDS_SET = CONVERSATION_IDS_SET;
    }

    public List<String> findConversationIds() {
        Set conversationIds = this.stringRedisTemplate.opsForZSet().reverseRange((Object)this.CONVERSATION_IDS_SET, 0L, -1L);
        if (conversationIds == null || conversationIds.isEmpty()) {
            return List.of();
        }
        return new ArrayList<String>(conversationIds);
    }

    public List<Message> findByConversationId(String conversationId) {
        if (conversationId == null || conversationId.isEmpty()) {
            throw new IllegalArgumentException("conversationId cannot be null or empty");
        }
        List list = this.stringRedisTemplate.opsForList().range((Object)(this.PREFIX + conversationId), 0L, -1L);
        if (list == null || list.isEmpty()) {
            return List.of();
        }
        return list.stream().map(json -> {
            try {
                return this.deserializeMessage((String)json);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    public void saveAll(String conversationId, List<Message> messages) {
        if (conversationId == null || conversationId.isEmpty()) {
            throw new IllegalArgumentException("conversationId cannot be null or empty");
        }
        this.stringRedisTemplate.delete((Object)(this.PREFIX + conversationId));
        if (messages == null || messages.isEmpty()) {
            return;
        }
        List list = messages.stream().map(message -> {
            try {
                return this.objectMapper.writeValueAsString(message);
            }
            catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to serialize Message", e);
            }
        }).collect(Collectors.toList());
        this.stringRedisTemplate.opsForList().rightPushAll((Object)(this.PREFIX + conversationId), list);
        this.stringRedisTemplate.expire((Object)(this.PREFIX + conversationId), Duration.ofMinutes(2L));
        this.stringRedisTemplate.opsForZSet().add((Object)this.CONVERSATION_IDS_SET, (Object)conversationId, (double)System.currentTimeMillis());
    }

    public void deleteByConversationId(String conversationId) {
        if (conversationId == null || conversationId.isEmpty()) {
            throw new IllegalArgumentException("conversationId cannot be null or empty");
        }
        this.stringRedisTemplate.delete((Object)(this.PREFIX + conversationId));
        this.stringRedisTemplate.opsForZSet().remove((Object)this.CONVERSATION_IDS_SET, new Object[]{conversationId});
    }

    public Message deserializeMessage(String json) throws IOException {
        JsonNode jsonNode = this.objectMapper.readTree(json);
        if (!jsonNode.has("messageType")) {
            throw new IllegalArgumentException("Missing or invalid messageType field");
        }
        String messageType = jsonNode.get("messageType").asText();
        String text = jsonNode.has("text") ? jsonNode.get("text").asText() : "";
        Map<String, Object> metadata = this.getMetadata(jsonNode);
        List<Media> mediaList = this.getMediaList(jsonNode);
        return switch (MessageType.valueOf((String)messageType)) {
            case MessageType.SYSTEM -> new SystemMessage(text);
            case MessageType.USER -> UserMessage.builder().text(text).media(mediaList).metadata(metadata).build();
            case MessageType.ASSISTANT -> {
                List<AssistantMessage.ToolCall> toolCalls = this.getToolCalls(jsonNode);
                yield new AssistantMessage(text, metadata, toolCalls, mediaList);
            }
            default -> throw new IllegalArgumentException("Unknown message type: " + messageType);
        };
    }

    private Media deserializeMedia(ObjectMapper mapper, JsonNode mediaNode) throws IOException {
        Media.Builder builder = Media.builder();
        if (mediaNode.has("mimeType")) {
            JsonNode mimeNode = mediaNode.get("mimeType");
            String type = mimeNode.get("type").asText();
            String subtype = mimeNode.get("subtype").asText();
            builder.mimeType(new MimeType(type, subtype));
        }
        if (mediaNode.has("data")) {
            String data = mediaNode.get("data").asText();
            if (data.startsWith("http://") || data.startsWith("https://")) {
                builder.data((Object)new URL(data));
            } else {
                byte[] bytes = Base64.getDecoder().decode(data);
                builder.data((Object)bytes);
            }
        }
        if (mediaNode.has("dataAsByteArray")) {
            byte[] bytes = Base64.getDecoder().decode(mediaNode.get("dataAsByteArray").asText());
            builder.data((Object)bytes);
        }
        if (mediaNode.has("id")) {
            builder.id(mediaNode.get("id").asText());
        }
        if (mediaNode.has("name")) {
            builder.name(mediaNode.get("name").asText());
        }
        return builder.build();
    }

    private Map<String, Object> getMetadata(JsonNode jsonNode) {
        if (jsonNode.has("metadata")) {
            return (Map)this.objectMapper.convertValue((Object)jsonNode.get("metadata"), (TypeReference)new TypeReference<Map<String, Object>>(){});
        }
        return new HashMap<String, Object>();
    }

    private List<Media> getMediaList(JsonNode jsonNode) throws IOException {
        ArrayList<Media> mediaList = new ArrayList<Media>();
        if (jsonNode.has("media")) {
            for (JsonNode mediaNode : jsonNode.get("media")) {
                mediaList.add(this.deserializeMedia(this.objectMapper, mediaNode));
            }
        }
        return mediaList;
    }

    private List<AssistantMessage.ToolCall> getToolCalls(JsonNode jsonNode) {
        if (jsonNode.has("toolCalls")) {
            return (List)this.objectMapper.convertValue((Object)jsonNode.get("toolCalls"), (TypeReference)new TypeReference<List<AssistantMessage.ToolCall>>(){});
        }
        return Collections.emptyList();
    }
}
