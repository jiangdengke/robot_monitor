package com.robotmonitor.bot.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.INSP_TASK_RESULT;

import com.robotmonitor.bot.mapper.InspTaskResultMapper;
import com.robotmonitor.common.core.domain.insp.InspTaskResult;
import com.robotmonitor.common.jooq.GenericJooqCrudSupport;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqInspTaskResultMapper extends GenericJooqCrudSupport<InspTaskResult> implements InspTaskResultMapper {
    public JooqInspTaskResultMapper(DSLContext dsl) {
        super(dsl, INSP_TASK_RESULT, INSP_TASK_RESULT.ID, InspTaskResult.class);
    }

    @Override
    public InspTaskResult selectInspTaskResultById(Long id) {
        return selectById(id);
    }

    @Override
    public List<InspTaskResult> selectInspTaskResultList(InspTaskResult query) {
        return selectList(query);
    }

    @Override
    public int insertInspTaskResult(InspTaskResult result) {
        return insert(result);
    }

    @Override
    public int updateInspTaskResult(InspTaskResult result) {
        return update(result);
    }

    @Override
    public int deleteInspTaskResultById(Long id) {
        return deleteById(id);
    }

    @Override
    public int deleteInspTaskResultByIds(Long[] ids) {
        return deleteByIds(ids);
    }
}
