/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.servlet.http.HttpServletRequest
 */
package com.robotmonitor.common.utils.ip;

import com.robotmonitor.common.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpUtils {
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String[] headerNames = new String[]{"x-forwarded-for", "Proxy-Client-IP", "WL-Proxy-Client-IP", "X-Real-IP"};
        String ip = null;
        for (String header : headerNames) {
            ip = request.getHeader(header);
            if (IpUtils.isUnknown(ip)) continue;
            ip = IpUtils.extractFirstValidIp(ip);
            break;
        }
        if (IpUtils.isUnknown(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    private static String extractFirstValidIp(String ipString) {
        String[] ips;
        if (ipString == null) {
            return null;
        }
        for (String ip : ips = ipString.split(",")) {
            if (!IpUtils.isValidIp(ip = ip.trim())) continue;
            return ip;
        }
        return null;
    }

    private static boolean isValidIp(String ip) {
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            return false;
        }
        if (IpUtils.textToNumericFormatV4(ip) != null) {
            return true;
        }
        return ip.contains(":");
    }

    public static boolean internalIp(String ip) {
        byte[] addr = IpUtils.textToNumericFormatV4(ip);
        return IpUtils.internalIp(addr) || "127.0.0.1".equals(ip);
    }

    private static boolean internalIp(byte[] addr) {
        if (StringUtils.isNull(addr) || addr.length < 2) {
            return true;
        }
        byte b0 = addr[0];
        byte b1 = addr[1];
        int SECTION_1 = 10;
        int SECTION_2 = -84;
        int SECTION_3 = 16;
        int SECTION_4 = 31;
        int SECTION_5 = -64;
        int SECTION_6 = -88;
        switch (b0) {
            case 10: {
                return true;
            }
            case -84: {
                if (b1 < 16 || b1 > 31) break;
                return true;
            }
            case -64: {
                if (b1 != -88) break;
                return true;
            }
            default: {
                return false;
            }
        }
        return false;
    }

    public static byte[] textToNumericFormatV4(String text) {
        if (text == null || text.length() == 0) {
            return null;
        }
        byte[] bytes = new byte[4];
        String[] elements = text.split("\\.", -1);
        try {
            switch (elements.length) {
                case 1: {
                    long l = Long.parseLong(elements[0]);
                    if (l < 0L || l > 0xFFFFFFFFL) {
                        return null;
                    }
                    bytes[0] = (byte)(l >> 24 & 0xFFL);
                    bytes[1] = (byte)((l & 0xFFFFFFL) >> 16 & 0xFFL);
                    bytes[2] = (byte)((l & 0xFFFFL) >> 8 & 0xFFL);
                    bytes[3] = (byte)(l & 0xFFL);
                    break;
                }
                case 2: {
                    long l = Integer.parseInt(elements[0]);
                    if (l < 0L || l > 255L) {
                        return null;
                    }
                    bytes[0] = (byte)(l & 0xFFL);
                    l = Integer.parseInt(elements[1]);
                    if (l < 0L || l > 0xFFFFFFL) {
                        return null;
                    }
                    bytes[1] = (byte)(l >> 16 & 0xFFL);
                    bytes[2] = (byte)((l & 0xFFFFL) >> 8 & 0xFFL);
                    bytes[3] = (byte)(l & 0xFFL);
                    break;
                }
                case 3: {
                    long l;
                    for (int i = 0; i < 2; ++i) {
                        l = Integer.parseInt(elements[i]);
                        if (l < 0L || l > 255L) {
                            return null;
                        }
                        bytes[i] = (byte)(l & 0xFFL);
                    }
                    l = Integer.parseInt(elements[2]);
                    if (l < 0L || l > 65535L) {
                        return null;
                    }
                    bytes[2] = (byte)(l >> 8 & 0xFFL);
                    bytes[3] = (byte)(l & 0xFFL);
                    break;
                }
                case 4: {
                    for (int i = 0; i < 4; ++i) {
                        long l = Integer.parseInt(elements[i]);
                        if (l < 0L || l > 255L) {
                            return null;
                        }
                        bytes[i] = (byte)(l & 0xFFL);
                    }
                    break;
                }
                default: {
                    return null;
                }
            }
        }
        catch (NumberFormatException e) {
            return null;
        }
        return bytes;
    }

    public static String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }

    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        }
        catch (UnknownHostException e) {
            return "\u672a\u77e5";
        }
    }

    public static boolean isUnknown(String checkString) {
        return StringUtils.isBlank((CharSequence)checkString) || "unknown".equalsIgnoreCase(checkString);
    }
}
