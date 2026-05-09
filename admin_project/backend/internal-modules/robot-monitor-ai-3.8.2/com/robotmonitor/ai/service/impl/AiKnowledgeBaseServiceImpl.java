/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.utils.DateUtils
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.ai.service.impl;

import com.robotmonitor.ai.domain.AiKnowledgeBase;
import com.robotmonitor.ai.mapper.AiKnowledgeBaseMapper;
import com.robotmonitor.ai.service.EmbeddingService;
import com.robotmonitor.ai.service.IAiKnowledgeBaseService;
import com.robotmonitor.common.utils.DateUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AiKnowledgeBaseServiceImpl
implements IAiKnowledgeBaseService {
    @Autowired
    private AiKnowledgeBaseMapper aiKnowledgeBaseMapper;
    @Autowired
    private EmbeddingService embeddingService;

    @Override
    public AiKnowledgeBase selectAiKnowledgeBaseById(Long id) {
        return this.aiKnowledgeBaseMapper.selectAiKnowledgeBaseById(id);
    }

    @Override
    public List<AiKnowledgeBase> selectAiKnowledgeBaseList(AiKnowledgeBase aiKnowledgeBase) {
        return this.aiKnowledgeBaseMapper.selectAiKnowledgeBaseList(aiKnowledgeBase);
    }

    @Override
    public int insertAiKnowledgeBase(AiKnowledgeBase aiKnowledgeBase) {
        aiKnowledgeBase.setType("1");
        aiKnowledgeBase.setStatus("1");
        aiKnowledgeBase.setEnable("0");
        aiKnowledgeBase.setUpdateTime(DateUtils.getNowDate());
        aiKnowledgeBase.setCreateTime(DateUtils.getNowDate());
        return this.aiKnowledgeBaseMapper.insertAiKnowledgeBase(aiKnowledgeBase);
    }

    @Override
    public int updateAiKnowledgeBase(AiKnowledgeBase aiKnowledgeBase) {
        AiKnowledgeBase originalAiKnowledgeBase = this.selectAiKnowledgeBaseById(aiKnowledgeBase.getId());
        if (!(originalAiKnowledgeBase.getContent().equals(aiKnowledgeBase.getContent()) && originalAiKnowledgeBase.getSource().equals(aiKnowledgeBase.getSource()) && originalAiKnowledgeBase.getType().equals(aiKnowledgeBase.getType()) && originalAiKnowledgeBase.getEnable().equals(aiKnowledgeBase.getEnable()))) {
            ArrayList<String> documentIds = new ArrayList<String>(1);
            documentIds.add(originalAiKnowledgeBase.getVectorId());
            this.embeddingService.deleteVectorStoreByIds(documentIds);
            aiKnowledgeBase.setVectorId(null);
            aiKnowledgeBase.setStatus("1");
            aiKnowledgeBase.setEnable("0");
        }
        aiKnowledgeBase.setUpdateTime(DateUtils.getNowDate());
        return this.aiKnowledgeBaseMapper.updateAiKnowledgeBase(aiKnowledgeBase);
    }

    @Override
    public int deleteAiKnowledgeBaseByIds(Long[] ids) {
        return this.aiKnowledgeBaseMapper.deleteAiKnowledgeBaseByIds(ids);
    }

    @Override
    public int deleteAiKnowledgeBaseById(Long id) {
        return this.aiKnowledgeBaseMapper.deleteAiKnowledgeBaseById(id);
    }

    @Override
    public void embedding(Long[] ids, String userName) {
        ArrayList<AiKnowledgeBase> aiKnowledgeBases = new ArrayList<AiKnowledgeBase>(ids.length);
        ArrayList<String> documentIds = new ArrayList<String>(ids.length);
        for (Long id : ids) {
            AiKnowledgeBase aiKnowledgeBase = this.selectAiKnowledgeBaseById(id);
            if ("1".equalsIgnoreCase(aiKnowledgeBase.getStatus())) {
                aiKnowledgeBase.setEnable("1");
                aiKnowledgeBase.setUpdateBy(userName);
                aiKnowledgeBases.add(aiKnowledgeBase);
            }
            if (!"3".equalsIgnoreCase(aiKnowledgeBase.getStatus()) && !"4".equalsIgnoreCase(aiKnowledgeBase.getStatus())) continue;
            documentIds.add(aiKnowledgeBase.getVectorId());
            aiKnowledgeBase.setEnable("1");
            aiKnowledgeBase.setUpdateBy(userName);
            aiKnowledgeBases.add(aiKnowledgeBase);
        }
        this.embeddingService.deleteVectorStoreByIds(documentIds);
        this.embeddingService.embeddingText(aiKnowledgeBases);
    }

    @Override
    public void enable(Long[] ids, String userName) {
        this.embedding(ids, userName);
    }

    @Override
    public void disable(Long[] ids, String userName) {
        ArrayList<String> documentIds = new ArrayList<String>(ids.length);
        for (Long id : ids) {
            AiKnowledgeBase aiKnowledgeBase = this.selectAiKnowledgeBaseById(id);
            aiKnowledgeBase.setUpdateBy(userName);
            aiKnowledgeBase.setVectorId(null);
            aiKnowledgeBase.setStatus("1");
            aiKnowledgeBase.setEnable("0");
            documentIds.add(aiKnowledgeBase.getVectorId());
            this.aiKnowledgeBaseMapper.updateAiKnowledgeBase(aiKnowledgeBase);
        }
        this.embeddingService.deleteVectorStoreByIds(documentIds);
    }
}
