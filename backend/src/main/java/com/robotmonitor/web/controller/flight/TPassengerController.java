/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Log
 *  com.robotmonitor.common.core.controller.BaseController
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.core.page.TableDataInfo
 *  com.robotmonitor.common.enums.BusinessType
 *  com.robotmonitor.common.utils.DateUtils
 *  com.robotmonitor.common.utils.poi.ExcelUtil
 *  com.robotmonitor.flight.domain.AuthResponse
 *  com.robotmonitor.flight.domain.BarCodeRespons
 *  com.robotmonitor.flight.domain.CollectInParam2
 *  com.robotmonitor.flight.domain.CollectInResponse2
 *  com.robotmonitor.flight.domain.Passenger
 *  com.robotmonitor.flight.domain.PassengerParam
 *  com.robotmonitor.flight.domain.dto.FlightChangePassengerDTO
 *  com.robotmonitor.flight.domain.dto.PassengerInLoungeDTO
 *  com.robotmonitor.flight.domain.dto.PassengerOutgoingDTO
 *  com.robotmonitor.flight.service.IPassengerService
 *  jakarta.servlet.http.HttpServletResponse
 *  org.apache.commons.lang3.ObjectUtils
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.security.access.prepost.PreAuthorize
 *  org.springframework.web.bind.annotation.DeleteMapping
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.PutMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.robotmonitor.web.controller.flight;

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
import com.robotmonitor.flight.domain.dto.FlightChangePassengerDTO;
import com.robotmonitor.flight.domain.dto.PassengerInLoungeDTO;
import com.robotmonitor.flight.domain.dto.PassengerOutgoingDTO;
import com.robotmonitor.flight.service.IPassengerService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/flight/passenger"})
public class TPassengerController
extends BaseController {
    @Autowired
    private IPassengerService tPassengerService;

    @PostMapping(value={"/getAuth"})
    public AjaxResult getAuth(String employeeNo) {
        AuthResponse info = this.tPassengerService.getAuth(employeeNo);
        if (ObjectUtils.isEmpty((Object)info)) {
            return new AjaxResult(500, "\u672a\u83b7\u53d6\u5230LTS\u767b\u5f55\u4ee4\u724c", null);
        }
        return AjaxResult.success((Object)info);
    }

    @PostMapping(value={"/BarCode"})
    public AjaxResult BarCode(String robotId, String barCode, String inType) {
        BarCodeRespons info = this.tPassengerService.barCode(robotId, barCode, inType);
        if (ObjectUtils.isEmpty((Object)info)) {
            return new AjaxResult(500, "\u672a\u83b7\u53d6\u5230\u767b\u673a\u724c\u4fe1\u606f", null);
        }
        return AjaxResult.success((Object)info);
    }

    @PostMapping(value={"/BarCode2"})
    public AjaxResult BarCode(@RequestBody CollectInParam2 param) {
        CollectInResponse2 info = this.tPassengerService.barCode2(param);
        if (ObjectUtils.isEmpty((Object)info) || !info.getCode().equals("1")) {
            return new AjaxResult(500, "\u672a\u83b7\u53d6\u5230\u65c5\u5ba2\u4fe1\u606f", null);
        }
        return AjaxResult.success((Object)info);
    }

    @PostMapping(value={"/passengerGetIn"})
    public AjaxResult passengerGetIn(@RequestBody CollectInResponse2 param) {
        this.tPassengerService.passengerGetIn2(param.getParam().getRobotId(), param);
        return AjaxResult.success();
    }

    @PostMapping(value={"/passengerGetOut"})
    public AjaxResult passengerGetOut(String reId, String oriImgUrl, Long regionId) {
        return this.toAjax(this.tPassengerService.setPassengerGetOut(reId, oriImgUrl, regionId));
    }

    @PreAuthorize(value="@ss.hasPermi('system:passenger:list')")
    @GetMapping(value={"/list"})
    public TableDataInfo list(Passenger tPassenger) {
        this.startPage();
        List list = this.tPassengerService.selectPassengerList(tPassenger);
        return this.getDataTable(list);
    }

    @PreAuthorize(value="@ss.hasPermi('system:passenger:export')")
    @Log(title="\u65c5\u5ba2\u4fe1\u606f", businessType=BusinessType.EXPORT)
    @PostMapping(value={"/export"})
    public void export(HttpServletResponse response, Passenger tPassenger) {
        List list = this.tPassengerService.selectPassengerList(tPassenger);
        ExcelUtil util = new ExcelUtil(Passenger.class);
        util.exportExcel(response, list, "\u65c5\u5ba2\u4fe1\u606f\u6570\u636e");
    }

    @PreAuthorize(value="@ss.hasPermi('system:passenger:query')")
    @GetMapping(value={"/{id}"})
    public AjaxResult getInfo(@PathVariable(value="id") Long id) {
        return AjaxResult.success((Object)this.tPassengerService.selectPassengerById(id));
    }

    @PreAuthorize(value="@ss.hasPermi('system:passenger:add')")
    @Log(title="\u65c5\u5ba2\u4fe1\u606f", businessType=BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Passenger tPassenger) {
        return this.toAjax(this.tPassengerService.insertPassenger(tPassenger));
    }

    @PreAuthorize(value="@ss.hasPermi('system:passenger:edit')")
    @Log(title="\u65c5\u5ba2\u4fe1\u606f", businessType=BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Passenger tPassenger) {
        return this.toAjax(this.tPassengerService.updatePassenger(tPassenger));
    }

    @PreAuthorize(value="@ss.hasPermi('system:passenger:remove')")
    @Log(title="\u65c5\u5ba2\u4fe1\u606f", businessType=BusinessType.DELETE)
    @DeleteMapping(value={"/{ids}"})
    public AjaxResult remove(@PathVariable Long[] ids) {
        return this.toAjax(this.tPassengerService.deletePassengerByIds(ids));
    }

    @GetMapping(value={"/outgoingList"})
    public TableDataInfo outgoingList(Passenger passenger) {
        this.startPage();
        List<Passenger> list = this.tPassengerService.selectPassengerOutgoingList(passenger);
        ArrayList<PassengerOutgoingDTO> dtoList = new ArrayList<PassengerOutgoingDTO>();
        for (Passenger p : list) {
            PassengerOutgoingDTO dto = new PassengerOutgoingDTO();
            dto.setUserName(p.getUserName());
            dto.setGetOutTime(p.getGetOutTime());
            dto.setFlightNo(p.getFlightNo());
            dto.setFlightDate(DateUtils.parseDate((Object)p.getFlightDate()));
            dto.setOriImageUrl(p.getOrigImageUrl());
            dto.setRegisterImageUrl(p.getRegisterImageUrl());
            dtoList.add(dto);
        }
        return this.getDataTable(dtoList);
    }

    @GetMapping(value={"/inLoungeList"})
    public TableDataInfo inLoungeList(@RequestBody Passenger passenger) {
        this.startPage();
        List<Passenger> list = this.tPassengerService.selectPassengerInLoungeList(passenger);
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
            dtoList.add(dto);
        }
        return this.getDataTable(dtoList);
    }

    @GetMapping(value={"/flightChangeList"})
    public TableDataInfo flightChangeList(Passenger passenger) {
        this.startPage();
        List<FlightChangePassengerDTO> list = this.tPassengerService.selectPassengerWithFlightChangeList(passenger);
        ArrayList<FlightChangePassengerDTO> dtoList = new ArrayList<FlightChangePassengerDTO>();
        for (FlightChangePassengerDTO p : list) {
            FlightChangePassengerDTO dto = new FlightChangePassengerDTO();
            dto.setId(p.getId());
            dto.setUserName(p.getUserName());
            dto.setRoomCode(p.getRoomCode());
            dto.setFlightNo(p.getFlightNo());
            dto.setFlightDate(p.getFlightDate());
            dto.setFlightId(p.getFlightId());
            dto.setOrigImageUrl(p.getOrigImageUrl());
            dto.setRegisterImageUrl(p.getRegisterImageUrl());
            dto.setUpdateTime(p.getUpdateTime());
            dto.setCreateTime(p.getCreateTime());
            dto.setWarningType(p.getWarningType());
            dto.setWarningType(p.getWarningType());
            dto.setChangeBefore(p.getChangeBefore());
            dto.setChangeAfter(p.getChangeAfter());
            dto.setCoordinate(p.getCoordinate());
            dto.setOrig(p.getOrig());
            dto.setDest(p.getDest());
            dto.setCabin(p.getCabin());
            dto.setGetInTime(p.getGetInTime());
            dto.setGetOutTime(p.getGetOutTime());
            dto.setStatus(p.getStatus());
            dto.setRegionId(p.getRegionId());
            dto.setRegionName(p.getRegionName());
            dto.setRemark(p.getRemark());
            dtoList.add(dto);
        }
        return this.getDataTable(dtoList);
    }

    @GetMapping(value={"/statisticsByInType"})
    public TableDataInfo getPassengerStatisticsByInType(PassengerParam param) {
        this.startPage();
        List list = this.tPassengerService.selectPassenger(param);
        return this.getDataTable(list);
    }

    @GetMapping(value={"/passengerWarningList"})
    public TableDataInfo passengerWarningList(@RequestBody Passenger passenger) {
        this.startPage();
        List<FlightChangePassengerDTO> list = this.tPassengerService.selectPassengerWithFlightChangeList(passenger);
        ArrayList<FlightChangePassengerDTO> dtoList = new ArrayList<FlightChangePassengerDTO>();
        for (FlightChangePassengerDTO p : list) {
            FlightChangePassengerDTO dto = new FlightChangePassengerDTO();
            dto.setId(p.getId());
            dto.setUserName(p.getUserName());
            dto.setRoomCode(p.getRoomCode());
            dto.setFlightNo(p.getFlightNo());
            dto.setFlightDate(p.getFlightDate());
            dto.setFlightId(p.getFlightId());
            dto.setOrigImageUrl(p.getOrigImageUrl());
            dto.setRegisterImageUrl(p.getRegisterImageUrl());
            dto.setUpdateTime(p.getUpdateTime());
            dto.setCreateTime(p.getCreateTime());
            dto.setWarningType(p.getWarningType());
            dto.setWarningType(p.getWarningType());
            dto.setChangeBefore(p.getChangeBefore());
            dto.setChangeAfter(p.getChangeAfter());
            dto.setCoordinate(p.getCoordinate());
            dto.setOrig(p.getOrig());
            dto.setDest(p.getDest());
            dto.setCabin(p.getCabin());
            dto.setGetInTime(p.getGetInTime());
            dto.setGetOutTime(p.getGetOutTime());
            dto.setStatus(p.getStatus());
            dto.setRegionId(p.getRegionId());
            dto.setRegionName(p.getRegionName());
            dto.setRemark(p.getRemark());
            dtoList.add(dto);
        }
        return this.getDataTable(dtoList);
    }
}
