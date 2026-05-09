/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.constant.GenConstants
 *  com.robotmonitor.common.core.domain.BaseEntity
 *  com.robotmonitor.common.utils.StringUtils
 *  jakarta.validation.Valid
 *  jakarta.validation.constraints.NotBlank
 *  org.apache.commons.lang3.ArrayUtils
 */
package com.robotmonitor.generator.domain;

import com.robotmonitor.common.constant.GenConstants;
import com.robotmonitor.common.core.domain.BaseEntity;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.generator.domain.GenTableColumn;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

public class GenTable
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long tableId;
    @NotBlank(message="\u8868\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotBlank(message="\u8868\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a") String tableName;
    @NotBlank(message="\u8868\u63cf\u8ff0\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotBlank(message="\u8868\u63cf\u8ff0\u4e0d\u80fd\u4e3a\u7a7a") String tableComment;
    private String subTableName;
    private String subTableFkName;
    @NotBlank(message="\u5b9e\u4f53\u7c7b\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotBlank(message="\u5b9e\u4f53\u7c7b\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a") String className;
    private String tplCategory;
    @NotBlank(message="\u751f\u6210\u5305\u8def\u5f84\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotBlank(message="\u751f\u6210\u5305\u8def\u5f84\u4e0d\u80fd\u4e3a\u7a7a") String packageName;
    @NotBlank(message="\u751f\u6210\u6a21\u5757\u540d\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotBlank(message="\u751f\u6210\u6a21\u5757\u540d\u4e0d\u80fd\u4e3a\u7a7a") String moduleName;
    @NotBlank(message="\u751f\u6210\u4e1a\u52a1\u540d\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotBlank(message="\u751f\u6210\u4e1a\u52a1\u540d\u4e0d\u80fd\u4e3a\u7a7a") String businessName;
    @NotBlank(message="\u751f\u6210\u529f\u80fd\u540d\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotBlank(message="\u751f\u6210\u529f\u80fd\u540d\u4e0d\u80fd\u4e3a\u7a7a") String functionName;
    @NotBlank(message="\u4f5c\u8005\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotBlank(message="\u4f5c\u8005\u4e0d\u80fd\u4e3a\u7a7a") String functionAuthor;
    private String genType;
    private String genPath;
    private GenTableColumn pkColumn;
    private GenTable subTable;
    @Valid
    private List<GenTableColumn> columns;
    private String options;
    private String treeCode;
    private String treeParentCode;
    private String treeName;
    private String parentMenuId;
    private String parentMenuName;

    public Long getTableId() {
        return this.tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return this.tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public String getSubTableName() {
        return this.subTableName;
    }

    public void setSubTableName(String subTableName) {
        this.subTableName = subTableName;
    }

    public String getSubTableFkName() {
        return this.subTableFkName;
    }

    public void setSubTableFkName(String subTableFkName) {
        this.subTableFkName = subTableFkName;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTplCategory() {
        return this.tplCategory;
    }

    public void setTplCategory(String tplCategory) {
        this.tplCategory = tplCategory;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getBusinessName() {
        return this.businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getFunctionName() {
        return this.functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionAuthor() {
        return this.functionAuthor;
    }

    public void setFunctionAuthor(String functionAuthor) {
        this.functionAuthor = functionAuthor;
    }

    public String getGenType() {
        return this.genType;
    }

    public void setGenType(String genType) {
        this.genType = genType;
    }

    public String getGenPath() {
        return this.genPath;
    }

    public void setGenPath(String genPath) {
        this.genPath = genPath;
    }

    public GenTableColumn getPkColumn() {
        return this.pkColumn;
    }

    public void setPkColumn(GenTableColumn pkColumn) {
        this.pkColumn = pkColumn;
    }

    public GenTable getSubTable() {
        return this.subTable;
    }

    public void setSubTable(GenTable subTable) {
        this.subTable = subTable;
    }

    public List<GenTableColumn> getColumns() {
        return this.columns;
    }

    public void setColumns(List<GenTableColumn> columns) {
        this.columns = columns;
    }

    public String getOptions() {
        return this.options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getTreeCode() {
        return this.treeCode;
    }

    public void setTreeCode(String treeCode) {
        this.treeCode = treeCode;
    }

    public String getTreeParentCode() {
        return this.treeParentCode;
    }

    public void setTreeParentCode(String treeParentCode) {
        this.treeParentCode = treeParentCode;
    }

    public String getTreeName() {
        return this.treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    public String getParentMenuId() {
        return this.parentMenuId;
    }

    public void setParentMenuId(String parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public String getParentMenuName() {
        return this.parentMenuName;
    }

    public void setParentMenuName(String parentMenuName) {
        this.parentMenuName = parentMenuName;
    }

    public boolean isSub() {
        return GenTable.isSub(this.tplCategory);
    }

    public static boolean isSub(String tplCategory) {
        return tplCategory != null && StringUtils.equals((CharSequence)"sub", (CharSequence)tplCategory);
    }

    public boolean isTree() {
        return GenTable.isTree(this.tplCategory);
    }

    public static boolean isTree(String tplCategory) {
        return tplCategory != null && StringUtils.equals((CharSequence)"tree", (CharSequence)tplCategory);
    }

    public boolean isCrud() {
        return GenTable.isCrud(this.tplCategory);
    }

    public static boolean isCrud(String tplCategory) {
        return tplCategory != null && StringUtils.equals((CharSequence)"crud", (CharSequence)tplCategory);
    }

    public boolean isSuperColumn(String javaField) {
        return GenTable.isSuperColumn(this.tplCategory, javaField);
    }

    public static boolean isSuperColumn(String tplCategory, String javaField) {
        if (GenTable.isTree(tplCategory)) {
            return StringUtils.equalsAnyIgnoreCase((CharSequence)javaField, (CharSequence[])((CharSequence[])ArrayUtils.addAll((Object[])GenConstants.TREE_ENTITY, (Object[])GenConstants.BASE_ENTITY)));
        }
        return StringUtils.equalsAnyIgnoreCase((CharSequence)javaField, (CharSequence[])GenConstants.BASE_ENTITY);
    }
}
