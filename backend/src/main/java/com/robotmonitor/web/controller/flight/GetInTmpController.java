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
 *  com.robotmonitor.flight.domain.GetInTmp
 *  com.robotmonitor.flight.service.IGetInTmpService
 *  jakarta.servlet.http.HttpServletResponse
 *  org.springframework.beans.factory.annotation.Autowired
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
import com.robotmonitor.common.utils.poi.ExcelUtil;
import com.robotmonitor.flight.domain.GetInTmp;
import com.robotmonitor.flight.service.IGetInTmpService;
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
@RequestMapping(value={"/flight/tmp"})
public class GetInTmpController
extends BaseController {
    @Autowired
    private IGetInTmpService getInTmpService;

    @GetMapping(value={"/list"})
    public TableDataInfo list(GetInTmp getInTmp) {
        this.startPage();
        List list = this.getInTmpService.selectGetInTmpList(getInTmp);
        return this.getDataTable(list);
    }

    @Log(title="\u51c6\u5165\u5237\u5361\u6a21\u62df\u6570\u636e", businessType=BusinessType.EXPORT)
    @PostMapping(value={"/export"})
    public void export(HttpServletResponse response, GetInTmp getInTmp) {
        List list = this.getInTmpService.selectGetInTmpList(getInTmp);
        ExcelUtil util = new ExcelUtil(GetInTmp.class);
        util.exportExcel(response, list, "\u51c6\u5165\u5237\u5361\u6a21\u62df\u6570\u636e\u6570\u636e");
    }

    @GetMapping(value={"/{id}"})
    public AjaxResult getInfo(@PathVariable(value="id") Long id) {
        return AjaxResult.success((Object)this.getInTmpService.selectGetInTmpById(id));
    }

    @Log(title="\u51c6\u5165\u5237\u5361\u6a21\u62df\u6570\u636e", businessType=BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GetInTmp getInTmp) {
        return this.toAjax(this.getInTmpService.insertGetInTmp(getInTmp));
    }

    @Log(title="\u51c6\u5165\u5237\u5361\u6a21\u62df\u6570\u636e", businessType=BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GetInTmp getInTmp) {
        return this.toAjax(this.getInTmpService.updateGetInTmp(getInTmp));
    }

    @Log(title="\u51c6\u5165\u5237\u5361\u6a21\u62df\u6570\u636e", businessType=BusinessType.DELETE)
    @DeleteMapping(value={"/{ids}"})
    public AjaxResult remove(@PathVariable Long[] ids) {
        return this.toAjax(this.getInTmpService.deleteGetInTmpByIds(ids));
    }
}
