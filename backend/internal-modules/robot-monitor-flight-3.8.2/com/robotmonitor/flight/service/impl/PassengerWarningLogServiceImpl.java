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
import com.robotmonitor.flight.domain.PassengerWarningLog;
import com.robotmonitor.flight.mapper.PassengerWarningLogMapper;
import com.robotmonitor.flight.service.IPassengerWarningLogService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassengerWarningLogServiceImpl
implements IPassengerWarningLogService {
    @Autowired
    private PassengerWarningLogMapper passengerWarningLogMapper;

    @Override
    public PassengerWarningLog selectPassengerWarningLogById(Long id) {
        return this.passengerWarningLogMapper.selectPassengerWarningLogById(id);
    }

    @Override
    public List<PassengerWarningLog> selectPassengerWarningLogList(PassengerWarningLog passengerWarningLog) {
        return this.passengerWarningLogMapper.selectPassengerWarningLogList(passengerWarningLog);
    }

    @Override
    public int insertPassengerWarningLog(PassengerWarningLog passengerWarningLog) {
        passengerWarningLog.setCreateTime(DateUtils.getNowDate());
        return this.passengerWarningLogMapper.insertPassengerWarningLog(passengerWarningLog);
    }

    @Override
    public int updatePassengerWarningLog(PassengerWarningLog passengerWarningLog) {
        passengerWarningLog.setUpdateTime(DateUtils.getNowDate());
        return this.passengerWarningLogMapper.updatePassengerWarningLog(passengerWarningLog);
    }

    @Override
    public int deletePassengerWarningLogByIds(Long[] ids) {
        return this.passengerWarningLogMapper.deletePassengerWarningLogByIds(ids);
    }

    @Override
    public int deletePassengerWarningLogById(Long id) {
        return this.passengerWarningLogMapper.deletePassengerWarningLogById(id);
    }

    @Override
    public List<PassengerWarningLog> selectCurWarningLogList() {
        return this.passengerWarningLogMapper.selectCurWarningLogList();
    }
}
