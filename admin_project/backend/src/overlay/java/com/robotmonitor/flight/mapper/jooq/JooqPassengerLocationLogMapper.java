package com.robotmonitor.flight.mapper.jooq;

import static com.robotmonitor.flight.mapper.jooq.JooqFlightMapperSupport.stringValue;
import static com.robotmonitor.flight.mapper.jooq.JooqFlightMapperSupport.toDate;
import static com.robotmonitor.flight.mapper.jooq.JooqFlightMapperSupport.toLocalDate;
import static com.robotmonitor.flight.mapper.jooq.JooqFlightMapperSupport.toLocalDateTime;
import static com.robotmonitor.flight.mapper.jooq.JooqFlightMapperSupport.toLong;
import static com.robotmonitor.jooq.generated.Tables.CONFIG_REGION;
import static com.robotmonitor.jooq.generated.Tables.PASSENGER_LOCATION_LOG;

import com.robotmonitor.flight.domain.PassengerLocationLog;
import com.robotmonitor.flight.mapper.PassengerLocationLogMapper;
import java.time.LocalDate;
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
public class JooqPassengerLocationLogMapper implements PassengerLocationLogMapper {
    private final DSLContext dsl;

    public JooqPassengerLocationLogMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public PassengerLocationLog selectPassengerLocationLogById(Long id) {
        return dsl.select(PASSENGER_LOCATION_LOG.fields())
            .from(PASSENGER_LOCATION_LOG)
            .where(PASSENGER_LOCATION_LOG.ID.eq(id))
            .fetchOne(this::mapLog);
    }

    @Override
    public List<PassengerLocationLog> selectPassengerLocationLogList(PassengerLocationLog query) {
        return dsl.select(PASSENGER_LOCATION_LOG.fields())
            .from(PASSENGER_LOCATION_LOG)
            .where(logConditions(query))
            .fetch(this::mapLog);
    }

    @Override
    public int insertPassengerLocationLog(PassengerLocationLog log) {
        Long id = dsl.insertInto(PASSENGER_LOCATION_LOG)
            .set(PASSENGER_LOCATION_LOG.PID, log.getPid())
            .set(PASSENGER_LOCATION_LOG.REID, log.getReid())
            .set(PASSENGER_LOCATION_LOG.PASSENGER_ID, toLong(log.getPassengerId()))
            .set(PASSENGER_LOCATION_LOG.USER_NAME, log.getUserName())
            .set(PASSENGER_LOCATION_LOG.ROOM_CODE, log.getRoomCode())
            .set(PASSENGER_LOCATION_LOG.FLIGHT_NO, log.getFlightNo())
            .set(PASSENGER_LOCATION_LOG.FLIGHT_DATE, toLocalDate(log.getFlightDate()))
            .set(PASSENGER_LOCATION_LOG.REGION_ID, log.getRegionId() == null ? null : log.getRegionId().longValue())
            .set(PASSENGER_LOCATION_LOG.REGION_NAME, log.getRegionName())
            .set(PASSENGER_LOCATION_LOG.COORDINATE, log.getCoordinate())
            .set(PASSENGER_LOCATION_LOG.DEVICE_ID, toLong(log.getDeviceId()))
            .set(PASSENGER_LOCATION_LOG.DEVICE_NAME, log.getDeviceName())
            .set(PASSENGER_LOCATION_LOG.DEEP_GLINT_DEVICE_ID, log.getDeepGlintDeviceId())
            .set(PASSENGER_LOCATION_LOG.RECOGNITION_TYPE, log.getRecognitionType())
            .set(PASSENGER_LOCATION_LOG.CTS, log.getCts())
            .set(PASSENGER_LOCATION_LOG.ORI_IMAGE_URL, log.getOriImageUrl())
            .set(PASSENGER_LOCATION_LOG.REGISTER_IMAGE_URL, log.getRegisterImageUrl())
            .set(PASSENGER_LOCATION_LOG.IS_OUT, log.getIsOut())
            .set(PASSENGER_LOCATION_LOG.CREATE_TIME, toLocalDateTime(log.getCreateTime()))
            .returningResult(PASSENGER_LOCATION_LOG.ID)
            .fetchOne(PASSENGER_LOCATION_LOG.ID);
        log.setId(id);
        return id == null ? 0 : 1;
    }

