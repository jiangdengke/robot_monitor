package com.robotmonitor.system.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.SYS_USER_POST;

import com.robotmonitor.system.domain.SysUserPost;
import com.robotmonitor.system.mapper.SysUserPostMapper;
import java.util.Arrays;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqSysUserPostMapper implements SysUserPostMapper {
    private final DSLContext dsl;

    public JooqSysUserPostMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public int deleteUserPostByUserId(Long userId) {
        return this.dsl.deleteFrom(SYS_USER_POST)
            .where(SYS_USER_POST.USER_ID.eq(userId))
            .execute();
    }

    @Override
    public int countUserPostById(Long postId) {
        Integer count = this.dsl.selectCount()
            .from(SYS_USER_POST)
            .where(SYS_USER_POST.POST_ID.eq(postId))
            .fetchOne(0, Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public int deleteUserPost(Long[] userIds) {
        return this.dsl.deleteFrom(SYS_USER_POST)
            .where(SYS_USER_POST.USER_ID.in(Arrays.asList(userIds)))
            .execute();
    }

    @Override
    public int batchUserPost(List<SysUserPost> userPosts) {
        if (userPosts == null || userPosts.isEmpty()) {
            return 0;
        }
        return this.dsl.batch(userPosts.stream()
            .map(item -> this.dsl.insertInto(SYS_USER_POST)
                .set(SYS_USER_POST.USER_ID, item.getUserId())
                .set(SYS_USER_POST.POST_ID, item.getPostId()))
            .toList())
            .execute().length;
    }
}
