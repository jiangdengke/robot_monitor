/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.config.domain.RecognitionResult
 */
package com.robotmonitor.flight.service;

import com.robotmonitor.config.domain.RecognitionResult;
import com.robotmonitor.flight.domain.AuthResponse;
import com.robotmonitor.flight.domain.BarCodeRespons;
import com.robotmonitor.flight.domain.CollectInParam;
import com.robotmonitor.flight.domain.CollectInParam2;
import com.robotmonitor.flight.domain.CollectInResponse;
import com.robotmonitor.flight.domain.CollectInResponse2;
import com.robotmonitor.flight.domain.Passenger;
import com.robotmonitor.flight.domain.PassengerParam;
import com.robotmonitor.flight.domain.PassengerStatistics;
import com.robotmonitor.flight.domain.Result;
import com.robotmonitor.flight.domain.dto.FlightChangePassengerDTO;
import java.util.List;

public interface IPassengerService {
    public AuthResponse getAuth(String var1);

    public BarCodeRespons barCode(String var1, String var2, String var3);

    public BarCodeRespons barCodeForTest(String var1);

    public Result<CollectInResponse> passengerGetIn(CollectInParam var1);

    public CollectInResponse2 barCode2(CollectInParam2 var1);

    public void passengerGetIn2(String var1, CollectInResponse2 var2);

    public int setPassengerGetOut(String var1, String var2, Long var3);

    public int updatePassengerRegionAndStatus(String var1, Long var2, String var3, String var4, String var5);

    public RecognitionResult findPassage(String var1);

    public Passenger selectPassengerById(Long var1);

    public List<Passenger> selectPassengerList(Passenger var1);

    public int insertPassenger(Passenger var1);

    public int updatePassenger(Passenger var1);

    public int deletePassengerByIds(Long[] var1);

    public List<Passenger> selectPassengerOutgoingList(Passenger var1);

    public List<Passenger> selectPassengerInLoungeList(Passenger var1);

    public PassengerStatistics getPassengerStatistics();

    public List<FlightChangePassengerDTO> selectPassengerWithFlightChangeList(Passenger var1);

    public List<Passenger> selectPassenger(PassengerParam var1);
}
