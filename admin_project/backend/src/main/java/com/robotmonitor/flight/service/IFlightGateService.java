/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.service;

import com.robotmonitor.flight.domain.FlightGate;
import java.util.List;

public interface IFlightGateService {
    public FlightGate selectFlightGateByFlightId(String var1);

    public List<FlightGate> selectFlightGateList(FlightGate var1);

    public int insertFlightGate(FlightGate var1);

    public int updateFlightGate(FlightGate var1);

    public int deleteFlightGateByFlightIds(String[] var1);

    public int deleteFlightGateByFlightId(String var1);
}
