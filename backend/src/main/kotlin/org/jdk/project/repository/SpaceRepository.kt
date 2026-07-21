package org.jdk.project.repository

import org.jooq.DSLContext
import org.jooq.generated.project.Tables.AREA
import org.jooq.generated.project.Tables.POINT
import org.jooq.generated.project.Tables.SITE
import org.springframework.stereotype.Repository

@Repository
class SpaceRepository(
    private val dsl: DSLContext,
) {
    fun siteExists(id: Long): Boolean = dsl.fetchExists(SITE, SITE.ID.eq(id))

    fun findAreaSiteIdById(id: Long): Long? =
        dsl
            .select(AREA.SITE_ID)
            .from(AREA)
            .where(AREA.ID.eq(id))
            .fetchOne(AREA.SITE_ID)

    fun findPointSiteIdById(id: Long): Long? =
        dsl
            .select(POINT.SITE_ID)
            .from(POINT)
            .where(POINT.ID.eq(id))
            .fetchOne(POINT.SITE_ID)

    fun hasAreasBySiteId(siteId: Long): Boolean = dsl.fetchExists(AREA, AREA.SITE_ID.eq(siteId))

    fun hasPointsBySiteId(siteId: Long): Boolean = dsl.fetchExists(POINT, POINT.SITE_ID.eq(siteId))

    fun hasPointsByAreaId(areaId: Long): Boolean = dsl.fetchExists(POINT, POINT.AREA_ID.eq(areaId))

    fun findAllSites(): List<SiteRow> =
        dsl
            .selectFrom(SITE)
            .orderBy(SITE.ID.asc())
            .fetch { record ->
                SiteRow(
                    id = record.id,
                    siteCode = record.code,
                    siteName = record.name,
                    locationDesc = record.locationDesc,
                    enabled = record.enabled,
                    remark = record.remark,
                )
            }

    fun insertSite(site: SiteWriteData): Long? =
        dsl
            .insertInto(SITE)
            .set(SITE.CODE, site.siteCode)
            .set(SITE.NAME, site.siteName)
            .set(SITE.LOCATION_DESC, site.locationDesc)
            .set(SITE.ENABLED, site.enabled)
            .set(SITE.REMARK, site.remark)
            .returningResult(SITE.ID)
            .fetchOne(SITE.ID)

    fun updateSite(
        id: Long,
        site: SiteWriteData,
    ): Int =
        dsl
            .update(SITE)
            .set(SITE.CODE, site.siteCode)
            .set(SITE.NAME, site.siteName)
            .set(SITE.LOCATION_DESC, site.locationDesc)
            .set(SITE.ENABLED, site.enabled)
            .set(SITE.REMARK, site.remark)
            .where(SITE.ID.eq(id))
            .execute()

    fun deleteSiteById(id: Long): Int = dsl.deleteFrom(SITE).where(SITE.ID.eq(id)).execute()

    fun findAllAreas(): List<AreaRow> =
        dsl
            .select(
                AREA.ID,
                AREA.SITE_ID,
                SITE.CODE,
                SITE.NAME,
                AREA.NAME,
                AREA.COORDINATE,
                AREA.MAX_CAPACITY,
                AREA.VISIBLE,
                AREA.ENABLED,
                AREA.REMARK,
            ).from(AREA)
            .join(SITE)
            .on(AREA.SITE_ID.eq(SITE.ID))
            .orderBy(AREA.ID.asc())
            .fetch { record ->
                AreaRow(
                    id = record.get(AREA.ID),
                    siteId = record.get(AREA.SITE_ID),
                    siteCode = record.get(SITE.CODE),
                    siteName = record.get(SITE.NAME),
                    areaName = record.get(AREA.NAME),
                    coordinate = record.get(AREA.COORDINATE),
                    maxCapacity = record.get(AREA.MAX_CAPACITY),
                    visible = record.get(AREA.VISIBLE),
                    enabled = record.get(AREA.ENABLED),
                    remark = record.get(AREA.REMARK),
                )
            }

    fun insertArea(area: AreaWriteData): Long? =
        dsl
            .insertInto(AREA)
            .set(AREA.SITE_ID, area.siteId)
            .set(AREA.NAME, area.areaName)
            .set(AREA.COORDINATE, area.coordinate)
            .set(AREA.MAX_CAPACITY, area.maxCapacity)
            .set(AREA.VISIBLE, area.visible)
            .set(AREA.ENABLED, area.enabled)
            .set(AREA.REMARK, area.remark)
            .returningResult(AREA.ID)
            .fetchOne(AREA.ID)

    fun updateArea(
        id: Long,
        area: AreaWriteData,
    ): Int =
        dsl
            .update(AREA)
            .set(AREA.SITE_ID, area.siteId)
            .set(AREA.NAME, area.areaName)
            .set(AREA.COORDINATE, area.coordinate)
            .set(AREA.MAX_CAPACITY, area.maxCapacity)
            .set(AREA.VISIBLE, area.visible)
            .set(AREA.ENABLED, area.enabled)
            .set(AREA.REMARK, area.remark)
            .where(AREA.ID.eq(id))
            .execute()

    fun deleteAreaById(id: Long): Int = dsl.deleteFrom(AREA).where(AREA.ID.eq(id)).execute()

    fun findAllPoints(): List<PointRow> =
        dsl
            .select(
                POINT.ID,
                POINT.SITE_ID,
                SITE.CODE,
                SITE.NAME,
                POINT.AREA_ID,
                AREA.NAME,
                POINT.NAME,
                POINT.COORDINATE,
                POINT.MAX_CAPACITY,
                POINT.VISIBLE,
                POINT.ENABLED,
                POINT.REMARK,
            ).from(POINT)
            .join(SITE)
            .on(POINT.SITE_ID.eq(SITE.ID))
            .leftJoin(AREA)
            .on(POINT.AREA_ID.eq(AREA.ID))
            .orderBy(POINT.ID.asc())
            .fetch { record ->
                PointRow(
                    id = record.get(POINT.ID),
                    siteId = record.get(POINT.SITE_ID),
                    siteCode = record.get(SITE.CODE),
                    siteName = record.get(SITE.NAME),
                    areaId = record.get(POINT.AREA_ID),
                    areaName = record.get(AREA.NAME),
                    pointName = record.get(POINT.NAME),
                    coordinate = record.get(POINT.COORDINATE),
                    maxCapacity = record.get(POINT.MAX_CAPACITY),
                    visible = record.get(POINT.VISIBLE),
                    enabled = record.get(POINT.ENABLED),
                    remark = record.get(POINT.REMARK),
                )
            }

    fun insertPoint(point: PointWriteData): Long? =
        dsl
            .insertInto(POINT)
            .set(POINT.SITE_ID, point.siteId)
            .set(POINT.AREA_ID, point.areaId)
            .set(POINT.NAME, point.pointName)
            .set(POINT.COORDINATE, point.coordinate)
            .set(POINT.MAX_CAPACITY, point.maxCapacity)
            .set(POINT.VISIBLE, point.visible)
            .set(POINT.ENABLED, point.enabled)
            .set(POINT.REMARK, point.remark)
            .returningResult(POINT.ID)
            .fetchOne(POINT.ID)

    fun updatePoint(
        id: Long,
        point: PointWriteData,
    ): Int =
        dsl
            .update(POINT)
            .set(POINT.SITE_ID, point.siteId)
            .set(POINT.AREA_ID, point.areaId)
            .set(POINT.NAME, point.pointName)
            .set(POINT.COORDINATE, point.coordinate)
            .set(POINT.MAX_CAPACITY, point.maxCapacity)
            .set(POINT.VISIBLE, point.visible)
            .set(POINT.ENABLED, point.enabled)
            .set(POINT.REMARK, point.remark)
            .where(POINT.ID.eq(id))
            .execute()

    fun deletePointById(id: Long): Int = dsl.deleteFrom(POINT).where(POINT.ID.eq(id)).execute()
}

