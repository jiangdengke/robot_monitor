package com.robotmonitor.web.controller.flight;

import static com.robotmonitor.jooq.generated.Tables.CONFIG_AREA;
import static com.robotmonitor.jooq.generated.Tables.CONFIG_REGION;
import static com.robotmonitor.jooq.generated.Tables.CONFIG_ROBOT;
import static com.robotmonitor.jooq.generated.Tables.CONFIG_TABLE;
import static com.robotmonitor.jooq.generated.Tables.FLIGHT_INFO;
import static com.robotmonitor.jooq.generated.Tables.INSP_TASK_RESULT;
import static com.robotmonitor.jooq.generated.Tables.PASSENGER;
import static com.robotmonitor.jooq.generated.Tables.PASSENGER_WARNING_LOG;
import static com.robotmonitor.jooq.generated.Tables.ROBOT_TASK;

import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.domain.config.ConfigRegion;
import com.robotmonitor.common.core.domain.config.ConfigRobot;
import com.robotmonitor.common.core.page.TableDataInfo;
import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.config.service.IConfigRegionService;
import com.robotmonitor.config.service.IConfigRobotService;
import com.robotmonitor.flight.domain.FlightInfo;
import com.robotmonitor.flight.domain.Passenger;
import com.robotmonitor.flight.domain.PassengerWarningLog;
import com.robotmonitor.flight.domain.digitalTwin.DigitalTwinDto;
import com.robotmonitor.flight.domain.digitalTwin.InspectionDto;
import com.robotmonitor.flight.domain.digitalTwin.NoticeDto;
import com.robotmonitor.flight.domain.digitalTwin.PassengerDto;
import com.robotmonitor.flight.domain.digitalTwin.RobotDto;
import com.robotmonitor.flight.domain.digitalTwin.TableDto;
import com.robotmonitor.flight.domain.digitalTwin.WarningDto;
import com.robotmonitor.flight.domain.dto.PassengerInLoungeDTO;
import com.robotmonitor.flight.service.IDigitalTwinService;
import com.robotmonitor.flight.service.IFlightInfoService;
import com.robotmonitor.flight.service.IPassengerService;
import com.robotmonitor.flight.service.IPassengerWarningLogService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
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
    private DSLContext dsl;

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
        return AjaxResult.success(buildDigitalTwin(roomCode));
    }

    @PostMapping({"/handleInspection"})
    public AjaxResult handleInspection(@RequestParam("id") String id) {
        Long resultId = parseLong(id);
        if (resultId == null) {
            return AjaxResult.error("巡检结果 ID 不能为空");
        }
        return AjaxResult.success(dsl.update(INSP_TASK_RESULT)
            .set(INSP_TASK_RESULT.ABNORMAL, "2")
            .where(INSP_TASK_RESULT.ID.eq(resultId).or(INSP_TASK_RESULT.INSP_TASK_ID.eq(resultId)))
            .execute());
    }

    @GetMapping({"/guide"})
    public AjaxResult guide(
        @RequestParam("robotId") String robotId,
        @RequestParam("areaId") Long areaId,
        @RequestParam(value = "languageType", required = false) String languageType
    ) {
        ConfigRegion region = configRegionService.selectConfigRegionById(areaId);
        Map<String, Object> task = createMockTask(robotId, "数字孪生区域引导", "guide", region == null ? null : region.getCoordinate(), languageType);
        return AjaxResult.success(task);
    }

    @GetMapping({"/interruptGuideTask"})
    public AjaxResult interruptGuideTask(@RequestParam("robotId") String robotId) {
        dsl.update(CONFIG_ROBOT)
            .set(CONFIG_ROBOT.WORKING_STATE, "idle")
            .set(CONFIG_ROBOT.TASK_STATUS, "4")
            .where(CONFIG_ROBOT.ROBOT_ID.eq(robotId).or(CONFIG_ROBOT.ID.eq(parseLong(robotId))))
            .execute();
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
            ConfigRobot fallback = new ConfigRobot();
            fallback.setRobotType("多功能机器人");
            fallback.setEnable(1L);
            robotList = configRobotService.selectConfigRobotList(fallback);
        }
        if (ObjectUtils.isEmpty(robotList) || robotList.isEmpty()) {
            return AjaxResult.error("没有可用的机器人");
        }
        Map<String, Object> task = createMockTask(robotList.get(0).getRobotId(), "数字孪生旅客提醒", "notify", dto.getCoordinate(), dto.getWarningInfo());
        Long taskId = (Long) task.get("id");
        if (dto.getWarningId() != null) {
            dsl.update(PASSENGER_WARNING_LOG)
                .set(PASSENGER_WARNING_LOG.ROBOT_TASK_ID, taskId)
                .set(PASSENGER_WARNING_LOG.NOTICE_TYPE, "2")
                .set(PASSENGER_WARNING_LOG.IS_SUCCESS, "0")
                .where(PASSENGER_WARNING_LOG.ID.eq(dto.getWarningId()))
                .execute();
        }
        return AjaxResult.success(task);
    }

    @PostMapping({"/manualNotice"})
    public AjaxResult manualNotice(NoticeDto dto) {
        if (dto.getWarningId() != null) {
            dsl.update(PASSENGER_WARNING_LOG)
                .set(PASSENGER_WARNING_LOG.NOTICE_TYPE, "1")
                .set(PASSENGER_WARNING_LOG.IS_SUCCESS, "1")
                .where(PASSENGER_WARNING_LOG.ID.eq(dto.getWarningId()))
                .execute();
        }
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("warningId", dto.getWarningId());
        result.put("status", "success");
        result.put("mock", true);
        return AjaxResult.success(result);
    }

    private Map<String, Object> createMockTask(String robotId, String taskName, String action, String target, String payload) {
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        Long id = dsl.insertInto(ROBOT_TASK)
            .set(ROBOT_TASK.ROBOT_ID, robotId)
            .set(ROBOT_TASK.TASK_NAME, taskName)
            .set(ROBOT_TASK.TASK_TYPE, "0")
            .set(ROBOT_TASK.TASK_SUBTYPE, action)
            .set(ROBOT_TASK.TASK_MODE, "mock")
            .set(ROBOT_TASK.TASK_STATUS, "2")
            .set(ROBOT_TASK.DIRECT_EXECUTION, "1")
            .set(ROBOT_TASK.CREATE_TIME, now)
            .set(ROBOT_TASK.START_TIME, now)
            .set(ROBOT_TASK.RETURN_INFO, "本地 mock 任务已提交")
            .set(ROBOT_TASK.CMD, "{action=" + action + ",target=" + target + ",payload=" + payload + "}")
            .returningResult(ROBOT_TASK.ID)
            .fetchOne(ROBOT_TASK.ID);
        dsl.update(CONFIG_ROBOT)
            .set(CONFIG_ROBOT.TASK_ID, id)
            .set(CONFIG_ROBOT.TASK_STATUS, "2")
            .set(CONFIG_ROBOT.WORKING_STATE, action)
            .where(CONFIG_ROBOT.ROBOT_ID.eq(robotId).or(CONFIG_ROBOT.ID.eq(parseLong(robotId))))
            .execute();
        Map<String, Object> task = new LinkedHashMap<>();
        task.put("id", id);
        task.put("robotId", robotId);
        task.put("taskName", taskName);
        task.put("taskStatus", "2");
        task.put("action", action);
        task.put("mock", true);
        return task;
    }

    private DigitalTwinDto buildDigitalTwin(String roomCode) {
        DigitalTwinDto dto = new DigitalTwinDto();
        Map<Long, RegionInfo> regionMap = regionInfoMap();
        dto.setPassengerList(buildPassengerList(roomCode, regionMap));
        dto.setInspectionList(buildInspectionList(roomCode, regionMap));
        dto.setTableList(buildTableList(roomCode, regionMap));
        dto.setRobotList(buildRobotList(roomCode, regionMap));
        return dto;
    }

    private List<PassengerDto> buildPassengerList(String roomCode, Map<Long, RegionInfo> regionMap) {
        Condition condition = StringUtils.isBlank(roomCode) ? DSL.trueCondition() : PASSENGER.ROOM_CODE.eq(roomCode);
        List<PassengerDto> list = dsl.select(
                PASSENGER.ID,
                PASSENGER.USER_NAME,
                PASSENGER.ROOM_CODE,
                PASSENGER.FLIGHT_NO,
                PASSENGER.FLIGHT_DATE,
                PASSENGER.MEM_LEVEL,
                PASSENGER.PID,
                PASSENGER.FLIGHT_ID,
                PASSENGER.REGION_ID,
                PASSENGER.COORDINATE,
                FLIGHT_INFO.LATEST_OFF_STATUS,
                FLIGHT_INFO.ESTM_TAKE_OFF_TIME
            )
            .from(PASSENGER)
            .leftJoin(FLIGHT_INFO)
            .on(PASSENGER.FLIGHT_ID.eq(FLIGHT_INFO.FLIGHT_ID))
            .where(condition.and(PASSENGER.STATUS.ne("0")))
            .orderBy(PASSENGER.CREATE_TIME.desc())
            .fetch(record -> {
                PassengerDto dto = new PassengerDto();
                Long regionId = record.get(PASSENGER.REGION_ID);
                RegionInfo region = regionMap.get(regionId);
                dto.setId(record.get(PASSENGER.ID));
                dto.setUserName(record.get(PASSENGER.USER_NAME));
                dto.setRoomCode(record.get(PASSENGER.ROOM_CODE));
                dto.setFlightNo(record.get(PASSENGER.FLIGHT_NO));
                LocalDate flightDate = record.get(PASSENGER.FLIGHT_DATE);
                dto.setFlightDate(flightDate == null ? null : flightDate.toString());
                dto.setMemLevel(record.get(PASSENGER.MEM_LEVEL));
                dto.setPid(record.get(PASSENGER.PID));
                dto.setFlightId(record.get(PASSENGER.FLIGHT_ID));
                dto.setRegionId(regionId);
                dto.setCoordinate(firstText(record.get(PASSENGER.COORDINATE), region == null ? null : region.coordinate));
                dto.setLatestOffStatus(record.get(FLIGHT_INFO.LATEST_OFF_STATUS));
                dto.setEstmTakeOffTime(formatTime(record.get(FLIGHT_INFO.ESTM_TAKE_OFF_TIME)));
                return dto;
            });
        Map<Long, List<WarningDto>> warningMap = warningMap();
        for (PassengerDto dto : list) {
            List<WarningDto> warnings = warningMap.getOrDefault(dto.getId(), List.of());
            dto.setWarningLogList(warnings);
            dto.setIsHaveNotice(warnings.stream().anyMatch(warning -> !"1".equals(warning.getIsSuccess())));
        }
        return list;
    }

    private List<InspectionDto> buildInspectionList(String roomCode, Map<Long, RegionInfo> regionMap) {
        List<Long> roomRegionIds = regionMap.values().stream()
            .filter(region -> StringUtils.isBlank(roomCode) || roomCode.equals(region.roomCode))
            .map(region -> region.id)
            .toList();
        return dsl.select(
                INSP_TASK_RESULT.ID,
                INSP_TASK_RESULT.INSP_TASK_ID,
                INSP_TASK_RESULT.ROBOT_ID,
                INSP_TASK_RESULT.TYPE,
                INSP_TASK_RESULT.POINT,
                INSP_TASK_RESULT.ABNORMAL,
                INSP_TASK_RESULT.ABNORMAL_INFO,
                INSP_TASK_RESULT.IMAGE_BASE64,
                CONFIG_ROBOT.REGION_ID
            )
            .from(INSP_TASK_RESULT)
            .leftJoin(CONFIG_ROBOT)
            .on(INSP_TASK_RESULT.ROBOT_ID.eq(CONFIG_ROBOT.ROBOT_ID))
            .where(roomRegionIds.isEmpty() ? DSL.trueCondition() : CONFIG_ROBOT.REGION_ID.in(roomRegionIds))
            .orderBy(INSP_TASK_RESULT.CREATE_TIME.desc())
            .limit(100)
            .fetch(record -> {
                Long regionId = record.get(CONFIG_ROBOT.REGION_ID);
                RegionInfo region = regionMap.get(regionId);
                InspectionDto dto = new InspectionDto();
                dto.setInspTaskId(firstLong(record.get(INSP_TASK_RESULT.INSP_TASK_ID), record.get(INSP_TASK_RESULT.ID)));
                dto.setRobotId(record.get(INSP_TASK_RESULT.ROBOT_ID));
                dto.setType(record.get(INSP_TASK_RESULT.TYPE));
                dto.setPoint(record.get(INSP_TASK_RESULT.POINT));
                dto.setAbnormal(labelAbnormal(record.get(INSP_TASK_RESULT.ABNORMAL)));
                dto.setAbnormalInfo(record.get(INSP_TASK_RESULT.ABNORMAL_INFO));
                dto.setImageBase64(record.get(INSP_TASK_RESULT.IMAGE_BASE64));
                dto.setRegion(regionId == null ? null : String.valueOf(regionId));
                dto.setAreaName(region == null ? null : region.areaName);
                dto.setCoordinate(region == null ? null : region.coordinate);
                return dto;
            });
    }

    private List<TableDto> buildTableList(String roomCode, Map<Long, RegionInfo> regionMap) {
        Condition condition = CONFIG_TABLE.IS_ENABLE.eq("1");
        if (StringUtils.isNotBlank(roomCode)) {
            condition = condition.and(CONFIG_TABLE.ROOM_CODE.eq(roomCode));
        }
        return dsl.select(
                CONFIG_TABLE.ID,
                CONFIG_TABLE.TABLE_NO,
                CONFIG_TABLE.REGION_ID,
                CONFIG_TABLE.IS_ENABLE,
                CONFIG_TABLE.DEVICE_ID,
                CONFIG_TABLE.CAMERA_COORDINATES,
                CONFIG_TABLE.STATUS
            )
            .from(CONFIG_TABLE)
            .where(condition)
            .orderBy(CONFIG_TABLE.ID.asc())
            .fetch(record -> {
                Long regionId = record.get(CONFIG_TABLE.REGION_ID);
                RegionInfo region = regionMap.get(regionId);
                TableDto dto = new TableDto();
                dto.setId(record.get(CONFIG_TABLE.ID));
                dto.setTableNo(record.get(CONFIG_TABLE.TABLE_NO));
                dto.setRegionId(regionId);
                dto.setIsEnable(record.get(CONFIG_TABLE.IS_ENABLE));
                dto.setDeviceId(record.get(CONFIG_TABLE.DEVICE_ID));
                dto.setCameraCoordinates(record.get(CONFIG_TABLE.CAMERA_COORDINATES));
                dto.setAreaName(region == null ? null : region.areaName);
                dto.setStatus("1".equals(record.get(CONFIG_TABLE.STATUS)) ? "翻台" : "空闲");
                return dto;
            });
    }

    private List<RobotDto> buildRobotList(String roomCode, Map<Long, RegionInfo> regionMap) {
        Condition condition = CONFIG_ROBOT.IS_DELETE.ne("1");
        if (StringUtils.isNotBlank(roomCode)) {
            condition = condition.and(CONFIG_ROBOT.ROOM_CODE.eq(roomCode));
        }
        return dsl.select(
                CONFIG_ROBOT.ROBOT_ID,
                CONFIG_ROBOT.ROBOT_NAME,
                CONFIG_ROBOT.REGION_ID,
                CONFIG_ROBOT.ORI_COORDINATE
            )
            .from(CONFIG_ROBOT)
            .where(condition)
            .orderBy(CONFIG_ROBOT.ID.asc())
            .fetch(record -> {
                Long regionId = record.get(CONFIG_ROBOT.REGION_ID);
                RegionInfo region = regionMap.get(regionId);
                RobotDto dto = new RobotDto();
                dto.setRobotId(record.get(CONFIG_ROBOT.ROBOT_ID));
                dto.setRobotName(record.get(CONFIG_ROBOT.ROBOT_NAME));
                dto.setRegionId(regionId);
                dto.setCoordinate(firstText(record.get(CONFIG_ROBOT.ORI_COORDINATE), region == null ? null : region.coordinate));
                return dto;
            });
    }

    private Map<Long, List<WarningDto>> warningMap() {
        Map<Long, List<WarningDto>> map = new HashMap<>();
        dsl.select(
                PASSENGER_WARNING_LOG.ID,
                PASSENGER_WARNING_LOG.PASSENGER_ID,
                PASSENGER_WARNING_LOG.FLIGHT_ID,
                PASSENGER_WARNING_LOG.FLIGHT_WARNING_ID,
                PASSENGER_WARNING_LOG.WARNING_TYPE,
                PASSENGER_WARNING_LOG.NOTICE_TYPE,
                PASSENGER_WARNING_LOG.REGION_ID,
                PASSENGER_WARNING_LOG.WARNING_INFO,
                PASSENGER_WARNING_LOG.IS_SUCCESS
            )
            .from(PASSENGER_WARNING_LOG)
            .orderBy(PASSENGER_WARNING_LOG.CREATE_TIME.desc())
            .fetch(record -> {
                WarningDto dto = new WarningDto();
                dto.setId(record.get(PASSENGER_WARNING_LOG.ID));
                dto.setPassengerId(record.get(PASSENGER_WARNING_LOG.PASSENGER_ID));
                dto.setFlightId(record.get(PASSENGER_WARNING_LOG.FLIGHT_ID));
                dto.setFlightWarningId(record.get(PASSENGER_WARNING_LOG.FLIGHT_WARNING_ID));
                dto.setWarningType(record.get(PASSENGER_WARNING_LOG.WARNING_TYPE));
                dto.setNoticeType(record.get(PASSENGER_WARNING_LOG.NOTICE_TYPE));
                dto.setRegionId(record.get(PASSENGER_WARNING_LOG.REGION_ID));
                dto.setWarningInfo(record.get(PASSENGER_WARNING_LOG.WARNING_INFO));
                dto.setIsSuccess(record.get(PASSENGER_WARNING_LOG.IS_SUCCESS));
                map.computeIfAbsent(dto.getPassengerId(), key -> new ArrayList<>()).add(dto);
                return dto;
            });
        return map;
    }

    private Map<Long, RegionInfo> regionInfoMap() {
        Map<Long, RegionInfo> map = new HashMap<>();
        dsl.select(
                CONFIG_REGION.ID,
                CONFIG_REGION.ROOM_CODE,
                CONFIG_REGION.COORDINATE,
                CONFIG_REGION.AREA_ID,
                CONFIG_AREA.AREA_NAME
            )
            .from(CONFIG_REGION)
            .leftJoin(CONFIG_AREA)
            .on(CONFIG_REGION.AREA_ID.eq(CONFIG_AREA.ID))
            .fetch(record -> {
                RegionInfo info = new RegionInfo(
                    record.get(CONFIG_REGION.ID),
                    record.get(CONFIG_REGION.ROOM_CODE),
                    record.get(CONFIG_REGION.COORDINATE),
                    record.get(CONFIG_REGION.AREA_ID),
                    record.get(CONFIG_AREA.AREA_NAME)
                );
                map.put(info.id, info);
                return info;
            });
        return map;
    }

    private String formatTime(LocalDateTime time) {
        return time == null ? null : String.format("%02d:%02d", time.getHour(), time.getMinute());
    }

    private String labelAbnormal(String value) {
        if ("0".equals(value)) {
            return "无异常";
        }
        if ("2".equals(value)) {
            return "已处理";
        }
        return StringUtils.isBlank(value) ? "异常" : "异常";
    }

    private Long firstLong(Long first, Long second) {
        return first == null ? second : first;
    }

    private String firstText(String first, String second) {
        return StringUtils.isNotBlank(first) ? first : second;
    }

    private Long parseLong(String value) {
        try {
            return value == null || value.isBlank() ? null : Long.valueOf(value);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private record RegionInfo(Long id, String roomCode, String coordinate, Long areaId, String areaName) {
    }
}
