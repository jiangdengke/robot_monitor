package com.robotmonitor.web.controller.monitor;

import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.utils.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/monitor/cache"})
public class CacheController {
    private static final String[] DEFAULT_CACHE_NAMES = new String[] {
        "login_tokens:",
        "captcha_codes:",
        "sys_config:",
        "sys_dict:",
        "repeat_submit:",
        "rate_limit:",
        "robot_current_status:",
        "robot_current_task:",
        "robot_online_flag:"
    };

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @GetMapping
    public AjaxResult getInfo() {
        Properties info = redisTemplate.execute((RedisCallback<Properties>) connection -> connection.info());
        Properties commandStats = redisTemplate.execute((RedisCallback<Properties>) connection -> connection.info("commandstats"));
        Long dbSize = redisTemplate.execute((RedisCallback<Long>) connection -> connection.dbSize());
        Map<String, Object> result = new HashMap<>(3);
        result.put("info", info);
        result.put("dbSize", dbSize);
        List<Map<String, String>> pieList = new ArrayList<>();
        if (commandStats != null) {
            commandStats.stringPropertyNames().forEach(key -> {
                Map<String, String> data = new HashMap<>(2);
                String property = commandStats.getProperty(key);
                data.put("name", StringUtils.removeStart(key, "cmdstat_"));
                data.put("value", StringUtils.substringBetween(property, "calls=", ",usec"));
                pieList.add(data);
            });
        }
        result.put("commandStats", pieList);
        return AjaxResult.success(result);
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @GetMapping({"/getNames"})
    public AjaxResult getNames() {
        List<Map<String, String>> names = new ArrayList<>();
        for (String cacheName : DEFAULT_CACHE_NAMES) {
            if (hasKeys(cacheName)) {
                names.add(cacheName(cacheName, remark(cacheName)));
            }
        }
        if (names.isEmpty()) {
            for (String cacheName : DEFAULT_CACHE_NAMES) {
                names.add(cacheName(cacheName, remark(cacheName)));
            }
        }
        return AjaxResult.success(names);
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @GetMapping({"/getKeys/{cacheName}"})
    public AjaxResult getKeys(@PathVariable("cacheName") String cacheName) {
        Set<String> keys = redisTemplate.keys(cacheName + "*");
        List<String> sortedKeys = new ArrayList<>();
        if (keys != null) {
            sortedKeys.addAll(keys);
            sortedKeys.sort(String::compareTo);
        }
        return AjaxResult.success(sortedKeys);
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @GetMapping({"/getValue/{cacheName}/{cacheKey}"})
    public AjaxResult getValue(@PathVariable("cacheName") String cacheName, @PathVariable("cacheKey") String cacheKey) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("cacheName", cacheName);
        result.put("cacheKey", cacheKey);
        result.put("cacheValue", readValue(cacheKey));
        return AjaxResult.success(result);
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @DeleteMapping({"/clearCacheName/{cacheName}"})
    public AjaxResult clearCacheName(@PathVariable("cacheName") String cacheName) {
        Set<String> keys = redisTemplate.keys(cacheName + "*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @DeleteMapping({"/clearCacheKey/{cacheKey}"})
    public AjaxResult clearCacheKey(@PathVariable("cacheKey") String cacheKey) {
        redisTemplate.delete(cacheKey);
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @DeleteMapping({"/clearCacheAll"})
    public AjaxResult clearCacheAll() {
        Set<String> keys = redisTemplate.keys("*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
        return AjaxResult.success();
    }

    private boolean hasKeys(String cacheName) {
        Set<String> keys = redisTemplate.keys(cacheName + "*");
        return keys != null && !keys.isEmpty();
    }

    private Map<String, String> cacheName(String cacheName, String remark) {
        Map<String, String> item = new LinkedHashMap<>();
        item.put("cacheName", cacheName);
        item.put("remark", remark);
        return item;
    }

    private String remark(String cacheName) {
        return switch (cacheName) {
            case "login_tokens:" -> "用户登录令牌";
            case "captcha_codes:" -> "验证码";
            case "sys_config:" -> "系统配置";
            case "sys_dict:" -> "数据字典";
            case "repeat_submit:" -> "防重复提交";
            case "rate_limit:" -> "限流记录";
            case "robot_current_status:" -> "机器人当前状态";
            case "robot_current_task:" -> "机器人当前任务";
            case "robot_online_flag:" -> "机器人在线标记";
            default -> "业务缓存";
        };
    }

    private String readValue(String cacheKey) {
        DataType type = redisTemplate.type(cacheKey);
        if (DataType.LIST.equals(type)) {
            return String.valueOf(redisTemplate.opsForList().range(cacheKey, 0, -1));
        }
        if (DataType.SET.equals(type)) {
            return String.valueOf(redisTemplate.opsForSet().members(cacheKey));
        }
        if (DataType.HASH.equals(type)) {
            return String.valueOf(redisTemplate.opsForHash().entries(cacheKey));
        }
        if (DataType.ZSET.equals(type)) {
            return String.valueOf(redisTemplate.opsForZSet().range(cacheKey, 0, -1));
        }
        return String.valueOf(redisTemplate.opsForValue().get(cacheKey));
    }
}
