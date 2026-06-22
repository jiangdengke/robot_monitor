package org.jdk.project.service.configquery

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.config.TaskDto
import org.jooq.DSLContext
import org.jooq.generated.project.Tables.LOUNGE
import org.jooq.generated.project.Tables.ROBOT
import org.jooq.generated.project.Tables.ROBOT_TASK_TEMPLATE
import org.springframework.stereotype.Service

@Service
class TaskConfigQueryService(
    private val dsl: DSLContext,
    private val mapper: ConfigQueryMapper,
) {
    fun listTaskTemplates(): ListResponse<TaskDto> {
        val rows =
            dsl.select(
                ROBOT_TASK_TEMPLATE.ID,
                ROBOT_TASK_TEMPLATE.NAME,
                ROBOT_TASK_TEMPLATE.ROBOT_ID,
                ConfigQueryMapper.ROBOT_NAME,
                LOUNGE.CODE,
                ConfigQueryMapper.LOUNGE_NAME,
                ROBOT_TASK_TEMPLATE.COMMAND_CODE,
                ROBOT_TASK_TEMPLATE.COMMAND_NAME,
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
                .join(LOUNGE).on(ROBOT_TASK_TEMPLATE.LOUNGE_ID.eq(LOUNGE.ID))
                .leftJoin(ROBOT).on(ROBOT_TASK_TEMPLATE.ROBOT_ID.eq(ROBOT.ID))
                .orderBy(ROBOT_TASK_TEMPLATE.ID.asc())
                .fetch { record -> mapper.toTaskDto(record) }
        return ListResponse.of(rows.size.toLong(), rows)
    }
}
