package com.robotmonitor.flight.mapper.jooq;

import static com.robotmonitor.flight.mapper.jooq.JooqFlightMapperSupport.isBlank;
import static com.robotmonitor.flight.mapper.jooq.JooqFlightMapperSupport.toCompactDateTime;
import static com.robotmonitor.flight.mapper.jooq.JooqFlightMapperSupport.toLocalDate;
import static com.robotmonitor.flight.mapper.jooq.JooqFlightMapperSupport.toLocalDateTime;
import static com.robotmonitor.jooq.generated.Tables.FLIGHT_GATE;

import com.robotmonitor.flight.domain.FlightGate;
import com.robotmonitor.flight.mapper.FlightGateMapper;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqFlightGateMapper implements FlightGateMapper {
    private final DSLContext dsl;

    public JooqFlightGateMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public FlightGate selectFlightGateByFlightId(String flightId) {
        return dsl.select(FLIGHT_GATE.fields())
            .from(FLIGHT_GATE)
            .where(FLIGHT_GATE.FLIGHT_ID.eq(flightId))
            .fetchOne(this::mapGate);
    }

    @Override
    public List<FlightGate> selectFlightGateList(FlightGate query) {
        return dsl.select(FLIGHT_GATE.fields())
            .from(FLIGHT_GATE)
            .where(conditions(query))
            .orderBy(FLIGHT_GATE.UPDATE_TIME.desc())
            .fetch(this::mapGate);
    }

    @Override
    public int insertFlightGate(FlightGate gate) {
        if (isBlank(gate.getFlightId())) {
            return 0;
        }
        return dsl.insertInto(FLIGHT_GATE)
            .set(writeValues(gate))
            .execute();
    }

    @Override
    public int updateFlightGate(FlightGate gate) {
        if (isBlank(gate.getFlightId())) {
            return 0;
        }
        Map<Field<?>, Object> values = writeValues(gate);
        values.remove(FLIGHT_GATE.FLIGHT_ID);
        if (values.isEmpty()) {
            return 0;
        }
        return dsl.update(FLIGHT_GATE)
            .set(values)
            .where(FLIGHT_GATE.FLIGHT_ID.eq(gate.getFlightId()))
            .execute();
    }

    @Override
    public int deleteFlightGateByFlightId(String flightId) {
        return dsl.deleteFrom(FLIGHT_GATE)
            .where(FLIGHT_GATE.FLIGHT_ID.eq(flightId))
            .execute();
    }

    @Override
    public int deleteFlightGateByFlightIds(String[] flightIds) {
        if (flightIds == null || flightIds.length == 0) {
            return 0;
        }
        return dsl.deleteFrom(FLIGHT_GATE)
            .where(FLIGHT_GATE.FLIGHT_ID.in(Arrays.asList(flightIds)))
            .execute();
    }

    private Condition conditions(FlightGate gate) {
        if (gate == null) {
            return DSL.noCondition();
        }
        return DSL.and(
            eqIfPresent(FLIGHT_GATE.FLIGHT_ID, gate.getFlightId()),
            eqIfPresent(FLIGHT_GATE.GATE_CD, gate.getGateCd()),
            eqIfPresent(FLIGHT_GATE.GATE_ATTR, gate.getGateAttr()),
            eqIfPresent(FLIGHT_GATE.ESTM_START_TIME, toLocalDateTime(gate.getEstmStartTime())),
            eqIfPresent(FLIGHT_GATE.ESTM_END_TIME, toLocalDateTime(gate.getEstmEndTime())),
            eqIfPresent(FLIGHT_GATE.TERMINAL_CD, gate.getTerminalCd()),
            eqIfPresent(FLIGHT_GATE.SEND_TIME, toLocalDateTime(gate.getSendTime())),
            eqIfPresent(FLIGHT_GATE.SCHE_EXEC_DATE, toLocalDate(gate.getScheExecDate()))
        );
    }

    private Map<Field<?>, Object> writeValues(FlightGate gate) {
        Map<Field<?>, Object> values = new LinkedHashMap<>();
        put(values, FLIGHT_GATE.FLIGHT_ID, gate.getFlightId());
        put(values, FLIGHT_GATE.GATE_CD, gate.getGateCd());
        put(values, FLIGHT_GATE.GATE_ATTR, gate.getGateAttr());
        put(values, FLIGHT_GATE.ESTM_START_TIME, toLocalDateTime(gate.getEstmStartTime()));
        put(values, FLIGHT_GATE.ESTM_END_TIME, toLocalDateTime(gate.getEstmEndTime()));
        put(values, FLIGHT_GATE.TERMINAL_CD, gate.getTerminalCd());
        put(values, FLIGHT_GATE.SEND_TIME, toLocalDateTime(gate.getSendTime()));
        put(values, FLIGHT_GATE.SCHE_EXEC_DATE, toLocalDate(gate.getScheExecDate()));
        put(values, FLIGHT_GATE.UPDATE_TIME, toLocalDateTime(gate.getUpdateTime()));
        return values;
    }

    private FlightGate mapGate(Record record) {
        FlightGate gate = new FlightGate();
        gate.setFlightId(record.get(FLIGHT_GATE.FLIGHT_ID));
        gate.setGateCd(record.get(FLIGHT_GATE.GATE_CD));
        gate.setGateAttr(record.get(FLIGHT_GATE.GATE_ATTR));
        gate.setEstmStartTime(toCompactDateTime(record.get(FLIGHT_GATE.ESTM_START_TIME)));
        gate.setEstmEndTime(toCompactDateTime(record.get(FLIGHT_GATE.ESTM_END_TIME)));
        gate.setTerminalCd(record.get(FLIGHT_GATE.TERMINAL_CD));
        gate.setSendTime(toCompactDateTime(record.get(FLIGHT_GATE.SEND_TIME)));
        gate.setScheExecDate(record.get(FLIGHT_GATE.SCHE_EXEC_DATE) == null ? null : record.get(FLIGHT_GATE.SCHE_EXEC_DATE).toString());
        gate.setUpdateTime(JooqFlightMapperSupport.toDate(record.get(FLIGHT_GATE.UPDATE_TIME)));
        return gate;
    }

    private Condition eqIfPresent(Field<?> field, Object value) {
        if (value == null || value instanceof String text && text.isBlank()) {
            return DSL.noCondition();
        }
        return ((Field<Object>) field).eq(value);
    }

    private void put(Map<Field<?>, Object> values, Field<?> field, Object value) {
        if (value != null && (!(value instanceof String text) || !text.isBlank())) {
            values.put(field, value);
        }
    }
}
