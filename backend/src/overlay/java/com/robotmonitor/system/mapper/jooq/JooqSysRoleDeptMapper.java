package com.robotmonitor.system.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.SYS_ROLE_DEPT;

import com.robotmonitor.system.domain.SysRoleDept;
import com.robotmonitor.system.mapper.SysRoleDeptMapper;
import java.util.Arrays;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqSysRoleDeptMapper implements SysRoleDeptMapper {
    private final DSLContext dsl;

    public JooqSysRoleDeptMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public int deleteRoleDeptByRoleId(Long roleId) {
        return this.dsl.deleteFrom(SYS_ROLE_DEPT)
            .where(SYS_ROLE_DEPT.ROLE_ID.eq(roleId))
            .execute();
    }

    @Override
    public int deleteRoleDept(Long[] roleIds) {
        return this.dsl.deleteFrom(SYS_ROLE_DEPT)
            .where(SYS_ROLE_DEPT.ROLE_ID.in(Arrays.asList(roleIds)))
            .execute();
    }

    @Override
    public int selectCountRoleDeptByDeptId(Long deptId) {
        Integer count = this.dsl.selectCount()
            .from(SYS_ROLE_DEPT)
            .where(SYS_ROLE_DEPT.DEPT_ID.eq(deptId))
            .fetchOne(0, Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public int batchRoleDept(List<SysRoleDept> roleDepts) {
        if (roleDepts == null || roleDepts.isEmpty()) {
            return 0;
        }
        return this.dsl.batch(roleDepts.stream()
            .map(item -> this.dsl.insertInto(SYS_ROLE_DEPT)
                .set(SYS_ROLE_DEPT.ROLE_ID, item.getRoleId())
                .set(SYS_ROLE_DEPT.DEPT_ID, item.getDeptId()))
            .toList())
            .execute().length;
    }
}
