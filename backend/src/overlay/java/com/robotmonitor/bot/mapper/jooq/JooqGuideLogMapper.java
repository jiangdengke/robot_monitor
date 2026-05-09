package com.robotmonitor.bot.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.CONFIG_REGION;
import static com.robotmonitor.jooq.generated.Tables.CONFIG_ROBOT;
import static com.robotmonitor.jooq.generated.Tables.GUIDE_LOG;
import static com.robotmonitor.jooq.generated.Tables.SYS_DEPT;

import com.robotmonitor.bot.domain.GuideLog;
import com.robotmonitor.bot.domain.GuideLogInfo;
import com.robotmonitor.bot.domain.GuideLogInfoRequest;
import com.robotmonitor.bot.mapper.GuideLogMapper;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
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
public class JooqGuideLogMapper implements GuideLogMapper {
    private final DSLContext dsl;

    public JooqGuideLogMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public GuideLog selectGuideLogById(Long id) {
        return dsl.select(GUIDE_LOG.fields())
            .from(GUIDE_LOG)
            .where(GUIDE_LOG.ID.eq(id))
            .fetchOne(this::mapLog);
    }

    @Override
    public List<GuideLog> selectGuideLogList(GuideLog query) {
        return dsl.select(GUIDE_LOG.fields())
            .from(GUIDE_LOG)
            .where(conditions(query))
            .orderBy(GUIDE_LOG.CREATE_TIME.desc())
            .fetch(this::mapLog);
    }

    @Override
    public List<GuideLogInfo> selectGuideLogInfoList(GuideLogInfoRequest query) {
        return dsl.select(
                GUIDE_LOG.ROBOT_ID,
                CONFIG_ROBOT.ROBOT_NAME,
                GUIDE_LOG.REGION_ID,
                CONFIG_REGION.REGION_NAME,
                CONFIG_ROBOT.ROOM_CODE,
                SYS_DEPT.DEPT_NAME,
                GUIDE_LOG.COORDINATE,
                GUIDE_LOG.CREATE_TIME
            )
            .from(GUIDE_LOG)
            .join(CONFIG_ROBOT).on(GUIDE_LOG.ROBOT_ID.eq(CONFIG_ROBOT.ROBOT_ID))
            .join(CONFIG_REGION).on(GUIDE_LOG.REGION_ID.eq(CONFIG_REGION.ID))
            .join(SYS_DEPT).on(CONFIG_ROBOT.ROOM_CODE.eq(SYS_DEPT.ROOM_CODE))
            .where(infoConditions(query))
            .orderBy(GUIDE_LOG.CREATE_TIME.desc())
            .fetch(this::mapInfo);
    }

    @Override
    public int insertGuideLog(GuideLog log) {
        Long id = dsl.insertInto(GUIDE_LOG)
            .set(writeValues(log))
            .returningResult(GUIDE_LOG.ID)
            .fetchOne(GUIDE_LOG.ID);
        log.setId(id);
        return id == null ? 0 : 1;
    }

    @Override
    public int updateGuideLog(GuideLog log) {
        if (log.getId() == null) {
            return 0;
        }
        Map<Field<?>, Object> values = writeValues(log);
        values.remove(GUIDE_LOG.ID);
        if (values.isEmpty()) {
            return 0;
        }
        return dsl.update(GUIDE_LOG)
            .set(values)
            .where(GUIDE_LOG.ID.eq(log.getId()))
            .execute();
    }

    @Override
    public int deleteGuideLogById(Long id) {
        return dsl.deleteFrom(GUIDE_LOG).where(GUIDE_LOG.ID.eq(id)).execute();
    }

    @Override
    public int deleteGuideLogByIds(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }
        return dsl.deleteFrom(GUIDE_LOG).where(GUIDE_LOG.ID.in(Arrays.asList(ids))).execute();
    }

    private Condition conditions(GuideLog log) {
        if (log == null) {
            return DSL.noCondition();
        }
        return DSL.and(
            eqIfPresent(GUIDE_LOG.ROBOT_ID, log.getRobotId()),
            eqIfPresent(GUIDE_LOG.REGION_ID, log.getRegionId()),
            eqIfPresent(GUIDE_LOG.COORDINATE, log.getCoordinate())
        );
    }

    private Condition infoConditions(GuideLogInfoRequest query) {
        if (query == null) {
            return DSL.noCondition();
        }
        return DSL.and(
            isBlank(query.getRobotName()) ? DSL.noCondition() : CONFIG_ROBOT.ROBOT_NAME.like("%" + query.getRobotName() + "%"),
            eqIfPresent(CONFIG_ROBOT.ROOM_CODE, query.getRoomCode()),
            query.getStartTime() == null ? DSL.noCondition() : GUIDE_LOG.CREATE_TIME.ge(toLocalDateTime(query.getStartTime())),
            query.getEndTime() == null ? DSL.noCondition() : GUIDE_LOG.CREATE_TIME.le(toLocalDateTime(query.getEndTime()))
        );
    }

    private Map<Field<?>, Object> writeValues(GuideLog log) {
        Map<Field<?>, Object> values = new LinkedHashMap<>();
        put(values, GUIDE_LOG.ID, log.getId());
        put(values, GUIDE_LOG.ROBOT_ID, log.getRobotId());
        put(values, GUIDE_LOG.REGION_ID, log.getRegionId());
        put(values, GUIDE_LOG.COORDINATE, log.getCoordinate());
        put(values, GUIDE_LOG.CREATE_TIME, toLocalDateTime(log.getCreateTime()));
        return values;
    }

    private GuideLog mapLog(Record record) {
        GuideLog log = new GuideLog();
        log.setId(record.get(GUIDE_LOG.ID));
        log.setRobotId(record.get(GUIDE_LOG.ROBOT_ID));
        log.setRegionId(record.get(GUIDE_LOG.REGION_ID));
        log.setCoordinate(record.get(GUIDE_LOG.COORDINATE));
        log.setCreateTime(toDate(record.get(GUIDE_LOG.CREATE_TIME)));
        return log;
    }

    private GuideLogInfo mapInfo(Record record) {
        GuideLogInfo info = new GuideLogInfo();
        info.setRobotId(record.get(GUIDE_LOG.ROBOT_ID));
        info.setRobotName(record.get(CONFIG_ROBOT.ROBOT_NAME));
        info.setRegionId(record.get(GUIDE_LOG.REGION_ID));
        info.setRegionName(record.get(CONFIG_REGION.REGION_NAME));
        info.setRoomCode(record.get(CONFIG_ROBOT.ROOM_CODE));
        info.setDeptName(record.get(SYS_DEPT.DEPT_NAME));
        info.setCoordinate(record.get(GUIDE_LOG.COORDINATE));
        info.setCreateTime(toDate(record.get(GUIDE_LOG.CREATE_TIME)));
        return info;
    }

    private LocalDateTime toLocalDateTime(Date value) {
        return value == null ? null : LocalDateTime.ofInstant(value.toInstant(), ZoneId.systemDefault());
    }

    private Date toDate(LocalDateTime value) {
        return value == null ? null : Date.from(value.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Condition eqIfPresent(Field<?> field, Object value) {
        if (value == null || value instanceof String text && text.isBlank()) {
            return DSL.noCondition();
        }
        return ((Field<Object>) field).eq(value);
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private void put(Map<Field<?>, Object> values, Field<?> field, Object value) {
        if (value != null) {
            values.put(field, value);
        }
    }
}
