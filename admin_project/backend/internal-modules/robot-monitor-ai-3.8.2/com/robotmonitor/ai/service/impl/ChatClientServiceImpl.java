/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.ai.chat.client.ChatClient
 *  org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor
 *  org.springframework.ai.chat.client.advisor.api.Advisor
 *  org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor
 *  org.springframework.ai.chat.memory.ChatMemory
 *  org.springframework.ai.chat.model.ChatModel
 *  org.springframework.ai.chat.prompt.ChatOptions
 *  org.springframework.ai.chat.prompt.PromptTemplate
 *  org.springframework.ai.model.tool.ToolCallingChatOptions
 *  org.springframework.ai.openai.OpenAiChatModel
 *  org.springframework.ai.vectorstore.SearchRequest
 *  org.springframework.ai.vectorstore.VectorStore
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 */
package com.robotmonitor.ai.service.impl;

import com.robotmonitor.ai.service.ChatClientService;
import com.robotmonitor.ai.tools.DateTimeTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatClientServiceImpl
implements ChatClientService {
    @Autowired
    private OpenAiChatModel chatModel;
    @Autowired
    private VectorStore vectorStore;
    @Autowired
    private ChatMemory chatMemory;
    @Autowired
    private DateTimeTools dateTimeTools;

    @Override
    public ChatClient getKnowledgeBaseChat() {
        return ChatClient.builder((ChatModel)this.chatModel).defaultSystem("\u4f60\u662f\u4e00\u4e2a\u56fd\u822a\u673a\u573a\u8d35\u5bbe\u5ba4\u673a\u5668\u4eba\u52a9\u624b\uff0c\u4f60\u7684\u540d\u5b57\u53eb\u5c0f\u98de\uff0c\u4f60\u4f1a\u6240\u6709\u548c\u673a\u573a\u3001\u8d35\u5bbe\u5ba4\u670d\u52a1\u76f8\u5173\u7684\u77e5\u8bc6\uff0c\u8bf7\u4ee5\u53cb\u597d\u7684\u8bed\u6c14\u56de\u7b54\u95ee\u9898\uff0c\u5b57\u6570\u4e0d\u8d85\u8fc750\u4e2a\u5b57\u3002\n").defaultOptions((ChatOptions)ToolCallingChatOptions.builder().temperature(Double.valueOf(0.8)).build()).defaultTools(new Object[]{this.dateTimeTools}).defaultAdvisors(new Advisor[]{new SimpleLoggerAdvisor(), QuestionAnswerAdvisor.builder((VectorStore)this.vectorStore).promptTemplate(new PromptTemplate("{query}\nContext information is below, surrounded by\n---------------------\n{question_answer_context}\n---------------------\nGiven the context and provided history information, reply to the user comment.\n\nRules:\n0. IMPORTANT: If the answer cannot be found in the context, return exactly: Unknown. Do NOT add any other text, explanation, or punctuation.\n   Do NOT translate or paraphrase. Do NOT add any punctuation, spaces, line breaks, or explanation\n1. Only use the information inside the context above and the provided history. Do not use any outside knowledge or assumptions.\n2. If the answer cannot be found in the context above, respond exactly with: Unknown. Do not guess or infer from outside knowledge or context.\n3. Do not add any extra punctuation, explanation, or text beyond what is needed to answer the question.\n4. Any supplementary information (such as current location or time) is for context reference only and must not be used to infer answers.\n\n/no_think\n")).searchRequest(SearchRequest.builder().similarityThreshold(0.75).topK(5).build()).build()}).build();
    }

    @Override
    public ChatClient getOtherKnowledgeBaseChat() {
        return ChatClient.builder((ChatModel)this.chatModel).defaultSystem("\u4f60\u662f\u4e00\u4e2a\u56fd\u822a\u673a\u573a\u8d35\u5bbe\u5ba4\u673a\u5668\u4eba\u52a9\u624b\uff0c\u4f60\u7684\u540d\u5b57\u53eb\u5c0f\u98de\uff0c\u4f60\u4f1a\u6240\u6709\u548c\u673a\u573a\u3001\u8d35\u5bbe\u5ba4\u670d\u52a1\u76f8\u5173\u7684\u77e5\u8bc6\uff0c\u8bf7\u4ee5\u53cb\u597d\u7684\u8bed\u6c14\u56de\u7b54\u95ee\u9898\uff0c\u5b57\u6570\u4e0d\u8d85\u8fc750\u4e2a\u5b57\u3002\n").defaultOptions((ChatOptions)ToolCallingChatOptions.builder().temperature(Double.valueOf(0.8)).build()).defaultTools(new Object[]{this.dateTimeTools}).defaultAdvisors(new Advisor[]{new SimpleLoggerAdvisor(), QuestionAnswerAdvisor.builder((VectorStore)this.vectorStore).promptTemplate(new PromptTemplate("{query}\nContext information is below, surrounded by ---------------------\n{question_answer_context}\n---------------------\nGiven the context and provided history information, reply to the user comment.\n\nRules:\n1. Only use the information inside the context above and the provided history. Do not use any outside knowledge or assumptions.\n2. Answer all parts of the question that can be answered using the context. For parts that cannot be answered from the context, skip them.\n3. If no part of the question can be answered from the context, respond exactly with: Unknown\n4. Do not add any extra punctuation, explanation, or text beyond what is needed to answer the question.\n\n/no_think\n")).searchRequest(SearchRequest.builder().similarityThreshold(0.75).topK(5).build()).build()}).build();
    }

    @Override
    public ChatClient getChatClient() {
        return ChatClient.builder((ChatModel)this.chatModel).defaultSystem("\u4f60\u662f\u4e00\u4e2a\u56fd\u822a\u673a\u573a\u8d35\u5bbe\u5ba4\u673a\u5668\u4eba\u52a9\u624b\uff0c\u4f60\u7684\u540d\u5b57\u53eb\u5c0f\u98de\uff0c\u4f60\u4f1a\u6240\u6709\u548c\u673a\u573a\u3001\u8d35\u5bbe\u5ba4\u670d\u52a1\u76f8\u5173\u7684\u77e5\u8bc6\uff0c\u8bf7\u4ee5\u53cb\u597d\u7684\u8bed\u6c14\u56de\u7b54\u95ee\u9898\uff0c\u5b57\u6570\u4e0d\u8d85\u8fc750\u4e2a\u5b57\u3002\n").defaultOptions((ChatOptions)ToolCallingChatOptions.builder().temperature(Double.valueOf(0.8)).build()).defaultTools(new Object[]{this.dateTimeTools}).defaultAdvisors(new Advisor[]{new SimpleLoggerAdvisor()}).build();
    }

    @Override
    public ChatClient getToolsChatClient() {
        return ChatClient.builder((ChatModel)this.chatModel).defaultSystem("\u4f60\u662f\u4e00\u4e2a\u8bed\u4e49\u5206\u6790\u4e13\u5bb6\uff0c\u53ef\u4ee5\u4ece\u7528\u4e8e\u8bed\u4e49\u4e0a\u5206\u6790\u7528\u6237\u4e3b\u8981\u60f3\u8981\u505a\u4ec0\u4e48\uff0c\u64c5\u957f\u603b\u7ed3\u5bf9\u8bdd\u4e2d\u7684\u5173\u952e\u4fe1\u606f\u3002\n").defaultOptions(ChatOptions.builder().temperature(Double.valueOf(0.1)).build()).defaultAdvisors(new Advisor[]{new SimpleLoggerAdvisor()}).build();
    }

    @Override
    public ChatClient getTextAnalyseChatClient() {
        return ChatClient.builder((ChatModel)this.chatModel).defaultSystem("\u4f60\u662f\u4e00\u4e2a\u6587\u5b57\u5206\u6790\u4e13\u5bb6\uff0c\u64c5\u957f\u63d0\u70bc\u548c\u603b\u7ed3\u5bf9\u8bdd\u7684\u4e3b\u9898\u548c\u6458\u8981\u3002\n").defaultOptions(ChatOptions.builder().temperature(Double.valueOf(0.1)).build()).defaultAdvisors(new Advisor[]{new SimpleLoggerAdvisor()}).build();
    }
}
