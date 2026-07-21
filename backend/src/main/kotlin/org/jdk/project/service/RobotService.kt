package org.jdk.project.service

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.config.RobotDto
import org.jdk.project.dto.config.RobotUpsertRequest
import org.jdk.project.exception.BusinessException
import org.jdk.project.repository.RobotRepository
import org.jdk.project.repository.RobotRow
import org.jdk.project.repository.RobotWriteData
import org.jdk.project.repository.SpaceRepository
import org.jdk.project.repository.TaskRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RobotService(
    private val robotRepository: RobotRepository,
    private val spaceRepository: SpaceRepository,
    private val taskRepository: TaskRepository,
) {
    fun listRobots(): ListResponse<RobotDto> {
        val rows = robotRepository.findAllRobots().map(::toRobotDto)
        return ListResponse.of(rows.size.toLong(), rows)
    }

    @Transactional
    fun createRobot(request: RobotUpsertRequest): Long? {
        val robot = request.toRobotWriteData()
        validateRobotLocation(robot)
        return robotRepository.insertRobot(robot)
    }

    @Transactional
    fun updateRobot(
        id: Long,
        request: RobotUpsertRequest,
    ) {
        val robot = request.toRobotWriteData()
        validateRobotLocation(robot)
        val currentSiteId = robotRepository.findRobotSiteIdById(id)
        if (currentSiteId != null && currentSiteId != robot.siteId && taskRepository.hasTaskTemplatesByRobotId(id)) {
            throw BusinessException("机器人已被任务引用，无法更换场地")
        }
        ServiceSupport.ensureUpdated(
            robotRepository.updateRobot(id, robot),
            "机器人不存在",
        )
    }

    @Transactional
    fun deleteRobot(id: Long) {
        if (taskRepository.hasTaskTemplatesByRobotId(id)) {
            throw BusinessException("机器人已被任务引用，无法删除")
        }
        robotRepository.deleteRobotById(id)
    }

    private fun RobotUpsertRequest.toRobotWriteData(): RobotWriteData =
        RobotWriteData(
            siteId = ServiceSupport.requireId(siteId, "场地不能为空"),
            pointId = pointId,
            robotCode = robotId,
            robotName = robotName,
            mac = ServiceSupport.defaultString(mac, ""),
            ipAddress = ServiceSupport.defaultString(robotIp, ""),
            robotType = ServiceSupport.defaultString(robotType, ""),
            batteryPercent = ServiceSupport.defaultInt(batteryState, 0),
            chargingState = ServiceSupport.defaultString(chargingState, ""),
            workingState = ServiceSupport.defaultString(workingState, ""),
            standbyState = ServiceSupport.defaultString(standbyState, ""),
            positioningState = ServiceSupport.defaultString(positioningState, ""),
            enabled = enable == null || enable == 1,
            initialCoordinate = ServiceSupport.defaultString(oriCoordinate, ""),
            adminMode = adminMode == true,
            errorCode = ServiceSupport.defaultString(errorCode, ""),
            errorMessage = ServiceSupport.defaultString(errorMessage, ""),
            remark = ServiceSupport.defaultString(remark, ""),
        )

    private fun validateRobotLocation(robot: RobotWriteData) {
        if (!spaceRepository.siteExists(robot.siteId)) {
            throw BusinessException("场地不存在")
        }
        val pointId = robot.pointId ?: return
        if (spaceRepository.findPointSiteIdById(pointId) != robot.siteId) {
            throw BusinessException("点位不存在或不属于所选场地")
        }
    }

    private fun toRobotDto(row: RobotRow): RobotDto =
        RobotDto(
            id = row.id,
            robotId = row.robotCode,
            robotName = row.robotName,
            mac = row.mac,
            robotIp = row.ipAddress,
            siteId = row.siteId,
            siteCode = row.siteCode,
            siteName = row.siteName,
            pointId = row.pointId,
            pointName = row.pointName,
            robotType = row.robotType,
            batteryState = row.batteryPercent,
            chargingState = row.chargingState,
            workingState = row.workingState,
            standbyState = row.standbyState,
            positioningState = row.positioningState,
            enable = booleanNumber(row.enabled),
            oriCoordinate = row.initialCoordinate,
            adminMode = row.adminMode,
            remark = row.remark,
        )

    private fun booleanNumber(value: Boolean?): Int = if (value == true) 1 else 0
}
