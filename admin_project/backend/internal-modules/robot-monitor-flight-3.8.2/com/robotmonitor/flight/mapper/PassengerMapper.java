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
import com.robotmonitor.flight.domain.Passenger;
import com.robotmonitor.flight.domain.PassengerParam;
import com.robotmonitor.flight.domain.dto.FlightChangePassengerDTO;
import java.util.List;

public interface PassengerMapper {
    public Passenger selectPassengerById(Long var1);

    public List<Passenger> selectPassengerList(Passenger var1);

    public List<Passenger> selectPassengerList2(PassengerParam var1);

    public List<Passenger> selectPassengerList_Re(Passenger var1);

    @AutoFill(value=OperationType.INSERT)
    public int insertPassenger(Passenger var1);

    @AutoFill(value=OperationType.UPDATE)
    public int updatePassenger(Passenger var1);

    public int deletePassengerById(Long var1);

    public int deletePassengerByIds(Long[] var1);

    @AutoFill(value=OperationType.UPDATE)
    public int setPassengerGetOut(Long var1, String var2, Long var3);

    @AutoFill(value=OperationType.UPDATE)
    public int updatePassengerRegionAndStatus(Long var1, Long var2, String var3, String var4, String var5);

    public List<Passenger> selectPassengerWithUnflownFlights();

    public List<Passenger> selectPassengerOutgoingList(Passenger var1);

    public List<Passenger> selectPassengerInLoungeList(Passenger var1);

    @AutoFill(value=OperationType.UPDATE)
    public int updatePassengerCheckoutStatus(String var1, String var2, String var3, String var4);

    public Passenger selectPassengerByPid(String var1);

    public Passenger selectPassengerByReid(String var1);

    public List<FlightChangePassengerDTO> selectPassengerWithFlightChangeList(Passenger var1);
}
