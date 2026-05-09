package com.robotmonitor.generator.controller;

import static com.robotmonitor.jooq.generated.Tables.GEN_TABLE;
import static com.robotmonitor.jooq.generated.Tables.GEN_TABLE_COLUMN;

import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.page.TableDataInfo;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tool/gen")
public class GenController {
    private final DSLContext dsl;

    public GenController(DSLContext dsl) {
        this.dsl = dsl;
    }

    @GetMapping("/list")
    public TableDataInfo genList(@RequestParam Map<String, String> query) {
        int pageNum = parseInt(query.get("pageNum"), 1);
        int pageSize = parseInt(query.get("pageSize"), 20);
        Condition condition = tableCondition(query);
        long total = dsl.fetchCount(GEN_TABLE, condition);
        List<Map<String, Object>> rows = dsl.select(GEN_TABLE.fields())
            .from(GEN_TABLE)
            .where(condition)
            .orderBy(GEN_TABLE.CREATE_TIME.desc(), GEN_TABLE.TABLE_ID.desc())
            .limit(pageSize)
            .offset(Math.max(0, pageNum - 1) * pageSize)
            .fetch(this::mapTable);
        return table(rows, total);
    }

    @GetMapping("/{tableId}")
    public AjaxResult getInfo(@PathVariable Long tableId) {
        Map<String, Object> info = tableById(tableId);
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("info", info);
        payload.put("rows", columnsByTableId(tableId));
        payload.put("tables", dsl.select(GEN_TABLE.fields()).from(GEN_TABLE).orderBy(GEN_TABLE.TABLE_NAME.asc()).fetch(this::mapTable));
        return AjaxResult.success(payload);
    }

    @GetMapping("/db/list")
    public TableDataInfo dataList(@RequestParam Map<String, String> query) {
        String tableName = trim(query.get("tableName"));
        String tableComment = trim(query.get("tableComment"));
        List<String> imported = dsl.select(GEN_TABLE.TABLE_NAME).from(GEN_TABLE).fetch(GEN_TABLE.TABLE_NAME);
        List<Map<String, Object>> rows = candidateTables().stream()
            .filter(row -> !imported.contains(String.valueOf(row.get("tableName"))))
            .filter(row -> contains(row.get("tableName"), tableName))
            .filter(row -> contains(row.get("tableComment"), tableComment))
            .toList();
        return table(rows, rows.size());
    }

    @GetMapping("/column/{tableId}")
    public TableDataInfo columnList(@PathVariable Long tableId) {
        List<Map<String, Object>> rows = columnsByTableId(tableId);
        return table(rows, rows.size());
    }

    @PostMapping("/importTable")
    public AjaxResult importTableSave(@RequestParam String tables) {
        List<String> tableNames = split(tables);
        if (tableNames.isEmpty()) {
            return AjaxResult.error("请选择要导入的表");
        }
        List<String> imported = new ArrayList<>();
        for (String tableName : tableNames) {
            if (existsTable(tableName)) {
                continue;
            }
            Map<String, Object> candidate = candidateByName(tableName);
            if (candidate == null) {
                continue;
            }
            Long tableId = insertGenTable(candidate);
            insertColumns(tableId, tableName);
            imported.add(tableName);
        }
        return AjaxResult.success(Map.of("imported", imported));
    }

    @PutMapping
    @SuppressWarnings("unchecked")
    public AjaxResult editSave(@RequestBody Map<String, Object> payload) {
        Map<String, Object> info = payload.containsKey("info") && payload.get("info") instanceof Map<?, ?>
            ? (Map<String, Object>) payload.get("info")
            : payload;
        Long tableId = parseLong(value(info, "tableId", "table_id"));
        if (tableId == null) {
            return AjaxResult.error("表 ID 不能为空");
        }
        updateGenTable(tableId, info);
        Object rows = payload.get("rows");
        if (rows instanceof List<?> list) {
            for (int index = 0; index < list.size(); index++) {
                Object item = list.get(index);
                if (item instanceof Map<?, ?> column) {
                    updateColumn(tableId, (Map<String, Object>) column, index + 1);
                }
            }
        }
        return AjaxResult.success();
    }

    @DeleteMapping("/{tableIds}")
    public AjaxResult remove(@PathVariable String tableIds) {
        List<Long> ids = split(tableIds).stream().map(this::parseLong).filter(Objects::nonNull).toList();
        if (ids.isEmpty()) {
            return AjaxResult.error("请选择要删除的表");
        }
        dsl.deleteFrom(GEN_TABLE_COLUMN).where(GEN_TABLE_COLUMN.TABLE_ID.in(ids)).execute();
        int rows = dsl.deleteFrom(GEN_TABLE).where(GEN_TABLE.TABLE_ID.in(ids)).execute();
        return AjaxResult.success(rows);
    }