    @Override
    public int updatePassengerLocationLog(PassengerLocationLog log) {
        if (log.getId() == null) {
            return 0;
        }
        Map<Field<?>, Object> values = new LinkedHashMap<>();
        put(values, PASSENGER_LOCATION_LOG.PID, log.getPid());
        put(values, PASSENGER_LOCATION_LOG.REID, log.getReid());
        put(values, PASSENGER_LOCATION_LOG.PASSENGER_ID, toLong(log.getPassengerId()));
        put(values, PASSENGER_LOCATION_LOG.USER_NAME, log.getUserName());
        put(values, PASSENGER_LOCATION_LOG.ROOM_CODE, log.getRoomCode());
        put(values, PASSENGER_LOCATION_LOG.FLIGHT_NO, log.getFlightNo());
        put(values, PASSENGER_LOCATION_LOG.FLIGHT_DATE, toLocalDate(log.getFlightDate()));
        put(values, PASSENGER_LOCATION_LOG.REGION_ID, log.getRegionId() == null ? null : log.getRegionId().longValue());
        put(values, PASSENGER_LOCATION_LOG.REGION_NAME, log.getRegionName());
        put(values, PASSENGER_LOCATION_LOG.COORDINATE, log.getCoordinate());
        put(values, PASSENGER_LOCATION_LOG.DEVICE_ID, toLong(log.getDeviceId()));
        put(values, PASSENGER_LOCATION_LOG.DEVICE_NAME, log.getDeviceName());
        put(values, PASSENGER_LOCATION_LOG.DEEP_GLINT_DEVICE_ID, log.getDeepGlintDeviceId());
        put(values, PASSENGER_LOCATION_LOG.RECOGNITION_TYPE, log.getRecognitionType());
        put(values, PASSENGER_LOCATION_LOG.CTS, log.getCts());
        put(values, PASSENGER_LOCATION_LOG.ORI_IMAGE_URL, log.getOriImageUrl());
        put(values, PASSENGER_LOCATION_LOG.REGISTER_IMAGE_URL, log.getRegisterImageUrl());
        put(values, PASSENGER_LOCATION_LOG.IS_OUT, log.getIsOut());
        put(values, PASSENGER_LOCATION_LOG.CREATE_TIME, toLocalDateTime(log.getCreateTime()));
        if (values.isEmpty()) {
            return 0;
        }
        return dsl.update(PASSENGER_LOCATION_LOG)
            .set(values)
            .where(PASSENGER_LOCATION_LOG.ID.eq(log.getId()))
            .execute();
    }

    @Override
    public int deletePassengerLocationLogById(Long id) {
        return dsl.deleteFrom(PASSENGER_LOCATION_LOG).where(PASSENGER_LOCATION_LOG.ID.eq(id)).execute();
    }

