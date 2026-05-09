/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 */
package com.robotmonitor.ai.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.robotmonitor.ai.utils.ChatTypeConvertUtil;
import java.util.Date;

public class AiQuestionStatDTO {
    private String robotId;
    private String robotName;
    private String deptName;
    private String aiAutoClassification;
    private String question;
    private String answer;
    private String chatType;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public String getRobotId() {
        return this.robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public String getRobotName() {
        return this.robotName;
    }

    public void setRobotName(String robotName) {
        this.robotName = robotName;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getAiAutoClassification() {
        return this.aiAutoClassification;
    }

    public void setAiAutoClassification(String aiAutoClassification) {
        this.aiAutoClassification = aiAutoClassification;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getChatType() {
        return this.chatType;
    }

    public String getChatTypeChinese() {
        return ChatTypeConvertUtil.convertToChinese(this.chatType);
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
