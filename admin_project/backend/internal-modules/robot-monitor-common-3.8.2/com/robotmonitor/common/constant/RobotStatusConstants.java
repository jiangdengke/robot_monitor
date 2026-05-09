/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.constant;

import java.util.Map;

public class RobotStatusConstants {
    public static final String CHARGING_STATE_CHARGE = "1";
    public static final String CHARGING_STATE_NOT_CHARGE = "0";
    public static final String WORKING_STATE_WORK = "1";
    public static final String WORKING_STATE_NOT_WORK = "0";
    public static final String STANDBY_STATE_STANDBY = "1";
    public static final String STANDBY_STATE_NOT_STANDBY = "0";
    public static final String ERROR_STATE_ERROR = "1";
    public static final String ERROR_STATE_NO_ERROR = "0";
    public static final String ROBOT_HTTP_CMD_STATE_IN_USE = "0";
    public static final String ROBOT_HTTP_CMD_STATE_NOT_IN_USE = "1";
    public static final long ROBOT_ONLINE_STATUS_OFFLINE = 0L;
    public static final long ROBOT_ONLINE_STATUS_CMD_ONLINE = 1L;
    public static final long ROBOT_ONLINE_STATUS_VOICE_ONLINE = 2L;
    public static final Map<String, Long> ROBOT_ONLINE_STATUS_MAP = Map.of("cmd", 1L, "sound", 2L);
}
