package com.robotmonitor.system.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.SYS_POST;
import static com.robotmonitor.jooq.generated.Tables.SYS_USER;
import static com.robotmonitor.jooq.generated.Tables.SYS_USER_POST;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.contains;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.equalsIfPresent;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.toDate;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.toLocalDateTime;

import com.robotmonitor.system.domain.SysPost;
import com.robotmonitor.system.mapper.SysPostMapper;
import java.util.Arrays;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqSysPostMapper implements SysPostMapper {
    private final DSLContext dsl;

    public JooqSysPostMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public List<SysPost> selectPostList(SysPost post) {
        return this.dsl.selectFrom(SYS_POST)
            .where(contains(SYS_POST.POST_CODE, post == null ? null : post.getPostCode()))
            .and(equalsIfPresent(SYS_POST.STATUS, post == null ? null : post.getStatus()))
            .and(contains(SYS_POST.POST_NAME, post == null ? null : post.getPostName()))
            .orderBy(SYS_POST.POST_SORT.asc(), SYS_POST.POST_ID.asc())
            .fetch(this::map);
    }

    @Override
    public List<SysPost> selectPostAll() {
        return this.dsl.selectFrom(SYS_POST)
            .orderBy(SYS_POST.POST_SORT.asc(), SYS_POST.POST_ID.asc())
            .fetch(this::map);
    }

    @Override
    public SysPost selectPostById(Long postId) {
        return this.dsl.selectFrom(SYS_POST)
            .where(SYS_POST.POST_ID.eq(postId))
            .fetchOne(this::map);
    }

    @Override
    public List<Long> selectPostListByUserId(Long userId) {
        return this.dsl.select(SYS_POST.POST_ID)
            .from(SYS_POST)
            .leftJoin(SYS_USER_POST).on(SYS_USER_POST.POST_ID.eq(SYS_POST.POST_ID))
            .leftJoin(SYS_USER).on(SYS_USER.USER_ID.eq(SYS_USER_POST.USER_ID))
            .where(SYS_USER.USER_ID.eq(userId))
            .fetch(SYS_POST.POST_ID);
    }

    @Override
    public List<SysPost> selectPostsByUserName(String userName) {
        return this.dsl.select(SYS_POST.POST_ID, SYS_POST.POST_NAME, SYS_POST.POST_CODE)
            .from(SYS_POST)
            .leftJoin(SYS_USER_POST).on(SYS_USER_POST.POST_ID.eq(SYS_POST.POST_ID))
            .leftJoin(SYS_USER).on(SYS_USER.USER_ID.eq(SYS_USER_POST.USER_ID))
            .where(SYS_USER.USER_NAME.eq(userName))
            .fetch(this::map);
    }

    @Override
    public int deletePostById(Long postId) {
        return this.dsl.deleteFrom(SYS_POST)
            .where(SYS_POST.POST_ID.eq(postId))
            .execute();
    }

    @Override
    public int deletePostByIds(Long[] postIds) {
        return this.dsl.deleteFrom(SYS_POST)
            .where(SYS_POST.POST_ID.in(Arrays.asList(postIds)))
            .execute();
    }

    @Override
    public int updatePost(SysPost post) {
        return this.dsl.update(SYS_POST)
            .set(SYS_POST.POST_CODE, post.getPostCode())
            .set(SYS_POST.POST_NAME, post.getPostName())
            .set(SYS_POST.POST_SORT, parseSort(post.getPostSort()))
            .set(SYS_POST.STATUS, post.getStatus())
            .set(SYS_POST.UPDATE_BY, post.getUpdateBy())
            .set(SYS_POST.UPDATE_TIME, toLocalDateTime(post.getUpdateTime()))
            .set(SYS_POST.REMARK, post.getRemark())
            .where(SYS_POST.POST_ID.eq(post.getPostId()))
            .execute();
    }

    @Override
    public int insertPost(SysPost post) {
        return this.dsl.insertInto(SYS_POST)
            .set(SYS_POST.POST_CODE, post.getPostCode())
            .set(SYS_POST.POST_NAME, post.getPostName())
            .set(SYS_POST.POST_SORT, parseSort(post.getPostSort()))
            .set(SYS_POST.STATUS, post.getStatus())
            .set(SYS_POST.CREATE_BY, post.getCreateBy())
            .set(SYS_POST.CREATE_TIME, toLocalDateTime(post.getCreateTime()))
            .set(SYS_POST.REMARK, post.getRemark())
            .execute();
    }

    @Override
    public SysPost checkPostNameUnique(String postName) {
        return this.dsl.selectFrom(SYS_POST)
            .where(SYS_POST.POST_NAME.eq(postName))
            .limit(1)
            .fetchOne(this::map);
    }

    @Override
    public SysPost checkPostCodeUnique(String postCode) {
        return this.dsl.selectFrom(SYS_POST)
            .where(SYS_POST.POST_CODE.eq(postCode))
            .limit(1)
            .fetchOne(this::map);
    }

    private SysPost map(Record record) {
        SysPost post = new SysPost();
        post.setPostId(record.get(SYS_POST.POST_ID));
        post.setPostCode(record.get(SYS_POST.POST_CODE));
        post.setPostName(record.get(SYS_POST.POST_NAME));
        Integer sort = record.get(SYS_POST.POST_SORT);
        post.setPostSort(sort == null ? null : String.valueOf(sort));
        post.setStatus(record.get(SYS_POST.STATUS));
        post.setCreateBy(record.get(SYS_POST.CREATE_BY));
        post.setCreateTime(toDate(record.get(SYS_POST.CREATE_TIME)));
        post.setUpdateBy(record.get(SYS_POST.UPDATE_BY));
        post.setUpdateTime(toDate(record.get(SYS_POST.UPDATE_TIME)));
        post.setRemark(record.get(SYS_POST.REMARK));
        return post;
    }

    private Integer parseSort(String postSort) {
        return postSort == null || postSort.isBlank() ? null : Integer.valueOf(postSort);
    }
}
