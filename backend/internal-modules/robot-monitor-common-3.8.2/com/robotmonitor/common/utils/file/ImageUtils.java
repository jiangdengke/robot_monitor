/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.util.IOUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.robotmonitor.common.utils.file;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageUtils {
    private static final Logger log = LoggerFactory.getLogger(ImageUtils.class);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static byte[] getImage(String imagePath) {
        InputStream is = ImageUtils.getFile(imagePath);
        try {
            byte[] byArray = IOUtils.toByteArray((InputStream)is);
            return byArray;
        }
        catch (Exception e) {
            log.error("\u56fe\u7247\u52a0\u8f7d\u5f02\u5e38 {}", (Throwable)e);
            byte[] byArray = null;
            return byArray;
        }
        finally {
            IOUtils.closeQuietly((Closeable)is);
        }
    }

    public static InputStream getFile(String imagePath) {
        try {
            byte[] result = ImageUtils.readFile(imagePath);
            result = Arrays.copyOf(result, result.length);
            return new ByteArrayInputStream(result);
        }
        catch (Exception e) {
            log.error("\u83b7\u53d6\u56fe\u7247\u5f02\u5e38 {}", (Throwable)e);
            return null;
        }
    }

    public static byte[] readFile(String url) {
        block8: {
            byte[] byArray;
            block9: {
                if (!url.startsWith("http")) break block8;
                URL urlObj = new URL(url);
                URLConnection urlConnection = urlObj.openConnection();
                urlConnection.setConnectTimeout(30000);
                urlConnection.setReadTimeout(60000);
                urlConnection.setDoInput(true);
                InputStream in = urlConnection.getInputStream();
                try {
                    byArray = IOUtils.toByteArray((InputStream)in);
                    if (in == null) break block9;
                }
                catch (Throwable throwable) {
                    try {
                        if (in != null) {
                            try {
                                in.close();
                            }
                            catch (Throwable throwable2) {
                                throwable.addSuppressed(throwable2);
                            }
                        }
                        throw throwable;
                    }
                    catch (Exception e) {
                        log.error("\u83b7\u53d6\u6587\u4ef6\u8def\u5f84\u5f02\u5e38 {}", (Throwable)e);
                        return null;
                    }
                }
                in.close();
            }
            return byArray;
        }
        return null;
    }
}
