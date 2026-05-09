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
import com.robotmonitor.flight.domain.FlightWarning;
import com.robotmonitor.flight.mapper.FlightWarningMapper;
import com.robotmonitor.flight.service.IFlightWarningService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightWarningServiceImpl
implements IFlightWarningService {
    @Autowired
    private FlightWarningMapper flightWarningMapper;

    @Override
    public FlightWarning selectFlightWarningById(Long id) {
        return this.flightWarningMapper.selectFlightWarningById(id);
    }

    @Override
    public List<FlightWarning> selectFlightWarningList(FlightWarning flightWarning) {
        return this.flightWarningMapper.selectFlightWarningList(flightWarning);
    }

    @Override
    public int insertFlightWarning(FlightWarning flightWarning) {
        flightWarning.setCreateTime(DateUtils.getNowDate());
        return this.flightWarningMapper.insertFlightWarning(flightWarning);
    }

    @Override
    public int updateFlightWarning(FlightWarning flightWarning) {
        return this.flightWarningMapper.updateFlightWarning(flightWarning);
    }

    @Override
    public int deleteFlightWarningByIds(Long[] ids) {
        return this.flightWarningMapper.deleteFlightWarningByIds(ids);
    }

    @Override
    public int deleteFlightWarningById(Long id) {
        return this.flightWarningMapper.deleteFlightWarningById(id);
    }

    @Override
    public List<FlightWarning> selectCurrentFlightWarningList(String dNow) {
        return this.flightWarningMapper.selectCurrentFlightWarningList(dNow);
    }
}
