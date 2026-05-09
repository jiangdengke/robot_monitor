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
import com.robotmonitor.flight.domain.FlightKafkaLog;
import com.robotmonitor.flight.mapper.FlightKafkaLogMapper;
import com.robotmonitor.flight.service.IFlightKafkaLogService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightKafkaLogServiceImpl
implements IFlightKafkaLogService {
    @Autowired
    private FlightKafkaLogMapper flightKafkaLogMapper;

    @Override
    public FlightKafkaLog selectFlightKafkaLogById(Long id) {
        return this.flightKafkaLogMapper.selectFlightKafkaLogById(id);
    }

    @Override
    public List<FlightKafkaLog> selectFlightKafkaLogList(FlightKafkaLog flightKafkaLog) {
        return this.flightKafkaLogMapper.selectFlightKafkaLogList(flightKafkaLog);
    }

    @Override
    public int insertFlightKafkaLog(FlightKafkaLog flightKafkaLog) {
        flightKafkaLog.setCreateTime(DateUtils.getNowDate());
        return this.flightKafkaLogMapper.insertFlightKafkaLog(flightKafkaLog);
    }

    @Override
    public int updateFlightKafkaLog(FlightKafkaLog flightKafkaLog) {
        return this.flightKafkaLogMapper.updateFlightKafkaLog(flightKafkaLog);
    }

    @Override
    public int deleteFlightKafkaLogByIds(Long[] ids) {
        return this.flightKafkaLogMapper.deleteFlightKafkaLogByIds(ids);
    }

    @Override
    public int deleteFlightKafkaLogById(Long id) {
        return this.flightKafkaLogMapper.deleteFlightKafkaLogById(id);
    }
}
