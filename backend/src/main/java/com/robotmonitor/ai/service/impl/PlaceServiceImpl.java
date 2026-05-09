/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.config.domain.ConfigArea
 *  com.robotmonitor.config.domain.ConfigAreaDetail
 *  com.robotmonitor.config.service.IConfigAreaService
 *  com.robotmonitor.config.service.IConfigImgService
 *  com.robotmonitor.config.service.IConfigRegionService
 *  com.robotmonitor.config.service.IConfigRobotService
 *  io.jsonwebtoken.lang.Collections
 *  org.apache.logging.log4j.util.Strings
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.ai.service.impl;

import com.robotmonitor.ai.domain.PlaceImage;
import com.robotmonitor.ai.domain.PlaceInfo;
import com.robotmonitor.ai.service.PlaceService;
import com.robotmonitor.config.domain.ConfigArea;
import com.robotmonitor.config.domain.ConfigAreaDetail;
import com.robotmonitor.config.service.IConfigAreaService;
import com.robotmonitor.config.service.IConfigImgService;
import com.robotmonitor.config.service.IConfigRegionService;
import com.robotmonitor.config.service.IConfigRobotService;
import io.jsonwebtoken.lang.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaceServiceImpl
implements PlaceService {
    private static final Logger log = LoggerFactory.getLogger(PlaceServiceImpl.class);
    @Autowired
    private IConfigRegionService configRegionService;
    @Autowired
    private IConfigImgService configImgService;
    @Autowired
    private IConfigRobotService configRobotService;
    @Autowired
    private IConfigAreaService configAreaService;

    @Override
    public PlaceInfo findPlaceInfo(String location, String name, String language, String robotId) {
        Long[] imageIds;
        List configImgs;
        log.info("location : {}, name : {}, language : {}, robotId : {}", new Object[]{location, name, language, robotId});
        ConfigArea configArea = this.configAreaService.selectAreaByRoomCodeAndNameAndLanguage(location, name, language);
        if (null == configArea) {
            return null;
        }
        String audioUrl = null;
        ConfigAreaDetail configAreaDetail = (ConfigAreaDetail)configArea.getConfigAreaDetailList().get(0);
        if (Strings.isNotBlank((String)configAreaDetail.getArrAudio())) {
            audioUrl = "/api/voice/area?id=" + configAreaDetail.getId() + "&type=1";
            if (Strings.isNotBlank((String)configAreaDetail.getAudio())) {
                audioUrl = audioUrl + ",/api/voice/area?id=" + configAreaDetail.getId() + "&type=0";
            }
        }
        ArrayList<PlaceImage> placeImages = new ArrayList<PlaceImage>();
        if (Strings.isNotBlank((String)configArea.getImgIds()) && !Collections.isEmpty((Collection)(configImgs = this.configImgService.selectConfigImgListByIds(imageIds = (Long[])Arrays.stream(configArea.getImgIds().split(",")).map(Long::parseLong).toArray(Long[]::new))))) {
            configImgs.forEach(configImg -> placeImages.add(new PlaceImage(configImg.getImgName(), "/api/image/config/" + configImg.getId())));
        }
        return new PlaceInfo(configArea.getId(), configAreaDetail.getAreaName(), configArea.getRoomCode(), configArea.getCoordinate(), placeImages, audioUrl, configAreaDetail.getRemark());
    }

    @Override
    public String getPlaceNames(String location, String language) {
        return this.configAreaService.findAreaNameByRoomCodeAndLanguageType(location, language);
    }
}
