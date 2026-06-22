package org.jdk.project.service.config

import org.jdk.project.dto.config.ComplaintUpsertRequest
import org.jdk.project.service.config.ConfigCommandSupport.defaultString
import org.jdk.project.service.config.ConfigCommandSupport.ensureUpdated
import org.jooq.DSLContext
import org.jooq.generated.project.Tables.COMPLAINT_RECORD
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ComplaintCommandService(
    private val dsl: DSLContext,
) {
    @Transactional
    fun create(request: ComplaintUpsertRequest): Long? =
        dsl.insertInto(COMPLAINT_RECORD)
            .set(COMPLAINT_RECORD.LOUNGE_ID, request.loungeId)
            .set(COMPLAINT_RECORD.PASSENGER_NAME, request.userName)
            .set(COMPLAINT_RECORD.CARD_PROVIDER, defaultString(request.cardService, ""))
            .set(COMPLAINT_RECORD.CARD_NO, defaultString(request.cardNo, ""))
            .set(COMPLAINT_RECORD.CONTENT, defaultString(request.complaintContent, ""))
            .set(COMPLAINT_RECORD.FEEDBACK, defaultString(request.complaintFeedback, ""))
            .returningResult(COMPLAINT_RECORD.ID)
            .fetchOne(COMPLAINT_RECORD.ID)

    @Transactional
    fun update(id: Long, request: ComplaintUpsertRequest) {
        ensureUpdated(
            dsl.update(COMPLAINT_RECORD)
                .set(COMPLAINT_RECORD.LOUNGE_ID, request.loungeId)
                .set(COMPLAINT_RECORD.PASSENGER_NAME, request.userName)
                .set(COMPLAINT_RECORD.CARD_PROVIDER, defaultString(request.cardService, ""))
                .set(COMPLAINT_RECORD.CARD_NO, defaultString(request.cardNo, ""))
                .set(COMPLAINT_RECORD.CONTENT, defaultString(request.complaintContent, ""))
                .set(COMPLAINT_RECORD.FEEDBACK, defaultString(request.complaintFeedback, ""))
                .where(COMPLAINT_RECORD.ID.eq(id))
                .execute(),
            "投诉记录不存在",
        )
    }

    @Transactional
    fun delete(id: Long) {
        dsl.deleteFrom(COMPLAINT_RECORD).where(COMPLAINT_RECORD.ID.eq(id)).execute()
    }
}
