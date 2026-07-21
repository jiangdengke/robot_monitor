package org.jdk.project.repository

import org.jooq.DSLContext
import org.jooq.generated.project.Tables.ROBOT
import org.jooq.generated.project.Tables.ROBOT_TASK_LOG
import org.jooq.generated.project.Tables.ROBOT_TASK_TEMPLATE
import org.jooq.generated.project.Tables.SITE
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

@Repository
class TaskRepository(
    private val dsl: DSLContext,
) {
    fun hasTaskTemplatesBySiteId(siteId: Long): Boolean = dsl.fetchExists(ROBOT_TASK_TEMPLATE, ROBOT_TASK_TEMPLATE.SITE_ID.eq(siteId))

    fun hasTaskTemplatesByRobotId(robotId: Long): Boolean = dsl.fetchExists(ROBOT_TASK_TEMPLATE, ROBOT_TASK_TEMPLATE.ROBOT_ID.eq(robotId))

    fun findAllTaskTemplates(): List<TaskTemplateRow> =
        dsl
            .select(
                ROBOT_TASK_TEMPLATE.ID,
                ROBOT_TASK_TEMPLATE.NAME,
                ROBOT_TASK_TEMPLATE.ROBOT_ID,
                ROBOT.NAME,
                ROBOT_TASK_TEMPLATE.SITE_ID,
                SITE.CODE,
                SITE.NAME,
                ROBOT_TASK_TEMPLATE.COMMAND_CODE,
                ROBOT_TASK_TEMPLATE.COMMAND_NAME,
                ROBOT_TASK_TEMPLATE.TARGET_POINT,
                ROBOT_TASK_TEMPLATE.PRIORITY,
                ROBOT_TASK_TEMPLATE.EXECUTE_TYPE,
                ROBOT_TASK_TEMPLATE.EXECUTE_DAY,
                ROBOT_TASK_TEMPLATE.EXECUTE_AT,
                ROBOT_TASK_TEMPLATE.TASK_TYPE,
                ROBOT_TASK_TEMPLATE.TASK_SUBTYPE,
                ROBOT_TASK_TEMPLATE.TASK_MODE,
                ROBOT_TASK_TEMPLATE.DIRECT_EXECUTION,
                ROBOT_TASK_TEMPLATE.RETURN_REQUIRED,
                ROBOT_TASK_TEMPLATE.ENABLED,
                ROBOT_TASK_TEMPLATE.REMARK,
            ).from(ROBOT_TASK_TEMPLATE)
            .join(SITE)
            .on(ROBOT_TASK_TEMPLATE.SITE_ID.eq(SITE.ID))
            .leftJoin(ROBOT)
            .on(ROBOT_TASK_TEMPLATE.ROBOT_ID.eq(ROBOT.ID))
            .orderBy(ROBOT_TASK_TEMPLATE.ID.asc())
            .fetch { record ->
                TaskTemplateRow(
                    id = record.get(ROBOT_TASK_TEMPLATE.ID),
                    taskName = record.get(ROBOT_TASK_TEMPLATE.NAME),
                    robotId = record.get(ROBOT_TASK_TEMPLATE.ROBOT_ID),
                    robotName = record.get(ROBOT.NAME),
                    siteId = record.get(ROBOT_TASK_TEMPLATE.SITE_ID),
                    siteCode = record.get(SITE.CODE),
                    siteName = record.get(SITE.NAME),
                    commandCode = record.get(ROBOT_TASK_TEMPLATE.COMMAND_CODE),
                    commandName = record.get(ROBOT_TASK_TEMPLATE.COMMAND_NAME),
                    targetPoint = record.get(ROBOT_TASK_TEMPLATE.TARGET_POINT),
                    priority = record.get(ROBOT_TASK_TEMPLATE.PRIORITY),
                    executeType = record.get(ROBOT_TASK_TEMPLATE.EXECUTE_TYPE),
                    executeDay = record.get(ROBOT_TASK_TEMPLATE.EXECUTE_DAY),
                    executeAt = record.get(ROBOT_TASK_TEMPLATE.EXECUTE_AT),
                    taskType = record.get(ROBOT_TASK_TEMPLATE.TASK_TYPE),
                    taskSubtype = record.get(ROBOT_TASK_TEMPLATE.TASK_SUBTYPE),
                    taskMode = record.get(ROBOT_TASK_TEMPLATE.TASK_MODE),
                    directExecution = record.get(ROBOT_TASK_TEMPLATE.DIRECT_EXECUTION),
                    returnRequired = record.get(ROBOT_TASK_TEMPLATE.RETURN_REQUIRED),
                    enabled = record.get(ROBOT_TASK_TEMPLATE.ENABLED),
                    remark = record.get(ROBOT_TASK_TEMPLATE.REMARK),
                )
            }

    fun findTaskTemplateById(id: Long): TaskTemplateRow? =
        dsl
            .select(
                ROBOT_TASK_TEMPLATE.ID,
                ROBOT_TASK_TEMPLATE.NAME,
                ROBOT_TASK_TEMPLATE.ROBOT_ID,
                ROBOT_TASK_TEMPLATE.SITE_ID,
                ROBOT_TASK_TEMPLATE.COMMAND_CODE,
                ROBOT_TASK_TEMPLATE.COMMAND_NAME,
                ROBOT_TASK_TEMPLATE.TARGET_POINT,
                ROBOT_TASK_TEMPLATE.PRIORITY,
                ROBOT_TASK_TEMPLATE.EXECUTE_TYPE,
                ROBOT_TASK_TEMPLATE.EXECUTE_DAY,
                ROBOT_TASK_TEMPLATE.EXECUTE_AT,
                ROBOT_TASK_TEMPLATE.TASK_TYPE,
                ROBOT_TASK_TEMPLATE.TASK_SUBTYPE,
                ROBOT_TASK_TEMPLATE.TASK_MODE,
                ROBOT_TASK_TEMPLATE.DIRECT_EXECUTION,
                ROBOT_TASK_TEMPLATE.RETURN_REQUIRED,
                ROBOT_TASK_TEMPLATE.ENABLED,
                ROBOT_TASK_TEMPLATE.REMARK,
            ).from(ROBOT_TASK_TEMPLATE)
            .where(ROBOT_TASK_TEMPLATE.ID.eq(id))
            .fetchOne { record ->
                TaskTemplateRow(
                    id = record.get(ROBOT_TASK_TEMPLATE.ID),
                    taskName = record.get(ROBOT_TASK_TEMPLATE.NAME),
                    robotId = record.get(ROBOT_TASK_TEMPLATE.ROBOT_ID),
                    robotName = null,
                    siteId = record.get(ROBOT_TASK_TEMPLATE.SITE_ID),
                    siteCode = null,
                    siteName = null,
                    commandCode = record.get(ROBOT_TASK_TEMPLATE.COMMAND_CODE),
                    commandName = record.get(ROBOT_TASK_TEMPLATE.COMMAND_NAME),
                    targetPoint = record.get(ROBOT_TASK_TEMPLATE.TARGET_POINT),
                    priority = record.get(ROBOT_TASK_TEMPLATE.PRIORITY),
                    executeType = record.get(ROBOT_TASK_TEMPLATE.EXECUTE_TYPE),
                    executeDay = record.get(ROBOT_TASK_TEMPLATE.EXECUTE_DAY),
                    executeAt = record.get(ROBOT_TASK_TEMPLATE.EXECUTE_AT),
                    taskType = record.get(ROBOT_TASK_TEMPLATE.TASK_TYPE),
                    taskSubtype = record.get(ROBOT_TASK_TEMPLATE.TASK_SUBTYPE),
                    taskMode = record.get(ROBOT_TASK_TEMPLATE.TASK_MODE),
                    directExecution = record.get(ROBOT_TASK_TEMPLATE.DIRECT_EXECUTION),
                    returnRequired = record.get(ROBOT_TASK_TEMPLATE.RETURN_REQUIRED),
                    enabled = record.get(ROBOT_TASK_TEMPLATE.ENABLED),
                    remark = record.get(ROBOT_TASK_TEMPLATE.REMARK),
                )
            }

    fun insertTaskTemplate(task: TaskTemplateWriteData): Long? =
        dsl
            .insertInto(ROBOT_TASK_TEMPLATE)
            .set(ROBOT_TASK_TEMPLATE.SITE_ID, task.siteId)
            .set(ROBOT_TASK_TEMPLATE.ROBOT_ID, task.robotId)
            .set(ROBOT_TASK_TEMPLATE.NAME, task.taskName)
            .set(ROBOT_TASK_TEMPLATE.COMMAND_CODE, task.commandCode)
            .set(ROBOT_TASK_TEMPLATE.COMMAND_NAME, task.commandName)
            .set(ROBOT_TASK_TEMPLATE.TARGET_POINT, task.targetPoint)
            .set(ROBOT_TASK_TEMPLATE.PRIORITY, task.priority)
            .set(ROBOT_TASK_TEMPLATE.EXECUTE_TYPE, task.executeType)
            .set(ROBOT_TASK_TEMPLATE.EXECUTE_DAY, task.executeDay)
            .set(ROBOT_TASK_TEMPLATE.EXECUTE_AT, task.executeAt)
            .set(ROBOT_TASK_TEMPLATE.TASK_TYPE, task.taskType)
            .set(ROBOT_TASK_TEMPLATE.TASK_SUBTYPE, task.taskSubtype)
            .set(ROBOT_TASK_TEMPLATE.TASK_MODE, task.taskMode)
            .set(ROBOT_TASK_TEMPLATE.DIRECT_EXECUTION, task.directExecution)
            .set(ROBOT_TASK_TEMPLATE.RETURN_REQUIRED, task.returnRequired)
            .set(ROBOT_TASK_TEMPLATE.ENABLED, task.enabled)
            .set(ROBOT_TASK_TEMPLATE.REMARK, task.remark)
            .returningResult(ROBOT_TASK_TEMPLATE.ID)
            .fetchOne(ROBOT_TASK_TEMPLATE.ID)

    fun updateTaskTemplate(
        id: Long,
        task: TaskTemplateWriteData,
    ): Int =
        dsl
            .update(ROBOT_TASK_TEMPLATE)
            .set(ROBOT_TASK_TEMPLATE.SITE_ID, task.siteId)
            .set(ROBOT_TASK_TEMPLATE.ROBOT_ID, task.robotId)
            .set(ROBOT_TASK_TEMPLATE.NAME, task.taskName)
            .set(ROBOT_TASK_TEMPLATE.COMMAND_CODE, task.commandCode)
            .set(ROBOT_TASK_TEMPLATE.COMMAND_NAME, task.commandName)
            .set(ROBOT_TASK_TEMPLATE.TARGET_POINT, task.targetPoint)
            .set(ROBOT_TASK_TEMPLATE.PRIORITY, task.priority)
            .set(ROBOT_TASK_TEMPLATE.EXECUTE_TYPE, task.executeType)
            .set(ROBOT_TASK_TEMPLATE.EXECUTE_DAY, task.executeDay)
            .set(ROBOT_TASK_TEMPLATE.EXECUTE_AT, task.executeAt)
            .set(ROBOT_TASK_TEMPLATE.TASK_TYPE, task.taskType)
            .set(ROBOT_TASK_TEMPLATE.TASK_SUBTYPE, task.taskSubtype)
            .set(ROBOT_TASK_TEMPLATE.TASK_MODE, task.taskMode)
            .set(ROBOT_TASK_TEMPLATE.DIRECT_EXECUTION, task.directExecution)
            .set(ROBOT_TASK_TEMPLATE.RETURN_REQUIRED, task.returnRequired)
            .set(ROBOT_TASK_TEMPLATE.ENABLED, task.enabled)
            .set(ROBOT_TASK_TEMPLATE.REMARK, task.remark)
            .where(ROBOT_TASK_TEMPLATE.ID.eq(id))
            .execute()

    fun deleteTaskTemplateById(id: Long): Int = dsl.deleteFrom(ROBOT_TASK_TEMPLATE).where(ROBOT_TASK_TEMPLATE.ID.eq(id)).execute()

    fun insertTaskLog(taskLog: TaskLogWriteData): Long? =
        dsl
            .insertInto(ROBOT_TASK_LOG)
            .set(ROBOT_TASK_LOG.ROBOT_ID, taskLog.robotId)
            .set(ROBOT_TASK_LOG.TASK_TEMPLATE_ID, taskLog.taskTemplateId)
            .set(ROBOT_TASK_LOG.TASK_NAME, taskLog.taskName)
            .set(ROBOT_TASK_LOG.TASK_TYPE, taskLog.taskType)
            .set(ROBOT_TASK_LOG.TASK_SUBTYPE, taskLog.taskSubtype)
            .set(ROBOT_TASK_LOG.TASK_MODE, taskLog.taskMode)
            .set(ROBOT_TASK_LOG.TASK_STATUS, taskLog.taskStatus)
            .set(ROBOT_TASK_LOG.DIRECT_EXECUTION, taskLog.directExecution)
            .set(ROBOT_TASK_LOG.COMMAND_PAYLOAD, taskLog.commandPayload)
            .returningResult(ROBOT_TASK_LOG.ID)
            .fetchOne(ROBOT_TASK_LOG.ID)

    fun findAllTaskLogs(): List<TaskLogRow> =
        dsl
            .select(
                ROBOT_TASK_LOG.ID,
                ROBOT_TASK_LOG.ROBOT_ID,
                ROBOT.NAME,
                ROBOT_TASK_LOG.TASK_TEMPLATE_ID,
                ROBOT_TASK_LOG.TASK_NAME,
                ROBOT_TASK_LOG.TASK_TYPE,
                ROBOT_TASK_LOG.TASK_SUBTYPE,
                ROBOT_TASK_LOG.TASK_MODE,
                ROBOT_TASK_LOG.TASK_STATUS,
                ROBOT_TASK_LOG.DIRECT_EXECUTION,
                ROBOT_TASK_LOG.COMMAND_PAYLOAD,
                ROBOT_TASK_LOG.RETURN_PAYLOAD,
                ROBOT_TASK_LOG.CREATED_AT,
                ROBOT_TASK_LOG.STARTED_AT,
                ROBOT_TASK_LOG.FINISHED_AT,
            ).from(ROBOT_TASK_LOG)
            .leftJoin(ROBOT)
            .on(ROBOT_TASK_LOG.ROBOT_ID.eq(ROBOT.ID))
            .orderBy(ROBOT_TASK_LOG.ID.desc())
            .fetch { record ->
                TaskLogRow(
                    id = record.get(ROBOT_TASK_LOG.ID),
                    robotId = record.get(ROBOT_TASK_LOG.ROBOT_ID),
                    robotName = record.get(ROBOT.NAME),
                    taskTemplateId = record.get(ROBOT_TASK_LOG.TASK_TEMPLATE_ID),
                    taskName = record.get(ROBOT_TASK_LOG.TASK_NAME),
                    taskType = record.get(ROBOT_TASK_LOG.TASK_TYPE),
                    taskSubtype = record.get(ROBOT_TASK_LOG.TASK_SUBTYPE),
                    taskMode = record.get(ROBOT_TASK_LOG.TASK_MODE),
                    taskStatus = record.get(ROBOT_TASK_LOG.TASK_STATUS),
                    directExecution = record.get(ROBOT_TASK_LOG.DIRECT_EXECUTION),
                    commandPayload = record.get(ROBOT_TASK_LOG.COMMAND_PAYLOAD),
                    returnPayload = record.get(ROBOT_TASK_LOG.RETURN_PAYLOAD),
                    createdAt = record.get(ROBOT_TASK_LOG.CREATED_AT),
                    startedAt = record.get(ROBOT_TASK_LOG.STARTED_AT),
                    finishedAt = record.get(ROBOT_TASK_LOG.FINISHED_AT),
                )
            }
}

