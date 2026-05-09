/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.flight.domain.FlightInfo
 *  com.robotmonitor.flight.domain.FlightWarning
 *  com.robotmonitor.flight.service.IFlightInfoService
 *  com.robotmonitor.flight.service.IFlightWarningService
 *  io.jsonwebtoken.lang.Collections
 *  org.apache.logging.log4j.util.Strings
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.ai.service.impl;

import com.robotmonitor.ai.domain.AiFlightChange;
import com.robotmonitor.ai.domain.AiFlightInfo;
import com.robotmonitor.ai.service.AiFlightService;
import com.robotmonitor.flight.domain.FlightInfo;
import com.robotmonitor.flight.domain.FlightWarning;
import com.robotmonitor.flight.service.IFlightInfoService;
import com.robotmonitor.flight.service.IFlightWarningService;
import io.jsonwebtoken.lang.Collections;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AiFlightServiceImpl
implements AiFlightService {
    private static final Logger log = LoggerFactory.getLogger(AiFlightServiceImpl.class);
    @Autowired
    private IFlightInfoService flightInfoService;
    @Autowired
    private IFlightWarningService flightWarningService;

    @Override
    public AiFlightInfo findFlight(String flightNo, String flightDate) {
        log.info("flightNo:{}, flightDate:{}", (Object)flightNo, (Object)flightDate);
        if (Strings.isBlank((String)flightNo)) {
            return null;
        }
        if (Strings.isBlank((String)flightDate)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            flightDate = sdf.format(new Date());
        }
        String airlineCode = flightNo.substring(0, 2);
        String flightNumber = flightNo.substring(2);
        FlightInfo queryRequest = new FlightInfo();
        queryRequest.setAirlineCd(airlineCode);
        queryRequest.setFlightNo(flightNumber);
        queryRequest.setScheExecDate(flightDate);
        List flightInfos = this.flightInfoService.selectFlightInfoList(queryRequest);
        if (Collections.isEmpty((Collection)flightInfos)) {
            return null;
        }
        FlightInfo flightInfo = (FlightInfo)flightInfos.get(0);
        FlightWarning warningQueryRequest = new FlightWarning();
        warningQueryRequest.setFlightId(flightInfo.getFlightId());
        List flightWarnings = this.flightWarningService.selectFlightWarningList(warningQueryRequest);
        ArrayList<AiFlightChange> flightChanges = new ArrayList<AiFlightChange>();
        if (!Collections.isEmpty((Collection)flightWarnings)) {
            for (FlightWarning flightWarning : flightWarnings) {
                flightChanges.add(new AiFlightChange(flightWarning));
            }
        }
        return new AiFlightInfo(flightInfo, flightChanges);
    }
}
