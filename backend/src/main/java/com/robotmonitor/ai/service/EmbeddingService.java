/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.ai.service;

import com.robotmonitor.ai.domain.AiKnowledgeBase;
import java.util.List;

public interface EmbeddingService {
    public void embeddingText(List<AiKnowledgeBase> var1);

    public void deleteVectorStoreByIds(List<String> var1);
}
