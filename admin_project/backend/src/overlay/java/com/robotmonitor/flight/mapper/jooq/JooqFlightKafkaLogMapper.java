package com.robotmonitor.flight.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.FLIGHT_KAFKA_LOG;

import com.robotmonitor.common.jooq.GenericJooqCrudSupport;
import com.robotmonitor.flight.domain.FlightKafkaLog;
import com.robotmonitor.flight.mapper.FlightKafkaLogMapper;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqFlightKafkaLogMapper extends GenericJooqCrudSupport<FlightKafkaLog> implements FlightKafkaLogMapper {
    public JooqFlightKafkaLogMapper(DSLContext dsl) {
        super(dsl, FLIGHT_KAFKA_LOG, FLIGHT_KAFKA_LOG.ID, FlightKafkaLog.class);
    }

    @Override
    public FlightKafkaLog selectFlightKafkaLogById(Long id) {
        return selectById(id);
    }

    @Override
    public List<FlightKafkaLog> selectFlightKafkaLogList(FlightKafkaLog query) {
        return selectList(query);
    }

    @Override
    public int insertFlightKafkaLog(FlightKafkaLog log) {
        return insert(log);
    }

    @Override
    public int updateFlightKafkaLog(FlightKafkaLog log) {
        return update(log);
    }

    @Override
    public int deleteFlightKafkaLogById(Long id) {
        return deleteById(id);
    }

    @Override
    public int deleteFlightKafkaLogByIds(Long[] ids) {
        return deleteByIds(ids);
    }
}
