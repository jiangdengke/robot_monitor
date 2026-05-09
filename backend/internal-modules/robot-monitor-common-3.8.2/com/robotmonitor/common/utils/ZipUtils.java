/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.util.Base64Utils
 *  org.xerial.snappy.Snappy
 */
package com.robotmonitor.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;
import org.xerial.snappy.Snappy;

public class ZipUtils {
    private static final Logger log = LoggerFactory.getLogger(ZipUtils.class);

    public static String zipStr(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        try {
            byte[] compress = ZipUtils.compress(str.getBytes(StandardCharsets.UTF_8));
            str = Base64Utils.encodeToString((byte[])compress);
        }
        catch (IOException e) {
            log.error("zipStr raw text:{}", (Object)str);
            e.printStackTrace();
            log.error("zipStr ex:{}", (Throwable)e);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String unZipStr(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        try {
            byte[] bytes = Base64Utils.decodeFromString((String)str);
            byte[] uncompress = ZipUtils.uncompress(bytes);
            str = new String(uncompress, StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            log.error("unzipStr raw text:{}", (Object)str);
            e.printStackTrace();
            log.error("unzipStr ex:{}", (Throwable)e);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static byte[] compress(byte[] bytes) throws IOException {
        return Snappy.compress((byte[])bytes);
    }

    public static byte[] uncompress(byte[] bytes) throws IOException {
        return Snappy.uncompress((byte[])bytes);
    }

    public static byte[] gzip(byte[] data) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(bos);
        gzip.write(data);
        gzip.finish();
        gzip.close();
        byte[] ret = bos.toByteArray();
        bos.flush();
        bos.close();
        return ret;
    }

    public static byte[] unGzip(byte[] data) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        GZIPInputStream gzip = new GZIPInputStream(bis);
        byte[] buf = new byte[1024];
        int num = -1;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((num = gzip.read(buf, 0, buf.length)) != -1) {
            bos.write(buf, 0, num);
        }
        gzip.close();
        bis.close();
        byte[] ret = bos.toByteArray();
        bos.flush();
        bos.close();
        return ret;
    }
}
