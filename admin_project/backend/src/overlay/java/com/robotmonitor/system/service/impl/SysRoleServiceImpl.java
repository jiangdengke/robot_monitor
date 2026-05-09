package com.robotmonitor.system.service.impl;

import com.robotmonitor.common.annotation.DataScope;
import com.robotmonitor.common.core.domain.entity.SysRole;
import com.robotmonitor.common.core.domain.entity.SysUser;
import com.robotmonitor.common.exception.ServiceException;
import com.robotmonitor.common.utils.SecurityUtils;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.common.utils.spring.SpringUtils;
import com.robotmonitor.system.domain.SysRoleDept;
import com.robotmonitor.system.domain.SysRoleMenu;
import com.robotmonitor.system.domain.SysUserRole;
import com.robotmonitor.system.mapper.SysRoleDeptMapper;
import com.robotmonitor.system.mapper.SysRoleMapper;
import com.robotmonitor.system.mapper.SysRoleMenuMapper;
import com.robotmonitor.system.mapper.SysUserRoleMapper;
import com.robotmonitor.system.repository.jooq.GeneratedJooqRoleRepository;
import com.robotmonitor.system.service.ISysRoleService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysRoleServiceImpl implements ISysRoleService {
    @Autowired
    private SysRoleMapper roleMapper;
    @Autowired
    private SysRoleMenuMapper roleMenuMapper;
    @Autowired
    private SysUserRoleMapper userRoleMapper;
    @Autowired
    private SysRoleDeptMapper roleDeptMapper;
    @Autowired
    private GeneratedJooqRoleRepository generatedJooqRoleRepository;

    @Override
    @DataScope(deptAlias = "d")
    public List<SysRole> selectRoleList(SysRole role) {
        return this.roleMapper.selectRoleList(role);
    }

    @Override
    public List<SysRole> selectRolesByUserId(Long userId) {
        List<SysRole> userRoles = this.generatedJooqRoleRepository.selectRolePermissionByUserId(userId);
        List<SysRole> roles = this.selectRoleAll();
        for (SysRole role : roles) {
            for (SysRole userRole : userRoles) {
                if (role.getRoleId().longValue() == userRole.getRoleId().longValue()) {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return roles;
    }

    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        List<SysRole> perms = this.generatedJooqRoleRepository.selectRolePermissionByUserId(userId);
        HashSet<String> permsSet = new HashSet<>();
        for (SysRole perm : perms) {
            if (StringUtils.isNotNull(perm)) {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public List<SysRole> selectRoleAll() {
        return ((SysRoleServiceImpl) SpringUtils.getAopProxy(this)).selectRoleList(new SysRole());
    }

    @Override
    public List<Long> selectRoleListByUserId(Long userId) {
        return this.generatedJooqRoleRepository.selectRoleListByUserId(userId);
    }

    @Override
    public SysRole selectRoleById(Long roleId) {
        return this.roleMapper.selectRoleById(roleId);
    }

    @Override
    public String checkRoleNameUnique(SysRole role) {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        SysRole info = this.roleMapper.checkRoleNameUnique(role.getRoleName());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
            return "1";
        }
        return "0";
    }

    @Override
    public String checkRoleKeyUnique(SysRole role) {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        SysRole info = this.roleMapper.checkRoleKeyUnique(role.getRoleKey());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
            return "1";
        }
        return "0";
    }

    @Override
    public void checkRoleAllowed(SysRole role) {
        if (StringUtils.isNotNull(role.getRoleId()) && role.isAdmin()) {
            throw new ServiceException("不允许操作超级管理员角色");
        }
    }

    @Override
    public void checkRoleDataScope(Long roleId) {
        if (!SysUser.isAdmin(SecurityUtils.getUserId())) {
            SysRole role = new SysRole();
            role.setRoleId(roleId);
            List<SysRole> roles = ((SysRoleServiceImpl) SpringUtils.getAopProxy(this)).selectRoleList(role);
            if (StringUtils.isEmpty(roles)) {
                throw new ServiceException("没有权限访问角色数据！");
            }
        }
    }

    @Override
    public int countUserRoleByRoleId(Long roleId) {
        return this.userRoleMapper.countUserRoleByRoleId(roleId);
    }

    @Override
    @Transactional
    public int insertRole(SysRole role) {
        this.roleMapper.insertRole(role);
        return this.insertRoleMenu(role);
    }

    @Override
    @Transactional
    public int updateRole(SysRole role) {
        this.roleMapper.updateRole(role);
        this.roleMenuMapper.deleteRoleMenuByRoleId(role.getRoleId());
        return this.insertRoleMenu(role);
    }

    @Override
    public int updateRoleStatus(SysRole role) {
        return this.roleMapper.updateRole(role);
    }

    @Override
    @Transactional
    public int authDataScope(SysRole role) {
        this.roleMapper.updateRole(role);
        this.roleDeptMapper.deleteRoleDeptByRoleId(role.getRoleId());
        return this.insertRoleDept(role);
    }

    public int insertRoleMenu(SysRole role) {
        int rows = 1;
        ArrayList<SysRoleMenu> list = new ArrayList<>();
        for (Long menuId : role.getMenuIds()) {
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (!list.isEmpty()) {
            rows = this.roleMenuMapper.batchRoleMenu(list);
        }
        return rows;
    }

    public int insertRoleDept(SysRole role) {
        int rows = 1;
        ArrayList<SysRoleDept> list = new ArrayList<>();
        for (Long deptId : role.getDeptIds()) {
            SysRoleDept rd = new SysRoleDept();
            rd.setRoleId(role.getRoleId());
            rd.setDeptId(deptId);
            list.add(rd);
        }
        if (!list.isEmpty()) {
            rows = this.roleDeptMapper.batchRoleDept(list);
        }
        return rows;
    }

    @Override
    @Transactional
    public int deleteRoleById(Long roleId) {
        this.roleMenuMapper.deleteRoleMenuByRoleId(roleId);
        this.roleDeptMapper.deleteRoleDeptByRoleId(roleId);
        return this.roleMapper.deleteRoleById(roleId);
    }

    @Override
    @Transactional
    public int deleteRoleByIds(Long[] roleIds) {
        for (Long roleId : roleIds) {
            this.checkRoleAllowed(new SysRole(roleId));
            this.checkRoleDataScope(roleId);
            SysRole role = this.selectRoleById(roleId);
            if (this.countUserRoleByRoleId(roleId) > 0) {
                throw new ServiceException(String.format("%1$s已分配,不能删除", role.getRoleName()));
            }
        }
        this.roleMenuMapper.deleteRoleMenu(roleIds);
        this.roleDeptMapper.deleteRoleDept(roleIds);
        return this.roleMapper.deleteRoleByIds(roleIds);
    }

    @Override
    public int deleteAuthUser(SysUserRole userRole) {
        return this.userRoleMapper.deleteUserRoleInfo(userRole);
    }

    @Override
    public int deleteAuthUsers(Long roleId, Long[] userIds) {
        return this.userRoleMapper.deleteUserRoleInfos(roleId, userIds);
    }

    @Override
    public int insertAuthUsers(Long roleId, Long[] userIds) {
        ArrayList<SysUserRole> list = new ArrayList<>();
        for (Long userId : userIds) {
            SysUserRole ur = new SysUserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        return this.userRoleMapper.batchUserRole(list);
    }
}
