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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/DigitalTwin"})
public class DigitalTwinCotroller extends BaseController {
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

    @GetMapping({"/selectRegionList"})
    public AjaxResult selectRegionList(@RequestParam(value = "roomCode", required = false) String roomCode) {
        List<ConfigRegion> list = digitalTwinService.selectRegionList(roomCode);
        return AjaxResult.success(list);
    }

    @GetMapping({"/selectAreaList"})
    public AjaxResult selectAreaList(
        @RequestParam(value = "roomCode", required = false) String roomCode,
        @RequestParam(value = "languageType", required = false) String languageType
    ) {
        return AjaxResult.success(digitalTwinService.selectAreaList(roomCode, languageType));
    }

    public AjaxResult robotGuide(@RequestParam("areaId") Long areaId) {
        return AjaxResult.success(digitalTwinService.robotGuide(areaId));
    }

    @GetMapping({"/passengerList"})
    public TableDataInfo passengerList(@RequestParam(value = "roomCode", required = false) String roomCode) {
        Passenger pa = new Passenger();
        pa.setRoomCode(roomCode);
        List<Passenger> list = passengerService.selectPassengerInLoungeList(pa);
        List<PassengerWarningLog> warningLogList = passengerWarningLogService.selectCurWarningLogList();
        ArrayList<PassengerInLoungeDTO> dtoList = new ArrayList<>();
        for (Passenger p : list) {
            PassengerInLoungeDTO dto = new PassengerInLoungeDTO();
            dto.setId(p.getId());
            dto.setUserName(p.getUserName());
            dto.setCoordinate(p.getCoordinate());
            dto.setFlightNo(p.getFlightNo());
            dto.setFlightDate(DateUtils.parseDate(p.getFlightDate()));
            dto.setOriImageUrl(p.getOrigImageUrl());
            dto.setRegisterImageUrl(p.getRegisterImageUrl());
            dto.setUpdateTime(p.getUpdateTime());
            dto.setCreateTime(p.getCreateTime());
            dto.setRegionId(p.getRegionId());
            FlightInfo flight = flightInfoService.selectFlightInfoByFlightId(p.getFlightId());
            if (flight != null) {
                dto.setEstmTakeOffTime(flight.getEstmTakeOffTime());
                dto.setLatestOnStatus(flight.getLatestOnStatus());
                dto.setFlightId(flight.getFlightId());
            }
            ConfigRegion region = configRegionService.selectConfigRegionById(p.getRegionId());
            if (region != null) {
                dto.setAreaName(region.getAreaName());
                dto.setIsGuide(region.getIsGuide());
                dto.setAreaId(region.getAreaId());
            }
            List<PassengerWarningLog> tmplogList = warningLogList.stream()
                .filter(x -> x.getPassengerId() != null && x.getPassengerId().equals(p.getId()))
                .collect(Collectors.toList());
            List<PassengerWarningLog> tmplogList2 = tmplogList.stream()
                .filter(x -> !"1".equals(x.getIsSuccess()))
                .collect(Collectors.toList());
            dto.setWarningLogList(tmplogList);
            if (ObjectUtils.isNotEmpty(tmplogList2)) {
                dto.setIsHaveNotice(Boolean.TRUE);
            }
            dtoList.add(dto);
        }
        List<PassengerInLoungeDTO> reList = dtoList.stream()
            .sorted(Comparator.comparing(PassengerInLoungeDTO::getEstmTakeOffTime, Comparator.nullsLast(String::compareTo)))
            .collect(Collectors.toList());
        return getDataTable(reList);
    }

    @GetMapping({"/getAll"})
    public AjaxResult getAll(@RequestParam(value = "roomCode", required = false) String roomCode) {
        DigitalTwinDto dto = digitalTwinService.getAll(roomCode);
        return AjaxResult.success(dto);
    }

    @PostMapping({"/handleInspection"})
    public AjaxResult handleInspection(@RequestParam("id") String id) {
        digitalTwinService.handleInspection(id);
        return AjaxResult.success();
    }

    @GetMapping({"/guide"})
    public AjaxResult guide(
        @RequestParam("robotId") Long robotId,
        @RequestParam("areaId") Long areaId,
        @RequestParam(value = "languageType", required = false) String languageType
    ) {
        RobotMoveRequest para = new RobotMoveRequest();
        para.setRobotId(robotId.longValue());
        para.setAreaId(areaId.toString());
        para.setNeedVoice(true);
        para.setLanguage(languageType);
        return AjaxResult.success(robotService.guide(para));
    }

    @GetMapping({"/interruptGuideTask"})
    public AjaxResult interruptGuideTask(@RequestParam("robotId") String robotId) {
        robotService.interruptGuideTask(robotId);
        return AjaxResult.success();
    }

    @GetMapping({"/notifyCustomer"})
    public AjaxResult notifyCustomer(NoticeDto dto) {
        ConfigRobot ro = new ConfigRobot();
        ro.setRoomCode(dto.getRoomCode());
        ro.setRobotType("多功能机器人");
        ro.setEnable(1L);
        List<ConfigRobot> robotList = configRobotService.selectConfigRobotList(ro);
        if (ObjectUtils.isEmpty(robotList) || robotList.isEmpty()) {
            return AjaxResult.error("没有可用的机器人");
        }
        CustomerNotificationRequest re = new CustomerNotificationRequest();
        re.setRobotId(robotList.get(0).getRobotId());
        re.setText(dto.getWarningInfo());
        re.setCoordinate(dto.getCoordinate());
        RobotTask task = robotService.notifyCustomer(re);
        PassengerWarningLog log = new PassengerWarningLog();
        log.setId(dto.getWarningId());
        log.setRobotTaskId(task.getId());
        log.setNoticeType("2");
        passengerWarningLogService.updatePassengerWarningLog(log);
        return AjaxResult.success();
    }
}
