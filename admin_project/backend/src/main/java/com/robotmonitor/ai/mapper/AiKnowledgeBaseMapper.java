/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.ai.mapper;

import com.robotmonitor.ai.domain.AiKnowledgeBase;
import java.util.List;

public interface AiKnowledgeBaseMapper {
    public AiKnowledgeBase selectAiKnowledgeBaseById(Long var1);

    public List<AiKnowledgeBase> selectAiKnowledgeBaseList(AiKnowledgeBase var1);

    public int insertAiKnowledgeBase(AiKnowledgeBase var1);

    public int updateAiKnowledgeBase(AiKnowledgeBase var1);

    public int deleteAiKnowledgeBaseById(Long var1);

    public int deleteAiKnowledgeBaseByIds(Long[] var1);
}
