/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.servlet.ReadListener
 *  jakarta.servlet.ServletInputStream
 *  jakarta.servlet.http.HttpServletRequest
 *  jakarta.servlet.http.HttpServletRequestWrapper
 *  org.apache.commons.io.IOUtils
 */
package com.robotmonitor.common.filter;

import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.common.utils.html.EscapeUtil;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;

public class XssHttpServletRequestWrapper
extends HttpServletRequestWrapper {
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            int length = values.length;
            String[] escapseValues = new String[length];
            for (int i = 0; i < length; ++i) {
                escapseValues[i] = EscapeUtil.clean(values[i]).trim();
            }
            return escapseValues;
        }
        return super.getParameterValues(name);
    }

    public ServletInputStream getInputStream() throws IOException {
        if (!this.isJsonRequest()) {
            return super.getInputStream();
        }
        String json = IOUtils.toString((InputStream)super.getInputStream(), (String)"utf-8");
        if (StringUtils.isEmpty(json)) {
            return super.getInputStream();
        }
        json = EscapeUtil.clean(json).trim();
        final byte[] jsonBytes = json.getBytes("utf-8");
        final ByteArrayInputStream bis = new ByteArrayInputStream(jsonBytes);
        return new ServletInputStream(){

            public boolean isFinished() {
                return true;
            }

            public boolean isReady() {
                return true;
            }

            public int available() throws IOException {
                return jsonBytes.length;
            }

            public void setReadListener(ReadListener readListener) {
            }

            public int read() throws IOException {
                return bis.read();
            }
        };
    }

    public boolean isJsonRequest() {
        String header = super.getHeader("Content-Type");
        return StringUtils.startsWithIgnoreCase((CharSequence)header, (CharSequence)"application/json");
    }
}
