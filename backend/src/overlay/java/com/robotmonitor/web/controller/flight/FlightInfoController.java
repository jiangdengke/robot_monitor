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
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping({"/flight/flightinfo"})
public class FlightInfoController extends BaseController {
    @Autowired
    private IFlightInfoService flightInfoService;

    @GetMapping({"/list"})
    public TableDataInfo list(FlightInfo flightInfo) {
        startPage();
        return getDataTable(flightInfoService.selectFlightInfoList(flightInfo));
    }

    @Log(title = "航班计划", businessType = BusinessType.EXPORT)
    @PostMapping({"/export"})
    public void export(HttpServletResponse response, FlightInfo flightInfo) {
        List<FlightInfo> list = flightInfoService.selectFlightInfoList(flightInfo);
        ExcelUtil<FlightInfo> util = new ExcelUtil<>(FlightInfo.class);
        util.exportExcel(response, list, "航班计划数据");
    }

    @GetMapping({"/{flightId}"})
    public AjaxResult getInfo(@PathVariable("flightId") String flightId) {
        return AjaxResult.success(flightInfoService.selectFlightInfoByFlightId(flightId));
    }

    @Log(title = "航班计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FlightInfo flightInfo) {
        if (flightInfo.getFlightId() == null || flightInfo.getFlightId().isBlank()) {
            flightInfo.setFlightId((flightInfo.getFlightNo() == null ? "FLIGHT" : flightInfo.getFlightNo()) + "-" + (flightInfo.getScheExecDate() == null ? System.currentTimeMillis() : flightInfo.getScheExecDate()));
        }
        return toAjax(flightInfoService.insertFlightInfo(flightInfo));
    }

    @Log(title = "航班计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FlightInfo flightInfo) {
        return toAjax(flightInfoService.updateFlightInfo(flightInfo));
    }

    @Log(title = "航班计划", businessType = BusinessType.DELETE)
    @DeleteMapping({"/{flightIds}"})
    public AjaxResult remove(@PathVariable String[] flightIds) {
        int rows = Arrays.stream(flightIds)
            .mapToInt(flightInfoService::deleteFlightInfoByFlightId)
            .sum();
        return toAjax(rows);
    }

    @Log(title = "航班计划", businessType = BusinessType.INSERT)
    @PostMapping({"/addTmp"})
    public AjaxResult addTmp(@RequestBody String msg) throws JAXBException {
        return toAjax(flightInfoService.addTmp(msg));
    }

    @Log(title = "加入测试航班计划", businessType = BusinessType.INSERT)
    @PostMapping({"/addTmpByFile"})
    public AjaxResult addTmpByFile(@RequestParam("file") MultipartFile file) throws JAXBException {
        try {
            if (file.isEmpty()) {
                return AjaxResult.error("文件为空，请选择要上传的文件");
            }
            String originalFilename = file.getOriginalFilename();
            if (originalFilename != null && !originalFilename.toLowerCase().endsWith(".txt")) {
                return AjaxResult.error("只支持上传TXT文件");
            }
            return toAjax(flightInfoService.addTmp(readTxtFileContent(file)));
        } catch (IOException ex) {
            return AjaxResult.error("文件处理失败: " + ex.getMessage());
        }
    }

    private String readTxtFileContent(MultipartFile file) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        }
        return contentBuilder.toString().trim();
    }
}
