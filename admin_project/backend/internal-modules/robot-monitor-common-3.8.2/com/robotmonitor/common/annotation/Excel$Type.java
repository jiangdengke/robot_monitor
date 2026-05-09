/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.annotation;

public static enum Excel.Type {
    ALL(0),
    EXPORT(1),
    IMPORT(2);

    private final int value;

    private Excel.Type(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
