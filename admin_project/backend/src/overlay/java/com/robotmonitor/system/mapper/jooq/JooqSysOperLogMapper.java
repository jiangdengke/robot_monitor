package com.robotmonitor.system.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.SYS_OPER_LOG;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.contains;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.equalsIfPresent;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.toDate;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.toLocalDateTime;

import com.robotmonitor.system.domain.SysOperLog;
import com.robotmonitor.system.mapper.SysOperLogMapper;
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
public class JooqSysOperLogMapper implements SysOperLogMapper {
    private final DSLContext dsl;

    public JooqSysOperLogMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public void insertOperlog(SysOperLog operLog) {
        this.dsl.insertInto(SYS_OPER_LOG)
            .set(SYS_OPER_LOG.TITLE, operLog.getTitle())
            .set(SYS_OPER_LOG.BUSINESS_TYPE, operLog.getBusinessType())
            .set(SYS_OPER_LOG.METHOD, operLog.getMethod())
            .set(SYS_OPER_LOG.REQUEST_METHOD, operLog.getRequestMethod())
            .set(SYS_OPER_LOG.OPERATOR_TYPE, operLog.getOperatorType())
            .set(SYS_OPER_LOG.OPER_NAME, operLog.getOperName())
            .set(SYS_OPER_LOG.DEPT_NAME, operLog.getDeptName())
            .set(SYS_OPER_LOG.OPER_URL, operLog.getOperUrl())
            .set(SYS_OPER_LOG.OPER_IP, operLog.getOperIp())
            .set(SYS_OPER_LOG.OPER_LOCATION, operLog.getOperLocation())
            .set(SYS_OPER_LOG.OPER_PARAM, operLog.getOperParam())
            .set(SYS_OPER_LOG.JSON_RESULT, operLog.getJsonResult())
            .set(SYS_OPER_LOG.STATUS, operLog.getStatus())
            .set(SYS_OPER_LOG.ERROR_MSG, operLog.getErrorMsg())
            .set(SYS_OPER_LOG.OPER_TIME, toLocalDateTime(operLog.getOperTime()))
            .execute();
    }

    @Override
    public List<SysOperLog> selectOperLogList(SysOperLog operLog) {
        return this.dsl.selectFrom(SYS_OPER_LOG)
            .where(contains(SYS_OPER_LOG.TITLE, operLog == null ? null : operLog.getTitle()))
            .and(equalsIfPresent(SYS_OPER_LOG.BUSINESS_TYPE, operLog == null ? null : operLog.getBusinessType()))
            .and(equalsIfPresent(SYS_OPER_LOG.STATUS, operLog == null ? null : operLog.getStatus()))
            .and(contains(SYS_OPER_LOG.OPER_NAME, operLog == null ? null : operLog.getOperName()))
            .and(businessTypes(operLog))
            .orderBy(SYS_OPER_LOG.OPER_ID.desc())
            .fetch(this::map);
    }

    @Override
    public int deleteOperLogByIds(Long[] operIds) {
        return this.dsl.deleteFrom(SYS_OPER_LOG)
            .where(SYS_OPER_LOG.OPER_ID.in(Arrays.asList(operIds)))
            .execute();
    }

    @Override
    public SysOperLog selectOperLogById(Long operId) {
        return this.dsl.selectFrom(SYS_OPER_LOG)
            .where(SYS_OPER_LOG.OPER_ID.eq(operId))
            .fetchOne(this::map);
    }

    @Override
    public void cleanOperLog() {
        this.dsl.truncate(SYS_OPER_LOG).execute();
    }

    private Condition businessTypes(SysOperLog operLog) {
        if (operLog == null || operLog.getBusinessTypes() == null || operLog.getBusinessTypes().length == 0) {
            return DSL.noCondition();
        }
        return SYS_OPER_LOG.BUSINESS_TYPE.in(Arrays.asList(operLog.getBusinessTypes()));
    }

    private SysOperLog map(Record record) {
        SysOperLog log = new SysOperLog();
        log.setOperId(record.get(SYS_OPER_LOG.OPER_ID));
        log.setTitle(record.get(SYS_OPER_LOG.TITLE));
        log.setBusinessType(record.get(SYS_OPER_LOG.BUSINESS_TYPE));
        log.setMethod(record.get(SYS_OPER_LOG.METHOD));
        log.setRequestMethod(record.get(SYS_OPER_LOG.REQUEST_METHOD));
        log.setOperatorType(record.get(SYS_OPER_LOG.OPERATOR_TYPE));
        log.setOperName(record.get(SYS_OPER_LOG.OPER_NAME));
        log.setDeptName(record.get(SYS_OPER_LOG.DEPT_NAME));
        log.setOperUrl(record.get(SYS_OPER_LOG.OPER_URL));
        log.setOperIp(record.get(SYS_OPER_LOG.OPER_IP));
        log.setOperLocation(record.get(SYS_OPER_LOG.OPER_LOCATION));
        log.setOperParam(record.get(SYS_OPER_LOG.OPER_PARAM));
        log.setJsonResult(record.get(SYS_OPER_LOG.JSON_RESULT));
        log.setStatus(record.get(SYS_OPER_LOG.STATUS));
        log.setErrorMsg(record.get(SYS_OPER_LOG.ERROR_MSG));
        log.setOperTime(toDate(record.get(SYS_OPER_LOG.OPER_TIME)));
        return log;
    }
}
