package com.robotmonitor.web.controller.monitor;

import com.robotmonitor.common.annotation.Log;
import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.page.TableDataInfo;
import com.robotmonitor.common.core.redis.RedisCache;
import com.robotmonitor.common.enums.BusinessType;
import com.robotmonitor.common.utils.poi.ExcelUtil;
import com.robotmonitor.system.domain.SysLogininfor;
import com.robotmonitor.system.service.ISysLogininforService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/monitor/logininfor"})
public class SysLogininforController extends BaseController {
    @Autowired
    private ISysLogininforService logininforService;

    @Autowired
    private RedisCache redisCache;

    @PreAuthorize("@ss.hasPermi('monitor:logininfor:list')")
    @GetMapping({"/list"})
    public TableDataInfo list(SysLogininfor logininfor) {
        startPage();
        List<SysLogininfor> list = logininforService.selectLogininforList(logininfor);
        return getDataTable(list);
    }

    @Log(title = "登录日志", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('monitor:logininfor:export')")
    @PostMapping({"/export"})
    public void export(HttpServletResponse response, SysLogininfor logininfor) {
        List<SysLogininfor> list = logininforService.selectLogininforList(logininfor);
        ExcelUtil<SysLogininfor> util = new ExcelUtil<>(SysLogininfor.class);
        util.exportExcel(response, list, "登录日志");
    }

    @PreAuthorize("@ss.hasPermi('monitor:logininfor:remove')")
    @Log(title = "登录日志", businessType = BusinessType.DELETE)
    @DeleteMapping({"/{infoIds}"})
    public AjaxResult remove(@PathVariable("infoIds") Long[] infoIds) {
        return toAjax(logininforService.deleteLogininforByIds(infoIds));
    }

    @PreAuthorize("@ss.hasPermi('monitor:logininfor:remove')")
    @Log(title = "登录日志", businessType = BusinessType.CLEAN)
    @DeleteMapping({"/clean"})
    public AjaxResult clean() {
        logininforService.cleanLogininfor();
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('monitor:logininfor:unlock')")
    @Log(title = "账户解锁", businessType = BusinessType.UPDATE)
    @GetMapping({"/unlock/{userName}"})
    public AjaxResult unlock(@PathVariable("userName") String userName) {
        Set<String> keys = new HashSet<>();
        collectKeys(keys, "pwd_err_cnt:" + userName);
        collectKeys(keys, "password_error:" + userName);
        collectKeys(keys, "login_fail:" + userName);
        collectKeys(keys, "login_error:" + userName);
        collectKeys(keys, "login_retry:" + userName);
        collectKeys(keys, "sys_user_pwd_err_cnt:" + userName);
        collectKeys(keys, "pwd_err_cnt:*" + userName + "*");
        collectKeys(keys, "password_error:*" + userName + "*");
        collectKeys(keys, "login_fail:*" + userName + "*");
        if (!keys.isEmpty()) {
            redisCache.deleteObject(keys);
        }
        return AjaxResult.success("用户" + userName + "解锁成功");
    }

    private void collectKeys(Set<String> keys, String pattern) {
        Collection<String> matchedKeys = redisCache.keys(pattern);
        if (matchedKeys != null && !matchedKeys.isEmpty()) {
            keys.addAll(matchedKeys);
        }
    }
}
