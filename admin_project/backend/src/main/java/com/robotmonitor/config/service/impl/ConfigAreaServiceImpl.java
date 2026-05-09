/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.redis.RedisCache
 *  com.robotmonitor.common.utils.CreateVoiceUtils
 *  com.robotmonitor.common.utils.DateUtils
 *  com.robotmonitor.common.utils.StringUtils
 *  org.apache.commons.lang3.ObjectUtils
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.data.redis.core.StringRedisTemplate
 *  org.springframework.stereotype.Service
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.transaction.interceptor.TransactionAspectSupport
 *  org.springframework.util.CollectionUtils
 */
package com.robotmonitor.config.service.impl;

import com.robotmonitor.common.core.redis.RedisCache;
import com.robotmonitor.common.utils.CreateVoiceUtils;
import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.config.domain.ConfigArea;
import com.robotmonitor.config.domain.ConfigAreaDetail;
import com.robotmonitor.config.dto.ConfigAreaDto;
import com.robotmonitor.config.mapper.ConfigAreaDetailMapper;
import com.robotmonitor.config.mapper.ConfigAreaMapper;
import com.robotmonitor.config.service.IConfigAreaService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

@Service
public class ConfigAreaServiceImpl
implements IConfigAreaService {
    @Autowired
    private ConfigAreaMapper configAreaMapper;
    @Autowired
    private ConfigAreaDetailMapper configAreaDetailMapper;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public ConfigArea selectConfigAreaById(Long id) {
        ConfigArea configArea = this.configAreaMapper.selectConfigAreaById(id);
        if (ObjectUtils.isNotEmpty((Object)((Object)configArea))) {
            if (StringUtils.isNotEmpty((String)configArea.getImgIds())) {
                Long[] imageIds = (Long[])Arrays.stream(configArea.getImgIds().split(",")).map(Long::parseLong).toArray(Long[]::new);
                ArrayList<String> imgUrlList = new ArrayList<String>();
                configArea.setImgUrlList(imgUrlList);
                for (Long imageId : imageIds) {
                    imgUrlList.add("/api/rest/image/config/" + imageId);
                }
            }
            configArea.setConfigAreaDetailList(this.configAreaDetailMapper.selectDetailListByAreaId(configArea.getId()));
        }
        return configArea;
    }

    @Override
    public List<ConfigArea> selectConfigAreaList(ConfigArea configArea) {
        List<ConfigArea> areaList = this.configAreaMapper.selectConfigAreaList(configArea);
        for (ConfigArea area : areaList) {
            if (StringUtils.isNotEmpty((String)area.getImgIds())) {
                Long[] imageIds = (Long[])Arrays.stream(area.getImgIds().split(",")).map(Long::parseLong).toArray(Long[]::new);
                ArrayList imgUrlList = new ArrayList();
                area.setImgUrlList(imgUrlList);
                for (Long imageId : imageIds) {
                    imgUrlList.add("/api/rest/image/config/" + imageId);
                }
            }
            List<ConfigAreaDetail> detailList = this.configAreaDetailMapper.selectDetailListByAreaId(area.getId());
            for (ConfigAreaDetail detail : detailList) {
                detail.setAudio("");
                detail.setArrAudio("");
            }
            area.setConfigAreaDetailList(detailList);
            List detaiTemp = detailList.stream().filter(v -> v.getLanguageType().toLowerCase().equals("cn")).collect(Collectors.toList());
            if (!ObjectUtils.isNotEmpty(detaiTemp)) continue;
            area.setAreaName(((ConfigAreaDetail)((Object)detaiTemp.get(0))).getAreaName());
        }
        return areaList;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public boolean insertConfigArea(ConfigArea configArea) {
        try {
            this.configAreaMapper.insertConfigArea(configArea);
            for (ConfigAreaDetail detail : configArea.getConfigAreaDetailList()) {
                detail.setAreaId(configArea.getId());
                String s = CreateVoiceUtils.createVoice((StringRedisTemplate)this.stringRedisTemplate, (String)detail.getRemark(), (String)detail.getLanguageType());
                detail.setAudio(s);
                String arrAudio = CreateVoiceUtils.createVoice((StringRedisTemplate)this.stringRedisTemplate, (String)detail.getArrText(), (String)detail.getLanguageType());
                detail.setArrAudio(arrAudio);
                this.configAreaDetailMapper.insertConfigAreaDetail(detail);
            }
            return true;
        }
        catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public boolean updateConfigArea(ConfigArea configArea) {
        configArea.setUpdateTime(DateUtils.getNowDate());
        try {
            this.configAreaMapper.updateConfigArea(configArea);
            this.configAreaDetailMapper.deleteByAreaId(configArea.getId());
            for (ConfigAreaDetail detail : configArea.getConfigAreaDetailList()) {
                detail.setAreaId(configArea.getId());
                String s = CreateVoiceUtils.createVoice((StringRedisTemplate)this.stringRedisTemplate, (String)detail.getRemark(), (String)detail.getLanguageType());
                detail.setAudio(s);
                String arrAudio = CreateVoiceUtils.createVoice((StringRedisTemplate)this.stringRedisTemplate, (String)detail.getArrText(), (String)detail.getLanguageType());
                detail.setArrAudio(arrAudio);
                this.configAreaDetailMapper.insertConfigAreaDetail(detail);
            }
            return true;
        }
        catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    @Override
    public int deleteConfigAreaByIds(Long[] ids) {
        return this.configAreaMapper.deleteConfigAreaByIds(ids);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public boolean deleteConfigAreaById(Long id) {
        try {
            this.configAreaDetailMapper.deleteByAreaId(id);
            this.configAreaMapper.deleteConfigAreaById(id);
            return true;
        }
        catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    @Override
    public List<ConfigAreaDto> convetToDto(List<ConfigArea> list, String languageType) {
        ArrayList<ConfigAreaDto> dtoList = new ArrayList<ConfigAreaDto>();
        for (ConfigArea area : list) {
            ConfigAreaDto dto = new ConfigAreaDto();
            List detailList = area.getConfigAreaDetailList().stream().filter(v -> v.getLanguageType().equals(languageType)).collect(Collectors.toList());
            if (!ObjectUtils.isNotEmpty(detailList)) continue;
            ConfigAreaDetail detail = (ConfigAreaDetail)((Object)detailList.get(0));
            dto.setAudio(detail.getAudio());
            dto.setArrAudio(detail.getArrAudio());
            dto.setAreaName(detail.getAreaName());
            dto.setArrText(detail.getArrText());
            dto.setLabel(detail.getLabel());
            dto.setLanguageType(languageType);
            dto.setCurCapacity(area.getCurCapacity());
            dto.setCoordinate(area.getCoordinate());
            dto.setId(area.getId());
            dto.setRemark(detail.getRemark());
            dto.setIsGuide(area.getIsGuide());
            dto.setIsShow(area.getIsShow());
            dto.setMaxCapacity(area.getMaxCapacity());
            dto.setImgUrlList(area.getImgUrlList());
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public List<ConfigAreaDto> selectAreaList(String roomCode, String languageType) {
        ConfigArea param = new ConfigArea();
        param.setRoomCode(roomCode);
        param.setIsShow("1");
        List<ConfigArea> list = this.selectConfigAreaList(param);
        return this.convetToDto(list, languageType);
    }

    @Override
    public List<ConfigAreaDto> selectAreaListForDigitalTwin(String roomCode, String languageType) {
        ConfigArea param = new ConfigArea();
        param.setRoomCode(roomCode);
        param.setIsShow("1");
        List<ConfigArea> list = this.configAreaMapper.selectConfigAreaList(param);
        for (ConfigArea area : list) {
            if (StringUtils.isNotEmpty((String)area.getImgIds())) {
                Long[] imageIds = (Long[])Arrays.stream(area.getImgIds().split(",")).map(Long::parseLong).toArray(Long[]::new);
                ArrayList<String> imgUrlList = new ArrayList<String>();
                area.setImgUrlList(imgUrlList);
                for (Long imageId : imageIds) {
                    imgUrlList.add("/api/rest/image/config/" + imageId);
                }
            }
            List<ConfigAreaDetail> detailList = this.configAreaDetailMapper.selectDetailListWithoutAudioByAreaId(area.getId());
            area.setConfigAreaDetailList(detailList);
            List detaiTemp = detailList.stream().filter(v -> v.getLanguageType().toLowerCase().equals("cn")).collect(Collectors.toList());
            if (!ObjectUtils.isNotEmpty(detaiTemp)) continue;
            area.setAreaName(((ConfigAreaDetail)((Object)detaiTemp.get(0))).getAreaName());
        }
        return this.convetToDto(list, languageType);
    }

    @Override
    public ConfigArea selectAreaByRoomCodeAndNameAndLanguage(String location, String name, String languageType) {
        List<ConfigArea> list = this.configAreaMapper.selectAreaByRoomCodeAndNameAndLanguage(location, name, languageType);
        if (!CollectionUtils.isEmpty(list)) {
            ConfigArea configArea = list.get(0);
            ConfigAreaDetail queryRequest = new ConfigAreaDetail();
            queryRequest.setAreaId(configArea.getId());
            queryRequest.setLanguageType(languageType);
            configArea.setConfigAreaDetailList(this.configAreaDetailMapper.selectConfigAreaDetailList(queryRequest));
            return configArea;
        }
        return null;
    }

    @Override
    public ConfigAreaDetail getConfigAreaDetailById(Long id) {
        return this.configAreaDetailMapper.selectConfigAreaDetailById(id);
    }

    @Override
    public String findAreaNameByRoomCodeAndLanguageType(String roomCode, String languageType) {
        String key = "room_code_area_name:" + roomCode + "_" + languageType;
        String areaName = (String)this.redisCache.getCacheObject(key);
        if (StringUtils.isBlank((CharSequence)areaName)) {
            areaName = this.configAreaDetailMapper.findAreaNameByRoomCodeAndLanguageType(roomCode, languageType);
            this.redisCache.setCacheObject(key, (Object)areaName, Integer.valueOf(1), TimeUnit.HOURS);
        }
        return areaName;
    }

    @Override
    public List<ConfigAreaDetail> selectAreaByRegionId(Long id) {
        return this.configAreaDetailMapper.selectAreaByRegionId(id);
    }
}
