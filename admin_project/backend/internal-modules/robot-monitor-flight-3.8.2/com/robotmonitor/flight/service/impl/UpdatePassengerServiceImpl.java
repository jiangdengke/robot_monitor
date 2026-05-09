/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.config.ConfigRegion
 *  com.robotmonitor.common.utils.DateUtils
 *  com.robotmonitor.common.utils.StringUtils
 *  com.robotmonitor.config.domain.ConfigDevice
 *  com.robotmonitor.config.domain.RecognitionResult
 *  com.robotmonitor.config.domain.deepglint.changelist.ChangeListPerson
 *  com.robotmonitor.config.domain.deepglint.changelist.ChangeListRequest
 *  com.robotmonitor.config.domain.deepglint.changelist.ChangeListResponse
 *  com.robotmonitor.config.mapper.ConfigDeviceMapper
 *  com.robotmonitor.config.service.IDeepGlintService
 *  com.robotmonitor.config.service.IRegionMatchService
 *  io.jsonwebtoken.lang.Collections
 *  org.apache.logging.log4j.util.Strings
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.flight.service.impl;

import com.robotmonitor.common.core.domain.config.ConfigRegion;
import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.config.domain.ConfigDevice;
import com.robotmonitor.config.domain.RecognitionResult;
import com.robotmonitor.config.domain.deepglint.changelist.ChangeListPerson;
import com.robotmonitor.config.domain.deepglint.changelist.ChangeListRequest;
import com.robotmonitor.config.domain.deepglint.changelist.ChangeListResponse;
import com.robotmonitor.config.mapper.ConfigDeviceMapper;
import com.robotmonitor.config.service.IDeepGlintService;
import com.robotmonitor.config.service.IRegionMatchService;
import com.robotmonitor.flight.domain.Passenger;
import com.robotmonitor.flight.domain.PassengerLocationLog;
import com.robotmonitor.flight.domain.PassengerLog;
import com.robotmonitor.flight.mapper.PassengerLocationLogMapper;
import com.robotmonitor.flight.mapper.PassengerMapper;
import com.robotmonitor.flight.service.IPassengerLogService;
import com.robotmonitor.flight.service.IPassengerService;
import com.robotmonitor.flight.service.IUpdatePassengerService;
import io.jsonwebtoken.lang.Collections;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdatePassengerServiceImpl
implements IUpdatePassengerService {
    @Autowired
    private PassengerMapper passengerMapper;
    @Autowired
    private IPassengerService passengerService;
    @Autowired
    private ConfigDeviceMapper configDeviceMapper;
    @Autowired
    private PassengerLocationLogMapper passengerLocationLogMapper;
    @Autowired
    private IRegionMatchService regionMatchService;
    @Autowired
    private IDeepGlintService deepGlintService;
    @Autowired
    private IPassengerLogService iPassengerLogService;
    private static final String IS_OUT_IN = "0";
    private static final String IS_OUT_OUT = "1";
    private static final Logger log = LoggerFactory.getLogger((String)"passenger-location-out-task");

    @Override
    public void updatePassengerLocation(String pId, Set<String> exitDeviceIds, String logicDeviceId, boolean hasTags, Long captureTime, Integer captureX, Integer captureY, Integer captureWidth, Integer captureHeight, Integer origImageWidth, Integer origImageHeight, Long cts, String origImageUrl, String registerUrl, String reid) {
        ConfigRegion region;
        Passenger passenger = new Passenger();
        passenger.setPid(pId);
        String isOut = exitDeviceIds.contains(logicDeviceId) ? IS_OUT_OUT : IS_OUT_IN;
        boolean isRegistered = false;
        if (hasTags) {
            Passenger p = this.passengerMapper.selectPassengerByPid(pId);
            if (null != p) {
                isRegistered = true;
                passenger = p;
            } else if (Strings.isNotBlank((String)reid) && null != (p = this.passengerMapper.selectPassengerByReid(reid))) {
                isRegistered = true;
                passenger = p;
                passenger.setPid(pId);
            }
        }
        RecognitionResult recognitionResult = new RecognitionResult();
        ConfigRegion configRegion = this.regionMatchService.matchRegion(pId, logicDeviceId, captureTime, captureX, captureY, captureWidth, captureHeight, origImageWidth, origImageHeight);
        recognitionResult.setRegion(configRegion);
        ConfigDevice configDevice = this.configDeviceMapper.selectConfigDeviceByDeepGlintDeviceId(logicDeviceId);
        recognitionResult.setConfigDevice(configDevice);
        recognitionResult.setCts("" + cts);
        recognitionResult.setOrigImageUrl(origImageUrl);
        if (hasTags) {
            recognitionResult.setRegisterImageUrl(registerUrl);
            passenger.setReid(reid);
        }
        recognitionResult.setRecognitionType("face");
        ConfigRegion configRegion2 = region = recognitionResult != null ? recognitionResult.getRegion() : null;
        if (isRegistered) {
            int result = 0;
            if (region != null) {
                Long regionIdForUpdate = null;
                regionIdForUpdate = region.getId();
                log.info("[PID-{}-LOCATION] \u627e\u5230\u6709\u6548\u4f4d\u7f6e\uff0c\u533a\u57dfID: {}\uff0c\u7c7b\u578b: {}\uff0c\u65f6\u95f4\u6233: {}", new Object[]{pId, IS_OUT_OUT.equals(isOut) ? "\u51fa\u53e3" : (region != null ? region.getId() : "null"), recognitionResult.getRecognitionType(), recognitionResult.getCts()});
                if (IS_OUT_OUT.equals(isOut)) {
                    this.passengerService.setPassengerGetOut(pId, recognitionResult.getOrigImageUrl(), regionIdForUpdate);
                } else if (regionIdForUpdate != null) {
                    result = this.passengerService.updatePassengerRegionAndStatus(pId, regionIdForUpdate, IS_OUT_OUT, recognitionResult.getOrigImageUrl(), registerUrl);
                }
                if (result > 0) {
                    log.info("[PID-{}-LOCATION] \u66f4\u65b0\u65c5\u5ba2 {} \u4f4d\u7f6e\u4fe1\u606f\u6210\u529f\uff0c\u4f4d\u7f6eID: {}", new Object[]{pId, passenger.getUserName(), IS_OUT_OUT.equals(isOut) ? "\u51fa\u53e3" : (region != null ? region.getId() : "null")});
                } else {
                    log.info("[PID-{}-LOCATION] \u66f4\u65b0\u65c5\u5ba2 {} \u4f4d\u7f6e\u4fe1\u606f\u5931\u8d25", (Object)pId, (Object)passenger.getUserName());
                }
            } else {
                log.info("[PID-{}-LOCATION] \u672a\u627e\u5230\u65c5\u5ba2 {} \u7684\u4f4d\u7f6e\u4fe1\u606f", (Object)pId, (Object)passenger.getUserName());
            }
        }
        this.insertGetOut(passenger, isOut);
        this.logPassengerLocation(passenger, recognitionResult, isOut, captureTime);
    }

    private void insertGetOut(Passenger passenger, String isOut) {
        try {
            if (IS_OUT_OUT.equals(isOut)) {
                PassengerLog passengerLog = new PassengerLog();
                passengerLog.setRobotId(passenger.getRobotId());
                passengerLog.setPId(passenger.getPid());
                passengerLog.setRoomCode(passenger.getRoomCode());
                passengerLog.setCollectData(passenger.getOrigImageUrl());
                this.iPassengerLogService.insertGetOut(passengerLog);
            }
        }
        catch (Exception e) {
            log.error("insertGetOut failed: {}", (Object)e.getMessage());
        }
    }

    @Override
    public void mergePassenger() {
        log.info("\u5f00\u59cb\u5408\u5e76\u672a\u77e5\u65c5\u5ba2");
        List<PassengerLocationLog> passengerLocationLogs = this.passengerLocationLogMapper.findUnrecognizedPassenger();
        log.info("\u5f53\u524d\u672a\u8bc6\u522b\u65c5\u5ba2\u6570\u4e3a\uff1a{}", null == passengerLocationLogs ? IS_OUT_IN : Integer.valueOf(passengerLocationLogs.size()));
        if (!Collections.isEmpty(passengerLocationLogs)) {
            for (PassengerLocationLog passengerLocationLog : passengerLocationLogs) {
                ChangeListRequest changeListRequest = new ChangeListRequest();
                changeListRequest.setOriginPersonID(passengerLocationLog.getPid());
                ChangeListResponse changeListResponse = this.deepGlintService.personChangelist(changeListRequest);
                if (1 == changeListResponse.getCode() && !Collections.isEmpty((Collection)changeListResponse.getData().getPersons())) {
                    Passenger passenger;
                    log.info("\u67e5\u8be2\u5230\u5f53\u524dPID({})\u7684\u5408\u5e76\u5386\u53f2", (Object)passengerLocationLog.getPid());
                    Optional<ChangeListPerson> person = changeListResponse.getData().getPersons().stream().filter(changeListPerson -> changeListPerson.getOriginPersonID().equals(passengerLocationLog.getPid())).findFirst();
                    if (!person.isPresent()) continue;
                    String mergePersonId = person.get().getMergedPersonID();
                    if (mergePersonId.equals(passengerLocationLog.getPid())) {
                        log.info("\u5408\u5e76ID\u4e0e\u5f53\u524d\u76f8\u540c\uff0c\u5f53\u524dPID({})\u5df2\u662f\u6700\u65b0\uff0c\u4e0d\u9700\u8981\u4fee\u6539", (Object)passengerLocationLog.getPid());
                        continue;
                    }
                    log.info("\u67e5\u8be2\u662f\u5426\u5b58\u5728\u6700\u7ec8\u5408\u5e76\u540e\u7684PID({}),\u65b0PID({})", (Object)passengerLocationLog.getPid(), (Object)mergePersonId);
                    PassengerLocationLog needMergePassenger = this.passengerLocationLogMapper.selectPassengerLocationLogByPid(mergePersonId);
                    if (null != needMergePassenger) {
                        log.info("\u67e5\u8be2\u5230\u9700\u8981\u5408\u5e76\u7684PID({})", (Object)passengerLocationLog.getPid());
                        if (needMergePassenger.getCreateTime().after(passengerLocationLog.getCreateTime())) {
                            log.info("\u9700\u8981\u5408\u5e76\u7684PID({}/{})\u6bd4\u5f53\u524dPID({}/{})\u65b0\uff0c\u5220\u9664\u5f53\u524d\u7684PID", new Object[]{needMergePassenger.getPid(), needMergePassenger.getCreateTime(), passengerLocationLog.getPid(), passengerLocationLog.getCreateTime()});
                            this.passengerLocationLogMapper.deletePassengerLocationLogById(passengerLocationLog.getId());
                            continue;
                        }
                        log.info("\u9700\u8981\u5408\u5e76\u7684PID({}/{})\u6bd4\u5f53\u524dPID({}/{})\u65e7\uff0c\u8865\u5145\u66f4\u65b0\u5f53\u524dPID\uff0c\u5220\u9664\u5408\u5e76\u7684PID\uff0c\u540c\u65f6\u9700\u8981\u66f4\u65b0passenger", new Object[]{needMergePassenger.getPid(), needMergePassenger.getCreateTime(), passengerLocationLog.getPid(), passengerLocationLog.getCreateTime()});
                        passengerLocationLog.setReid(needMergePassenger.getReid());
                        passengerLocationLog.setPassengerId(needMergePassenger.getPassengerId());
                        passengerLocationLog.setUserName(needMergePassenger.getUserName());
                        passengerLocationLog.setRoomCode(needMergePassenger.getRoomCode());
                        passengerLocationLog.setFlightNo(needMergePassenger.getFlightNo());
                        passengerLocationLog.setFlightDate(needMergePassenger.getFlightDate());
                        passengerLocationLog.setRegionId(needMergePassenger.getRegionId());
                        passengerLocationLog.setRegionName(needMergePassenger.getRegionName());
                        passengerLocationLog.setCoordinate(needMergePassenger.getCoordinate());
                        passengerLocationLog.setPid(needMergePassenger.getPid());
                        this.passengerLocationLogMapper.updatePassengerLocationLog(passengerLocationLog);
                        this.passengerLocationLogMapper.deletePassengerLocationLogById(needMergePassenger.getId());
                        if (needMergePassenger.getPassengerId() == null || (passenger = this.passengerMapper.selectPassengerById(Long.valueOf(needMergePassenger.getPassengerId()))) == null) continue;
                        this.passengerService.updatePassengerRegionAndStatus(passenger.getPid(), passenger.getRegionId(), passenger.getStatus(), passenger.getOrigImageUrl(), passenger.getRegisterImageUrl());
                        continue;
                    }
                    log.info("\u672a\u67e5\u8be2\u5230\u9700\u8981\u5408\u5e76\u7684PID({})\uff0c\u66f4\u65b0\u5f53\u524dPID({})", (Object)mergePersonId, (Object)passengerLocationLog.getPid());
                    if (passengerLocationLog.getPassengerId() != null && (passenger = this.passengerMapper.selectPassengerById(Long.valueOf(passengerLocationLog.getPassengerId()))) != null) {
                        passenger.setPid(mergePersonId);
                        this.passengerMapper.updatePassenger(passenger);
                    }
                    passengerLocationLog.setPid(mergePersonId);
                    this.passengerLocationLogMapper.updatePassengerLocationLog(passengerLocationLog);
                    continue;
                }
                log.error("\u8c03\u7528\u683c\u6797\u63a5\u53e3\u5f02\u5e38\uff0ccode:{}, message{}", (Object)changeListResponse.getCode(), (Object)changeListResponse.getMsg());
            }
        }
    }

    private void logPassengerLocation(Passenger passenger, RecognitionResult recognitionResult, String isOut, Long captureTime) {
        try {
            String pId = passenger.getPid();
            boolean hasRegion = null != recognitionResult.getRegion();
            log.info("[PID-{}-LOCATION] \u5f00\u59cb\u8bb0\u5f55\u65c5\u5ba2 {} \u4f4d\u7f6e\u65e5\u5fd7\uff0c\u533a\u57df: {}\uff0c\u8bbe\u5907: {}", new Object[]{pId, passenger.getUserName(), hasRegion ? recognitionResult.getRegion().getRegionName() : null, recognitionResult.getConfigDevice().getDeviceName()});
            PassengerLocationLog logEntry = new PassengerLocationLog();
            logEntry.setReid(passenger.getReid());
            logEntry.setPid(passenger.getPid());
            logEntry.setPassengerId(null != passenger.getId() ? String.valueOf(passenger.getId()) : null);
            logEntry.setUserName(passenger.getUserName());
            logEntry.setRoomCode(passenger.getRoomCode());
            logEntry.setFlightNo(passenger.getFlightNo());
            logEntry.setFlightDate(DateUtils.parseDate((Object)passenger.getFlightDate()));
            if (hasRegion) {
                logEntry.setRegionId(recognitionResult.getRegion().getId().intValue());
                logEntry.setRegionName(StringUtils.isNotBlank((CharSequence)recognitionResult.getRegion().getRemark()) ? recognitionResult.getRegion().getRemark() : recognitionResult.getRegion().getRegionName());
                logEntry.setCoordinate(recognitionResult.getRegion().getCoordinate());
            }
            logEntry.setDeviceId("" + recognitionResult.getConfigDevice().getId());
            logEntry.setDeviceName(recognitionResult.getConfigDevice().getDeviceName());
            logEntry.setDeepGlintDeviceId(recognitionResult.getConfigDevice().getDeepGlintDeviceId());
            logEntry.setCreateTime(new Date(captureTime));
            logEntry.setRecognitionType(recognitionResult.getRecognitionType());
            logEntry.setCts(recognitionResult.getCts());
            logEntry.setOriImageUrl(recognitionResult.getOrigImageUrl());
            logEntry.setRegisterImageUrl(recognitionResult.getRegisterImageUrl());
            logEntry.setIsOut(isOut);
            PassengerLocationLog originalPassengerLocationLog = this.passengerLocationLogMapper.selectPassengerLocationLogByPid(passenger.getPid());
            int result = 0;
            if (null == originalPassengerLocationLog) {
                result = this.passengerLocationLogMapper.insertPassengerLocationLog(logEntry);
            } else if (originalPassengerLocationLog.getCreateTime().before(logEntry.getCreateTime())) {
                logEntry.setId(originalPassengerLocationLog.getId());
                result = this.passengerLocationLogMapper.updatePassengerLocationLog(logEntry);
            } else {
                result = 1;
                log.info("[PID-{}-LOCATION] \u65c5\u5ba2 {} \u4f4d\u7f6e\u65e5\u5fd7\u5df2\u5b58\u5728\u66f4\u65b0\u6570\u636e\uff0c\u8df3\u8fc7\u66f4\u65b0", (Object)pId, (Object)passenger.getUserName());
            }
            if (result > 0) {
                log.info("[PID-{}-LOCATION] \u65c5\u5ba2 {} \u4f4d\u7f6e\u65e5\u5fd7\u8bb0\u5f55\u6210\u529f\uff0ccts: {}", new Object[]{pId, passenger.getUserName(), recognitionResult.getCts()});
            } else {
                log.info("[PID-{}-LOCATION] \u65c5\u5ba2 {} \u4f4d\u7f6e\u65e5\u5fd7\u8bb0\u5f55\u5931\u8d25", (Object)pId, (Object)passenger.getUserName());
            }
        }
        catch (Exception e) {
            log.error("[PID-{}-LOCATION] \u8bb0\u5f55\u65c5\u5ba2 {} \u4f4d\u7f6e\u65e5\u5fd7\u65f6\u53d1\u751f\u5f02\u5e38", new Object[]{passenger.getReid(), passenger.getUserName(), e});
        }
    }
}
