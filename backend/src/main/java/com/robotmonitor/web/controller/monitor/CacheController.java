/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.utils.StringUtils
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.data.redis.core.RedisTemplate
 *  org.springframework.security.access.prepost.PreAuthorize
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.robotmonitor.web.controller.monitor;

import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.utils.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/monitor/cache"})
public class CacheController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PreAuthorize(value="@ss.hasPermi('monitor:cache:list')")
    @GetMapping
    public AjaxResult getInfo() throws Exception {
        Properties info = (Properties)this.redisTemplate.execute((RedisCallback<Properties>)(connection -> connection.info()));
        Properties commandStats = (Properties)this.redisTemplate.execute((RedisCallback<Properties>)(connection -> connection.info("commandstats")));
        Object dbSize = this.redisTemplate.execute((RedisCallback<Long>)(connection -> connection.dbSize()));
        HashMap<String, Object> result = new HashMap<String, Object>(3);
        result.put("info", info);
        result.put("dbSize", dbSize);
        ArrayList<Map<String, String>> pieList = new ArrayList<Map<String, String>>();
        commandStats.stringPropertyNames().forEach(key -> {
            HashMap<String, String> data = new HashMap<String, String>(2);
            String property = commandStats.getProperty((String)key);
            data.put("name", StringUtils.removeStart((String)key, (String)"cmdstat_"));
            data.put("value", StringUtils.substringBetween((String)property, (String)"calls=", (String)",usec"));
            pieList.add(data);
        });
        result.put("commandStats", pieList);
        return AjaxResult.success(result);
    }
}
