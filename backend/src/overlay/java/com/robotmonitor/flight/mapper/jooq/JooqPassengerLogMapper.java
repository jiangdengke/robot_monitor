package com.robotmonitor.flight.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.PASSENGER_LOG;

import com.robotmonitor.common.jooq.GenericJooqCrudSupport;
import com.robotmonitor.flight.domain.PassengerLog;
import com.robotmonitor.flight.mapper.PassengerLogMapper;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqPassengerLogMapper extends GenericJooqCrudSupport<PassengerLog> implements PassengerLogMapper {
    public JooqPassengerLogMapper(DSLContext dsl) {
        super(dsl, PASSENGER_LOG, PASSENGER_LOG.ID, PassengerLog.class);
    }

    @Override
    public PassengerLog selectPassengerLogById(Long id) {
        return selectById(id);
    }

    @Override
    public List<PassengerLog> selectPassengerLogList(PassengerLog query) {
        return selectList(query);
    }

    @Override
    public int insertPassengerLog(PassengerLog log) {
        return insert(log);
    }

    @Override
    public int updatePassengerLog(PassengerLog log) {
        return update(log);
    }

    @Override
    public int deletePassengerLogById(Long id) {
        return deleteById(id);
    }

    @Override
    public int deletePassengerLogByIds(Long[] ids) {
        return deleteByIds(ids);
    }
}
