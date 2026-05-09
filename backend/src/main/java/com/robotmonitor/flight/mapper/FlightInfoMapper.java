/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.mapper;

import com.robotmonitor.flight.domain.FlightInfo;
import com.robotmonitor.flight.domain.FlightParam;
import java.util.List;

public interface FlightInfoMapper {
    public FlightInfo selectFlightInfoByFlightId(String var1);

    public List<FlightInfo> selectFlightInfoList(FlightInfo var1);

    public int insertFlightInfo(FlightInfo var1);

    public int updateFlightInfo(FlightInfo var1);

    public int deleteFlightInfoByFlightId(String var1);

    public int deleteFlightInfoByFlightIds(Long[] var1);

    public List<FlightInfo> selectUnflownFlights();

    public List<FlightInfo> selectWillTakeOffFlights(FlightParam var1);
}
