package com.robotmonitor.web.controller.flight;

import static com.robotmonitor.jooq.generated.Tables.PASSENGER;

import com.robotmonitor.common.annotation.Log;
import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.page.TableDataInfo;
import com.robotmonitor.common.enums.BusinessType;
import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.common.utils.poi.ExcelUtil;
import com.robotmonitor.flight.domain.AuthResponse;
import com.robotmonitor.flight.domain.BarCodeRespons;
import com.robotmonitor.flight.domain.CollectInParam2;
import com.robotmonitor.flight.domain.CollectInResponse2;
import com.robotmonitor.flight.domain.Passenger;
import com.robotmonitor.flight.domain.PassengerParam;
import com.robotmonitor.flight.domain.PassengerStatistics;
import com.robotmonitor.flight.domain.dto.FlightChangePassengerDTO;
import com.robotmonitor.flight.domain.dto.PassengerInLoungeDTO;
import com.robotmonitor.flight.domain.dto.PassengerOutgoingDTO;
import com.robotmonitor.flight.service.IPassengerService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/flight/passenger"})
public class TPassengerController extends BaseController {
    @Autowired
    private IPassengerService passengerService;
    @Autowired
    private DSLContext dsl;

    @PostMapping({"/getAuth"})
    public AjaxResult getAuth(String employeeNo) {
        AuthResponse info = passengerService.getAuth(employeeNo);
        return info == null ? AjaxResult.error("未获取到LTS登录令牌") : AjaxResult.success(info);
    }

    @PostMapping({"/BarCode"})
    public AjaxResult barCode(String robotId, String barCode, String inType) {
        BarCodeRespons info = passengerService.barCode(robotId, barCode, inType);
        return info == null ? AjaxResult.error("未获取到登机牌信息") : AjaxResult.success(info);
    }

    @PostMapping({"/BarCode2"})
    public AjaxResult barCode2(@RequestBody CollectInParam2 param) {
        CollectInResponse2 info = passengerService.barCode2(param);
        return info == null || !"1".equals(info.getCode()) ? AjaxResult.error("未获取到旅客信息") : AjaxResult.success(info);
    }

    @PostMapping({"/passengerGetIn"})
    public AjaxResult passengerGetIn(@RequestBody CollectInResponse2 param) {
        passengerService.passengerGetIn2(param.getParam().getRobotId(), param);
        return AjaxResult.success();
    }

    @PostMapping({"/passengerGetOut"})
    public AjaxResult passengerGetOut(String reId, String oriImgUrl, Long regionId) {
        return toAjax(passengerService.setPassengerGetOut(reId, oriImgUrl, regionId));
    }

    @PostMapping({"/checkout/{id}"})
    public AjaxResult checkout(@PathVariable("id") Long id) {
        Passenger passenger = passengerService.selectPassengerById(id);
        if (passenger == null) {
            return AjaxResult.error("旅客不存在");
        }
        return toAjax(dsl.update(PASSENGER)
            .set(PASSENGER.STATUS, "0")
            .set(PASSENGER.GET_OUT_TIME, java.time.LocalDateTime.now())
            .where(PASSENGER.ID.eq(id))
            .execute());
    }

    @GetMapping({"/list"})
    public TableDataInfo list(Passenger passenger) {
        startPage();
        return getDataTable(passengerService.selectPassengerList(passenger));
    }

    @Log(title = "旅客信息", businessType = BusinessType.EXPORT)
    @PostMapping({"/export"})
    public void export(HttpServletResponse response, Passenger passenger) {
        List<Passenger> list = passengerService.selectPassengerList(passenger);
        ExcelUtil<Passenger> util = new ExcelUtil<>(Passenger.class);
        util.exportExcel(response, list, "旅客信息数据");
    }

    @GetMapping({"/{id}"})
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(passengerService.selectPassengerById(id));
    }

    @Log(title = "旅客信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Passenger passenger) {
        return toAjax(passengerService.insertPassenger(passenger));
    }

    @Log(title = "旅客信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Passenger passenger) {
        return toAjax(passengerService.updatePassenger(passenger));
    }

    @Log(title = "旅客信息", businessType = BusinessType.DELETE)
    @DeleteMapping({"/{ids}"})
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(passengerService.deletePassengerByIds(ids));
    }

    @GetMapping({"/outgoingList"})
    public TableDataInfo outgoingList(Passenger passenger) {
        startPage();
        List<Passenger> list = passengerService.selectPassengerOutgoingList(passenger);
        List<PassengerOutgoingDTO> dtoList = new ArrayList<>();
        for (Passenger p : list) {
            PassengerOutgoingDTO dto = new PassengerOutgoingDTO();
            dto.setUserName(p.getUserName());
            dto.setGetOutTime(p.getGetOutTime());
            dto.setFlightNo(p.getFlightNo());
            dto.setFlightDate(DateUtils.parseDate(p.getFlightDate()));
            dto.setOriImageUrl(p.getOrigImageUrl());
            dto.setRegisterImageUrl(p.getRegisterImageUrl());
            dtoList.add(dto);
        }
        return getDataTable(dtoList);
    }

    @GetMapping({"/inLoungeList"})
    public TableDataInfo inLoungeList(Passenger passenger) {
        startPage();
        List<Passenger> list = passengerService.selectPassengerInLoungeList(passenger);
        List<PassengerInLoungeDTO> dtoList = new ArrayList<>();
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
            dtoList.add(dto);
        }
        return getDataTable(dtoList);
    }

    @GetMapping({"/flightChangeList"})
    public TableDataInfo flightChangeList(Passenger passenger) {
        startPage();
        return getDataTable(passengerService.selectPassengerWithFlightChangeList(passenger));
    }

    @GetMapping({"/statistics"})
    public AjaxResult getPassengerStatistics() {
        PassengerStatistics statistics = passengerService.getPassengerStatistics();
        return AjaxResult.success(statistics);
    }

    @GetMapping({"/statisticsByInType"})
    public TableDataInfo getPassengerStatisticsByInType(PassengerParam param) {
        startPage();
        return getDataTable(passengerService.selectPassenger(param));
    }

    @GetMapping({"/passengerWarningList"})
    public TableDataInfo passengerWarningList(Passenger passenger) {
        startPage();
        List<FlightChangePassengerDTO> list = passengerService.selectPassengerWithFlightChangeList(passenger);
        return getDataTable(list);
    }
}
