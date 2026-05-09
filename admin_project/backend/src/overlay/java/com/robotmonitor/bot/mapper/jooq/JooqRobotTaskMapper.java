package com.robotmonitor.bot.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.ROBOT_TASK;

import com.robotmonitor.bot.mapper.RobotTaskMapper;
import com.robotmonitor.common.core.domain.robot.RobotTask;
import com.robotmonitor.common.jooq.GenericJooqCrudSupport;
import java.util.List;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqRobotTaskMapper extends GenericJooqCrudSupport<RobotTask> implements RobotTaskMapper {
    public JooqRobotTaskMapper(DSLContext dsl) {
        super(dsl, ROBOT_TASK, ROBOT_TASK.ID, RobotTask.class);
    }

    @Override
    public RobotTask selectRobotTaskById(Long id) {
        return selectById(id);
    }

    @Override
    public List<RobotTask> selectRobotTaskList(RobotTask query) {
        return dsl.select(ROBOT_TASK.fields())
            .from(ROBOT_TASK)
            .where(conditions(query))
            .orderBy(ROBOT_TASK.CREATE_TIME.desc())
            .fetch(this::map);
    }

    @Override
    public int insertRobotTask(RobotTask task) {
        return insert(task);
    }

    @Override
    public int updateRobotTask(RobotTask task) {
        return update(task);
    }

    @Override
    public int deleteRobotTaskById(Long id) {
        return deleteById(id);
    }

    @Override
    public int deleteRobotTaskByIds(Long[] ids) {
        return deleteByIds(ids);
    }

    @Override
    public List<RobotTask> findTaskListByRobotId(String robotId) {
        return dsl.select(ROBOT_TASK.fields())
            .from(ROBOT_TASK)
            .where(ROBOT_TASK.ROBOT_ID.eq(robotId))
            .and(ROBOT_TASK.TASK_STATUS.in("0", "1", "2", "3"))
            .orderBy(ROBOT_TASK.CREATE_TIME.asc())
            .fetch(this::map);
    }

    private Condition conditions(RobotTask task) {
        if (task == null) {
            return DSL.noCondition();
        }
        return DSL.and(
            eqIfPresent(ROBOT_TASK.ROBOT_ID, task.getRobotId()),
            eqIfPresent(ROBOT_TASK.TASK_ID, task.getTaskId()),
            likeIfPresent(ROBOT_TASK.TASK_NAME, task.getTaskName()),
            eqIfPresent(ROBOT_TASK.TASK_TYPE, task.getTaskType()),
            eqIfPresent(ROBOT_TASK.TASK_SUBTYPE, task.getTaskSubtype()),
            eqIfPresent(ROBOT_TASK.TASK_MODE, task.getTaskMode()),
            eqIfPresent(ROBOT_TASK.TASK_STATUS, task.getTaskStatus()),
            eqIfPresent(ROBOT_TASK.DIRECT_EXECUTION, task.getDirectExecution()),
            eqIfPresent(ROBOT_TASK.START_TIME, toLocalDateTime(task.getStartTime())),
            eqIfPresent(ROBOT_TASK.END_TIME, toLocalDateTime(task.getEndTime())),
            eqIfPresent(ROBOT_TASK.RETURN_INFO, task.getReturnInfo()),
            eqIfPresent(ROBOT_TASK.CMD, task.getCmd())
        );
    }
}
