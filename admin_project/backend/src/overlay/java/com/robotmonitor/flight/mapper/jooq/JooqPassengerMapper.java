package com.robotmonitor.flight.mapper.jooq;

import static com.robotmonitor.flight.mapper.jooq.JooqFlightMapperSupport.isBlank;
import static com.robotmonitor.flight.mapper.jooq.JooqFlightMapperSupport.toDate;
import static com.robotmonitor.flight.mapper.jooq.JooqFlightMapperSupport.toLocalDate;
import static com.robotmonitor.flight.mapper.jooq.JooqFlightMapperSupport.toLocalDateTime;
import static com.robotmonitor.jooq.generated.Tables.CONFIG_REGION;
import static com.robotmonitor.jooq.generated.Tables.FLIGHT_INFO;
import static com.robotmonitor.jooq.generated.Tables.FLIGHT_WARNING;
import static com.robotmonitor.jooq.generated.Tables.PASSENGER;

import com.robotmonitor.flight.domain.Passenger;
import com.robotmonitor.flight.domain.PassengerParam;
import com.robotmonitor.flight.domain.dto.FlightChangePassengerDTO;
import com.robotmonitor.flight.mapper.PassengerMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqPassengerMapper implements PassengerMapper {
    private final DSLContext dsl;

    public JooqPassengerMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public Passenger selectPassengerById(Long id) {
        return dsl.select(PASSENGER.fields())
            .from(PASSENGER)
            .where(PASSENGER.ID.eq(id))
            .fetchOne(this::mapPassenger);
    }

    @Override
    public List<Passenger> selectPassengerList(Passenger query) {
        return dsl.select(PASSENGER.fields())
            .from(PASSENGER)
            .where(passengerConditions(query))
            .orderBy(PASSENGER.CREATE_TIME.desc())
            .fetch(this::mapPassenger);
    }

    @Override
    public List<Passenger> selectPassengerList2(PassengerParam query) {
        return dsl.select(PASSENGER.fields())
            .from(PASSENGER)
            .where(paramConditions(query))
            .orderBy(PASSENGER.GET_IN_TIME.desc(), PASSENGER.GET_OUT_TIME.desc())
            .fetch(this::mapPassenger);
    }

    @Override
    public List<Passenger> selectPassengerList_Re(Passenger query) {
        return dsl.select(PASSENGER.fields())
            .from(PASSENGER)
            .where(DSL.and(
                likeIfPresent(PASSENGER.USER_NAME, query == null ? null : query.getUserName()),
                eqIfPresent(PASSENGER.FLIGHT_NO, query == null ? null : query.getFlightNo()),
                eqIfPresent(PASSENGER.FLIGHT_DATE, query == null ? null : toLocalDate(query.getFlightDate())),
                eqIfPresent(PASSENGER.SEAT, query == null ? null : query.getSeat()),
                eqIfPresent(PASSENGER.FLIGHT_ID, query == null ? null : query.getFlightId()),
                PASSENGER.STATUS.eq("1")
            ))
            .fetch(this::mapPassenger);
    }

    @Override
    public int insertPassenger(Passenger passenger) {
        var insert = dsl.insertInto(PASSENGER)
            .set(PASSENGER.USER_NAME, passenger.getUserName())
            .set(PASSENGER.ROOM_CODE, passenger.getRoomCode())
            .set(PASSENGER.FLIGHT_NO, passenger.getFlightNo())
            .set(PASSENGER.FLIGHT_DATE, toLocalDate(passenger.getFlightDate()))
            .set(PASSENGER.ORIG, passenger.getOrig())
            .set(PASSENGER.DEST, passenger.getDest())
            .set(PASSENGER.CABIN, passenger.getCabin())
            .set(PASSENGER.SEAT, passenger.getSeat())
            .set(PASSENGER.SEQ, passenger.getSeq())
            .set(PASSENGER.CARD_SERVICE, passenger.getCardService())
            .set(PASSENGER.CARD_NO, passenger.getCardNo())
            .set(PASSENGER.MEM_LEVEL, passenger.getMemLevel())
            .set(PASSENGER.STAR_LEVEL, passenger.getStarLevel())
            .set(PASSENGER.IN_TYPE, passenger.getInType())
            .set(PASSENGER.GET_IN_TIME, toLocalDateTime(passenger.getGetInTime()))
            .set(PASSENGER.GET_OUT_TIME, toLocalDateTime(passenger.getGetOutTime()))
            .set(PASSENGER.STATUS, passenger.getStatus())
            .set(PASSENGER.CREATE_TIME, toLocalDateTime(passenger.getCreateTime()))
            .set(PASSENGER.UPDATE_TIME, toLocalDateTime(passenger.getUpdateTime()))
            .set(PASSENGER.REID, passenger.getReid())
            .set(PASSENGER.PID, passenger.getPid())
            .set(PASSENGER.FLIGHT_ID, passenger.getFlightId())
            .set(PASSENGER.REGION_ID, passenger.getRegionId())
            .set(PASSENGER.ORI_IMAGE_URL, passenger.getOrigImageUrl())
            .set(PASSENGER.REGISTER_IMAGE_URL, passenger.getRegisterImageUrl())
            .set(PASSENGER.PHOTO, passenger.getPhoto())
            .set(PASSENGER.ROBOT_ID, passenger.getRobotId())
            .set(PASSENGER.FOLLOWER_NUM, passenger.getFollowerNum())
            .set(PASSENGER.IS_MEMBER, passenger.getIsMember())
            .set(PASSENGER.COLLEDT_ID, passenger.getColledtId())
            .set(PASSENGER.COORDINATE, passenger.getCoordinate())
            .set(PASSENGER.WARNING_TYPE, passenger.getWarningType());
        Long id = insert.returningResult(PASSENGER.ID).fetchOne(PASSENGER.ID);
        passenger.setId(id);
        return id == null ? 0 : 1;
    }

    @Override
    public int updatePassenger(Passenger passenger) {
        if (passenger.getId() == null) {
            return 0;
        }
        Map<Field<?>, Object> values = new LinkedHashMap<>();
        put(values, PASSENGER.USER_NAME, passenger.getUserName());
        put(values, PASSENGER.ROOM_CODE, passenger.getRoomCode());
        put(values, PASSENGER.FLIGHT_NO, passenger.getFlightNo());
        put(values, PASSENGER.FLIGHT_DATE, toLocalDate(passenger.getFlightDate()));
        put(values, PASSENGER.ORIG, passenger.getOrig());
        put(values, PASSENGER.DEST, passenger.getDest());
        put(values, PASSENGER.CABIN, passenger.getCabin());
        put(values, PASSENGER.SEAT, passenger.getSeat());
        put(values, PASSENGER.SEQ, passenger.getSeq());
        put(values, PASSENGER.CARD_SERVICE, passenger.getCardService());
        put(values, PASSENGER.CARD_NO, passenger.getCardNo());
        put(values, PASSENGER.MEM_LEVEL, passenger.getMemLevel());
        put(values, PASSENGER.STAR_LEVEL, passenger.getStarLevel());
        put(values, PASSENGER.IN_TYPE, passenger.getInType());
        put(values, PASSENGER.GET_IN_TIME, toLocalDateTime(passenger.getGetInTime()));
        put(values, PASSENGER.GET_OUT_TIME, toLocalDateTime(passenger.getGetOutTime()));
        put(values, PASSENGER.STATUS, passenger.getStatus());
        put(values, PASSENGER.CREATE_TIME, toLocalDateTime(passenger.getCreateTime()));
        put(values, PASSENGER.UPDATE_TIME, toLocalDateTime(passenger.getUpdateTime()));
        put(values, PASSENGER.REID, passenger.getReid());
        put(values, PASSENGER.PID, passenger.getPid());
        put(values, PASSENGER.FLIGHT_ID, passenger.getFlightId());
        put(values, PASSENGER.REGION_ID, passenger.getRegionId());
        put(values, PASSENGER.ORI_IMAGE_URL, passenger.getOrigImageUrl());
        put(values, PASSENGER.REGISTER_IMAGE_URL, passenger.getRegisterImageUrl());
        put(values, PASSENGER.PHOTO, passenger.getPhoto());
        put(values, PASSENGER.ROBOT_ID, passenger.getRobotId());
        put(values, PASSENGER.FOLLOWER_NUM, passenger.getFollowerNum());
        put(values, PASSENGER.IS_MEMBER, passenger.getIsMember());
        put(values, PASSENGER.COLLEDT_ID, passenger.getColledtId());
        put(values, PASSENGER.COORDINATE, passenger.getCoordinate());
        put(values, PASSENGER.WARNING_TYPE, passenger.getWarningType());
        if (values.isEmpty()) {
            return 0;
        }
        return dsl.update(PASSENGER)
            .set(values)
            .where(PASSENGER.ID.eq(passenger.getId()))
            .execute();
    }

    @Override
    public int deletePassengerById(Long id) {
        return dsl.deleteFrom(PASSENGER).where(PASSENGER.ID.eq(id)).execute();
    }

    @Override
    public int deletePassengerByIds(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }
        return dsl.deleteFrom(PASSENGER).where(PASSENGER.ID.in(Arrays.asList(ids))).execute();
    }

    @Override
    public int setPassengerGetOut(Long id, String oriImgUrl, Long regionId) {
        return dsl.update(PASSENGER)
            .set(PASSENGER.STATUS, "0")
            .set(PASSENGER.GET_OUT_TIME, LocalDateTime.now())
            .set(PASSENGER.ORI_IMAGE_URL, oriImgUrl)
            .set(PASSENGER.REGION_ID, regionId)
            .where(PASSENGER.ID.eq(id))
            .execute();
    }

    @Override
    public int updatePassengerRegionAndStatus(Long id, Long regionId, String status, String origImageUrl, String registerImageUrl) {
        return dsl.update(PASSENGER)
            .set(PASSENGER.REGION_ID, regionId)
            .set(PASSENGER.ORI_IMAGE_URL, origImageUrl)
            .set(PASSENGER.REGISTER_IMAGE_URL, registerImageUrl)
            .set(PASSENGER.STATUS, status)
            .setNull(PASSENGER.GET_OUT_TIME)
            .set(PASSENGER.UPDATE_TIME, LocalDateTime.now())
            .where(PASSENGER.ID.eq(id))
            .execute();
    }

    @Override
    public List<Passenger> selectPassengerWithUnflownFlights() {
        return dsl.select(PASSENGER.fields())
            .from(PASSENGER)
            .join(FLIGHT_INFO).on(PASSENGER.FLIGHT_ID.eq(FLIGHT_INFO.FLIGHT_ID))
            .where(FLIGHT_INFO.LATEST_OFF_STATUS.in("SCH", "ETD"))
            .fetch(this::mapPassenger);
    }

    @Override
    public List<Passenger> selectPassengerOutgoingList(Passenger query) {
        return dsl.select(PASSENGER.fields())
            .from(PASSENGER)
            .where(DSL.and(
                PASSENGER.STATUS.eq("0"),
                likeIfPresent(PASSENGER.USER_NAME, query == null ? null : query.getUserName()),
                eqIfPresent(PASSENGER.FLIGHT_NO, query == null ? null : query.getFlightNo()),
                eqIfPresent(PASSENGER.FLIGHT_DATE, query == null ? null : toLocalDate(query.getFlightDate())),
                query == null || query.getGetOutTimeStart() == null ? DSL.noCondition() : PASSENGER.GET_OUT_TIME.ge(toLocalDateTime(query.getGetOutTimeStart())),
                query == null || query.getGetOutTimeEnd() == null ? DSL.noCondition() : PASSENGER.GET_OUT_TIME.le(toLocalDateTime(query.getGetOutTimeEnd())),
                eqIfPresent(PASSENGER.ORIG, query == null ? null : query.getOrig()),
                eqIfPresent(PASSENGER.DEST, query == null ? null : query.getDest())
            ))
            .orderBy(PASSENGER.GET_OUT_TIME.desc())
            .fetch(this::mapPassenger);
    }

    @Override
    public List<Passenger> selectPassengerInLoungeList(Passenger query) {
        return dsl.select(PASSENGER.fields())
            .from(PASSENGER)
            .join(FLIGHT_INFO).on(PASSENGER.FLIGHT_ID.eq(FLIGHT_INFO.FLIGHT_ID))
            .where(DSL.and(
                PASSENGER.STATUS.eq("1"),
                FLIGHT_INFO.LATEST_OFF_STATUS.in("SCH", "ETD", "CLD", "OUT"),
                PASSENGER.GET_IN_TIME.gt(LocalDate.now().atStartOfDay()),
                likeIfPresent(PASSENGER.USER_NAME, query == null ? null : query.getUserName()),
                eqIfPresent(PASSENGER.FLIGHT_NO, query == null ? null : query.getFlightNo()),
                eqIfPresent(PASSENGER.FLIGHT_DATE, query == null ? null : toLocalDate(query.getFlightDate())),
                eqIfPresent(PASSENGER.ORIG, query == null ? null : query.getOrig()),
                eqIfPresent(PASSENGER.DEST, query == null ? null : query.getDest()),
                eqIfPresent(PASSENGER.ROOM_CODE, query == null ? null : query.getRoomCode())
            ))
            .orderBy(PASSENGER.UPDATE_TIME.desc())
            .fetch(this::mapPassenger);
    }

    @Override
    public int updatePassengerCheckoutStatus(String pId, String status, String origImageUrl, String registerImageUrl) {
        return dsl.update(PASSENGER)
            .set(PASSENGER.STATUS, status)
            .set(PASSENGER.GET_OUT_TIME, LocalDateTime.now())
            .set(PASSENGER.ORI_IMAGE_URL, origImageUrl)
            .set(PASSENGER.REGISTER_IMAGE_URL, registerImageUrl)
            .where(PASSENGER.PID.eq(pId))
            .execute();
    }

    @Override
    public Passenger selectPassengerByPid(String pid) {
        return dsl.select(PASSENGER.fields())
            .from(PASSENGER)
            .where(PASSENGER.PID.eq(pid).and(PASSENGER.CREATE_TIME.ge(LocalDate.now().atStartOfDay())))
            .orderBy(PASSENGER.CREATE_TIME.desc())
            .limit(1)
            .fetchOne(this::mapPassenger);
    }

    @Override
    public Passenger selectPassengerByReid(String reid) {
        return dsl.select(PASSENGER.fields())
            .from(PASSENGER)
            .where(PASSENGER.REID.eq(reid))
            .orderBy(PASSENGER.CREATE_TIME.desc())
            .limit(1)
            .fetchOne(this::mapPassenger);
    }

    @Override
    public List<FlightChangePassengerDTO> selectPassengerWithFlightChangeList(Passenger query) {
        return dsl.select(
                PASSENGER.ID,
                PASSENGER.USER_NAME,
                PASSENGER.ROOM_CODE,
                PASSENGER.FLIGHT_NO,
                PASSENGER.FLIGHT_DATE,
                PASSENGER.FLIGHT_ID,
                PASSENGER.ORI_IMAGE_URL,
                PASSENGER.REGISTER_IMAGE_URL,
                PASSENGER.ORIG,
                PASSENGER.DEST,
                PASSENGER.CABIN,
                PASSENGER.GET_IN_TIME,
                PASSENGER.GET_OUT_TIME,
                PASSENGER.STATUS,
                PASSENGER.CREATE_TIME,
                PASSENGER.UPDATE_TIME,
                PASSENGER.REGION_ID,
                CONFIG_REGION.COORDINATE,
                CONFIG_REGION.REGION_NAME,
                CONFIG_REGION.REMARK,
                FLIGHT_WARNING.WARNING_TYPE,
                FLIGHT_WARNING.CHANGE_BEFORE,
                FLIGHT_WARNING.CHANGE_AFTER
            )
            .from(PASSENGER)
            .join(FLIGHT_WARNING).on(PASSENGER.FLIGHT_ID.eq(FLIGHT_WARNING.FLIGHT_ID))
            .leftJoin(CONFIG_REGION).on(PASSENGER.REGION_ID.eq(CONFIG_REGION.ID))
            .where(DSL.and(
                likeIfPresent(PASSENGER.USER_NAME, query == null ? null : query.getUserName()),
                eqIfPresent(PASSENGER.FLIGHT_ID, query == null ? null : query.getFlightId()),
                eqIfPresent(PASSENGER.FLIGHT_NO, query == null ? null : query.getFlightNo()),
                eqIfPresent(FLIGHT_WARNING.WARNING_TYPE, query == null ? null : query.getWarningType())
            ))
            .orderBy(PASSENGER.CREATE_TIME.desc())
            .fetch(this::mapFlightChangePassenger);
    }

    private Condition passengerConditions(Passenger p) {
        if (p == null) {
            return DSL.noCondition();
        }
        return DSL.and(
            likeIfPresent(PASSENGER.USER_NAME, p.getUserName()),
            eqIfPresent(PASSENGER.ROOM_CODE, p.getRoomCode()),
            eqIfPresent(PASSENGER.FLIGHT_NO, p.getFlightNo()),
            eqIfPresent(PASSENGER.FLIGHT_DATE, toLocalDate(p.getFlightDate())),
            eqIfPresent(PASSENGER.ORIG, p.getOrig()),
            eqIfPresent(PASSENGER.DEST, p.getDest()),
            eqIfPresent(PASSENGER.CABIN, p.getCabin()),
            eqIfPresent(PASSENGER.SEAT, p.getSeat()),
            eqIfPresent(PASSENGER.SEQ, p.getSeq()),
            eqIfPresent(PASSENGER.CARD_SERVICE, p.getCardService()),
            eqIfPresent(PASSENGER.CARD_NO, p.getCardNo()),
            eqIfPresent(PASSENGER.MEM_LEVEL, p.getMemLevel()),
            eqIfPresent(PASSENGER.STAR_LEVEL, p.getStarLevel()),
            eqIfPresent(PASSENGER.IN_TYPE, p.getInType()),
            p.getGetInTime() == null ? DSL.noCondition() : PASSENGER.GET_IN_TIME.eq(toLocalDateTime(p.getGetInTime())),
            p.getGetOutTime() == null ? DSL.noCondition() : PASSENGER.GET_OUT_TIME.eq(toLocalDateTime(p.getGetOutTime())),
            eqIfPresent(PASSENGER.STATUS, p.getStatus()),
            eqIfPresent(PASSENGER.REID, p.getReid()),
            eqIfPresent(PASSENGER.PID, p.getPid()),
            eqIfPresent(PASSENGER.FLIGHT_ID, p.getFlightId())
        );
    }

    private Condition paramConditions(PassengerParam p) {
        if (p == null) {
            return DSL.noCondition();
        }
        return DSL.and(
            likeIfPresent(PASSENGER.USER_NAME, p.getUserName()),
            eqIfPresent(PASSENGER.ROOM_CODE, p.getRoomCode()),
            eqIfPresent(PASSENGER.FLIGHT_NO, p.getFlightNo()),
            eqIfPresent(PASSENGER.FLIGHT_DATE, toLocalDate(p.getFlightDate())),
            eqIfPresent(PASSENGER.CARD_SERVICE, p.getCardService()),
            eqIfPresent(PASSENGER.CARD_NO, p.getCardNo()),
            eqIfPresent(PASSENGER.MEM_LEVEL, p.getMemLevel()),
            eqIfPresent(PASSENGER.STAR_LEVEL, p.getStarLevel()),
            eqIfPresent(PASSENGER.IN_TYPE, p.getInType()),
            eqIfPresent(PASSENGER.STATUS, p.getStatus())
        );
    }

    private Passenger mapPassenger(Record record) {
        Passenger p = new Passenger();
        p.setId(record.get(PASSENGER.ID));
        p.setUserName(record.get(PASSENGER.USER_NAME));
        p.setRoomCode(record.get(PASSENGER.ROOM_CODE));
        p.setFlightNo(record.get(PASSENGER.FLIGHT_NO));
        p.setFlightDate(record.get(PASSENGER.FLIGHT_DATE) == null ? null : record.get(PASSENGER.FLIGHT_DATE).toString());
        p.setOrig(record.get(PASSENGER.ORIG));
        p.setDest(record.get(PASSENGER.DEST));
        p.setCabin(record.get(PASSENGER.CABIN));
        p.setSeat(record.get(PASSENGER.SEAT));
        p.setSeq(record.get(PASSENGER.SEQ));
        p.setCardService(record.get(PASSENGER.CARD_SERVICE));
        p.setCardNo(record.get(PASSENGER.CARD_NO));
        p.setMemLevel(record.get(PASSENGER.MEM_LEVEL));
        p.setStarLevel(record.get(PASSENGER.STAR_LEVEL));
        p.setInType(record.get(PASSENGER.IN_TYPE));
        p.setGetInTime(toDate(record.get(PASSENGER.GET_IN_TIME)));
        p.setGetOutTime(toDate(record.get(PASSENGER.GET_OUT_TIME)));
        p.setStatus(record.get(PASSENGER.STATUS));
        p.setCreateTime(toDate(record.get(PASSENGER.CREATE_TIME)));
        p.setUpdateTime(toDate(record.get(PASSENGER.UPDATE_TIME)));
        p.setReid(record.get(PASSENGER.REID));
        p.setPid(record.get(PASSENGER.PID));
        p.setFlightId(record.get(PASSENGER.FLIGHT_ID));
        p.setRegionId(record.get(PASSENGER.REGION_ID));
        p.setOrigImageUrl(record.get(PASSENGER.ORI_IMAGE_URL));
        p.setRegisterImageUrl(record.get(PASSENGER.REGISTER_IMAGE_URL));
        p.setPhoto(record.get(PASSENGER.PHOTO));
        p.setRobotId(record.get(PASSENGER.ROBOT_ID));
        p.setFollowerNum(record.get(PASSENGER.FOLLOWER_NUM) == null ? 0 : record.get(PASSENGER.FOLLOWER_NUM));
        p.setIsMember(record.get(PASSENGER.IS_MEMBER));
        p.setColledtId(record.get(PASSENGER.COLLEDT_ID));
        p.setCoordinate(record.get(PASSENGER.COORDINATE));
        p.setWarningType(record.get(PASSENGER.WARNING_TYPE));
        p.setRemark(record.get(PASSENGER.REMARK));
        return p;
    }

    private FlightChangePassengerDTO mapFlightChangePassenger(Record record) {
        FlightChangePassengerDTO dto = new FlightChangePassengerDTO();
        dto.setId(record.get(PASSENGER.ID));
        dto.setUserName(record.get(PASSENGER.USER_NAME));
        dto.setRoomCode(record.get(PASSENGER.ROOM_CODE));
        dto.setFlightNo(record.get(PASSENGER.FLIGHT_NO));
        dto.setFlightDate(toDate(record.get(PASSENGER.FLIGHT_DATE)));
        dto.setFlightId(record.get(PASSENGER.FLIGHT_ID));
        dto.setOrigImageUrl(record.get(PASSENGER.ORI_IMAGE_URL));
        dto.setRegisterImageUrl(record.get(PASSENGER.REGISTER_IMAGE_URL));
        dto.setOrig(record.get(PASSENGER.ORIG));
        dto.setDest(record.get(PASSENGER.DEST));
        dto.setCabin(record.get(PASSENGER.CABIN));
        dto.setGetInTime(toDate(record.get(PASSENGER.GET_IN_TIME)));
        dto.setGetOutTime(toDate(record.get(PASSENGER.GET_OUT_TIME)));
        dto.setStatus(record.get(PASSENGER.STATUS));
        dto.setCreateTime(toDate(record.get(PASSENGER.CREATE_TIME)));
        dto.setUpdateTime(toDate(record.get(PASSENGER.UPDATE_TIME)));
        dto.setRegionId(record.get(PASSENGER.REGION_ID) == null ? null : String.valueOf(record.get(PASSENGER.REGION_ID)));
        dto.setCoordinate(record.get(CONFIG_REGION.COORDINATE));
        dto.setRegionName(record.get(CONFIG_REGION.REGION_NAME));
        dto.setRemark(record.get(CONFIG_REGION.REMARK));
        dto.setWarningType(record.get(FLIGHT_WARNING.WARNING_TYPE));
        dto.setChangeBefore(record.get(FLIGHT_WARNING.CHANGE_BEFORE));
        dto.setChangeAfter(record.get(FLIGHT_WARNING.CHANGE_AFTER));
        return dto;
    }

    private Condition eqIfPresent(org.jooq.Field<?> field, Object value) {
        if (value == null || value instanceof String text && text.isBlank()) {
            return DSL.noCondition();
        }
        return ((org.jooq.Field<Object>) field).eq(value);
    }

    private Condition likeIfPresent(org.jooq.Field<String> field, String value) {
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
