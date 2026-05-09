package com.robotmonitor.config.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.CONFIG_TASK;
import static com.robotmonitor.jooq.generated.Tables.SYS_DEPT;

import com.robotmonitor.common.core.domain.config.ConfigTask;
import com.robotmonitor.common.jooq.GenericJooqCrudSupport;
import com.robotmonitor.config.mapper.ConfigTaskMapper;
import java.util.Arrays;
import java.util.List;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqConfigTaskMapper extends GenericJooqCrudSupport<ConfigTask> implements ConfigTaskMapper {
    public JooqConfigTaskMapper(DSLContext dsl) {
        super(dsl, CONFIG_TASK, CONFIG_TASK.ID, ConfigTask.class);
    }

    @Override
    public ConfigTask selectConfigTaskById(Long id) {
        return base()
            .where(CONFIG_TASK.ID.eq(id))
            .fetchOne(this::mapTask);
    }

    @Override
    public List<ConfigTask> selectConfigTaskList(ConfigTask query) {
        return base()
            .where(taskConditions(query).and(CONFIG_TASK.IS_DELETE.eq("0")).and(CONFIG_TASK.ID.gt(0L)))
            .fetch(this::mapTask);
    }

    @Override
    public int insertConfigTask(ConfigTask task) {
        return insert(task);
    }

    @Override
    public int updateConfigTask(ConfigTask task) {
        return update(task);
    }

    @Override
    public int deleteConfigTaskById(Long id) {
        return softDeleteById(id, CONFIG_TASK.IS_DELETE, "1");
    }

    @Override
    public int deleteConfigTaskByIds(Long[] ids) {
        return softDeleteByIds(ids, CONFIG_TASK.IS_DELETE, "1");
    }

    private org.jooq.SelectJoinStep<Record> base() {
        return dsl.select(CONFIG_TASK.fields())
            .select(SYS_DEPT.DEPT_NAME)
            .from(CONFIG_TASK)
            .leftJoin(SYS_DEPT).on(CONFIG_TASK.ROOM_CODE.eq(SYS_DEPT.ROOM_CODE));
    }

    private Condition taskConditions(ConfigTask task) {
        if (task == null) {
            return DSL.noCondition();
        }
        return DSL.and(Arrays.asList(
            likeIfPresent(CONFIG_TASK.TASK_NAME, task.getTaskName()),
            eqIfPresent(CONFIG_TASK.ROBOT_ID, task.getRobotId()),
            eqIfPresent(CONFIG_TASK.COMMAND, task.getCommand()),
            eqIfPresent(CONFIG_TASK.COMMAND_CN, task.getCommandCn()),
            eqIfPresent(CONFIG_TASK.REGION, task.getRegion()),
            eqIfPresent(CONFIG_TASK.PRIORITY, task.getPriority()),
            eqIfPresent(CONFIG_TASK.ENABLE, task.getEnable()),
            eqIfPresent(CONFIG_TASK.EXECUTE_TYPE, task.getExecuteType()),
            eqIfPresent(CONFIG_TASK.EXECUTE_DAY, task.getExecuteDay()),
            task.getExecuteTime() == null ? DSL.noCondition() : CONFIG_TASK.EXECUTE_TIME.eq(toLocalDateTime(task.getExecuteTime())),
            eqIfPresent(CONFIG_TASK.IS_RETURN, task.getIsReturn()),
            eqIfPresent(CONFIG_TASK.ROOM_CODE, task.getRoomCode()),
            eqIfPresent(CONFIG_TASK.IMG_IDS, task.getImgIds()),
            eqIfPresent(CONFIG_TASK.AUDIT_IDS, task.getAuditIds()),
            eqIfPresent(CONFIG_TASK.TASK_TYPE, task.getTaskType()),
            eqIfPresent(CONFIG_TASK.TASK_SUBTYPE, task.getTaskSubtype()),
            eqIfPresent(CONFIG_TASK.TASK_MODE, task.getTaskMode()),
            eqIfPresent(CONFIG_TASK.DIRECT_EXECUTION, task.getDirectExecution())
        ));
    }

    private ConfigTask mapTask(Record record) {
        ConfigTask task = map(record);
        task.setDeptName(record.get(SYS_DEPT.DEPT_NAME));
        return task;
    }
}
