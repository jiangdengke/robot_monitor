package org.jdk.project.service.config;

import static org.jdk.project.service.config.ConfigCommandSupport.defaultString;
import static org.jdk.project.service.config.ConfigCommandSupport.ensureUpdated;
import static org.jdk.project.service.config.ConfigCommandSupport.requiredId;
import static org.jooq.generated.project.Tables.ROBOT_TASK_LOG;
import static org.jooq.generated.project.Tables.ROBOT_TASK_TEMPLATE;

import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.config.TaskUpsertRequest;
import org.jdk.project.exception.BusinessException;
import org.jooq.DSLContext;
import org.jooq.generated.project.tables.pojos.RobotTaskTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskCommandService {

  private final DSLContext dsl;

  @Transactional
  public Long create(TaskUpsertRequest request) {
    RobotTaskTemplate task = new RobotTaskTemplate();
    task.setLoungeId(requiredId(request.getLoungeId(), "贵宾室不能为空"));
    task.setRobotId(request.getRobotId());
    task.setName(request.getTaskName());
    task.setCommandCode(request.getCommandCode());
    task.setCommandName(defaultString(request.getCommandName(), ""));
    task.setTargetRegion(defaultString(request.getTargetRegion(), ""));
    task.setPriority(defaultString(request.getPriority(), "NORMAL"));
    task.setExecuteType(defaultString(request.getExecuteType(), "IMMEDIATELY"));
    task.setExecuteDay(defaultString(request.getExecuteDay(), ""));
    task.setTaskType(defaultString(request.getTaskType(), ""));
    task.setTaskSubtype(defaultString(request.getTaskSubtype(), ""));
    task.setTaskMode(defaultString(request.getTaskMode(), ""));
    task.setDirectExecution(Boolean.TRUE.equals(request.getDirectExecution()));
    task.setReturnRequired(Boolean.TRUE.equals(request.getReturnRequired()));
    task.setEnabled(request.getEnabled() == null || request.getEnabled());
    task.setRemark(defaultString(request.getRemark(), ""));
    return dsl.insertInto(ROBOT_TASK_TEMPLATE)
        .set(dsl.newRecord(ROBOT_TASK_TEMPLATE, task))
        .returningResult(ROBOT_TASK_TEMPLATE.ID)
        .fetchOne(ROBOT_TASK_TEMPLATE.ID);
  }

  @Transactional
  public void update(Long id, TaskUpsertRequest request) {
    ensureUpdated(
        dsl.update(ROBOT_TASK_TEMPLATE)
            .set(ROBOT_TASK_TEMPLATE.LOUNGE_ID, requiredId(request.getLoungeId(), "贵宾室不能为空"))
            .set(ROBOT_TASK_TEMPLATE.ROBOT_ID, request.getRobotId())
            .set(ROBOT_TASK_TEMPLATE.NAME, request.getTaskName())
            .set(ROBOT_TASK_TEMPLATE.COMMAND_CODE, request.getCommandCode())
            .set(ROBOT_TASK_TEMPLATE.COMMAND_NAME, defaultString(request.getCommandName(), ""))
            .set(ROBOT_TASK_TEMPLATE.TARGET_REGION, defaultString(request.getTargetRegion(), ""))
            .set(ROBOT_TASK_TEMPLATE.PRIORITY, defaultString(request.getPriority(), "NORMAL"))
            .set(ROBOT_TASK_TEMPLATE.EXECUTE_TYPE, defaultString(request.getExecuteType(), "IMMEDIATELY"))
            .set(ROBOT_TASK_TEMPLATE.EXECUTE_DAY, defaultString(request.getExecuteDay(), ""))
            .set(ROBOT_TASK_TEMPLATE.TASK_TYPE, defaultString(request.getTaskType(), ""))
            .set(ROBOT_TASK_TEMPLATE.TASK_SUBTYPE, defaultString(request.getTaskSubtype(), ""))
            .set(ROBOT_TASK_TEMPLATE.TASK_MODE, defaultString(request.getTaskMode(), ""))
            .set(ROBOT_TASK_TEMPLATE.DIRECT_EXECUTION, Boolean.TRUE.equals(request.getDirectExecution()))
            .set(ROBOT_TASK_TEMPLATE.RETURN_REQUIRED, Boolean.TRUE.equals(request.getReturnRequired()))
            .set(ROBOT_TASK_TEMPLATE.ENABLED, request.getEnabled() == null || request.getEnabled())
            .set(ROBOT_TASK_TEMPLATE.REMARK, defaultString(request.getRemark(), ""))
            .where(ROBOT_TASK_TEMPLATE.ID.eq(id))
            .execute(),
        "任务不存在");
  }

  @Transactional
  public void delete(Long id) {
    dsl.deleteFrom(ROBOT_TASK_TEMPLATE).where(ROBOT_TASK_TEMPLATE.ID.eq(id)).execute();
  }

  @Transactional
  public Long run(Long id) {
    RobotTaskTemplate template =
        dsl.selectFrom(ROBOT_TASK_TEMPLATE)
            .where(ROBOT_TASK_TEMPLATE.ID.eq(id))
            .fetchOneInto(RobotTaskTemplate.class);
    if (template == null) {
      throw new BusinessException("任务不存在");
    }
    var record =
        dsl.insertInto(ROBOT_TASK_LOG)
            .set(ROBOT_TASK_LOG.ROBOT_ID, template.getRobotId())
            .set(ROBOT_TASK_LOG.TASK_TEMPLATE_ID, template.getId())
            .set(ROBOT_TASK_LOG.TASK_NAME, template.getName())
            .set(ROBOT_TASK_LOG.TASK_TYPE, template.getTaskType())
            .set(ROBOT_TASK_LOG.TASK_SUBTYPE, template.getTaskSubtype())
            .set(ROBOT_TASK_LOG.TASK_MODE, template.getTaskMode())
            .set(ROBOT_TASK_LOG.TASK_STATUS, "SUBMITTED")
            .set(ROBOT_TASK_LOG.DIRECT_EXECUTION, template.getDirectExecution())
            .set(ROBOT_TASK_LOG.COMMAND_PAYLOAD, template.getCommandName())
            .returningResult(ROBOT_TASK_LOG.ID)
            .fetchOne();
    return record == null ? null : record.get(ROBOT_TASK_LOG.ID);
  }
}
