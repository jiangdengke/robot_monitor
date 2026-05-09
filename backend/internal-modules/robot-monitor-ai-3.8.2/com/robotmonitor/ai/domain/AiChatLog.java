/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Excel
 *  com.robotmonitor.common.core.domain.BaseEntity
 *  org.apache.commons.lang3.builder.ToStringBuilder
 *  org.apache.commons.lang3.builder.ToStringStyle
 */
package com.robotmonitor.ai.domain;

import com.robotmonitor.common.annotation.Excel;
import com.robotmonitor.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AiChatLog
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    @Excel(name="\u673a\u5668\u4ebaID")
    private String robotId;
    @Excel(name="\u63d0\u95ee")
    private String question;
    @Excel(name="\u56de\u7b54")
    private String answer;
    @Excel(name="\u8bed\u8a00")
    private String language;
    @Excel(name="\u804a\u5929\u7c7b\u578b", readConverterExp="\u4e1a=\u52a1\u5206\u7c7b")
    private String chatType;
    @Excel(name="ai\u81ea\u52a8\u5206\u7c7b")
    private String aiAutoClassification;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public String getRobotId() {
        return this.robotId;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return this.answer;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }

    public String getChatType() {
        return this.chatType;
    }

    public void setAiAutoClassification(String aiAutoClassification) {
        this.aiAutoClassification = aiAutoClassification;
    }

    public String getAiAutoClassification() {
        return this.aiAutoClassification;
    }

    public String toString() {
        return new ToStringBuilder((Object)this, ToStringStyle.MULTI_LINE_STYLE).append("id", (Object)this.getId()).append("robotId", (Object)this.getRobotId()).append("question", (Object)this.getQuestion()).append("answer", (Object)this.getAnswer()).append("language", (Object)this.getLanguage()).append("chatType", (Object)this.getChatType()).append("aiAutoClassification", (Object)this.getAiAutoClassification()).append("createTime", (Object)this.getCreateTime()).toString();
    }
}
