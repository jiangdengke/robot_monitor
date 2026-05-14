package org.jdk.project.service;

import static org.jooq.generated.project.Tables.KNOWLEDGE_BASE;
import static org.jooq.generated.project.Tables.LOGIN_LOG;
import static org.jooq.generated.project.Tables.OPERATION_LOG;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.knowledge.KnowledgeUpsertRequest;
import org.jdk.project.exception.BusinessException;
import org.jooq.DSLContext;
import org.jooq.generated.project.tables.pojos.KnowledgeBase;
import org.jooq.generated.project.tables.pojos.LoginLog;
import org.jooq.generated.project.tables.pojos.OperationLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MonitorService {

  private final DSLContext dsl;

  public ListResponse<LoginLog> listLoginLogs() {
    List<LoginLog> rows =
        dsl.selectFrom(LOGIN_LOG).orderBy(LOGIN_LOG.ID.desc()).fetchInto(LoginLog.class);
    return ListResponse.of(rows.size(), rows);
  }

  public ListResponse<OperationLog> listOperationLogs() {
    List<OperationLog> rows =
        dsl.selectFrom(OPERATION_LOG).orderBy(OPERATION_LOG.ID.desc()).fetchInto(OperationLog.class);
    return ListResponse.of(rows.size(), rows);
  }

  public ListResponse<KnowledgeBase> listKnowledge() {
    List<KnowledgeBase> rows =
        dsl.selectFrom(KNOWLEDGE_BASE).orderBy(KNOWLEDGE_BASE.ID.desc()).fetchInto(KnowledgeBase.class);
    return ListResponse.of(rows.size(), rows);
  }

  @Transactional
  public Long createKnowledge(KnowledgeUpsertRequest request) {
    KnowledgeBase entity = new KnowledgeBase();
    entity.setTitle(request.getTitle());
    entity.setContent(request.getContent());
    entity.setSource(request.getSource());
    entity.setKnowledgeType(defaultString(request.getKnowledgeType(), "FAQ"));
    entity.setProcessStatus(defaultString(request.getProcessStatus(), "PENDING"));
    entity.setEnabled(request.getEnabled() == null || request.getEnabled());
    entity.setVectorRef(defaultString(request.getVectorRef(), ""));
    entity.setCreatedBy(request.getCreatedBy());
    entity.setRemark(defaultString(request.getRemark(), ""));
    return dsl.insertInto(KNOWLEDGE_BASE)
        .set(dsl.newRecord(KNOWLEDGE_BASE, entity))
        .returningResult(KNOWLEDGE_BASE.ID)
        .fetchOne(KNOWLEDGE_BASE.ID);
  }

  @Transactional
  public void updateKnowledge(Long id, KnowledgeUpsertRequest request) {
    ensureUpdated(
        dsl.update(KNOWLEDGE_BASE)
            .set(KNOWLEDGE_BASE.TITLE, request.getTitle())
            .set(KNOWLEDGE_BASE.CONTENT, request.getContent())
            .set(KNOWLEDGE_BASE.SOURCE, request.getSource())
            .set(KNOWLEDGE_BASE.KNOWLEDGE_TYPE, defaultString(request.getKnowledgeType(), "FAQ"))
            .set(KNOWLEDGE_BASE.PROCESS_STATUS, defaultString(request.getProcessStatus(), "PENDING"))
            .set(KNOWLEDGE_BASE.ENABLED, request.getEnabled() == null || request.getEnabled())
            .set(KNOWLEDGE_BASE.VECTOR_REF, defaultString(request.getVectorRef(), ""))
            .set(KNOWLEDGE_BASE.CREATED_BY, request.getCreatedBy())
            .set(KNOWLEDGE_BASE.REMARK, defaultString(request.getRemark(), ""))
            .where(KNOWLEDGE_BASE.ID.eq(id))
            .execute(),
        "知识库记录不存在");
  }

  @Transactional
  public void deleteKnowledge(Long id) {
    dsl.deleteFrom(KNOWLEDGE_BASE).where(KNOWLEDGE_BASE.ID.eq(id)).execute();
  }

  private void ensureUpdated(int updated, String message) {
    if (updated == 0) throw new BusinessException(message);
  }

  private String defaultString(String value, String fallback) {
    return value == null ? fallback : value;
  }
}