data class SiteRow(
    val id: Long?,
    val siteCode: String?,
    val siteName: String?,
    val locationDesc: String?,
    val enabled: Boolean?,
    val remark: String?,
)

data class SiteWriteData(
    val siteCode: String?,
    val siteName: String?,
    val locationDesc: String,
    val enabled: Boolean,
    val remark: String,
)

data class AreaRow(
    val id: Long?,
    val siteId: Long?,
    val siteCode: String?,
    val siteName: String?,
    val areaName: String?,
    val coordinate: String?,
    val maxCapacity: Int?,
    val visible: Boolean?,
    val enabled: Boolean?,
    val remark: String?,
)

data class AreaWriteData(
    val siteId: Long,
    val areaName: String?,
    val coordinate: String,
    val maxCapacity: Int,
    val visible: Boolean,
    val enabled: Boolean,
    val remark: String,
)

data class PointRow(
    val id: Long?,
    val siteId: Long?,
    val siteCode: String?,
    val siteName: String?,
    val areaId: Long?,
    val areaName: String?,
    val pointName: String?,
    val coordinate: String?,
    val maxCapacity: Int?,
    val visible: Boolean?,
    val enabled: Boolean?,
    val remark: String?,
)

data class PointWriteData(
    val siteId: Long,
    val areaId: Long?,
    val pointName: String?,
    val coordinate: String,
    val maxCapacity: Int,
    val visible: Boolean,
    val enabled: Boolean,
    val remark: String,
)
