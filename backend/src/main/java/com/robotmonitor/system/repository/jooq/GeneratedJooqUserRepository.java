package com.robotmonitor.system.repository.jooq;

import static com.robotmonitor.jooq.generated.Tables.SYS_DEPT;
import static com.robotmonitor.jooq.generated.Tables.SYS_USER;

import com.robotmonitor.common.core.domain.entity.SysDept;
import com.robotmonitor.common.core.domain.entity.SysUser;
import java.util.Date;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

@Repository
public class GeneratedJooqUserRepository {
    private final DSLContext dsl;

    public GeneratedJooqUserRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public SysUser selectUserByUserName(String userName) {
        Record record = this.dsl.select(
                SYS_USER.USER_ID,
                SYS_USER.DEPT_ID,
                SYS_USER.USER_NAME,
                SYS_USER.NICK_NAME,
                SYS_USER.EMAIL,
                SYS_USER.PHONENUMBER,
                SYS_USER.SEX,
                SYS_USER.AVATAR,
                SYS_USER.PASSWORD,
                SYS_USER.STATUS,
                SYS_USER.DEL_FLAG,
                SYS_USER.LOGIN_IP,
                SYS_USER.LOGIN_DATE,
                SYS_USER.CREATE_BY,
                SYS_USER.CREATE_TIME,
                SYS_USER.UPDATE_BY,
                SYS_USER.UPDATE_TIME,
                SYS_USER.REMARK,
                SYS_USER.USER_TYPE,
                SYS_DEPT.DEPT_ID,
                SYS_DEPT.PARENT_ID,
                SYS_DEPT.ANCESTORS,
                SYS_DEPT.DEPT_NAME,
                SYS_DEPT.ORDER_NUM,
                SYS_DEPT.LEADER,
                SYS_DEPT.PHONE,
                SYS_DEPT.EMAIL,
                SYS_DEPT.STATUS,
                SYS_DEPT.DEL_FLAG,
                SYS_DEPT.ROOM_CODE
            )
            .from(SYS_USER)
            .leftJoin(SYS_DEPT).on(SYS_USER.DEPT_ID.eq(SYS_DEPT.DEPT_ID))
            .where(SYS_USER.USER_NAME.eq(userName))
            .fetchOne();
        return mapUser(record);
    }

    public SysUser selectUserById(Long userId) {
        Record record = this.dsl.select(
                SYS_USER.USER_ID,
                SYS_USER.DEPT_ID,
                SYS_USER.USER_NAME,
                SYS_USER.NICK_NAME,
                SYS_USER.EMAIL,
                SYS_USER.PHONENUMBER,
                SYS_USER.SEX,
                SYS_USER.AVATAR,
                SYS_USER.PASSWORD,
                SYS_USER.STATUS,
                SYS_USER.DEL_FLAG,
                SYS_USER.LOGIN_IP,
                SYS_USER.LOGIN_DATE,
                SYS_USER.CREATE_BY,
                SYS_USER.CREATE_TIME,
                SYS_USER.UPDATE_BY,
                SYS_USER.UPDATE_TIME,
                SYS_USER.REMARK,
                SYS_USER.USER_TYPE,
                SYS_DEPT.DEPT_ID,
                SYS_DEPT.PARENT_ID,
                SYS_DEPT.ANCESTORS,
                SYS_DEPT.DEPT_NAME,
                SYS_DEPT.ORDER_NUM,
                SYS_DEPT.LEADER,
                SYS_DEPT.PHONE,
                SYS_DEPT.EMAIL,
                SYS_DEPT.STATUS,
                SYS_DEPT.DEL_FLAG,
                SYS_DEPT.ROOM_CODE
            )
            .from(SYS_USER)
            .leftJoin(SYS_DEPT).on(SYS_USER.DEPT_ID.eq(SYS_DEPT.DEPT_ID))
            .where(SYS_USER.USER_ID.eq(userId))
            .fetchOne();
        return mapUser(record);
    }

    public int checkUserNameUnique(String userName) {
        Integer count = this.dsl.selectCount()
            .from(SYS_USER)
            .where(SYS_USER.USER_NAME.eq(userName))
            .fetchOne(0, Integer.class);
        return count == null ? 0 : count;
    }

    public SysUser checkPhoneUnique(String phone) {
        Record record = this.dsl.select(SYS_USER.USER_ID, SYS_USER.PHONENUMBER)
            .from(SYS_USER)
            .where(SYS_USER.PHONENUMBER.eq(phone))
            .limit(1)
            .fetchOne();
        if (record == null) {
            return null;
        }
        SysUser user = new SysUser();
        user.setUserId(record.get(SYS_USER.USER_ID));
        user.setPhonenumber(record.get(SYS_USER.PHONENUMBER));
        return user;
    }

    public SysUser checkEmailUnique(String email) {
        Record record = this.dsl.select(SYS_USER.USER_ID, SYS_USER.EMAIL)
            .from(SYS_USER)
            .where(SYS_USER.EMAIL.eq(email))
            .limit(1)
            .fetchOne();
        if (record == null) {
            return null;
        }
        SysUser user = new SysUser();
        user.setUserId(record.get(SYS_USER.USER_ID));
        user.setEmail(record.get(SYS_USER.EMAIL));
        return user;
    }

    public int updateUserAvatar(String userName, String avatar) {
        return this.dsl.update(SYS_USER)
            .set(SYS_USER.AVATAR, avatar)
            .where(SYS_USER.USER_NAME.eq(userName))
            .execute();
    }

    public int resetUserPwd(String userName, String password) {
        return this.dsl.update(SYS_USER)
            .set(SYS_USER.PASSWORD, password)
            .where(SYS_USER.USER_NAME.eq(userName))
            .execute();
    }

    private SysUser mapUser(Record record) {
        if (record == null) {
            return null;
        }
        SysUser user = new SysUser();
        user.setUserId(record.get(SYS_USER.USER_ID));
        user.setDeptId(record.get(SYS_USER.DEPT_ID));
        user.setUserName(record.get(SYS_USER.USER_NAME));
        user.setNickName(record.get(SYS_USER.NICK_NAME));
        user.setEmail(record.get(SYS_USER.EMAIL));
        user.setPhonenumber(record.get(SYS_USER.PHONENUMBER));
        user.setSex(record.get(SYS_USER.SEX));
        user.setAvatar(record.get(SYS_USER.AVATAR));
        user.setPassword(record.get(SYS_USER.PASSWORD));
        user.setStatus(record.get(SYS_USER.STATUS));
        user.setDelFlag(record.get(SYS_USER.DEL_FLAG));
        user.setLoginIp(record.get(SYS_USER.LOGIN_IP));
        user.setLoginDate(toDate(record.get(SYS_USER.LOGIN_DATE)));
        user.setCreateBy(record.get(SYS_USER.CREATE_BY));
        user.setCreateTime(toDate(record.get(SYS_USER.CREATE_TIME)));
        user.setUpdateBy(record.get(SYS_USER.UPDATE_BY));
        user.setUpdateTime(toDate(record.get(SYS_USER.UPDATE_TIME)));
        user.setRemark(record.get(SYS_USER.REMARK));
        user.setUserType(record.get(SYS_USER.USER_TYPE));
        if (record.get(SYS_DEPT.DEPT_ID) != null) {
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
            user.setDept(dept);
        }
        return user;
    }

    private Date toDate(java.time.LocalDateTime value) {
        return value == null ? null : java.sql.Timestamp.valueOf(value);
    }
}
