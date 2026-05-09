/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.DataScope
 *  com.robotmonitor.common.core.domain.entity.SysRole
 *  com.robotmonitor.common.core.domain.entity.SysUser
 *  com.robotmonitor.common.exception.ServiceException
 *  com.robotmonitor.common.utils.SecurityUtils
 *  com.robotmonitor.common.utils.StringUtils
 *  com.robotmonitor.common.utils.bean.BeanValidators
 *  com.robotmonitor.common.utils.spring.SpringUtils
 *  jakarta.validation.Validator
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.util.CollectionUtils
 */
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
public class SysUserServiceImpl
implements ISysUserService {
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
    @DataScope(deptAlias="d", userAlias="u")
    public List<SysUser> selectUserList(SysUser user) {
        return this.userMapper.selectUserList(user);
    }

    @Override
    @DataScope(deptAlias="d", userAlias="u")
    public List<SysUser> selectAllocatedList(SysUser user) {
        return this.userMapper.selectAllocatedList(user);
    }

    @Override
    @DataScope(deptAlias="d", userAlias="u")
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
        if (count > 0) {
            return "1";
        }
        return "0";
    }

    @Override
    public String checkPhoneUnique(SysUser user) {
        Long userId = StringUtils.isNull((Object)user.getUserId()) ? -1L : user.getUserId();
        SysUser info = this.generatedJooqUserRepository.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull((Object)info) && info.getUserId().longValue() != userId.longValue()) {
            return "1";
        }
        return "0";
    }

    @Override
    public String checkEmailUnique(SysUser user) {
        Long userId = StringUtils.isNull((Object)user.getUserId()) ? -1L : user.getUserId();
        SysUser info = this.generatedJooqUserRepository.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull((Object)info) && info.getUserId().longValue() != userId.longValue()) {
            return "1";
        }
        return "0";
    }

    @Override
    public void checkUserAllowed(SysUser user) {
        if (StringUtils.isNotNull((Object)user.getUserId()) && user.isAdmin()) {
            throw new ServiceException("\u4e0d\u5141\u8bb8\u64cd\u4f5c\u8d85\u7ea7\u7ba1\u7406\u5458\u7528\u6237");
        }
    }

    @Override
    public void checkUserDataScope(Long userId) {
        if (!SysUser.isAdmin((Long)SecurityUtils.getUserId())) {
            SysUser user = new SysUser();
            user.setUserId(userId);
            List<SysUser> users = ((SysUserServiceImpl)SpringUtils.getAopProxy((Object)this)).selectUserList(user);
            if (StringUtils.isEmpty(users)) {
                throw new ServiceException("\u6ca1\u6709\u6743\u9650\u8bbf\u95ee\u7528\u6237\u6570\u636e\uff01");
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
        if (StringUtils.isNotEmpty((Object[])posts)) {
            ArrayList<SysUserPost> list = new ArrayList<SysUserPost>(posts.length);
            for (Object postId : posts) {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId((Long)postId);
                list.add(up);
            }
            this.userPostMapper.batchUserPost(list);
        }
    }

    public void insertUserRole(Long userId, Long[] roleIds) {
        if (StringUtils.isNotEmpty((Object[])roleIds)) {
            ArrayList<SysUserRole> list = new ArrayList<SysUserRole>(roleIds.length);
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
        if (StringUtils.isNull(userList) || userList.size() == 0) {
            throw new ServiceException("\u5bfc\u5165\u7528\u6237\u6570\u636e\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = this.configService.selectConfigByKey("sys.user.initPassword");
        for (SysUser user : userList) {
            try {
                SysUser u = this.userMapper.selectUserByUserName(user.getUserName());
                if (StringUtils.isNull((Object)u)) {
                    BeanValidators.validateWithException((Validator)this.validator, (Object)user, (Class[])new Class[0]);
                    user.setPassword(SecurityUtils.encryptPassword((String)password));
                    user.setCreateBy(operName);
                    this.insertUser(user);
                    successMsg.append("<br/>" + ++successNum + "\u3001\u8d26\u53f7 " + user.getUserName() + " \u5bfc\u5165\u6210\u529f");
                    continue;
                }
                if (isUpdateSupport.booleanValue()) {
                    BeanValidators.validateWithException((Validator)this.validator, (Object)user, (Class[])new Class[0]);
                    user.setUpdateBy(operName);
                    this.updateUser(user);
                    successMsg.append("<br/>" + ++successNum + "\u3001\u8d26\u53f7 " + user.getUserName() + " \u66f4\u65b0\u6210\u529f");
                    continue;
                }
                failureMsg.append("<br/>" + ++failureNum + "\u3001\u8d26\u53f7 " + user.getUserName() + " \u5df2\u5b58\u5728");
            }
            catch (Exception e) {
                String msg = "<br/>" + ++failureNum + "\u3001\u8d26\u53f7 " + user.getUserName() + " \u5bfc\u5165\u5931\u8d25\uff1a";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, (Throwable)e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "\u5f88\u62b1\u6b49\uff0c\u5bfc\u5165\u5931\u8d25\uff01\u5171 " + failureNum + " \u6761\u6570\u636e\u683c\u5f0f\u4e0d\u6b63\u786e\uff0c\u9519\u8bef\u5982\u4e0b\uff1a");
            throw new ServiceException(failureMsg.toString());
        }
        successMsg.insert(0, "\u606d\u559c\u60a8\uff0c\u6570\u636e\u5df2\u5168\u90e8\u5bfc\u5165\u6210\u529f\uff01\u5171 " + successNum + " \u6761\uff0c\u6570\u636e\u5982\u4e0b\uff1a");
        return successMsg.toString();
    }

    @Override
    public int deleteRobot(String robotId) {
        return this.userMapper.deleteRobot(robotId);
    }
}
