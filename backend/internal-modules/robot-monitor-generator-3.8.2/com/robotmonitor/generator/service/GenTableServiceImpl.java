/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.fastjson2.JSON
 *  com.alibaba.fastjson2.JSONObject
 *  com.robotmonitor.common.exception.ServiceException
 *  com.robotmonitor.common.utils.SecurityUtils
 *  com.robotmonitor.common.utils.StringUtils
 *  org.apache.commons.io.FileUtils
 *  org.apache.commons.io.IOUtils
 *  org.apache.velocity.Template
 *  org.apache.velocity.VelocityContext
 *  org.apache.velocity.app.Velocity
 *  org.apache.velocity.context.Context
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 *  org.springframework.transaction.annotation.Transactional
 */
package com.robotmonitor.generator.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.robotmonitor.common.exception.ServiceException;
import com.robotmonitor.common.utils.SecurityUtils;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.generator.domain.GenTable;
import com.robotmonitor.generator.domain.GenTableColumn;
import com.robotmonitor.generator.mapper.GenTableColumnMapper;
import com.robotmonitor.generator.mapper.GenTableMapper;
import com.robotmonitor.generator.service.IGenTableService;
import com.robotmonitor.generator.util.GenUtils;
import com.robotmonitor.generator.util.VelocityInitializer;
import com.robotmonitor.generator.util.VelocityUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenTableServiceImpl
implements IGenTableService {
    private static final Logger log = LoggerFactory.getLogger(GenTableServiceImpl.class);
    @Autowired
    private GenTableMapper genTableMapper;
    @Autowired
    private GenTableColumnMapper genTableColumnMapper;

    @Override
    public GenTable selectGenTableById(Long id) {
        GenTable genTable = this.genTableMapper.selectGenTableById(id);
        this.setTableFromOptions(genTable);
        return genTable;
    }

    @Override
    public List<GenTable> selectGenTableList(GenTable genTable) {
        return this.genTableMapper.selectGenTableList(genTable);
    }

    @Override
    public List<GenTable> selectDbTableList(GenTable genTable) {
        return this.genTableMapper.selectDbTableList(genTable);
    }

    @Override
    public List<GenTable> selectDbTableListByNames(String[] tableNames) {
        return this.genTableMapper.selectDbTableListByNames(tableNames);
    }

    @Override
    public List<GenTable> selectGenTableAll() {
        return this.genTableMapper.selectGenTableAll();
    }

    @Override
    @Transactional
    public void updateGenTable(GenTable genTable) {
        String options = JSON.toJSONString((Object)genTable.getParams());
        genTable.setOptions(options);
        int row = this.genTableMapper.updateGenTable(genTable);
        if (row > 0) {
            for (GenTableColumn cenTableColumn : genTable.getColumns()) {
                this.genTableColumnMapper.updateGenTableColumn(cenTableColumn);
            }
        }
    }

    @Override
    @Transactional
    public void deleteGenTableByIds(Long[] tableIds) {
        this.genTableMapper.deleteGenTableByIds(tableIds);
        this.genTableColumnMapper.deleteGenTableColumnByIds(tableIds);
    }

    @Override
    @Transactional
    public void importGenTable(List<GenTable> tableList) {
        String operName = SecurityUtils.getUsername();
        try {
            for (GenTable table : tableList) {
                String tableName = table.getTableName();
                GenUtils.initTable(table, operName);
                int row = this.genTableMapper.insertGenTable(table);
                if (row <= 0) continue;
                List<GenTableColumn> genTableColumns = this.genTableColumnMapper.selectDbTableColumnsByName(tableName);
                for (GenTableColumn column : genTableColumns) {
                    GenUtils.initColumnField(column, table);
                    this.genTableColumnMapper.insertGenTableColumn(column);
                }
            }
        }
        catch (Exception e) {
            throw new ServiceException("\u5bfc\u5165\u5931\u8d25\uff1a" + e.getMessage());
        }
    }

    @Override
    public Map<String, String> previewCode(Long tableId) {
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        GenTable table = this.genTableMapper.selectGenTableById(tableId);
        this.setSubTable(table);
        this.setPkColumn(table);
        VelocityInitializer.initVelocity();
        VelocityContext context = VelocityUtils.prepareContext(table);
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
        for (String template : templates) {
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate((String)template, (String)"UTF-8");
            tpl.merge((Context)context, (Writer)sw);
            dataMap.put(template, sw.toString());
        }
        return dataMap;
    }

    @Override
    public byte[] downloadCode(String tableName) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        this.generatorCode(tableName, zip);
        IOUtils.closeQuietly((OutputStream)zip);
        return outputStream.toByteArray();
    }

    @Override
    public void generatorCode(String tableName) {
        GenTable table = this.genTableMapper.selectGenTableByName(tableName);
        this.setSubTable(table);
        this.setPkColumn(table);
        VelocityInitializer.initVelocity();
        VelocityContext context = VelocityUtils.prepareContext(table);
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
        for (String template : templates) {
            if (StringUtils.containsAny((CharSequence)template, (CharSequence[])new CharSequence[]{"sql.vm", "api.js.vm", "index.vue.vm", "index-tree.vue.vm"})) continue;
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate((String)template, (String)"UTF-8");
            tpl.merge((Context)context, (Writer)sw);
            try {
                String path = GenTableServiceImpl.getGenPath(table, template);
                FileUtils.writeStringToFile((File)new File(path), (String)sw.toString(), (String)"UTF-8");
            }
            catch (IOException e) {
                throw new ServiceException("\u6e32\u67d3\u6a21\u677f\u5931\u8d25\uff0c\u8868\u540d\uff1a" + table.getTableName());
            }
        }
    }

    @Override
    @Transactional
    public void synchDb(String tableName) {
        GenTable table = this.genTableMapper.selectGenTableByName(tableName);
        List<GenTableColumn> tableColumns = table.getColumns();
        Map tableColumnMap = tableColumns.stream().collect(Collectors.toMap(GenTableColumn::getColumnName, Function.identity()));
        List<GenTableColumn> dbTableColumns = this.genTableColumnMapper.selectDbTableColumnsByName(tableName);
        if (StringUtils.isEmpty(dbTableColumns)) {
            throw new ServiceException("\u540c\u6b65\u6570\u636e\u5931\u8d25\uff0c\u539f\u8868\u7ed3\u6784\u4e0d\u5b58\u5728");
        }
        List dbTableColumnNames = dbTableColumns.stream().map(GenTableColumn::getColumnName).collect(Collectors.toList());
        dbTableColumns.forEach(column -> {
            GenUtils.initColumnField(column, table);
            if (tableColumnMap.containsKey(column.getColumnName())) {
                GenTableColumn prevColumn = (GenTableColumn)((Object)((Object)tableColumnMap.get(column.getColumnName())));
                column.setColumnId(prevColumn.getColumnId());
                if (column.isList()) {
                    column.setDictType(prevColumn.getDictType());
                    column.setQueryType(prevColumn.getQueryType());
                }
                if (StringUtils.isNotEmpty((String)prevColumn.getIsRequired()) && !column.isPk() && (column.isInsert() || column.isEdit()) && (column.isUsableColumn() || !column.isSuperColumn())) {
                    column.setIsRequired(prevColumn.getIsRequired());
                    column.setHtmlType(prevColumn.getHtmlType());
                }
                this.genTableColumnMapper.updateGenTableColumn((GenTableColumn)((Object)column));
            } else {
                this.genTableColumnMapper.insertGenTableColumn((GenTableColumn)((Object)column));
            }
        });
        List<GenTableColumn> delColumns = tableColumns.stream().filter(column -> !dbTableColumnNames.contains(column.getColumnName())).collect(Collectors.toList());
        if (StringUtils.isNotEmpty(delColumns)) {
            this.genTableColumnMapper.deleteGenTableColumns(delColumns);
        }
    }

    @Override
    public byte[] downloadCode(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (String tableName : tableNames) {
            this.generatorCode(tableName, zip);
        }
        IOUtils.closeQuietly((OutputStream)zip);
        return outputStream.toByteArray();
    }

    private void generatorCode(String tableName, ZipOutputStream zip) {
        GenTable table = this.genTableMapper.selectGenTableByName(tableName);
        this.setSubTable(table);
        this.setPkColumn(table);
        VelocityInitializer.initVelocity();
        VelocityContext context = VelocityUtils.prepareContext(table);
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
        for (String template : templates) {
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate((String)template, (String)"UTF-8");
            tpl.merge((Context)context, (Writer)sw);
            try {
                zip.putNextEntry(new ZipEntry(VelocityUtils.getFileName(template, table)));
                IOUtils.write((String)sw.toString(), (OutputStream)zip, (String)"UTF-8");
                IOUtils.closeQuietly((Writer)sw);
                zip.flush();
                zip.closeEntry();
            }
            catch (IOException e) {
                log.error("\u6e32\u67d3\u6a21\u677f\u5931\u8d25\uff0c\u8868\u540d\uff1a" + table.getTableName(), (Throwable)e);
            }
        }
    }

    @Override
    public void validateEdit(GenTable genTable) {
        if ("tree".equals(genTable.getTplCategory())) {
            String options = JSON.toJSONString((Object)genTable.getParams());
            JSONObject paramsObj = JSON.parseObject((String)options);
            if (StringUtils.isEmpty((String)paramsObj.getString("treeCode"))) {
                throw new ServiceException("\u6811\u7f16\u7801\u5b57\u6bb5\u4e0d\u80fd\u4e3a\u7a7a");
            }
            if (StringUtils.isEmpty((String)paramsObj.getString("treeParentCode"))) {
                throw new ServiceException("\u6811\u7236\u7f16\u7801\u5b57\u6bb5\u4e0d\u80fd\u4e3a\u7a7a");
            }
            if (StringUtils.isEmpty((String)paramsObj.getString("treeName"))) {
                throw new ServiceException("\u6811\u540d\u79f0\u5b57\u6bb5\u4e0d\u80fd\u4e3a\u7a7a");
            }
            if ("sub".equals(genTable.getTplCategory())) {
                if (StringUtils.isEmpty((String)genTable.getSubTableName())) {
                    throw new ServiceException("\u5173\u8054\u5b50\u8868\u7684\u8868\u540d\u4e0d\u80fd\u4e3a\u7a7a");
                }
                if (StringUtils.isEmpty((String)genTable.getSubTableFkName())) {
                    throw new ServiceException("\u5b50\u8868\u5173\u8054\u7684\u5916\u952e\u540d\u4e0d\u80fd\u4e3a\u7a7a");
                }
            }
        }
    }

    public void setPkColumn(GenTable table) {
        for (GenTableColumn column : table.getColumns()) {
            if (!column.isPk()) continue;
            table.setPkColumn(column);
            break;
        }
        if (StringUtils.isNull((Object)((Object)table.getPkColumn()))) {
            table.setPkColumn(table.getColumns().get(0));
        }
        if ("sub".equals(table.getTplCategory())) {
            for (GenTableColumn column : table.getSubTable().getColumns()) {
                if (!column.isPk()) continue;
                table.getSubTable().setPkColumn(column);
                break;
            }
            if (StringUtils.isNull((Object)((Object)table.getSubTable().getPkColumn()))) {
                table.getSubTable().setPkColumn(table.getSubTable().getColumns().get(0));
            }
        }
    }

    public void setSubTable(GenTable table) {
        String subTableName = table.getSubTableName();
        if (StringUtils.isNotEmpty((String)subTableName)) {
            table.setSubTable(this.genTableMapper.selectGenTableByName(subTableName));
        }
    }

    public void setTableFromOptions(GenTable genTable) {
        JSONObject paramsObj = JSON.parseObject((String)genTable.getOptions());
        if (StringUtils.isNotNull((Object)paramsObj)) {
            String treeCode = paramsObj.getString("treeCode");
            String treeParentCode = paramsObj.getString("treeParentCode");
            String treeName = paramsObj.getString("treeName");
            String parentMenuId = paramsObj.getString("parentMenuId");
            String parentMenuName = paramsObj.getString("parentMenuName");
            genTable.setTreeCode(treeCode);
            genTable.setTreeParentCode(treeParentCode);
            genTable.setTreeName(treeName);
            genTable.setParentMenuId(parentMenuId);
            genTable.setParentMenuName(parentMenuName);
        }
    }

    public static String getGenPath(GenTable table, String template) {
        String genPath = table.getGenPath();
        if (StringUtils.equals((CharSequence)genPath, (CharSequence)"/")) {
            return System.getProperty("user.dir") + File.separator + "src" + File.separator + VelocityUtils.getFileName(template, table);
        }
        return genPath + File.separator + VelocityUtils.getFileName(template, table);
    }
}
