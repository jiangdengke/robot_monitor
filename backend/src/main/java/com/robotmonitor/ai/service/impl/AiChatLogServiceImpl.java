/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.utils.DateUtils
 *  io.jsonwebtoken.lang.Collections
 *  org.apache.logging.log4j.util.Strings
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.ai.service.impl;

import com.robotmonitor.ai.domain.AiChatLog;
import com.robotmonitor.ai.dto.AiQuestionStatDTO;
import com.robotmonitor.ai.mapper.AiChatLogMapper;
import com.robotmonitor.ai.service.ChatClientService;
import com.robotmonitor.ai.service.IAiChatLogService;
import com.robotmonitor.ai.utils.AiUtils;
import com.robotmonitor.common.utils.DateUtils;
import io.jsonwebtoken.lang.Collections;
import java.util.List;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AiChatLogServiceImpl
implements IAiChatLogService {
    @Autowired
    private AiChatLogMapper aiChatLogMapper;
    @Autowired
    private ChatClientService chatClientService;

    @Override
    public AiChatLog selectAiChatLogById(Long id) {
        return this.aiChatLogMapper.selectAiChatLogById(id);
    }

    @Override
    public List<AiChatLog> selectAiChatLogList(AiChatLog aiChatLog) {
        return this.aiChatLogMapper.selectAiChatLogList(aiChatLog);
    }

    @Override
    public int insertAiChatLog(AiChatLog aiChatLog) {
        aiChatLog.setCreateTime(DateUtils.getNowDate());
        return this.aiChatLogMapper.insertAiChatLog(aiChatLog);
    }

    @Override
    public int updateAiChatLog(AiChatLog aiChatLog) {
        return this.aiChatLogMapper.updateAiChatLog(aiChatLog);
    }

    @Override
    public int deleteAiChatLogByIds(Long[] ids) {
        return this.aiChatLogMapper.deleteAiChatLogByIds(ids);
    }

    @Override
    public int deleteAiChatLogById(Long id) {
        return this.aiChatLogMapper.deleteAiChatLogById(id);
    }

    @Override
    public void runAiAutoClassification() {
        List<AiChatLog> aiChatLogs = this.aiChatLogMapper.findNeedAutoClassificationLog();
        Object tags = this.aiChatLogMapper.findAllTags();
        while (!Collections.isEmpty(aiChatLogs)) {
            if (Strings.isBlank((String)tags)) {
                tags = "";
            }
            for (AiChatLog aiChatLog : aiChatLogs) {
                String aiAutoClassification = this.getAutoClassificationResult(aiChatLog.getQuestion(), aiChatLog.getAnswer(), (String)tags);
                if (!((String)tags).contains(aiAutoClassification)) {
                    tags = (String)tags + "," + aiAutoClassification;
                }
                aiChatLog.setAiAutoClassification(aiAutoClassification);
                this.aiChatLogMapper.updateAiChatLog(aiChatLog);
            }
            aiChatLogs = this.aiChatLogMapper.findNeedAutoClassificationLog();
        }
    }

    private String getAutoClassificationResult(String question, String answer, String tags) {
        String prompt = "\u4f60\u662f\u4e00\u540d\u667a\u80fd\u5ba2\u670d\u5206\u7c7b\u4e13\u5bb6\u3002\n\u8bf7\u6839\u636e\u4e0b\u9762\u7684\u63d0\u95ee\u548c\u56de\u7b54\uff0c\u81ea\u52a8\u751f\u6210\u4e00\u4e2a\u6700\u5408\u9002\u7684\u4e1a\u52a1\u5206\u7c7b\u6807\u7b7e\uff08\u4e2d\u6587\u3001\u63cf\u8ff0\u6027\u3001\u7b80\u77ed\u3001\u5b57\u6570\u4e0d\u80fd\u8d85\u8fc710\u5b57\uff09\u3002\n\u5df2\u5b58\u5728\u7684\u5927\u5206\u7c7b\u6709\uff1a\u95ee\u7b54\uff08FAQ\uff09\u3001INTRODUCING_PLACES\uff08\u4ecb\u7ecd\u5730\u70b9\uff09\u3001\u5bfb\u8def\uff08FINDING_PLACES\uff09\u3001\u822a\u73ed\u52a8\u6001\u76f8\u5173\uff08FLIGHT\uff09\u3001\u8d35\u5bbe\u5ba4\u51c6\u5165\uff08ACCESS\uff09\u3001\u5176\u4ed6\uff08OTHER\uff09\n\u8981\u6c42\uff1a\n1. \u6807\u7b7e\u53ea\u5305\u542b\u5b50\u7c7b\u4fe1\u606f\u3002\n2. \u5c3d\u91cf\u6e05\u695a\u8bf4\u660e\u4e1a\u52a1\u5185\u5bb9\uff0c\u4f46\u4e0d\u8981\u8d85\u8fc710\u4e2a\u5b57\u3002\n3. \u4fdd\u6301\u7b80\u6d01\u660e\u4e86\u3002\n4. \u6807\u7b7e\u4e3a\u5927\u5206\u7c7b\u4e0b\u7684\u518d\u6b21\u7ec6\u5206\u3002\n5. \u5c3d\u53ef\u80fd\u4f7f\u7528\u5df2\u5b58\u5728\u7684\u6807\u7b7e\n6. \u5982\u679c\u662f\u5bfb\u8def\uff0c\u6807\u7b7e\u4e2d\u8bf7\u5e26\u4e0a\u76ee\u7684\u5730\n\n\u63d0\u95ee\uff1a%s\n\u56de\u7b54\uff1a%s\n\u5f53\u524d\u5df2\u7ecf\u5b58\u5728\u7684\u6807\u7b7e\u6709\uff1a%s\n\u3010\u8bf7\u4ec5\u8fd4\u56de\u3011\uff1a\u5206\u7c7b\u6807\u7b7e\uff0c\u4e0d\u52a0\u6ce8\u91ca\u6216\u89e3\u91ca\u3002\n/no_think\n".formatted(question, answer, tags);
        return AiUtils.removeThinkTag(this.chatClientService.getTextAnalyseChatClient().prompt(prompt).call().content());
    }

    @Override
    public List<AiQuestionStatDTO> selectAiQuestionStatList(String robotId, String question, String chatType, String startTime, String endTime) {
        List<AiQuestionStatDTO> list = this.aiChatLogMapper.selectAiQuestionStatList(robotId, question, chatType, startTime, endTime);
        for (AiQuestionStatDTO item : list) {
            if (item.getChatType() != null && !item.getChatType().trim().isEmpty()) continue;
            item.setChatType("OTHER");
        }
        return list;
    }
}
