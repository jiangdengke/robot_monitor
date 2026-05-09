/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.ai.service;

import com.robotmonitor.ai.domain.AiChatLog;
import com.robotmonitor.ai.dto.AiQuestionStatDTO;
import java.util.List;

public interface IAiChatLogService {
    public AiChatLog selectAiChatLogById(Long var1);

    public List<AiChatLog> selectAiChatLogList(AiChatLog var1);

    public int insertAiChatLog(AiChatLog var1);

    public int updateAiChatLog(AiChatLog var1);

    public int deleteAiChatLogByIds(Long[] var1);

    public int deleteAiChatLogById(Long var1);

    public void runAiAutoClassification();

    public List<AiQuestionStatDTO> selectAiQuestionStatList(String var1, String var2, String var3, String var4, String var5);
}
