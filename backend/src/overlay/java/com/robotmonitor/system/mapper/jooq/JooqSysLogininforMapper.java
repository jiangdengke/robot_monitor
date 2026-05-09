package com.robotmonitor.system.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.SYS_LOGININFOR;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.contains;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.equalsIfPresent;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.toDate;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.toLocalDateTime;

import com.robotmonitor.system.domain.SysLogininfor;
import com.robotmonitor.system.mapper.SysLogininforMapper;
import java.util.Arrays;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqSysLogininforMapper implements SysLogininforMapper {
    private final DSLContext dsl;

    public JooqSysLogininforMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public void insertLogininfor(SysLogininfor logininfor) {
        this.dsl.insertInto(SYS_LOGININFOR)
            .set(SYS_LOGININFOR.USER_NAME, logininfor.getUserName())
            .set(SYS_LOGININFOR.STATUS, logininfor.getStatus())
            .set(SYS_LOGININFOR.IPADDR, logininfor.getIpaddr())
            .set(SYS_LOGININFOR.LOGIN_LOCATION, logininfor.getLoginLocation())
            .set(SYS_LOGININFOR.BROWSER, logininfor.getBrowser())
            .set(SYS_LOGININFOR.OS, logininfor.getOs())
            .set(SYS_LOGININFOR.MSG, logininfor.getMsg())
            .set(SYS_LOGININFOR.LOGIN_TIME, toLocalDateTime(logininfor.getLoginTime()))
            .execute();
    }

    @Override
    public List<SysLogininfor> selectLogininforList(SysLogininfor logininfor) {
        return this.dsl.selectFrom(SYS_LOGININFOR)
            .where(contains(SYS_LOGININFOR.IPADDR, logininfor == null ? null : logininfor.getIpaddr()))
            .and(contains(SYS_LOGININFOR.USER_NAME, logininfor == null ? null : logininfor.getUserName()))
            .and(equalsIfPresent(SYS_LOGININFOR.STATUS, logininfor == null ? null : logininfor.getStatus()))
            .orderBy(SYS_LOGININFOR.INFO_ID.desc())
            .fetch(this::map);
    }

    @Override
    public int deleteLogininforByIds(Long[] infoIds) {
        return this.dsl.deleteFrom(SYS_LOGININFOR)
            .where(SYS_LOGININFOR.INFO_ID.in(Arrays.asList(infoIds)))
            .execute();
    }

    @Override
    public int cleanLogininfor() {
        return this.dsl.truncate(SYS_LOGININFOR).execute();
    }

    private SysLogininfor map(Record record) {
        SysLogininfor info = new SysLogininfor();
        info.setInfoId(record.get(SYS_LOGININFOR.INFO_ID));
        info.setUserName(record.get(SYS_LOGININFOR.USER_NAME));
        info.setStatus(record.get(SYS_LOGININFOR.STATUS));
        info.setIpaddr(record.get(SYS_LOGININFOR.IPADDR));
        info.setLoginLocation(record.get(SYS_LOGININFOR.LOGIN_LOCATION));
        info.setBrowser(record.get(SYS_LOGININFOR.BROWSER));
        info.setOs(record.get(SYS_LOGININFOR.OS));
        info.setMsg(record.get(SYS_LOGININFOR.MSG));
        info.setLoginTime(toDate(record.get(SYS_LOGININFOR.LOGIN_TIME)));
        return info;
    }
}
