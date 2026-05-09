/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.annotation;

public static enum Excel.ColumnType {
    NUMERIC(0),
    STRING(1),
    IMAGE(2);

    private final int value;

    private Excel.ColumnType(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
