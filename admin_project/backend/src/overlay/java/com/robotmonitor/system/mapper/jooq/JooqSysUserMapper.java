package com.robotmonitor.system.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.SYS_DEPT;
import static com.robotmonitor.jooq.generated.Tables.SYS_ROLE;
import static com.robotmonitor.jooq.generated.Tables.SYS_USER;
import static com.robotmonitor.jooq.generated.Tables.SYS_USER_ROLE;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.contains;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.equalsIfPresent;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.toDate;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.toLocalDateTime;

import com.robotmonitor.common.core.domain.entity.SysDept;
import com.robotmonitor.common.core.domain.entity.SysUser;
import com.robotmonitor.system.mapper.SysUserMapper;
import java.util.Arrays;
import java.util.List;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqSysUserMapper implements SysUserMapper {
    private final DSLContext dsl;

    public JooqSysUserMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public List<SysUser> selectUserList(SysUser user) {
        return this.dsl.select(SYS_USER.fields())
            .select(SYS_DEPT.DEPT_NAME, SYS_DEPT.LEADER)
            .from(SYS_USER)
            .leftJoin(SYS_DEPT).on(SYS_USER.DEPT_ID.eq(SYS_DEPT.DEPT_ID))
            .where(SYS_USER.DEL_FLAG.eq("0"))
            .and(equalsIfPresent(SYS_USER.USER_ID, user == null || isZero(user.getUserId()) ? null : user.getUserId()))
            .and(contains(SYS_USER.USER_NAME, user == null ? null : user.getUserName()))
            .and(equalsIfPresent(SYS_USER.STATUS, user == null ? null : user.getStatus()))
            .and(contains(SYS_USER.PHONENUMBER, user == null ? null : user.getPhonenumber()))
            .and(deptFilter(user))
            .and(SYS_USER.USER_TYPE.eq("00"))
            .orderBy(SYS_USER.USER_ID.asc())
            .fetch(this::mapUserWithDept);
    }

    @Override
    public List<SysUser> selectAllocatedList(SysUser user) {
        Long roleId = user == null ? null : user.getRoleId();
        return this.dsl.selectDistinct(SYS_USER.fields())
            .from(SYS_USER)
            .leftJoin(SYS_DEPT).on(SYS_USER.DEPT_ID.eq(SYS_DEPT.DEPT_ID))
            .leftJoin(SYS_USER_ROLE).on(SYS_USER_ROLE.USER_ID.eq(SYS_USER.USER_ID))
            .leftJoin(SYS_ROLE).on(SYS_ROLE.ROLE_ID.eq(SYS_USER_ROLE.ROLE_ID))
            .where(SYS_USER.DEL_FLAG.eq("0"))
            .and(SYS_ROLE.ROLE_ID.eq(roleId))
            .and(contains(SYS_USER.USER_NAME, user == null ? null : user.getUserName()))
            .and(contains(SYS_USER.PHONENUMBER, user == null ? null : user.getPhonenumber()))
            .orderBy(SYS_USER.USER_ID.asc())
            .fetch(this::mapUser);
    }

    @Override
    public List<SysUser> selectUnallocatedList(SysUser user) {
        Long roleId = user == null ? null : user.getRoleId();
        return this.dsl.selectDistinct(SYS_USER.fields())
            .from(SYS_USER)
            .leftJoin(SYS_DEPT).on(SYS_USER.DEPT_ID.eq(SYS_DEPT.DEPT_ID))
            .leftJoin(SYS_USER_ROLE).on(SYS_USER_ROLE.USER_ID.eq(SYS_USER.USER_ID))
            .leftJoin(SYS_ROLE).on(SYS_ROLE.ROLE_ID.eq(SYS_USER_ROLE.ROLE_ID))
            .where(SYS_USER.DEL_FLAG.eq("0"))
            .and(SYS_ROLE.ROLE_ID.ne(roleId).or(SYS_ROLE.ROLE_ID.isNull()))
            .and(SYS_USER.USER_ID.notIn(
                DSL.select(SYS_USER_ROLE.USER_ID)
                    .from(SYS_USER_ROLE)
                    .where(SYS_USER_ROLE.ROLE_ID.eq(roleId))
            ))
            .and(contains(SYS_USER.USER_NAME, user == null ? null : user.getUserName()))
            .and(contains(SYS_USER.PHONENUMBER, user == null ? null : user.getPhonenumber()))
            .orderBy(SYS_USER.USER_ID.asc())
            .fetch(this::mapUser);
    }

    @Override
    public SysUser selectUserByUserName(String userName) {
        return this.userBase()
            .where(SYS_USER.USER_NAME.eq(userName))
            .fetchOne(this::mapUserWithDept);
    }

    @Override
    public SysUser selectUserById(Long userId) {
        return this.userBase()
            .where(SYS_USER.USER_ID.eq(userId))
            .fetchOne(this::mapUserWithDept);
    }

    @Override
    public int insertUser(SysUser user) {
        Long userId = this.dsl.insertInto(SYS_USER)
            .set(SYS_USER.DEPT_ID, zeroToNull(user.getDeptId()))
            .set(SYS_USER.USER_NAME, user.getUserName())
            .set(SYS_USER.NICK_NAME, user.getNickName())
            .set(SYS_USER.EMAIL, user.getEmail())
            .set(SYS_USER.AVATAR, user.getAvatar())
            .set(SYS_USER.PHONENUMBER, user.getPhonenumber())
            .set(SYS_USER.SEX, user.getSex())
            .set(SYS_USER.PASSWORD, user.getPassword())
            .set(SYS_USER.STATUS, user.getStatus())
            .set(SYS_USER.CREATE_BY, user.getCreateBy())
            .set(SYS_USER.CREATE_TIME, toLocalDateTime(user.getCreateTime()))
            .set(SYS_USER.REMARK, user.getRemark())
            .set(SYS_USER.USER_TYPE, user.getUserType())
            .returningResult(SYS_USER.USER_ID)
            .fetchOne(SYS_USER.USER_ID);
        user.setUserId(userId);
        return userId == null ? 0 : 1;
    }

    @Override
    public int updateUser(SysUser user) {
        var update = this.dsl.update(SYS_USER).set(SYS_USER.USER_ID, user.getUserId());
        if (user.getDeptId() != null && !isZero(user.getDeptId())) {
            update = update.set(SYS_USER.DEPT_ID, zeroToNull(user.getDeptId()));
        }
        if (user.getUserName() != null) {
            update = update.set(SYS_USER.USER_NAME, user.getUserName());
        }
        if (user.getNickName() != null) {
            update = update.set(SYS_USER.NICK_NAME, user.getNickName());
        }
        if (user.getEmail() != null) {
            update = update.set(SYS_USER.EMAIL, user.getEmail());
        }
        if (user.getPhonenumber() != null) {
            update = update.set(SYS_USER.PHONENUMBER, user.getPhonenumber());
        }
        if (user.getSex() != null) {
            update = update.set(SYS_USER.SEX, user.getSex());
        }
        if (user.getAvatar() != null) {
            update = update.set(SYS_USER.AVATAR, user.getAvatar());
        }
        if (user.getPassword() != null) {
            update = update.set(SYS_USER.PASSWORD, user.getPassword());
        }
        if (user.getStatus() != null) {
            update = update.set(SYS_USER.STATUS, user.getStatus());
        }
        if (user.getLoginIp() != null) {
            update = update.set(SYS_USER.LOGIN_IP, user.getLoginIp());
        }
        if (user.getLoginDate() != null) {
            update = update.set(SYS_USER.LOGIN_DATE, toLocalDateTime(user.getLoginDate()));
        }
        if (user.getUpdateBy() != null) {
            update = update.set(SYS_USER.UPDATE_BY, user.getUpdateBy());
        }
        if (user.getUpdateTime() != null) {
            update = update.set(SYS_USER.UPDATE_TIME, toLocalDateTime(user.getUpdateTime()));
        }
        if (user.getRemark() != null) {
            update = update.set(SYS_USER.REMARK, user.getRemark());
        }
        return update.where(SYS_USER.USER_ID.eq(user.getUserId())).execute();
    }

    @Override
    public int updateUserAvatar(String userName, String avatar) {
        return this.dsl.update(SYS_USER)
            .set(SYS_USER.AVATAR, avatar)
            .where(SYS_USER.USER_NAME.eq(userName))
            .execute();
    }

    @Override
    public int resetUserPwd(String userName, String password) {
        return this.dsl.update(SYS_USER)
            .set(SYS_USER.PASSWORD, password)
            .where(SYS_USER.USER_NAME.eq(userName))
            .execute();
    }

    @Override
    public int deleteUserById(Long userId) {
        return this.dsl.update(SYS_USER)
            .set(SYS_USER.DEL_FLAG, "2")
            .where(SYS_USER.USER_ID.eq(userId))
            .execute();
    }

    @Override
    public int deleteUserByIds(Long[] userIds) {
        return this.dsl.update(SYS_USER)
            .set(SYS_USER.DEL_FLAG, "2")
            .where(SYS_USER.USER_ID.in(Arrays.asList(userIds)))
            .execute();
    }

    @Override
    public int checkUserNameUnique(String userName) {
        Integer count = this.dsl.selectCount()
            .from(SYS_USER)
            .where(SYS_USER.USER_NAME.eq(userName))
            .fetchOne(0, Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public SysUser checkPhoneUnique(String phonenumber) {
        return this.dsl.select(SYS_USER.USER_ID, SYS_USER.PHONENUMBER)
            .from(SYS_USER)
            .where(SYS_USER.PHONENUMBER.eq(phonenumber))
            .limit(1)
            .fetchOne(this::mapUser);
    }

    @Override
    public SysUser checkEmailUnique(String email) {
        return this.dsl.select(SYS_USER.USER_ID, SYS_USER.EMAIL)
            .from(SYS_USER)
            .where(SYS_USER.EMAIL.eq(email))
            .limit(1)
            .fetchOne(this::mapUser);
    }

    @Override
    public int deleteRobot(String robotId) {
        return this.dsl.update(SYS_USER)
            .set(SYS_USER.DEL_FLAG, "2")
            .where(SYS_USER.USER_NAME.eq(robotId))
            .and(SYS_USER.USER_TYPE.eq("99"))
            .execute();
    }

    private org.jooq.SelectJoinStep<Record> userBase() {
        return this.dsl.select(SYS_USER.fields())
            .select(
                SYS_DEPT.DEPT_ID,
                SYS_DEPT.PARENT_ID,
                SYS_DEPT.ANCESTORS,
                SYS_DEPT.DEPT_NAME,
                SYS_DEPT.ORDER_NUM,
                SYS_DEPT.LEADER,
                SYS_DEPT.STATUS
            )
            .from(SYS_USER)
            .leftJoin(SYS_DEPT).on(SYS_USER.DEPT_ID.eq(SYS_DEPT.DEPT_ID));
    }

    private Condition deptFilter(SysUser user) {
        Long deptId = user == null ? null : user.getDeptId();
        if (deptId == null || deptId == 0L) {
            return DSL.noCondition();
        }
        return SYS_USER.DEPT_ID.eq(deptId)
            .or(SYS_USER.DEPT_ID.in(
                DSL.select(SYS_DEPT.DEPT_ID)
                    .from(SYS_DEPT)
                    .where(SYS_DEPT.ANCESTORS.like("%" + deptId + "%"))
            ));
    }

    private SysUser mapUserWithDept(Record record) {
        SysUser user = mapUser(record);
        Long deptId = get(record, SYS_DEPT.DEPT_ID);
        if (deptId != null) {
            SysDept dept = new SysDept();
            dept.setDeptId(deptId);
            dept.setParentId(get(record, SYS_DEPT.PARENT_ID));
            dept.setAncestors(get(record, SYS_DEPT.ANCESTORS));
            dept.setDeptName(get(record, SYS_DEPT.DEPT_NAME));
            dept.setOrderNum(get(record, SYS_DEPT.ORDER_NUM));
            dept.setLeader(get(record, SYS_DEPT.LEADER));
            dept.setStatus(get(record, SYS_DEPT.STATUS));
            user.setDept(dept);
        }
        return user;
    }

    private SysUser mapUser(Record record) {
        SysUser user = new SysUser();
        user.setUserId(get(record, SYS_USER.USER_ID));
        user.setDeptId(get(record, SYS_USER.DEPT_ID));
        user.setUserName(get(record, SYS_USER.USER_NAME));
        user.setNickName(get(record, SYS_USER.NICK_NAME));
        user.setEmail(get(record, SYS_USER.EMAIL));
        user.setAvatar(get(record, SYS_USER.AVATAR));
        user.setPhonenumber(get(record, SYS_USER.PHONENUMBER));
        user.setPassword(get(record, SYS_USER.PASSWORD));
        user.setSex(get(record, SYS_USER.SEX));
        user.setStatus(get(record, SYS_USER.STATUS));
        user.setDelFlag(get(record, SYS_USER.DEL_FLAG));
        user.setLoginIp(get(record, SYS_USER.LOGIN_IP));
        user.setLoginDate(toDate(get(record, SYS_USER.LOGIN_DATE)));
        user.setCreateBy(get(record, SYS_USER.CREATE_BY));
        user.setCreateTime(toDate(get(record, SYS_USER.CREATE_TIME)));
        user.setUpdateBy(get(record, SYS_USER.UPDATE_BY));
        user.setUpdateTime(toDate(get(record, SYS_USER.UPDATE_TIME)));
        user.setRemark(get(record, SYS_USER.REMARK));
        user.setUserType(get(record, SYS_USER.USER_TYPE));
        return user;
    }

    private <T> T get(Record record, TableField<?, T> field) {
        return record.field(field) == null ? null : record.get(field);
    }

    private boolean isZero(Long value) {
        return value != null && value == 0L;
    }

    private Long zeroToNull(Long value) {
        return value == null || value == 0L ? null : value;
    }
}
