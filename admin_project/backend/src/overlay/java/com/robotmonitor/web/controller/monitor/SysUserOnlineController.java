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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/monitor/online"})
public class SysUserOnlineController extends BaseController {
    @Autowired
    private ISysUserOnlineService userOnlineService;

    @Autowired
    private RedisCache redisCache;

    @PreAuthorize("@ss.hasPermi('monitor:online:list')")
    @GetMapping({"/list"})
    public TableDataInfo list(
        @RequestParam(value = "ipaddr", required = false) String ipaddr,
        @RequestParam(value = "userName", required = false) String userName
    ) {
        Collection<String> keys = redisCache.keys("login_tokens:*");
        ArrayList<SysUserOnline> userOnlineList = new ArrayList<>();
        for (String key : keys) {
            LoginUser user = redisCache.getCacheObject(key);
            if (StringUtils.isNotEmpty(ipaddr) && StringUtils.isNotEmpty(userName)) {
                if (!StringUtils.equals(ipaddr, user.getIpaddr()) || !StringUtils.equals(userName, user.getUsername())) {
                    continue;
                }
                userOnlineList.add(userOnlineService.selectOnlineByInfo(ipaddr, userName, user));
                continue;
            }
            if (StringUtils.isNotEmpty(ipaddr)) {
                if (!StringUtils.equals(ipaddr, user.getIpaddr())) {
                    continue;
                }
                userOnlineList.add(userOnlineService.selectOnlineByIpaddr(ipaddr, user));
                continue;
            }
            if (StringUtils.isNotEmpty(userName) && StringUtils.isNotNull(user.getUser())) {
                if (!StringUtils.equals(userName, user.getUsername())) {
                    continue;
                }
                userOnlineList.add(userOnlineService.selectOnlineByUserName(userName, user));
                continue;
            }
            userOnlineList.add(userOnlineService.loginUserToUserOnline(user));
        }
        Collections.reverse(userOnlineList);
        userOnlineList.removeAll(Collections.singleton(null));
        return getDataTable(userOnlineList);
    }

    @PreAuthorize("@ss.hasPermi('monitor:online:forceLogout')")
    @Log(title = "在线用户", businessType = BusinessType.FORCE)
    @DeleteMapping({"/{tokenId}"})
    public AjaxResult forceLogout(@PathVariable("tokenId") String tokenId) {
        redisCache.deleteObject("login_tokens:" + tokenId);
        return AjaxResult.success();
    }
}
