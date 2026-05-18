package org.jdk.project.service.configquery;

import static org.jooq.generated.project.Tables.LOUNGE;
import static org.jooq.generated.project.Tables.ROBOT;
import static org.jooq.generated.project.Tables.ROBOT_TASK_TEMPLATE;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.config.TaskDto;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskConfigQueryService {

  private final DSLContext dsl;
  private final ConfigQueryMapper mapper;

  public ListResponse<TaskDto> listTaskTemplates() {
    List<TaskDto> rows =
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
                ROBOT_TASK_TEMPLATE.REMARK)
            .from(ROBOT_TASK_TEMPLATE)
            .join(LOUNGE)
            .on(ROBOT_TASK_TEMPLATE.LOUNGE_ID.eq(LOUNGE.ID))
            .leftJoin(ROBOT)
            .on(ROBOT_TASK_TEMPLATE.ROBOT_ID.eq(ROBOT.ID))
            .orderBy(ROBOT_TASK_TEMPLATE.ID.asc())
            .fetch(mapper::toTaskDto);
    return ListResponse.of(rows.size(), rows);
  }
}
