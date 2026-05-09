package com.robotmonitor.flight.mapper.jooq;

import static com.robotmonitor.flight.mapper.jooq.JooqFlightMapperSupport.isBlank;
import static com.robotmonitor.flight.mapper.jooq.JooqFlightMapperSupport.toDate;
import static com.robotmonitor.flight.mapper.jooq.JooqFlightMapperSupport.toLocalDate;
import static com.robotmonitor.flight.mapper.jooq.JooqFlightMapperSupport.toLocalDateTime;
import static com.robotmonitor.jooq.generated.Tables.FLIGHT_INFO;
import static com.robotmonitor.jooq.generated.Tables.FLIGHT_WARNING;
import static com.robotmonitor.jooq.generated.Tables.PASSENGER;

import com.robotmonitor.flight.domain.FlightWarning;
import com.robotmonitor.flight.mapper.FlightWarningMapper;
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
public class JooqFlightWarningMapper implements FlightWarningMapper {
    private final DSLContext dsl;

    public JooqFlightWarningMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public FlightWarning selectFlightWarningById(Long id) {
        return dsl.select(FLIGHT_WARNING.fields())
            .from(FLIGHT_WARNING)
            .where(FLIGHT_WARNING.ID.eq(id))
            .fetchOne(this::mapWarning);
    }

    @Override
    public List<FlightWarning> selectFlightWarningList(FlightWarning query) {
        return dsl.select(FLIGHT_WARNING.fields())
            .from(FLIGHT_WARNING)
            .where(conditions(query))
            .orderBy(FLIGHT_WARNING.CREATE_TIME.desc())
            .fetch(this::mapWarning);
    }

    @Override
    public int insertFlightWarning(FlightWarning warning) {
        Long id = dsl.insertInto(FLIGHT_WARNING)
            .set(writeValues(warning))
            .returningResult(FLIGHT_WARNING.ID)
            .fetchOne(FLIGHT_WARNING.ID);
        warning.setId(id);
        return id == null ? 0 : 1;
    }

    @Override
    public int updateFlightWarning(FlightWarning warning) {
        if (warning.getId() == null) {
            return 0;
        }
        Map<Field<?>, Object> values = writeValues(warning);
        values.remove(FLIGHT_WARNING.ID);
        if (values.isEmpty()) {
            return 0;
        }
        return dsl.update(FLIGHT_WARNING)
            .set(values)
            .where(FLIGHT_WARNING.ID.eq(warning.getId()))
            .execute();
    }

    @Override
    public int deleteFlightWarningById(Long id) {
        return dsl.deleteFrom(FLIGHT_WARNING).where(FLIGHT_WARNING.ID.eq(id)).execute();
    }

    @Override
    public int deleteFlightWarningByIds(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }
        return dsl.deleteFrom(FLIGHT_WARNING).where(FLIGHT_WARNING.ID.in(Arrays.asList(ids))).execute();
    }

    @Override
    public List<FlightWarning> selectCurrentFlightWarningList(String dNow) {
        return dsl.select(
                FLIGHT_WARNING.ID,
                FLIGHT_WARNING.FLIGHT_ID,
                FLIGHT_WARNING.WARNING_TYPE,
                FLIGHT_WARNING.CHANGE_BEFORE,
                FLIGHT_WARNING.CHANGE_AFTER,
                FLIGHT_WARNING.CREATE_TIME,
                FLIGHT_INFO.FLIGHT_NO,
                PASSENGER.USER_NAME,
                PASSENGER.ID,
                PASSENGER.REGION_ID
            )
            .from(FLIGHT_WARNING)
            .leftJoin(FLIGHT_INFO).on(FLIGHT_WARNING.FLIGHT_ID.eq(FLIGHT_INFO.FLIGHT_ID))
            .leftJoin(PASSENGER).on(PASSENGER.FLIGHT_ID.eq(FLIGHT_WARNING.FLIGHT_ID))
            .where(FLIGHT_WARNING.CREATE_TIME.ge(toLocalDate(dNow).atStartOfDay()))
            .orderBy(FLIGHT_WARNING.CREATE_TIME.desc())
            .fetch(record -> {
                FlightWarning warning = mapWarning(record);
                warning.setFlightNo(record.get(FLIGHT_INFO.FLIGHT_NO));
                warning.setUserName(record.get(PASSENGER.USER_NAME));
                warning.setPassengerId(record.get(PASSENGER.ID));
                warning.setRegionId(record.get(PASSENGER.REGION_ID));
                return warning;
            });
    }

    private Condition conditions(FlightWarning warning) {
        if (warning == null) {
            return DSL.noCondition();
        }
        return DSL.and(
            eqIfPresent(FLIGHT_WARNING.FLIGHT_ID, warning.getFlightId()),
            eqIfPresent(FLIGHT_WARNING.WARNING_TYPE, warning.getWarningType()),
            eqIfPresent(FLIGHT_WARNING.CHANGE_BEFORE, warning.getChangeBefore()),
            eqIfPresent(FLIGHT_WARNING.CHANGE_AFTER, warning.getChangeAfter())
        );
    }

    private Map<Field<?>, Object> writeValues(FlightWarning warning) {
        Map<Field<?>, Object> values = new LinkedHashMap<>();
        put(values, FLIGHT_WARNING.ID, warning.getId());
        put(values, FLIGHT_WARNING.FLIGHT_ID, warning.getFlightId());
        put(values, FLIGHT_WARNING.FLIGHT_NO, warning.getFlightNo());
        put(values, FLIGHT_WARNING.PASSENGER_ID, warning.getPassengerId());
        put(values, FLIGHT_WARNING.USER_NAME, warning.getUserName());
        put(values, FLIGHT_WARNING.REGION_ID, warning.getRegionId());
        put(values, FLIGHT_WARNING.WARNING_TYPE, warning.getWarningType());
        put(values, FLIGHT_WARNING.CHANGE_BEFORE, warning.getChangeBefore());
        put(values, FLIGHT_WARNING.CHANGE_AFTER, warning.getChangeAfter());
        put(values, FLIGHT_WARNING.CREATE_TIME, toLocalDateTime(warning.getCreateTime()));
        return values;
    }

    private FlightWarning mapWarning(Record record) {
        FlightWarning warning = new FlightWarning();
        warning.setId(record.get(FLIGHT_WARNING.ID));
        warning.setFlightId(record.get(FLIGHT_WARNING.FLIGHT_ID));
        warning.setFlightNo(record.get(FLIGHT_WARNING.FLIGHT_NO));
        warning.setPassengerId(record.get(FLIGHT_WARNING.PASSENGER_ID));
        warning.setUserName(record.get(FLIGHT_WARNING.USER_NAME));
        warning.setRegionId(record.get(FLIGHT_WARNING.REGION_ID));
        warning.setWarningType(record.get(FLIGHT_WARNING.WARNING_TYPE));
        warning.setChangeBefore(record.get(FLIGHT_WARNING.CHANGE_BEFORE));
        warning.setChangeAfter(record.get(FLIGHT_WARNING.CHANGE_AFTER));
        warning.setCreateTime(toDate(record.get(FLIGHT_WARNING.CREATE_TIME)));
        return warning;
    }

    private Condition eqIfPresent(Field<?> field, Object value) {
        if (value == null || value instanceof String text && text.isBlank()) {
            return DSL.noCondition();
        }
        return ((Field<Object>) field).eq(value);
    }

    private void put(Map<Field<?>, Object> values, Field<?> field, Object value) {
        if (value != null) {
            values.put(field, value);
        }
    }
}
