package org.jdk.project.service.configquery

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.config.AreaDetailDto
import org.jdk.project.dto.config.AreaDto
import org.jdk.project.dto.config.RegionDto
import org.jooq.DSLContext
import org.jooq.generated.project.Tables.AREA
import org.jooq.generated.project.Tables.AREA_I18N
import org.jooq.generated.project.Tables.LOUNGE
import org.jooq.generated.project.Tables.REGION
import org.springframework.stereotype.Service

@Service
class AreaConfigQueryService(
    private val dsl: DSLContext,
    private val mapper: ConfigQueryMapper,
) {
    fun listRegions(): ListResponse<RegionDto> {
        val rows: List<RegionDto> =
            dsl.select(
                REGION.ID,
                REGION.NAME,
                REGION.AREA_ID,
                ConfigQueryMapper.AREA_NAME,
                LOUNGE.CODE,
                ConfigQueryMapper.LOUNGE_NAME,
                REGION.COORDINATE,
                REGION.MAX_CAPACITY,
                REGION.GUIDE_ENABLED,
                REGION.VISIBLE,
                REGION.ENABLED,
                REGION.REMARK,
            ).from(REGION)
                .join(LOUNGE).on(REGION.LOUNGE_ID.eq(LOUNGE.ID))
                .leftJoin(AREA).on(REGION.AREA_ID.eq(AREA.ID))
                .orderBy(REGION.ID.asc())
                .fetch { record -> mapper.toRegionDto(record) }
        return ListResponse.of(rows.size.toLong(), rows)
    }

    fun listAreas(): ListResponse<AreaDto> {
        val rows: List<AreaDto> =
            dsl.select(
                AREA.ID,
                AREA.NAME,
                LOUNGE.CODE,
                ConfigQueryMapper.LOUNGE_NAME,
                AREA.COORDINATE,
                AREA.MAX_CAPACITY,
                AREA.GUIDE_ENABLED,
                AREA.VISIBLE,
                AREA.ENABLED,
                AREA.REMARK,
            ).from(AREA)
                .join(LOUNGE).on(AREA.LOUNGE_ID.eq(LOUNGE.ID))
                .orderBy(AREA.ID.asc())
                .fetch { record -> mapper.toAreaDto(record, loadAreaDetails(record.get(AREA.ID))) }
        return ListResponse.of(rows.size.toLong(), rows)
    }

    private fun loadAreaDetails(areaId: Long?): List<AreaDetailDto> =
        dsl.selectFrom(AREA_I18N)
            .where(AREA_I18N.AREA_ID.eq(areaId))
            .orderBy(AREA_I18N.ID.asc())
            .fetch { record ->
                AreaDetailDto(
                    id = record.get(AREA_I18N.ID),
                    languageType = record.get(AREA_I18N.LANGUAGE_CODE),
                    areaName = record.get(AREA_I18N.DISPLAY_NAME),
                    label = record.get(AREA_I18N.LABEL_TEXT),
                    arrText = record.get(AREA_I18N.ARRIVAL_TEXT),
                    remark = record.get(AREA_I18N.SPEECH_TEXT),
                )
            }
}
