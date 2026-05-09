/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.service;

import com.robotmonitor.flight.domain.FlightWarning;
import java.util.List;

public interface IFlightWarningService {
    public FlightWarning selectFlightWarningById(Long var1);

    public List<FlightWarning> selectFlightWarningList(FlightWarning var1);

    public int insertFlightWarning(FlightWarning var1);

    public int updateFlightWarning(FlightWarning var1);

    public int deleteFlightWarningByIds(Long[] var1);

    public int deleteFlightWarningById(Long var1);

    public List<FlightWarning> selectCurrentFlightWarningList(String var1);
}
