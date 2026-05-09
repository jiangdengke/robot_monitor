/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.redis.RedisCache
 *  com.robotmonitor.common.utils.DateUtils
 *  com.robotmonitor.config.domain.ConfigDevice
 *  com.robotmonitor.config.domain.deepglint.face.CaptureFace
 *  com.robotmonitor.config.domain.deepglint.face.HitTag
 *  com.robotmonitor.config.mapper.ConfigDeviceMapper
 *  com.robotmonitor.config.service.IRegionMatchService
 *  com.robotmonitor.flight.domain.Passenger
 *  com.robotmonitor.flight.domain.PassengerOutLog
 *  com.robotmonitor.flight.mapper.PassengerMapper
 *  com.robotmonitor.flight.mapper.PassengerOutLogMapper
 *  com.robotmonitor.flight.service.IPassengerService
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 */
package com.robotmonitor.quartz.task;

import com.robotmonitor.common.core.redis.RedisCache;
import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.config.domain.ConfigDevice;
import com.robotmonitor.config.domain.deepglint.face.CaptureFace;
import com.robotmonitor.config.domain.deepglint.face.HitTag;
import com.robotmonitor.config.mapper.ConfigDeviceMapper;
import com.robotmonitor.config.service.IRegionMatchService;
import com.robotmonitor.flight.domain.Passenger;
import com.robotmonitor.flight.domain.PassengerOutLog;
import com.robotmonitor.flight.mapper.PassengerMapper;
import com.robotmonitor.flight.mapper.PassengerOutLogMapper;
import com.robotmonitor.flight.service.IPassengerService;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="passengerOutTask")
public class PassengerOutTask {
    private static final Logger log = LoggerFactory.getLogger((String)"passenger-location-out-task");
    @Autowired
    private PassengerMapper passengerMapper;
    @Autowired
    private ConfigDeviceMapper configDeviceMapper;
    @Autowired
    private PassengerOutLogMapper passengerOutLogMapper;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private IPassengerService passengerService;
    @Autowired
    private IRegionMatchService regionMatchService;
    private static final int LOCK_EXPIRE_TIME = 30;

    public void testPassengerOut() {
        log.info("[PID-OUT] \u5e94\u7528\u542f\u52a8 - \u81ea\u52a8\u6267\u884c\u51c6\u51fa\u529f\u80fd\u6d4b\u8bd5");
        this.checkPassengerOut();
    }

