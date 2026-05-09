package com.robotmonitor.system.repository.jooq;

import static com.robotmonitor.jooq.generated.Tables.SYS_POST;
import static com.robotmonitor.jooq.generated.Tables.SYS_USER;
import static com.robotmonitor.jooq.generated.Tables.SYS_USER_POST;

import com.robotmonitor.system.domain.SysPost;
import java.util.Date;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

@Repository
public class GeneratedJooqPostRepository {
    private final DSLContext dsl;

    public GeneratedJooqPostRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public List<SysPost> selectPostAll() {
        return this.dsl.select(
                SYS_POST.POST_ID,
                SYS_POST.POST_CODE,
                SYS_POST.POST_NAME,
                SYS_POST.POST_SORT,
                SYS_POST.STATUS,
                SYS_POST.CREATE_BY,
                SYS_POST.CREATE_TIME,
                SYS_POST.UPDATE_BY,
                SYS_POST.UPDATE_TIME,
                SYS_POST.REMARK
            )
            .from(SYS_POST)
            .fetch(this::mapPost);
    }

    public List<Long> selectPostListByUserId(Long userId) {
        return this.dsl.select(SYS_POST.POST_ID)
            .from(SYS_POST)
            .leftJoin(SYS_USER_POST).on(SYS_USER_POST.POST_ID.eq(SYS_POST.POST_ID))
            .leftJoin(SYS_USER).on(SYS_USER.USER_ID.eq(SYS_USER_POST.USER_ID))
            .where(SYS_USER.USER_ID.eq(userId))
            .fetch(SYS_POST.POST_ID);
    }

    public List<SysPost> selectPostsByUserName(String userName) {
        return this.dsl.select(
                SYS_POST.POST_ID,
                SYS_POST.POST_NAME,
                SYS_POST.POST_CODE
            )
            .from(SYS_POST)
            .leftJoin(SYS_USER_POST).on(SYS_USER_POST.POST_ID.eq(SYS_POST.POST_ID))
            .leftJoin(SYS_USER).on(SYS_USER.USER_ID.eq(SYS_USER_POST.USER_ID))
            .where(SYS_USER.USER_NAME.eq(userName))
            .fetch(this::mapPost);
    }

    private SysPost mapPost(Record record) {
        SysPost post = new SysPost();
        post.setPostId(record.get(SYS_POST.POST_ID));
        post.setPostCode(record.get(SYS_POST.POST_CODE));
        post.setPostName(record.get(SYS_POST.POST_NAME));
        post.setPostSort(record.get(SYS_POST.POST_SORT) == null ? null : String.valueOf(record.get(SYS_POST.POST_SORT)));
        post.setStatus(record.get(SYS_POST.STATUS));
        post.setCreateBy(record.get(SYS_POST.CREATE_BY));
        post.setCreateTime(toDate(record.get(SYS_POST.CREATE_TIME)));
        post.setUpdateBy(record.get(SYS_POST.UPDATE_BY));
        post.setUpdateTime(toDate(record.get(SYS_POST.UPDATE_TIME)));
        post.setRemark(record.get(SYS_POST.REMARK));
        return post;
    }

    private Date toDate(java.time.LocalDateTime value) {
        return value == null ? null : java.sql.Timestamp.valueOf(value);
    }
}
