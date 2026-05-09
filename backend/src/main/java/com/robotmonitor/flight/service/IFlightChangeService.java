/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.service;

import com.robotmonitor.flight.domain.FlightChange;
import com.robotmonitor.flight.dto.CmdDto;
import java.util.List;

public interface IFlightChangeService {
    public List<FlightChange> queryList();

    public List<CmdDto> queryCmdList();
}
