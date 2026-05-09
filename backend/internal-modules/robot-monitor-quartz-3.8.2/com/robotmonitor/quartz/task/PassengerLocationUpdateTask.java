/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.utils.DateUtils
 *  com.robotmonitor.config.domain.ConfigDevice
 *  com.robotmonitor.config.domain.deepglint.facelist.FaceListCaptureFace
 *  com.robotmonitor.config.domain.deepglint.facelist.FaceListRequest
 *  com.robotmonitor.config.domain.deepglint.facelist.FaceListResponse
 *  com.robotmonitor.config.domain.deepglint.facelist.FaceListTag
 *  com.robotmonitor.config.mapper.ConfigDeviceMapper
 *  com.robotmonitor.config.service.IDeepGlintService
 *  com.robotmonitor.flight.service.IUpdatePassengerService
 *  org.apache.logging.log4j.util.Strings
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 *  org.springframework.util.CollectionUtils
 */
package com.robotmonitor.quartz.task;

import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.config.domain.ConfigDevice;
import com.robotmonitor.config.domain.deepglint.facelist.FaceListCaptureFace;
import com.robotmonitor.config.domain.deepglint.facelist.FaceListRequest;
import com.robotmonitor.config.domain.deepglint.facelist.FaceListResponse;
import com.robotmonitor.config.domain.deepglint.facelist.FaceListTag;
import com.robotmonitor.config.mapper.ConfigDeviceMapper;
import com.robotmonitor.config.service.IDeepGlintService;
import com.robotmonitor.flight.service.IUpdatePassengerService;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component(value="passengerLocationUpdateTask")
public class PassengerLocationUpdateTask {
    private static final Logger log = LoggerFactory.getLogger((String)"passenger-location-out-task");
    @Autowired
    private ConfigDeviceMapper configDeviceMapper;
    @Autowired
    private IDeepGlintService deepGlintService;
    @Autowired
    private IUpdatePassengerService updatePassengerService;
    private static final int LOCK_EXPIRE_TIME = 30;

    public void testPassengeLocation() {
        log.info("[PID-LOCATION] \u5e94\u7528\u542f\u52a8 - \u81ea\u52a8\u6267\u884c\u4f4d\u7f6e\u66f4\u65b0\u529f\u80fd\u6d4b\u8bd5");
        this.updatePassengerLocations();
    }

    public void updatePassengerLocations() {
        try {
            log.info("[PID-LOCATION] \u5f00\u59cb\u6267\u884c\u65c5\u5ba2\u4f4d\u7f6e\u66f4\u65b0\u5b9a\u65f6\u4efb\u52a1");
            FaceListRequest faceListRequest = new FaceListRequest();
            faceListRequest.setLimit(Integer.valueOf(1000));
            long currentTime = DateUtils.getDateNowYYYYMMDDHHMM().getTime();
            long startTime = currentTime - 172800000L;
            long endTime = currentTime - 0L;
            faceListRequest.setStartTime(Long.valueOf(startTime));
            faceListRequest.setEndTime(Long.valueOf(endTime));
            FaceListResponse faceListResponse = this.deepGlintService.queryFaceList(faceListRequest);
            Set<String> exitDeviceIds = this.getExitDeviceIds();
            if (1 == faceListResponse.getCode()) {
                for (FaceListCaptureFace captureFace : this.filterSamePerson(faceListResponse.getData().getCaptureFaces())) {
                    boolean hasTags = !CollectionUtils.isEmpty((Collection)captureFace.getTags());
                    this.updatePassengerService.updatePassengerLocation(captureFace.getPersonID(), exitDeviceIds, captureFace.getLogicDeviceId(), hasTags, captureFace.getCaptureTime(), captureFace.getCaptureX(), captureFace.getCaptureY(), captureFace.getCaptureWidth(), captureFace.getCaptureHeight(), captureFace.getOrigImageWidth(), captureFace.getOrigImageHeight(), captureFace.getCts(), captureFace.getOrigImageUrl(), hasTags ? ((FaceListTag)captureFace.getTags().get(0)).getRegisterUrl() : null, hasTags ? ((FaceListTag)captureFace.getTags().get(0)).getRegisterID() : null);
                }
            } else {
                log.error("\u67e5\u8be2\u4eba\u8138\u5217\u8868\u5931\u8d25\uff0c{}", (Object)faceListResponse.getMsg());
            }
        }
        catch (Exception e) {
            log.error("[PID-LOCATION] \u6267\u884c\u65c5\u5ba2\u4f4d\u7f6e\u66f4\u65b0\u5b9a\u65f6\u4efb\u52a1\u65f6\u53d1\u751f\u5f02\u5e38", (Throwable)e);
            throw e;
        }
    }

    public Collection<FaceListCaptureFace> filterSamePerson(List<FaceListCaptureFace> captureFaces) {
        log.info("[PID-LOCATION] \u8fc7\u6ee4\u76f8\u540c\u65c5\u5ba2\uff0c\u5f53\u524d\u65c5\u5ba2\u6570\uff1a{}", (Object)captureFaces.size());
        HashMap map = new HashMap();
        captureFaces.forEach(captureFace -> {
            if (Strings.isNotBlank((String)captureFace.getPersonID()) && (!map.containsKey(captureFace.getPersonID()) || ((FaceListCaptureFace)map.get(captureFace.getPersonID())).getCaptureTime() < captureFace.getCaptureTime())) {
                map.put(captureFace.getPersonID(), captureFace);
            }
        });
        log.info("[PID-LOCATION] \u8fc7\u6ee4\u76f8\u540c\u65c5\u5ba2\uff0c\u8fc7\u6ee4\u540e\u65c5\u5ba2\u6570\uff1a{}", (Object)map.values().size());
        return map.values();
    }

    private Set<String> getExitDeviceIds() {
        log.info("\u5f00\u59cb\u67e5\u8be2\u51fa\u53e3\u6444\u50cf\u5934\u8bbe\u5907\u4fe1\u606f");
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
