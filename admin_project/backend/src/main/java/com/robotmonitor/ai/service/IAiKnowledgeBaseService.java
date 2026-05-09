/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.ai.service;

import com.robotmonitor.ai.domain.AiKnowledgeBase;
import java.util.List;

public interface IAiKnowledgeBaseService {
    public AiKnowledgeBase selectAiKnowledgeBaseById(Long var1);

    public List<AiKnowledgeBase> selectAiKnowledgeBaseList(AiKnowledgeBase var1);

    public int insertAiKnowledgeBase(AiKnowledgeBase var1);

    public int updateAiKnowledgeBase(AiKnowledgeBase var1);

    public int deleteAiKnowledgeBaseByIds(Long[] var1);

    public int deleteAiKnowledgeBaseById(Long var1);

    public void embedding(Long[] var1, String var2);

    public void enable(Long[] var1, String var2);

    public void disable(Long[] var1, String var2);
}
