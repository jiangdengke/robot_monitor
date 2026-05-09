package com.robotmonitor.config.service.impl;

import com.robotmonitor.common.core.redis.RedisCache;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

@Service
public class ConfigAreaServiceImpl implements IConfigAreaService {
    @Autowired
    private ConfigAreaMapper configAreaMapper;

    @Autowired
    private ConfigAreaDetailMapper configAreaDetailMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ConfigArea selectConfigAreaById(Long id) {
        ConfigArea configArea = configAreaMapper.selectConfigAreaById(id);
        if (ObjectUtils.isNotEmpty(configArea)) {
            fillImageUrls(configArea);
            configArea.setConfigAreaDetailList(configAreaDetailMapper.selectDetailListByAreaId(configArea.getId()));
        }
        return configArea;
    }

    @Override
    public List<ConfigArea> selectConfigAreaList(ConfigArea configArea) {
        List<ConfigArea> areaList = configAreaMapper.selectConfigAreaList(configArea);
        for (ConfigArea area : areaList) {
            fillImageUrls(area);
            List<ConfigAreaDetail> detailList = configAreaDetailMapper.selectDetailListByAreaId(area.getId());
            for (ConfigAreaDetail detail : detailList) {
                detail.setAudio("");
                detail.setArrAudio("");
            }
            area.setConfigAreaDetailList(detailList);
            applyDefaultAreaName(area, detailList);
        }
        return areaList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertConfigArea(ConfigArea configArea) {
        try {
            configAreaMapper.insertConfigArea(configArea);
            replaceAreaDetails(configArea);
            return true;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateConfigArea(ConfigArea configArea) {
        configArea.setUpdateTime(DateUtils.getNowDate());
        try {
            configAreaMapper.updateConfigArea(configArea);
            configAreaDetailMapper.deleteByAreaId(configArea.getId());
            replaceAreaDetails(configArea);
            return true;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    @Override
    public int deleteConfigAreaByIds(Long[] ids) {
        return configAreaMapper.deleteConfigAreaByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteConfigAreaById(Long id) {
        try {
            configAreaDetailMapper.deleteByAreaId(id);
            configAreaMapper.deleteConfigAreaById(id);
            return true;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    @Override
    public List<ConfigAreaDto> convetToDto(List<ConfigArea> list, String languageType) {
        List<ConfigAreaDto> dtoList = new ArrayList<>();
        for (ConfigArea area : list) {
            List<ConfigAreaDetail> detailList = area.getConfigAreaDetailList();
            if (CollectionUtils.isEmpty(detailList)) {
                continue;
            }
            List<ConfigAreaDetail> matchedDetails = detailList.stream()
                .filter(detail -> equalsIgnoreCase(detail.getLanguageType(), languageType))
                .collect(Collectors.toList());
            if (!ObjectUtils.isNotEmpty(matchedDetails)) {
                continue;
            }
            ConfigAreaDetail detail = matchedDetails.get(0);
            ConfigAreaDto dto = new ConfigAreaDto();
            dto.setAudio(nullToEmpty(detail.getAudio()));
            dto.setArrAudio(nullToEmpty(detail.getArrAudio()));
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
            dto.setImgIds(area.getImgIds());
            dto.setRoomCode(area.getRoomCode());
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public List<ConfigAreaDto> selectAreaList(String roomCode, String languageType) {
        ConfigArea param = new ConfigArea();
        param.setRoomCode(roomCode);
        param.setIsShow("1");
        return convetToDto(selectConfigAreaList(param), languageType);
    }

    @Override
    public List<ConfigAreaDto> selectAreaListForDigitalTwin(String roomCode, String languageType) {
        ConfigArea param = new ConfigArea();
        param.setRoomCode(roomCode);
        param.setIsShow("1");
        List<ConfigArea> list = configAreaMapper.selectConfigAreaList(param);
        for (ConfigArea area : list) {
            fillImageUrls(area);
            List<ConfigAreaDetail> detailList = configAreaDetailMapper.selectDetailListWithoutAudioByAreaId(area.getId());
            area.setConfigAreaDetailList(detailList);
            applyDefaultAreaName(area, detailList);
        }
        return convetToDto(list, languageType);
    }

    @Override
    public ConfigArea selectAreaByRoomCodeAndNameAndLanguage(String location, String name, String languageType) {
        List<ConfigArea> list = configAreaMapper.selectAreaByRoomCodeAndNameAndLanguage(location, name, languageType);
        if (!CollectionUtils.isEmpty(list)) {
            ConfigArea configArea = list.get(0);
            ConfigAreaDetail queryRequest = new ConfigAreaDetail();
            queryRequest.setAreaId(configArea.getId());
            queryRequest.setLanguageType(languageType);
            configArea.setConfigAreaDetailList(configAreaDetailMapper.selectConfigAreaDetailList(queryRequest));
            return configArea;
        }
        return null;
    }

    @Override
    public ConfigAreaDetail getConfigAreaDetailById(Long id) {
        return configAreaDetailMapper.selectConfigAreaDetailById(id);
    }

    @Override
    public String findAreaNameByRoomCodeAndLanguageType(String roomCode, String languageType) {
        String key = "room_code_area_name:" + roomCode + "_" + languageType;
        String areaName = (String) redisCache.getCacheObject(key);
        if (StringUtils.isBlank(areaName)) {
            areaName = configAreaDetailMapper.findAreaNameByRoomCodeAndLanguageType(roomCode, languageType);
            redisCache.setCacheObject(key, areaName, 1, TimeUnit.HOURS);
        }
        return areaName;
    }

    @Override
    public List<ConfigAreaDetail> selectAreaByRegionId(Long id) {
        return configAreaDetailMapper.selectAreaByRegionId(id);
    }

    private void replaceAreaDetails(ConfigArea configArea) {
        List<ConfigAreaDetail> details = configArea.getConfigAreaDetailList();
        if (CollectionUtils.isEmpty(details)) {
            return;
        }
        for (ConfigAreaDetail detail : details) {
            detail.setAreaId(configArea.getId());
            if (detail.getAudio() == null) {
                detail.setAudio("");
            }
            if (detail.getArrAudio() == null) {
                detail.setArrAudio("");
            }
            configAreaDetailMapper.insertConfigAreaDetail(detail);
        }
    }

    private void fillImageUrls(ConfigArea area) {
        if (StringUtils.isNotEmpty(area.getImgIds())) {
            Long[] imageIds = Arrays.stream(area.getImgIds().split(","))
                .filter(StringUtils::isNotBlank)
                .map(Long::parseLong)
                .toArray(Long[]::new);
            List<String> imgUrlList = new ArrayList<>();
            area.setImgUrlList(imgUrlList);
            for (Long imageId : imageIds) {
                imgUrlList.add("/api/rest/image/config/" + imageId);
            }
        }
    }

    private void applyDefaultAreaName(ConfigArea area, List<ConfigAreaDetail> detailList) {
        if (CollectionUtils.isEmpty(detailList)) {
            return;
        }
        List<ConfigAreaDetail> cnDetails = detailList.stream()
            .filter(detail -> equalsIgnoreCase(detail.getLanguageType(), "CN"))
            .collect(Collectors.toList());
        if (ObjectUtils.isNotEmpty(cnDetails)) {
            area.setAreaName(cnDetails.get(0).getAreaName());
        }
    }

    private boolean equalsIgnoreCase(String left, String right) {
        return left != null && right != null && left.equalsIgnoreCase(right);
    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}
