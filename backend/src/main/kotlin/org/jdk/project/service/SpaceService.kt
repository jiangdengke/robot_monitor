package org.jdk.project.service

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.config.AreaDto
import org.jdk.project.dto.config.AreaUpsertRequest
import org.jdk.project.dto.config.PointDto
import org.jdk.project.dto.config.PointUpsertRequest
import org.jdk.project.dto.config.SiteDto
import org.jdk.project.dto.config.SiteUpsertRequest
import org.jdk.project.exception.BusinessException
import org.jdk.project.repository.AreaRow
import org.jdk.project.repository.AreaWriteData
import org.jdk.project.repository.DeviceRepository
import org.jdk.project.repository.PointRow
import org.jdk.project.repository.PointWriteData
import org.jdk.project.repository.RobotRepository
import org.jdk.project.repository.SiteRow
import org.jdk.project.repository.SiteWriteData
import org.jdk.project.repository.SpaceRepository
import org.jdk.project.repository.TaskRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SpaceService(
    private val spaceRepository: SpaceRepository,
    private val deviceRepository: DeviceRepository,
    private val robotRepository: RobotRepository,
    private val taskRepository: TaskRepository,
) {
    fun listSites(): ListResponse<SiteDto> {
        val rows = spaceRepository.findAllSites().map(::toSiteDto)
        return ListResponse.of(rows.size.toLong(), rows)
    }

    @Transactional
    fun createSite(request: SiteUpsertRequest): Long? =
        spaceRepository.insertSite(
            SiteWriteData(
                siteCode = request.siteCode,
                siteName = request.siteName,
                locationDesc = ServiceSupport.defaultString(request.locationDesc, ""),
                enabled = request.enabled ?: true,
                remark = ServiceSupport.defaultString(request.remark, ""),
            ),
        )

    @Transactional
    fun updateSite(
        id: Long,
        request: SiteUpsertRequest,
    ) {
        ServiceSupport.ensureUpdated(
            spaceRepository.updateSite(
                id,
                SiteWriteData(
                    siteCode = request.siteCode,
                    siteName = request.siteName,
                    locationDesc = ServiceSupport.defaultString(request.locationDesc, ""),
                    enabled = request.enabled ?: true,
                    remark = ServiceSupport.defaultString(request.remark, ""),
                ),
            ),
            "场地不存在",
        )
    }

    @Transactional
    fun deleteSite(id: Long) {
        if (hasSiteDependencies(id)) {
            throw BusinessException("场地下存在关联数据，无法删除")
        }
        spaceRepository.deleteSiteById(id)
    }

    fun listAreas(): ListResponse<AreaDto> {
        val rows = spaceRepository.findAllAreas().map(::toAreaDto)
        return ListResponse.of(rows.size.toLong(), rows)
    }

    @Transactional
    fun createArea(request: AreaUpsertRequest): Long? {
        val area = request.toAreaWriteData()
        ensureSiteExists(area.siteId)
        return spaceRepository.insertArea(area)
    }

    @Transactional
    fun updateArea(
        id: Long,
        request: AreaUpsertRequest,
    ) {
        val area = request.toAreaWriteData()
        ensureSiteExists(area.siteId)
        val currentSiteId = spaceRepository.findAreaSiteIdById(id)
        if (currentSiteId != null && currentSiteId != area.siteId && spaceRepository.hasPointsByAreaId(id)) {
            throw BusinessException("区域下存在点位，无法更换场地")
        }
        ServiceSupport.ensureUpdated(spaceRepository.updateArea(id, area), "区域不存在")
    }

    @Transactional
    fun deleteArea(id: Long) {
        if (spaceRepository.hasPointsByAreaId(id)) {
            throw BusinessException("区域下存在点位，无法删除")
        }
        spaceRepository.deleteAreaById(id)
    }

    fun listPoints(): ListResponse<PointDto> {
        val rows = spaceRepository.findAllPoints().map(::toPointDto)
        return ListResponse.of(rows.size.toLong(), rows)
    }

    @Transactional
    fun createPoint(request: PointUpsertRequest): Long? {
        val point = request.toPointWriteData()
        validatePointLocation(point)
        return spaceRepository.insertPoint(point)
    }

    @Transactional
    fun updatePoint(
        id: Long,
        request: PointUpsertRequest,
    ) {
        val point = request.toPointWriteData()
        validatePointLocation(point)
        val currentSiteId = spaceRepository.findPointSiteIdById(id)
        val hasBindings = deviceRepository.hasBindingsByPointId(id) || robotRepository.hasRobotsByPointId(id)
        if (currentSiteId != null && currentSiteId != point.siteId && hasBindings) {
            throw BusinessException("点位已绑定设备或机器人，无法更换场地")
        }
        ServiceSupport.ensureUpdated(spaceRepository.updatePoint(id, point), "点位不存在")
    }

    @Transactional
    fun deletePoint(id: Long) {
        if (deviceRepository.hasBindingsByPointId(id) || robotRepository.hasRobotsByPointId(id)) {
            throw BusinessException("点位已绑定设备或机器人，无法删除")
        }
        spaceRepository.deletePointById(id)
    }

    private fun hasSiteDependencies(siteId: Long): Boolean =
        spaceRepository.hasAreasBySiteId(siteId) ||
            spaceRepository.hasPointsBySiteId(siteId) ||
            deviceRepository.hasDevicesBySiteId(siteId) ||
            robotRepository.hasRobotsBySiteId(siteId) ||
            taskRepository.hasTaskTemplatesBySiteId(siteId)

    private fun ensureSiteExists(siteId: Long) {
        if (!spaceRepository.siteExists(siteId)) {
            throw BusinessException("场地不存在")
        }
    }

    private fun validatePointLocation(point: PointWriteData) {
        ensureSiteExists(point.siteId)
        val areaId = point.areaId ?: return
        if (spaceRepository.findAreaSiteIdById(areaId) != point.siteId) {
            throw BusinessException("区域不存在或不属于所选场地")
        }
    }

    private fun AreaUpsertRequest.toAreaWriteData(): AreaWriteData =
        AreaWriteData(
            siteId = ServiceSupport.requireId(siteId, "场地不能为空"),
            areaName = areaName,
            coordinate = ServiceSupport.defaultString(coordinate, ""),
            maxCapacity = ServiceSupport.defaultInt(maxCapacity, 0),
            visible = isShow != "0",
            enabled = enable == null || enable == 1,
            remark = ServiceSupport.defaultString(remark, ""),
        )

    private fun PointUpsertRequest.toPointWriteData(): PointWriteData =
        PointWriteData(
            siteId = ServiceSupport.requireId(siteId, "场地不能为空"),
            areaId = areaId,
            pointName = pointName,
            coordinate = ServiceSupport.defaultString(coordinate, ""),
            maxCapacity = ServiceSupport.defaultInt(maxCapacity, 0),
            visible = isShow != "0",
            enabled = enable == null || enable == 1,
            remark = ServiceSupport.defaultString(remark, ""),
        )

    private fun toSiteDto(row: SiteRow): SiteDto =
        SiteDto(
            id = row.id,
            siteCode = row.siteCode,
            siteName = row.siteName,
            locationDesc = row.locationDesc,
            enabled = row.enabled,
            remark = row.remark,
        )

    private fun toAreaDto(row: AreaRow): AreaDto =
        AreaDto(
            id = row.id,
            siteId = row.siteId,
            siteCode = row.siteCode,
            siteName = row.siteName,
            areaName = row.areaName,
            coordinate = row.coordinate,
            maxCapacity = row.maxCapacity,
            isShow = booleanFlag(row.visible),
            enable = booleanNumber(row.enabled),
            remark = row.remark,
        )

    private fun toPointDto(row: PointRow): PointDto =
        PointDto(
            id = row.id,
            siteId = row.siteId,
            siteCode = row.siteCode,
            siteName = row.siteName,
            areaId = row.areaId,
            areaName = row.areaName,
            pointName = row.pointName,
            coordinate = row.coordinate,
            maxCapacity = row.maxCapacity,
            isShow = booleanFlag(row.visible),
            enable = booleanNumber(row.enabled),
            remark = row.remark,
        )

    private fun booleanFlag(value: Boolean?): String = if (value == true) "1" else "0"

    private fun booleanNumber(value: Boolean?): Int = if (value == true) 1 else 0
}
