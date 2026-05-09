/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.AutoFill
 *  com.robotmonitor.common.core.domain.entity.SysUser
 *  com.robotmonitor.common.enums.OperationType
 *  org.apache.ibatis.annotations.Param
 */
package com.robotmonitor.system.mapper;

import com.robotmonitor.common.annotation.AutoFill;
import com.robotmonitor.common.core.domain.entity.SysUser;
import com.robotmonitor.common.enums.OperationType;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysUserMapper {
    public List<SysUser> selectUserList(SysUser var1);

    public List<SysUser> selectAllocatedList(SysUser var1);

    public List<SysUser> selectUnallocatedList(SysUser var1);

    public SysUser selectUserByUserName(String var1);

    public SysUser selectUserById(Long var1);

    @AutoFill(value=OperationType.INSERT)
    public int insertUser(SysUser var1);

    public int updateUser(SysUser var1);

    @AutoFill(value=OperationType.UPDATE)
    public int updateUserAvatar(@Param(value="userName") String var1, @Param(value="avatar") String var2);

    @AutoFill(value=OperationType.UPDATE)
    public int resetUserPwd(@Param(value="userName") String var1, @Param(value="password") String var2);

    public int deleteUserById(Long var1);

    public int deleteUserByIds(Long[] var1);

    public int checkUserNameUnique(String var1);

    public SysUser checkPhoneUnique(String var1);

    public SysUser checkEmailUnique(String var1);

    public int deleteRobot(String var1);
}
