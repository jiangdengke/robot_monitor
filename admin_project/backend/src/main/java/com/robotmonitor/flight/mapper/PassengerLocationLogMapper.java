/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.mapper;

import com.robotmonitor.flight.domain.PassengerLocationLog;
import java.util.List;

public interface PassengerLocationLogMapper {
    public PassengerLocationLog selectPassengerLocationLogById(Long var1);

    public List<PassengerLocationLog> selectPassengerLocationLogList(PassengerLocationLog var1);

    public int insertPassengerLocationLog(PassengerLocationLog var1);

    public int updatePassengerLocationLog(PassengerLocationLog var1);

    public int deletePassengerLocationLogById(Long var1);

    public int deletePassengerLocationLogByIds(Long[] var1);

    public PassengerLocationLog selectPassengerLocationLogByCtsId(String var1);

    public int countCurrentPassengers();

    public int countDepartedPassengers();

    public int countVisitors();

    public List<PassengerLocationLog> selectCurrentPassengerDetails();

    public List<PassengerLocationLog> selectDepartedPassengerDetails();

    public List<PassengerLocationLog> selectVisitorDetails();

    public PassengerLocationLog selectPassengerLocationLogByPid(String var1);

    public List<PassengerLocationLog> findUnrecognizedPassenger();

    public List<PassengerLocationLog> selectCurrentPassengerList(String var1);

    public Long getCountByArea(Long var1);
}
