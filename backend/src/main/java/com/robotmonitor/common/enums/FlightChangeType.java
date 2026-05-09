/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.enums;

public enum FlightChangeType {
    TIME_CHANGE("timeChange", "\u8d77\u98de\u65f6\u95f4\u53d8\u66f4"),
    READY("ready", "\u5373\u5c06\u8d77\u98de"),
    GATE_CHANGE("gateChange", "\u767b\u673a\u53e3\u53d8\u66f4"),
    CANCEL("cancel", "\u822a\u73ed\u53d6\u6d88");

    private final String code;
    private final String info;

    private FlightChangeType(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return this.code;
    }

    public String getInfo() {
        return this.info;
    }
}
