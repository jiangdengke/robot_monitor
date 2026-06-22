package org.jdk.project.service.config

import org.jdk.project.dto.config.RobotUpsertRequest
import org.jdk.project.service.config.ConfigCommandSupport.defaultInt
import org.jdk.project.service.config.ConfigCommandSupport.defaultString
import org.jdk.project.service.config.ConfigCommandSupport.ensureUpdated
import org.jdk.project.service.config.ConfigCommandSupport.requiredId
import org.jooq.DSLContext
import org.jooq.generated.project.Tables.ROBOT
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RobotCommandService(
    private val dsl: DSLContext,
) {
    @Transactional
    fun create(request: RobotUpsertRequest): Long? =
        dsl.insertInto(ROBOT)
            .set(ROBOT.LOUNGE_ID, requiredId(request.loungeId, "贵宾室不能为空"))
            .set(ROBOT.REGION_ID, request.regionId)
            .set(ROBOT.ROBOT_CODE, request.robotId)
            .set(ROBOT.NAME, request.robotName)
            .set(ROBOT.MAC, defaultString(request.mac, ""))
            .set(ROBOT.IP_ADDRESS, defaultString(request.robotIp, ""))
            .set(ROBOT.ROBOT_TYPE, defaultString(request.robotType, ""))
            .set(ROBOT.BATTERY_PERCENT, defaultInt(request.batteryState, 0))
            .set(ROBOT.CHARGING_STATE, defaultString(request.chargingState, ""))
            .set(ROBOT.WORKING_STATE, defaultString(request.workingState, ""))
            .set(ROBOT.STANDBY_STATE, defaultString(request.standbyState, ""))
            .set(ROBOT.POSITIONING_STATE, defaultString(request.positioningState, ""))
            .set(ROBOT.ENABLED, request.enable == null || request.enable == 1)
            .set(ROBOT.INITIAL_COORDINATE, defaultString(request.oriCoordinate, ""))
            .set(ROBOT.ADMIN_MODE, request.adminMode == true)
            .set(ROBOT.ERROR_CODE, defaultString(request.errorCode, ""))
            .set(ROBOT.ERROR_MESSAGE, defaultString(request.errorMessage, ""))
            .set(ROBOT.REMARK, defaultString(request.remark, ""))
            .returningResult(ROBOT.ID)
            .fetchOne(ROBOT.ID)

    @Transactional
    fun update(id: Long, request: RobotUpsertRequest) {
        ensureUpdated(
            dsl.update(ROBOT)
                .set(ROBOT.LOUNGE_ID, requiredId(request.loungeId, "贵宾室不能为空"))
                .set(ROBOT.REGION_ID, request.regionId)
                .set(ROBOT.ROBOT_CODE, request.robotId)
                .set(ROBOT.NAME, request.robotName)
                .set(ROBOT.MAC, defaultString(request.mac, ""))
                .set(ROBOT.IP_ADDRESS, defaultString(request.robotIp, ""))
                .set(ROBOT.ROBOT_TYPE, defaultString(request.robotType, ""))
                .set(ROBOT.BATTERY_PERCENT, defaultInt(request.batteryState, 0))
                .set(ROBOT.CHARGING_STATE, defaultString(request.chargingState, ""))
                .set(ROBOT.WORKING_STATE, defaultString(request.workingState, ""))
                .set(ROBOT.STANDBY_STATE, defaultString(request.standbyState, ""))
                .set(ROBOT.POSITIONING_STATE, defaultString(request.positioningState, ""))
                .set(ROBOT.ENABLED, request.enable == null || request.enable == 1)
                .set(ROBOT.INITIAL_COORDINATE, defaultString(request.oriCoordinate, ""))
                .set(ROBOT.ADMIN_MODE, request.adminMode == true)
                .set(ROBOT.ERROR_CODE, defaultString(request.errorCode, ""))
                .set(ROBOT.ERROR_MESSAGE, defaultString(request.errorMessage, ""))
                .set(ROBOT.REMARK, defaultString(request.remark, ""))
                .where(ROBOT.ID.eq(id))
                .execute(),
            "机器人不存在",
        )
    }

    @Transactional
    fun delete(id: Long) {
        dsl.deleteFrom(ROBOT).where(ROBOT.ID.eq(id)).execute()
    }
}
