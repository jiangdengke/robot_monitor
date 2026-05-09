package com.robotmonitor.web.controller.config;

import com.robotmonitor.common.annotation.Log;
import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.page.TableDataInfo;
import com.robotmonitor.common.enums.BusinessType;
import com.robotmonitor.common.utils.poi.ExcelUtil;
import com.robotmonitor.config.domain.ConfigArea;
import com.robotmonitor.config.domain.ConfigAreaDetail;
import com.robotmonitor.config.service.IConfigAreaService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
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
@RequestMapping({"/config/area"})
public class ConfigAreaController extends BaseController {
    @Autowired
    private IConfigAreaService configAreaService;

    @GetMapping({"/list"})
    public TableDataInfo list(ConfigArea configArea) {
        startPage();
        List<ConfigArea> list = configAreaService.selectConfigAreaList(configArea);
        return getDataTable(list);
    }

    @Log(title = "贵宾室功能区管理", businessType = BusinessType.EXPORT)
    @PostMapping({"/export"})
    public void export(HttpServletResponse response, ConfigArea configArea) {
        List<ConfigArea> list = configAreaService.selectConfigAreaList(configArea);
        ExcelUtil<ConfigArea> util = new ExcelUtil<>(ConfigArea.class);
        util.exportExcel(response, list, "贵宾室功能区管理数据");
    }

    @GetMapping({"/{id}"})
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(configAreaService.selectConfigAreaById(id));
    }

    @Log(title = "贵宾室功能区管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ConfigArea configArea) {
        normalizeDetails(configArea);
        return toAjax(configAreaService.insertConfigArea(configArea));
    }

    @Log(title = "贵宾室功能区管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ConfigArea configArea) {
        normalizeDetails(configArea);
        return toAjax(configAreaService.updateConfigArea(configArea));
    }

    @Log(title = "贵宾室功能区管理", businessType = BusinessType.DELETE)
    @DeleteMapping({"/{id}"})
    public AjaxResult remove(@PathVariable("id") Long id) {
        return toAjax(configAreaService.deleteConfigAreaById(id));
    }

    private void normalizeDetails(ConfigArea configArea) {
        if (configArea.getConfigAreaDetailList() == null || configArea.getConfigAreaDetailList().isEmpty()) {
            String areaName = firstNotBlank(configArea.getAreaName(), configArea.getRemark(), "默认功能区");
            ConfigAreaDetail detail = new ConfigAreaDetail();
            detail.setLanguageType("CN");
            detail.setAreaName(areaName);
            detail.setLabel("介绍");
            detail.setArrText("已到达" + areaName);
            detail.setRemark(areaName);
            configArea.setConfigAreaDetailList(List.of(detail));
            return;
        }
        for (ConfigAreaDetail detail : configArea.getConfigAreaDetailList()) {
            String areaName = firstNotBlank(detail.getAreaName(), configArea.getAreaName(), "默认功能区");
            detail.setAreaName(areaName);
            if (isBlank(detail.getLanguageType())) {
                detail.setLanguageType("CN");
            }
            if (isBlank(detail.getLabel())) {
                detail.setLabel("介绍");
            }
            if (isBlank(detail.getRemark())) {
                detail.setRemark(areaName);
            }
            if (isBlank(detail.getArrText())) {
                detail.setArrText("已到达" + areaName);
            }
        }
    }

    private String firstNotBlank(String... values) {
        for (String value : values) {
            if (!isBlank(value)) {
                return value;
            }
        }
        return "";
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
