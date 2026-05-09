package com.robotmonitor.system.repository.jooq;

import static com.robotmonitor.jooq.generated.Tables.SYS_DEPT;
import static com.robotmonitor.jooq.generated.Tables.SYS_ROLE_DEPT;
import static com.robotmonitor.jooq.generated.Tables.SYS_USER;

import com.robotmonitor.common.core.domain.entity.SysDept;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

@Repository
public class JooqDeptRepository {
    private final DSLContext dsl;

    public JooqDeptRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public List<SysDept> selectDeptList(SysDept dept) {
        return this.dsl.selectFrom(SYS_DEPT)
            .where(SYS_DEPT.DEL_FLAG.eq("0"))
            .and(equalsIfPresent(SYS_DEPT.DEPT_ID, dept == null || isZero(dept.getDeptId()) ? null : dept.getDeptId()))
            .and(equalsIfPresent(SYS_DEPT.PARENT_ID, dept == null || isZero(dept.getParentId()) ? null : dept.getParentId()))
            .and(contains(SYS_DEPT.DEPT_NAME, dept == null ? null : dept.getDeptName()))
            .and(equalsIfPresent(SYS_DEPT.STATUS, dept == null ? null : dept.getStatus()))
            .orderBy(SYS_DEPT.PARENT_ID.asc(), SYS_DEPT.ORDER_NUM.asc())
            .fetch(this::mapDept);
    }

    public SysDept selectDeptById(Long deptId) {
        return this.dsl.selectFrom(SYS_DEPT)
            .where(SYS_DEPT.DEPT_ID.eq(deptId))
            .fetchOne(this::mapDept);
    }

    public Integer hasChildByDeptId(Long deptId) {
        Integer count = this.dsl.selectCount()
            .from(SYS_DEPT)
            .where(SYS_DEPT.DEL_FLAG.eq("0"))
            .and(SYS_DEPT.PARENT_ID.eq(deptId))
            .fetchOne(0, Integer.class);
        return count == null ? 0 : count;
    }

    public Integer checkDeptExistUser(Long deptId) {
        Integer count = this.dsl.selectCount()
            .from(SYS_USER)
            .where(SYS_USER.DEPT_ID.eq(deptId))
            .and(SYS_USER.DEL_FLAG.eq("0"))
            .fetchOne(0, Integer.class);
        return count == null ? 0 : count;
    }

    public SysDept checkDeptNameUnique(String deptName, Long parentId) {
        return this.dsl.selectFrom(SYS_DEPT)
            .where(SYS_DEPT.DEPT_NAME.eq(deptName))
            .and(SYS_DEPT.PARENT_ID.eq(parentId))
            .limit(1)
            .fetchOne(this::mapDept);
    }

    public Integer selectNormalChildrenDeptById(Long deptId) {
        Integer count = this.dsl.selectCount()
            .from(SYS_DEPT)
            .where(SYS_DEPT.STATUS.eq("0"))
            .and(SYS_DEPT.DEL_FLAG.eq("0"))
            .and(SYS_DEPT.ANCESTORS.like("%" + deptId + "%"))
            .fetchOne(0, Integer.class);
        return count == null ? 0 : count;
    }

    public List<SysDept> allRoomCode(String deptId) {
        Condition condition = SYS_DEPT.DEL_FLAG.eq("0")
            .and(hasText(deptId) ? SYS_DEPT.ANCESTORS.like("%" + deptId + "%") : DSL.noCondition())
            .and(SYS_DEPT.ROOM_CODE.isNotNull())
            .and(SYS_DEPT.STATUS.eq("0"));

        return this.dsl.selectFrom(SYS_DEPT)
            .where(condition)
            .orderBy(SYS_DEPT.PARENT_ID.asc(), SYS_DEPT.ORDER_NUM.asc())
            .fetch(this::mapDept);
    }

    public List<SysDept> roomList() {
        return this.dsl.selectFrom(SYS_DEPT)
            .where(SYS_DEPT.DEL_FLAG.eq("0"))
            .and(SYS_DEPT.ROOM_CODE.isNotNull())
            .and(SYS_DEPT.STATUS.eq("0"))
            .orderBy(SYS_DEPT.PARENT_ID.asc(), SYS_DEPT.ORDER_NUM.asc())
            .fetch(this::mapDept);
    }

    public List<Long> selectDeptListByRoleId(Long roleId) {
        return this.dsl.select(SYS_DEPT.DEPT_ID)
            .from(SYS_DEPT)
            .leftJoin(SYS_ROLE_DEPT).on(SYS_ROLE_DEPT.DEPT_ID.eq(SYS_DEPT.DEPT_ID))
            .where(SYS_ROLE_DEPT.ROLE_ID.eq(roleId))
            .orderBy(SYS_DEPT.PARENT_ID.asc(), SYS_DEPT.ORDER_NUM.asc())
            .fetch(SYS_DEPT.DEPT_ID);
    }

    private boolean isZero(Long value) {
        return value != null && value == 0L;
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    private Condition contains(Field<String> field, String value) {
        return hasText(value) ? field.like("%" + value + "%") : DSL.noCondition();
    }

    private Condition equalsIfPresent(Field<String> field, String value) {
        return hasText(value) ? field.eq(value) : DSL.noCondition();
    }

    private <T> Condition equalsIfPresent(Field<T> field, T value) {
        return value == null ? DSL.noCondition() : field.eq(value);
    }

    private Date toDate(LocalDateTime value) {
        return value == null ? null : java.sql.Timestamp.valueOf(value);
    }

    private SysDept mapDept(Record record) {
        if (record == null) {
            return null;
        }

        SysDept dept = new SysDept();
        dept.setDeptId(get(record, SYS_DEPT.DEPT_ID));
        dept.setParentId(get(record, SYS_DEPT.PARENT_ID));
        dept.setAncestors(get(record, SYS_DEPT.ANCESTORS));
        dept.setDeptName(get(record, SYS_DEPT.DEPT_NAME));
        dept.setOrderNum(get(record, SYS_DEPT.ORDER_NUM));
        dept.setLeader(get(record, SYS_DEPT.LEADER));
        dept.setPhone(get(record, SYS_DEPT.PHONE));
        dept.setEmail(get(record, SYS_DEPT.EMAIL));
        dept.setStatus(get(record, SYS_DEPT.STATUS));
        dept.setDelFlag(get(record, SYS_DEPT.DEL_FLAG));
        dept.setRoomCode(get(record, SYS_DEPT.ROOM_CODE));
        dept.setCreateBy(get(record, SYS_DEPT.CREATE_BY));
        dept.setCreateTime(toDate(get(record, SYS_DEPT.CREATE_TIME)));
        dept.setUpdateBy(get(record, SYS_DEPT.UPDATE_BY));
        dept.setUpdateTime(toDate(get(record, SYS_DEPT.UPDATE_TIME)));
        return dept;
    }

    private <T> T get(Record record, TableField<?, T> field) {
        return record.field(field) == null ? null : record.get(field);
    }
}
