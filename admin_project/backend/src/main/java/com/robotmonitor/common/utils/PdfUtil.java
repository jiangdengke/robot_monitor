/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.itextpdf.text.BaseColor
 *  com.itextpdf.text.Document
 *  com.itextpdf.text.Font
 *  com.itextpdf.text.PageSize
 *  com.itextpdf.text.Paragraph
 *  com.itextpdf.text.Phrase
 *  com.itextpdf.text.Rectangle
 *  com.itextpdf.text.pdf.BaseFont
 *  com.itextpdf.text.pdf.PdfPCell
 *  com.itextpdf.text.pdf.PdfPTable
 */
package com.robotmonitor.common.utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class PdfUtil {
    public static Font NORMALFONT;
    public static Font BOLDFONT;
    public static float fixedHeight;
    public static int spacing;

    public static Document createDocument() {
        Document document = new Document();
        Rectangle rectangle = new Rectangle(PageSize.A4);
        rectangle.setBackgroundColor(BaseColor.WHITE);
        document.setPageSize(rectangle);
        document.setMargins(20.0f, 20.0f, 20.0f, 20.0f);
        return document;
    }

    public static Paragraph createParagraph(String text, Font font) {
        Paragraph elements = new Paragraph(text, font);
        elements.setSpacingBefore(5.0f);
        elements.setSpacingAfter(5.0f);
        elements.setSpacingAfter((float)spacing);
        return elements;
    }

    public static Font createFont(int fontNumber, int fontSize, BaseColor fontColor) {
        BaseFont bf = null;
        try {
            bf = BaseFont.createFont((String)"STSong-Light", (String)"UniGB-UCS2-H", (boolean)false);
            return new Font(bf, (float)fontNumber, fontSize, fontColor);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Font(bf, 12.0f, 0, BaseColor.BLACK);
        }
    }

    public static void disableBorderSide(PdfPCell cell) {
        if (cell != null) {
            cell.disableBorderSide(1);
            cell.disableBorderSide(2);
            cell.disableBorderSide(4);
            cell.disableBorderSide(8);
        }
    }

    public static PdfPCell createCenterPdfPCell() {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(5);
        cell.setHorizontalAlignment(1);
        cell.setFixedHeight(fixedHeight);
        return cell;
    }

    public static PdfPCell createCenterPdfPCell(String text, int rowSpan, int colSpan, Font font) {
        PdfPCell cell = new PdfPCell((Phrase)new Paragraph(text, font));
        cell.setVerticalAlignment(5);
        cell.setHorizontalAlignment(1);
        cell.setFixedHeight(fixedHeight);
        cell.setRowspan(rowSpan);
        cell.setColspan(colSpan);
        return cell;
    }

    public static PdfPTable createPdfPTable(int len) {
        PdfPTable pdfPTable = new PdfPTable(len);
        pdfPTable.setSpacingBefore(5.0f);
        pdfPTable.setHorizontalAlignment(1);
        return pdfPTable;
    }

    static {
        fixedHeight = 27.0f;
        spacing = 5;
        try {
            BaseFont bfChinese = BaseFont.createFont((String)"STSong-Light", (String)"UniGB-UCS2-H", (boolean)false);
            NORMALFONT = new Font(bfChinese, 10.0f, 0);
            BOLDFONT = new Font(bfChinese, 14.0f, 1);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
