/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.bot.domain.CustomerNotificationRequest
 *  com.robotmonitor.bot.domain.RobotMoveRequest
 *  com.robotmonitor.bot.service.RobotService
 *  com.robotmonitor.common.core.controller.BaseController
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.core.domain.config.ConfigRegion
 *  com.robotmonitor.common.core.domain.config.ConfigRobot
 *  com.robotmonitor.common.core.domain.robot.RobotTask
 *  com.robotmonitor.common.core.page.TableDataInfo
 *  com.robotmonitor.common.utils.DateUtils
 *  com.robotmonitor.config.service.IConfigRegionService
 *  com.robotmonitor.config.service.IConfigRobotService
 *  com.robotmonitor.flight.domain.FlightInfo
 *  com.robotmonitor.flight.domain.Passenger
 *  com.robotmonitor.flight.domain.PassengerWarningLog
 *  com.robotmonitor.flight.domain.digitalTwin.DigitalTwinDto
 *  com.robotmonitor.flight.domain.digitalTwin.NoticeDto
 *  com.robotmonitor.flight.domain.dto.PassengerInLoungeDTO
 *  com.robotmonitor.flight.service.IDigitalTwinService
 *  com.robotmonitor.flight.service.IFlightInfoService
 *  com.robotmonitor.flight.service.IPassengerService
 *  com.robotmonitor.flight.service.IPassengerWarningLogService
 *  org.apache.commons.lang3.ObjectUtils
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.robotmonitor.web.controller.flight;

