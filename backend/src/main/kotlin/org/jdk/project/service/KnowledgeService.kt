package org.jdk.project.service

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.knowledge.KnowledgeUpsertRequest
import org.jdk.project.exception.BusinessException
import org.jooq.DSLContext
import org.jooq.generated.project.Tables.KNOWLEDGE_BASE
import org.jooq.generated.project.tables.pojos.KnowledgeBase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class KnowledgeService(
    private val dsl: DSLContext,
) {
    fun listKnowledge(): ListResponse<KnowledgeBase> {
        val rows = dsl.selectFrom(KNOWLEDGE_BASE).orderBy(KNOWLEDGE_BASE.ID.desc()).fetchInto(KnowledgeBase::class.java)
        return ListResponse.of(rows.size.toLong(), rows)
    }

    fun getKnowledge(id: Long): KnowledgeBase {
        return dsl.selectFrom(KNOWLEDGE_BASE)
            .where(KNOWLEDGE_BASE.ID.eq(id))
            .fetchOneInto(KnowledgeBase::class.java)
            ?: throw BusinessException("知识库记录不存在")
    }

    @Transactional
    fun createKnowledge(request: KnowledgeUpsertRequest): Long? =
        dsl.insertInto(KNOWLEDGE_BASE)
            .set(KNOWLEDGE_BASE.TITLE, request.title)
            .set(KNOWLEDGE_BASE.CONTENT, request.content)
            .set(KNOWLEDGE_BASE.SOURCE, request.source)
            .set(KNOWLEDGE_BASE.KNOWLEDGE_TYPE, defaultString(request.knowledgeType, "FAQ"))
            .set(KNOWLEDGE_BASE.PROCESS_STATUS, defaultString(request.processStatus, "PENDING"))
            .set(KNOWLEDGE_BASE.ENABLED, request.enabled ?: true)
            .set(KNOWLEDGE_BASE.VECTOR_REF, defaultString(request.vectorRef, ""))
            .set(KNOWLEDGE_BASE.CREATED_BY, request.createdBy)
            .set(KNOWLEDGE_BASE.REMARK, defaultString(request.remark, ""))
            .returningResult(KNOWLEDGE_BASE.ID)
            .fetchOne(KNOWLEDGE_BASE.ID)

    @Transactional
    fun updateKnowledge(id: Long, request: KnowledgeUpsertRequest) {
        ensureUpdated(
            dsl.update(KNOWLEDGE_BASE)
                .set(KNOWLEDGE_BASE.TITLE, request.title)
                .set(KNOWLEDGE_BASE.CONTENT, request.content)
                .set(KNOWLEDGE_BASE.SOURCE, request.source)
                .set(KNOWLEDGE_BASE.KNOWLEDGE_TYPE, defaultString(request.knowledgeType, "FAQ"))
                .set(KNOWLEDGE_BASE.PROCESS_STATUS, defaultString(request.processStatus, "PENDING"))
                .set(KNOWLEDGE_BASE.ENABLED, request.enabled ?: true)
                .set(KNOWLEDGE_BASE.VECTOR_REF, defaultString(request.vectorRef, ""))
                .set(KNOWLEDGE_BASE.CREATED_BY, request.createdBy)
                .set(KNOWLEDGE_BASE.REMARK, defaultString(request.remark, ""))
                .where(KNOWLEDGE_BASE.ID.eq(id))
                .execute(),
            "知识库记录不存在",
        )
    }

    @Transactional
    fun deleteKnowledge(id: Long) {
        dsl.deleteFrom(KNOWLEDGE_BASE).where(KNOWLEDGE_BASE.ID.eq(id)).execute()
    }

    private fun ensureUpdated(updated: Int, message: String) {
        if (updated == 0) throw BusinessException(message)
    }

    private fun defaultString(value: String?, fallback: String): String = value ?: fallback
}
