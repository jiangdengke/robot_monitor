/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.system.mapper;

import com.robotmonitor.system.domain.SysRoleDept;
import java.util.List;

public interface SysRoleDeptMapper {
    public int deleteRoleDeptByRoleId(Long var1);

    public int deleteRoleDept(Long[] var1);

    public int selectCountRoleDeptByDeptId(Long var1);

    public int batchRoleDept(List<SysRoleDept> var1);
}
