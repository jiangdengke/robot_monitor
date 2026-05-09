/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.robotmonitor.common.core.domain.config.ConfigRegion
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.config.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robotmonitor.common.core.domain.config.ConfigRegion;
import com.robotmonitor.config.deepglint.DeepGlintApiConfig;
import com.robotmonitor.config.domain.ConfigDevice;
import com.robotmonitor.config.domain.ConfigDeviceRegion;
import com.robotmonitor.config.domain.deepglint.face.CaptureFace;
import com.robotmonitor.config.domain.deepglint.face.FaceHistoryAlertRequest;
import com.robotmonitor.config.domain.deepglint.face.FaceHistoryAlertResponse;
import com.robotmonitor.config.domain.deepglint.face.Point;
import com.robotmonitor.config.mapper.ConfigDeviceMapper;
import com.robotmonitor.config.mapper.ConfigDeviceRegionMapper;
import com.robotmonitor.config.mapper.ConfigRegionMapper;
import com.robotmonitor.config.service.IDeepGlintService;
import com.robotmonitor.config.service.IRegionMatchService;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegionMatchServiceImpl
implements IRegionMatchService {
    private static final Logger log = LoggerFactory.getLogger((String)"passenger-location-out-task");
    @Autowired
    private DeepGlintApiConfig deepGlintApiConfig;
    @Autowired
    private ConfigDeviceRegionMapper configDeviceRegionMapper;
    @Autowired
    private ConfigRegionMapper configRegionMapper;
    @Autowired
    private ConfigDeviceMapper configDeviceMapper;
    @Autowired
    private IDeepGlintService deepGlintService;

    @Override
    public ConfigRegion matchRegion(CaptureFace captureFace, String reId) {
        if (captureFace == null) {
            String logPrefix = reId != null ? "[PID-{}-MATCH] " : "";
            log.info(logPrefix + "CaptureFace\u4e3anull", (Object)reId);
            return null;
        }
        return this.matchRegion(reId, captureFace.getLogicDeviceId(), captureFace.getCaptureTime(), captureFace.getCaptureX(), captureFace.getCaptureY(), captureFace.getCaptureWidth(), captureFace.getCaptureHeight(), captureFace.getOrigImageWidth(), captureFace.getOrigImageHeight());
    }

    @Override
    public ConfigRegion matchRegion(String reId, String logicDeviceId, Long captureTime, Integer captureX, Integer captureY, Integer captureWidth, Integer captureHeight, Integer origImageWidth, Integer origImageHeight) {
        if (logicDeviceId == null || logicDeviceId.isEmpty()) {
            String logPrefix = reId != null ? "[PID-{}-MATCH] " : "";
            log.info(logPrefix + "CaptureFace\u7f3a\u5c11LogicDeviceId", (Object)reId);
            return null;
        }
        String logPrefix = reId != null ? "[PID-{}-MATCH] " : "";
        log.info(logPrefix + "\u5f00\u59cb\u5339\u914d\u533a\u57df: \u8bbe\u5907ID={}, \u6293\u62cd\u65f6\u95f4={}", new Object[]{reId, logicDeviceId, new Date(captureTime)});
        List<ConfigDeviceRegion> regions = this.findRegionsByDeviceId(logicDeviceId);
        if (regions.isEmpty()) {
            log.info(logPrefix + "\u672a\u627e\u5230\u8bbe\u5907 {} \u7684\u533a\u57df\u914d\u7f6e", (Object)reId, (Object)logicDeviceId);
            return null;
        }
        Point faceCenter = this.calculateFaceCenter(captureX, captureY, captureWidth, captureHeight);
        if (faceCenter == null) {
            log.info(logPrefix + "\u65e0\u6cd5\u8ba1\u7b97\u4eba\u8138\u4e2d\u5fc3\u70b9", (Object)reId);
            return null;
        }
        log.info(logPrefix + "\u4eba\u8138\u4e2d\u5fc3\u70b9: ({}, {})", new Object[]{reId, faceCenter.getX(), faceCenter.getY()});
        Long matchedRegionId = null;
        for (ConfigDeviceRegion region : regions) {
            if (!this.isPointInRegion(faceCenter, region.getCoordinate(), origImageWidth, origImageHeight)) continue;
            log.info(logPrefix + "\u4eba\u8138\u5339\u914d\u5230\u533a\u57df: deviceId={}, regionId={}", new Object[]{reId, logicDeviceId, region.getRegionId()});
            matchedRegionId = region.getRegionId();
            break;
        }
        if (matchedRegionId == null) {
            log.info(logPrefix + "\u4eba\u8138\u672a\u5339\u914d\u5230\u4efb\u4f55\u533a\u57df: deviceId={}, faceCenter=({},{})", new Object[]{reId, logicDeviceId, faceCenter.getX(), faceCenter.getY()});
            return null;
        }
        ConfigRegion region = this.configRegionMapper.selectConfigRegionById(matchedRegionId);
        log.info(logPrefix + "\u533a\u57df\u5339\u914d\u5b8c\u6210: REID={}, \u533a\u57dfID={}, \u533a\u57df\u540d\u79f0={}", new Object[]{reId, matchedRegionId, region != null ? region.getRegionName() : "UNKNOWN"});
        return region;
    }

    @Override
    public CaptureFace getLatestCaptureFace(String pId) {
        Object captureFace = null;
        FaceHistoryAlertRequest request = new FaceHistoryAlertRequest();
        request.setLimit(1);
        request.setRegisterPersonId(pId);
        request.setRepoId(this.deepGlintApiConfig.getRepoId());
        long currentTime = System.currentTimeMillis();
        long startTime = currentTime - 300000L;
        request.setStartTime(startTime);
        request.setEndTime(currentTime);
        log.info("[PID-{}-FIND] \u67e5\u8be2\u4eba\u8138\u5386\u53f2\u544a\u8b66 - \u65f6\u95f4\u8303\u56f4: {} \u5230 {}", new Object[]{pId, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(startTime)), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(currentTime))});
        FaceHistoryAlertResponse response = this.deepGlintService.queryFaceHistoryAlert(request);
        if (!response.isSuccess()) {
            log.info("[PID-{}-FIND] \u683c\u6797\u63a5\u53e3\u8c03\u7528\u5931\u8d25: Code={}, Msg={}", new Object[]{pId, response.getCode(), response.getMsg()});
            return null;
        }
        if (response.getData() == null || response.getData().getCaptureFaces() == null || response.getData().getCaptureFaces().isEmpty()) {
            log.info("[PID-{}-FIND] \u672a\u627e\u5230\u8be5\u4eba\u5458\u7684\u6293\u62cd\u8bb0\u5f55", (Object)pId);
            return null;
        }
        return response.getData().getCaptureFaces().get(0);
    }

    public List<ConfigDeviceRegion> findRegionsByDeviceId(String logicDeviceId) {
        try {
            Long deviceId = this.convertLogicDeviceIdToDeviceId(logicDeviceId);
            if (deviceId == null) {
                log.info("\u65e0\u6cd5\u8f6c\u6362LogicDeviceId: {}", (Object)logicDeviceId);
                return Collections.emptyList();
            }
            List<ConfigDeviceRegion> regions = this.configDeviceRegionMapper.selectByDeviceId(deviceId);
            log.info("\u627e\u5230\u8bbe\u5907 {} \u7684\u533a\u57df\u914d\u7f6e\u6570\u91cf: {}", (Object)deviceId, (Object)regions.size());
            return regions;
        }
        catch (Exception e) {
            log.error("\u67e5\u8be2\u8bbe\u5907\u533a\u57df\u914d\u7f6e\u5931\u8d25: {}", (Object)logicDeviceId, (Object)e);
            return Collections.emptyList();
        }
    }

    private Long convertLogicDeviceIdToDeviceId(String logicDeviceId) {
        try {
            ConfigDevice device = this.configDeviceMapper.selectConfigDeviceByDeepGlintDeviceId(logicDeviceId);
            if (device != null) {
                log.info("\u627e\u5230\u683c\u7075\u6df1\u77b3\u8bbe\u5907ID: {} \u5bf9\u5e94\u7684\u8bbe\u5907ID: {}", (Object)logicDeviceId, (Object)device.getId());
                return device.getId();
            }
            log.info("\u672a\u627e\u5230\u683c\u7075\u6df1\u77b3\u8bbe\u5907ID: {} \u5bf9\u5e94\u7684\u8bbe\u5907\u8bb0\u5f55", (Object)logicDeviceId);
            return null;
        }
        catch (Exception e) {
            log.error("\u6839\u636e\u683c\u7075\u6df1\u77b3\u8bbe\u5907ID\u67e5\u8be2\u8bbe\u5907\u5931\u8d25: {}", (Object)logicDeviceId, (Object)e);
            return null;
        }
    }

    private Point calculateFaceCenter(CaptureFace captureFace) {
        Integer captureX = captureFace.getCaptureX();
        Integer captureY = captureFace.getCaptureY();
        Integer captureWidth = captureFace.getCaptureWidth();
        Integer captureHeight = captureFace.getCaptureHeight();
        return this.calculateFaceCenter(captureX, captureY, captureWidth, captureHeight);
    }

    private Point calculateFaceCenter(Integer captureX, Integer captureY, Integer captureWidth, Integer captureHeight) {
        if (captureX == null || captureY == null || captureWidth == null || captureHeight == null || captureX == 0 || captureY == 0 || captureWidth == 0 || captureHeight == 0) {
            log.warn("\u65e0\u6548\u7684\u5750\u6807\u6570\u636e: CaptureX={}, CaptureY={}, CaptureWidth={}, CaptureHeight={}", new Object[]{captureX, captureY, captureWidth, captureHeight});
            return null;
        }
        double centerX = (double)captureX.intValue() + (double)captureWidth.intValue() / 2.0;
        double centerY = (double)captureY.intValue() + (double)captureHeight.intValue() / 2.0;
        return new Point(centerX, centerY);
    }

    private boolean isPointInRegion(Point point, String coordinateJson, CaptureFace captureFace) {
        return this.isPointInRegion(point, coordinateJson, captureFace.getOrigImageWidth(), captureFace.getOrigImageHeight());
    }

    private boolean isPointInRegion(Point point, String coordinateJson, Integer origImageWidth, Integer origImageHeight) {
        try {
            List<List<Double>> coordinates = this.parseCoordinateJson(coordinateJson);
            if (coordinates == null || coordinates.size() != 4) {
                log.warn("\u5750\u6807\u683c\u5f0f\u9519\u8bef: {}", (Object)coordinateJson);
                return false;
            }
            int imageWidth = origImageWidth != null ? origImageWidth : this.deepGlintApiConfig.getImageWidth();
            int imageHeight = origImageHeight != null ? origImageHeight : this.deepGlintApiConfig.getImageHeight();
            List<Point> polygon = coordinates.stream().map(coord -> this.normalizeToPixel((Double)coord.get(0), (Double)coord.get(1), imageWidth, imageHeight)).collect(Collectors.toList());
            return this.rayCasting(point, polygon);
        }
        catch (Exception e) {
            log.error("\u89e3\u6790\u5750\u6807JSON\u5931\u8d25: {}", (Object)coordinateJson, (Object)e);
            return false;
        }
    }

    private Point normalizeToPixel(double normalizedX, double normalizedY, int imageWidth, int imageHeight) {
        int pixelX = (int)(normalizedX * (double)imageWidth);
        int pixelY = (int)(normalizedY * (double)imageHeight);
        return new Point(pixelX, pixelY);
    }

    private List<List<Double>> parseCoordinateJson(String coordinateJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return (List)objectMapper.readValue(coordinateJson, (JavaType)objectMapper.getTypeFactory().constructCollectionType(List.class, (JavaType)objectMapper.getTypeFactory().constructCollectionType(List.class, Double.class)));
        }
        catch (Exception e) {
            log.error("JSON\u89e3\u6790\u5931\u8d25: {}", (Object)coordinateJson, (Object)e);
            return null;
        }
    }

    private boolean rayCasting(Point point, List<Point> polygon) {
        int n = polygon.size();
        boolean inside = false;
        int i = 0;
        int j = n - 1;
        while (i < n) {
            Point p2;
            Point p1 = polygon.get(i);
            if (this.isPointOnSegment(point, p1, p2 = polygon.get(j))) {
                return true;
            }
            if (p1.getY() > point.getY() != p2.getY() > point.getY() && point.getX() < (p2.getX() - p1.getX()) * (point.getY() - p1.getY()) / (p2.getY() - p1.getY()) + p1.getX()) {
                inside = !inside;
            }
            j = i++;
        }
        return inside;
    }

    private boolean isPointOnSegment(Point point, Point p1, Point p2) {
        double crossProduct = (point.getY() - p1.getY()) * (p2.getX() - p1.getX()) - (point.getX() - p1.getX()) * (p2.getY() - p1.getY());
        if (Math.abs(crossProduct) > 1.0E-10) {
            return false;
        }
        return !(point.getX() < Math.min(p1.getX(), p2.getX()) || point.getX() > Math.max(p1.getX(), p2.getX()) || point.getY() < Math.min(p1.getY(), p2.getY())) && !(point.getY() > Math.max(p1.getY(), p2.getY()));
    }
}
