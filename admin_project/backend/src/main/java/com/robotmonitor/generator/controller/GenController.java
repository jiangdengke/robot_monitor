/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Log
 *  com.robotmonitor.common.core.controller.BaseController
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.core.page.TableDataInfo
 *  com.robotmonitor.common.core.text.Convert
 *  com.robotmonitor.common.enums.BusinessType
 *  jakarta.servlet.http.HttpServletResponse
 *  org.apache.commons.io.IOUtils
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.security.access.prepost.PreAuthorize
 *  org.springframework.validation.annotation.Validated
 *  org.springframework.web.bind.annotation.DeleteMapping
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.PutMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.robotmonitor.generator.controller;

import com.robotmonitor.common.annotation.Log;
import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.page.TableDataInfo;
import com.robotmonitor.common.core.text.Convert;
import com.robotmonitor.common.enums.BusinessType;
import com.robotmonitor.generator.domain.GenTable;
import com.robotmonitor.generator.domain.GenTableColumn;
import com.robotmonitor.generator.service.IGenTableColumnService;
import com.robotmonitor.generator.service.IGenTableService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/tool/gen"})
public class GenController
extends BaseController {
    @Autowired
    private IGenTableService genTableService;
    @Autowired
    private IGenTableColumnService genTableColumnService;

    @PreAuthorize(value="@ss.hasPermi('tool:gen:list')")
    @GetMapping(value={"/list"})
    public TableDataInfo genList(GenTable genTable) {
        this.startPage();
        List<GenTable> list = this.genTableService.selectGenTableList(genTable);
        return this.getDataTable(list);
    }

    @PreAuthorize(value="@ss.hasPermi('tool:gen:query')")
    @GetMapping(value={"/{tableId}"})
    public AjaxResult getInfo(@PathVariable Long tableId) {
        GenTable table = this.genTableService.selectGenTableById(tableId);
        List<GenTable> tables = this.genTableService.selectGenTableAll();
        List<GenTableColumn> list = this.genTableColumnService.selectGenTableColumnListByTableId(tableId);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("info", (Object)table);
        map.put("rows", list);
        map.put("tables", tables);
        return AjaxResult.success(map);
    }

    @PreAuthorize(value="@ss.hasPermi('tool:gen:list')")
    @GetMapping(value={"/db/list"})
    public TableDataInfo dataList(GenTable genTable) {
        this.startPage();
        List<GenTable> list = this.genTableService.selectDbTableList(genTable);
        return this.getDataTable(list);
    }

    @PreAuthorize(value="@ss.hasPermi('tool:gen:list')")
    @GetMapping(value={"/column/{tableId}"})
    public TableDataInfo columnList(Long tableId) {
        TableDataInfo dataInfo = new TableDataInfo();
        List<GenTableColumn> list = this.genTableColumnService.selectGenTableColumnListByTableId(tableId);
        dataInfo.setRows(list);
        dataInfo.setTotal((long)list.size());
        return dataInfo;
    }

    @PreAuthorize(value="@ss.hasPermi('tool:gen:import')")
    @Log(title="\u4ee3\u7801\u751f\u6210", businessType=BusinessType.IMPORT)
    @PostMapping(value={"/importTable"})
    public AjaxResult importTableSave(String tables) {
        String[] tableNames = Convert.toStrArray((String)tables);
        List<GenTable> tableList = this.genTableService.selectDbTableListByNames(tableNames);
        this.genTableService.importGenTable(tableList);
        return AjaxResult.success();
    }

    @PreAuthorize(value="@ss.hasPermi('tool:gen:edit')")
    @Log(title="\u4ee3\u7801\u751f\u6210", businessType=BusinessType.UPDATE)
    @PutMapping
    public AjaxResult editSave(@Validated @RequestBody GenTable genTable) {
        this.genTableService.validateEdit(genTable);
        this.genTableService.updateGenTable(genTable);
        return AjaxResult.success();
    }

    @PreAuthorize(value="@ss.hasPermi('tool:gen:remove')")
    @Log(title="\u4ee3\u7801\u751f\u6210", businessType=BusinessType.DELETE)
    @DeleteMapping(value={"/{tableIds}"})
    public AjaxResult remove(@PathVariable Long[] tableIds) {
        this.genTableService.deleteGenTableByIds(tableIds);
        return AjaxResult.success();
    }

    @PreAuthorize(value="@ss.hasPermi('tool:gen:preview')")
    @GetMapping(value={"/preview/{tableId}"})
    public AjaxResult preview(@PathVariable(value="tableId") Long tableId) throws IOException {
        Map<String, String> dataMap = this.genTableService.previewCode(tableId);
        return AjaxResult.success(dataMap);
    }

    @PreAuthorize(value="@ss.hasPermi('tool:gen:code')")
    @Log(title="\u4ee3\u7801\u751f\u6210", businessType=BusinessType.GENCODE)
    @GetMapping(value={"/download/{tableName}"})
    public void download(HttpServletResponse response, @PathVariable(value="tableName") String tableName) throws IOException {
        byte[] data = this.genTableService.downloadCode(tableName);
        this.genCode(response, data);
    }

    @PreAuthorize(value="@ss.hasPermi('tool:gen:code')")
    @Log(title="\u4ee3\u7801\u751f\u6210", businessType=BusinessType.GENCODE)
    @GetMapping(value={"/genCode/{tableName}"})
    public AjaxResult genCode(@PathVariable(value="tableName") String tableName) {
        this.genTableService.generatorCode(tableName);
        return AjaxResult.success();
    }

    @PreAuthorize(value="@ss.hasPermi('tool:gen:edit')")
    @Log(title="\u4ee3\u7801\u751f\u6210", businessType=BusinessType.UPDATE)
    @GetMapping(value={"/synchDb/{tableName}"})
    public AjaxResult synchDb(@PathVariable(value="tableName") String tableName) {
        this.genTableService.synchDb(tableName);
        return AjaxResult.success();
    }

    @PreAuthorize(value="@ss.hasPermi('tool:gen:code')")
    @Log(title="\u4ee3\u7801\u751f\u6210", businessType=BusinessType.GENCODE)
    @GetMapping(value={"/batchGenCode"})
    public void batchGenCode(HttpServletResponse response, String tables) throws IOException {
        String[] tableNames = Convert.toStrArray((String)tables);
        byte[] data = this.genTableService.downloadCode(tableNames);
        this.genCode(response, data);
    }

    private void genCode(HttpServletResponse response, byte[] data) throws IOException {
        response.reset();
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment; filename=\"robotmonitor.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write((byte[])data, (OutputStream)response.getOutputStream());
    }
}
