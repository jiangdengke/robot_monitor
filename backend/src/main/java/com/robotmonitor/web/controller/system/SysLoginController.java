/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.core.domain.config.ConfigRobot
 *  com.robotmonitor.common.core.domain.entity.SysUser
 *  com.robotmonitor.common.core.domain.model.LoginBody
 *  com.robotmonitor.common.core.redis.RedisCache
 *  com.robotmonitor.common.utils.SecurityUtils
 *  com.robotmonitor.config.service.IConfigRobotService
 *  com.robotmonitor.flight.service.IPassengerService
 *  com.robotmonitor.framework.web.service.SysLoginService
 *  com.robotmonitor.framework.web.service.SysPermissionService
 *  com.robotmonitor.system.service.ISysMenuService
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.robotmonitor.web.controller.system;

import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.domain.config.ConfigRobot;
import com.robotmonitor.common.core.domain.entity.SysUser;
import com.robotmonitor.common.core.domain.model.LoginBody;
import com.robotmonitor.common.core.redis.RedisCache;
import com.robotmonitor.common.utils.SecurityUtils;
import com.robotmonitor.config.service.IConfigRobotService;
import com.robotmonitor.flight.service.IPassengerService;
import com.robotmonitor.framework.web.service.SysLoginService;
import com.robotmonitor.framework.web.service.SysPermissionService;
import com.robotmonitor.system.service.ISysMenuService;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SysLoginController {
    @Autowired
    private SysLoginService loginService;
    @Autowired
    private ISysMenuService menuService;
    @Autowired
    private SysPermissionService permissionService;
    @Autowired
    private IConfigRobotService configRobotService;
    @Autowired
    private IPassengerService tPassengerService;
    @Autowired
    private RedisCache redisCache;

    @PostMapping(value={"/login"})
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        AjaxResult ajax = AjaxResult.success();
        String token = this.loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(), loginBody.getUuid());
        ajax.put("token", (Object)token);
        return ajax;
    }

    @Transactional
    @PostMapping(value={"/robotLogin"})
    public AjaxResult robotLogin(@RequestBody LoginBody loginBody) {
        ConfigRobot robot = this.configRobotService.selectConfigRobotByRobotId(loginBody.getRobotId());
        AjaxResult ajax = AjaxResult.success();
        String token = this.loginService.robotLogin(robot.getRobotId(), robot.getMac());
        this.redisCache.setCacheObject("robot_login_tokens:" + loginBody.getRobotId(), (Object)robot);
        ajax.put("token", (Object)token);
        return ajax;
    }

    @GetMapping(value={"getInfo"})
    public AjaxResult getInfo() {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        Set roles = this.permissionService.getRolePermission(user);
        Set permissions = this.permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", (Object)user);
        ajax.put("roles", (Object)roles);
        ajax.put("permissions", (Object)permissions);
        ajax.put("roomList", (Object)SecurityUtils.getLoginUser().getRoomList());
        return ajax;
    }

    @GetMapping(value={"getRouters"})
    public AjaxResult getRouters() {
        Long userId = SecurityUtils.getUserId();
        List menus = this.menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success((Object)this.menuService.buildMenus(menus));
    }
}
