package org.jdk.project.service.config

import org.jdk.project.dto.config.TaskUpsertRequest
import org.jdk.project.exception.BusinessException
import org.jdk.project.service.config.ConfigCommandSupport.defaultString
import org.jdk.project.service.config.ConfigCommandSupport.ensureUpdated
import org.jdk.project.service.config.ConfigCommandSupport.requiredId
import org.jooq.DSLContext
import org.jooq.generated.project.Tables.ROBOT_TASK_LOG
import org.jooq.generated.project.Tables.ROBOT_TASK_TEMPLATE
import org.jooq.generated.project.tables.pojos.RobotTaskTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TaskCommandService(
    private val dsl: DSLContext,
) {
    @Transactional
    fun create(request: TaskUpsertRequest): Long? =
        dsl.insertInto(ROBOT_TASK_TEMPLATE)
            .set(ROBOT_TASK_TEMPLATE.LOUNGE_ID, requiredId(request.loungeId, "贵宾室不能为空"))
            .set(ROBOT_TASK_TEMPLATE.ROBOT_ID, request.robotId)
            .set(ROBOT_TASK_TEMPLATE.NAME, request.taskName)
            .set(ROBOT_TASK_TEMPLATE.COMMAND_CODE, request.commandCode)
            .set(ROBOT_TASK_TEMPLATE.COMMAND_NAME, defaultString(request.commandName, ""))
            .set(ROBOT_TASK_TEMPLATE.TARGET_REGION, defaultString(request.targetRegion, ""))
            .set(ROBOT_TASK_TEMPLATE.PRIORITY, defaultString(request.priority, "NORMAL"))
            .set(ROBOT_TASK_TEMPLATE.EXECUTE_TYPE, defaultString(request.executeType, "IMMEDIATELY"))
            .set(ROBOT_TASK_TEMPLATE.EXECUTE_DAY, defaultString(request.executeDay, ""))
            .set(ROBOT_TASK_TEMPLATE.TASK_TYPE, defaultString(request.taskType, ""))
            .set(ROBOT_TASK_TEMPLATE.TASK_SUBTYPE, defaultString(request.taskSubtype, ""))
            .set(ROBOT_TASK_TEMPLATE.TASK_MODE, defaultString(request.taskMode, ""))
            .set(ROBOT_TASK_TEMPLATE.DIRECT_EXECUTION, request.directExecution == true)
            .set(ROBOT_TASK_TEMPLATE.RETURN_REQUIRED, request.returnRequired == true)
            .set(ROBOT_TASK_TEMPLATE.ENABLED, request.enabled ?: true)
            .set(ROBOT_TASK_TEMPLATE.REMARK, defaultString(request.remark, ""))
            .returningResult(ROBOT_TASK_TEMPLATE.ID)
            .fetchOne(ROBOT_TASK_TEMPLATE.ID)

    @Transactional
    fun update(id: Long, request: TaskUpsertRequest) {
        ensureUpdated(
            dsl.update(ROBOT_TASK_TEMPLATE)
                .set(ROBOT_TASK_TEMPLATE.LOUNGE_ID, requiredId(request.loungeId, "贵宾室不能为空"))
                .set(ROBOT_TASK_TEMPLATE.ROBOT_ID, request.robotId)
                .set(ROBOT_TASK_TEMPLATE.NAME, request.taskName)
                .set(ROBOT_TASK_TEMPLATE.COMMAND_CODE, request.commandCode)
                .set(ROBOT_TASK_TEMPLATE.COMMAND_NAME, defaultString(request.commandName, ""))
                .set(ROBOT_TASK_TEMPLATE.TARGET_REGION, defaultString(request.targetRegion, ""))
                .set(ROBOT_TASK_TEMPLATE.PRIORITY, defaultString(request.priority, "NORMAL"))
                .set(ROBOT_TASK_TEMPLATE.EXECUTE_TYPE, defaultString(request.executeType, "IMMEDIATELY"))
                .set(ROBOT_TASK_TEMPLATE.EXECUTE_DAY, defaultString(request.executeDay, ""))
                .set(ROBOT_TASK_TEMPLATE.TASK_TYPE, defaultString(request.taskType, ""))
                .set(ROBOT_TASK_TEMPLATE.TASK_SUBTYPE, defaultString(request.taskSubtype, ""))
                .set(ROBOT_TASK_TEMPLATE.TASK_MODE, defaultString(request.taskMode, ""))
                .set(ROBOT_TASK_TEMPLATE.DIRECT_EXECUTION, request.directExecution == true)
                .set(ROBOT_TASK_TEMPLATE.RETURN_REQUIRED, request.returnRequired == true)
                .set(ROBOT_TASK_TEMPLATE.ENABLED, request.enabled ?: true)
                .set(ROBOT_TASK_TEMPLATE.REMARK, defaultString(request.remark, ""))
                .where(ROBOT_TASK_TEMPLATE.ID.eq(id))
                .execute(),
            "任务不存在",
        )
    }

    @Transactional
    fun delete(id: Long) {
        dsl.deleteFrom(ROBOT_TASK_TEMPLATE).where(ROBOT_TASK_TEMPLATE.ID.eq(id)).execute()
    }

    @Transactional
    fun run(id: Long): Long? {
        val template =
            dsl.selectFrom(ROBOT_TASK_TEMPLATE)
                .where(ROBOT_TASK_TEMPLATE.ID.eq(id))
                .fetchOneInto(RobotTaskTemplate::class.java)
                ?: throw BusinessException("任务不存在")
        val record =
            dsl.insertInto(ROBOT_TASK_LOG)
                .set(ROBOT_TASK_LOG.ROBOT_ID, template.robotId)
                .set(ROBOT_TASK_LOG.TASK_TEMPLATE_ID, template.id)
                .set(ROBOT_TASK_LOG.TASK_NAME, template.name)
                .set(ROBOT_TASK_LOG.TASK_TYPE, template.taskType)
                .set(ROBOT_TASK_LOG.TASK_SUBTYPE, template.taskSubtype)
                .set(ROBOT_TASK_LOG.TASK_MODE, template.taskMode)
                .set(ROBOT_TASK_LOG.TASK_STATUS, "SUBMITTED")
                .set(ROBOT_TASK_LOG.DIRECT_EXECUTION, template.directExecution)
                .set(ROBOT_TASK_LOG.COMMAND_PAYLOAD, template.commandName)
                .returningResult(ROBOT_TASK_LOG.ID)
                .fetchOne()
        return record?.get(ROBOT_TASK_LOG.ID)
    }
}