    public void checkPassengerOut() {
        try {
            log.info("[PID-OUT] \u5f00\u59cb\u6267\u884c\u65c5\u5ba2\u51c6\u51fa\u68c0\u6d4b\u5b9a\u65f6\u4efb\u52a1");
            long startTime = System.currentTimeMillis();
            List<Passenger> passengers = this.getPassengersWithUnflownFlights();
            if (passengers.isEmpty()) {
                log.info("[PID-OUT] \u6ca1\u6709\u627e\u5230\u672a\u8d77\u98de\u822a\u73ed\u7684\u65c5\u5ba2\uff0c\u4efb\u52a1\u7ed3\u675f");
                return;
            }
            log.info("[PID-OUT] \u627e\u5230 {} \u4e2a\u672a\u8d77\u98de\u822a\u73ed\u7684\u65c5\u5ba2\uff0c\u5f00\u59cb\u5904\u7406\u51c6\u51fa\u68c0\u6d4b", (Object)passengers.size());
            int checkoutCount = this.checkPassengerOut(passengers);
            long endTime = System.currentTimeMillis();
            log.info("[PID-OUT] \u65c5\u5ba2\u51c6\u51fa\u68c0\u6d4b\u5b9a\u65f6\u4efb\u52a1\u5b8c\u6210\uff0c\u5171\u5904\u7406\u51c6\u51fa {} \u4eba\uff0c\u8017\u65f6: {}ms", (Object)checkoutCount, (Object)(endTime - startTime));
        }
        catch (Exception e) {
            log.error("[PID-OUT] \u6267\u884c\u65c5\u5ba2\u51c6\u51fa\u68c0\u6d4b\u5b9a\u65f6\u4efb\u52a1\u65f6\u53d1\u751f\u5f02\u5e38", (Throwable)e);
            throw e;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int checkPassengerOut(List<Passenger> passengers) {
        Set<String> exitDeviceIds = this.getExitDeviceIds();
        if (exitDeviceIds.isEmpty()) {
            log.info("[PID-OUT] \u672a\u627e\u5230\u51fa\u53e3\u6444\u50cf\u5934\u8bbe\u5907");
            return 0;
        }
        log.info("[PID-OUT] \u627e\u5230 {} \u4e2a\u51fa\u53e3\u6444\u50cf\u5934\u8bbe\u5907", (Object)exitDeviceIds.size());
        int checkoutCount = 0;
        Iterator<Passenger> iterator = passengers.iterator();
        while (iterator.hasNext()) {
            Passenger passenger = iterator.next();
            try {
                String pId = passenger.getPid();
                if (pId == null || pId.trim().isEmpty()) {
                    log.info("[PID-OUT] \u65c5\u5ba2 {} REID\u4e3a\u7a7a\uff0c\u8df3\u8fc7", (Object)passenger.getUserName());
                    continue;
                }
                log.info("[PID-{}-OUT] \u5f00\u59cb\u5904\u7406\u65c5\u5ba2 {} \u51c6\u51fa\u68c0\u6d4b", (Object)pId, (Object)passenger.getUserName());
                String lockKey = "passenger_out_lock:" + pId;
                boolean lockAcquired = this.tryAcquireLock(lockKey, pId, "OUT");
                if (lockAcquired) {
                    try {
                        log.info("[PID-{}-OUT] \u5f00\u59cb\u68c0\u67e5\u65c5\u5ba2\u51c6\u51fa\u72b6\u6001", (Object)pId);
                        String origImageUrl = null;
                        String registerImageUrl = null;
                        CaptureFace captureFace = this.regionMatchService.getLatestCaptureFace(pId);
                        if (captureFace == null) continue;
                        origImageUrl = captureFace.getOrigImageUrl();
                        registerImageUrl = ((HitTag)captureFace.getTags().get(0)).getRegisterUrl();
                        log.info("[PID-{}-OUT] \u65c5\u5ba2 {} \u6700\u65b0\u6293\u62cd\u65f6\u95f4: {}, \u8bbe\u5907ID: {}", new Object[]{pId, passenger != null ? passenger.getUserName() : pId, new Date(captureFace.getCaptureTime()), captureFace.getLogicDeviceId()});
                        if (!exitDeviceIds.contains(captureFace.getLogicDeviceId())) {
                            log.info("[PID-{}-OUT] \u65c5\u5ba2 {} \u6700\u65b0\u6293\u62cd\u8bbe\u5907\u4e0d\u662f\u51fa\u53e3\u8bbe\u5907\uff0c\u672a\u6ee1\u8db3\u51c6\u51fa\u6761\u4ef6", (Object)pId, (Object)(passenger != null ? passenger.getUserName() : pId));
                            int n = 0;
                            return n;
                        }
                        int result = this.passengerMapper.updatePassengerCheckoutStatus(pId, "0", origImageUrl, registerImageUrl);
                        if (result > 0) {
                            ++checkoutCount;
                            log.info("[PID-{}-OUT] \u65c5\u5ba2 {} \u5df2\u786e\u8ba4\u79bb\u5f00\u8d35\u5bbe\u5385\uff0c\u66f4\u65b0\u4e3a\u51c6\u51fa\u72b6\u6001\u6210\u529f", (Object)pId, (Object)passenger.getUserName());
                            this.logPassengerOut(passenger, captureFace);
                            continue;
                        }
                        log.info("[PID-{}-OUT] \u66f4\u65b0\u65c5\u5ba2 {} \u51c6\u51fa\u72b6\u6001\u5931\u8d25", (Object)pId, (Object)passenger.getUserName());
                        continue;
                    }
                    finally {
                        this.releaseLock(lockKey, pId, "OUT");
                        continue;
                    }
                }
                log.info("[PID-{}-OUT] \u65c5\u5ba2 {} \u7684\u51c6\u51fa\u68c0\u6d4b\u9501\u5df2\u88ab\u5360\u7528\uff0c\u8df3\u8fc7\u672c\u6b21\u5904\u7406", (Object)pId, (Object)passenger.getUserName());
            }
            catch (Exception e) {
                log.error("[PID-{}-OUT] \u5904\u7406\u65c5\u5ba2 {} \u51c6\u51fa\u68c0\u6d4b\u65f6\u53d1\u751f\u5f02\u5e38", new Object[]{passenger.getReid(), passenger.getUserName(), e});
            }
        }
        return checkoutCount;
    }

    private void logPassengerOut(Passenger passenger, CaptureFace captureFace) {
        try {
            int result;
            String pId = passenger.getPid();
            String cts = null;
            if (captureFace != null) {
                cts = String.valueOf(captureFace.getCts());
                try {
                    PassengerOutLog existingLog = this.passengerOutLogMapper.selectPassengerOutLogByCtsId(cts);
                    if (existingLog != null) {
                        log.info("[PID-{}-OUT] cts {} \u5df2\u5b58\u5728\u4e8e\u51c6\u51fa\u65e5\u5fd7\u4e2d\uff0c\u8df3\u8fc7\u65b0\u589e", (Object)pId, (Object)cts);
                        return;
                    }
                }
                catch (Exception e) {
                    log.error("[PID-{}-OUT] \u67e5\u8be2FaceID\u5b58\u5728\u6027\u65f6\u53d1\u751f\u5f02\u5e38", (Object)pId, (Object)e);
                }
            }
            PassengerOutLog logEntry = new PassengerOutLog();
            logEntry.setReid(passenger.getReid());
            logEntry.setPid(passenger.getPid());
            logEntry.setPassengerId(String.valueOf(passenger.getId()));
            logEntry.setOutTime(new Date());
            logEntry.setUserName(passenger.getUserName());
            logEntry.setRoomCode(passenger.getRoomCode());
            logEntry.setFlightNo(passenger.getFlightNo());
            logEntry.setFlightDate(DateUtils.parseDate((Object)passenger.getFlightDate()));
            logEntry.setRecognitionType("face");
            logEntry.setCts(cts);
            if (captureFace != null) {
                logEntry.setOrigImageUrl(captureFace.getOrigImageUrl());
            }
            if (captureFace != null) {
                logEntry.setRegisterImageUrl(((HitTag)captureFace.getTags().get(0)).getRegisterUrl());
            }
            if ((result = this.passengerOutLogMapper.insertPassengerOutLog(logEntry)) > 0) {
                log.info("[PID-{}-OUT] \u65c5\u5ba2 {} \u51c6\u51fa\u65e5\u5fd7\u8bb0\u5f55\u6210\u529f\uff0ccts: {}", new Object[]{pId, passenger.getUserName(), cts});
            } else {
                log.warn("[PID-{}-OUT] \u65c5\u5ba2 {} \u51c6\u51fa\u65e5\u5fd7\u8bb0\u5f55\u5931\u8d25", (Object)pId, (Object)passenger.getUserName());
            }
        }
        catch (Exception e) {
            log.error("[PID-{}-OUT] \u8bb0\u5f55\u65c5\u5ba2 {} \u51c6\u51fa\u65e5\u5fd7\u65f6\u53d1\u751f\u5f02\u5e38", new Object[]{passenger.getReid(), passenger.getUserName(), e});
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
