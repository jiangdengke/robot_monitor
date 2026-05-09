/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.utils.DateUtils
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.flight.service.impl;

import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.flight.domain.FlightGate;
import com.robotmonitor.flight.mapper.FlightGateMapper;
import com.robotmonitor.flight.service.IFlightGateService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightGateServiceImpl
implements IFlightGateService {
    @Autowired
    private FlightGateMapper flightGateMapper;

    @Override
    public FlightGate selectFlightGateByFlightId(String flightId) {
        return this.flightGateMapper.selectFlightGateByFlightId(flightId);
    }

    @Override
    public List<FlightGate> selectFlightGateList(FlightGate flightGate) {
        return this.flightGateMapper.selectFlightGateList(flightGate);
    }

    @Override
    public int insertFlightGate(FlightGate flightGate) {
        return this.flightGateMapper.insertFlightGate(flightGate);
    }

    @Override
    public int updateFlightGate(FlightGate flightGate) {
        flightGate.setUpdateTime(DateUtils.getNowDate());
        return this.flightGateMapper.updateFlightGate(flightGate);
    }

    @Override
    public int deleteFlightGateByFlightIds(String[] flightIds) {
        return this.flightGateMapper.deleteFlightGateByFlightIds(flightIds);
    }

    @Override
    public int deleteFlightGateByFlightId(String flightId) {
        return this.flightGateMapper.deleteFlightGateByFlightId(flightId);
    }
}
