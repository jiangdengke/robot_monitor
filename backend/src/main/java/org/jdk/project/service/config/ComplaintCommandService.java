package org.jdk.project.service.config;

import static org.jdk.project.service.config.ConfigCommandSupport.defaultString;
import static org.jdk.project.service.config.ConfigCommandSupport.ensureUpdated;
import static org.jooq.generated.project.Tables.COMPLAINT_RECORD;

import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.config.ComplaintUpsertRequest;
import org.jooq.DSLContext;
import org.jooq.generated.project.tables.pojos.ComplaintRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ComplaintCommandService {

  private final DSLContext dsl;

  @Transactional
  public Long create(ComplaintUpsertRequest request) {
    ComplaintRecord complaint = new ComplaintRecord();
    complaint.setLoungeId(request.getLoungeId());
    complaint.setPassengerName(request.getUserName());
    complaint.setCardProvider(defaultString(request.getCardService(), ""));
    complaint.setCardNo(defaultString(request.getCardNo(), ""));
    complaint.setContent(defaultString(request.getComplaintContent(), ""));
    complaint.setFeedback(defaultString(request.getComplaintFeedback(), ""));
    return dsl.insertInto(COMPLAINT_RECORD)
        .set(dsl.newRecord(COMPLAINT_RECORD, complaint))
        .returningResult(COMPLAINT_RECORD.ID)
        .fetchOne(COMPLAINT_RECORD.ID);
  }

  @Transactional
  public void update(Long id, ComplaintUpsertRequest request) {
    ensureUpdated(
        dsl.update(COMPLAINT_RECORD)
            .set(COMPLAINT_RECORD.LOUNGE_ID, request.getLoungeId())
            .set(COMPLAINT_RECORD.PASSENGER_NAME, request.getUserName())
            .set(COMPLAINT_RECORD.CARD_PROVIDER, defaultString(request.getCardService(), ""))
            .set(COMPLAINT_RECORD.CARD_NO, defaultString(request.getCardNo(), ""))
            .set(COMPLAINT_RECORD.CONTENT, defaultString(request.getComplaintContent(), ""))
            .set(COMPLAINT_RECORD.FEEDBACK, defaultString(request.getComplaintFeedback(), ""))
            .where(COMPLAINT_RECORD.ID.eq(id))
            .execute(),
        "投诉记录不存在");
  }

  @Transactional
  public void delete(Long id) {
    dsl.deleteFrom(COMPLAINT_RECORD).where(COMPLAINT_RECORD.ID.eq(id)).execute();
  }
}
