/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.utils.DateUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.ai.document.Document
 *  org.springframework.ai.vectorstore.VectorStore
 *  org.springframework.util.CollectionUtils
 */
package com.robotmonitor.ai.runnable;

import com.robotmonitor.ai.domain.AiKnowledgeBase;
import com.robotmonitor.ai.mapper.AiKnowledgeBaseMapper;
import com.robotmonitor.common.utils.DateUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.util.CollectionUtils;

public class EmbeddingRunnable
implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(EmbeddingRunnable.class);
    private VectorStore vectorStore;
    private AiKnowledgeBaseMapper aiKnowledgeBaseMapper;
    private List<AiKnowledgeBase> aiKnowledgeBases;

    public EmbeddingRunnable(VectorStore vectorStore, AiKnowledgeBaseMapper aiKnowledgeBaseMapper, List<AiKnowledgeBase> aiKnowledgeBases) {
        this.vectorStore = vectorStore;
        this.aiKnowledgeBaseMapper = aiKnowledgeBaseMapper;
        this.aiKnowledgeBases = aiKnowledgeBases;
    }

    @Override
    public void run() {
        if (CollectionUtils.isEmpty(this.aiKnowledgeBases)) {
            return;
        }
        ArrayList documents = new ArrayList(this.aiKnowledgeBases.size());
        this.aiKnowledgeBases.forEach(aiKnowledgeBase -> {
            Document doc = Document.builder().text(aiKnowledgeBase.getContent()).metadata(Map.of("enable", aiKnowledgeBase.getEnable(), "source", aiKnowledgeBase.getSource(), "type", aiKnowledgeBase.getType())).build();
            aiKnowledgeBase.setDocument(doc);
            aiKnowledgeBase.setStatus("2");
            aiKnowledgeBase.setUpdateTime(DateUtils.getNowDate());
            this.aiKnowledgeBaseMapper.updateAiKnowledgeBase((AiKnowledgeBase)((Object)aiKnowledgeBase));
            documents.add(aiKnowledgeBase.getDocument());
        });
        int batchSize = 10;
        try {
            for (int i = 0; i < documents.size(); i += batchSize) {
                int end = Math.min(i + batchSize, documents.size());
                List batch = documents.subList(i, end);
                this.vectorStore.add(batch);
                batch.forEach(document -> log.info(document.toString()));
                batch.forEach(document -> this.aiKnowledgeBases.stream().filter(aiKnowledgeBase -> aiKnowledgeBase.getDocument().equals(document)).forEach(aiKnowledgeBase -> {
                    aiKnowledgeBase.setVectorId(aiKnowledgeBase.getDocument().getId());
                    aiKnowledgeBase.setStatus("3");
                    aiKnowledgeBase.setUpdateTime(DateUtils.getNowDate());
                    this.aiKnowledgeBaseMapper.updateAiKnowledgeBase((AiKnowledgeBase)((Object)((Object)aiKnowledgeBase)));
                }));
            }
        }
        catch (Exception exception) {
            log.error("\u5411\u91cf\u5316\u5931\u8d25\uff1a{}", (Object)exception.getMessage(), (Object)exception);
            this.aiKnowledgeBases.forEach(aiKnowledgeBase -> {
                aiKnowledgeBase.setVectorId(aiKnowledgeBase.getDocument().getId());
                aiKnowledgeBase.setStatus("4");
                aiKnowledgeBase.setUpdateTime(DateUtils.getNowDate());
                this.aiKnowledgeBaseMapper.updateAiKnowledgeBase((AiKnowledgeBase)((Object)aiKnowledgeBase));
            });
        }
    }
}
