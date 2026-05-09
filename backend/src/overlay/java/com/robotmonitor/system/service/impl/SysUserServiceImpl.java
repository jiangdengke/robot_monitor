package com.robotmonitor.system.service.impl;

import com.robotmonitor.common.annotation.DataScope;
import com.robotmonitor.common.core.domain.entity.SysRole;
import com.robotmonitor.common.core.domain.entity.SysUser;
import com.robotmonitor.common.exception.ServiceException;
import com.robotmonitor.common.utils.SecurityUtils;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.common.utils.bean.BeanValidators;
import com.robotmonitor.common.utils.spring.SpringUtils;
import com.robotmonitor.system.domain.SysPost;
import com.robotmonitor.system.domain.SysUserPost;
import com.robotmonitor.system.domain.SysUserRole;
import com.robotmonitor.system.mapper.SysPostMapper;
import com.robotmonitor.system.mapper.SysRoleMapper;
import com.robotmonitor.system.mapper.SysUserMapper;
import com.robotmonitor.system.mapper.SysUserPostMapper;
import com.robotmonitor.system.mapper.SysUserRoleMapper;
import com.robotmonitor.system.repository.jooq.GeneratedJooqPostRepository;
import com.robotmonitor.system.repository.jooq.GeneratedJooqRoleRepository;
import com.robotmonitor.system.repository.jooq.GeneratedJooqUserRepository;
import com.robotmonitor.system.service.ISysConfigService;
import com.robotmonitor.system.service.ISysUserService;
import jakarta.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class SysUserServiceImpl implements ISysUserService {
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysRoleMapper roleMapper;
    @Autowired
    private SysPostMapper postMapper;
    @Autowired
    private SysUserRoleMapper userRoleMapper;
    @Autowired
    private SysUserPostMapper userPostMapper;
    @Autowired
    private GeneratedJooqUserRepository generatedJooqUserRepository;
    @Autowired
    private GeneratedJooqRoleRepository generatedJooqRoleRepository;
    @Autowired
    private GeneratedJooqPostRepository generatedJooqPostRepository;
    @Autowired
    private ISysConfigService configService;
    @Autowired
    protected Validator validator;

    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUserList(SysUser user) {
        return this.userMapper.selectUserList(user);
    }

    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectAllocatedList(SysUser user) {
        return this.userMapper.selectAllocatedList(user);
    }

    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUnallocatedList(SysUser user) {
        return this.userMapper.selectUnallocatedList(user);
    }

    @Override
    public SysUser selectUserByUserName(String userName) {
        return this.generatedJooqUserRepository.selectUserByUserName(userName);
    }

    @Override
    public SysUser selectUserById(Long userId) {
        return this.generatedJooqUserRepository.selectUserById(userId);
    }

    @Override
    public String selectUserRoleGroup(String userName) {
        List<SysRole> list = this.generatedJooqRoleRepository.selectRolesByUserName(userName);
        if (CollectionUtils.isEmpty(list)) {
            return "";
        }
        return list.stream().map(SysRole::getRoleName).collect(Collectors.joining(","));
    }

    @Override
    public String selectUserPostGroup(String userName) {
        List<SysPost> list = this.generatedJooqPostRepository.selectPostsByUserName(userName);
        if (CollectionUtils.isEmpty(list)) {
            return "";
        }
        return list.stream().map(SysPost::getPostName).collect(Collectors.joining(","));
    }

    @Override
    public String checkUserNameUnique(String userName) {
        int count = this.generatedJooqUserRepository.checkUserNameUnique(userName);
        return count > 0 ? "1" : "0";
    }

    @Override
    public String checkPhoneUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = this.generatedJooqUserRepository.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return "1";
        }
        return "0";
    }

    @Override
    public String checkEmailUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = this.generatedJooqUserRepository.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return "1";
        }
        return "0";
    }

    @Override
    public void checkUserAllowed(SysUser user) {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin()) {
            throw new ServiceException("不允许操作超级管理员用户");
        }
    }

    @Override
    public void checkUserDataScope(Long userId) {
        if (!SysUser.isAdmin(SecurityUtils.getUserId())) {
            SysUser user = new SysUser();
            user.setUserId(userId);
            List<SysUser> users = ((SysUserServiceImpl) SpringUtils.getAopProxy(this)).selectUserList(user);
            if (StringUtils.isEmpty(users)) {
                throw new ServiceException("没有权限访问用户数据！");
            }
        }
    }

    @Override
    @Transactional
    public int insertUser(SysUser user) {
        int rows = this.userMapper.insertUser(user);
        this.insertUserPost(user);
        this.insertUserRole(user);
        return rows;
    }

    @Override
    public boolean registerUser(SysUser user) {
        return this.userMapper.insertUser(user) > 0;
    }

    @Override
    @Transactional
    public int updateUser(SysUser user) {
        Long userId = user.getUserId();
        this.userRoleMapper.deleteUserRoleByUserId(userId);
        this.insertUserRole(user);
        this.userPostMapper.deleteUserPostByUserId(userId);
        this.insertUserPost(user);
        return this.userMapper.updateUser(user);
    }

    @Override
    @Transactional
    public void insertUserAuth(Long userId, Long[] roleIds) {
        this.userRoleMapper.deleteUserRoleByUserId(userId);
        this.insertUserRole(userId, roleIds);
    }

    @Override
    public int updateUserStatus(SysUser user) {
        return this.userMapper.updateUser(user);
    }

    @Override
    public int updateUserProfile(SysUser user) {
        return this.userMapper.updateUser(user);
    }

    @Override
    public boolean updateUserAvatar(String userName, String avatar) {
        return this.generatedJooqUserRepository.updateUserAvatar(userName, avatar) > 0;
    }

    @Override
    public int resetPwd(SysUser user) {
        return this.userMapper.updateUser(user);
    }

    @Override
    public int resetUserPwd(String userName, String password) {
        return this.generatedJooqUserRepository.resetUserPwd(userName, password);
    }

    public void insertUserRole(SysUser user) {
        this.insertUserRole(user.getUserId(), user.getRoleIds());
    }

    public void insertUserPost(SysUser user) {
        Object[] posts = user.getPostIds();
        if (StringUtils.isNotEmpty(posts)) {
            ArrayList<SysUserPost> list = new ArrayList<>(posts.length);
            for (Object postId : posts) {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId((Long) postId);
                list.add(up);
            }
            this.userPostMapper.batchUserPost(list);
        }
    }

    public void insertUserRole(Long userId, Long[] roleIds) {
        if (StringUtils.isNotEmpty(roleIds)) {
            ArrayList<SysUserRole> list = new ArrayList<>(roleIds.length);
            for (Long roleId : roleIds) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                list.add(ur);
            }
            this.userRoleMapper.batchUserRole(list);
        }
    }

    @Override
    @Transactional
    public int deleteUserById(Long userId) {
        this.userRoleMapper.deleteUserRoleByUserId(userId);
        this.userPostMapper.deleteUserPostByUserId(userId);
        return this.userMapper.deleteUserById(userId);
    }

    @Override
    @Transactional
    public int deleteUserByIds(Long[] userIds) {
        for (Long userId : userIds) {
            this.checkUserAllowed(new SysUser(userId));
            this.checkUserDataScope(userId);
        }
        this.userRoleMapper.deleteUserRole(userIds);
        this.userPostMapper.deleteUserPost(userIds);
        return this.userMapper.deleteUserByIds(userIds);
    }

    @Override
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(userList) || userList.isEmpty()) {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = this.configService.selectConfigByKey("sys.user.initPassword");
        for (SysUser user : userList) {
            try {
                SysUser u = this.generatedJooqUserRepository.selectUserByUserName(user.getUserName());
                if (StringUtils.isNull(u)) {
                    BeanValidators.validateWithException(this.validator, user);
                    user.setPassword(SecurityUtils.encryptPassword(password));
                    user.setCreateBy(operName);
                    this.insertUser(user);
                    successMsg.append("<br/>").append(++successNum).append("、账号 ").append(user.getUserName()).append(" 导入成功");
                    continue;
                }
                if (isUpdateSupport.booleanValue()) {
                    BeanValidators.validateWithException(this.validator, user);
                    user.setUpdateBy(operName);
                    this.updateUser(user);
                    successMsg.append("<br/>").append(++successNum).append("、账号 ").append(user.getUserName()).append(" 更新成功");
                    continue;
                }
                failureMsg.append("<br/>").append(++failureNum).append("、账号 ").append(user.getUserName()).append(" 已存在");
            } catch (Exception e) {
                String msg = "<br/>" + ++failureNum + "、账号 " + user.getUserName() + " 导入失败：";
                failureMsg.append(msg).append(e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        return successMsg.toString();
    }

    @Override
    public int deleteRobot(String robotId) {
        return this.userMapper.deleteRobot(robotId);
    }
}
