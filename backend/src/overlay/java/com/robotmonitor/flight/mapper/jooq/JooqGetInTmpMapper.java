package com.robotmonitor.flight.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.GET_IN_TMP;

import com.robotmonitor.common.jooq.GenericJooqCrudSupport;
import com.robotmonitor.flight.domain.GetInTmp;
import com.robotmonitor.flight.mapper.GetInTmpMapper;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqGetInTmpMapper extends GenericJooqCrudSupport<GetInTmp> implements GetInTmpMapper {
    public JooqGetInTmpMapper(DSLContext dsl) {
        super(dsl, GET_IN_TMP, GET_IN_TMP.ID, GetInTmp.class);
    }

    @Override
    public GetInTmp selectGetInTmpById(Long id) {
        return selectById(id);
    }

    @Override
    public List<GetInTmp> selectGetInTmpList(GetInTmp query) {
        return selectList(query);
    }

    @Override
    public int insertGetInTmp(GetInTmp entity) {
        return insert(entity);
    }

    @Override
    public int updateGetInTmp(GetInTmp entity) {
        return update(entity);
    }

    @Override
    public int deleteGetInTmpById(Long id) {
        return deleteById(id);
    }

    @Override
    public int deleteGetInTmpByIds(Long[] ids) {
        return deleteByIds(ids);
    }
}
