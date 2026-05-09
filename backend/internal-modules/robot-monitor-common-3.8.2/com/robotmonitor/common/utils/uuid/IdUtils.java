/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.utils.uuid;

import com.robotmonitor.common.utils.uuid.UUID;

public class IdUtils {
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    public static String simpleUUID() {
        return UUID.randomUUID().toString(true);
    }
}
