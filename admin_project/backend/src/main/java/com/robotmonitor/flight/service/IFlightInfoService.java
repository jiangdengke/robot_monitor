/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.xml.bind.JAXBException
 */
package com.robotmonitor.flight.service;

import com.robotmonitor.flight.domain.FlightInfo;
import com.robotmonitor.flight.domain.FlightParam;
import java.util.List;
import javax.xml.bind.JAXBException;

public interface IFlightInfoService {
    public FlightInfo selectFlightInfoByFlightId(String var1);

    public List<FlightInfo> selectFlightInfoList(FlightInfo var1);

    public int insertFlightInfo(FlightInfo var1);

    public int updateFlightInfo(FlightInfo var1);

    public int deleteFlightInfoByFlightIds(Long[] var1);

    public int deleteFlightInfoByFlightId(String var1);

    public int addTmp(String var1) throws JAXBException;

    public List<FlightInfo> selectWillTakeOffFlights(FlightParam var1);
}