data class TaskTemplateRow(
    val id: Long?,
    val taskName: String?,
    val robotId: Long?,
    val robotName: String?,
    val siteId: Long?,
    val siteCode: String?,
    val siteName: String?,
    val commandCode: Long?,
    val commandName: String?,
    val targetPoint: String?,
    val priority: String?,
    val executeType: String?,
    val executeDay: String?,
    val executeAt: OffsetDateTime?,
    val taskType: String?,
    val taskSubtype: String?,
    val taskMode: String?,
    val directExecution: Boolean?,
    val returnRequired: Boolean?,
    val enabled: Boolean?,
    val remark: String?,
)

data class TaskTemplateWriteData(
    val siteId: Long,
    val robotId: Long?,
    val taskName: String?,
    val commandCode: Long?,
    val commandName: String,
    val targetPoint: String,
    val priority: String,
    val executeType: String,
    val executeDay: String,
    val executeAt: OffsetDateTime?,
    val taskType: String,
    val taskSubtype: String,
    val taskMode: String,
    val directExecution: Boolean,
    val returnRequired: Boolean,
    val enabled: Boolean,
    val remark: String,
)

data class TaskLogWriteData(
    val robotId: Long?,
    val taskTemplateId: Long?,
    val taskName: String?,
    val taskType: String?,
    val taskSubtype: String?,
    val taskMode: String?,
    val taskStatus: String,
    val directExecution: Boolean?,
    val commandPayload: String?,
)

data class TaskLogRow(
    val id: Long?,
    val robotId: Long?,
    val robotName: String?,
    val taskTemplateId: Long?,
    val taskName: String?,
    val taskType: String?,
    val taskSubtype: String?,
    val taskMode: String?,
    val taskStatus: String?,
    val directExecution: Boolean?,
    val commandPayload: String?,
    val returnPayload: String?,
    val createdAt: OffsetDateTime?,
    val startedAt: OffsetDateTime?,
    val finishedAt: OffsetDateTime?,
)
