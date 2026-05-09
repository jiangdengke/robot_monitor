package com.robotmonitor.system.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.SYS_USER_ROLE;

import com.robotmonitor.system.domain.SysUserRole;
import com.robotmonitor.system.mapper.SysUserRoleMapper;
import java.util.Arrays;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqSysUserRoleMapper implements SysUserRoleMapper {
    private final DSLContext dsl;

    public JooqSysUserRoleMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public int deleteUserRoleByUserId(Long userId) {
        return this.dsl.deleteFrom(SYS_USER_ROLE)
            .where(SYS_USER_ROLE.USER_ID.eq(userId))
            .execute();
    }

    @Override
    public int deleteUserRole(Long[] userIds) {
        return this.dsl.deleteFrom(SYS_USER_ROLE)
            .where(SYS_USER_ROLE.USER_ID.in(Arrays.asList(userIds)))
            .execute();
    }

    @Override
    public int countUserRoleByRoleId(Long roleId) {
        Integer count = this.dsl.selectCount()
            .from(SYS_USER_ROLE)
            .where(SYS_USER_ROLE.ROLE_ID.eq(roleId))
            .fetchOne(0, Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public int batchUserRole(List<SysUserRole> userRoles) {
        if (userRoles == null || userRoles.isEmpty()) {
            return 0;
        }
        return this.dsl.batch(userRoles.stream()
            .map(item -> this.dsl.insertInto(SYS_USER_ROLE)
                .set(SYS_USER_ROLE.USER_ID, item.getUserId())
                .set(SYS_USER_ROLE.ROLE_ID, item.getRoleId()))
            .toList())
            .execute().length;
    }

    @Override
    public int deleteUserRoleInfo(SysUserRole userRole) {
        return this.dsl.deleteFrom(SYS_USER_ROLE)
            .where(SYS_USER_ROLE.USER_ID.eq(userRole.getUserId()))
            .and(SYS_USER_ROLE.ROLE_ID.eq(userRole.getRoleId()))
            .execute();
    }

    @Override
    public int deleteUserRoleInfos(Long roleId, Long[] userIds) {
        return this.dsl.deleteFrom(SYS_USER_ROLE)
            .where(SYS_USER_ROLE.ROLE_ID.eq(roleId))
            .and(SYS_USER_ROLE.USER_ID.in(Arrays.asList(userIds)))
            .execute();
    }
}
