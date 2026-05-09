package com.robotmonitor.quartz.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.SYS_JOB;

import com.robotmonitor.quartz.domain.SysJob;
import com.robotmonitor.quartz.mapper.SysJobMapper;
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
public class JooqSysJobMapper implements SysJobMapper {
    private final DSLContext dsl;

    public JooqSysJobMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public List<SysJob> selectJobList(SysJob query) {
        return dsl.select(SYS_JOB.fields())
            .from(SYS_JOB)
            .where(conditions(query))
            .orderBy(SYS_JOB.JOB_ID.asc())
            .fetch(this::mapJob);
    }

    @Override
    public List<SysJob> selectJobAll() {
        return dsl.select(SYS_JOB.fields())
            .from(SYS_JOB)
            .orderBy(SYS_JOB.JOB_ID.asc())
            .fetch(this::mapJob);
    }

    @Override
    public SysJob selectJobById(Long jobId) {
        return dsl.select(SYS_JOB.fields())
            .from(SYS_JOB)
            .where(SYS_JOB.JOB_ID.eq(jobId))
            .fetchOne(this::mapJob);
    }

    @Override
    public int deleteJobById(Long jobId) {
        return dsl.deleteFrom(SYS_JOB)
            .where(SYS_JOB.JOB_ID.eq(jobId))
            .execute();
    }

    @Override
    public int deleteJobByIds(Long[] jobIds) {
        if (jobIds == null || jobIds.length == 0) {
            return 0;
        }
        return dsl.deleteFrom(SYS_JOB)
            .where(SYS_JOB.JOB_ID.in(Arrays.asList(jobIds)))
            .execute();
    }

    @Override
    public int updateJob(SysJob job) {
        if (job.getJobId() == null) {
            return 0;
        }
        Map<Field<?>, Object> values = writeValues(job);
        values.remove(SYS_JOB.JOB_ID);
        values.put(SYS_JOB.UPDATE_TIME, LocalDateTime.now());
        if (values.isEmpty()) {
            return 0;
        }
        return dsl.update(SYS_JOB)
            .set(values)
            .where(SYS_JOB.JOB_ID.eq(job.getJobId()))
            .execute();
    }

    @Override
    public int insertJob(SysJob job) {
        Map<Field<?>, Object> values = writeValues(job);
        values.put(SYS_JOB.CREATE_TIME, LocalDateTime.now());
        Long id = dsl.insertInto(SYS_JOB)
            .set(values)
            .returningResult(SYS_JOB.JOB_ID)
            .fetchOne(SYS_JOB.JOB_ID);
        job.setJobId(id);
        return id == null ? 0 : 1;
    }

    private Condition conditions(SysJob job) {
        if (job == null) {
            return DSL.noCondition();
        }
        return DSL.and(
            isBlank(job.getJobName()) ? DSL.noCondition() : SYS_JOB.JOB_NAME.like("%" + job.getJobName() + "%"),
            isBlank(job.getJobGroup()) ? DSL.noCondition() : SYS_JOB.JOB_GROUP.eq(job.getJobGroup()),
            isBlank(job.getStatus()) ? DSL.noCondition() : SYS_JOB.STATUS.eq(job.getStatus()),
            isBlank(job.getInvokeTarget()) ? DSL.noCondition() : SYS_JOB.INVOKE_TARGET.like("%" + job.getInvokeTarget() + "%")
        );
    }

    private Map<Field<?>, Object> writeValues(SysJob job) {
        Map<Field<?>, Object> values = new LinkedHashMap<>();
        put(values, SYS_JOB.JOB_ID, job.getJobId());
        put(values, SYS_JOB.JOB_NAME, job.getJobName());
        put(values, SYS_JOB.JOB_GROUP, job.getJobGroup());
        put(values, SYS_JOB.INVOKE_TARGET, job.getInvokeTarget());
        put(values, SYS_JOB.CRON_EXPRESSION, job.getCronExpression());
        put(values, SYS_JOB.MISFIRE_POLICY, job.getMisfirePolicy());
        put(values, SYS_JOB.CONCURRENT, job.getConcurrent());
        put(values, SYS_JOB.STATUS, job.getStatus());
        put(values, SYS_JOB.CREATE_BY, job.getCreateBy());
        put(values, SYS_JOB.CREATE_TIME, toLocalDateTime(job.getCreateTime()));
        put(values, SYS_JOB.UPDATE_BY, job.getUpdateBy());
        put(values, SYS_JOB.UPDATE_TIME, toLocalDateTime(job.getUpdateTime()));
        put(values, SYS_JOB.REMARK, job.getRemark());
        return values;
    }

    private SysJob mapJob(Record record) {
        SysJob job = new SysJob();
        job.setJobId(record.get(SYS_JOB.JOB_ID));
        job.setJobName(record.get(SYS_JOB.JOB_NAME));
        job.setJobGroup(record.get(SYS_JOB.JOB_GROUP));
        job.setInvokeTarget(record.get(SYS_JOB.INVOKE_TARGET));
        job.setCronExpression(record.get(SYS_JOB.CRON_EXPRESSION));
        job.setMisfirePolicy(record.get(SYS_JOB.MISFIRE_POLICY));
        job.setConcurrent(record.get(SYS_JOB.CONCURRENT));
        job.setStatus(record.get(SYS_JOB.STATUS));
        job.setCreateBy(record.get(SYS_JOB.CREATE_BY));
        job.setCreateTime(toDate(record.get(SYS_JOB.CREATE_TIME)));
        job.setUpdateBy(record.get(SYS_JOB.UPDATE_BY));
        job.setUpdateTime(toDate(record.get(SYS_JOB.UPDATE_TIME)));
        job.setRemark(record.get(SYS_JOB.REMARK));
        return job;
    }

    private LocalDateTime toLocalDateTime(Date value) {
        return value == null ? null : LocalDateTime.ofInstant(value.toInstant(), ZoneId.systemDefault());
    }

    private Date toDate(LocalDateTime value) {
        return value == null ? null : Date.from(value.atZone(ZoneId.systemDefault()).toInstant());
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private void put(Map<Field<?>, Object> values, Field<?> field, Object value) {
        if (value != null && (!(value instanceof String text) || !text.isBlank())) {
            values.put(field, value);
        }
    }
}
