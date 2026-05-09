package com.robotmonitor.flight.mapper.jooq;

import static com.robotmonitor.flight.mapper.jooq.JooqFlightMapperSupport.isBlank;
import static com.robotmonitor.flight.mapper.jooq.JooqFlightMapperSupport.toCompactDateTime;
import static com.robotmonitor.flight.mapper.jooq.JooqFlightMapperSupport.toDate;
import static com.robotmonitor.flight.mapper.jooq.JooqFlightMapperSupport.toLocalDate;
import static com.robotmonitor.flight.mapper.jooq.JooqFlightMapperSupport.toLocalDateTime;
import static com.robotmonitor.jooq.generated.Tables.FLIGHT_INFO;

import com.robotmonitor.flight.domain.FlightInfo;
import com.robotmonitor.flight.domain.FlightParam;
import com.robotmonitor.flight.mapper.FlightInfoMapper;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
public class JooqFlightInfoMapper implements FlightInfoMapper {
    private final DSLContext dsl;

    public JooqFlightInfoMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public FlightInfo selectFlightInfoByFlightId(String flightId) {
        return dsl.select(FLIGHT_INFO.fields())
            .from(FLIGHT_INFO)
            .where(FLIGHT_INFO.FLIGHT_ID.eq(flightId))
            .fetchOne(this::mapFlightInfo);
    }

    @Override
    public List<FlightInfo> selectFlightInfoList(FlightInfo query) {
        return dsl.select(FLIGHT_INFO.fields())
            .from(FLIGHT_INFO)
            .where(conditions(query))
            .orderBy(FLIGHT_INFO.UPDATE_TIME.desc())
            .fetch(this::mapFlightInfo);
    }

    @Override
    public int insertFlightInfo(FlightInfo info) {
        if (info.getFlightId() == null) {
            return 0;
        }
        return dsl.insertInto(FLIGHT_INFO)
            .set(writeValues(info))
            .execute();
    }

    @Override
    public int updateFlightInfo(FlightInfo info) {
        if (info.getFlightId() == null) {
            return 0;
        }
        Map<Field<?>, Object> values = writeValues(info);
        values.remove(FLIGHT_INFO.FLIGHT_ID);
        if (values.isEmpty()) {
            return 0;
        }
        return dsl.update(FLIGHT_INFO)
            .set(values)
            .where(FLIGHT_INFO.FLIGHT_ID.eq(info.getFlightId()))
            .execute();
    }

    @Override
    public int deleteFlightInfoByFlightId(String flightId) {
        return dsl.update(FLIGHT_INFO)
            .set(FLIGHT_INFO.IS_DELETE, "1")
            .where(FLIGHT_INFO.FLIGHT_ID.eq(flightId))
            .execute();
    }

    @Override
    public int deleteFlightInfoByFlightIds(Long[] flightIds) {
        if (flightIds == null || flightIds.length == 0) {
            return 0;
        }
        List<String> ids = Arrays.stream(flightIds).map(String::valueOf).toList();
        return dsl.deleteFrom(FLIGHT_INFO)
            .where(FLIGHT_INFO.FLIGHT_ID.in(ids))
            .execute();
    }

    @Override
    public List<FlightInfo> selectUnflownFlights() {
        return dsl.select(FLIGHT_INFO.fields())
            .from(FLIGHT_INFO)
            .where(unflownCondition())
            .fetch(this::mapFlightInfo);
    }

    @Override
    public List<FlightInfo> selectWillTakeOffFlights(FlightParam param) {
        LocalDateTime upperBound = takeOffUpperBound(param);
        return dsl.select(FLIGHT_INFO.fields())
            .from(FLIGHT_INFO)
            .where(DSL.and(
                unflownCondition(),
                param == null || isBlank(param.getFlightDate()) ? DSL.noCondition() : FLIGHT_INFO.SCHE_EXEC_DATE.eq(toLocalDate(param.getFlightDate())),
                upperBound == null ? DSL.noCondition() : FLIGHT_INFO.ESTM_TAKE_OFF_TIME.le(upperBound),
                FLIGHT_INFO.ESTM_TAKE_OFF_TIME.isNotNull()
            ))
            .orderBy(FLIGHT_INFO.ESTM_TAKE_OFF_TIME.asc())
            .fetch(this::mapFlightInfo);
    }

