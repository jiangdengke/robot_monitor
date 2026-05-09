/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.ai.chat.client.ChatClient
 */
package com.robotmonitor.ai.service;

import org.springframework.ai.chat.client.ChatClient;

public interface ChatClientService {
    public ChatClient getKnowledgeBaseChat();

    public ChatClient getOtherKnowledgeBaseChat();

    public ChatClient getChatClient();

    public ChatClient getToolsChatClient();

    public ChatClient getTextAnalyseChatClient();
}
