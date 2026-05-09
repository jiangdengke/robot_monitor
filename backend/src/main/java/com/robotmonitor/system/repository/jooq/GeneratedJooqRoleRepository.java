package com.robotmonitor.system.repository.jooq;

import static com.robotmonitor.jooq.generated.Tables.SYS_ROLE;
import static com.robotmonitor.jooq.generated.Tables.SYS_USER;
import static com.robotmonitor.jooq.generated.Tables.SYS_USER_ROLE;

import com.robotmonitor.common.core.domain.entity.SysRole;
import java.util.Date;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

@Repository
public class GeneratedJooqRoleRepository {
    private final DSLContext dsl;

    public GeneratedJooqRoleRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public List<SysRole> selectRolesByUserName(String userName) {
        return this.dsl.selectDistinct(
                SYS_ROLE.ROLE_ID,
                SYS_ROLE.ROLE_NAME,
                SYS_ROLE.ROLE_KEY,
                SYS_ROLE.ROLE_SORT,
                SYS_ROLE.DATA_SCOPE,
                SYS_ROLE.MENU_CHECK_STRICTLY,
                SYS_ROLE.DEPT_CHECK_STRICTLY,
                SYS_ROLE.STATUS,
                SYS_ROLE.DEL_FLAG,
                SYS_ROLE.CREATE_BY,
                SYS_ROLE.CREATE_TIME,
                SYS_ROLE.UPDATE_BY,
                SYS_ROLE.UPDATE_TIME,
                SYS_ROLE.REMARK
            )
            .from(SYS_ROLE)
            .leftJoin(SYS_USER_ROLE).on(SYS_USER_ROLE.ROLE_ID.eq(SYS_ROLE.ROLE_ID))
            .leftJoin(SYS_USER).on(SYS_USER_ROLE.USER_ID.eq(SYS_USER.USER_ID))
            .where(SYS_USER.USER_NAME.eq(userName))
            .and(SYS_ROLE.DEL_FLAG.eq("0"))
            .fetch(this::mapRole);
    }

    public List<SysRole> selectRolePermissionByUserId(Long userId) {
        return this.dsl.selectDistinct(
                SYS_ROLE.ROLE_ID,
                SYS_ROLE.ROLE_NAME,
                SYS_ROLE.ROLE_KEY,
                SYS_ROLE.ROLE_SORT,
                SYS_ROLE.DATA_SCOPE,
                SYS_ROLE.MENU_CHECK_STRICTLY,
                SYS_ROLE.DEPT_CHECK_STRICTLY,
                SYS_ROLE.STATUS,
                SYS_ROLE.DEL_FLAG,
                SYS_ROLE.CREATE_BY,
                SYS_ROLE.CREATE_TIME,
                SYS_ROLE.UPDATE_BY,
                SYS_ROLE.UPDATE_TIME,
                SYS_ROLE.REMARK
            )
            .from(SYS_ROLE)
            .leftJoin(SYS_USER_ROLE).on(SYS_USER_ROLE.ROLE_ID.eq(SYS_ROLE.ROLE_ID))
            .where(SYS_USER_ROLE.USER_ID.eq(userId))
            .fetch(this::mapRole);
    }

    public List<Long> selectRoleListByUserId(Long userId) {
        return this.dsl.select(SYS_ROLE.ROLE_ID)
            .from(SYS_ROLE)
            .leftJoin(SYS_USER_ROLE).on(SYS_USER_ROLE.ROLE_ID.eq(SYS_ROLE.ROLE_ID))
            .leftJoin(SYS_USER).on(SYS_USER.USER_ID.eq(SYS_USER_ROLE.USER_ID))
            .where(SYS_USER.USER_ID.eq(userId))
            .fetch(SYS_ROLE.ROLE_ID);
    }

    private SysRole mapRole(Record record) {
        SysRole role = new SysRole();
        role.setRoleId(record.get(SYS_ROLE.ROLE_ID));
        role.setRoleName(record.get(SYS_ROLE.ROLE_NAME));
        role.setRoleKey(record.get(SYS_ROLE.ROLE_KEY));
        Integer roleSort = record.get(SYS_ROLE.ROLE_SORT);
        role.setRoleSort(roleSort == null ? null : String.valueOf(roleSort));
        role.setDataScope(record.get(SYS_ROLE.DATA_SCOPE));
        Byte menuCheckStrictly = record.get(SYS_ROLE.MENU_CHECK_STRICTLY);
        Byte deptCheckStrictly = record.get(SYS_ROLE.DEPT_CHECK_STRICTLY);
        role.setMenuCheckStrictly(menuCheckStrictly != null && menuCheckStrictly == 1);
        role.setDeptCheckStrictly(deptCheckStrictly != null && deptCheckStrictly == 1);
        role.setStatus(record.get(SYS_ROLE.STATUS));
        role.setDelFlag(record.get(SYS_ROLE.DEL_FLAG));
        role.setCreateBy(record.get(SYS_ROLE.CREATE_BY));
        role.setCreateTime(toDate(record.get(SYS_ROLE.CREATE_TIME)));
        role.setUpdateBy(record.get(SYS_ROLE.UPDATE_BY));
        role.setUpdateTime(toDate(record.get(SYS_ROLE.UPDATE_TIME)));
        role.setRemark(record.get(SYS_ROLE.REMARK));
        return role;
    }

    private Date toDate(java.time.LocalDateTime value) {
        return value == null ? null : java.sql.Timestamp.valueOf(value);
    }
}
