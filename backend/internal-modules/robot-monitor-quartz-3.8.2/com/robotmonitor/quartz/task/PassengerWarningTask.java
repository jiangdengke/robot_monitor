/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.enums.FlightChangeType
 *  com.robotmonitor.common.utils.DateUtils
 *  com.robotmonitor.flight.domain.FlightInfo
 *  com.robotmonitor.flight.domain.FlightParam
 *  com.robotmonitor.flight.domain.FlightWarning
 *  com.robotmonitor.flight.domain.Passenger
 *  com.robotmonitor.flight.domain.PassengerWarningLog
 *  com.robotmonitor.flight.service.IFlightInfoService
 *  com.robotmonitor.flight.service.IFlightWarningService
 *  com.robotmonitor.flight.service.IPassengerService
 *  com.robotmonitor.flight.service.IPassengerWarningLogService
 *  org.apache.commons.lang3.ObjectUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 */
package com.robotmonitor.quartz.task;

import com.robotmonitor.common.enums.FlightChangeType;
import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.flight.domain.FlightInfo;
import com.robotmonitor.flight.domain.FlightParam;
import com.robotmonitor.flight.domain.FlightWarning;
import com.robotmonitor.flight.domain.Passenger;
import com.robotmonitor.flight.domain.PassengerWarningLog;
import com.robotmonitor.flight.service.IFlightInfoService;
import com.robotmonitor.flight.service.IFlightWarningService;
import com.robotmonitor.flight.service.IPassengerService;
import com.robotmonitor.flight.service.IPassengerWarningLogService;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="PassengerWarningTask")
public class PassengerWarningTask {
    private static final Logger log = LoggerFactory.getLogger((String)"passenger-location-out-task");
    @Autowired
    private IPassengerService passengerService;
    @Autowired
    private IFlightInfoService flightInfoService;
    @Autowired
    private IPassengerWarningLogService warningLogService;
    @Autowired
    private IFlightWarningService flightWarningService;
    private final long EARLYTIME = 3000L;

    public void insertPassengerWarning() {
        try {
            PassengerWarningLog warninglog;
            log.info("\u5f00\u59cb\u6267\u884c\u65c5\u5ba2\u63d0\u9192\u5b9a\u65f6\u4efb\u52a1");
            ArrayList passengerWarningLogs = new ArrayList();
            FlightParam param = new FlightParam();
            param.setFlightDate(DateUtils.getDate());
            param.setCurrentTime(Long.getLong(DateUtils.dateTimeNow((String)"YYYYMMDDHHMMSS") + "00"));
            param.setEarlyTime(Long.valueOf(3000L));
            List flightInfoList = this.flightInfoService.selectWillTakeOffFlights(param);
            for (FlightInfo info : flightInfoList) {
                Passenger pParam = new Passenger();
                pParam.setFlightId(info.getFlightId());
                List pList = this.passengerService.selectPassengerList(pParam);
                for (Passenger p : pList) {
                    warninglog = new PassengerWarningLog();
                    warninglog.setPassengerId(p.getId());
                    warninglog.setWarningType(FlightChangeType.READY.getCode());
                    warninglog.setWarningInfo("\u5c0a\u656c\u7684\u65c5\u5ba2" + p.getUserName() + "\u60a8\u597d\uff0c\u60a8\u4e58\u5750\u7684" + info.getFlightNo() + "\u822a\u73ed\u5373\u5c06\u8d77\u98de\uff0c\u8bf7\u524d\u5f80" + info.getGateCd() + "\u767b\u673a\u53e3\u767b\u673a\u3002");
                    warninglog.setFlightId(info.getFlightId());
                    warninglog.setRegionId(p.getRegionId());
                    this.insertWarningLog(warninglog);
                }
            }
            List flightWarningList = this.flightWarningService.selectCurrentFlightWarningList(DateUtils.getDate());
            for (FlightWarning info : flightWarningList) {
                warninglog = new PassengerWarningLog();
                warninglog.setPassengerId(info.getPassengerId());
                warninglog.setWarningType(info.getWarningType());
                if (info.getWarningType().equals(FlightChangeType.GATE_CHANGE.getCode())) {
                    warninglog.setWarningInfo("\u5c0a\u656c\u7684\u65c5\u5ba2" + info.getUserName() + "\u60a8\u597d\uff0c\u60a8\u4e58\u5750\u7684" + info.getFlightNo() + "\u822a\u73ed\u767b\u673a\u53e3\u53d8\u66f4\u4e3a" + info.getChangeAfter() + "\uff0c\u8bf7\u524d\u5f80" + info.getChangeAfter() + "\u767b\u673a\u53e3\u767b\u673a\u3002");
                } else if (info.getWarningType().equals(FlightChangeType.TIME_CHANGE.getCode())) {
                    warninglog.setWarningInfo("\u5c0a\u656c\u7684\u65c5\u5ba2" + info.getUserName() + "\u60a8\u597d\uff0c\u60a8\u4e58\u5750\u7684" + info.getFlightNo() + "\u822a\u73ed\u8d77\u98de\u65f6\u95f4\u53d8\u66f4\u4e3a" + info.getChangeAfter() + "\uff0c\u8bf7\u6ce8\u610f\u767b\u673a\u65f6\u95f4\u3002");
                } else if (info.getWarningType().equals(FlightChangeType.CANCEL.getCode())) {
                    warninglog.setWarningInfo("\u5c0a\u656c\u7684\u65c5\u5ba2" + info.getUserName() + "\u60a8\u597d\uff0c\u975e\u5e38\u62b1\u6b49\u60a8\u4e58\u5750\u7684" + info.getFlightNo() + "\u822a\u73ed\u5df2\u88ab\u53d6\u6d88\u3002");
                }
                warninglog.setFlightId(info.getFlightId());
                warninglog.setRegionId(info.getRegionId());
                warninglog.setFlightWarningId(info.getId());
                this.insertWarningLog(warninglog);
            }
            log.info("\u5f00\u59cb\u6267\u884c\u65c5\u5ba2\u63d0\u9192\u5b9a\u65f6\u4efb\u52a1");
        }
        catch (Exception e) {
            log.error("\u6267\u884c\u65c5\u5ba2\u63d0\u9192\u5b9a\u65f6\u4efb\u52a1\u65f6\u53d1\u751f\u5f02\u5e38", (Throwable)e);
            throw e;
        }
    }

    private void insertWarningLog(PassengerWarningLog warninglog) {
        PassengerWarningLog logParam = new PassengerWarningLog();
        logParam.setFlightWarningId(warninglog.getFlightWarningId());
        logParam.setFlightId(warninglog.getFlightId());
        logParam.setPassengerId(warninglog.getPassengerId());
        logParam.setWarningType(warninglog.getWarningType());
        List log_old = this.warningLogService.selectPassengerWarningLogList(logParam);
        if (ObjectUtils.isNotEmpty((Object)log_old)) {
            return;
        }
        this.warningLogService.insertPassengerWarningLog(warninglog);
    }
}
