/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.config.ConfigRegion
 *  com.robotmonitor.common.core.redis.RedisCache
 *  com.robotmonitor.common.utils.DateUtils
 *  com.robotmonitor.config.deepglint.DeepGlintApiConfig
 *  com.robotmonitor.config.domain.ConfigDevice
 *  com.robotmonitor.config.domain.RecognitionResult
 *  com.robotmonitor.config.mapper.ConfigDeviceMapper
 *  com.robotmonitor.flight.domain.Passenger
 *  com.robotmonitor.flight.domain.PassengerLocationLog
 *  com.robotmonitor.flight.mapper.PassengerLocationLogMapper
 *  com.robotmonitor.flight.mapper.PassengerMapper
 *  com.robotmonitor.flight.service.IPassengerService
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 */
package com.robotmonitor.quartz.task;

import com.robotmonitor.common.core.domain.config.ConfigRegion;
import com.robotmonitor.common.core.redis.RedisCache;
import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.config.deepglint.DeepGlintApiConfig;
import com.robotmonitor.config.domain.ConfigDevice;
import com.robotmonitor.config.domain.RecognitionResult;
import com.robotmonitor.config.mapper.ConfigDeviceMapper;
import com.robotmonitor.flight.domain.Passenger;
import com.robotmonitor.flight.domain.PassengerLocationLog;
import com.robotmonitor.flight.mapper.PassengerLocationLogMapper;
import com.robotmonitor.flight.mapper.PassengerMapper;
import com.robotmonitor.flight.service.IPassengerService;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="passengerLocationTask")
public class PassengerLocationTask {
    private static final Logger log = LoggerFactory.getLogger((String)"passenger-location-out-task");
    @Autowired
    private IPassengerService passengerService;
    @Autowired
    private PassengerMapper passengerMapper;
    @Autowired
    private ConfigDeviceMapper configDeviceMapper;
    @Autowired
    private PassengerLocationLogMapper passengerLocationLogMapper;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private DeepGlintApiConfig deepGlintApiConfig;
    private static final int LOCK_EXPIRE_TIME = 30;

