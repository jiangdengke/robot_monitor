/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.cache.Cache
 *  com.google.common.cache.CacheBuilder
 *  com.google.common.cache.RemovalListener
 *  com.google.common.cache.RemovalNotification
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.util.StringUtils
 */
package com.robotmonitor.common.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class GuavaCacheUtils {
    private static final Logger log = LoggerFactory.getLogger(GuavaCacheUtils.class);
    private static Cache<String, Object> cache = CacheBuilder.newBuilder().maximumSize(10000L).expireAfterWrite(24L, TimeUnit.HOURS).initialCapacity(10).removalListener((RemovalListener)new RemovalListener<String, Object>(){

        public void onRemoval(RemovalNotification<String, Object> rn) {
            if (log.isInfoEnabled()) {
                // empty if block
            }
        }
    }).build();

    public static Object get(String key) {
        return StringUtils.hasText((String)key) ? cache.getIfPresent((Object)key) : null;
    }

    public static void put(String key, Object value) {
        if (StringUtils.hasText((String)key) && value != null) {
            cache.put((Object)key, value);
        }
    }

    public static void remove(String key) {
        if (StringUtils.hasText((String)key)) {
            cache.invalidate((Object)key);
        }
    }

    public static void remove(List<String> keys) {
        if (keys != null && keys.size() > 0) {
            cache.invalidateAll(keys);
        }
    }

    public static void removeAll() {
        cache.invalidateAll();
    }
}
