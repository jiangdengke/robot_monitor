package com.robotmonitor.system.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.SYS_DEPT;
import static com.robotmonitor.jooq.generated.Tables.SYS_ROLE_DEPT;
import static com.robotmonitor.jooq.generated.Tables.SYS_USER;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.contains;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.equalsIfPresent;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.hasText;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.toDate;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.toLocalDateTime;

import com.robotmonitor.common.core.domain.entity.SysDept;
import com.robotmonitor.system.mapper.SysDeptMapper;
import java.util.Arrays;
import java.util.List;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqSysDeptMapper implements SysDeptMapper {
    private final DSLContext dsl;

    public JooqSysDeptMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public List<SysDept> selectDeptList(SysDept dept) {
        return this.dsl.selectFrom(SYS_DEPT)
            .where(SYS_DEPT.DEL_FLAG.eq("0"))
            .and(equalsIfPresent(SYS_DEPT.DEPT_ID, dept == null || isZero(dept.getDeptId()) ? null : dept.getDeptId()))
            .and(equalsIfPresent(SYS_DEPT.PARENT_ID, dept == null || isZero(dept.getParentId()) ? null : dept.getParentId()))
            .and(contains(SYS_DEPT.DEPT_NAME, dept == null ? null : dept.getDeptName()))
            .and(equalsIfPresent(SYS_DEPT.STATUS, dept == null ? null : dept.getStatus()))
            .orderBy(SYS_DEPT.PARENT_ID.asc(), SYS_DEPT.ORDER_NUM.asc())
            .fetch(this::map);
    }

    @Override
    public List<Long> selectDeptListByRoleId(Long roleId, boolean deptCheckStrictly) {
        Condition condition = SYS_ROLE_DEPT.ROLE_ID.eq(roleId);
        if (deptCheckStrictly) {
            condition = condition.and(SYS_DEPT.DEPT_ID.notIn(
                DSL.select(SYS_DEPT.PARENT_ID)
                    .from(SYS_DEPT)
                    .join(SYS_ROLE_DEPT).on(SYS_ROLE_DEPT.DEPT_ID.eq(SYS_DEPT.DEPT_ID))
                    .where(SYS_ROLE_DEPT.ROLE_ID.eq(roleId))
            ));
        }
        return this.dsl.select(SYS_DEPT.DEPT_ID)
            .from(SYS_DEPT)
            .leftJoin(SYS_ROLE_DEPT).on(SYS_ROLE_DEPT.DEPT_ID.eq(SYS_DEPT.DEPT_ID))
            .where(condition)
            .orderBy(SYS_DEPT.PARENT_ID.asc(), SYS_DEPT.ORDER_NUM.asc())
            .fetch(SYS_DEPT.DEPT_ID);
    }

    @Override
    public SysDept selectDeptById(Long deptId) {
        return this.dsl.selectFrom(SYS_DEPT)
            .where(SYS_DEPT.DEPT_ID.eq(deptId))
            .fetchOne(this::map);
    }

    @Override
    public List<SysDept> selectChildrenDeptById(Long deptId) {
        return this.dsl.selectFrom(SYS_DEPT)
            .where(SYS_DEPT.ANCESTORS.like("%" + deptId + "%"))
            .fetch(this::map);
    }

    @Override
    public int selectNormalChildrenDeptById(Long deptId) {
        Integer count = this.dsl.selectCount()
            .from(SYS_DEPT)
            .where(SYS_DEPT.STATUS.eq("0"))
            .and(SYS_DEPT.DEL_FLAG.eq("0"))
            .and(SYS_DEPT.ANCESTORS.like("%" + deptId + "%"))
            .fetchOne(0, Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public int hasChildByDeptId(Long deptId) {
        Integer count = this.dsl.selectCount()
            .from(SYS_DEPT)
            .where(SYS_DEPT.DEL_FLAG.eq("0"))
            .and(SYS_DEPT.PARENT_ID.eq(deptId))
            .fetchOne(0, Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public int checkDeptExistUser(Long deptId) {
        Integer count = this.dsl.selectCount()
            .from(SYS_USER)
            .where(SYS_USER.DEPT_ID.eq(deptId))
            .and(SYS_USER.DEL_FLAG.eq("0"))
            .fetchOne(0, Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public SysDept checkDeptNameUnique(String deptName, Long parentId) {
        return this.dsl.selectFrom(SYS_DEPT)
            .where(SYS_DEPT.DEPT_NAME.eq(deptName))
            .and(SYS_DEPT.PARENT_ID.eq(parentId))
            .limit(1)
            .fetchOne(this::map);
    }

    @Override
    public int insertSysDept(SysDept dept) {
        Long deptId = this.dsl.insertInto(SYS_DEPT)
            .set(SYS_DEPT.PARENT_ID, dept.getParentId())
            .set(SYS_DEPT.ANCESTORS, dept.getAncestors())
            .set(SYS_DEPT.DEPT_NAME, dept.getDeptName())
            .set(SYS_DEPT.ORDER_NUM, dept.getOrderNum())
            .set(SYS_DEPT.LEADER, dept.getLeader())
            .set(SYS_DEPT.PHONE, dept.getPhone())
            .set(SYS_DEPT.EMAIL, dept.getEmail())
            .set(SYS_DEPT.STATUS, dept.getStatus())
            .set(SYS_DEPT.DEL_FLAG, dept.getDelFlag())
            .set(SYS_DEPT.CREATE_BY, dept.getCreateBy())
            .set(SYS_DEPT.CREATE_TIME, toLocalDateTime(dept.getCreateTime()))
            .set(SYS_DEPT.ROOM_CODE, dept.getRoomCode())
            .returningResult(SYS_DEPT.DEPT_ID)
            .fetchOne(SYS_DEPT.DEPT_ID);
        dept.setDeptId(deptId);
        return deptId == null ? 0 : 1;
    }

    @Override
    public int updateDept(SysDept dept) {
        return this.dsl.update(SYS_DEPT)
            .set(SYS_DEPT.PARENT_ID, dept.getParentId())
            .set(SYS_DEPT.ANCESTORS, dept.getAncestors())
            .set(SYS_DEPT.DEPT_NAME, dept.getDeptName())
            .set(SYS_DEPT.ORDER_NUM, dept.getOrderNum())
            .set(SYS_DEPT.LEADER, dept.getLeader())
            .set(SYS_DEPT.PHONE, dept.getPhone())
            .set(SYS_DEPT.EMAIL, dept.getEmail())
            .set(SYS_DEPT.STATUS, dept.getStatus())
            .set(SYS_DEPT.UPDATE_BY, dept.getUpdateBy())
            .set(SYS_DEPT.UPDATE_TIME, toLocalDateTime(dept.getUpdateTime()))
            .set(SYS_DEPT.ROOM_CODE, dept.getRoomCode())
            .where(SYS_DEPT.DEPT_ID.eq(dept.getDeptId()))
            .execute();
    }

    @Override
    public void updateDeptStatusNormal(Long[] deptIds) {
        this.dsl.update(SYS_DEPT)
            .set(SYS_DEPT.STATUS, "0")
            .where(SYS_DEPT.DEPT_ID.in(Arrays.asList(deptIds)))
            .execute();
    }

    @Override
    public int updateDeptChildren(List<SysDept> depts) {
        int rows = 0;
        for (SysDept dept : depts) {
            rows += this.dsl.update(SYS_DEPT)
                .set(SYS_DEPT.ANCESTORS, dept.getAncestors())
                .where(SYS_DEPT.DEPT_ID.eq(dept.getDeptId()))
                .execute();
        }
        return rows;
    }

    @Override
    public int deleteDeptById(Long deptId) {
        return this.dsl.update(SYS_DEPT)
            .set(SYS_DEPT.DEL_FLAG, "2")
            .where(SYS_DEPT.DEPT_ID.eq(deptId))
            .execute();
    }

    @Override
    public List<SysDept> allRoomCode(String deptId) {
        return this.dsl.selectFrom(SYS_DEPT)
            .where(SYS_DEPT.DEL_FLAG.eq("0"))
            .and(hasText(deptId) ? SYS_DEPT.ANCESTORS.like("%" + deptId + "%") : DSL.noCondition())
            .and(SYS_DEPT.ROOM_CODE.isNotNull())
            .and(SYS_DEPT.STATUS.eq("0"))
            .orderBy(SYS_DEPT.PARENT_ID.asc(), SYS_DEPT.ORDER_NUM.asc())
            .fetch(this::map);
    }

    @Override
    public List<SysDept> roomList() {
        return this.dsl.selectFrom(SYS_DEPT)
            .where(SYS_DEPT.DEL_FLAG.eq("0"))
            .and(SYS_DEPT.ROOM_CODE.isNotNull())
            .and(SYS_DEPT.STATUS.eq("0"))
            .orderBy(SYS_DEPT.PARENT_ID.asc(), SYS_DEPT.ORDER_NUM.asc())
            .fetch(this::map);
    }

    private boolean isZero(Long value) {
        return value != null && value == 0L;
    }

    private SysDept map(Record record) {
        SysDept dept = new SysDept();
        dept.setDeptId(record.get(SYS_DEPT.DEPT_ID));
        dept.setParentId(record.get(SYS_DEPT.PARENT_ID));
        dept.setAncestors(record.get(SYS_DEPT.ANCESTORS));
        dept.setDeptName(record.get(SYS_DEPT.DEPT_NAME));
        dept.setOrderNum(record.get(SYS_DEPT.ORDER_NUM));
        dept.setLeader(record.get(SYS_DEPT.LEADER));
        dept.setPhone(record.get(SYS_DEPT.PHONE));
        dept.setEmail(record.get(SYS_DEPT.EMAIL));
        dept.setStatus(record.get(SYS_DEPT.STATUS));
        dept.setDelFlag(record.get(SYS_DEPT.DEL_FLAG));
        dept.setRoomCode(record.get(SYS_DEPT.ROOM_CODE));
        return dept;
    }
}
