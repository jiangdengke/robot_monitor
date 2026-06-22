package org.jdk.project.service.configquery

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.config.ComplaintDto
import org.jooq.DSLContext
import org.jooq.generated.project.Tables.COMPLAINT_RECORD
import org.jooq.generated.project.Tables.LOUNGE
import org.springframework.stereotype.Service

@Service
class ComplaintConfigQueryService(
    private val dsl: DSLContext,
    private val mapper: ConfigQueryMapper,
) {
    fun listComplaints(): ListResponse<ComplaintDto> {
        val rows: List<ComplaintDto> =
            dsl.select(
                COMPLAINT_RECORD.ID,
                COMPLAINT_RECORD.PASSENGER_NAME,
                LOUNGE.CODE,
                ConfigQueryMapper.LOUNGE_NAME,
                COMPLAINT_RECORD.CARD_PROVIDER,
                COMPLAINT_RECORD.CARD_NO,
                COMPLAINT_RECORD.CONTENT,
                COMPLAINT_RECORD.FEEDBACK,
                COMPLAINT_RECORD.CREATED_AT,
            ).from(COMPLAINT_RECORD)
                .leftJoin(LOUNGE).on(COMPLAINT_RECORD.LOUNGE_ID.eq(LOUNGE.ID))
                .orderBy(COMPLAINT_RECORD.ID.desc())
                .fetch { record -> mapper.toComplaintDto(record) }
        return ListResponse.of(rows.size.toLong(), rows)
    }
}
