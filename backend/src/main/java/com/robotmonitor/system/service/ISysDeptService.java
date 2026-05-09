/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.TreeSelect
 *  com.robotmonitor.common.core.domain.entity.SysDept
 */
package com.robotmonitor.system.service;

import com.robotmonitor.common.core.domain.TreeSelect;
import com.robotmonitor.common.core.domain.entity.SysDept;
import java.util.List;

public interface ISysDeptService {
    public List<SysDept> selectDeptList(SysDept var1);

    public List<SysDept> buildDeptTree(List<SysDept> var1);

    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> var1);

    public List<Long> selectDeptListByRoleId(Long var1);

    public SysDept selectDeptById(Long var1);

    public int selectNormalChildrenDeptById(Long var1);

    public boolean hasChildByDeptId(Long var1);

    public boolean checkDeptExistUser(Long var1);

    public String checkDeptNameUnique(SysDept var1);

    public void checkDeptDataScope(Long var1);

    public int insertDept(SysDept var1);

    public int updateDept(SysDept var1);

    public int deleteDeptById(Long var1);

    public List<SysDept> allRoomCode(String var1);

    public List<SysDept> roomList();
}
