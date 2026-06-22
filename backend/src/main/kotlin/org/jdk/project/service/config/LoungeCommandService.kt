package org.jdk.project.service.config

import org.jdk.project.dto.config.LoungeUpsertRequest
import org.jdk.project.service.config.ConfigCommandSupport.defaultString
import org.jdk.project.service.config.ConfigCommandSupport.ensureUpdated
import org.jooq.DSLContext
import org.jooq.generated.project.Tables.LOUNGE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LoungeCommandService(
    private val dsl: DSLContext,
) {
    @Transactional
    fun create(request: LoungeUpsertRequest): Long? =
        dsl.insertInto(LOUNGE)
            .set(LOUNGE.CODE, request.roomCode)
            .set(LOUNGE.NAME, request.deptName)
            .set(LOUNGE.TERMINAL, defaultString(request.terminal, ""))
            .set(LOUNGE.LOCATION_DESC, defaultString(request.locationDesc, ""))
            .set(LOUNGE.ENABLED, request.enabled ?: true)
            .set(LOUNGE.REMARK, defaultString(request.remark, ""))
            .returningResult(LOUNGE.ID)
            .fetchOne(LOUNGE.ID)

    @Transactional
    fun update(id: Long, request: LoungeUpsertRequest) {
        val updated =
            dsl.update(LOUNGE)
                .set(LOUNGE.CODE, request.roomCode)
                .set(LOUNGE.NAME, request.deptName)
                .set(LOUNGE.TERMINAL, defaultString(request.terminal, ""))
                .set(LOUNGE.LOCATION_DESC, defaultString(request.locationDesc, ""))
                .set(LOUNGE.ENABLED, request.enabled ?: true)
                .set(LOUNGE.REMARK, defaultString(request.remark, ""))
                .where(LOUNGE.ID.eq(id))
                .execute()
        ensureUpdated(updated, "贵宾室不存在")
    }

    @Transactional
    fun delete(id: Long) {
        dsl.deleteFrom(LOUNGE).where(LOUNGE.ID.eq(id)).execute()
    }
}
