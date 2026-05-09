package com.robotmonitor.system.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.SYS_DEPT;
import static com.robotmonitor.jooq.generated.Tables.SYS_ROLE;
import static com.robotmonitor.jooq.generated.Tables.SYS_USER;
import static com.robotmonitor.jooq.generated.Tables.SYS_USER_ROLE;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.contains;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.equalsIfPresent;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.toDate;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.toLocalDateTime;

import com.robotmonitor.common.core.domain.entity.SysRole;
import com.robotmonitor.system.mapper.SysRoleMapper;
import java.util.Arrays;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqSysRoleMapper implements SysRoleMapper {
    private final DSLContext dsl;

    public JooqSysRoleMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public List<SysRole> selectRoleList(SysRole role) {
        return this.dsl.selectDistinct(SYS_ROLE.fields())
            .from(SYS_ROLE)
            .leftJoin(SYS_USER_ROLE).on(SYS_USER_ROLE.ROLE_ID.eq(SYS_ROLE.ROLE_ID))
            .leftJoin(SYS_USER).on(SYS_USER.USER_ID.eq(SYS_USER_ROLE.USER_ID))
            .leftJoin(SYS_DEPT).on(SYS_DEPT.DEPT_ID.eq(SYS_USER.DEPT_ID))
            .where(SYS_ROLE.DEL_FLAG.eq("0"))
            .and(equalsIfPresent(SYS_ROLE.ROLE_ID, role == null ? null : role.getRoleId()))
            .and(contains(SYS_ROLE.ROLE_NAME, role == null ? null : role.getRoleName()))
            .and(equalsIfPresent(SYS_ROLE.STATUS, role == null ? null : role.getStatus()))
            .and(contains(SYS_ROLE.ROLE_KEY, role == null ? null : role.getRoleKey()))
            .orderBy(SYS_ROLE.ROLE_SORT.asc())
            .fetch(this::map);
    }

    @Override
    public List<SysRole> selectRolePermissionByUserId(Long userId) {
        return this.dsl.selectDistinct(SYS_ROLE.fields())
            .from(SYS_ROLE)
            .leftJoin(SYS_USER_ROLE).on(SYS_USER_ROLE.ROLE_ID.eq(SYS_ROLE.ROLE_ID))
            .where(SYS_ROLE.DEL_FLAG.eq("0"))
            .and(SYS_USER_ROLE.USER_ID.eq(userId))
            .fetch(this::map);
    }

    @Override
    public List<SysRole> selectRoleAll() {
        return this.dsl.selectDistinct(SYS_ROLE.fields())
            .from(SYS_ROLE)
            .where(SYS_ROLE.DEL_FLAG.eq("0"))
            .orderBy(SYS_ROLE.ROLE_SORT.asc())
            .fetch(this::map);
    }

    @Override
    public List<Long> selectRoleListByUserId(Long userId) {
        return this.dsl.select(SYS_ROLE.ROLE_ID)
            .from(SYS_ROLE)
            .leftJoin(SYS_USER_ROLE).on(SYS_USER_ROLE.ROLE_ID.eq(SYS_ROLE.ROLE_ID))
            .leftJoin(SYS_USER).on(SYS_USER.USER_ID.eq(SYS_USER_ROLE.USER_ID))
            .where(SYS_USER.USER_ID.eq(userId))
            .fetch(SYS_ROLE.ROLE_ID);
    }

    @Override
    public SysRole selectRoleById(Long roleId) {
        return this.dsl.selectFrom(SYS_ROLE)
            .where(SYS_ROLE.ROLE_ID.eq(roleId))
            .fetchOne(this::map);
    }

    @Override
    public List<SysRole> selectRolesByUserName(String userName) {
        return this.dsl.selectDistinct(SYS_ROLE.fields())
            .from(SYS_ROLE)
            .leftJoin(SYS_USER_ROLE).on(SYS_USER_ROLE.ROLE_ID.eq(SYS_ROLE.ROLE_ID))
            .leftJoin(SYS_USER).on(SYS_USER.USER_ID.eq(SYS_USER_ROLE.USER_ID))
            .where(SYS_ROLE.DEL_FLAG.eq("0"))
            .and(SYS_USER.USER_NAME.eq(userName))
            .fetch(this::map);
    }

    @Override
    public SysRole checkRoleNameUnique(String roleName) {
        return this.dsl.selectFrom(SYS_ROLE)
            .where(SYS_ROLE.ROLE_NAME.eq(roleName))
            .limit(1)
            .fetchOne(this::map);
    }

    @Override
    public SysRole checkRoleKeyUnique(String roleKey) {
        return this.dsl.selectFrom(SYS_ROLE)
            .where(SYS_ROLE.ROLE_KEY.eq(roleKey))
            .limit(1)
            .fetchOne(this::map);
    }

    @Override
    public int updateRole(SysRole role) {
        return this.dsl.update(SYS_ROLE)
            .set(SYS_ROLE.ROLE_NAME, role.getRoleName())
            .set(SYS_ROLE.ROLE_KEY, role.getRoleKey())
            .set(SYS_ROLE.ROLE_SORT, parseSort(role.getRoleSort()))
            .set(SYS_ROLE.DATA_SCOPE, role.getDataScope())
            .set(SYS_ROLE.MENU_CHECK_STRICTLY, boolByte(role.isMenuCheckStrictly()))
            .set(SYS_ROLE.DEPT_CHECK_STRICTLY, boolByte(role.isDeptCheckStrictly()))
            .set(SYS_ROLE.STATUS, role.getStatus())
            .set(SYS_ROLE.UPDATE_BY, role.getUpdateBy())
            .set(SYS_ROLE.UPDATE_TIME, toLocalDateTime(role.getUpdateTime()))
            .set(SYS_ROLE.REMARK, role.getRemark())
            .where(SYS_ROLE.ROLE_ID.eq(role.getRoleId()))
            .execute();
    }

    @Override
    public int insertRole(SysRole role) {
        Long roleId = this.dsl.insertInto(SYS_ROLE)
            .set(SYS_ROLE.ROLE_NAME, role.getRoleName())
            .set(SYS_ROLE.ROLE_KEY, role.getRoleKey())
            .set(SYS_ROLE.ROLE_SORT, parseSort(role.getRoleSort()))
            .set(SYS_ROLE.DATA_SCOPE, role.getDataScope())
            .set(SYS_ROLE.MENU_CHECK_STRICTLY, boolByte(role.isMenuCheckStrictly()))
            .set(SYS_ROLE.DEPT_CHECK_STRICTLY, boolByte(role.isDeptCheckStrictly()))
            .set(SYS_ROLE.STATUS, role.getStatus())
            .set(SYS_ROLE.CREATE_BY, role.getCreateBy())
            .set(SYS_ROLE.CREATE_TIME, toLocalDateTime(role.getCreateTime()))
            .set(SYS_ROLE.REMARK, role.getRemark())
            .returningResult(SYS_ROLE.ROLE_ID)
            .fetchOne(SYS_ROLE.ROLE_ID);
        role.setRoleId(roleId);
        return roleId == null ? 0 : 1;
    }

    @Override
    public int deleteRoleById(Long roleId) {
        return this.dsl.update(SYS_ROLE)
            .set(SYS_ROLE.DEL_FLAG, "2")
            .where(SYS_ROLE.ROLE_ID.eq(roleId))
            .execute();
    }

    @Override
    public int deleteRoleByIds(Long[] roleIds) {
        return this.dsl.update(SYS_ROLE)
            .set(SYS_ROLE.DEL_FLAG, "2")
            .where(SYS_ROLE.ROLE_ID.in(Arrays.asList(roleIds)))
            .execute();
    }

    private SysRole map(Record record) {
        SysRole role = new SysRole();
        role.setRoleId(record.get(SYS_ROLE.ROLE_ID));
        role.setRoleName(record.get(SYS_ROLE.ROLE_NAME));
        role.setRoleKey(record.get(SYS_ROLE.ROLE_KEY));
        Integer sort = record.get(SYS_ROLE.ROLE_SORT);
        role.setRoleSort(sort == null ? null : String.valueOf(sort));
        role.setDataScope(record.get(SYS_ROLE.DATA_SCOPE));
        Byte menuStrict = record.get(SYS_ROLE.MENU_CHECK_STRICTLY);
        Byte deptStrict = record.get(SYS_ROLE.DEPT_CHECK_STRICTLY);
        role.setMenuCheckStrictly(menuStrict != null && menuStrict == 1);
        role.setDeptCheckStrictly(deptStrict != null && deptStrict == 1);
        role.setStatus(record.get(SYS_ROLE.STATUS));
        role.setDelFlag(record.get(SYS_ROLE.DEL_FLAG));
        role.setCreateBy(record.get(SYS_ROLE.CREATE_BY));
        role.setCreateTime(toDate(record.get(SYS_ROLE.CREATE_TIME)));
        role.setUpdateBy(record.get(SYS_ROLE.UPDATE_BY));
        role.setUpdateTime(toDate(record.get(SYS_ROLE.UPDATE_TIME)));
        role.setRemark(record.get(SYS_ROLE.REMARK));
        return role;
    }

    private Integer parseSort(String value) {
        return value == null || value.isBlank() ? null : Integer.valueOf(value);
    }

    private Byte boolByte(boolean value) {
        return (byte) (value ? 1 : 0);
    }
}