    public void testPassengeLocation() {
        log.info("[PID-LOCATION] \u5e94\u7528\u542f\u52a8 - \u81ea\u52a8\u6267\u884c\u4f4d\u7f6e\u66f4\u65b0\u529f\u80fd\u6d4b\u8bd5");
        this.updatePassengerLocations();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updatePassengerLocations() {
        try {
            log.info("[PID-LOCATION] \u5f00\u59cb\u6267\u884c\u65c5\u5ba2\u4f4d\u7f6e\u66f4\u65b0\u5b9a\u65f6\u4efb\u52a1");
            long startTime = System.currentTimeMillis();
            List<Passenger> passengers = this.getPassengersWithUnflownFlights();
            if (passengers.isEmpty()) {
                log.info("[PID-LOCATION] \u6ca1\u6709\u627e\u5230\u672a\u8d77\u98de\u822a\u73ed\u7684\u65c5\u5ba2\uff0c\u4efb\u52a1\u7ed3\u675f");
                return;
            }
            log.info("[PID-LOCATION] \u627e\u5230 {} \u4e2a\u672a\u8d77\u98de\u822a\u73ed\u7684\u65c5\u5ba2\uff0c\u5f00\u59cb\u5904\u7406", (Object)passengers.size());
            Set<String> exitDeviceIds = this.getExitDeviceIds();
            int updatedCount = 0;
            for (Passenger passenger : passengers) {
                try {
                    String pId = passenger.getPid();
                    if (pId == null || pId.trim().isEmpty()) {
                        log.info("[PID-LOCATION] \u65c5\u5ba2 {} PID\u4e3a\u7a7a\uff0c\u8df3\u8fc7", (Object)passenger.getUserName());
                        continue;
                    }
                    log.info("[PID-{}-LOCATION] \u5f00\u59cb\u5904\u7406\u65c5\u5ba2 {} \u4f4d\u7f6e\u4fe1\u606f", (Object)pId, (Object)passenger.getUserName());
                    String lockKey = "passenger_update_lock:" + pId;
                    boolean lockAcquired = this.tryAcquireLock(lockKey, pId, "LOCATION");
                    if (lockAcquired) {
                        try {
                            ConfigRegion region;
                            log.info("[PID-{}-LOCATION] \u5f00\u59cb\u67e5\u8be2\u65c5\u5ba2\u4f4d\u7f6e\uff0c\u6392\u9664\u51fa\u53e3\u6444\u50cf\u5934", (Object)pId);
                            RecognitionResult recognitionResult = this.findPassageExcludingExitCameras(pId, exitDeviceIds);
                            ConfigRegion configRegion = region = recognitionResult != null ? recognitionResult.getRegion() : null;
                            if (region != null) {
                                log.info("[PID-{}-LOCATION] \u627e\u5230\u6709\u6548\u4f4d\u7f6e\uff0c\u533a\u57dfID: {}\uff0c\u7c7b\u578b: {}\uff0c\u65f6\u95f4\u6233: {}", new Object[]{pId, region.getId(), recognitionResult.getRecognitionType(), recognitionResult.getCts()});
                                int result = this.passengerService.updatePassengerRegionAndStatus(pId, region.getId(), "1", recognitionResult.getOrigImageUrl(), recognitionResult.getRegisterImageUrl());
                                if (result > 0) {
                                    ++updatedCount;
                                    log.info("[PID-{}-LOCATION] \u66f4\u65b0\u65c5\u5ba2 {} \u4f4d\u7f6e\u4fe1\u606f\u6210\u529f\uff0c\u4f4d\u7f6eID: {}", new Object[]{pId, passenger.getUserName(), region.getId()});
                                    this.logPassengerLocation(passenger, recognitionResult);
                                    continue;
                                }
                                log.info("[PID-{}-LOCATION] \u66f4\u65b0\u65c5\u5ba2 {} \u4f4d\u7f6e\u4fe1\u606f\u5931\u8d25", (Object)pId, (Object)passenger.getUserName());
                                continue;
                            }
                            log.info("[PID-{}-LOCATION] \u672a\u627e\u5230\u65c5\u5ba2 {} \u7684\u4f4d\u7f6e\u4fe1\u606f\uff08\u6392\u9664\u51fa\u53e3\u6444\u50cf\u5934\u540e\uff09", (Object)pId, (Object)passenger.getUserName());
                            continue;
                        }
                        finally {
                            this.releaseLock(lockKey, pId, "LOCATION");
                            continue;
                        }
                    }
                    log.info("[PID-{}-LOCATION] \u65c5\u5ba2 {} \u7684\u66f4\u65b0\u9501\u5df2\u88ab\u5360\u7528\uff0c\u8df3\u8fc7\u672c\u6b21\u66f4\u65b0", (Object)pId, (Object)passenger.getUserName());
                }
                catch (Exception e) {
                    log.error("[PID-{}-LOCATION] \u5904\u7406\u65c5\u5ba2 {} \u4f4d\u7f6e\u4fe1\u606f\u65f6\u53d1\u751f\u5f02\u5e38", new Object[]{passenger.getReid(), passenger.getUserName(), e});
                }
            }
            long endTime = System.currentTimeMillis();
            log.info("[PID-LOCATION] \u65c5\u5ba2\u4f4d\u7f6e\u66f4\u65b0\u5b9a\u65f6\u4efb\u52a1\u5b8c\u6210\uff0c\u5171\u66f4\u65b0 {} \u6761\u8bb0\u5f55\uff0c\u8017\u65f6: {}ms", (Object)updatedCount, (Object)(endTime - startTime));
        }
        catch (Exception e) {
            log.error("[PID-LOCATION] \u6267\u884c\u65c5\u5ba2\u4f4d\u7f6e\u66f4\u65b0\u5b9a\u65f6\u4efb\u52a1\u65f6\u53d1\u751f\u5f02\u5e38", (Throwable)e);
            throw e;
        }
    }

    private RecognitionResult findPassageExcludingExitCameras(String pId, Set<String> exitDeviceIds) {
        try {
            String recognitionType = this.deepGlintApiConfig.getRecognitionType();
            log.info("[PID-{}-LOCATION] \u5f00\u59cb\u67e5\u627e\u65c5\u5ba2\u4f4d\u7f6e\uff08\u6392\u9664\u51fa\u53e3\u6444\u50cf\u5934\uff09\uff0c\u8bc6\u522b\u7c7b\u578b\u914d\u7f6e: {}", (Object)pId, (Object)recognitionType);
            Passenger passenger = this.passengerMapper.selectPassengerByPid(pId);
            log.info("[PID-{}-LOCATION] \u65c5\u5ba2 {} \u4f7f\u7528\u4eba\u8138\u8bc6\u522b", (Object)pId, (Object)passenger.getUserName());
            RecognitionResult result = this.passengerService.findPassage(pId);
            if (result != null) {
                if (result.getRegion() == null) {
                    log.info("[PID-{}-LOCATION] \u65c5\u5ba2 {} \u7684\u533a\u57df\u4e3a\u7a7a", (Object)pId, (Object)passenger.getUserName());
                    return null;
                }
                log.info("[PID-{}-LOCATION] \u4eba\u8138\u8bc6\u522b\u67e5\u8be2\u6210\u529f\uff0c\u533a\u57dfID: {}", (Object)pId, (Object)result.getRegion().getId());
                ConfigDevice configDevice = result.getConfigDevice();
                if (configDevice != null) {
                    String deepGlintDeviceId = configDevice.getDeepGlintDeviceId();
                    if (deepGlintDeviceId == null || !exitDeviceIds.contains(deepGlintDeviceId)) {
                        log.info("[PID-{}-LOCATION] \u65c5\u5ba2 {} \u901a\u8fc7\u4eba\u8138\u8bc6\u522b\u627e\u5230\u4f4d\u7f6e\uff0c\u4f4d\u7f6eID: {}", new Object[]{pId, passenger.getUserName(), result.getRegion().getId()});
                        return result;
                    }
                    log.info("[PID-{}-LOCATION] \u65c5\u5ba2 {} \u7684\u4eba\u8138\u8bc6\u522b\u4f4d\u7f6e\u5728\u51fa\u53e3\u6444\u50cf\u5934\u8bbe\u5907 {}\uff0c\u4e0d\u7b26\u5408\u8981\u6c42", new Object[]{pId, passenger.getUserName(), deepGlintDeviceId});
                }
            }
            return null;
        }
        catch (Exception e) {
            log.error("\u67e5\u627e\u65c5\u5ba2 {} \u4f4d\u7f6e\uff08\u6392\u9664\u51fa\u53e3\u6444\u50cf\u5934\uff09\u65f6\u53d1\u751f\u5f02\u5e38", (Object)pId, (Object)e);
            return null;
        }
    }

    private void logPassengerLocation(Passenger passenger, RecognitionResult recognitionResult) {
        try {
            String pId = passenger.getPid();
            log.info("[PID-{}-LOCATION] \u5f00\u59cb\u8bb0\u5f55\u65c5\u5ba2 {} \u4f4d\u7f6e\u65e5\u5fd7\uff0c\u533a\u57df: {}\uff0c\u8bbe\u5907: {}", new Object[]{pId, passenger.getUserName(), recognitionResult.getRegion().getRegionName(), recognitionResult.getConfigDevice().getDeviceName()});
            PassengerLocationLog logEntry = new PassengerLocationLog();
            logEntry.setReid(passenger.getReid());
            logEntry.setPid(passenger.getPid());
            logEntry.setPassengerId(String.valueOf(passenger.getId()));
            logEntry.setUserName(passenger.getUserName());
            logEntry.setRoomCode(passenger.getRoomCode());
            logEntry.setFlightNo(passenger.getFlightNo());
            logEntry.setFlightDate(DateUtils.parseDate((Object)passenger.getFlightDate()));
            logEntry.setRegionId(Integer.valueOf(recognitionResult.getRegion().getId().intValue()));
            logEntry.setRegionName(recognitionResult.getRegion().getRegionName());
            logEntry.setCoordinate(recognitionResult.getRegion().getCoordinate());
            logEntry.setDeviceId("" + recognitionResult.getConfigDevice().getId());
            logEntry.setDeviceName(recognitionResult.getConfigDevice().getDeviceName());
            logEntry.setDeepGlintDeviceId(recognitionResult.getConfigDevice().getDeepGlintDeviceId());
            logEntry.setCreateTime(new Date());
            logEntry.setRecognitionType(recognitionResult.getRecognitionType());
            logEntry.setCts(recognitionResult.getCts());
            logEntry.setOriImageUrl(recognitionResult.getOrigImageUrl());
            logEntry.setRegisterImageUrl(recognitionResult.getRegisterImageUrl());
            int result = this.passengerLocationLogMapper.insertPassengerLocationLog(logEntry);
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

    private boolean tryAcquireLock(String lockKey, String pId, String lockType) {
        try {
            String existingLock = (String)this.redisCache.getCacheObject(lockKey);
            if (existingLock == null) {
                this.redisCache.setCacheObject(lockKey, (Object)"locked", Integer.valueOf(30), TimeUnit.SECONDS);
                String currentLock = (String)this.redisCache.getCacheObject(lockKey);
                if ("locked".equals(currentLock)) {
                    return true;
                }
            } else {
                log.info("[PID-{}-{}] \u5206\u5e03\u5f0f\u9501\u5df2\u88ab\u5360\u7528\uff0c\u5f53\u524d\u6301\u6709\u503c: {}", new Object[]{pId, lockType, existingLock});
            }
            return false;
        }
        catch (Exception e) {
            log.error("[PID-{}-{}] \u83b7\u53d6Redis\u9501\u5f02\u5e38", new Object[]{pId, lockType, e});
            return false;
        }
    }

    private void releaseLock(String lockKey, String pId, String lockType) {
        try {
            this.redisCache.deleteObject(lockKey);
        }
        catch (Exception e) {
            log.error("[PID-{}-{}] \u91ca\u653eRedis\u9501\u5f02\u5e38", new Object[]{pId, lockType, e});
        }
    }

    private List<Passenger> getPassengersWithUnflownFlights() {
        List passengers = this.passengerMapper.selectPassengerWithUnflownFlights();
        return passengers;
    }

    private Set<String> getExitDeviceIds() {
        List exitDevices = this.configDeviceMapper.selectExitDevices();
        HashSet<String> exitDeviceIds = new HashSet<String>();
        for (ConfigDevice device : exitDevices) {
            if (device.getDeepGlintDeviceId() == null) continue;
            exitDeviceIds.add(device.getDeepGlintDeviceId());
        }
        log.info("\u83b7\u53d6\u5230 {} \u4e2a\u51fa\u53e3\u6444\u50cf\u5934\u8bbe\u5907", (Object)exitDeviceIds.size());
        return exitDeviceIds;
    }
}
