package com.robotmonitor.generator.mapper.jooq;

import static com.robotmonitor.generator.mapper.jooq.JooqGenMapperSupport.isBlank;
import static com.robotmonitor.generator.mapper.jooq.JooqGenMapperSupport.mapColumn;
import static com.robotmonitor.generator.mapper.jooq.JooqGenMapperSupport.toLocalDateTime;
import static com.robotmonitor.jooq.generated.Tables.GEN_TABLE_COLUMN;

import com.robotmonitor.generator.domain.GenTableColumn;
import com.robotmonitor.generator.mapper.GenTableColumnMapper;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqGenTableColumnMapper implements GenTableColumnMapper {
    private final DSLContext dsl;
    private final DataSource dataSource;

    public JooqGenTableColumnMapper(DSLContext dsl, DataSource dataSource) {
        this.dsl = dsl;
        this.dataSource = dataSource;
    }

    @Override
    public List<GenTableColumn> selectDbTableColumnsByName(String tableName) {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData meta = connection.getMetaData();
            String schema = connection.getSchema();
            Map<String, String> primaryKeys = primaryKeys(meta, schema, tableName);
            List<GenTableColumn> columns = new ArrayList<>();
            try (ResultSet rs = meta.getColumns(connection.getCatalog(), schema, tableName, null)) {
                int sort = 0;
                while (rs.next()) {
                    sort++;
                    String columnName = rs.getString("COLUMN_NAME");
                    GenTableColumn column = new GenTableColumn();
                    column.setColumnName(columnName);
                    column.setColumnComment(defaultString(rs.getString("REMARKS")));
                    column.setColumnType(columnType(rs));
                    column.setIsRequired(rs.getInt("NULLABLE") == DatabaseMetaData.columnNoNulls && !primaryKeys.containsKey(columnName) ? "1" : null);
                    column.setIsPk(primaryKeys.containsKey(columnName) ? "1" : "0");
                    column.setIsIncrement("YES".equalsIgnoreCase(safeString(rs, "IS_AUTOINCREMENT")) ? "1" : "0");
                    column.setSort(sort);
                    columns.add(column);
                }
            }
            return columns;
        } catch (SQLException ex) {
            throw new IllegalStateException("Cannot read columns for table " + tableName, ex);
        }
    }

    @Override
    public List<GenTableColumn> selectGenTableColumnListByTableId(Long tableId) {
        return dsl.select(GEN_TABLE_COLUMN.fields())
            .from(GEN_TABLE_COLUMN)
            .where(GEN_TABLE_COLUMN.TABLE_ID.eq(tableId))
            .orderBy(GEN_TABLE_COLUMN.SORT.asc())
            .fetch(JooqGenMapperSupport::mapColumn);
    }

    @Override
    public int insertGenTableColumn(GenTableColumn column) {
        Long id = dsl.insertInto(GEN_TABLE_COLUMN)
            .set(writeValues(column, true))
            .returningResult(GEN_TABLE_COLUMN.COLUMN_ID)
            .fetchOne(GEN_TABLE_COLUMN.COLUMN_ID);
        column.setColumnId(id);
        return id == null ? 0 : 1;
    }

    @Override
    public int updateGenTableColumn(GenTableColumn column) {
        if (column.getColumnId() == null) {
            return 0;
        }
        Map<Field<?>, Object> values = writeValues(column, false);
        values.remove(GEN_TABLE_COLUMN.COLUMN_ID);
        if (values.isEmpty()) {
            return 0;
        }
        values.put(GEN_TABLE_COLUMN.UPDATE_TIME, LocalDateTime.now());
        return dsl.update(GEN_TABLE_COLUMN)
            .set(values)
            .where(GEN_TABLE_COLUMN.COLUMN_ID.eq(column.getColumnId()))
            .execute();
    }

    @Override
    public int deleteGenTableColumns(List<GenTableColumn> columns) {
        if (columns == null || columns.isEmpty()) {
            return 0;
        }
        List<Long> ids = columns.stream().map(GenTableColumn::getColumnId).filter(id -> id != null).toList();
        if (ids.isEmpty()) {
            return 0;
        }
        return dsl.deleteFrom(GEN_TABLE_COLUMN).where(GEN_TABLE_COLUMN.COLUMN_ID.in(ids)).execute();
    }

    @Override
    public int deleteGenTableColumnByIds(Long[] tableIds) {
        if (tableIds == null || tableIds.length == 0) {
            return 0;
        }
        return dsl.deleteFrom(GEN_TABLE_COLUMN).where(GEN_TABLE_COLUMN.TABLE_ID.in(Arrays.asList(tableIds))).execute();
    }

    private Map<Field<?>, Object> writeValues(GenTableColumn column, boolean insert) {
        Map<Field<?>, Object> values = new LinkedHashMap<>();
        put(values, GEN_TABLE_COLUMN.COLUMN_ID, column.getColumnId());
        put(values, GEN_TABLE_COLUMN.TABLE_ID, column.getTableId());
        put(values, GEN_TABLE_COLUMN.COLUMN_NAME, column.getColumnName());
        put(values, GEN_TABLE_COLUMN.COLUMN_COMMENT, column.getColumnComment());
        put(values, GEN_TABLE_COLUMN.COLUMN_TYPE, column.getColumnType());
        put(values, GEN_TABLE_COLUMN.JAVA_TYPE, column.getJavaType());
        put(values, GEN_TABLE_COLUMN.JAVA_FIELD, column.getJavaField());
        put(values, GEN_TABLE_COLUMN.IS_PK, column.getIsPk());
        put(values, GEN_TABLE_COLUMN.IS_INCREMENT, column.getIsIncrement());
        put(values, GEN_TABLE_COLUMN.IS_REQUIRED, column.getIsRequired());
        put(values, GEN_TABLE_COLUMN.IS_INSERT, column.getIsInsert());
        put(values, GEN_TABLE_COLUMN.IS_EDIT, column.getIsEdit());
        put(values, GEN_TABLE_COLUMN.IS_LIST, column.getIsList());
        put(values, GEN_TABLE_COLUMN.IS_QUERY, column.getIsQuery());
        put(values, GEN_TABLE_COLUMN.QUERY_TYPE, column.getQueryType());
        put(values, GEN_TABLE_COLUMN.HTML_TYPE, column.getHtmlType());
        put(values, GEN_TABLE_COLUMN.DICT_TYPE, column.getDictType());
        put(values, GEN_TABLE_COLUMN.SORT, column.getSort());
        put(values, GEN_TABLE_COLUMN.CREATE_BY, column.getCreateBy());
        put(values, GEN_TABLE_COLUMN.CREATE_TIME, insert ? LocalDateTime.now() : toLocalDateTime(column.getCreateTime()));
        put(values, GEN_TABLE_COLUMN.UPDATE_BY, column.getUpdateBy());
        put(values, GEN_TABLE_COLUMN.UPDATE_TIME, toLocalDateTime(column.getUpdateTime()));
        return values;
    }

    private Map<String, String> primaryKeys(DatabaseMetaData meta, String schema, String tableName) throws SQLException {
        Map<String, String> keys = new LinkedHashMap<>();
        try (ResultSet rs = meta.getPrimaryKeys(null, schema, tableName)) {
            while (rs.next()) {
                keys.put(rs.getString("COLUMN_NAME"), "1");
            }
        }
        return keys;
    }

    private String columnType(ResultSet rs) throws SQLException {
        String typeName = rs.getString("TYPE_NAME");
        int size = rs.getInt("COLUMN_SIZE");
        int scale = rs.getInt("DECIMAL_DIGITS");
        if (size > 0 && !typeName.equalsIgnoreCase("TEXT") && !typeName.equalsIgnoreCase("CLOB") && !typeName.equalsIgnoreCase("BLOB")) {
            if (scale > 0) {
                return typeName.toLowerCase() + "(" + size + "," + scale + ")";
            }
            return typeName.toLowerCase() + "(" + size + ")";
        }
        return typeName.toLowerCase();
    }

    private String safeString(ResultSet rs, String column) {
        try {
            return rs.getString(column);
        } catch (SQLException ex) {
            return "";
        }
    }

    private String defaultString(String value) {
        return isBlank(value) ? "" : value;
    }

    private void put(Map<Field<?>, Object> values, Field<?> field, Object value) {
        if (value != null) {
            values.put(field, value);
        }
    }
}
