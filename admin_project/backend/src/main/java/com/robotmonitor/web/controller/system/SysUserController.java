/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Log
 *  com.robotmonitor.common.core.controller.BaseController
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.core.domain.entity.SysRole
 *  com.robotmonitor.common.core.domain.entity.SysUser
 *  com.robotmonitor.common.core.page.TableDataInfo
 *  com.robotmonitor.common.enums.BusinessType
 *  com.robotmonitor.common.utils.SecurityUtils
 *  com.robotmonitor.common.utils.StringUtils
 *  com.robotmonitor.common.utils.poi.ExcelUtil
 *  com.robotmonitor.system.service.ISysPostService
 *  com.robotmonitor.system.service.ISysRoleService
 *  com.robotmonitor.system.service.ISysUserService
 *  jakarta.servlet.http.HttpServletResponse
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
 *  org.springframework.web.multipart.MultipartFile
 */
package com.robotmonitor.web.controller.system;

import com.robotmonitor.common.annotation.Log;
import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.domain.entity.SysRole;
import com.robotmonitor.common.core.domain.entity.SysUser;
import com.robotmonitor.common.core.page.TableDataInfo;
import com.robotmonitor.common.enums.BusinessType;
import com.robotmonitor.common.utils.SecurityUtils;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.common.utils.poi.ExcelUtil;
import com.robotmonitor.system.service.ISysPostService;
import com.robotmonitor.system.service.ISysRoleService;
import com.robotmonitor.system.service.ISysUserService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;
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
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value={"/system/user"})
public class SysUserController
extends BaseController {
    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysRoleService roleService;
    @Autowired
    private ISysPostService postService;

    @PreAuthorize(value="@ss.hasPermi('system:user:list')")
    @GetMapping(value={"/list"})
    public TableDataInfo list(SysUser user) {
        this.startPage();
        List list = this.userService.selectUserList(user);
        return this.getDataTable(list);
    }

    @Log(title="\u7528\u6237\u7ba1\u7406", businessType=BusinessType.EXPORT)
    @PreAuthorize(value="@ss.hasPermi('system:user:export')")
    @PostMapping(value={"/export"})
    public void export(HttpServletResponse response, SysUser user) {
        List list = this.userService.selectUserList(user);
        ExcelUtil util = new ExcelUtil(SysUser.class);
        util.exportExcel(response, list, "\u7528\u6237\u6570\u636e");
    }

    @Log(title="\u7528\u6237\u7ba1\u7406", businessType=BusinessType.IMPORT)
    @PreAuthorize(value="@ss.hasPermi('system:user:import')")
    @PostMapping(value={"/importData"})
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil util = new ExcelUtil(SysUser.class);
        List userList = util.importExcel(file.getInputStream());
        String operName = this.getUsername();
        String message = this.userService.importUser(userList, Boolean.valueOf(updateSupport), operName);
        return AjaxResult.success((String)message);
    }

    @PostMapping(value={"/importTemplate"})
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil util = new ExcelUtil(SysUser.class);
        util.importTemplateExcel(response, "\u7528\u6237\u6570\u636e");
    }

    @PreAuthorize(value="@ss.hasPermi('system:user:query')")
    @GetMapping(value={"/", "/{userId}"})
    public AjaxResult getInfo(@PathVariable(value="userId", required=false) Long userId) {
        this.userService.checkUserDataScope(userId);
        AjaxResult ajax = AjaxResult.success();
        List<SysRole> roles = this.roleService.selectRoleAll();
        ajax.put("roles", (Object)(SysUser.isAdmin((Long)userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList())));
        ajax.put("posts", (Object)this.postService.selectPostAll());
        if (StringUtils.isNotNull((Object)userId)) {
            SysUser sysUser = this.userService.selectUserById(userId);
            ajax.put("data", (Object)sysUser);
            ajax.put("postIds", (Object)this.postService.selectPostListByUserId(userId));
            ajax.put("roleIds", sysUser.getRoles().stream().map(SysRole::getRoleId).collect(Collectors.toList()));
        }
        return ajax;
    }

    @PreAuthorize(value="@ss.hasPermi('system:user:add')")
    @Log(title="\u7528\u6237\u7ba1\u7406", businessType=BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysUser user) {
        if ("1".equals(this.userService.checkUserNameUnique(user.getUserName()))) {
            return AjaxResult.error((String)("\u65b0\u589e\u7528\u6237'" + user.getUserName() + "'\u5931\u8d25\uff0c\u767b\u5f55\u8d26\u53f7\u5df2\u5b58\u5728"));
        }
        if (StringUtils.isNotEmpty((String)user.getPhonenumber()) && "1".equals(this.userService.checkPhoneUnique(user))) {
            return AjaxResult.error((String)("\u65b0\u589e\u7528\u6237'" + user.getUserName() + "'\u5931\u8d25\uff0c\u624b\u673a\u53f7\u7801\u5df2\u5b58\u5728"));
        }
        if (StringUtils.isNotEmpty((String)user.getEmail()) && "1".equals(this.userService.checkEmailUnique(user))) {
            return AjaxResult.error((String)("\u65b0\u589e\u7528\u6237'" + user.getUserName() + "'\u5931\u8d25\uff0c\u90ae\u7bb1\u8d26\u53f7\u5df2\u5b58\u5728"));
        }
        user.setCreateBy(this.getUsername());
        user.setPassword(SecurityUtils.encryptPassword((String)user.getPassword()));
        return this.toAjax(this.userService.insertUser(user));
    }

    @PreAuthorize(value="@ss.hasPermi('system:user:edit')")
    @Log(title="\u7528\u6237\u7ba1\u7406", businessType=BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysUser user) {
        this.userService.checkUserAllowed(user);
        this.userService.checkUserDataScope(user.getUserId());
        if (StringUtils.isNotEmpty((String)user.getPhonenumber()) && "1".equals(this.userService.checkPhoneUnique(user))) {
            return AjaxResult.error((String)("\u4fee\u6539\u7528\u6237'" + user.getUserName() + "'\u5931\u8d25\uff0c\u624b\u673a\u53f7\u7801\u5df2\u5b58\u5728"));
        }
        if (StringUtils.isNotEmpty((String)user.getEmail()) && "1".equals(this.userService.checkEmailUnique(user))) {
            return AjaxResult.error((String)("\u4fee\u6539\u7528\u6237'" + user.getUserName() + "'\u5931\u8d25\uff0c\u90ae\u7bb1\u8d26\u53f7\u5df2\u5b58\u5728"));
        }
        user.setUpdateBy(this.getUsername());
        return this.toAjax(this.userService.updateUser(user));
    }

    @PreAuthorize(value="@ss.hasPermi('system:user:remove')")
    @Log(title="\u7528\u6237\u7ba1\u7406", businessType=BusinessType.DELETE)
    @DeleteMapping(value={"/{userIds}"})
    public AjaxResult remove(@PathVariable Long[] userIds) {
        if (ArrayUtils.contains((Object[])userIds, (Object)this.getUserId())) {
            return this.error("\u5f53\u524d\u7528\u6237\u4e0d\u80fd\u5220\u9664");
        }
        return this.toAjax(this.userService.deleteUserByIds(userIds));
    }

    @PreAuthorize(value="@ss.hasPermi('system:user:resetPwd')")
    @Log(title="\u7528\u6237\u7ba1\u7406", businessType=BusinessType.UPDATE)
    @PutMapping(value={"/resetPwd"})
    public AjaxResult resetPwd(@RequestBody SysUser user) {
        this.userService.checkUserAllowed(user);
        this.userService.checkUserDataScope(user.getUserId());
        user.setPassword(SecurityUtils.encryptPassword((String)user.getPassword()));
        user.setUpdateBy(this.getUsername());
        return this.toAjax(this.userService.resetPwd(user));
    }

    public static void main(String[] args) {
        System.out.println(SecurityUtils.encryptPassword((String)"admin"));
    }

    @PreAuthorize(value="@ss.hasPermi('system:user:edit')")
    @Log(title="\u7528\u6237\u7ba1\u7406", businessType=BusinessType.UPDATE)
    @PutMapping(value={"/changeStatus"})
    public AjaxResult changeStatus(@RequestBody SysUser user) {
        this.userService.checkUserAllowed(user);
        this.userService.checkUserDataScope(user.getUserId());
        user.setUpdateBy(this.getUsername());
        return this.toAjax(this.userService.updateUserStatus(user));
    }

    @PreAuthorize(value="@ss.hasPermi('system:user:query')")
    @GetMapping(value={"/authRole/{userId}"})
    public AjaxResult authRole(@PathVariable(value="userId") Long userId) {
        AjaxResult ajax = AjaxResult.success();
        SysUser user = this.userService.selectUserById(userId);
        List<SysRole> roles = this.roleService.selectRolesByUserId(userId);
        ajax.put("user", (Object)user);
        ajax.put("roles", (Object)(SysUser.isAdmin((Long)userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList())));
        return ajax;
    }

    @PreAuthorize(value="@ss.hasPermi('system:user:edit')")
    @Log(title="\u7528\u6237\u7ba1\u7406", businessType=BusinessType.GRANT)
    @PutMapping(value={"/authRole"})
    public AjaxResult insertAuthRole(Long userId, Long[] roleIds) {
        this.userService.checkUserDataScope(userId);
        this.userService.insertUserAuth(userId, roleIds);
        return this.success();
    }
}
