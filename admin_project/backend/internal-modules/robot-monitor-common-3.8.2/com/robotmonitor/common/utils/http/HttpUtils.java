/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.robotmonitor.common.utils.http;

import com.robotmonitor.common.utils.StringUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {
    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    public static String sendGet(String url) {
        return HttpUtils.sendGet(url, "");
    }

    public static String sendGet(String url, String param) {
        return HttpUtils.sendGet(url, param, "UTF-8");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String sendGet(String url, String param, String contentType) {
        StringBuilder result = new StringBuilder();
        InputStream inputStream = null;
        InputStreamReader reader = null;
        BufferedReader in = null;
        try {
            String line;
            String urlNameString = StringUtils.isNotBlank((CharSequence)param) ? url + "?" + param : url;
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            inputStream = connection.getInputStream();
            reader = new InputStreamReader(inputStream, contentType);
            in = new BufferedReader(reader);
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        }
        catch (ConnectException e) {
            log.error("\u8c03\u7528HttpUtils.sendGet ConnectException, url=" + url + ",param=" + param, (Throwable)e);
        }
        catch (SocketTimeoutException e) {
            log.error("\u8c03\u7528HttpUtils.sendGet SocketTimeoutException, url=" + url + ",param=" + param, (Throwable)e);
        }
        catch (IOException e) {
            log.error("\u8c03\u7528HttpUtils.sendGet IOException, url=" + url + ",param=" + param, (Throwable)e);
        }
        catch (Exception e) {
            log.error("\u8c03\u7528HttpsUtil.sendGet Exception, url=" + url + ",param=" + param, (Throwable)e);
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            }
            catch (Exception ex) {
                log.error("\u8c03\u7528in.close Exception, url=" + url + ",param=" + param, (Throwable)ex);
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            }
            catch (IOException e) {
                log.error("\u8c03\u7528reader.close Exception, url=" + url + ",param=" + param, (Throwable)e);
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            catch (IOException e) {
                log.error("\u8c03\u7528inputStream.close Exception, url=" + url + ",param=" + param, (Throwable)e);
            }
        }
        return result.toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String sendPost(String url, String param, Boolean jsonFlag) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            String line;
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            if (jsonFlag.booleanValue()) {
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
            }
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        }
        catch (ConnectException e) {
            log.error("\u8c03\u7528HttpUtils.sendPost ConnectException, url=" + url + ",param=" + param, (Throwable)e);
        }
        catch (SocketTimeoutException e) {
            log.error("\u8c03\u7528HttpUtils.sendPost SocketTimeoutException, url=" + url + ",param=" + param, (Throwable)e);
        }
        catch (IOException e) {
            log.error("\u8c03\u7528HttpUtils.sendPost IOException, url=" + url + ",param=" + param, (Throwable)e);
        }
        catch (Exception e) {
            log.error("\u8c03\u7528HttpsUtil.sendPost Exception, url=" + url + ",param=" + param, (Throwable)e);
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            }
            catch (IOException ex) {
                log.error("\u8c03\u7528in.close Exception, url=" + url + ",param=" + param, (Throwable)ex);
            }
        }
        return result.toString();
    }

    private static class TrustAnyHostnameVerifier
    implements HostnameVerifier {
        private TrustAnyHostnameVerifier() {
        }

        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static class TrustAnyTrustManager
    implements X509TrustManager {
        private TrustAnyTrustManager() {
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}