    private Condition conditions(FlightInfo info) {
        if (info == null) {
            return DSL.noCondition();
        }
        return DSL.and(
            eqIfPresent(FLIGHT_INFO.SEND_TIME, toLocalDateTime(info.getSendTime())),
            eqIfPresent(FLIGHT_INFO.AIRLINE_CD, info.getAirlineCd()),
            likeIfPresent(FLIGHT_INFO.FLIGHT_NO, info.getFlightNo()),
            eqIfPresent(FLIGHT_INFO.SCHE_EXEC_DATE, toLocalDate(info.getScheExecDate())),
            eqIfPresent(FLIGHT_INFO.FLIGHT_ATTR, info.getFlightAttr()),
            eqIfPresent(FLIGHT_INFO.CRAFT_TYPE, info.getCraftType()),
            eqIfPresent(FLIGHT_INFO.CRAFT_NO, info.getCraftNo()),
            eqIfPresent(FLIGHT_INFO.LATEST_OFF_STATUS, info.getLatestOffStatus()),
            eqIfPresent(FLIGHT_INFO.LATEST_ON_STATUS, info.getLatestOnStatus()),
            eqIfPresent(FLIGHT_INFO.DOM_FLIGHT_STATE, info.getDomFlightState()),
            eqIfPresent(FLIGHT_INFO.INT_FLIGHT_STATE, info.getIntFlightState()),
            eqIfPresent(FLIGHT_INFO.DOM_FLIGHT_ABSTATE, info.getDomFlightAbstate()),
            eqIfPresent(FLIGHT_INFO.INT_FLIGHT_ABSTATE, info.getIntFlightAbstate()),
            eqIfPresent(FLIGHT_INFO.DOM_AB_STATE_TIME, toLocalDateTime(info.getDomAbStateTime())),
            eqIfPresent(FLIGHT_INFO.INT_AB_STATE_TIME, toLocalDateTime(info.getIntAbStateTime())),
            eqIfPresent(FLIGHT_INFO.DOM_FLIGHT_ABSTATE_REASON, info.getDomFlightAbstateReason()),
            eqIfPresent(FLIGHT_INFO.INT_FLIGHT_ABSTATE_REASON, info.getIntFlightAbstateReason()),
            eqIfPresent(FLIGHT_INFO.DOM_INNER_FLIGHT_ABSTATE_REASON, info.getDomInnerFlightAbstateReason()),
            eqIfPresent(FLIGHT_INFO.INT_INNER_FLIGHT_ABSTATE_REASON, info.getIntInnerFlightAbstateReason()),
            eqIfPresent(FLIGHT_INFO.DOM_FLIGHT_ABSTATE_REASON_DESC, info.getDomFlightAbstateReasonDesc()),
            eqIfPresent(FLIGHT_INFO.INT_FLIGHT_ABSTATE_REASON_DESC, info.getIntFlightAbstateReasonDesc()),
            eqIfPresent(FLIGHT_INFO.AIRLINE, info.getAirline()),
            eqIfPresent(FLIGHT_INFO.STATION, info.getStation()),
            eqIfPresent(FLIGHT_INFO.STATION_CN, info.getStationCn()),
            eqIfPresent(FLIGHT_INFO.SCHE_TAKE_OFF_TIME, toLocalDateTime(info.getScheTakeOffTime())),
            eqIfPresent(FLIGHT_INFO.ESTM_TAKE_OFF_TIME, toLocalDateTime(info.getEstmTakeOffTime())),
            eqIfPresent(FLIGHT_INFO.ACTL_TAKE_OFF_TIME, toLocalDateTime(info.getActlTakeOffTime())),
            eqIfPresent(FLIGHT_INFO.GATE_CD, info.getGateCd()),
            eqIfPresent(FLIGHT_INFO.GATE_ATTR, info.getGateAttr()),
            eqIfPresent(FLIGHT_INFO.ESTM_START_TIME, toLocalDateTime(info.getEstmStartTime())),
            eqIfPresent(FLIGHT_INFO.ESTM_END_TIME, toLocalDateTime(info.getEstmEndTime())),
            eqIfPresent(FLIGHT_INFO.CAROUSEL_CD, info.getCarouselCd()),
            eqIfPresent(FLIGHT_INFO.CAROUSEL_CLASS, info.getCarouselClass()),
            eqIfPresent(FLIGHT_INFO.CAROUSEL_ATTR, info.getCarouselAttr()),
            eqIfPresent(FLIGHT_INFO.IS_DELETE, info.getIsDelete())
        );
    }

