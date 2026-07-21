package org.jdk.project.service

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.config.TaskDto
import org.jdk.project.dto.config.TaskLogDto
import org.jdk.project.dto.config.TaskUpsertRequest
import org.jdk.project.exception.BusinessException
import org.jdk.project.repository.RobotRepository
import org.jdk.project.repository.SpaceRepository
import org.jdk.project.repository.TaskLogRow
import org.jdk.project.repository.TaskLogWriteData
import org.jdk.project.repository.TaskRepository
import org.jdk.project.repository.TaskTemplateRow
import org.jdk.project.repository.TaskTemplateWriteData
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Service
class TaskService(
    private val taskRepository: TaskRepository,
    private val robotRepository: RobotRepository,
    private val spaceRepository: SpaceRepository,
) {
    fun listTaskTemplates(): ListResponse<TaskDto> {
        val rows = taskRepository.findAllTaskTemplates().map(::toTaskDto)
        return ListResponse.of(rows.size.toLong(), rows)
    }

    @Transactional
    fun createTask(request: TaskUpsertRequest): Long? {
        val task = request.toTaskWriteData()
        validateTaskAssignment(task)
        return taskRepository.insertTaskTemplate(task)
    }

    @Transactional
    fun updateTask(
        id: Long,
        request: TaskUpsertRequest,
    ) {
        val task = request.toTaskWriteData()
        validateTaskAssignment(task)
        ServiceSupport.ensureUpdated(
            taskRepository.updateTaskTemplate(id, task),
            "任务不存在",
        )
    }

    @Transactional
    fun deleteTask(id: Long) {
        taskRepository.deleteTaskTemplateById(id)
    }

    @Transactional
    fun runTask(id: Long): Long? {
        val template = taskRepository.findTaskTemplateById(id) ?: throw BusinessException("任务不存在")
        return taskRepository.insertTaskLog(
            TaskLogWriteData(
                robotId = template.robotId,
                taskTemplateId = template.id,
                taskName = template.taskName,
                taskType = template.taskType,
                taskSubtype = template.taskSubtype,
                taskMode = template.taskMode,
                taskStatus = "SUBMITTED",
                directExecution = template.directExecution,
                commandPayload = template.commandName,
            ),
        )
    }

    fun listTaskLogs(): ListResponse<TaskLogDto> {
        val rows = taskRepository.findAllTaskLogs().map(::toTaskLogDto)
        return ListResponse.of(rows.size.toLong(), rows)
    }

    private fun TaskUpsertRequest.toTaskWriteData(): TaskTemplateWriteData =
        TaskTemplateWriteData(
            siteId = ServiceSupport.requireId(siteId, "场地不能为空"),
            robotId = robotId,
            taskName = taskName,
            commandCode = commandCode,
            commandName = ServiceSupport.defaultString(commandName, ""),
            targetPoint = ServiceSupport.defaultString(targetPoint, ""),
            priority = ServiceSupport.defaultString(priority, "NORMAL"),
            executeType = ServiceSupport.defaultString(executeType, "IMMEDIATELY"),
            executeDay = ServiceSupport.defaultString(executeDay, ""),
            executeAt = parseExecuteAt(executeAt),
            taskType = ServiceSupport.defaultString(taskType, ""),
            taskSubtype = ServiceSupport.defaultString(taskSubtype, ""),
            taskMode = ServiceSupport.defaultString(taskMode, ""),
            directExecution = directExecution == true,
            returnRequired = returnRequired == true,
            enabled = enabled ?: true,
            remark = ServiceSupport.defaultString(remark, ""),
        )

    private fun validateTaskAssignment(task: TaskTemplateWriteData) {
        if (!spaceRepository.siteExists(task.siteId)) {
            throw BusinessException("场地不存在")
        }
        val robotId = task.robotId ?: return
        if (robotRepository.findRobotSiteIdById(robotId) != task.siteId) {
            throw BusinessException("机器人不存在或不属于所选场地")
        }
    }

    private fun toTaskDto(row: TaskTemplateRow): TaskDto =
        TaskDto(
            id = row.id,
            taskName = row.taskName,
            robotId = row.robotId,
            robotName = row.robotName,
            siteId = row.siteId,
            siteCode = row.siteCode,
            siteName = row.siteName,
            commandCode = row.commandCode,
            commandName = row.commandName,
            targetPoint = row.targetPoint,
            priority = row.priority,
            executeType = row.executeType,
            executeDay = row.executeDay,
            executeAt = formatDateTime(row.executeAt),
            taskType = row.taskType,
            taskSubtype = row.taskSubtype,
            taskMode = row.taskMode,
            directExecution = row.directExecution,
            returnRequired = row.returnRequired,
            enabled = row.enabled,
            remark = row.remark,
        )

    private fun toTaskLogDto(row: TaskLogRow): TaskLogDto =
        TaskLogDto(
            id = row.id,
            robotId = row.robotId,
            robotName = row.robotName,
            taskTemplateId = row.taskTemplateId,
            taskName = row.taskName,
            taskType = row.taskType,
            taskSubtype = row.taskSubtype,
            taskMode = row.taskMode,
            taskStatus = row.taskStatus,
            directExecution = row.directExecution,
            commandPayload = row.commandPayload,
            returnPayload = row.returnPayload,
            createdAt = formatDateTime(row.createdAt),
            startedAt = formatDateTime(row.startedAt),
            finishedAt = formatDateTime(row.finishedAt),
        )

    private fun parseExecuteAt(value: String?): OffsetDateTime? {
        val normalizedValue = value?.trim().orEmpty()
        if (normalizedValue.isEmpty()) {
            return null
        }
        return try {
            OffsetDateTime.parse(normalizedValue)
        } catch (_: DateTimeParseException) {
            try {
                LocalDateTime.parse(normalizedValue.replace(' ', 'T')).atOffset(DEFAULT_ZONE_OFFSET)
            } catch (_: DateTimeParseException) {
                throw BusinessException("执行时间格式应为 yyyy-MM-dd HH:mm:ss 或 ISO-8601")
            }
        }
    }

    private fun formatDateTime(value: OffsetDateTime?): String? = value?.toLocalDateTime()?.format(DATETIME_FORMATTER)

    private companion object {
        private val DEFAULT_ZONE_OFFSET: ZoneOffset = ZoneOffset.ofHours(8)
        private val DATETIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    }
}
