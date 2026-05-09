/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.cache.RemovalListener
 *  com.google.common.cache.RemovalNotification
 */
package com.robotmonitor.common.utils;

import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

class GuavaCacheUtils.1
implements RemovalListener<String, Object> {
    GuavaCacheUtils.1() {
    }

    public void onRemoval(RemovalNotification<String, Object> rn) {
        if (log.isInfoEnabled()) {
            // empty if block
        }
    }
}
