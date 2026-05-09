package com.robotmonitor.generator.mapper.jooq;

import static com.robotmonitor.generator.mapper.jooq.JooqGenMapperSupport.isBlank;
import static com.robotmonitor.generator.mapper.jooq.JooqGenMapperSupport.toLocalDateTime;
import static com.robotmonitor.jooq.generated.Tables.GEN_TABLE;
import static com.robotmonitor.jooq.generated.Tables.GEN_TABLE_COLUMN;

import com.robotmonitor.generator.domain.GenTable;
import com.robotmonitor.generator.mapper.GenTableMapper;
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
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqGenTableMapper implements GenTableMapper {
    private final DSLContext dsl;
    private final DataSource dataSource;

    public JooqGenTableMapper(DSLContext dsl, DataSource dataSource) {
        this.dsl = dsl;
        this.dataSource = dataSource;
    }

    @Override
    public List<GenTable> selectGenTableList(GenTable query) {
        return dsl.select(GEN_TABLE.fields())
            .from(GEN_TABLE)
            .where(tableConditions(query))
            .orderBy(GEN_TABLE.CREATE_TIME.desc())
            .fetch(JooqGenMapperSupport::mapTable);
    }

    @Override
    public List<GenTable> selectDbTableList(GenTable query) {
        List<GenTable> tables = readDatabaseTables();
        List<String> imported = dsl.select(GEN_TABLE.TABLE_NAME).from(GEN_TABLE).fetch(GEN_TABLE.TABLE_NAME);
        return tables.stream()
            .filter(table -> !table.getTableName().startsWith("qrtz_"))
            .filter(table -> !table.getTableName().startsWith("gen_"))
            .filter(table -> !imported.contains(table.getTableName()))
            .filter(table -> matches(table.getTableName(), query == null ? null : query.getTableName()))
            .filter(table -> matches(table.getTableComment(), query == null ? null : query.getTableComment()))
            .toList();
    }

    @Override
    public List<GenTable> selectDbTableListByNames(String[] names) {
        if (names == null || names.length == 0) {
            return List.of();
        }
        List<String> wanted = Arrays.asList(names);
        return readDatabaseTables().stream()
            .filter(table -> wanted.contains(table.getTableName()))
            .toList();
    }

    @Override
    public List<GenTable> selectGenTableAll() {
        return dsl.select(GEN_TABLE.fields())
            .from(GEN_TABLE)
            .orderBy(GEN_TABLE.CREATE_TIME.desc())
            .fetch(JooqGenMapperSupport::mapTable);
    }

    @Override
    public GenTable selectGenTableById(Long tableId) {
        GenTable table = dsl.select(GEN_TABLE.fields())
            .from(GEN_TABLE)
            .where(GEN_TABLE.TABLE_ID.eq(tableId))
            .fetchOne(JooqGenMapperSupport::mapTable);
        attachColumns(table);
        return table;
    }

    @Override
    public GenTable selectGenTableByName(String tableName) {
        GenTable table = dsl.select(GEN_TABLE.fields())
            .from(GEN_TABLE)
            .where(GEN_TABLE.TABLE_NAME.eq(tableName))
            .fetchOne(JooqGenMapperSupport::mapTable);
        attachColumns(table);
        return table;
    }

    @Override
    public int insertGenTable(GenTable table) {
        Long id = dsl.insertInto(GEN_TABLE)
            .set(writeValues(table, true))
            .returningResult(GEN_TABLE.TABLE_ID)
            .fetchOne(GEN_TABLE.TABLE_ID);
        table.setTableId(id);
        return id == null ? 0 : 1;
    }

    @Override
    public int updateGenTable(GenTable table) {
        if (table.getTableId() == null) {
            return 0;
        }
        Map<Field<?>, Object> values = writeValues(table, false);
        values.remove(GEN_TABLE.TABLE_ID);
        values.put(GEN_TABLE.UPDATE_TIME, LocalDateTime.now());
        if (values.isEmpty()) {
            return 0;
        }
        return dsl.update(GEN_TABLE)
            .set(values)
            .where(GEN_TABLE.TABLE_ID.eq(table.getTableId()))
            .execute();
    }

    @Override
    public int deleteGenTableByIds(Long[] tableIds) {
        if (tableIds == null || tableIds.length == 0) {
            return 0;
        }
        return dsl.deleteFrom(GEN_TABLE).where(GEN_TABLE.TABLE_ID.in(Arrays.asList(tableIds))).execute();
    }

    private Condition tableConditions(GenTable table) {
        if (table == null) {
            return DSL.noCondition();
        }
        return DSL.and(
            isBlank(table.getTableName()) ? DSL.noCondition() : DSL.lower(GEN_TABLE.TABLE_NAME).like("%" + table.getTableName().toLowerCase() + "%"),
            isBlank(table.getTableComment()) ? DSL.noCondition() : DSL.lower(GEN_TABLE.TABLE_COMMENT).like("%" + table.getTableComment().toLowerCase() + "%")
        );
    }

    private Map<Field<?>, Object> writeValues(GenTable table, boolean insert) {
        Map<Field<?>, Object> values = new LinkedHashMap<>();
        put(values, GEN_TABLE.TABLE_ID, table.getTableId());
        put(values, GEN_TABLE.TABLE_NAME, table.getTableName());
        put(values, GEN_TABLE.TABLE_COMMENT, table.getTableComment());
        put(values, GEN_TABLE.SUB_TABLE_NAME, table.getSubTableName());
        put(values, GEN_TABLE.SUB_TABLE_FK_NAME, table.getSubTableFkName());
        put(values, GEN_TABLE.CLASS_NAME, table.getClassName());
        put(values, GEN_TABLE.TPL_CATEGORY, table.getTplCategory());
        put(values, GEN_TABLE.PACKAGE_NAME, table.getPackageName());
        put(values, GEN_TABLE.MODULE_NAME, table.getModuleName());
        put(values, GEN_TABLE.BUSINESS_NAME, table.getBusinessName());
        put(values, GEN_TABLE.FUNCTION_NAME, table.getFunctionName());
        put(values, GEN_TABLE.FUNCTION_AUTHOR, table.getFunctionAuthor());
        put(values, GEN_TABLE.GEN_TYPE, table.getGenType());
        put(values, GEN_TABLE.GEN_PATH, table.getGenPath());
        put(values, GEN_TABLE.OPTIONS, table.getOptions());
        put(values, GEN_TABLE.CREATE_BY, table.getCreateBy());
        put(values, GEN_TABLE.CREATE_TIME, insert ? LocalDateTime.now() : toLocalDateTime(table.getCreateTime()));
        put(values, GEN_TABLE.UPDATE_BY, table.getUpdateBy());
        put(values, GEN_TABLE.UPDATE_TIME, toLocalDateTime(table.getUpdateTime()));
        put(values, GEN_TABLE.REMARK, table.getRemark());
        return values;
    }

    private void attachColumns(GenTable table) {
        if (table == null || table.getTableId() == null) {
            return;
        }
        table.setColumns(dsl.select(GEN_TABLE_COLUMN.fields())
            .from(GEN_TABLE_COLUMN)
            .where(GEN_TABLE_COLUMN.TABLE_ID.eq(table.getTableId()))
            .orderBy(GEN_TABLE_COLUMN.SORT.asc())
            .fetch(JooqGenMapperSupport::mapColumn));
    }

    private List<GenTable> readDatabaseTables() {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData meta = connection.getMetaData();
            String schema = connection.getSchema();
            List<GenTable> tables = new ArrayList<>();
            try (ResultSet rs = meta.getTables(connection.getCatalog(), schema, "%", new String[] {"TABLE"})) {
                while (rs.next()) {
                    GenTable table = new GenTable();
                    table.setTableName(rs.getString("TABLE_NAME"));
                    table.setTableComment(defaultString(rs.getString("REMARKS")));
                    table.setCreateTime(new java.util.Date());
                    table.setUpdateTime(new java.util.Date());
                    tables.add(table);
                }
            }
            return tables;
        } catch (SQLException ex) {
            throw new IllegalStateException("Cannot read database tables", ex);
        }
    }

    private boolean matches(String value, String keyword) {
        return isBlank(keyword) || (value != null && value.toLowerCase().contains(keyword.toLowerCase()));
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