    @Override
    public int deletePassengerLocationLogByIds(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }
        return dsl.deleteFrom(PASSENGER_LOCATION_LOG).where(PASSENGER_LOCATION_LOG.ID.in(Arrays.asList(ids))).execute();
    }

    @Override
    public PassengerLocationLog selectPassengerLocationLogByCtsId(String cts) {
        return dsl.select(PASSENGER_LOCATION_LOG.fields())
            .from(PASSENGER_LOCATION_LOG)
            .where(PASSENGER_LOCATION_LOG.CTS.eq(cts))
            .limit(1)
            .fetchOne(this::mapLog);
    }

    @Override
    public int countCurrentPassengers() {
        return count(PASSENGER_LOCATION_LOG.IS_OUT.eq("0")
            .and(PASSENGER_LOCATION_LOG.REID.isNotNull())
            .and(today()));
    }

    @Override
    public int countDepartedPassengers() {
        return count(PASSENGER_LOCATION_LOG.IS_OUT.eq("1")
            .and(PASSENGER_LOCATION_LOG.REID.isNotNull())
            .and(today()));
    }

    @Override
    public int countVisitors() {
        return count(PASSENGER_LOCATION_LOG.PASSENGER_ID.isNull()
            .and(PASSENGER_LOCATION_LOG.USER_NAME.isNull())
            .and(today()));
    }

    @Override
    public List<PassengerLocationLog> selectCurrentPassengerDetails() {
        return todayList(PASSENGER_LOCATION_LOG.IS_OUT.eq("0").and(PASSENGER_LOCATION_LOG.REID.isNotNull()));
    }

    @Override
    public List<PassengerLocationLog> selectDepartedPassengerDetails() {
        return todayList(PASSENGER_LOCATION_LOG.IS_OUT.eq("1").and(PASSENGER_LOCATION_LOG.REID.isNotNull()));
    }

    @Override
    public List<PassengerLocationLog> selectVisitorDetails() {
        return todayList(PASSENGER_LOCATION_LOG.PASSENGER_ID.isNull().and(PASSENGER_LOCATION_LOG.USER_NAME.isNull()));
    }

    @Override
    public PassengerLocationLog selectPassengerLocationLogByPid(String pid) {
        return dsl.select(PASSENGER_LOCATION_LOG.fields())
            .from(PASSENGER_LOCATION_LOG)
            .where(PASSENGER_LOCATION_LOG.PID.eq(pid))
            .orderBy(PASSENGER_LOCATION_LOG.CREATE_TIME.desc())
            .limit(1)
            .fetchOne(this::mapLog);
    }

    @Override
    public List<PassengerLocationLog> findUnrecognizedPassenger() {
        return dsl.select(PASSENGER_LOCATION_LOG.fields())
            .from(PASSENGER_LOCATION_LOG)
            .where(PASSENGER_LOCATION_LOG.REID.isNull())
            .orderBy(PASSENGER_LOCATION_LOG.CREATE_TIME.desc())
            .fetch(this::mapLog);
    }

    @Override
    public List<PassengerLocationLog> selectCurrentPassengerList(String roomCode) {
        return dsl.select(PASSENGER_LOCATION_LOG.fields())
            .from(PASSENGER_LOCATION_LOG)
            .where(PASSENGER_LOCATION_LOG.IS_OUT.eq("0")
                .and(today())
                .and(PASSENGER_LOCATION_LOG.ROOM_CODE.eq(roomCode)))
            .orderBy(PASSENGER_LOCATION_LOG.CREATE_TIME.desc())
            .fetch(this::mapLog);
    }

    @Override
    public Long getCountByArea(Long areaId) {
        return dsl.selectCount()
            .from(PASSENGER_LOCATION_LOG)
            .where(PASSENGER_LOCATION_LOG.REGION_ID.in(
                dsl.select(CONFIG_REGION.ID).from(CONFIG_REGION).where(CONFIG_REGION.AREA_ID.eq(areaId))
            ))
            .and(PASSENGER_LOCATION_LOG.CREATE_TIME.ge(LocalDate.now().atStartOfDay()))
            .and(PASSENGER_LOCATION_LOG.IS_OUT.eq("0"))
            .fetchOne(0, Long.class);
    }

    private int count(Condition condition) {
        Integer value = dsl.selectCount().from(PASSENGER_LOCATION_LOG).where(condition).fetchOne(0, Integer.class);
        return value == null ? 0 : value;
    }

    private List<PassengerLocationLog> todayList(Condition condition) {
        return dsl.select(PASSENGER_LOCATION_LOG.fields())
            .from(PASSENGER_LOCATION_LOG)
            .where(condition.and(today()))
            .orderBy(PASSENGER_LOCATION_LOG.CREATE_TIME.desc())
            .fetch(this::mapLog);
    }

    private Condition today() {
        return PASSENGER_LOCATION_LOG.CREATE_TIME.ge(LocalDate.now().atStartOfDay());
    }

    private Condition logConditions(PassengerLocationLog log) {
        if (log == null) {
            return DSL.noCondition();
        }
        return DSL.and(
            eqIfPresent(PASSENGER_LOCATION_LOG.PID, log.getPid()),
            eqIfPresent(PASSENGER_LOCATION_LOG.REID, log.getReid()),
            eqIfPresent(PASSENGER_LOCATION_LOG.PASSENGER_ID, toLong(log.getPassengerId())),
            likeIfPresent(PASSENGER_LOCATION_LOG.USER_NAME, log.getUserName()),
            eqIfPresent(PASSENGER_LOCATION_LOG.ROOM_CODE, log.getRoomCode()),
            eqIfPresent(PASSENGER_LOCATION_LOG.FLIGHT_NO, log.getFlightNo()),
            eqIfPresent(PASSENGER_LOCATION_LOG.FLIGHT_DATE, toLocalDate(log.getFlightDate())),
            eqIfPresent(PASSENGER_LOCATION_LOG.REGION_ID, log.getRegionId() == null ? null : log.getRegionId().longValue()),
            likeIfPresent(PASSENGER_LOCATION_LOG.REGION_NAME, log.getRegionName()),
            eqIfPresent(PASSENGER_LOCATION_LOG.COORDINATE, log.getCoordinate()),
            eqIfPresent(PASSENGER_LOCATION_LOG.DEVICE_ID, toLong(log.getDeviceId())),
            likeIfPresent(PASSENGER_LOCATION_LOG.DEVICE_NAME, log.getDeviceName()),
            eqIfPresent(PASSENGER_LOCATION_LOG.DEEP_GLINT_DEVICE_ID, log.getDeepGlintDeviceId()),
            eqIfPresent(PASSENGER_LOCATION_LOG.RECOGNITION_TYPE, log.getRecognitionType()),
            eqIfPresent(PASSENGER_LOCATION_LOG.CTS, log.getCts()),
            eqIfPresent(PASSENGER_LOCATION_LOG.ORI_IMAGE_URL, log.getOriImageUrl()),
            eqIfPresent(PASSENGER_LOCATION_LOG.REGISTER_IMAGE_URL, log.getRegisterImageUrl()),
            eqIfPresent(PASSENGER_LOCATION_LOG.IS_OUT, log.getIsOut())
        );
    }

    private PassengerLocationLog mapLog(Record record) {
        PassengerLocationLog log = new PassengerLocationLog();
        log.setId(record.get(PASSENGER_LOCATION_LOG.ID));
        log.setPassengerId(stringValue(record.get(PASSENGER_LOCATION_LOG.PASSENGER_ID)));
        log.setUserName(record.get(PASSENGER_LOCATION_LOG.USER_NAME));
        log.setRoomCode(record.get(PASSENGER_LOCATION_LOG.ROOM_CODE));
        log.setFlightNo(record.get(PASSENGER_LOCATION_LOG.FLIGHT_NO));
        log.setFlightDate(toDate(record.get(PASSENGER_LOCATION_LOG.FLIGHT_DATE)));
        log.setReid(record.get(PASSENGER_LOCATION_LOG.REID));
        log.setPid(record.get(PASSENGER_LOCATION_LOG.PID));
        log.setRegionId(record.get(PASSENGER_LOCATION_LOG.REGION_ID) == null ? null : record.get(PASSENGER_LOCATION_LOG.REGION_ID).intValue());
        log.setRegionName(record.get(PASSENGER_LOCATION_LOG.REGION_NAME));
        log.setCoordinate(record.get(PASSENGER_LOCATION_LOG.COORDINATE));
        log.setDeviceId(stringValue(record.get(PASSENGER_LOCATION_LOG.DEVICE_ID)));
        log.setDeviceName(record.get(PASSENGER_LOCATION_LOG.DEVICE_NAME));
        log.setDeepGlintDeviceId(record.get(PASSENGER_LOCATION_LOG.DEEP_GLINT_DEVICE_ID));
        log.setRecognitionType(record.get(PASSENGER_LOCATION_LOG.RECOGNITION_TYPE));
        log.setOriImageUrl(record.get(PASSENGER_LOCATION_LOG.ORI_IMAGE_URL));
        log.setRegisterImageUrl(record.get(PASSENGER_LOCATION_LOG.REGISTER_IMAGE_URL));
        log.setIsOut(record.get(PASSENGER_LOCATION_LOG.IS_OUT));
        log.setCts(record.get(PASSENGER_LOCATION_LOG.CTS));
        log.setCreateTime(toDate(record.get(PASSENGER_LOCATION_LOG.CREATE_TIME)));
        return log;
    }

    private Condition eqIfPresent(org.jooq.Field<?> field, Object value) {
        if (value == null || value instanceof String text && text.isBlank()) {
            return DSL.noCondition();
        }
        return ((org.jooq.Field<Object>) field).eq(value);
    }

    private Condition likeIfPresent(org.jooq.Field<String> field, String value) {
        if (value == null || value.isBlank()) {
            return DSL.noCondition();
        }
        return field.like("%" + value + "%");
    }

    private void put(Map<Field<?>, Object> values, Field<?> field, Object value) {
        if (value != null) {
            values.put(field, value);
        }
    }
}
