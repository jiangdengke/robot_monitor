/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.annotation;

public static enum Excel.Align {
    AUTO(0),
    LEFT(1),
    CENTER(2),
    RIGHT(3);

    private final int value;

    private Excel.Align(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
