package com.robotmonitor.quartz.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.SYS_JOB_LOG;

import com.robotmonitor.quartz.domain.SysJobLog;
import com.robotmonitor.quartz.mapper.SysJobLogMapper;
import java.time.LocalDate;
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
public class JooqSysJobLogMapper implements SysJobLogMapper {
    private final DSLContext dsl;

    public JooqSysJobLogMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public List<SysJobLog> selectJobLogList(SysJobLog query) {
        return dsl.select(SYS_JOB_LOG.fields())
            .from(SYS_JOB_LOG)
            .where(conditions(query))
            .orderBy(SYS_JOB_LOG.CREATE_TIME.desc())
            .fetch(this::map);
    }

    @Override
    public List<SysJobLog> selectJobLogAll() {
        return dsl.select(SYS_JOB_LOG.fields())
            .from(SYS_JOB_LOG)
            .orderBy(SYS_JOB_LOG.CREATE_TIME.desc())
            .fetch(this::map);
    }

    @Override
    public SysJobLog selectJobLogById(Long jobLogId) {
        return dsl.select(SYS_JOB_LOG.fields())
            .from(SYS_JOB_LOG)
            .where(SYS_JOB_LOG.JOB_LOG_ID.eq(jobLogId))
            .fetchOne(this::map);
    }

    @Override
    public int insertJobLog(SysJobLog log) {
        Map<Field<?>, Object> values = writeValues(log);
        values.put(SYS_JOB_LOG.CREATE_TIME, LocalDateTime.now());
        Long id = dsl.insertInto(SYS_JOB_LOG)
            .set(values)
            .returningResult(SYS_JOB_LOG.JOB_LOG_ID)
            .fetchOne(SYS_JOB_LOG.JOB_LOG_ID);
        log.setJobLogId(id);
        return id == null ? 0 : 1;
    }

    @Override
    public int deleteJobLogByIds(Long[] jobLogIds) {
        if (jobLogIds == null || jobLogIds.length == 0) {
            return 0;
        }
        return dsl.deleteFrom(SYS_JOB_LOG)
            .where(SYS_JOB_LOG.JOB_LOG_ID.in(Arrays.asList(jobLogIds)))
            .execute();
    }

    @Override
    public int deleteJobLogById(Long jobLogId) {
        return dsl.deleteFrom(SYS_JOB_LOG)
            .where(SYS_JOB_LOG.JOB_LOG_ID.eq(jobLogId))
            .execute();
    }

    @Override
    public void cleanJobLog() {
        dsl.deleteFrom(SYS_JOB_LOG).execute();
    }

    private Condition conditions(SysJobLog log) {
        if (log == null) {
            return DSL.noCondition();
        }
        return DSL.and(
            isBlank(log.getJobName()) ? DSL.noCondition() : SYS_JOB_LOG.JOB_NAME.like("%" + log.getJobName() + "%"),
            isBlank(log.getJobGroup()) ? DSL.noCondition() : SYS_JOB_LOG.JOB_GROUP.eq(log.getJobGroup()),
            isBlank(log.getStatus()) ? DSL.noCondition() : SYS_JOB_LOG.STATUS.eq(log.getStatus()),
            isBlank(log.getInvokeTarget()) ? DSL.noCondition() : SYS_JOB_LOG.INVOKE_TARGET.like("%" + log.getInvokeTarget() + "%"),
            dateBoundary(log, "beginTime", true),
            dateBoundary(log, "endTime", false)
        );
    }

    private Condition dateBoundary(SysJobLog log, String key, boolean begin) {
        Object value = log.getParams().get(key);
        if (value == null || isBlank(String.valueOf(value))) {
            return DSL.noCondition();
        }
        LocalDate date = toLocalDate(value);
        LocalDateTime boundary = begin ? date.atStartOfDay() : date.plusDays(1).atStartOfDay();
        return begin ? SYS_JOB_LOG.CREATE_TIME.ge(boundary) : SYS_JOB_LOG.CREATE_TIME.lt(boundary);
    }

    private Map<Field<?>, Object> writeValues(SysJobLog log) {
        Map<Field<?>, Object> values = new LinkedHashMap<>();
        put(values, SYS_JOB_LOG.JOB_LOG_ID, log.getJobLogId());
        put(values, SYS_JOB_LOG.JOB_NAME, log.getJobName());
        put(values, SYS_JOB_LOG.JOB_GROUP, log.getJobGroup());
        put(values, SYS_JOB_LOG.INVOKE_TARGET, log.getInvokeTarget());
        put(values, SYS_JOB_LOG.JOB_MESSAGE, log.getJobMessage());
        put(values, SYS_JOB_LOG.STATUS, log.getStatus());
        put(values, SYS_JOB_LOG.EXCEPTION_INFO, log.getExceptionInfo());
        return values;
    }

    private SysJobLog map(Record record) {
        SysJobLog log = new SysJobLog();
        log.setJobLogId(record.get(SYS_JOB_LOG.JOB_LOG_ID));
        log.setJobName(record.get(SYS_JOB_LOG.JOB_NAME));
        log.setJobGroup(record.get(SYS_JOB_LOG.JOB_GROUP));
        log.setInvokeTarget(record.get(SYS_JOB_LOG.INVOKE_TARGET));
        log.setJobMessage(record.get(SYS_JOB_LOG.JOB_MESSAGE));
        log.setStatus(record.get(SYS_JOB_LOG.STATUS));
        log.setExceptionInfo(record.get(SYS_JOB_LOG.EXCEPTION_INFO));
        log.setCreateTime(toDate(record.get(SYS_JOB_LOG.CREATE_TIME)));
        return log;
    }

    private LocalDate toLocalDate(Object value) {
        if (value instanceof Date date) {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        String text = String.valueOf(value).trim();
        if (text.length() >= 10) {
            return LocalDate.parse(text.substring(0, 10));
        }
        return LocalDate.parse(text);
    }

    private Date toDate(LocalDateTime value) {
        return value == null ? null : Date.from(value.atZone(ZoneId.systemDefault()).toInstant());
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void put(Map<Field<?>, Object> values, Field<?> field, Object value) {
        if (value != null && (!(value instanceof String text) || !text.isBlank())) {
            values.put(field, value);
        }
    }
}
