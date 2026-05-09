/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.config.ConfigRobot
 */
package com.robotmonitor.flight.service;

import com.robotmonitor.common.core.domain.config.ConfigRobot;
import com.robotmonitor.flight.domain.CollectInResponse2;
import com.robotmonitor.flight.domain.PassengerLog;
import java.util.List;

public interface IPassengerLogService {
    public PassengerLog selectPassengerLogById(Long var1);

    public List<PassengerLog> selectPassengerLogList(PassengerLog var1);

    public int insertPassengerLog(PassengerLog var1);

    public int updatePassengerLog(PassengerLog var1);

    public int deletePassengerLogByIds(Long[] var1);

    public int deletePassengerLogById(Long var1);

    public int insertGetInLog(ConfigRobot var1, CollectInResponse2 var2);

    public int insertGetOut(PassengerLog var1);
}
