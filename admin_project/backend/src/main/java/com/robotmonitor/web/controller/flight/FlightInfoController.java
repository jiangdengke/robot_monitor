/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Log
 *  com.robotmonitor.common.core.controller.BaseController
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.core.page.TableDataInfo
 *  com.robotmonitor.common.enums.BusinessType
 *  com.robotmonitor.common.utils.poi.ExcelUtil
 *  com.robotmonitor.flight.domain.FlightInfo
 *  com.robotmonitor.flight.service.IFlightInfoService
 *  jakarta.servlet.http.HttpServletResponse
 *  javax.xml.bind.JAXBException
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.security.access.prepost.PreAuthorize
 *  org.springframework.web.bind.annotation.DeleteMapping
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.PutMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.robotmonitor.web.controller.flight;

import com.robotmonitor.common.annotation.Log;
import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.page.TableDataInfo;
import com.robotmonitor.common.enums.BusinessType;
import com.robotmonitor.common.utils.poi.ExcelUtil;
import com.robotmonitor.flight.domain.FlightInfo;
import com.robotmonitor.flight.service.IFlightInfoService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value={"/flight/flightinfo"})
public class FlightInfoController
extends BaseController {
    @Autowired
    private IFlightInfoService flightInfoService;

    @GetMapping(value={"/list"})
    public TableDataInfo list(FlightInfo flightInfo) {
        this.startPage();
        List list = this.flightInfoService.selectFlightInfoList(flightInfo);
        return this.getDataTable(list);
    }

    @PreAuthorize(value="@ss.hasPermi('flight:flightinfo:export')")
    @Log(title="\u822a\u73ed\u8ba1\u5212", businessType=BusinessType.EXPORT)
    @PostMapping(value={"/export"})
    public void export(HttpServletResponse response, FlightInfo flightInfo) {
        List list = this.flightInfoService.selectFlightInfoList(flightInfo);
        ExcelUtil util = new ExcelUtil(FlightInfo.class);
        util.exportExcel(response, list, "\u822a\u73ed\u8ba1\u5212\u6570\u636e");
    }

    @PreAuthorize(value="@ss.hasPermi('flight:flightinfo:query')")
    @GetMapping(value={"/{flightId}"})
    public AjaxResult getInfo(@PathVariable(value="flightId") String flightId) {
        return AjaxResult.success((Object)this.flightInfoService.selectFlightInfoByFlightId(flightId));
    }

    @Log(title="\u822a\u73ed\u8ba1\u5212", businessType=BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FlightInfo flightInfo) {
        return this.toAjax(this.flightInfoService.insertFlightInfo(flightInfo));
    }

    @PreAuthorize(value="@ss.hasPermi('flight:flightinfo:edit')")
    @Log(title="\u822a\u73ed\u8ba1\u5212", businessType=BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FlightInfo flightInfo) {
        return this.toAjax(this.flightInfoService.updateFlightInfo(flightInfo));
    }

    @PreAuthorize(value="@ss.hasPermi('flight:flightinfo:remove')")
    @Log(title="\u822a\u73ed\u8ba1\u5212", businessType=BusinessType.DELETE)
    @DeleteMapping(value={"/{flightIds}"})
    public AjaxResult remove(@PathVariable Long[] flightIds) {
        return this.toAjax(this.flightInfoService.deleteFlightInfoByFlightIds(flightIds));
    }

    @Log(title="\u822a\u73ed\u8ba1\u5212", businessType=BusinessType.INSERT)
    @PostMapping(value={"/addTmp"})
    public AjaxResult addTmp(@RequestBody String msg) throws JAXBException {
        return this.toAjax(this.flightInfoService.addTmp(msg));
    }

    @Log(title="\u52a0\u5165\u6d4b\u8bd5\u822a\u73ed\u8ba1\u5212", businessType=BusinessType.INSERT)
    @PostMapping(value={"/addTmpByFile"})
    public AjaxResult addTmpByFile(@RequestParam(value="file") MultipartFile file) throws JAXBException {
        try {
            if (file.isEmpty()) {
                return AjaxResult.error((String)"\u6587\u4ef6\u4e3a\u7a7a\uff0c\u8bf7\u9009\u62e9\u8981\u4e0a\u4f20\u7684\u6587\u4ef6");
            }
            String originalFilename = file.getOriginalFilename();
            if (originalFilename != null && !originalFilename.toLowerCase().endsWith(".txt")) {
                return AjaxResult.error((String)"\u53ea\u652f\u6301\u4e0a\u4f20TXT\u6587\u4ef6");
            }
            String msg = this.readTxtFileContent(file);
            return this.toAjax(this.flightInfoService.addTmp(msg));
        }
        catch (IOException e) {
            e.printStackTrace();
            return AjaxResult.error((String)("\u6587\u4ef6\u5904\u7406\u5931\u8d25: " + e.getMessage()));
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error((String)("\u6587\u4ef6\u4e0a\u4f20\u5931\u8d25: " + e.getMessage()));
        }
    }

    private String readTxtFileContent(MultipartFile file) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));){
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        }
        return contentBuilder.toString().trim();
    }
}
