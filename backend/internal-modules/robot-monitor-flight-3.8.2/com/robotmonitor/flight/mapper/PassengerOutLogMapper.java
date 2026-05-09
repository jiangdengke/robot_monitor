/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.mapper;

import com.robotmonitor.flight.domain.PassengerOutLog;
import java.util.List;

public interface PassengerOutLogMapper {
    public PassengerOutLog selectPassengerOutLogById(Long var1);

    public List<PassengerOutLog> selectPassengerOutLogList(PassengerOutLog var1);

    public int insertPassengerOutLog(PassengerOutLog var1);

    public int updatePassengerOutLog(PassengerOutLog var1);

    public int deletePassengerOutLogById(Long var1);

    public int deletePassengerOutLogByIds(Long[] var1);

    public PassengerOutLog selectPassengerOutLogByCtsId(String var1);
}
