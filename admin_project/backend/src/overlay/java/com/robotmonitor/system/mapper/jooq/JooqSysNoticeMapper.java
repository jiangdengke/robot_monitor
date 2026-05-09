package com.robotmonitor.system.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.SYS_NOTICE;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.contains;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.equalsIfPresent;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.toDate;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.toLocalDateTime;

import com.robotmonitor.system.domain.SysNotice;
import com.robotmonitor.system.mapper.SysNoticeMapper;
import java.util.Arrays;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqSysNoticeMapper implements SysNoticeMapper {
    private final DSLContext dsl;

    public JooqSysNoticeMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public SysNotice selectNoticeById(Long noticeId) {
        return this.dsl.selectFrom(SYS_NOTICE)
            .where(SYS_NOTICE.NOTICE_ID.eq(noticeId))
            .fetchOne(this::map);
    }

    @Override
    public List<SysNotice> selectNoticeList(SysNotice notice) {
        return this.dsl.selectFrom(SYS_NOTICE)
            .where(contains(SYS_NOTICE.NOTICE_TITLE, notice == null ? null : notice.getNoticeTitle()))
            .and(equalsIfPresent(SYS_NOTICE.NOTICE_TYPE, notice == null ? null : notice.getNoticeType()))
            .and(equalsIfPresent(SYS_NOTICE.STATUS, notice == null ? null : notice.getStatus()))
            .orderBy(SYS_NOTICE.CREATE_TIME.desc(), SYS_NOTICE.NOTICE_ID.desc())
            .fetch(this::map);
    }

    @Override
    public int insertNotice(SysNotice notice) {
        return this.dsl.insertInto(SYS_NOTICE)
            .set(SYS_NOTICE.NOTICE_TITLE, notice.getNoticeTitle())
            .set(SYS_NOTICE.NOTICE_TYPE, notice.getNoticeType())
            .set(SYS_NOTICE.NOTICE_CONTENT, notice.getNoticeContent())
            .set(SYS_NOTICE.STATUS, notice.getStatus())
            .set(SYS_NOTICE.CREATE_BY, notice.getCreateBy())
            .set(SYS_NOTICE.CREATE_TIME, toLocalDateTime(notice.getCreateTime()))
            .set(SYS_NOTICE.REMARK, notice.getRemark())
            .execute();
    }

    @Override
    public int updateNotice(SysNotice notice) {
        return this.dsl.update(SYS_NOTICE)
            .set(SYS_NOTICE.NOTICE_TITLE, notice.getNoticeTitle())
            .set(SYS_NOTICE.NOTICE_TYPE, notice.getNoticeType())
            .set(SYS_NOTICE.NOTICE_CONTENT, notice.getNoticeContent())
            .set(SYS_NOTICE.STATUS, notice.getStatus())
            .set(SYS_NOTICE.UPDATE_BY, notice.getUpdateBy())
            .set(SYS_NOTICE.UPDATE_TIME, toLocalDateTime(notice.getUpdateTime()))
            .set(SYS_NOTICE.REMARK, notice.getRemark())
            .where(SYS_NOTICE.NOTICE_ID.eq(notice.getNoticeId()))
            .execute();
    }

    @Override
    public int deleteNoticeById(Long noticeId) {
        return this.dsl.deleteFrom(SYS_NOTICE)
            .where(SYS_NOTICE.NOTICE_ID.eq(noticeId))
            .execute();
    }

    @Override
    public int deleteNoticeByIds(Long[] noticeIds) {
        return this.dsl.deleteFrom(SYS_NOTICE)
            .where(SYS_NOTICE.NOTICE_ID.in(Arrays.asList(noticeIds)))
            .execute();
    }

    private SysNotice map(Record record) {
        SysNotice notice = new SysNotice();
        notice.setNoticeId(record.get(SYS_NOTICE.NOTICE_ID));
        notice.setNoticeTitle(record.get(SYS_NOTICE.NOTICE_TITLE));
        notice.setNoticeType(record.get(SYS_NOTICE.NOTICE_TYPE));
        notice.setNoticeContent(record.get(SYS_NOTICE.NOTICE_CONTENT));
        notice.setStatus(record.get(SYS_NOTICE.STATUS));
        notice.setCreateBy(record.get(SYS_NOTICE.CREATE_BY));
        notice.setCreateTime(toDate(record.get(SYS_NOTICE.CREATE_TIME)));
        notice.setUpdateBy(record.get(SYS_NOTICE.UPDATE_BY));
        notice.setUpdateTime(toDate(record.get(SYS_NOTICE.UPDATE_TIME)));
        notice.setRemark(record.get(SYS_NOTICE.REMARK));
        return notice;
    }
}
