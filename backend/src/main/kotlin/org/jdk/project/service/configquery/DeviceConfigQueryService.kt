package org.jdk.project.service.configquery

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.config.DeviceDto
import org.jooq.DSLContext
import org.jooq.generated.project.Tables.DEVICE
import org.jooq.generated.project.Tables.LOUNGE
import org.springframework.stereotype.Service

@Service
class DeviceConfigQueryService(
    private val dsl: DSLContext,
    private val mapper: ConfigQueryMapper,
) {
    fun listDevices(): ListResponse<DeviceDto> {
        val rows: List<DeviceDto> =
            dsl.select(
                DEVICE.ID,
                DEVICE.NAME,
                DEVICE.DEVICE_TYPE,
                DEVICE.EXTERNAL_DEVICE_ID,
                LOUNGE.CODE,
                ConfigQueryMapper.LOUNGE_NAME,
                DEVICE.ENABLED,
                DEVICE.REMARK,
            ).from(DEVICE)
                .join(LOUNGE).on(DEVICE.LOUNGE_ID.eq(LOUNGE.ID))
                .orderBy(DEVICE.ID.asc())
                .fetch { record -> mapper.toDeviceDto(record) }
        return ListResponse.of(rows.size.toLong(), rows)
    }
}
