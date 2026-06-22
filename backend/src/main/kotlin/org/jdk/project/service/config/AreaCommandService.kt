package org.jdk.project.service.config

import org.jdk.project.dto.config.AreaDetailUpsertRequest
import org.jdk.project.dto.config.AreaUpsertRequest
import org.jdk.project.dto.config.RegionUpsertRequest
import org.jdk.project.service.config.ConfigCommandSupport.defaultInt
import org.jdk.project.service.config.ConfigCommandSupport.defaultString
import org.jdk.project.service.config.ConfigCommandSupport.ensureUpdated
import org.jdk.project.service.config.ConfigCommandSupport.requiredId
import org.jooq.DSLContext
import org.jooq.generated.project.Tables.AREA
import org.jooq.generated.project.Tables.AREA_I18N
import org.jooq.generated.project.Tables.REGION
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AreaCommandService(
    private val dsl: DSLContext,
) {
    @Transactional
    fun createRegion(request: RegionUpsertRequest): Long? =
        dsl.insertInto(REGION)
            .set(REGION.LOUNGE_ID, requiredId(request.loungeId, "贵宾室不能为空"))
            .set(REGION.AREA_ID, request.areaId)
            .set(REGION.NAME, request.regionName)
            .set(REGION.COORDINATE, defaultString(request.coordinate, ""))
            .set(REGION.MAX_CAPACITY, defaultInt(request.maxCapacity, 0))
            .set(REGION.GUIDE_ENABLED, request.isGuide == "1")
            .set(REGION.VISIBLE, request.isShow != "0")
            .set(REGION.ENABLED, request.enable == null || request.enable == 1)
            .set(REGION.REMARK, defaultString(request.remark, ""))
            .returningResult(REGION.ID)
            .fetchOne(REGION.ID)

    @Transactional
    fun updateRegion(id: Long, request: RegionUpsertRequest) {
        val updated =
            dsl.update(REGION)
                .set(REGION.LOUNGE_ID, requiredId(request.loungeId, "贵宾室不能为空"))
                .set(REGION.AREA_ID, request.areaId)
                .set(REGION.NAME, request.regionName)
                .set(REGION.COORDINATE, defaultString(request.coordinate, ""))
                .set(REGION.MAX_CAPACITY, defaultInt(request.maxCapacity, 0))
                .set(REGION.GUIDE_ENABLED, request.isGuide == "1")
                .set(REGION.VISIBLE, request.isShow != "0")
                .set(REGION.ENABLED, request.enable == null || request.enable == 1)
                .set(REGION.REMARK, defaultString(request.remark, ""))
                .where(REGION.ID.eq(id))
                .execute()
        ensureUpdated(updated, "区域不存在")
    }

    @Transactional
    fun deleteRegion(id: Long) {
        dsl.deleteFrom(REGION).where(REGION.ID.eq(id)).execute()
    }

    @Transactional
    fun createArea(request: AreaUpsertRequest): Long? {
        val areaId =
            dsl.insertInto(AREA)
                .set(AREA.LOUNGE_ID, requiredId(request.loungeId, "贵宾室不能为空"))
                .set(AREA.NAME, request.areaName)
                .set(AREA.COORDINATE, defaultString(request.coordinate, ""))
                .set(AREA.MAX_CAPACITY, defaultInt(request.maxCapacity, 0))
                .set(AREA.GUIDE_ENABLED, request.isGuide == "1")
                .set(AREA.VISIBLE, request.isShow != "0")
                .set(AREA.ENABLED, request.enable == null || request.enable == 1)
                .set(AREA.REMARK, defaultString(request.remark, ""))
                .returningResult(AREA.ID)
                .fetchOne(AREA.ID)
        replaceAreaDetails(areaId, request.configAreaDetailList)
        return areaId
    }

    @Transactional
    fun updateArea(id: Long, request: AreaUpsertRequest) {
        val updated =
            dsl.update(AREA)
                .set(AREA.LOUNGE_ID, requiredId(request.loungeId, "贵宾室不能为空"))
                .set(AREA.NAME, request.areaName)
                .set(AREA.COORDINATE, defaultString(request.coordinate, ""))
                .set(AREA.MAX_CAPACITY, defaultInt(request.maxCapacity, 0))
                .set(AREA.GUIDE_ENABLED, request.isGuide == "1")
                .set(AREA.VISIBLE, request.isShow != "0")
                .set(AREA.ENABLED, request.enable == null || request.enable == 1)
                .set(AREA.REMARK, defaultString(request.remark, ""))
                .where(AREA.ID.eq(id))
                .execute()
        ensureUpdated(updated, "功能区不存在")
        replaceAreaDetails(id, request.configAreaDetailList)
    }

    @Transactional
    fun deleteArea(id: Long) {
        dsl.deleteFrom(AREA_I18N).where(AREA_I18N.AREA_ID.eq(id)).execute()
        dsl.deleteFrom(AREA).where(AREA.ID.eq(id)).execute()
    }

    private fun replaceAreaDetails(areaId: Long?, details: List<AreaDetailUpsertRequest>?) {
        if (areaId == null) {
            return
        }
        dsl.deleteFrom(AREA_I18N).where(AREA_I18N.AREA_ID.eq(areaId)).execute()
        if (details.isNullOrEmpty()) {
            return
        }
        details.forEach { detail ->
            dsl.insertInto(AREA_I18N)
                .set(AREA_I18N.AREA_ID, areaId)
                .set(AREA_I18N.LANGUAGE_CODE, defaultString(detail.languageType, "CN"))
                .set(AREA_I18N.DISPLAY_NAME, defaultString(detail.areaName, ""))
                .set(AREA_I18N.LABEL_TEXT, defaultString(detail.label, ""))
                .set(AREA_I18N.ARRIVAL_TEXT, defaultString(detail.arrText, ""))
                .set(AREA_I18N.SPEECH_TEXT, defaultString(detail.remark, ""))
                .execute()
        }
    }
}
