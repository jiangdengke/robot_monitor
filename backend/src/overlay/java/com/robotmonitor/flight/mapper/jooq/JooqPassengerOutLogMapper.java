package com.robotmonitor.flight.mapper.jooq;

import static com.robotmonitor.flight.mapper.jooq.JooqFlightMapperSupport.toDate;
import static com.robotmonitor.flight.mapper.jooq.JooqFlightMapperSupport.toLocalDate;
import static com.robotmonitor.flight.mapper.jooq.JooqFlightMapperSupport.toLocalDateTime;
import static com.robotmonitor.flight.mapper.jooq.JooqFlightMapperSupport.toLong;
import static com.robotmonitor.jooq.generated.Tables.PASSENGER_OUT_LOG;

import com.robotmonitor.flight.domain.PassengerOutLog;
import com.robotmonitor.flight.mapper.PassengerOutLogMapper;
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
public class JooqPassengerOutLogMapper implements PassengerOutLogMapper {
    private final DSLContext dsl;

    public JooqPassengerOutLogMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public PassengerOutLog selectPassengerOutLogById(Long id) {
        return dsl.select(PASSENGER_OUT_LOG.fields())
            .from(PASSENGER_OUT_LOG)
            .where(PASSENGER_OUT_LOG.ID.eq(id))
            .fetchOne(this::mapLog);
    }

    @Override
    public List<PassengerOutLog> selectPassengerOutLogList(PassengerOutLog query) {
        return dsl.select(PASSENGER_OUT_LOG.fields())
            .from(PASSENGER_OUT_LOG)
            .where(conditions(query))
            .orderBy(PASSENGER_OUT_LOG.OUT_TIME.desc())
            .fetch(this::mapLog);
    }

    @Override
    public int insertPassengerOutLog(PassengerOutLog log) {
        Long id = dsl.insertInto(PASSENGER_OUT_LOG)
            .set(writeValues(log))
            .returningResult(PASSENGER_OUT_LOG.ID)
            .fetchOne(PASSENGER_OUT_LOG.ID);
        log.setId(id);
        return id == null ? 0 : 1;
    }

    @Override
    public int updatePassengerOutLog(PassengerOutLog log) {
        if (log.getId() == null) {
            return 0;
        }
        Map<Field<?>, Object> values = writeValues(log);
        values.remove(PASSENGER_OUT_LOG.ID);
        if (values.isEmpty()) {
            return 0;
        }
        return dsl.update(PASSENGER_OUT_LOG)
            .set(values)
            .where(PASSENGER_OUT_LOG.ID.eq(log.getId()))
            .execute();
    }

    @Override
    public int deletePassengerOutLogById(Long id) {
        return dsl.deleteFrom(PASSENGER_OUT_LOG).where(PASSENGER_OUT_LOG.ID.eq(id)).execute();
    }

    @Override
    public int deletePassengerOutLogByIds(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }
        return dsl.deleteFrom(PASSENGER_OUT_LOG).where(PASSENGER_OUT_LOG.ID.in(Arrays.asList(ids))).execute();
    }

    @Override
    public PassengerOutLog selectPassengerOutLogByCtsId(String ctsId) {
        return dsl.select(PASSENGER_OUT_LOG.fields())
            .from(PASSENGER_OUT_LOG)
            .where(PASSENGER_OUT_LOG.CTS.eq(ctsId))
            .orderBy(PASSENGER_OUT_LOG.OUT_TIME.desc())
            .limit(1)
            .fetchOne(this::mapLog);
    }

    private Condition conditions(PassengerOutLog log) {
        if (log == null) {
            return DSL.noCondition();
        }
        return DSL.and(
            eqIfPresent(PASSENGER_OUT_LOG.REID, log.getReid()),
            eqIfPresent(PASSENGER_OUT_LOG.PID, log.getPid()),
            eqIfPresent(PASSENGER_OUT_LOG.PASSENGER_ID, toLong(log.getPassengerId())),
            eqIfPresent(PASSENGER_OUT_LOG.USER_NAME, log.getUserName()),
            eqIfPresent(PASSENGER_OUT_LOG.ROOM_CODE, log.getRoomCode()),
            eqIfPresent(PASSENGER_OUT_LOG.FLIGHT_NO, log.getFlightNo()),
            eqIfPresent(PASSENGER_OUT_LOG.FLIGHT_DATE, toLocalDate(log.getFlightDate())),
            eqIfPresent(PASSENGER_OUT_LOG.RECOGNITION_TYPE, log.getRecognitionType()),
            eqIfPresent(PASSENGER_OUT_LOG.CTS, log.getCts())
        );
    }

    private Map<Field<?>, Object> writeValues(PassengerOutLog log) {
        Map<Field<?>, Object> values = new LinkedHashMap<>();
        put(values, PASSENGER_OUT_LOG.ID, log.getId());
        put(values, PASSENGER_OUT_LOG.PASSENGER_ID, toLong(log.getPassengerId()));
        put(values, PASSENGER_OUT_LOG.USER_NAME, log.getUserName());
        put(values, PASSENGER_OUT_LOG.ROOM_CODE, log.getRoomCode());
        put(values, PASSENGER_OUT_LOG.FLIGHT_NO, log.getFlightNo());
        put(values, PASSENGER_OUT_LOG.FLIGHT_DATE, toLocalDate(log.getFlightDate()));
        put(values, PASSENGER_OUT_LOG.REID, log.getReid());
        put(values, PASSENGER_OUT_LOG.PID, log.getPid());
        put(values, PASSENGER_OUT_LOG.RECOGNITION_TYPE, log.getRecognitionType());
        put(values, PASSENGER_OUT_LOG.ORI_IMAGE_URL, log.getOrigImageUrl());
        put(values, PASSENGER_OUT_LOG.REGISTER_IMAGE_URL, log.getRegisterImageUrl());
        put(values, PASSENGER_OUT_LOG.OUT_TIME, toLocalDateTime(log.getOutTime()));
        put(values, PASSENGER_OUT_LOG.CTS, log.getCts());
        return values;
    }

    private PassengerOutLog mapLog(Record record) {
        PassengerOutLog log = new PassengerOutLog();
        log.setId(record.get(PASSENGER_OUT_LOG.ID));
        log.setPassengerId(record.get(PASSENGER_OUT_LOG.PASSENGER_ID) == null ? null : String.valueOf(record.get(PASSENGER_OUT_LOG.PASSENGER_ID)));
        log.setUserName(record.get(PASSENGER_OUT_LOG.USER_NAME));
        log.setRoomCode(record.get(PASSENGER_OUT_LOG.ROOM_CODE));
        log.setFlightNo(record.get(PASSENGER_OUT_LOG.FLIGHT_NO));
        log.setFlightDate(toDate(record.get(PASSENGER_OUT_LOG.FLIGHT_DATE)));
        log.setReid(record.get(PASSENGER_OUT_LOG.REID));
        log.setPid(record.get(PASSENGER_OUT_LOG.PID));
        log.setRecognitionType(record.get(PASSENGER_OUT_LOG.RECOGNITION_TYPE));
        log.setOrigImageUrl(record.get(PASSENGER_OUT_LOG.ORI_IMAGE_URL));
        log.setRegisterImageUrl(record.get(PASSENGER_OUT_LOG.REGISTER_IMAGE_URL));
        log.setOutTime(toDate(record.get(PASSENGER_OUT_LOG.OUT_TIME)));
        log.setCts(record.get(PASSENGER_OUT_LOG.CTS));
        return log;
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
