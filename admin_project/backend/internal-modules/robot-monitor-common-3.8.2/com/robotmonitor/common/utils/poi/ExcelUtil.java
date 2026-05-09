/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.servlet.http.HttpServletResponse
 *  org.apache.commons.lang3.RegExUtils
 *  org.apache.poi.hssf.usermodel.HSSFClientAnchor
 *  org.apache.poi.hssf.usermodel.HSSFPicture
 *  org.apache.poi.hssf.usermodel.HSSFPictureData
 *  org.apache.poi.hssf.usermodel.HSSFShape
 *  org.apache.poi.hssf.usermodel.HSSFSheet
 *  org.apache.poi.hssf.usermodel.HSSFWorkbook
 *  org.apache.poi.ooxml.POIXMLDocumentPart
 *  org.apache.poi.ss.usermodel.BorderStyle
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.usermodel.ClientAnchor
 *  org.apache.poi.ss.usermodel.DataValidation
 *  org.apache.poi.ss.usermodel.DataValidationConstraint
 *  org.apache.poi.ss.usermodel.DataValidationHelper
 *  org.apache.poi.ss.usermodel.DateUtil
 *  org.apache.poi.ss.usermodel.Drawing
 *  org.apache.poi.ss.usermodel.FillPatternType
 *  org.apache.poi.ss.usermodel.Font
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.IndexedColors
 *  org.apache.poi.ss.usermodel.PictureData
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.VerticalAlignment
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.ss.usermodel.WorkbookFactory
 *  org.apache.poi.ss.util.CellRangeAddress
 *  org.apache.poi.ss.util.CellRangeAddressList
 *  org.apache.poi.util.IOUtils
 *  org.apache.poi.xssf.streaming.SXSSFWorkbook
 *  org.apache.poi.xssf.usermodel.XSSFClientAnchor
 *  org.apache.poi.xssf.usermodel.XSSFDataValidation
 *  org.apache.poi.xssf.usermodel.XSSFDrawing
 *  org.apache.poi.xssf.usermodel.XSSFPicture
 *  org.apache.poi.xssf.usermodel.XSSFShape
 *  org.apache.poi.xssf.usermodel.XSSFSheet
 *  org.apache.poi.xssf.usermodel.XSSFWorkbook
 *  org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.robotmonitor.common.utils.poi;

