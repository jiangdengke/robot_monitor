package com.robotmonitor.ai.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.AI_KNOWLEDGE_BASE;

import com.robotmonitor.ai.domain.AiKnowledgeBase;
import com.robotmonitor.ai.mapper.AiKnowledgeBaseMapper;
import com.robotmonitor.common.jooq.GenericJooqCrudSupport;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqAiKnowledgeBaseMapper extends GenericJooqCrudSupport<AiKnowledgeBase> implements AiKnowledgeBaseMapper {
    public JooqAiKnowledgeBaseMapper(DSLContext dsl) {
        super(dsl, AI_KNOWLEDGE_BASE, AI_KNOWLEDGE_BASE.ID, AiKnowledgeBase.class);
    }

    @Override
    public AiKnowledgeBase selectAiKnowledgeBaseById(Long id) {
        return selectById(id);
    }

    @Override
    public List<AiKnowledgeBase> selectAiKnowledgeBaseList(AiKnowledgeBase query) {
        return selectList(query);
    }

    @Override
    public int insertAiKnowledgeBase(AiKnowledgeBase entity) {
        return insert(entity);
    }

    @Override
    public int updateAiKnowledgeBase(AiKnowledgeBase entity) {
        return update(entity);
    }

    @Override
    public int deleteAiKnowledgeBaseById(Long id) {
        return deleteById(id);
    }

    @Override
    public int deleteAiKnowledgeBaseByIds(Long[] ids) {
        return deleteByIds(ids);
    }
}