    @GetMapping("/preview/{tableId}")
    public AjaxResult preview(@PathVariable Long tableId) {
        Map<String, Object> info = tableById(tableId);
        if (info == null) {
            return AjaxResult.error("表不存在");
        }
        List<Map<String, Object>> columns = columnsByTableId(tableId);
        return AjaxResult.success(previewMap(info, columns));
    }

    @GetMapping("/download/{tableName}")
    public void download(HttpServletResponse response, @PathVariable String tableName) throws IOException {
        writeZipText(response, tableName, previewText(tableName));
    }

    @GetMapping("/genCode/{tableName}")
    public AjaxResult genCode(@PathVariable String tableName) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("tableName", tableName);
        data.put("generated", true);
        data.put("message", "本地代码生成已完成，可通过下载查看生成内容");
        return AjaxResult.success(data);
    }

    @GetMapping("/synchDb/{tableName}")
    public AjaxResult synchDb(@PathVariable String tableName) {
        Map<String, Object> table = tableByName(tableName);
        if (table == null) {
            return AjaxResult.error("请先导入表：" + tableName);
        }
        Long tableId = parseLong(table.get("tableId"));
        List<String> existing = dsl.select(GEN_TABLE_COLUMN.COLUMN_NAME)
            .from(GEN_TABLE_COLUMN)
            .where(GEN_TABLE_COLUMN.TABLE_ID.eq(tableId))
            .fetch(GEN_TABLE_COLUMN.COLUMN_NAME);
        int inserted = 0;
        for (ColumnSeed seed : columnSeeds(tableName)) {
            if (!existing.contains(seed.columnName())) {
                insertColumn(tableId, seed, existing.size() + inserted + 1);
                inserted++;
            }
        }
        dsl.update(GEN_TABLE)
            .set(GEN_TABLE.UPDATE_TIME, LocalDateTime.now())
            .where(GEN_TABLE.TABLE_ID.eq(tableId))
            .execute();
        return AjaxResult.success(Map.of("inserted", inserted));
    }

    @GetMapping("/batchGenCode")
    public void batchGenCode(HttpServletResponse response, @RequestParam String tables) throws IOException {
        String content = split(tables).stream()
            .map(this::previewText)
            .collect(Collectors.joining("\n\n/* ============================== */\n\n"));
        writeZipText(response, "robotmonitor", content);
    }

    private Condition tableCondition(Map<String, String> query) {
        return DSL.and(
            like(GEN_TABLE.TABLE_NAME, query.get("tableName")),
            like(GEN_TABLE.TABLE_COMMENT, query.get("tableComment")),
            like(GEN_TABLE.CLASS_NAME, query.get("className")),
            like(GEN_TABLE.MODULE_NAME, query.get("moduleName"))
        );
    }

    private Long insertGenTable(Map<String, Object> candidate) {
        String tableName = String.valueOf(candidate.get("tableName"));
        String className = upperCamel(tableName);
        String businessName = businessName(tableName);
        LocalDateTime now = LocalDateTime.now();
        Long tableId = dsl.insertInto(GEN_TABLE)
            .set(GEN_TABLE.TABLE_NAME, tableName)
            .set(GEN_TABLE.TABLE_COMMENT, String.valueOf(candidate.get("tableComment")))
            .set(GEN_TABLE.CLASS_NAME, className)
            .set(GEN_TABLE.TPL_CATEGORY, "crud")
            .set(GEN_TABLE.PACKAGE_NAME, "com.robotmonitor." + moduleName(tableName))
            .set(GEN_TABLE.MODULE_NAME, moduleName(tableName))
            .set(GEN_TABLE.BUSINESS_NAME, businessName)
            .set(GEN_TABLE.FUNCTION_NAME, String.valueOf(candidate.get("tableComment")))
            .set(GEN_TABLE.FUNCTION_AUTHOR, "system")
            .set(GEN_TABLE.GEN_TYPE, "0")
            .set(GEN_TABLE.GEN_PATH, "/")
            .set(GEN_TABLE.OPTIONS, "{}")
            .set(GEN_TABLE.CREATE_BY, "system")
            .set(GEN_TABLE.CREATE_TIME, now)
            .set(GEN_TABLE.UPDATE_BY, "system")
            .set(GEN_TABLE.UPDATE_TIME, now)
            .set(GEN_TABLE.REMARK, "local generated")
            .returningResult(GEN_TABLE.TABLE_ID)
            .fetchOne(GEN_TABLE.TABLE_ID);
        return tableId;
    }

    private void updateGenTable(Long tableId, Map<String, Object> info) {
        dsl.update(GEN_TABLE)
            .set(GEN_TABLE.TABLE_COMMENT, text(info, "tableComment", "table_comment", ""))
            .set(GEN_TABLE.CLASS_NAME, text(info, "className", "class_name", ""))
            .set(GEN_TABLE.TPL_CATEGORY, text(info, "tplCategory", "tpl_category", "crud"))
            .set(GEN_TABLE.PACKAGE_NAME, text(info, "packageName", "package_name", "com.robotmonitor"))
            .set(GEN_TABLE.MODULE_NAME, text(info, "moduleName", "module_name", "system"))
            .set(GEN_TABLE.BUSINESS_NAME, text(info, "businessName", "business_name", "demo"))
            .set(GEN_TABLE.FUNCTION_NAME, text(info, "functionName", "function_name", ""))
            .set(GEN_TABLE.FUNCTION_AUTHOR, text(info, "functionAuthor", "function_author", "system"))
            .set(GEN_TABLE.GEN_TYPE, text(info, "genType", "gen_type", "0"))
            .set(GEN_TABLE.GEN_PATH, text(info, "genPath", "gen_path", "/"))
            .set(GEN_TABLE.OPTIONS, text(info, "options", "{}"))
            .set(GEN_TABLE.UPDATE_BY, "system")
            .set(GEN_TABLE.UPDATE_TIME, LocalDateTime.now())
            .set(GEN_TABLE.REMARK, text(info, "remark", ""))
            .where(GEN_TABLE.TABLE_ID.eq(tableId))
            .execute();
    }

    private void updateColumn(Long tableId, Map<String, Object> payload, int sort) {
        Long columnId = parseLong(value(payload, "columnId", "column_id"));
        if (columnId == null) {
            ColumnSeed seed = new ColumnSeed(
                text(payload, "columnName", "column_name", "field_" + sort),
                text(payload, "columnComment", "column_comment", "字段" + sort),
                text(payload, "columnType", "column_type", "varchar(255)"),
                text(payload, "javaType", "java_type", "String"),
                text(payload, "javaField", "java_field", "field" + sort),
                text(payload, "isPk", "is_pk", "0"),
                text(payload, "isIncrement", "is_increment", "0"),
                text(payload, "isRequired", "is_required", "0"),
                text(payload, "isInsert", "is_insert", "1"),
                text(payload, "isEdit", "is_edit", "1"),
                text(payload, "isList", "is_list", "1"),
                text(payload, "isQuery", "is_query", "0"),
                text(payload, "queryType", "query_type", "EQ"),
                text(payload, "htmlType", "html_type", "input"),
                text(payload, "dictType", "dict_type", "")
            );
            insertColumn(tableId, seed, sort);
            return;
        }
        dsl.update(GEN_TABLE_COLUMN)
            .set(GEN_TABLE_COLUMN.COLUMN_COMMENT, text(payload, "columnComment", "column_comment", ""))
            .set(GEN_TABLE_COLUMN.COLUMN_TYPE, text(payload, "columnType", "column_type", "varchar(255)"))
            .set(GEN_TABLE_COLUMN.JAVA_TYPE, text(payload, "javaType", "java_type", "String"))
            .set(GEN_TABLE_COLUMN.JAVA_FIELD, text(payload, "javaField", "java_field", "field" + sort))
            .set(GEN_TABLE_COLUMN.IS_PK, text(payload, "isPk", "is_pk", "0"))
            .set(GEN_TABLE_COLUMN.IS_INCREMENT, text(payload, "isIncrement", "is_increment", "0"))
            .set(GEN_TABLE_COLUMN.IS_REQUIRED, text(payload, "isRequired", "is_required", "0"))
            .set(GEN_TABLE_COLUMN.IS_INSERT, text(payload, "isInsert", "is_insert", "1"))
            .set(GEN_TABLE_COLUMN.IS_EDIT, text(payload, "isEdit", "is_edit", "1"))
            .set(GEN_TABLE_COLUMN.IS_LIST, text(payload, "isList", "is_list", "1"))
            .set(GEN_TABLE_COLUMN.IS_QUERY, text(payload, "isQuery", "is_query", "0"))
            .set(GEN_TABLE_COLUMN.QUERY_TYPE, text(payload, "queryType", "query_type", "EQ"))
            .set(GEN_TABLE_COLUMN.HTML_TYPE, text(payload, "htmlType", "html_type", "input"))
            .set(GEN_TABLE_COLUMN.DICT_TYPE, text(payload, "dictType", "dict_type", ""))
            .set(GEN_TABLE_COLUMN.SORT, sort)
            .set(GEN_TABLE_COLUMN.UPDATE_BY, "system")
            .set(GEN_TABLE_COLUMN.UPDATE_TIME, LocalDateTime.now())
            .where(GEN_TABLE_COLUMN.COLUMN_ID.eq(columnId).and(GEN_TABLE_COLUMN.TABLE_ID.eq(tableId)))
            .execute();
    }

    private void insertColumns(Long tableId, String tableName) {
        int sort = 0;
        for (ColumnSeed seed : columnSeeds(tableName)) {
            sort++;
            insertColumn(tableId, seed, sort);
        }
    }

    private void insertColumn(Long tableId, ColumnSeed seed, int sort) {
        LocalDateTime now = LocalDateTime.now();
        dsl.insertInto(GEN_TABLE_COLUMN)
            .set(GEN_TABLE_COLUMN.TABLE_ID, tableId)
            .set(GEN_TABLE_COLUMN.COLUMN_NAME, seed.columnName())
            .set(GEN_TABLE_COLUMN.COLUMN_COMMENT, seed.columnComment())
            .set(GEN_TABLE_COLUMN.COLUMN_TYPE, seed.columnType())
            .set(GEN_TABLE_COLUMN.JAVA_TYPE, seed.javaType())
            .set(GEN_TABLE_COLUMN.JAVA_FIELD, seed.javaField())
            .set(GEN_TABLE_COLUMN.IS_PK, seed.isPk())
            .set(GEN_TABLE_COLUMN.IS_INCREMENT, seed.isIncrement())
            .set(GEN_TABLE_COLUMN.IS_REQUIRED, seed.isRequired())
            .set(GEN_TABLE_COLUMN.IS_INSERT, seed.isInsert())
            .set(GEN_TABLE_COLUMN.IS_EDIT, seed.isEdit())
            .set(GEN_TABLE_COLUMN.IS_LIST, seed.isList())
            .set(GEN_TABLE_COLUMN.IS_QUERY, seed.isQuery())
            .set(GEN_TABLE_COLUMN.QUERY_TYPE, seed.queryType())
            .set(GEN_TABLE_COLUMN.HTML_TYPE, seed.htmlType())
            .set(GEN_TABLE_COLUMN.DICT_TYPE, seed.dictType())
            .set(GEN_TABLE_COLUMN.SORT, sort)
            .set(GEN_TABLE_COLUMN.CREATE_BY, "system")
            .set(GEN_TABLE_COLUMN.CREATE_TIME, now)
            .set(GEN_TABLE_COLUMN.UPDATE_BY, "system")
            .set(GEN_TABLE_COLUMN.UPDATE_TIME, now)
            .execute();
    }

    private Map<String, Object> tableById(Long tableId) {
        return dsl.select(GEN_TABLE.fields())
            .from(GEN_TABLE)
            .where(GEN_TABLE.TABLE_ID.eq(tableId))
            .fetchOne(this::mapTable);
    }

    private Map<String, Object> tableByName(String tableName) {
        return dsl.select(GEN_TABLE.fields())
            .from(GEN_TABLE)
            .where(GEN_TABLE.TABLE_NAME.eq(tableName))
            .fetchOne(this::mapTable);
    }

    private List<Map<String, Object>> columnsByTableId(Long tableId) {
        return dsl.select(GEN_TABLE_COLUMN.fields())
            .from(GEN_TABLE_COLUMN)
            .where(GEN_TABLE_COLUMN.TABLE_ID.eq(tableId))
            .orderBy(GEN_TABLE_COLUMN.SORT.asc(), GEN_TABLE_COLUMN.COLUMN_ID.asc())
            .fetch(this::mapColumn);
    }

    private Map<String, Object> mapTable(Record record) {
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("tableId", record.get(GEN_TABLE.TABLE_ID));
        row.put("tableName", record.get(GEN_TABLE.TABLE_NAME));
        row.put("tableComment", record.get(GEN_TABLE.TABLE_COMMENT));
        row.put("subTableName", record.get(GEN_TABLE.SUB_TABLE_NAME));
        row.put("subTableFkName", record.get(GEN_TABLE.SUB_TABLE_FK_NAME));
        row.put("className", record.get(GEN_TABLE.CLASS_NAME));
        row.put("tplCategory", record.get(GEN_TABLE.TPL_CATEGORY));
        row.put("packageName", record.get(GEN_TABLE.PACKAGE_NAME));
        row.put("moduleName", record.get(GEN_TABLE.MODULE_NAME));
        row.put("businessName", record.get(GEN_TABLE.BUSINESS_NAME));
        row.put("functionName", record.get(GEN_TABLE.FUNCTION_NAME));
        row.put("functionAuthor", record.get(GEN_TABLE.FUNCTION_AUTHOR));
        row.put("genType", record.get(GEN_TABLE.GEN_TYPE));
        row.put("genPath", record.get(GEN_TABLE.GEN_PATH));
        row.put("options", record.get(GEN_TABLE.OPTIONS));
        row.put("createBy", record.get(GEN_TABLE.CREATE_BY));
        row.put("createTime", record.get(GEN_TABLE.CREATE_TIME));
        row.put("updateBy", record.get(GEN_TABLE.UPDATE_BY));
        row.put("updateTime", record.get(GEN_TABLE.UPDATE_TIME));
        row.put("remark", record.get(GEN_TABLE.REMARK));
        return row;
    }

    private Map<String, Object> mapColumn(Record record) {
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("columnId", record.get(GEN_TABLE_COLUMN.COLUMN_ID));
        row.put("tableId", record.get(GEN_TABLE_COLUMN.TABLE_ID));
        row.put("columnName", record.get(GEN_TABLE_COLUMN.COLUMN_NAME));
        row.put("columnComment", record.get(GEN_TABLE_COLUMN.COLUMN_COMMENT));
        row.put("columnType", record.get(GEN_TABLE_COLUMN.COLUMN_TYPE));
        row.put("javaType", record.get(GEN_TABLE_COLUMN.JAVA_TYPE));
        row.put("javaField", record.get(GEN_TABLE_COLUMN.JAVA_FIELD));
        row.put("isPk", record.get(GEN_TABLE_COLUMN.IS_PK));
        row.put("isIncrement", record.get(GEN_TABLE_COLUMN.IS_INCREMENT));
        row.put("isRequired", record.get(GEN_TABLE_COLUMN.IS_REQUIRED));
        row.put("isInsert", record.get(GEN_TABLE_COLUMN.IS_INSERT));
        row.put("isEdit", record.get(GEN_TABLE_COLUMN.IS_EDIT));
        row.put("isList", record.get(GEN_TABLE_COLUMN.IS_LIST));
        row.put("isQuery", record.get(GEN_TABLE_COLUMN.IS_QUERY));
        row.put("queryType", record.get(GEN_TABLE_COLUMN.QUERY_TYPE));
        row.put("htmlType", record.get(GEN_TABLE_COLUMN.HTML_TYPE));
        row.put("dictType", record.get(GEN_TABLE_COLUMN.DICT_TYPE));
        row.put("sort", record.get(GEN_TABLE_COLUMN.SORT));
        row.put("createBy", record.get(GEN_TABLE_COLUMN.CREATE_BY));
        row.put("createTime", record.get(GEN_TABLE_COLUMN.CREATE_TIME));
        row.put("updateBy", record.get(GEN_TABLE_COLUMN.UPDATE_BY));
        row.put("updateTime", record.get(GEN_TABLE_COLUMN.UPDATE_TIME));
        return row;
    }

    private Map<String, String> previewMap(Map<String, Object> info, List<Map<String, Object>> columns) {
        String className = defaultText(asString(info.get("className")), "DemoEntity");
        String moduleName = defaultText(asString(info.get("moduleName")), "system");
        String businessName = defaultText(asString(info.get("businessName")), "demo");
        String functionName = defaultText(asString(info.get("functionName")), "示例功能");
        Map<String, String> preview = new LinkedHashMap<>();
        preview.put("domain/" + className + ".java", domainCode(info, columns));
        preview.put("controller/" + className + "Controller.java", controllerCode(info));
        preview.put("api/" + businessName + ".js", apiCode(moduleName, businessName));
        preview.put("views/" + moduleName + "/" + businessName + "/index.vue", vueCode(functionName, moduleName, businessName, columns));
        preview.put("sql/" + businessName + ".sql", sqlCode(info, columns));
        return preview;
    }

    private String previewText(String tableName) {
        Map<String, Object> table = tableByName(tableName);
        if (table == null) {
            return "/* table not imported: " + tableName + " */";
        }
        return previewMap(table, columnsByTableId(parseLong(table.get("tableId")))).entrySet().stream()
            .map(entry -> "/* " + entry.getKey() + " */\n" + entry.getValue())
            .collect(Collectors.joining("\n\n"));
    }

    private String domainCode(Map<String, Object> info, List<Map<String, Object>> columns) {
        String packageName = defaultText(asString(info.get("packageName")), "com.robotmonitor.system");
        String className = defaultText(asString(info.get("className")), "DemoEntity");
        String fields = columns.stream()
            .filter(column -> !"1".equals(asString(column.get("isPk"))))
            .map(column -> "    private " + defaultText(asString(column.get("javaType")), "String") + " " + defaultText(asString(column.get("javaField")), "field") + ";")
            .collect(Collectors.joining("\n"));
        return """
            package %s.domain;

            public class %s {
                private Long id;
            %s
            }
            """.formatted(packageName, className, fields.isBlank() ? "" : "\n" + fields);
    }

    private String controllerCode(Map<String, Object> info) {
        String packageName = defaultText(asString(info.get("packageName")), "com.robotmonitor.system");
        String className = defaultText(asString(info.get("className")), "DemoEntity");
        String moduleName = defaultText(asString(info.get("moduleName")), "system");
        String businessName = defaultText(asString(info.get("businessName")), "demo");
        return """
            package %s.controller;

            import org.springframework.web.bind.annotation.RequestMapping;
            import org.springframework.web.bind.annotation.RestController;

            @RestController
            @RequestMapping("/%s/%s")
            public class %sController {
            }
            """.formatted(packageName, moduleName, businessName, className);
    }

    private String apiCode(String moduleName, String businessName) {
        String base = "/" + moduleName + "/" + businessName;
        return """
            import { request } from '@/api/http'

            export const list%s = (query) => request('%s/list', { query })
            export const get%s = (id) => request(`${'%s'}/${id}`)
            export const add%s = (data) => request('%s', { method: 'POST', body: JSON.stringify(data) })
            export const update%s = (data) => request('%s', { method: 'PUT', body: JSON.stringify(data) })
            export const del%s = (ids) => request(`${'%s'}/${ids}`, { method: 'DELETE' })
            """.formatted(upperCamel(businessName), base, upperCamel(businessName), base, upperCamel(businessName), base, upperCamel(businessName), base, upperCamel(businessName), base);
    }

    private String vueCode(String functionName, String moduleName, String businessName, List<Map<String, Object>> columns) {
        String tableColumns = columns.stream()
            .filter(column -> "1".equals(asString(column.get("isList"))))
            .map(column -> "      <el-table-column prop=\"" + defaultText(asString(column.get("javaField")), "field") + "\" label=\"" + defaultText(asString(column.get("columnComment")), "字段") + "\" />")
            .collect(Collectors.joining("\n"));
        String formItems = columns.stream()
            .filter(column -> "1".equals(asString(column.get("isEdit"))))
            .map(column -> "      <el-form-item label=\"" + defaultText(asString(column.get("columnComment")), "字段") + "\"><el-input v-model=\"form." + defaultText(asString(column.get("javaField")), "field") + "\" /></el-form-item>")
            .collect(Collectors.joining("\n"));
        return """
            <template>
              <el-card>
                <template #header><h1>%s</h1></template>
                <el-table :data="rows" border>
            %s
                </el-table>
                <el-dialog v-model="open" title="编辑">
                  <el-form label-position="top">
            %s
                  </el-form>
                </el-dialog>
              </el-card>
            </template>

            <script setup>
            import { ref } from 'vue'
            import { request } from '@/api/http'

            const rows = ref([])
            const open = ref(false)
            const form = ref({})
            const load = async () => {
              const response = await request('/%s/%s/list')
              rows.value = response.rows || []
            }
            load()
            </script>
            """.formatted(functionName, tableColumns, formItems, moduleName, businessName);
    }

    private String sqlCode(Map<String, Object> info, List<Map<String, Object>> columns) {
        String tableName = defaultText(asString(info.get("tableName")), "demo_table");
        String body = columns.stream()
            .map(column -> "  `" + defaultText(asString(column.get("columnName")), "field") + "` " + defaultText(asString(column.get("columnType")), "varchar(255)") + " COMMENT '" + defaultText(asString(column.get("columnComment")), "") + "'")
            .collect(Collectors.joining(",\n"));
        return "CREATE TABLE `" + tableName + "` (\n" + body + "\n) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
    }

    private List<Map<String, Object>> candidateTables() {
        List<Map<String, Object>> rows = new ArrayList<>();
        addCandidate(rows, "sys_user", "用户信息表");
        addCandidate(rows, "sys_role", "角色信息表");
        addCandidate(rows, "sys_menu", "菜单权限表");
        addCandidate(rows, "config_robot", "机器人配置表");
        addCandidate(rows, "config_region", "功能区域表");
        addCandidate(rows, "config_table", "桌台配置表");
        addCandidate(rows, "flight_info", "航班信息表");
        addCandidate(rows, "passenger", "旅客信息表");
        addCandidate(rows, "food_config", "菜品配置表");
        addCandidate(rows, "food_order", "点餐订单表");
        addCandidate(rows, "ai_knowledge_base", "AI 知识库表");
        addCandidate(rows, "ai_chat_log", "AI 对话日志表");
        return rows;
    }

    private void addCandidate(List<Map<String, Object>> rows, String tableName, String comment) {
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("tableName", tableName);
        row.put("tableComment", comment);
        row.put("createTime", LocalDateTime.now());
        row.put("updateTime", LocalDateTime.now());
        rows.add(row);
    }

    private Map<String, Object> candidateByName(String tableName) {
        return candidateTables().stream()
            .filter(row -> Objects.equals(row.get("tableName"), tableName))
            .findFirst()
            .orElse(null);
    }

    private List<ColumnSeed> columnSeeds(String tableName) {
        return switch (tableName) {
            case "sys_user" -> List.of(
                pk("user_id", "用户ID"),
                varchar("user_name", "用户账号", "userName", "1", "1"),
                varchar("nick_name", "用户昵称", "nickName", "1", "1"),
                varchar("phonenumber", "手机号码", "phonenumber", "1", "1"),
                varchar("status", "状态", "status", "1", "1")
            );
            case "config_robot" -> List.of(
                pk("id", "主键"),
                varchar("robot_id", "机器人编号", "robotId", "1", "1"),
                varchar("robot_name", "机器人名称", "robotName", "1", "1"),
                varchar("robot_type", "机器人类型", "robotType", "1", "1"),
                varchar("enable", "启用状态", "enable", "1", "1"),
                varchar("room_code", "贵宾室", "roomCode", "1", "1")
            );
            case "config_region" -> List.of(
                pk("id", "主键"),
                varchar("region_name", "区域名称", "regionName", "1", "1"),
                text("coordinate", "区域坐标", "coordinate"),
                varchar("enable", "启用状态", "enable", "1", "1"),
                varchar("is_guide", "是否可引导", "isGuide", "1", "1")
            );
            case "config_table" -> List.of(
                pk("id", "主键"),
                varchar("table_no", "桌台编号", "tableNo", "1", "1"),
                number("region_id", "区域ID", "regionId"),
                varchar("status", "桌台状态", "status", "1", "1"),
                varchar("room_code", "贵宾室", "roomCode", "1", "1")
            );
            case "flight_info" -> List.of(
                varcharPk("flight_id", "航班ID"),
                varchar("flight_no", "航班号", "flightNo", "1", "1"),
                varchar("airline", "航空公司", "airline", "1", "1"),
                varchar("station_cn", "目的地", "stationCn", "1", "1"),
                datetime("sche_take_off_time", "计划起飞时间", "scheTakeOffTime")
            );
            case "passenger" -> List.of(
                pk("id", "主键"),
                varchar("user_name", "旅客姓名", "userName", "1", "1"),
                varchar("flight_no", "航班号", "flightNo", "1", "1"),
                varchar("card_no", "卡号", "cardNo", "1", "1"),
                number("region_id", "区域ID", "regionId")
            );
            case "food_config" -> List.of(
                pk("food_id", "菜品ID"),
                varchar("name", "菜品名称", "name", "1", "1"),
                decimal("price", "价格", "price"),
                number("calorie", "卡路里", "calorie"),
                varchar("dic_type_code", "菜品类型", "dicTypeCode", "1", "1")
            );
            case "food_order" -> List.of(
                pk("id", "主键"),
                varchar("order_code", "订单编号", "orderCode", "1", "1"),
                varchar("desk_no", "桌台号", "deskNo", "1", "1"),
                varchar("status", "订单状态", "status", "1", "1"),
                varchar("card_no", "卡号", "cardNo", "1", "1")
            );
            case "ai_knowledge_base" -> List.of(
                pk("id", "主键"),
                text("content", "知识内容", "content"),
                varchar("source", "来源", "source", "1", "1"),
                varchar("type", "类型", "type", "1", "1"),
                varchar("enable", "启用状态", "enable", "1", "1")
            );
            case "ai_chat_log" -> List.of(
                pk("id", "主键"),
                varchar("robot_id", "机器人编号", "robotId", "1", "1"),
                text("question", "问题", "question"),
                text("answer", "回答", "answer"),
                varchar("chat_type", "对话类型", "chatType", "1", "1")
            );
            default -> List.of(
                pk("id", "主键"),
                varchar("name", "名称", "name", "1", "1"),
                varchar("status", "状态", "status", "1", "1"),
                text("remark", "备注", "remark")
            );
        };
    }

    private ColumnSeed pk(String columnName, String comment) {
        return new ColumnSeed(columnName, comment, "bigint", "Long", camel(columnName), "1", "1", "1", "0", "0", "0", "0", "EQ", "input", "");
    }

    private ColumnSeed varcharPk(String columnName, String comment) {
        return new ColumnSeed(columnName, comment, "varchar(100)", "String", camel(columnName), "1", "0", "1", "0", "0", "1", "1", "EQ", "input", "");
    }

    private ColumnSeed varchar(String columnName, String comment, String javaField, String isList, String isQuery) {
        return new ColumnSeed(columnName, comment, "varchar(255)", "String", javaField, "0", "0", "0", "1", "1", isList, isQuery, isQuery.equals("1") ? "LIKE" : "EQ", "input", "");
    }

    private ColumnSeed text(String columnName, String comment, String javaField) {
        return new ColumnSeed(columnName, comment, "text", "String", javaField, "0", "0", "0", "1", "1", "1", "0", "EQ", "textarea", "");
    }

    private ColumnSeed number(String columnName, String comment, String javaField) {
        return new ColumnSeed(columnName, comment, "bigint", "Long", javaField, "0", "0", "0", "1", "1", "1", "0", "EQ", "input", "");
    }

    private ColumnSeed decimal(String columnName, String comment, String javaField) {
        return new ColumnSeed(columnName, comment, "decimal(10,2)", "BigDecimal", javaField, "0", "0", "0", "1", "1", "1", "0", "EQ", "input", "");
    }

    private ColumnSeed datetime(String columnName, String comment, String javaField) {
        return new ColumnSeed(columnName, comment, "datetime", "LocalDateTime", javaField, "0", "0", "0", "1", "1", "1", "1", "BETWEEN", "datetime", "");
    }

    private boolean existsTable(String tableName) {
        return dsl.fetchExists(dsl.selectOne().from(GEN_TABLE).where(GEN_TABLE.TABLE_NAME.eq(tableName)));
    }

    private TableDataInfo table(List<?> rows, long total) {
        TableDataInfo table = new TableDataInfo();
        table.setCode(200);
        table.setMsg("查询成功");
        table.setRows(rows);
        table.setTotal(total);
        return table;
    }

    private void writeZipText(HttpServletResponse response, String tableName, String content) throws IOException {
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        response.reset();
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + tableName + "-code.txt\"");
        response.setContentType("text/plain; charset=UTF-8");
        response.setContentLength(bytes.length);
        response.getOutputStream().write(bytes);
    }

    private Condition like(org.jooq.Field<String> field, String value) {
        String text = trim(value);
        return text == null ? DSL.noCondition() : DSL.lower(field).like("%" + text.toLowerCase(Locale.ROOT) + "%");
    }

    private List<String> split(String value) {
        if (value == null || value.isBlank()) {
            return List.of();
        }
        return Arrays.stream(value.split(","))
            .map(String::trim)
            .filter(item -> !item.isBlank())
            .distinct()
            .toList();
    }

    private boolean contains(Object value, String keyword) {
        return keyword == null || String.valueOf(value).toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT));
    }

    private String moduleName(String tableName) {
        int index = tableName.indexOf('_');
        return index > 0 ? tableName.substring(0, index) : "system";
    }

    private String businessName(String tableName) {
        String module = moduleName(tableName);
        String prefix = module + "_";
        return tableName.startsWith(prefix) ? camel(tableName.substring(prefix.length())) : camel(tableName);
    }

    private String upperCamel(String value) {
        String camel = camel(value);
        return camel.isEmpty() ? "Demo" : camel.substring(0, 1).toUpperCase(Locale.ROOT) + camel.substring(1);
    }

    private String camel(String value) {
        if (value == null || value.isBlank()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        boolean upperNext = false;
        for (char ch : value.toCharArray()) {
            if (ch == '_' || ch == '-') {
                upperNext = true;
                continue;
            }
            builder.append(upperNext ? Character.toUpperCase(ch) : Character.toLowerCase(ch));
            upperNext = false;
        }
        return builder.toString();
    }

    private Object value(Map<String, Object> payload, String... keys) {
        for (String key : keys) {
            if (payload.containsKey(key) && payload.get(key) != null) {
                return payload.get(key);
            }
        }
        return null;
    }

    private String text(Map<String, Object> payload, String key, String fallback) {
        return text(payload, key, key, fallback);
    }

    private String text(Map<String, Object> payload, String key, String altKey, String fallback) {
        Object value = value(payload, key, altKey);
        return value == null || String.valueOf(value).isBlank() ? fallback : String.valueOf(value);
    }

    private String asString(Object value) {
        return value == null ? null : String.valueOf(value);
    }

    private String defaultText(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value;
    }

    private String trim(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

    private Long parseLong(Object value) {
        try {
            return value == null || String.valueOf(value).isBlank() ? null : Long.valueOf(String.valueOf(value));
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private int parseInt(String value, int fallback) {
        try {
            return value == null || value.isBlank() ? fallback : Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return fallback;
        }
    }

    private record ColumnSeed(
        String columnName,
        String columnComment,
        String columnType,
        String javaType,
        String javaField,
        String isPk,
        String isIncrement,
        String isRequired,
        String isInsert,
        String isEdit,
        String isList,
        String isQuery,
        String queryType,
        String htmlType,
        String dictType
    ) {
    }
}
