/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.AutoFill
 *  com.robotmonitor.common.enums.OperationType
 */
package com.robotmonitor.flight.mapper;

import com.robotmonitor.common.annotation.AutoFill;
import com.robotmonitor.common.enums.OperationType;
import com.robotmonitor.flight.domain.PassengerWarningLog;
import java.util.List;

public interface PassengerWarningLogMapper {
    public PassengerWarningLog selectPassengerWarningLogById(Long var1);

    public List<PassengerWarningLog> selectPassengerWarningLogList(PassengerWarningLog var1);

    public int insertPassengerWarningLog(PassengerWarningLog var1);

    @AutoFill(value=OperationType.UPDATE)
    public int updatePassengerWarningLog(PassengerWarningLog var1);

    public int deletePassengerWarningLogById(Long var1);

    public int deletePassengerWarningLogByIds(Long[] var1);

    public List<PassengerWarningLog> selectCurWarningLogList();
}
