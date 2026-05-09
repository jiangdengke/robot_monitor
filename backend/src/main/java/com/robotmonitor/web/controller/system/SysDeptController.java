/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Log
 *  com.robotmonitor.common.core.controller.BaseController
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.core.domain.entity.SysDept
 *  com.robotmonitor.common.enums.BusinessType
 *  com.robotmonitor.common.utils.SecurityUtils
 *  com.robotmonitor.common.utils.StringUtils
 *  com.robotmonitor.system.service.ISysDeptService
 *  org.apache.commons.lang3.ArrayUtils
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.security.access.prepost.PreAuthorize
 *  org.springframework.validation.annotation.Validated
 *  org.springframework.web.bind.annotation.DeleteMapping
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.PutMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.robotmonitor.web.controller.system;

import com.robotmonitor.common.annotation.Log;
import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.domain.entity.SysDept;
import com.robotmonitor.common.enums.BusinessType;
import com.robotmonitor.common.utils.SecurityUtils;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.system.service.ISysDeptService;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/system/dept"})
public class SysDeptController
extends BaseController {
    @Autowired
    private ISysDeptService deptService;

    @GetMapping(value={"/list"})
    public AjaxResult list(SysDept dept) {
        List depts = this.deptService.selectDeptList(dept);
        return AjaxResult.success((Object)depts);
    }

    @PreAuthorize(value="@ss.hasPermi('system:dept:list')")
    @GetMapping(value={"/list/exclude/{deptId}"})
    public AjaxResult excludeChild(@PathVariable(value="deptId", required=false) Long deptId) {
        List depts = this.deptService.selectDeptList(new SysDept());
        Iterator it = depts.iterator();
        while (it.hasNext()) {
            SysDept d = (SysDept)it.next();
            if ((long)d.getDeptId().intValue() != deptId && !ArrayUtils.contains((Object[])StringUtils.split((String)d.getAncestors(), (String)","), (Object)("" + deptId))) continue;
            it.remove();
        }
        return AjaxResult.success((Object)depts);
    }

    @PreAuthorize(value="@ss.hasPermi('system:dept:query')")
    @GetMapping(value={"/{deptId}"})
    public AjaxResult getInfo(@PathVariable Long deptId) {
        this.deptService.checkDeptDataScope(deptId);
        return AjaxResult.success((Object)this.deptService.selectDeptById(deptId));
    }

    @GetMapping(value={"/treeselect"})
    public AjaxResult treeselect(SysDept dept) {
        List depts = this.deptService.selectDeptList(dept);
        return AjaxResult.success((Object)this.deptService.buildDeptTreeSelect(depts));
    }

    @GetMapping(value={"/roleDeptTreeselect/{roleId}"})
    public AjaxResult roleDeptTreeselect(@PathVariable(value="roleId") Long roleId) {
        List depts = this.deptService.selectDeptList(new SysDept());
        AjaxResult ajax = AjaxResult.success();
        ajax.put("checkedKeys", (Object)this.deptService.selectDeptListByRoleId(roleId));
        ajax.put("depts", (Object)this.deptService.buildDeptTreeSelect(depts));
        return ajax;
    }

    @Log(title="\u90e8\u95e8\u7ba1\u7406", businessType=BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysDept dept) {
        if ("1".equals(this.deptService.checkDeptNameUnique(dept))) {
            return AjaxResult.error((String)("\u65b0\u589e\u90e8\u95e8'" + dept.getDeptName() + "'\u5931\u8d25\uff0c\u90e8\u95e8\u540d\u79f0\u5df2\u5b58\u5728"));
        }
        dept.setCreateBy(this.getUsername());
        return this.toAjax(this.deptService.insertDept(dept));
    }

    @PreAuthorize(value="@ss.hasPermi('system:dept:edit')")
    @Log(title="\u90e8\u95e8\u7ba1\u7406", businessType=BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysDept dept) {
        Long deptId = dept.getDeptId();
        this.deptService.checkDeptDataScope(deptId);
        if ("1".equals(this.deptService.checkDeptNameUnique(dept))) {
            return AjaxResult.error((String)("\u4fee\u6539\u90e8\u95e8'" + dept.getDeptName() + "'\u5931\u8d25\uff0c\u90e8\u95e8\u540d\u79f0\u5df2\u5b58\u5728"));
        }
        if (dept.getParentId().equals(deptId)) {
            return AjaxResult.error((String)("\u4fee\u6539\u90e8\u95e8'" + dept.getDeptName() + "'\u5931\u8d25\uff0c\u4e0a\u7ea7\u90e8\u95e8\u4e0d\u80fd\u662f\u81ea\u5df1"));
        }
        if (StringUtils.equals((CharSequence)"1", (CharSequence)dept.getStatus()) && this.deptService.selectNormalChildrenDeptById(deptId) > 0) {
            return AjaxResult.error((String)"\u8be5\u90e8\u95e8\u5305\u542b\u672a\u505c\u7528\u7684\u5b50\u90e8\u95e8\uff01");
        }
        dept.setUpdateBy(this.getUsername());
        return this.toAjax(this.deptService.updateDept(dept));
    }

    @PreAuthorize(value="@ss.hasPermi('system:dept:remove')")
    @Log(title="\u90e8\u95e8\u7ba1\u7406", businessType=BusinessType.DELETE)
    @DeleteMapping(value={"/{deptId}"})
    public AjaxResult remove(@PathVariable Long deptId) {
        if (this.deptService.hasChildByDeptId(deptId)) {
            return AjaxResult.error((String)"\u5b58\u5728\u4e0b\u7ea7\u90e8\u95e8,\u4e0d\u5141\u8bb8\u5220\u9664");
        }
        if (this.deptService.checkDeptExistUser(deptId)) {
            return AjaxResult.error((String)"\u90e8\u95e8\u5b58\u5728\u7528\u6237,\u4e0d\u5141\u8bb8\u5220\u9664");
        }
        this.deptService.checkDeptDataScope(deptId);
        return this.toAjax(this.deptService.deleteDeptById(deptId));
    }

    @GetMapping(value={"/roomList"})
    public AjaxResult roomList() {
        List deptList = SecurityUtils.getLoginUser().getRoomList();
        return AjaxResult.success((Object)deptList);
    }
}
