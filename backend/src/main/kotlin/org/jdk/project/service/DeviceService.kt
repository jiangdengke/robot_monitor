package org.jdk.project.service

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.config.DeviceDto
import org.jdk.project.dto.config.DevicePointBindingDto
import org.jdk.project.dto.config.DevicePointBindingUpsertRequest
import org.jdk.project.dto.config.DeviceUpsertRequest
import org.jdk.project.exception.BusinessException
import org.jdk.project.repository.DevicePointBindingRow
import org.jdk.project.repository.DevicePointBindingWriteData
import org.jdk.project.repository.DeviceRepository
import org.jdk.project.repository.DeviceRow
import org.jdk.project.repository.DeviceWriteData
import org.jdk.project.repository.SpaceRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeviceService(
    private val deviceRepository: DeviceRepository,
    private val spaceRepository: SpaceRepository,
) {
    fun listDevices(): ListResponse<DeviceDto> {
        val rows = deviceRepository.findAllDevices().map(::toDeviceDto)
        return ListResponse.of(rows.size.toLong(), rows)
    }

    @Transactional
    fun createDevice(request: DeviceUpsertRequest): Long? {
        val device = request.toDeviceWriteData()
        ensureSiteExists(device.siteId)
        return deviceRepository.insertDevice(device)
    }

    @Transactional
    fun updateDevice(
        id: Long,
        request: DeviceUpsertRequest,
    ) {
        val device = request.toDeviceWriteData()
        ensureSiteExists(device.siteId)
        val currentSiteId = deviceRepository.findDeviceSiteIdById(id)
        if (currentSiteId != null && currentSiteId != device.siteId && deviceRepository.hasBindingsByDeviceId(id)) {
            throw BusinessException("设备已绑定点位，无法更换场地")
        }
        ServiceSupport.ensureUpdated(
            deviceRepository.updateDevice(id, device),
            "设备不存在",
        )
    }

    @Transactional
    fun deleteDevice(id: Long) {
        deviceRepository.deleteDevicePointBindingsByDeviceId(id)
        deviceRepository.deleteDeviceById(id)
    }

    fun listDevicePointBindings(
        deviceId: Long?,
        pointId: Long?,
    ): ListResponse<DevicePointBindingDto> {
        val rows = deviceRepository.findDevicePointBindings(deviceId, pointId).map(::toBindingDto)
        return ListResponse.of(rows.size.toLong(), rows)
    }

    @Transactional
    fun saveDevicePointBinding(request: DevicePointBindingUpsertRequest) {
        val deviceId = ServiceSupport.requireId(request.deviceId, "设备不能为空")
        val pointId = ServiceSupport.requireId(request.pointId, "点位不能为空")
        val deviceSiteId = deviceRepository.findDeviceSiteIdById(deviceId) ?: throw BusinessException("设备不存在")
        val pointSiteId = spaceRepository.findPointSiteIdById(pointId) ?: throw BusinessException("点位不存在")
        if (deviceSiteId != pointSiteId) {
            throw BusinessException("设备与点位必须属于同一场地")
        }
        deviceRepository.deleteDevicePointBinding(deviceId, pointId)
        deviceRepository.insertDevicePointBinding(
            DevicePointBindingWriteData(
                deviceId = deviceId,
                pointId = pointId,
                coordinate = ServiceSupport.defaultString(request.coordinate, ""),
                remark = ServiceSupport.defaultString(request.remark, ""),
            ),
        )
    }

    @Transactional
    fun deleteDevicePointBinding(
        deviceId: Long,
        pointId: Long,
    ) {
        deviceRepository.deleteDevicePointBinding(deviceId, pointId)
    }

    private fun DeviceUpsertRequest.toDeviceWriteData(): DeviceWriteData =
        DeviceWriteData(
            siteId = ServiceSupport.requireId(siteId, "场地不能为空"),
            deviceName = deviceName,
            deviceType = ServiceSupport.defaultString(deviceType, "CAMERA"),
            externalDeviceId = ServiceSupport.defaultString(deepGlintDeviceId, ""),
            enabled = enable == null || enable == 1,
            remark = ServiceSupport.defaultString(remark, ""),
        )

    private fun ensureSiteExists(siteId: Long) {
        if (!spaceRepository.siteExists(siteId)) {
            throw BusinessException("场地不存在")
        }
    }

    private fun toDeviceDto(row: DeviceRow): DeviceDto =
        DeviceDto(
            id = row.id,
            siteId = row.siteId,
            siteCode = row.siteCode,
            siteName = row.siteName,
            deviceName = row.deviceName,
            deviceType = row.deviceType,
            deepGlintDeviceId = row.externalDeviceId,
            enable = booleanNumber(row.enabled),
            remark = row.remark,
        )

    private fun toBindingDto(row: DevicePointBindingRow): DevicePointBindingDto =
        DevicePointBindingDto(
            deviceId = row.deviceId,
            deviceName = row.deviceName,
            pointId = row.pointId,
            pointName = row.pointName,
            coordinate = row.coordinate,
            remark = row.remark,
        )

    private fun booleanNumber(value: Boolean?): Int = if (value == true) 1 else 0
}
