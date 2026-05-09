/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.mapper;

import com.robotmonitor.flight.domain.FlightChange;
import com.robotmonitor.flight.dto.CmdItemDto;
import java.util.List;

public interface FlightChangeMapper {
    public List<FlightChange> queryList();

    public List<CmdItemDto> queryCmdList();
}
