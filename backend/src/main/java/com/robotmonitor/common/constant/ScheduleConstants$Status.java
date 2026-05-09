/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.constant;

public static enum ScheduleConstants.Status {
    NORMAL("0"),
    PAUSE("1");

    private String value;

    private ScheduleConstants.Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
