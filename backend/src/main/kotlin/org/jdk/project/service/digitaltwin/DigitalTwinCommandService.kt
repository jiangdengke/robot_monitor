package org.jdk.project.service.digitaltwin

import org.jdk.project.dto.digitaltwin.DigitalTwinActionRequest
import org.jdk.project.service.digitaltwin.DigitalTwinSupport.firstNonBlank
import org.jdk.project.service.digitaltwin.DigitalTwinSupport.parseLong
import org.jdk.project.service.digitaltwin.DigitalTwinSupport.trimToNull
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.generated.project.Tables.GUIDE_LOG
import org.jooq.generated.project.Tables.PASSENGER
import org.jooq.generated.project.Tables.PASSENGER_WARNING_LOG
import org.jooq.generated.project.Tables.REGION
import org.jooq.generated.project.Tables.ROBOT
import org.jooq.generated.project.Tables.ROBOT_TASK_LOG
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DigitalTwinCommandService(
    private val dsl: DSLContext,
) {
    @Transactional
    fun createGuideTask(request: DigitalTwinActionRequest) {
        val robotCode = trimToNull(request.robotId)
        val regionId = request.regionId ?: request.areaId
        val robot = findRobot(robotCode)
        val robotId = robot?.get(ROBOT.ID)
        var loungeId = robot?.get(ROBOT.LOUNGE_ID)
        if (loungeId == null && regionId != null) {
            loungeId =
                dsl.select(REGION.LOUNGE_ID)
                    .from(REGION)
                    .where(REGION.ID.eq(regionId))
                    .fetchOne(REGION.LOUNGE_ID)
        }
        val coordinate =
            if (regionId == null) {
                trimToNull(request.coordinate)
            } else {
                dsl.select(REGION.COORDINATE)
                    .from(REGION)
                    .where(REGION.ID.eq(regionId))
                    .fetchOne(REGION.COORDINATE)
            }

        dsl.insertInto(GUIDE_LOG)
            .set(GUIDE_LOG.LOUNGE_ID, loungeId)
            .set(GUIDE_LOG.ROBOT_ID, robotId)
            .set(GUIDE_LOG.REGION_ID, regionId)
            .set(GUIDE_LOG.RESULT_STATUS, "SUCCESS")
            .set(GUIDE_LOG.COORDINATE, coordinate)
            .execute()
    }

    @Transactional
    fun interruptGuideTask(request: DigitalTwinActionRequest) {
        val robot = findRobot(trimToNull(request.robotId))
        val robotId = robot?.get(ROBOT.ID)
        dsl.insertInto(ROBOT_TASK_LOG)
            .set(ROBOT_TASK_LOG.ROBOT_ID, robotId)
            .set(ROBOT_TASK_LOG.TASK_NAME, "停止当前任务")
            .set(ROBOT_TASK_LOG.TASK_TYPE, "引导")
            .set(ROBOT_TASK_LOG.TASK_STATUS, "已停止")
            .set(ROBOT_TASK_LOG.COMMAND_PAYLOAD, "{\"action\":\"interrupt\"}")
            .execute()
    }

    @Transactional
    fun saveManualNotice(request: DigitalTwinActionRequest) {
        saveNotice(request, "MANUAL")
    }

    @Transactional
    fun saveRobotNotice(request: DigitalTwinActionRequest) {
        saveNotice(request, "ROBOT")
    }

    private fun saveNotice(request: DigitalTwinActionRequest, noticeType: String) {
        val warningId = request.warningId
        val passengerId = request.passengerId
        val warningInfo = firstNonBlank(request.warningInfo, if (noticeType == "ROBOT") "机器人提醒" else "人工提醒")
        val warningType = firstNonBlank(request.warningType, "SERVICE_NOTICE")
        if (warningId != null) {
            val updated =
                dsl.update(PASSENGER_WARNING_LOG)
                    .set(PASSENGER_WARNING_LOG.NOTICE_TYPE, noticeType)
                    .set(PASSENGER_WARNING_LOG.RESULT_STATUS, "SUCCESS")
                    .set(PASSENGER_WARNING_LOG.WARNING_INFO, warningInfo)
                    .where(PASSENGER_WARNING_LOG.ID.eq(warningId))
                    .execute()
            if (updated > 0) {
                return
            }
        }
        if (passengerId == null) {
            return
        }
        val passenger =
            dsl.select(PASSENGER.FLIGHT_ID, PASSENGER.REGION_ID)
                .from(PASSENGER)
                .where(PASSENGER.ID.eq(passengerId))
                .fetchOne()
        dsl.insertInto(PASSENGER_WARNING_LOG)
            .set(PASSENGER_WARNING_LOG.PASSENGER_ID, passengerId)
            .set(PASSENGER_WARNING_LOG.FLIGHT_ID, passenger?.get(PASSENGER.FLIGHT_ID))
            .set(PASSENGER_WARNING_LOG.REGION_ID, passenger?.get(PASSENGER.REGION_ID))
            .set(PASSENGER_WARNING_LOG.WARNING_TYPE, warningType)
            .set(PASSENGER_WARNING_LOG.WARNING_INFO, warningInfo)
            .set(PASSENGER_WARNING_LOG.NOTICE_TYPE, noticeType)
            .set(PASSENGER_WARNING_LOG.RESULT_STATUS, "SUCCESS")
            .execute()
    }

    private fun findRobot(robotId: String?): Record? {
        if (robotId == null) {
            return null
        }
        var condition: Condition = ROBOT.ROBOT_CODE.eq(robotId)
        val id = parseLong(robotId)
        if (id != null) {
            condition = condition.or(ROBOT.ID.eq(id))
        }
        return dsl.select(ROBOT.ID, ROBOT.LOUNGE_ID).from(ROBOT).where(condition).fetchOne()
    }
}
