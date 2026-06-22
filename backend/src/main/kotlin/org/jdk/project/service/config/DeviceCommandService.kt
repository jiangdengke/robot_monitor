package org.jdk.project.service.config

import org.jdk.project.dto.config.DeviceRegionBindingUpsertRequest
import org.jdk.project.dto.config.DeviceUpsertRequest
import org.jdk.project.service.config.ConfigCommandSupport.defaultString
import org.jdk.project.service.config.ConfigCommandSupport.ensureUpdated
import org.jdk.project.service.config.ConfigCommandSupport.requiredId
import org.jooq.DSLContext
import org.jooq.generated.project.Tables.DEVICE
import org.jooq.generated.project.Tables.DEVICE_REGION_BINDING
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeviceCommandService(
    private val dsl: DSLContext,
) {
    @Transactional
    fun createDevice(request: DeviceUpsertRequest): Long? =
        dsl.insertInto(DEVICE)
            .set(DEVICE.LOUNGE_ID, requiredId(request.loungeId, "贵宾室不能为空"))
            .set(DEVICE.NAME, request.deviceName)
            .set(DEVICE.DEVICE_TYPE, defaultString(request.deviceType, "CAMERA"))
            .set(DEVICE.EXTERNAL_DEVICE_ID, defaultString(request.deepGlintDeviceId, ""))
            .set(DEVICE.ENABLED, request.enable == null || request.enable == 1)
            .set(DEVICE.REMARK, defaultString(request.remark, ""))
            .returningResult(DEVICE.ID)
            .fetchOne(DEVICE.ID)

    @Transactional
    fun updateDevice(id: Long, request: DeviceUpsertRequest) {
        ensureUpdated(
            dsl.update(DEVICE)
                .set(DEVICE.LOUNGE_ID, requiredId(request.loungeId, "贵宾室不能为空"))
                .set(DEVICE.NAME, request.deviceName)
                .set(DEVICE.DEVICE_TYPE, defaultString(request.deviceType, "CAMERA"))
                .set(DEVICE.EXTERNAL_DEVICE_ID, defaultString(request.deepGlintDeviceId, ""))
                .set(DEVICE.ENABLED, request.enable == null || request.enable == 1)
                .set(DEVICE.REMARK, defaultString(request.remark, ""))
                .where(DEVICE.ID.eq(id))
                .execute(),
            "设备不存在",
        )
    }

    @Transactional
    fun deleteDevice(id: Long) {
        dsl.deleteFrom(DEVICE).where(DEVICE.ID.eq(id)).execute()
    }

    @Transactional
    fun saveDeviceRegionBinding(request: DeviceRegionBindingUpsertRequest) {
        val deviceId = requiredId(request.deviceId, "设备不能为空")
        val regionId = requiredId(request.regionId, "区域不能为空")
        dsl.deleteFrom(DEVICE_REGION_BINDING)
            .where(DEVICE_REGION_BINDING.DEVICE_ID.eq(deviceId))
            .and(DEVICE_REGION_BINDING.REGION_ID.eq(regionId))
            .execute()
        dsl.insertInto(DEVICE_REGION_BINDING)
            .set(DEVICE_REGION_BINDING.DEVICE_ID, deviceId)
            .set(DEVICE_REGION_BINDING.REGION_ID, regionId)
            .set(DEVICE_REGION_BINDING.IMAGE_ID, request.imageId)
            .set(DEVICE_REGION_BINDING.COORDINATE, defaultString(request.coordinate, ""))
            .set(DEVICE_REGION_BINDING.REMARK, defaultString(request.remark, ""))
            .execute()
    }

    @Transactional
    fun deleteDeviceRegionBinding(deviceId: Long, regionId: Long) {
        dsl.deleteFrom(DEVICE_REGION_BINDING)
            .where(DEVICE_REGION_BINDING.DEVICE_ID.eq(deviceId))
            .and(DEVICE_REGION_BINDING.REGION_ID.eq(regionId))
            .execute()
    }
}