import com.robotmonitor.bot.domain.CustomerNotificationRequest;
import com.robotmonitor.bot.domain.RobotMoveRequest;
import com.robotmonitor.bot.service.RobotService;
import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.domain.config.ConfigRegion;
import com.robotmonitor.common.core.domain.config.ConfigRobot;
import com.robotmonitor.common.core.domain.robot.RobotTask;
import com.robotmonitor.common.core.page.TableDataInfo;
import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.config.service.IConfigRegionService;
import com.robotmonitor.config.service.IConfigRobotService;
import com.robotmonitor.flight.domain.FlightInfo;
import com.robotmonitor.flight.domain.Passenger;
import com.robotmonitor.flight.domain.PassengerWarningLog;
import com.robotmonitor.flight.domain.digitalTwin.DigitalTwinDto;
import com.robotmonitor.flight.domain.digitalTwin.NoticeDto;
import com.robotmonitor.flight.domain.dto.PassengerInLoungeDTO;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/DigitalTwin"})
public class DigitalTwinCotroller
extends BaseController {
    @Autowired
    private IDigitalTwinService digitalTwinService;
    @Autowired
    private IPassengerService passengerService;
    @Autowired
    private IFlightInfoService flightInfoService;
    @Autowired
    private IConfigRegionService configRegionService;
    @Autowired
    private IPassengerWarningLogService passengerWarningLogService;
    @Autowired
    private RobotService robotService;
    @Autowired
    private IConfigRobotService configRobotService;

    @GetMapping(value={"/selectRegionList"})
    public AjaxResult selectRegionList(String roomCode) {
        List list = this.digitalTwinService.selectRegionList(roomCode);
        return AjaxResult.success((Object)list);
    }

    @GetMapping(value={"/selectAreaList"})
    public AjaxResult selectAreaList(String roomCode, String languageType) {
        return AjaxResult.success((Object)this.digitalTwinService.selectAreaList(roomCode, languageType));
    }

    public AjaxResult robotGuide(Long areaId) {
        return AjaxResult.success((Object)this.digitalTwinService.robotGuide(areaId));
    }

    private Long[] stringToLongArray(String ss) {
        String[] ids = ss.split(",");
        Long[] ids_long = new Long[ids.length];
        for (int i = 0; i < ids.length; ++i) {
            ids_long[i] = Long.valueOf(ids[i]);
        }
        return ids_long;
    }

    @GetMapping(value={"/passengerList"})
    public TableDataInfo passengerList(String roomCode) {
        Passenger pa = new Passenger();
        pa.setRoomCode(roomCode);
        List<Passenger> list = this.passengerService.selectPassengerInLoungeList(pa);
        List<PassengerWarningLog> warningLogList = this.passengerWarningLogService.selectCurWarningLogList();
        ArrayList<PassengerInLoungeDTO> dtoList = new ArrayList<PassengerInLoungeDTO>();
        for (Passenger p : list) {
            PassengerInLoungeDTO dto = new PassengerInLoungeDTO();
            dto.setId(p.getId());
            dto.setUserName(p.getUserName());
            dto.setCoordinate(p.getCoordinate());
            dto.setFlightNo(p.getFlightNo());
            dto.setFlightDate(DateUtils.parseDate((Object)p.getFlightDate()));
            dto.setOriImageUrl(p.getOrigImageUrl());
            dto.setRegisterImageUrl(p.getRegisterImageUrl());
            dto.setUpdateTime(p.getUpdateTime());
            dto.setCreateTime(p.getCreateTime());
            dto.setRegionId(p.getRegionId());
            FlightInfo flight = this.flightInfoService.selectFlightInfoByFlightId(p.getFlightId());
            dto.setEstmTakeOffTime(flight.getEstmTakeOffTime());
            dto.setLatestOnStatus(flight.getLatestOnStatus());
            dto.setFlightId(flight.getFlightId());
            ConfigRegion region = this.configRegionService.selectConfigRegionById(p.getRegionId());
            dto.setAreaName(region.getAreaName());
            dto.setIsGuide(region.getIsGuide());
            dto.setAreaId(region.getAreaId());
            List<PassengerWarningLog> tmplogList = warningLogList.stream().filter(x -> x.getPassengerId().equals(p.getId())).collect(Collectors.toList());
            List<PassengerWarningLog> tmplogList2 = tmplogList.stream().filter(x -> !"1".equals(x.getIsSuccess())).collect(Collectors.toList());
            dto.setWarningLogList(tmplogList);
            if (ObjectUtils.isNotEmpty(tmplogList2)) {
                dto.setIsHaveNotice(Boolean.valueOf(true));
            }
            dtoList.add(dto);
        }
        List<PassengerInLoungeDTO> reList = dtoList.stream().sorted(Comparator.comparing(PassengerInLoungeDTO::getEstmTakeOffTime)).collect(Collectors.toList());
        return this.getDataTable(reList);
    }

    @GetMapping(value={"/getAll"})
    public AjaxResult getAll(String roomCode) {
        DigitalTwinDto dto = this.digitalTwinService.getAll(roomCode);
        return AjaxResult.success((Object)dto);
    }

    @PostMapping(value={"/handleInspection"})
    public AjaxResult handleInspection(String id) {
        this.digitalTwinService.handleInspection(id);
        return AjaxResult.success();
    }

    @GetMapping(value={"/guide"})
    public AjaxResult guide(Long robotId, Long areaId, String languageType) {
        RobotMoveRequest para = new RobotMoveRequest();
        para.setRobotId(robotId.longValue());
        para.setAreaId(areaId.toString());
        para.setNeedVoice(true);
        para.setLanguage(languageType);
        return AjaxResult.success((Object)this.robotService.guide(para));
    }

    @GetMapping(value={"/interruptGuideTask"})
    public AjaxResult interruptGuideTask(String robotId) {
        this.robotService.interruptGuideTask(robotId);
        return AjaxResult.success();
    }

    @GetMapping(value={"/notifyCustomer"})
    public AjaxResult notifyCustomer(NoticeDto dto) {
        ConfigRobot ro = new ConfigRobot();
        ro.setRoomCode(dto.getRoomCode());
        ro.setRobotType("\u591a\u529f\u80fd\u673a\u5668\u4eba");
        ro.setEnable(Long.valueOf(1L));
        List<ConfigRobot> robotList = this.configRobotService.selectConfigRobotList(ro);
        if (ObjectUtils.isEmpty((Object)robotList) || robotList.size() == 0) {
            return AjaxResult.error((String)"\u6ca1\u6709\u53ef\u7528\u7684\u673a\u5668\u4eba");
        }
        CustomerNotificationRequest re = new CustomerNotificationRequest();
        re.setRobotId(((ConfigRobot)robotList.get(0)).getRobotId());
        re.setText(dto.getWarningInfo());
        re.setCoordinate(dto.getCoordinate());
        RobotTask task = this.robotService.notifyCustomer(re);
        PassengerWarningLog log = new PassengerWarningLog();
        log.setId(dto.getWarningId());
        log.setRobotTaskId(task.getId());
        log.setNoticeType("2");
        this.passengerWarningLogService.updatePassengerWarningLog(log);
        return AjaxResult.success();
    }
}
