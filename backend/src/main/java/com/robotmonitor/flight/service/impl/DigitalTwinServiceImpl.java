/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.config.ConfigRegion
 *  com.robotmonitor.common.core.domain.config.ConfigRobot
 *  com.robotmonitor.common.utils.DictUtils
 *  com.robotmonitor.config.domain.ConfigArea
 *  com.robotmonitor.config.domain.ConfigAreaDetail
 *  com.robotmonitor.config.domain.ConfigTable
 *  com.robotmonitor.config.dto.ConfigAreaDto
 *  com.robotmonitor.config.service.IConfigAreaService
 *  com.robotmonitor.config.service.IConfigRegionService
 *  com.robotmonitor.config.service.IConfigRobotService
 *  com.robotmonitor.config.service.IConfigTableService
 *  org.apache.commons.lang3.ObjectUtils
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.flight.service.impl;

import com.robotmonitor.common.core.domain.config.ConfigRegion;
import com.robotmonitor.common.core.domain.config.ConfigRobot;
import com.robotmonitor.common.utils.DictUtils;
import com.robotmonitor.config.domain.ConfigArea;
import com.robotmonitor.config.domain.ConfigAreaDetail;
import com.robotmonitor.config.domain.ConfigTable;
import com.robotmonitor.config.dto.ConfigAreaDto;
import com.robotmonitor.config.service.IConfigAreaService;
import com.robotmonitor.config.service.IConfigRegionService;
import com.robotmonitor.config.service.IConfigRobotService;
import com.robotmonitor.config.service.IConfigTableService;
import com.robotmonitor.flight.domain.FlightInfo;
import com.robotmonitor.flight.domain.Passenger;
import com.robotmonitor.flight.domain.PassengerLocationLog;
import com.robotmonitor.flight.domain.PassengerWarningLog;
import com.robotmonitor.flight.domain.digitalTwin.DigitalTwinDto;
import com.robotmonitor.flight.domain.digitalTwin.InspectionDto;
import com.robotmonitor.flight.domain.digitalTwin.PassengerDto;
import com.robotmonitor.flight.domain.digitalTwin.RobotDto;
import com.robotmonitor.flight.domain.digitalTwin.TableDto;
import com.robotmonitor.flight.domain.digitalTwin.WarningDto;
import com.robotmonitor.flight.mapper.DigitalTwinMapper;
import com.robotmonitor.flight.mapper.PassengerLocationLogMapper;
import com.robotmonitor.flight.service.IDigitalTwinService;
import com.robotmonitor.flight.service.IFlightInfoService;
import com.robotmonitor.flight.service.IPassengerService;
import com.robotmonitor.flight.service.IPassengerWarningLogService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DigitalTwinServiceImpl
implements IDigitalTwinService {
    @Autowired
    private IConfigRegionService configRegionService;
    @Autowired
    private PassengerLocationLogMapper locationLogMapper;
    @Autowired
    private IConfigAreaService configAreaService;
    @Autowired
    private IPassengerService passengerService;
    @Autowired
    private IFlightInfoService flightInfoService;
    @Autowired
    private IPassengerWarningLogService passengerWarningLogService;
    @Autowired
    private DigitalTwinMapper digitalTwinMapper;
    @Autowired
    private IConfigTableService configTableService;
    @Autowired
    private IConfigRobotService configRobotService;

    @Override
    public List<ConfigRegion> selectRegionList(String roomCode) {
        ConfigRegion param = new ConfigRegion();
        param.setRoomCode(roomCode);
        param.setIsShow("1");
        param.setEnable(1);
        List list = this.configRegionService.selectConfigRegionList(param);
        List<PassengerLocationLog> logList = this.locationLogMapper.selectCurrentPassengerList(roomCode);
        for (ConfigRegion info : list) {
            Long count = logList.stream().filter(s -> s.getRegionId().equals(info.getId())).count();
            info.setCurCapacity(count);
            if (!ObjectUtils.isNotEmpty((Object)info.getMaxCapacity()) || info.getMaxCapacity() <= 0L) continue;
            info.setPerCapacity((double)Math.round(count * 100L / info.getMaxCapacity()));
        }
        return list;
    }

    @Override
    public List<ConfigAreaDto> selectAreaList(String roomCode, String languageType) {
        List dtoList = this.configAreaService.selectAreaListForDigitalTwin(roomCode, languageType);
        for (ConfigAreaDto info : dtoList) {
            Long count = this.locationLogMapper.getCountByArea(info.getId());
            info.setCurCapacity(count);
            if (!ObjectUtils.isNotEmpty((Object)info.getMaxCapacity()) || info.getMaxCapacity() <= 0L) continue;
            info.setPerCapacity((double)Math.round(count * 100L / info.getMaxCapacity()));
        }
        return dtoList;
    }

    @Override
    public Long robotGuide(Long areaId) {
        return 1L;
    }

    @Override
    public DigitalTwinDto getAll(String roomCode) {
        DigitalTwinDto dto = new DigitalTwinDto();
        dto.setPassengerList(this.getPassengerList(roomCode));
        dto.setInspectionList(this.getInspectionList(roomCode));
        dto.setTableList(this.getTableList(roomCode));
        dto.setRobotList(this.getRobotList(roomCode));
        return dto;
    }

    List<PassengerDto> getPassengerList(String roomCode) {
        Passenger pa = new Passenger();
        pa.setRoomCode(roomCode);
        List<Passenger> list = this.passengerService.selectPassengerInLoungeList(pa);
        List<PassengerWarningLog> warningLogList = this.passengerWarningLogService.selectCurWarningLogList();
        ArrayList<PassengerDto> dtoList = new ArrayList<PassengerDto>();
        for (Passenger p : list) {
            PassengerDto dto = new PassengerDto();
            dto.setPid(p.getPid());
            dto.setRoomCode(p.getRoomCode());
            dto.setMemLevel(p.getMemLevel());
            dto.setId(p.getId());
            dto.setUserName(p.getUserName());
            dto.setCoordinate(p.getCoordinate());
            dto.setFlightNo(p.getFlightNo());
            dto.setFlightDate(p.getFlightDate());
            dto.setRegionId(p.getRegionId());
            FlightInfo flight = this.flightInfoService.selectFlightInfoByFlightId(p.getFlightId());
            if (ObjectUtils.isNotEmpty((Object)flight.getEstmTakeOffTime()) && flight.getEstmTakeOffTime().length() == 14) {
                dto.setEstmTakeOffTime(flight.getEstmTakeOffTime().substring(8, 10) + ":" + flight.getEstmTakeOffTime().substring(10, 12));
            }
            dto.setLatestOffStatus(DictUtils.getDictLabel((String)"flight_status", (String)flight.getLatestOffStatus()));
            dto.setFlightId(flight.getFlightId());
            ConfigRegion region = this.configRegionService.selectConfigRegionById(p.getRegionId());
            if (ObjectUtils.isNotEmpty((Object)region)) {
                dto.setCoordinate(region.getCoordinate());
            }
            List tmplogList = warningLogList.stream().filter(x -> x.getPassengerId().equals(p.getId())).collect(Collectors.toList());
            List tmplogList2 = tmplogList.stream().filter(x -> !x.getIsSuccess().equals("1")).collect(Collectors.toList());
            for (PassengerWarningLog log : tmplogList) {
                log.setWarningType(DictUtils.getDictLabel((String)"flight_change_type", (String)log.getWarningType()));
                log.setNoticeType(DictUtils.getDictLabel((String)"notice_type", (String)log.getNoticeType()));
            }
            ArrayList<WarningDto> warningDtoList = new ArrayList<WarningDto>();
            for (PassengerWarningLog log : tmplogList) {
                String status;
                WarningDto warningDto = new WarningDto();
                warningDto.setWarningType(log.getWarningType());
                warningDto.setPassengerId(log.getPassengerId());
                warningDto.setFlightId(log.getFlightId());
                warningDto.setRegionId(log.getRegionId());
                warningDto.setIsSuccess(log.getIsSuccess());
                warningDto.setFlightWarningId(log.getFlightWarningId());
                warningDto.setNoticeType(log.getNoticeType());
                warningDto.setWarningInfo(log.getWarningInfo());
                warningDto.setId(log.getId());
                if (log.getNoticeType().equals("2") && ((status = this.digitalTwinMapper.selectTaskStatusById(log.getRobotTaskId())).equals("1") || status.equals("2"))) {
                    warningDto.setNoticeType(DictUtils.getDictLabel((String)"notice_type", (String)"3"));
                }
                warningDtoList.add(warningDto);
            }
            dto.setWarningLogList(warningDtoList);
            if (ObjectUtils.isNotEmpty(tmplogList2)) {
                dto.setIsHaveNotice(true);
            }
            dtoList.add(dto);
        }
        List<PassengerDto> reList = dtoList.stream().sorted(Comparator.comparing(PassengerDto::getEstmTakeOffTime)).collect(Collectors.toList());
        return reList;
    }

    public List<InspectionDto> getInspectionList(String roomCode) {
        List<InspectionDto> list = this.digitalTwinMapper.getInspectionList(roomCode);
        for (InspectionDto d : list) {
            String[] regionIds = d.getRegion().split(",");
            if (ObjectUtils.isNotEmpty((Object)regionIds) && regionIds.length > 0) {
                ConfigRegion region = this.configRegionService.selectConfigRegionById(Long.valueOf(Long.parseLong(regionIds[regionIds.length - 1])));
                d.setCoordinate(region.getCoordinate());
                ConfigArea area = this.configAreaService.selectConfigAreaById(region.getAreaId());
                if (ObjectUtils.isNotEmpty((Object)area)) {
                    d.setAreaName(area.getAreaName());
                }
            }
            if (!ObjectUtils.isNotEmpty((Object)d.getAbnormal())) continue;
            d.setAbnormal(DictUtils.getDictLabel((String)"insp_result_abnormal", (String)d.getAbnormal()));
        }
        return list;
    }

    @Override
    public void handleInspection(String id) {
        this.digitalTwinMapper.handleInspection(id);
    }

    List<TableDto> getTableList(String roomCode) {
        ConfigTable para = new ConfigTable();
        para.setRoomCode(roomCode);
        para.setIsEnable("1");
        List list = this.configTableService.selectConfigTableList(para);
        ArrayList<TableDto> relist = new ArrayList<TableDto>();
        for (ConfigTable table : list) {
            List detaiList;
            TableDto dto = new TableDto();
            dto.setId(table.getId());
            if (table.getStatus().equals("1")) {
                dto.setStatus("\u7ffb\u53f0");
            }
            dto.setTableNo(table.getTableNo());
            dto.setDeviceId(table.getDeviceId());
            if (ObjectUtils.isNotEmpty((Object)table.getRegionId()) && ObjectUtils.isNotEmpty((Object)(detaiList = this.configAreaService.selectAreaByRegionId(table.getRegionId()))) && detaiList.size() > 0) {
                dto.setAreaName(((ConfigAreaDetail)detaiList.get(0)).getAreaName());
            }
            relist.add(dto);
        }
        return relist;
    }

    List<RobotDto> getRobotList(String roomCode) {
        ConfigRobot para = new ConfigRobot();
        para.setIsDelete("0");
        para.setRoomCode(roomCode);
        List list = this.configRobotService.selectConfigRobotList(para);
        ArrayList<RobotDto> reList = new ArrayList<RobotDto>();
        for (ConfigRobot info : list) {
            RobotDto dto = new RobotDto();
            dto.setRobotName(info.getRobotName());
            dto.setRobotId(info.getRobotId());
            dto.setRegionId(info.getRegionId());
            ConfigRegion region = this.configRegionService.selectConfigRegionById(info.getRegionId());
            if (ObjectUtils.isNotEmpty((Object)region)) {
                dto.setCoordinate(region.getCoordinate());
            }
            reList.add(dto);
        }
        return reList;
    }
}