    private Map<Field<?>, Object> writeValues(FlightInfo info) {
        Map<Field<?>, Object> values = new LinkedHashMap<>();
        put(values, FLIGHT_INFO.FLIGHT_ID, info.getFlightId());
        put(values, FLIGHT_INFO.SEND_TIME, toLocalDateTime(info.getSendTime()));
        put(values, FLIGHT_INFO.AIRLINE_CD, info.getAirlineCd());
        put(values, FLIGHT_INFO.FLIGHT_NO, info.getFlightNo());
        put(values, FLIGHT_INFO.SCHE_EXEC_DATE, toLocalDate(info.getScheExecDate()));
        put(values, FLIGHT_INFO.FLIGHT_ATTR, info.getFlightAttr());
        put(values, FLIGHT_INFO.CRAFT_TYPE, info.getCraftType());
        put(values, FLIGHT_INFO.CRAFT_NO, info.getCraftNo());
        put(values, FLIGHT_INFO.LATEST_OFF_STATUS, info.getLatestOffStatus());
        put(values, FLIGHT_INFO.LATEST_ON_STATUS, info.getLatestOnStatus());
        put(values, FLIGHT_INFO.DOM_FLIGHT_STATE, info.getDomFlightState());
        put(values, FLIGHT_INFO.INT_FLIGHT_STATE, info.getIntFlightState());
        put(values, FLIGHT_INFO.DOM_FLIGHT_ABSTATE, info.getDomFlightAbstate());
        put(values, FLIGHT_INFO.INT_FLIGHT_ABSTATE, info.getIntFlightAbstate());
        put(values, FLIGHT_INFO.DOM_AB_STATE_TIME, toLocalDateTime(info.getDomAbStateTime()));
        put(values, FLIGHT_INFO.INT_AB_STATE_TIME, toLocalDateTime(info.getIntAbStateTime()));
        put(values, FLIGHT_INFO.DOM_FLIGHT_ABSTATE_REASON, info.getDomFlightAbstateReason());
        put(values, FLIGHT_INFO.INT_FLIGHT_ABSTATE_REASON, info.getIntFlightAbstateReason());
        put(values, FLIGHT_INFO.DOM_INNER_FLIGHT_ABSTATE_REASON, info.getDomInnerFlightAbstateReason());
        put(values, FLIGHT_INFO.INT_INNER_FLIGHT_ABSTATE_REASON, info.getIntInnerFlightAbstateReason());
        put(values, FLIGHT_INFO.DOM_FLIGHT_ABSTATE_REASON_DESC, info.getDomFlightAbstateReasonDesc());
        put(values, FLIGHT_INFO.INT_FLIGHT_ABSTATE_REASON_DESC, info.getIntFlightAbstateReasonDesc());
        put(values, FLIGHT_INFO.AIRLINE, info.getAirline());
        put(values, FLIGHT_INFO.STATION, info.getStation());
        put(values, FLIGHT_INFO.STATION_CN, info.getStationCn());
        put(values, FLIGHT_INFO.SCHE_TAKE_OFF_TIME, toLocalDateTime(info.getScheTakeOffTime()));
        put(values, FLIGHT_INFO.ESTM_TAKE_OFF_TIME, toLocalDateTime(info.getEstmTakeOffTime()));
        put(values, FLIGHT_INFO.ACTL_TAKE_OFF_TIME, toLocalDateTime(info.getActlTakeOffTime()));
        put(values, FLIGHT_INFO.GATE_CD, info.getGateCd());
        put(values, FLIGHT_INFO.GATE_ATTR, info.getGateAttr());
        put(values, FLIGHT_INFO.ESTM_START_TIME, toLocalDateTime(info.getEstmStartTime()));
        put(values, FLIGHT_INFO.ESTM_END_TIME, toLocalDateTime(info.getEstmEndTime()));
        put(values, FLIGHT_INFO.CAROUSEL_CD, info.getCarouselCd());
        put(values, FLIGHT_INFO.CAROUSEL_CLASS, info.getCarouselClass());
        put(values, FLIGHT_INFO.CAROUSEL_ATTR, info.getCarouselAttr());
        put(values, FLIGHT_INFO.UPDATE_TIME, toLocalDateTime(info.getUpdateTime()));
        put(values, FLIGHT_INFO.IS_DELETE, info.getIsDelete());
        return values;
    }

    private Condition unflownCondition() {
        return FLIGHT_INFO.LATEST_OFF_STATUS.in("SCH", "ETD")
            .and(FLIGHT_INFO.IS_DELETE.ne("1"));
    }

