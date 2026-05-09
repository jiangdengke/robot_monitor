/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.web.controller.tool;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class Base64ImageDecoder {
    public static void main(String[] args) {
        String inputFilePath = "C:\\work\\idcard.txt";
        String outputDir = "C:\\work\\";
        try {
            String base64Data = Base64ImageDecoder.readFileToString(inputFilePath);
            base64Data = base64Data.replaceAll("\\s+", "");
            byte[] imageBytes = Base64.getDecoder().decode(base64Data);
            String imageExtension = Base64ImageDecoder.detectImageType(imageBytes);
            String outputFilePath = outputDir + "decoded_image" + imageExtension;
            Base64ImageDecoder.writeBytesToFile(outputFilePath, imageBytes);
            System.out.println("\u56fe\u7247\u89e3\u7801\u6210\u529f\uff01");
            System.out.println("\u68c0\u6d4b\u5230\u7684\u56fe\u7247\u683c\u5f0f: " + imageExtension);
            System.out.println("\u4fdd\u5b58\u8def\u5f84: " + outputFilePath);
            System.out.println("\u56fe\u7247\u5927\u5c0f: " + imageBytes.length + " \u5b57\u8282");
        }
        catch (IOException e) {
            System.err.println("\u6587\u4ef6\u8bfb\u5199\u9519\u8bef: " + e.getMessage());
            e.printStackTrace();
        }
        catch (IllegalArgumentException e) {
            System.err.println("Base64\u89e3\u7801\u9519\u8bef: " + e.getMessage());
            e.printStackTrace();
        }
        catch (Exception e) {
            System.err.println("\u53d1\u751f\u672a\u77e5\u9519\u8bef: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String detectImageType(byte[] imageBytes) {
        if (imageBytes.length < 4) {
            return ".dat";
        }
        if (imageBytes[0] == -1 && imageBytes[1] == -40) {
            return ".jpg";
        }
        if (imageBytes[0] == -119 && imageBytes[1] == 80 && imageBytes[2] == 78 && imageBytes[3] == 71) {
            return ".png";
        }
        if (imageBytes[0] == 71 && imageBytes[1] == 73 && imageBytes[2] == 70) {
            return ".gif";
        }
        if (imageBytes[0] == 66 && imageBytes[1] == 77) {
            return ".bmp";
        }
        return ".dat";
    }

    private static String readFileToString(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath, new String[0])));
    }

    private static void writeBytesToFile(String filePath, byte[] data) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath);){
            fos.write(data);
        }
    }
}
