/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Log
 *  com.robotmonitor.common.core.controller.BaseController
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.core.domain.model.LoginUser
 *  com.robotmonitor.common.core.page.TableDataInfo
 *  com.robotmonitor.common.core.redis.RedisCache
 *  com.robotmonitor.common.enums.BusinessType
 *  com.robotmonitor.common.utils.StringUtils
 *  com.robotmonitor.system.domain.SysUserOnline
 *  com.robotmonitor.system.service.ISysUserOnlineService
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.security.access.prepost.PreAuthorize
 *  org.springframework.web.bind.annotation.DeleteMapping
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.robotmonitor.web.controller.monitor;

import com.robotmonitor.common.annotation.Log;
import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.domain.model.LoginUser;
import com.robotmonitor.common.core.page.TableDataInfo;
import com.robotmonitor.common.core.redis.RedisCache;
import com.robotmonitor.common.enums.BusinessType;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.system.domain.SysUserOnline;
import com.robotmonitor.system.service.ISysUserOnlineService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/monitor/online"})
public class SysUserOnlineController
extends BaseController {
    @Autowired
    private ISysUserOnlineService userOnlineService;
    @Autowired
    private RedisCache redisCache;

    @PreAuthorize(value="@ss.hasPermi('monitor:online:list')")
    @GetMapping(value={"/list"})
    public TableDataInfo list(String ipaddr, String userName) {
        Collection<String> keys = this.redisCache.keys("login_tokens:*");
        ArrayList<SysUserOnline> userOnlineList = new ArrayList<SysUserOnline>();
        for (String key : keys) {
            LoginUser user = (LoginUser)this.redisCache.getCacheObject(key);
            if (StringUtils.isNotEmpty((String)ipaddr) && StringUtils.isNotEmpty((String)userName)) {
                if (!StringUtils.equals((CharSequence)ipaddr, (CharSequence)user.getIpaddr()) || !StringUtils.equals((CharSequence)userName, (CharSequence)user.getUsername())) continue;
                userOnlineList.add(this.userOnlineService.selectOnlineByInfo(ipaddr, userName, user));
                continue;
            }
            if (StringUtils.isNotEmpty((String)ipaddr)) {
                if (!StringUtils.equals((CharSequence)ipaddr, (CharSequence)user.getIpaddr())) continue;
                userOnlineList.add(this.userOnlineService.selectOnlineByIpaddr(ipaddr, user));
                continue;
            }
            if (StringUtils.isNotEmpty((String)userName) && StringUtils.isNotNull((Object)user.getUser())) {
                if (!StringUtils.equals((CharSequence)userName, (CharSequence)user.getUsername())) continue;
                userOnlineList.add(this.userOnlineService.selectOnlineByUserName(userName, user));
                continue;
            }
            userOnlineList.add(this.userOnlineService.loginUserToUserOnline(user));
        }
        Collections.reverse(userOnlineList);
        userOnlineList.removeAll(Collections.singleton(null));
        return this.getDataTable(userOnlineList);
    }

    @PreAuthorize(value="@ss.hasPermi('monitor:online:forceLogout')")
    @Log(title="\u5728\u7ebf\u7528\u6237", businessType=BusinessType.FORCE)
    @DeleteMapping(value={"/{tokenId}"})
    public AjaxResult forceLogout(@PathVariable String tokenId) {
        this.redisCache.deleteObject("login_tokens:" + tokenId);
        return AjaxResult.success();
    }
}