import com.robotmonitor.common.annotation.Excel;
import com.robotmonitor.common.annotation.Excels;
import com.robotmonitor.common.config.RobotmonitorConfig;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.text.Convert;
import com.robotmonitor.common.exception.UtilException;
import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.common.utils.DictUtils;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.common.utils.file.FileTypeUtils;
import com.robotmonitor.common.utils.file.FileUtils;
import com.robotmonitor.common.utils.file.ImageUtils;
import com.robotmonitor.common.utils.poi.ExcelHandlerAdapter;
import com.robotmonitor.common.utils.reflect.ReflectUtils;
import jakarta.servlet.http.HttpServletResponse;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.commons.lang3.RegExUtils;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelUtil<T> {
    private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);
    public static final String FORMULA_REGEX_STR = "=|-|\\+|@";
    public static final String[] FORMULA_STR = new String[]{"=", "-", "+", "@"};
    public static final int sheetSize = 65536;
    private String sheetName;
    private Excel.Type type;
    private Workbook wb;
    private Sheet sheet;
    private Map<String, CellStyle> styles;
    private List<T> list;
    private List<Object[]> fields;
    private int rownum;
    private String title;
    private short maxHeight;
    private Map<Integer, Double> statistics = new HashMap<Integer, Double>();
    public Class<T> clazz;

    public ExcelUtil(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void init(List<T> list, String sheetName, String title, Excel.Type type) {
        if (list == null) {
            list = new ArrayList<T>();
        }
        this.list = list;
        this.sheetName = sheetName;
        this.type = type;
        this.title = title;
        this.createExcelField();
        this.createWorkbook();
        this.createTitle();
    }

    public void createTitle() {
        if (StringUtils.isNotEmpty(this.title)) {
            int n;
            if (this.rownum == 0) {
                int n2 = this.rownum;
                n = n2;
                this.rownum = n2 + 1;
            } else {
                n = 0;
            }
            Row titleRow = this.sheet.createRow(n);
            titleRow.setHeightInPoints(30.0f);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellStyle(this.styles.get("title"));
            titleCell.setCellValue(this.title);
            this.sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), titleRow.getRowNum(), this.fields.size() - 1));
        }
    }

    public List<T> importExcel(InputStream is) throws Exception {
        return this.importExcel(is, 0);
    }

    public List<T> importExcel(InputStream is, int titleNum) throws Exception {
        return this.importExcel("", is, titleNum);
    }

    public List<T> importExcel(String sheetName, InputStream is, int titleNum) throws Exception {
        Sheet sheet;
        this.type = Excel.Type.IMPORT;
        this.wb = WorkbookFactory.create((InputStream)is);
        ArrayList<Object> list = new ArrayList<Object>();
        Sheet sheet2 = sheet = StringUtils.isNotEmpty(sheetName) ? this.wb.getSheet(sheetName) : this.wb.getSheetAt(0);
        if (sheet == null) {
            throw new IOException("\u6587\u4ef6sheet\u4e0d\u5b58\u5728");
        }
        boolean isXSSFWorkbook = !(this.wb instanceof HSSFWorkbook);
        Map<String, PictureData> pictures = isXSSFWorkbook ? ExcelUtil.getSheetPictures07((XSSFSheet)sheet, (XSSFWorkbook)this.wb) : ExcelUtil.getSheetPictures03((HSSFSheet)sheet, (HSSFWorkbook)this.wb);
        int rows = sheet.getLastRowNum();
        if (rows > 0) {
            HashMap<Object, Integer> cellMap = new HashMap<Object, Integer>();
            Row heard = sheet.getRow(titleNum);
            for (int i = 0; i < heard.getPhysicalNumberOfCells(); ++i) {
                Cell cell = heard.getCell(i);
                if (StringUtils.isNotNull(cell)) {
                    String value = this.getCellValue(heard, i).toString();
                    cellMap.put(value, i);
                    continue;
                }
                cellMap.put(null, i);
            }
            List<Object[]> fields = this.getFields();
            HashMap<Integer, Object[]> fieldsMap = new HashMap<Integer, Object[]>();
            for (Object[] objects : fields) {
                Excel attr = (Excel)objects[1];
                Integer column = (Integer)cellMap.get(attr.name());
                if (column == null) continue;
                fieldsMap.put(column, objects);
            }
            for (int i = titleNum + 1; i <= rows; ++i) {
                Row row = sheet.getRow(i);
                if (this.isRowEmpty(row)) continue;
                Object entity = null;
                for (Map.Entry entry : fieldsMap.entrySet()) {
                    Object val = this.getCellValue(row, (Integer)entry.getKey());
                    entity = entity == null ? (Object)this.clazz.newInstance() : entity;
                    Field field = (Field)((Object[])entry.getValue())[0];
                    Excel attr = (Excel)((Object[])entry.getValue())[1];
                    Class<?> fieldType = field.getType();
                    if (String.class == fieldType) {
                        String dateFormat;
                        String s = Convert.toStr(val);
                        val = StringUtils.endsWith((CharSequence)s, (CharSequence)".0") ? StringUtils.substringBefore((String)s, (String)".0") : (StringUtils.isNotEmpty(dateFormat = field.getAnnotation(Excel.class).dateFormat()) ? this.parseDateToStr(dateFormat, val) : Convert.toStr(val));
                    } else if ((Integer.TYPE == fieldType || Integer.class == fieldType) && StringUtils.isNumeric((CharSequence)Convert.toStr(val))) {
                        val = Convert.toInt(val);
                    } else if ((Long.TYPE == fieldType || Long.class == fieldType) && StringUtils.isNumeric((CharSequence)Convert.toStr(val))) {
                        val = Convert.toLong(val);
                    } else if (Double.TYPE == fieldType || Double.class == fieldType) {
                        val = Convert.toDouble(val);
                    } else if (Float.TYPE == fieldType || Float.class == fieldType) {
                        val = Convert.toFloat(val);
                    } else if (BigDecimal.class == fieldType) {
                        val = Convert.toBigDecimal(val);
                    } else if (Date.class == fieldType) {
                        if (val instanceof String) {
                            val = DateUtils.parseDate(val);
                        } else if (val instanceof Double) {
                            val = DateUtil.getJavaDate((double)((Double)val));
                        }
                    } else if (Boolean.TYPE == fieldType || Boolean.class == fieldType) {
                        val = Convert.toBool(val, false);
                    }
                    if (!StringUtils.isNotNull(fieldType)) continue;
                    Object propertyName = field.getName();
                    if (StringUtils.isNotEmpty(attr.targetAttr())) {
                        propertyName = field.getName() + "." + attr.targetAttr();
                    } else if (StringUtils.isNotEmpty(attr.readConverterExp())) {
                        val = ExcelUtil.reverseByExp(Convert.toStr(val), attr.readConverterExp(), attr.separator());
                    } else if (StringUtils.isNotEmpty(attr.dictType())) {
                        val = ExcelUtil.reverseDictByExp(Convert.toStr(val), attr.dictType(), attr.separator());
                    } else if (!attr.handler().equals(ExcelHandlerAdapter.class)) {
                        val = this.dataFormatHandlerAdapter(val, attr);
                    } else if (Excel.ColumnType.IMAGE == attr.cellType() && StringUtils.isNotEmpty(pictures)) {
                        PictureData image = pictures.get(row.getRowNum() + "_" + entry.getKey());
                        if (image == null) {
                            val = "";
                        } else {
                            byte[] data = image.getData();
                            val = FileUtils.writeImportBytes(data);
                        }
                    }
                    ReflectUtils.invokeSetter(entity, (String)propertyName, val);
                }
                list.add(entity);
            }
        }
        return list;
    }

    public AjaxResult exportExcel(List<T> list, String sheetName) {
        return this.exportExcel(list, sheetName, "");
    }

    public AjaxResult exportExcel(List<T> list, String sheetName, String title) {
        this.init(list, sheetName, title, Excel.Type.EXPORT);
        return this.exportExcel();
    }

    public void exportExcel(HttpServletResponse response, List<T> list, String sheetName) {
        this.exportExcel(response, list, sheetName, "");
    }

    public void exportExcel(HttpServletResponse response, List<T> list, String sheetName, String title) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        this.init(list, sheetName, title, Excel.Type.EXPORT);
        this.exportExcel(response);
    }

    public AjaxResult importTemplateExcel(String sheetName) {
        return this.importTemplateExcel(sheetName, "");
    }

    public AjaxResult importTemplateExcel(String sheetName, String title) {
        this.init(null, sheetName, title, Excel.Type.IMPORT);
        return this.exportExcel();
    }

    public void importTemplateExcel(HttpServletResponse response, String sheetName) {
        this.importTemplateExcel(response, sheetName, "");
    }

    public void importTemplateExcel(HttpServletResponse response, String sheetName, String title) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        this.init(null, sheetName, title, Excel.Type.IMPORT);
        this.exportExcel(response);
    }

    public void exportExcel(HttpServletResponse response) {
        try {
            this.writeSheet();
            this.wb.write((OutputStream)response.getOutputStream());
        }
        catch (Exception e) {
            log.error("\u5bfc\u51faExcel\u5f02\u5e38{}", (Object)e.getMessage());
        }
        finally {
            IOUtils.closeQuietly((Closeable)this.wb);
        }
    }

    public AjaxResult exportExcel() {
        AjaxResult ajaxResult;
        FileOutputStream out = null;
        try {
            this.writeSheet();
            String filename = this.encodingFilename(this.sheetName);
            out = new FileOutputStream(this.getAbsoluteFile(filename));
            this.wb.write((OutputStream)out);
            ajaxResult = AjaxResult.success(filename);
        }
        catch (Exception e) {
            try {
                log.error("\u5bfc\u51faExcel\u5f02\u5e38{}", (Object)e.getMessage());
                throw new UtilException("\u5bfc\u51faExcel\u5931\u8d25\uff0c\u8bf7\u8054\u7cfb\u7f51\u7ad9\u7ba1\u7406\u5458\uff01");
            }
            catch (Throwable throwable) {
                IOUtils.closeQuietly((Closeable)this.wb);
                IOUtils.closeQuietly(out);
                throw throwable;
            }
        }
        IOUtils.closeQuietly((Closeable)this.wb);
        IOUtils.closeQuietly((Closeable)out);
        return ajaxResult;
    }

    public void writeSheet() {
        int sheetNo = Math.max(1, (int)Math.ceil((double)this.list.size() * 1.0 / 65536.0));
        for (int index = 0; index < sheetNo; ++index) {
            this.createSheet(sheetNo, index);
            Row row = this.sheet.createRow(this.rownum);
            int column = 0;
            for (Object[] os : this.fields) {
                Excel excel = (Excel)os[1];
                this.createCell(excel, row, column++);
            }
            if (!Excel.Type.EXPORT.equals((Object)this.type)) continue;
            this.fillExcelData(index, row);
            this.addStatisticsRow();
        }
    }

    public void fillExcelData(int index, Row row) {
        int startNo = index * 65536;
        int endNo = Math.min(startNo + 65536, this.list.size());
        for (int i = startNo; i < endNo; ++i) {
            row = this.sheet.createRow(i + 1 + this.rownum - startNo);
            T vo = this.list.get(i);
            int column = 0;
            for (Object[] os : this.fields) {
                Field field = (Field)os[0];
                Excel excel = (Excel)os[1];
                this.addCell(excel, row, vo, field, column++);
            }
        }
    }

    private Map<String, CellStyle> createStyles(Workbook wb) {
        HashMap<String, CellStyle> styles = new HashMap<String, CellStyle>();
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        Font titleFont = wb.createFont();
        titleFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short)16);
        titleFont.setBold(true);
        style.setFont(titleFont);
        styles.put("title", style);
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        Font dataFont = wb.createFont();
        dataFont.setFontName("Arial");
        dataFont.setFontHeightInPoints((short)10);
        style.setFont(dataFont);
        styles.put("data", style);
        style = wb.createCellStyle();
        style.cloneStyleFrom((CellStyle)styles.get("data"));
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = wb.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short)10);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(headerFont);
        styles.put("header", style);
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        Font totalFont = wb.createFont();
        totalFont.setFontName("Arial");
        totalFont.setFontHeightInPoints((short)10);
        style.setFont(totalFont);
        styles.put("total", style);
        styles.putAll(this.annotationStyles(wb));
        return styles;
    }

    private Map<String, CellStyle> annotationStyles(Workbook wb) {
        HashMap<String, CellStyle> styles = new HashMap<String, CellStyle>();
        for (Object[] os : this.fields) {
            Excel excel = (Excel)os[1];
            String key = "data_" + excel.align() + "_" + excel.color();
            if (styles.containsKey(key)) continue;
            CellStyle style = wb.createCellStyle();
            style = wb.createCellStyle();
            style.setAlignment(excel.align());
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setBorderRight(BorderStyle.THIN);
            style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
            style.setBorderLeft(BorderStyle.THIN);
            style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
            style.setBorderTop(BorderStyle.THIN);
            style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
            style.setBorderBottom(BorderStyle.THIN);
            style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
            Font dataFont = wb.createFont();
            dataFont.setFontName("Arial");
            dataFont.setFontHeightInPoints((short)10);
            dataFont.setColor(excel.color().index);
            style.setFont(dataFont);
            styles.put(key, style);
        }
        return styles;
    }

    public Cell createCell(Excel attr, Row row, int column) {
        Cell cell = row.createCell(column);
        cell.setCellValue(attr.name());
        this.setDataValidation(attr, row, column);
        cell.setCellStyle(this.styles.get("header"));
        return cell;
    }

    public void setCellVo(Object value, Excel attr, Cell cell) {
        if (Excel.ColumnType.STRING == attr.cellType()) {
            String cellValue = Convert.toStr(value);
            if (StringUtils.startsWithAny((CharSequence)cellValue, (CharSequence[])FORMULA_STR)) {
                cellValue = RegExUtils.replaceFirst((String)cellValue, (String)FORMULA_REGEX_STR, (String)"\t$0");
            }
            cell.setCellValue((String)(StringUtils.isNull(cellValue) ? attr.defaultValue() : cellValue + attr.suffix()));
        } else if (Excel.ColumnType.NUMERIC == attr.cellType()) {
            if (StringUtils.isNotNull(value)) {
                cell.setCellValue(StringUtils.contains((CharSequence)Convert.toStr(value), (CharSequence)".") ? Convert.toDouble(value) : (double)Convert.toInt(value).intValue());
            }
        } else if (Excel.ColumnType.IMAGE == attr.cellType()) {
            XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, (int)((short)cell.getColumnIndex()), cell.getRow().getRowNum(), (int)((short)(cell.getColumnIndex() + 1)), cell.getRow().getRowNum() + 1);
            String imagePath = Convert.toStr(value);
            if (StringUtils.isNotEmpty(imagePath)) {
                byte[] data = ImageUtils.getImage(imagePath);
                ExcelUtil.getDrawingPatriarch(cell.getSheet()).createPicture((ClientAnchor)anchor, cell.getSheet().getWorkbook().addPicture(data, this.getImageType(data)));
            }
        }
    }

    public static Drawing<?> getDrawingPatriarch(Sheet sheet) {
        if (sheet.getDrawingPatriarch() == null) {
            sheet.createDrawingPatriarch();
        }
        return sheet.getDrawingPatriarch();
    }

    public int getImageType(byte[] value) {
        String type = FileTypeUtils.getFileExtendName(value);
        if ("JPG".equalsIgnoreCase(type)) {
            return 5;
        }
        if ("PNG".equalsIgnoreCase(type)) {
            return 6;
        }
        return 5;
    }

    public void setDataValidation(Excel attr, Row row, int column) {
        if (attr.name().indexOf("\u6ce8\uff1a") >= 0) {
            this.sheet.setColumnWidth(column, 6000);
        } else {
            this.sheet.setColumnWidth(column, (int)((attr.width() + 0.72) * 256.0));
        }
        if (StringUtils.isNotEmpty(attr.prompt()) || attr.combo().length > 0) {
            this.setPromptOrValidation(this.sheet, attr.combo(), attr.prompt(), 1, 100, column, column);
        }
    }

    public Cell addCell(Excel attr, Row row, T vo, Field field, int column) {
        Cell cell = null;
        try {
            row.setHeight(this.maxHeight);
            if (attr.isExport()) {
                cell = row.createCell(column);
                cell.setCellStyle(this.styles.get("data_" + attr.align() + "_" + attr.color()));
                Object value = this.getTargetValue(vo, field, attr);
                String dateFormat = attr.dateFormat();
                String readConverterExp = attr.readConverterExp();
                String separator = attr.separator();
                String dictType = attr.dictType();
                if (StringUtils.isNotEmpty(dateFormat) && StringUtils.isNotNull(value)) {
                    cell.setCellValue(this.parseDateToStr(dateFormat, value));
                } else if (StringUtils.isNotEmpty(readConverterExp) && StringUtils.isNotNull(value)) {
                    cell.setCellValue(ExcelUtil.convertByExp(Convert.toStr(value), readConverterExp, separator));
                } else if (StringUtils.isNotEmpty(dictType) && StringUtils.isNotNull(value)) {
                    cell.setCellValue(ExcelUtil.convertDictByExp(Convert.toStr(value), dictType, separator));
                } else if (value instanceof BigDecimal && -1 != attr.scale()) {
                    cell.setCellValue(((BigDecimal)value).setScale(attr.scale(), attr.roundingMode()).toString());
                } else if (!attr.handler().equals(ExcelHandlerAdapter.class)) {
                    cell.setCellValue(this.dataFormatHandlerAdapter(value, attr));
                } else {
                    this.setCellVo(value, attr, cell);
                }
                this.addStatisticsData(column, Convert.toStr(value), attr);
            }
        }
        catch (Exception e) {
            log.error("\u5bfc\u51faExcel\u5931\u8d25{}", (Throwable)e);
        }
        return cell;
    }

    public void setPromptOrValidation(Sheet sheet, String[] textlist, String promptContent, int firstRow, int endRow, int firstCol, int endCol) {
        DataValidationHelper helper = sheet.getDataValidationHelper();
        DataValidationConstraint constraint = textlist.length > 0 ? helper.createExplicitListConstraint(textlist) : helper.createCustomConstraint("DD1");
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        DataValidation dataValidation = helper.createValidation(constraint, regions);
        if (StringUtils.isNotEmpty(promptContent)) {
            dataValidation.createPromptBox("", promptContent);
            dataValidation.setShowPromptBox(true);
        }
        if (dataValidation instanceof XSSFDataValidation) {
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        } else {
            dataValidation.setSuppressDropDownArrow(false);
        }
        sheet.addValidationData(dataValidation);
    }

    public static String convertByExp(String propertyValue, String converterExp, String separator) {
        String[] convertSource;
        StringBuilder propertyString = new StringBuilder();
        block0: for (String item : convertSource = converterExp.split(",")) {
            String[] itemArray = item.split("=");
            if (StringUtils.containsAny((CharSequence)separator, (CharSequence)propertyValue)) {
                for (String value : propertyValue.split(separator)) {
                    if (!itemArray[0].equals(value)) continue;
                    propertyString.append(itemArray[1] + separator);
                    continue block0;
                }
                continue;
            }
            if (!itemArray[0].equals(propertyValue)) continue;
            return itemArray[1];
        }
        return StringUtils.stripEnd((String)propertyString.toString(), (String)separator);
    }

    public static String reverseByExp(String propertyValue, String converterExp, String separator) {
        String[] convertSource;
        StringBuilder propertyString = new StringBuilder();
        block0: for (String item : convertSource = converterExp.split(",")) {
            String[] itemArray = item.split("=");
            if (StringUtils.containsAny((CharSequence)separator, (CharSequence)propertyValue)) {
                for (String value : propertyValue.split(separator)) {
                    if (!itemArray[1].equals(value)) continue;
                    propertyString.append(itemArray[0] + separator);
                    continue block0;
                }
                continue;
            }
            if (!itemArray[1].equals(propertyValue)) continue;
            return itemArray[0];
        }
        return StringUtils.stripEnd((String)propertyString.toString(), (String)separator);
    }

    public static String convertDictByExp(String dictValue, String dictType, String separator) {
        return DictUtils.getDictLabel(dictType, dictValue, separator);
    }

    public static String reverseDictByExp(String dictLabel, String dictType, String separator) {
        return DictUtils.getDictValue(dictType, dictLabel, separator);
    }

    public String dataFormatHandlerAdapter(Object value, Excel excel) {
        try {
            Object instance = excel.handler().newInstance();
            Method formatMethod = excel.handler().getMethod("format", Object.class, String[].class);
            value = formatMethod.invoke(instance, value, excel.args());
        }
        catch (Exception e) {
            log.error("\u4e0d\u80fd\u683c\u5f0f\u5316\u6570\u636e " + excel.handler(), (Object)e.getMessage());
        }
        return Convert.toStr(value);
    }

    private void addStatisticsData(Integer index, String text, Excel entity) {
        if (entity != null && entity.isStatistics()) {
            Double temp = 0.0;
            if (!this.statistics.containsKey(index)) {
                this.statistics.put(index, temp);
            }
            try {
                temp = Double.valueOf(text);
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
            this.statistics.put(index, this.statistics.get(index) + temp);
        }
    }

    public void addStatisticsRow() {
        if (this.statistics.size() > 0) {
            Row row = this.sheet.createRow(this.sheet.getLastRowNum() + 1);
            Set<Integer> keys = this.statistics.keySet();
            Cell cell = row.createCell(0);
            cell.setCellStyle(this.styles.get("total"));
            cell.setCellValue("\u5408\u8ba1");
            for (Integer key : keys) {
                cell = row.createCell(key.intValue());
                cell.setCellStyle(this.styles.get("total"));
                DecimalFormat decimalFormat = new DecimalFormat("######0.00");
                cell.setCellValue(decimalFormat.format(this.statistics.get(key)));
            }
            this.statistics.clear();
        }
    }

    public String encodingFilename(String filename) {
        filename = UUID.randomUUID().toString() + "_" + (String)filename + ".xlsx";
        return filename;
    }

    public String getAbsoluteFile(String filename) {
        String downloadPath = RobotmonitorConfig.getDownloadPath() + filename;
        File desc = new File(downloadPath);
        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        return downloadPath;
    }

    private Object getTargetValue(T vo, Field field, Excel excel) throws Exception {
        Object o = field.get(vo);
        if (StringUtils.isNotEmpty(excel.targetAttr())) {
            String target = excel.targetAttr();
            if (target.contains(".")) {
                String[] targets;
                for (String name : targets = target.split("[.]")) {
                    o = this.getValue(o, name);
                }
            } else {
                o = this.getValue(o, target);
            }
        }
        return o;
    }

    private Object getValue(Object o, String name) throws Exception {
        if (StringUtils.isNotNull(o) && StringUtils.isNotEmpty(name)) {
            Class<?> clazz = o.getClass();
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            o = field.get(o);
        }
        return o;
    }

    private void createExcelField() {
        this.fields = this.getFields();
        this.fields = this.fields.stream().sorted(Comparator.comparing(objects -> ((Excel)objects[1]).sort())).collect(Collectors.toList());
        this.maxHeight = this.getRowHeight();
    }

    public List<Object[]> getFields() {
        ArrayList<Object[]> fields = new ArrayList<Object[]>();
        ArrayList<Field> tempFields = new ArrayList<Field>();
        tempFields.addAll(Arrays.asList(this.clazz.getSuperclass().getDeclaredFields()));
        tempFields.addAll(Arrays.asList(this.clazz.getDeclaredFields()));
        for (Field field : tempFields) {
            Excel[] excels;
            Excel attr;
            if (field.isAnnotationPresent(Excel.class) && (attr = field.getAnnotation(Excel.class)) != null && (attr.type() == Excel.Type.ALL || attr.type() == this.type)) {
                field.setAccessible(true);
                fields.add(new Object[]{field, attr});
            }
            if (!field.isAnnotationPresent(Excels.class)) continue;
            Excels attrs = field.getAnnotation(Excels.class);
            for (Excel attr2 : excels = attrs.value()) {
                if (attr2 == null || attr2.type() != Excel.Type.ALL && attr2.type() != this.type) continue;
                field.setAccessible(true);
                fields.add(new Object[]{field, attr2});
            }
        }
        return fields;
    }

    public short getRowHeight() {
        double maxHeight = 0.0;
        for (Object[] os : this.fields) {
            Excel excel = (Excel)os[1];
            maxHeight = Math.max(maxHeight, excel.height());
        }
        return (short)(maxHeight * 20.0);
    }

    public void createWorkbook() {
        this.wb = new SXSSFWorkbook(500);
        this.sheet = this.wb.createSheet();
        this.wb.setSheetName(0, this.sheetName);
        this.styles = this.createStyles(this.wb);
    }

    public void createSheet(int sheetNo, int index) {
        if (sheetNo > 1 && index > 0) {
            this.sheet = this.wb.createSheet();
            this.createTitle();
            this.wb.setSheetName(index, this.sheetName + index);
        }
    }

    public Object getCellValue(Row row, int column) {
        if (row == null) {
            return row;
        }
        Object val = "";
        try {
            Cell cell = row.getCell(column);
            if (StringUtils.isNotNull(cell)) {
                if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {
                    val = cell.getNumericCellValue();
                    val = DateUtil.isCellDateFormatted((Cell)cell) ? DateUtil.getJavaDate((double)((Double)val)) : ((Double)val % 1.0 != 0.0 ? new BigDecimal(val.toString()) : new DecimalFormat("0").format(val));
                } else if (cell.getCellType() == CellType.STRING) {
                    val = cell.getStringCellValue();
                } else if (cell.getCellType() == CellType.BOOLEAN) {
                    val = cell.getBooleanCellValue();
                } else if (cell.getCellType() == CellType.ERROR) {
                    val = cell.getErrorCellValue();
                }
            }
        }
        catch (Exception e) {
            return val;
        }
        return val;
    }

    private boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); ++i) {
            Cell cell = row.getCell(i);
            if (cell == null || cell.getCellType() == CellType.BLANK) continue;
            return false;
        }
        return true;
    }

    public static Map<String, PictureData> getSheetPictures03(HSSFSheet sheet, HSSFWorkbook workbook) {
        HashMap<String, PictureData> sheetIndexPicMap = new HashMap<String, PictureData>();
        List pictures = workbook.getAllPictures();
        if (!pictures.isEmpty()) {
            for (HSSFShape shape : sheet.getDrawingPatriarch().getChildren()) {
                HSSFClientAnchor anchor = (HSSFClientAnchor)shape.getAnchor();
                if (!(shape instanceof HSSFPicture)) continue;
                HSSFPicture pic = (HSSFPicture)shape;
                int pictureIndex = pic.getPictureIndex() - 1;
                HSSFPictureData picData = (HSSFPictureData)pictures.get(pictureIndex);
                String picIndex = String.valueOf(anchor.getRow1()) + "_" + String.valueOf(anchor.getCol1());
                sheetIndexPicMap.put(picIndex, (PictureData)picData);
            }
            return sheetIndexPicMap;
        }
        return sheetIndexPicMap;
    }

    public static Map<String, PictureData> getSheetPictures07(XSSFSheet sheet, XSSFWorkbook workbook) {
        HashMap<String, PictureData> sheetIndexPicMap = new HashMap<String, PictureData>();
        for (POIXMLDocumentPart dr : sheet.getRelations()) {
            if (!(dr instanceof XSSFDrawing)) continue;
            XSSFDrawing drawing = (XSSFDrawing)dr;
            List shapes = drawing.getShapes();
            for (XSSFShape shape : shapes) {
                if (!(shape instanceof XSSFPicture)) continue;
                XSSFPicture pic = (XSSFPicture)shape;
                XSSFClientAnchor anchor = pic.getPreferredSize();
                CTMarker ctMarker = anchor.getFrom();
                String picIndex = ctMarker.getRow() + "_" + ctMarker.getCol();
                sheetIndexPicMap.put(picIndex, (PictureData)pic.getPictureData());
            }
        }
        return sheetIndexPicMap;
    }

    public String parseDateToStr(String dateFormat, Object val) {
        if (val == null) {
            return "";
        }
        String str = val instanceof Date ? DateUtils.parseDateToStr(dateFormat, (Date)val) : (val instanceof LocalDateTime ? DateUtils.parseDateToStr(dateFormat, DateUtils.toDate((LocalDateTime)val)) : (val instanceof LocalDate ? DateUtils.parseDateToStr(dateFormat, DateUtils.toDate((LocalDate)val)) : val.toString()));
        return str;
    }
}
