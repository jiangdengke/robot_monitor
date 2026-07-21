package org.jdk.project.repository

import org.jooq.DSLContext
import org.jooq.generated.project.Tables.DEVICE
import org.jooq.generated.project.Tables.DEVICE_POINT_BINDING
import org.jooq.generated.project.Tables.POINT
import org.jooq.generated.project.Tables.SITE
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository

@Repository
class DeviceRepository(
    private val dsl: DSLContext,
) {
    fun findDeviceSiteIdById(id: Long): Long? =
        dsl
            .select(DEVICE.SITE_ID)
            .from(DEVICE)
            .where(DEVICE.ID.eq(id))
            .fetchOne(DEVICE.SITE_ID)

    fun hasDevicesBySiteId(siteId: Long): Boolean = dsl.fetchExists(DEVICE, DEVICE.SITE_ID.eq(siteId))

    fun hasBindingsByDeviceId(deviceId: Long): Boolean = dsl.fetchExists(DEVICE_POINT_BINDING, DEVICE_POINT_BINDING.DEVICE_ID.eq(deviceId))

    fun hasBindingsByPointId(pointId: Long): Boolean = dsl.fetchExists(DEVICE_POINT_BINDING, DEVICE_POINT_BINDING.POINT_ID.eq(pointId))

    fun findAllDevices(): List<DeviceRow> =
        dsl
            .select(
                DEVICE.ID,
                DEVICE.SITE_ID,
                SITE.CODE,
                SITE.NAME,
                DEVICE.NAME,
                DEVICE.DEVICE_TYPE,
                DEVICE.EXTERNAL_DEVICE_ID,
                DEVICE.ENABLED,
                DEVICE.REMARK,
            ).from(DEVICE)
            .join(SITE)
            .on(DEVICE.SITE_ID.eq(SITE.ID))
            .orderBy(DEVICE.ID.asc())
            .fetch { record ->
                DeviceRow(
                    id = record.get(DEVICE.ID),
                    siteId = record.get(DEVICE.SITE_ID),
                    siteCode = record.get(SITE.CODE),
                    siteName = record.get(SITE.NAME),
                    deviceName = record.get(DEVICE.NAME),
                    deviceType = record.get(DEVICE.DEVICE_TYPE),
                    externalDeviceId = record.get(DEVICE.EXTERNAL_DEVICE_ID),
                    enabled = record.get(DEVICE.ENABLED),
                    remark = record.get(DEVICE.REMARK),
                )
            }

    fun insertDevice(device: DeviceWriteData): Long? =
        dsl
            .insertInto(DEVICE)
            .set(DEVICE.SITE_ID, device.siteId)
            .set(DEVICE.NAME, device.deviceName)
            .set(DEVICE.DEVICE_TYPE, device.deviceType)
            .set(DEVICE.EXTERNAL_DEVICE_ID, device.externalDeviceId)
            .set(DEVICE.ENABLED, device.enabled)
            .set(DEVICE.REMARK, device.remark)
            .returningResult(DEVICE.ID)
            .fetchOne(DEVICE.ID)

    fun updateDevice(
        id: Long,
        device: DeviceWriteData,
    ): Int =
        dsl
            .update(DEVICE)
            .set(DEVICE.SITE_ID, device.siteId)
            .set(DEVICE.NAME, device.deviceName)
            .set(DEVICE.DEVICE_TYPE, device.deviceType)
            .set(DEVICE.EXTERNAL_DEVICE_ID, device.externalDeviceId)
            .set(DEVICE.ENABLED, device.enabled)
            .set(DEVICE.REMARK, device.remark)
            .where(DEVICE.ID.eq(id))
            .execute()

    fun deleteDeviceById(id: Long): Int = dsl.deleteFrom(DEVICE).where(DEVICE.ID.eq(id)).execute()

    fun findDevicePointBindings(
        deviceId: Long?,
        pointId: Long?,
    ): List<DevicePointBindingRow> {
        var filterCondition = DSL.noCondition()
        if (deviceId != null) {
            filterCondition = filterCondition.and(DEVICE_POINT_BINDING.DEVICE_ID.eq(deviceId))
        }
        if (pointId != null) {
            filterCondition = filterCondition.and(DEVICE_POINT_BINDING.POINT_ID.eq(pointId))
        }

        return dsl
            .select(
                DEVICE_POINT_BINDING.DEVICE_ID,
                DEVICE.NAME,
                DEVICE_POINT_BINDING.POINT_ID,
                POINT.NAME,
                DEVICE_POINT_BINDING.COORDINATE,
                DEVICE_POINT_BINDING.REMARK,
            ).from(DEVICE_POINT_BINDING)
            .join(DEVICE)
            .on(DEVICE_POINT_BINDING.DEVICE_ID.eq(DEVICE.ID))
            .join(POINT)
            .on(DEVICE_POINT_BINDING.POINT_ID.eq(POINT.ID))
            .where(filterCondition)
            .orderBy(DEVICE_POINT_BINDING.DEVICE_ID.asc(), DEVICE_POINT_BINDING.POINT_ID.asc())
            .fetch { record ->
                DevicePointBindingRow(
                    deviceId = record.get(DEVICE_POINT_BINDING.DEVICE_ID),
                    deviceName = record.get(DEVICE.NAME),
                    pointId = record.get(DEVICE_POINT_BINDING.POINT_ID),
                    pointName = record.get(POINT.NAME),
                    coordinate = record.get(DEVICE_POINT_BINDING.COORDINATE),
                    remark = record.get(DEVICE_POINT_BINDING.REMARK),
                )
            }
    }

    fun deleteDevicePointBindingsByDeviceId(deviceId: Long): Int =
        dsl
            .deleteFrom(DEVICE_POINT_BINDING)
            .where(DEVICE_POINT_BINDING.DEVICE_ID.eq(deviceId))
            .execute()

    fun deleteDevicePointBinding(
        deviceId: Long,
        pointId: Long,
    ): Int =
        dsl
            .deleteFrom(DEVICE_POINT_BINDING)
            .where(DEVICE_POINT_BINDING.DEVICE_ID.eq(deviceId))
            .and(DEVICE_POINT_BINDING.POINT_ID.eq(pointId))
            .execute()

    fun insertDevicePointBinding(binding: DevicePointBindingWriteData): Int =
        dsl
            .insertInto(DEVICE_POINT_BINDING)
            .set(DEVICE_POINT_BINDING.DEVICE_ID, binding.deviceId)
            .set(DEVICE_POINT_BINDING.POINT_ID, binding.pointId)
            .set(DEVICE_POINT_BINDING.COORDINATE, binding.coordinate)
            .set(DEVICE_POINT_BINDING.REMARK, binding.remark)
            .execute()
}

data class DeviceRow(
    val id: Long?,
    val siteId: Long?,
    val siteCode: String?,
    val siteName: String?,
    val deviceName: String?,
    val deviceType: String?,
    val externalDeviceId: String?,
    val enabled: Boolean?,
    val remark: String?,
)

data class DeviceWriteData(
    val siteId: Long,
    val deviceName: String?,
    val deviceType: String,
    val externalDeviceId: String,
    val enabled: Boolean,
    val remark: String,
)

data class DevicePointBindingRow(
    val deviceId: Long?,
    val deviceName: String?,
    val pointId: Long?,
    val pointName: String?,
    val coordinate: String?,
    val remark: String?,
)

data class DevicePointBindingWriteData(
    val deviceId: Long,
    val pointId: Long,
    val coordinate: String,
    val remark: String,
)
