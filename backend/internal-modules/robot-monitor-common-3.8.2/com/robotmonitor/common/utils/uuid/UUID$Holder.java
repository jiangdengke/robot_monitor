/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.utils.uuid;

import com.robotmonitor.common.utils.uuid.UUID;
import java.security.SecureRandom;

private static class UUID.Holder {
    static final SecureRandom numberGenerator = UUID.getSecureRandom();

    private UUID.Holder() {
    }
}
