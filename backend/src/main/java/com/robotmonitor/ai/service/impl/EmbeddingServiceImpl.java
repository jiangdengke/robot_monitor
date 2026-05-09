/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.ai.vectorstore.VectorStore
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
 *  org.springframework.stereotype.Service
 *  org.springframework.util.CollectionUtils
 */
package com.robotmonitor.ai.service.impl;

import com.robotmonitor.ai.domain.AiKnowledgeBase;
import com.robotmonitor.ai.mapper.AiKnowledgeBaseMapper;
import com.robotmonitor.ai.runnable.EmbeddingRunnable;
import com.robotmonitor.ai.service.EmbeddingService;
import java.util.List;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class EmbeddingServiceImpl
implements EmbeddingService {
    @Autowired
    private VectorStore vectorStore;
    @Autowired
    @Qualifier(value="embeddingExecutor")
    private ThreadPoolTaskExecutor embeddingExecutor;
    @Autowired
    private AiKnowledgeBaseMapper aiKnowledgeBaseMapper;

    @Override
    public void embeddingText(List<AiKnowledgeBase> aiKnowledgeBases) {
        if (CollectionUtils.isEmpty(aiKnowledgeBases)) {
            return;
        }
        this.embeddingExecutor.execute((Runnable)new EmbeddingRunnable(this.vectorStore, this.aiKnowledgeBaseMapper, aiKnowledgeBases));
    }

    @Override
    public void deleteVectorStoreByIds(List<String> ids) {
        this.vectorStore.delete(ids);
    }
}
