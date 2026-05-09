/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.mapper;

import com.robotmonitor.flight.domain.FlightKafkaLog;
import java.util.List;

public interface FlightKafkaLogMapper {
    public FlightKafkaLog selectFlightKafkaLogById(Long var1);

    public List<FlightKafkaLog> selectFlightKafkaLogList(FlightKafkaLog var1);

    public int insertFlightKafkaLog(FlightKafkaLog var1);

    public int updateFlightKafkaLog(FlightKafkaLog var1);

    public int deleteFlightKafkaLogById(Long var1);

    public int deleteFlightKafkaLogByIds(Long[] var1);
}
