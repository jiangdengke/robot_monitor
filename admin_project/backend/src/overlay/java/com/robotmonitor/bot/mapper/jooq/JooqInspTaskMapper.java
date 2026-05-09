package com.robotmonitor.bot.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.INSP_TASK;

import com.robotmonitor.bot.mapper.InspTaskMapper;
import com.robotmonitor.common.core.domain.insp.InspTask;
import com.robotmonitor.common.jooq.GenericJooqCrudSupport;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqInspTaskMapper extends GenericJooqCrudSupport<InspTask> implements InspTaskMapper {
    public JooqInspTaskMapper(DSLContext dsl) {
        super(dsl, INSP_TASK, INSP_TASK.ID, InspTask.class);
    }

    @Override
    public InspTask selectInspTaskById(Long id) {
        return selectById(id);
    }

    @Override
    public List<InspTask> selectInspTaskList(InspTask query) {
        return selectList(query);
    }

    @Override
    public int insertInspTask(InspTask task) {
        return insert(task);
    }

    @Override
    public int updateInspTask(InspTask task) {
        return update(task);
    }

    @Override
    public int deleteInspTaskById(Long id) {
        return deleteById(id);
    }

    @Override
    public int deleteInspTaskByIds(Long[] ids) {
        return deleteByIds(ids);
    }
}
