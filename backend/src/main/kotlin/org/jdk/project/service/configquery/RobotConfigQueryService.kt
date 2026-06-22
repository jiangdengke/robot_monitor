package org.jdk.project.service.configquery

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.config.RobotDto
import org.jooq.DSLContext
import org.jooq.generated.project.Tables.LOUNGE
import org.jooq.generated.project.Tables.REGION
import org.jooq.generated.project.Tables.ROBOT
import org.springframework.stereotype.Service

@Service
class RobotConfigQueryService(
    private val dsl: DSLContext,
    private val mapper: ConfigQueryMapper,
) {
    fun listRobots(): ListResponse<RobotDto> {
        val rows =
            dsl.select(
                ROBOT.ID,
                ROBOT.ROBOT_CODE,
                ROBOT.NAME,
                ROBOT.MAC,
                ROBOT.IP_ADDRESS,
                LOUNGE.CODE,
                ConfigQueryMapper.LOUNGE_NAME,
                ROBOT.REGION_ID,
                ConfigQueryMapper.REGION_NAME,
                ROBOT.ROBOT_TYPE,
                ROBOT.BATTERY_PERCENT,
                ROBOT.CHARGING_STATE,
                ROBOT.WORKING_STATE,
                ROBOT.STANDBY_STATE,
                ROBOT.POSITIONING_STATE,
                ROBOT.ENABLED,
                ROBOT.INITIAL_COORDINATE,
                ROBOT.ADMIN_MODE,
                ROBOT.REMARK,
            ).from(ROBOT)
                .join(LOUNGE).on(ROBOT.LOUNGE_ID.eq(LOUNGE.ID))
                .leftJoin(REGION).on(ROBOT.REGION_ID.eq(REGION.ID))
                .orderBy(ROBOT.ID.asc())
                .fetch { record -> mapper.toRobotDto(record) }
        return ListResponse.of(rows.size.toLong(), rows)
    }
}
