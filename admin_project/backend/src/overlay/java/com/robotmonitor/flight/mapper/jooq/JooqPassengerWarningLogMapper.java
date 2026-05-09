package com.robotmonitor.flight.mapper.jooq;

import static com.robotmonitor.flight.mapper.jooq.JooqFlightMapperSupport.toDate;
import static com.robotmonitor.flight.mapper.jooq.JooqFlightMapperSupport.toLocalDateTime;
import static com.robotmonitor.jooq.generated.Tables.PASSENGER_WARNING_LOG;

import com.robotmonitor.flight.domain.PassengerWarningLog;
import com.robotmonitor.flight.mapper.PassengerWarningLogMapper;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqPassengerWarningLogMapper implements PassengerWarningLogMapper {
    private final DSLContext dsl;

    public JooqPassengerWarningLogMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public PassengerWarningLog selectPassengerWarningLogById(Long id) {
        return dsl.select(PASSENGER_WARNING_LOG.fields())
            .from(PASSENGER_WARNING_LOG)
            .where(PASSENGER_WARNING_LOG.ID.eq(id))
            .fetchOne(this::mapLog);
    }

    @Override
    public List<PassengerWarningLog> selectPassengerWarningLogList(PassengerWarningLog query) {
        return dsl.select(PASSENGER_WARNING_LOG.fields())
            .from(PASSENGER_WARNING_LOG)
            .where(logConditions(query))
            .fetch(this::mapLog);
    }

    @Override
    public int insertPassengerWarningLog(PassengerWarningLog log) {
        Long id = dsl.insertInto(PASSENGER_WARNING_LOG)
            .set(PASSENGER_WARNING_LOG.PASSENGER_ID, log.getPassengerId())
            .set(PASSENGER_WARNING_LOG.FLIGHT_ID, log.getFlightId())
            .set(PASSENGER_WARNING_LOG.FLIGHT_WARNING_ID, log.getFlightWarningId())
            .set(PASSENGER_WARNING_LOG.WARNING_TYPE, log.getWarningType())
            .set(PASSENGER_WARNING_LOG.NOTICE_TYPE, log.getNoticeType())
            .set(PASSENGER_WARNING_LOG.REGION_ID, log.getRegionId())
            .set(PASSENGER_WARNING_LOG.WARNING_INFO, log.getWarningInfo())
            .set(PASSENGER_WARNING_LOG.IS_SUCCESS, log.getIsSuccess())
            .set(PASSENGER_WARNING_LOG.ROBOT_TASK_ID, log.getRobotTaskId())
            .set(PASSENGER_WARNING_LOG.CREATE_BY, log.getCreateBy())
            .set(PASSENGER_WARNING_LOG.CREATE_TIME, toLocalDateTime(log.getCreateTime()))
            .set(PASSENGER_WARNING_LOG.UPDATE_BY, log.getUpdateBy())
            .set(PASSENGER_WARNING_LOG.UPDATE_TIME, toLocalDateTime(log.getUpdateTime()))
            .returningResult(PASSENGER_WARNING_LOG.ID)
            .fetchOne(PASSENGER_WARNING_LOG.ID);
        log.setId(id);
        return id == null ? 0 : 1;
    }

    @Override
    public int updatePassengerWarningLog(PassengerWarningLog log) {
        if (log.getId() == null) {
            return 0;
        }
        return dsl.update(PASSENGER_WARNING_LOG)
            .set(PASSENGER_WARNING_LOG.PASSENGER_ID, log.getPassengerId())
            .set(PASSENGER_WARNING_LOG.FLIGHT_ID, log.getFlightId())
            .set(PASSENGER_WARNING_LOG.FLIGHT_WARNING_ID, log.getFlightWarningId())
            .set(PASSENGER_WARNING_LOG.WARNING_TYPE, log.getWarningType())
            .set(PASSENGER_WARNING_LOG.NOTICE_TYPE, log.getNoticeType())
            .set(PASSENGER_WARNING_LOG.REGION_ID, log.getRegionId())
            .set(PASSENGER_WARNING_LOG.WARNING_INFO, log.getWarningInfo())
            .set(PASSENGER_WARNING_LOG.IS_SUCCESS, log.getIsSuccess())
            .set(PASSENGER_WARNING_LOG.ROBOT_TASK_ID, log.getRobotTaskId())
            .set(PASSENGER_WARNING_LOG.CREATE_BY, log.getCreateBy())
            .set(PASSENGER_WARNING_LOG.CREATE_TIME, toLocalDateTime(log.getCreateTime()))
            .set(PASSENGER_WARNING_LOG.UPDATE_BY, log.getUpdateBy())
            .set(PASSENGER_WARNING_LOG.UPDATE_TIME, toLocalDateTime(log.getUpdateTime()))
            .where(PASSENGER_WARNING_LOG.ID.eq(log.getId()))
            .execute();
    }

    @Override
    public int deletePassengerWarningLogById(Long id) {
        return dsl.deleteFrom(PASSENGER_WARNING_LOG).where(PASSENGER_WARNING_LOG.ID.eq(id)).execute();
    }

    @Override
    public int deletePassengerWarningLogByIds(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }
        return dsl.deleteFrom(PASSENGER_WARNING_LOG).where(PASSENGER_WARNING_LOG.ID.in(Arrays.asList(ids))).execute();
    }

    @Override
    public List<PassengerWarningLog> selectCurWarningLogList() {
        return dsl.select(PASSENGER_WARNING_LOG.fields())
            .from(PASSENGER_WARNING_LOG)
            .where(PASSENGER_WARNING_LOG.CREATE_TIME.ge(LocalDate.now().atStartOfDay()))
            .fetch(this::mapLog);
    }

    private Condition logConditions(PassengerWarningLog log) {
        if (log == null) {
            return DSL.noCondition();
        }
        return DSL.and(
            eqIfPresent(PASSENGER_WARNING_LOG.PASSENGER_ID, log.getPassengerId()),
            eqIfPresent(PASSENGER_WARNING_LOG.FLIGHT_ID, log.getFlightId()),
            eqIfPresent(PASSENGER_WARNING_LOG.FLIGHT_WARNING_ID, log.getFlightWarningId()),
            eqIfPresent(PASSENGER_WARNING_LOG.WARNING_TYPE, log.getWarningType()),
            eqIfPresent(PASSENGER_WARNING_LOG.NOTICE_TYPE, log.getNoticeType()),
            eqIfPresent(PASSENGER_WARNING_LOG.REGION_ID, log.getRegionId()),
            eqIfPresent(PASSENGER_WARNING_LOG.WARNING_INFO, log.getWarningInfo()),
            eqIfPresent(PASSENGER_WARNING_LOG.IS_SUCCESS, log.getIsSuccess()),
            eqIfPresent(PASSENGER_WARNING_LOG.ROBOT_TASK_ID, log.getRobotTaskId())
        );
    }

    private PassengerWarningLog mapLog(Record record) {
        PassengerWarningLog log = new PassengerWarningLog();
        log.setId(record.get(PASSENGER_WARNING_LOG.ID));
        log.setPassengerId(record.get(PASSENGER_WARNING_LOG.PASSENGER_ID));
        log.setFlightId(record.get(PASSENGER_WARNING_LOG.FLIGHT_ID));
        log.setFlightWarningId(record.get(PASSENGER_WARNING_LOG.FLIGHT_WARNING_ID));
        log.setWarningType(record.get(PASSENGER_WARNING_LOG.WARNING_TYPE));
        log.setNoticeType(record.get(PASSENGER_WARNING_LOG.NOTICE_TYPE));
        log.setRegionId(record.get(PASSENGER_WARNING_LOG.REGION_ID));
        log.setWarningInfo(record.get(PASSENGER_WARNING_LOG.WARNING_INFO));
        log.setIsSuccess(record.get(PASSENGER_WARNING_LOG.IS_SUCCESS));
        log.setRobotTaskId(record.get(PASSENGER_WARNING_LOG.ROBOT_TASK_ID));
        log.setCreateBy(record.get(PASSENGER_WARNING_LOG.CREATE_BY));
        log.setCreateTime(toDate(record.get(PASSENGER_WARNING_LOG.CREATE_TIME)));
        log.setUpdateBy(record.get(PASSENGER_WARNING_LOG.UPDATE_BY));
        log.setUpdateTime(toDate(record.get(PASSENGER_WARNING_LOG.UPDATE_TIME)));
        return log;
    }

    private Condition eqIfPresent(org.jooq.Field<?> field, Object value) {
        if (value == null || value instanceof String text && text.isBlank()) {
            return DSL.noCondition();
        }
        return ((org.jooq.Field<Object>) field).eq(value);
    }
}
