/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.mapper;

import com.robotmonitor.flight.domain.PassengerLog;
import java.util.List;

public interface PassengerLogMapper {
    public PassengerLog selectPassengerLogById(Long var1);

    public List<PassengerLog> selectPassengerLogList(PassengerLog var1);

    public int insertPassengerLog(PassengerLog var1);

    public int updatePassengerLog(PassengerLog var1);

    public int deletePassengerLogById(Long var1);

    public int deletePassengerLogByIds(Long[] var1);
}