    private LocalDateTime takeOffUpperBound(FlightParam param) {
        if (param == null || param.getCurrentTime() == null) {
            return null;
        }
        long currentMillis = param.getCurrentTime();
        if (currentMillis < 10_000_000_000L) {
            return LocalDateTime.now().plusSeconds(param.getEarlyTime() == null ? 0L : param.getEarlyTime());
        }
        long upper = currentMillis + (param.getEarlyTime() == null ? 0L : param.getEarlyTime());
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(upper), ZoneId.systemDefault());
    }

    private FlightInfo mapFlightInfo(Record record) {
        FlightInfo info = new FlightInfo();
        info.setFlightId(record.get(FLIGHT_INFO.FLIGHT_ID));
        info.setSendTime(toCompactDateTime(record.get(FLIGHT_INFO.SEND_TIME)));
        info.setAirlineCd(record.get(FLIGHT_INFO.AIRLINE_CD));
        info.setFlightNo(record.get(FLIGHT_INFO.FLIGHT_NO));
        info.setScheExecDate(record.get(FLIGHT_INFO.SCHE_EXEC_DATE) == null ? null : record.get(FLIGHT_INFO.SCHE_EXEC_DATE).toString());
        info.setFlightAttr(record.get(FLIGHT_INFO.FLIGHT_ATTR));
        info.setCraftType(record.get(FLIGHT_INFO.CRAFT_TYPE));
        info.setCraftNo(record.get(FLIGHT_INFO.CRAFT_NO));
        info.setLatestOffStatus(record.get(FLIGHT_INFO.LATEST_OFF_STATUS));
        info.setLatestOnStatus(record.get(FLIGHT_INFO.LATEST_ON_STATUS));
        info.setDomFlightState(record.get(FLIGHT_INFO.DOM_FLIGHT_STATE));
        info.setIntFlightState(record.get(FLIGHT_INFO.INT_FLIGHT_STATE));
        info.setDomFlightAbstate(record.get(FLIGHT_INFO.DOM_FLIGHT_ABSTATE));
        info.setIntFlightAbstate(record.get(FLIGHT_INFO.INT_FLIGHT_ABSTATE));
        info.setDomAbStateTime(toCompactDateTime(record.get(FLIGHT_INFO.DOM_AB_STATE_TIME)));
        info.setIntAbStateTime(toCompactDateTime(record.get(FLIGHT_INFO.INT_AB_STATE_TIME)));
        info.setDomFlightAbstateReason(record.get(FLIGHT_INFO.DOM_FLIGHT_ABSTATE_REASON));
        info.setIntFlightAbstateReason(record.get(FLIGHT_INFO.INT_FLIGHT_ABSTATE_REASON));
        info.setDomInnerFlightAbstateReason(record.get(FLIGHT_INFO.DOM_INNER_FLIGHT_ABSTATE_REASON));
        info.setIntInnerFlightAbstateReason(record.get(FLIGHT_INFO.INT_INNER_FLIGHT_ABSTATE_REASON));
        info.setDomFlightAbstateReasonDesc(record.get(FLIGHT_INFO.DOM_FLIGHT_ABSTATE_REASON_DESC));
        info.setIntFlightAbstateReasonDesc(record.get(FLIGHT_INFO.INT_FLIGHT_ABSTATE_REASON_DESC));
        info.setAirline(record.get(FLIGHT_INFO.AIRLINE));
        info.setStation(record.get(FLIGHT_INFO.STATION));
        info.setStationCn(record.get(FLIGHT_INFO.STATION_CN));
        info.setScheTakeOffTime(toCompactDateTime(record.get(FLIGHT_INFO.SCHE_TAKE_OFF_TIME)));
        info.setEstmTakeOffTime(toCompactDateTime(record.get(FLIGHT_INFO.ESTM_TAKE_OFF_TIME)));
        info.setActlTakeOffTime(toCompactDateTime(record.get(FLIGHT_INFO.ACTL_TAKE_OFF_TIME)));
        info.setGateCd(record.get(FLIGHT_INFO.GATE_CD));
        info.setGateAttr(record.get(FLIGHT_INFO.GATE_ATTR));
        info.setEstmStartTime(toDate(record.get(FLIGHT_INFO.ESTM_START_TIME)));
        info.setEstmEndTime(toDate(record.get(FLIGHT_INFO.ESTM_END_TIME)));
        info.setCarouselCd(record.get(FLIGHT_INFO.CAROUSEL_CD));
        info.setCarouselClass(record.get(FLIGHT_INFO.CAROUSEL_CLASS));
        info.setCarouselAttr(record.get(FLIGHT_INFO.CAROUSEL_ATTR));
        info.setUpdateTime(toDate(record.get(FLIGHT_INFO.UPDATE_TIME)));
        info.setIsDelete(record.get(FLIGHT_INFO.IS_DELETE));
        return info;
    }

    private Condition eqIfPresent(Field<?> field, Object value) {
        if (value == null || value instanceof String text && text.isBlank()) {
            return DSL.noCondition();
        }
        return ((Field<Object>) field).eq(value);
    }

    private Condition likeIfPresent(Field<String> field, String value) {
        if (isBlank(value)) {
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
