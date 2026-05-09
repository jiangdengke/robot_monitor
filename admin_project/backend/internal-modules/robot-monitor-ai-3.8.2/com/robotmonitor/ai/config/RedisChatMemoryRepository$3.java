/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.ai.chat.messages.MessageType
 */
package com.robotmonitor.ai.config;

import org.springframework.ai.chat.messages.MessageType;

static class RedisChatMemoryRepository.3 {
    static final /* synthetic */ int[] $SwitchMap$org$springframework$ai$chat$messages$MessageType;

    static {
        $SwitchMap$org$springframework$ai$chat$messages$MessageType = new int[MessageType.values().length];
        try {
            RedisChatMemoryRepository.3.$SwitchMap$org$springframework$ai$chat$messages$MessageType[MessageType.SYSTEM.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            RedisChatMemoryRepository.3.$SwitchMap$org$springframework$ai$chat$messages$MessageType[MessageType.USER.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            RedisChatMemoryRepository.3.$SwitchMap$org$springframework$ai$chat$messages$MessageType[MessageType.ASSISTANT.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
