package com.robotmonitor.generator.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.GEN_TABLE;
import static com.robotmonitor.jooq.generated.Tables.GEN_TABLE_COLUMN;

import com.robotmonitor.generator.domain.GenTable;
import com.robotmonitor.generator.domain.GenTableColumn;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.jooq.Record;

final class JooqGenMapperSupport {
    private JooqGenMapperSupport() {
    }

    static GenTable mapTable(Record record) {
        GenTable table = new GenTable();
        table.setTableId(record.get(GEN_TABLE.TABLE_ID));
        table.setTableName(record.get(GEN_TABLE.TABLE_NAME));
        table.setTableComment(record.get(GEN_TABLE.TABLE_COMMENT));
        table.setSubTableName(record.get(GEN_TABLE.SUB_TABLE_NAME));
        table.setSubTableFkName(record.get(GEN_TABLE.SUB_TABLE_FK_NAME));
        table.setClassName(record.get(GEN_TABLE.CLASS_NAME));
        table.setTplCategory(record.get(GEN_TABLE.TPL_CATEGORY));
        table.setPackageName(record.get(GEN_TABLE.PACKAGE_NAME));
        table.setModuleName(record.get(GEN_TABLE.MODULE_NAME));
        table.setBusinessName(record.get(GEN_TABLE.BUSINESS_NAME));
        table.setFunctionName(record.get(GEN_TABLE.FUNCTION_NAME));
        table.setFunctionAuthor(record.get(GEN_TABLE.FUNCTION_AUTHOR));
        table.setGenType(record.get(GEN_TABLE.GEN_TYPE));
        table.setGenPath(record.get(GEN_TABLE.GEN_PATH));
        table.setOptions(record.get(GEN_TABLE.OPTIONS));
        table.setCreateBy(record.get(GEN_TABLE.CREATE_BY));
        table.setCreateTime(toDate(record.get(GEN_TABLE.CREATE_TIME)));
        table.setUpdateBy(record.get(GEN_TABLE.UPDATE_BY));
        table.setUpdateTime(toDate(record.get(GEN_TABLE.UPDATE_TIME)));
        table.setRemark(record.get(GEN_TABLE.REMARK));
        return table;
    }

    static GenTableColumn mapColumn(Record record) {
        GenTableColumn column = new GenTableColumn();
        column.setColumnId(record.get(GEN_TABLE_COLUMN.COLUMN_ID));
        column.setTableId(record.get(GEN_TABLE_COLUMN.TABLE_ID));
        column.setColumnName(record.get(GEN_TABLE_COLUMN.COLUMN_NAME));
        column.setColumnComment(record.get(GEN_TABLE_COLUMN.COLUMN_COMMENT));
        column.setColumnType(record.get(GEN_TABLE_COLUMN.COLUMN_TYPE));
        column.setJavaType(record.get(GEN_TABLE_COLUMN.JAVA_TYPE));
        column.setJavaField(record.get(GEN_TABLE_COLUMN.JAVA_FIELD));
        column.setIsPk(record.get(GEN_TABLE_COLUMN.IS_PK));
        column.setIsIncrement(record.get(GEN_TABLE_COLUMN.IS_INCREMENT));
        column.setIsRequired(record.get(GEN_TABLE_COLUMN.IS_REQUIRED));
        column.setIsInsert(record.get(GEN_TABLE_COLUMN.IS_INSERT));
        column.setIsEdit(record.get(GEN_TABLE_COLUMN.IS_EDIT));
        column.setIsList(record.get(GEN_TABLE_COLUMN.IS_LIST));
        column.setIsQuery(record.get(GEN_TABLE_COLUMN.IS_QUERY));
        column.setQueryType(record.get(GEN_TABLE_COLUMN.QUERY_TYPE));
        column.setHtmlType(record.get(GEN_TABLE_COLUMN.HTML_TYPE));
        column.setDictType(record.get(GEN_TABLE_COLUMN.DICT_TYPE));
        column.setSort(record.get(GEN_TABLE_COLUMN.SORT));
        column.setCreateBy(record.get(GEN_TABLE_COLUMN.CREATE_BY));
        column.setCreateTime(toDate(record.get(GEN_TABLE_COLUMN.CREATE_TIME)));
        column.setUpdateBy(record.get(GEN_TABLE_COLUMN.UPDATE_BY));
        column.setUpdateTime(toDate(record.get(GEN_TABLE_COLUMN.UPDATE_TIME)));
        return column;
    }

    static LocalDateTime toLocalDateTime(Date value) {
        return value == null ? null : LocalDateTime.ofInstant(value.toInstant(), ZoneId.systemDefault());
    }

    static Date toDate(LocalDateTime value) {
        return value == null ? null : Date.from(value.atZone(ZoneId.systemDefault()).toInstant());
    }

    static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
