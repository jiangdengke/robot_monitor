package org.jdk.project.service.configquery

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.config.LoungeDto
import org.jooq.DSLContext
import org.jooq.generated.project.Tables.LOUNGE
import org.springframework.stereotype.Service

@Service
class LoungeConfigQueryService(
    private val dsl: DSLContext,
    private val mapper: ConfigQueryMapper,
) {
    fun listLounges(): ListResponse<LoungeDto> {
        val rows: List<LoungeDto> = dsl.selectFrom(LOUNGE).orderBy(LOUNGE.ID.asc()).fetch { record -> mapper.toLoungeDto(record) }
        return ListResponse.of(rows.size.toLong(), rows)
    }
}
