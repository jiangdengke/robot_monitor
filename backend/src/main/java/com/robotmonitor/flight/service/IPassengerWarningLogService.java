/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.service;

import com.robotmonitor.flight.domain.PassengerWarningLog;
import java.util.List;

public interface IPassengerWarningLogService {
    public PassengerWarningLog selectPassengerWarningLogById(Long var1);

    public List<PassengerWarningLog> selectPassengerWarningLogList(PassengerWarningLog var1);

    public int insertPassengerWarningLog(PassengerWarningLog var1);

    public int updatePassengerWarningLog(PassengerWarningLog var1);

    public int deletePassengerWarningLogByIds(Long[] var1);

    public int deletePassengerWarningLogById(Long var1);

    public List<PassengerWarningLog> selectCurWarningLogList();
}
