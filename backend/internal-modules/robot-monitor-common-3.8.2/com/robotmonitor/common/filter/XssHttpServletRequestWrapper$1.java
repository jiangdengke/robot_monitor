/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.servlet.ReadListener
 *  jakarta.servlet.ServletInputStream
 */
package com.robotmonitor.common.filter;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

class XssHttpServletRequestWrapper.1
extends ServletInputStream {
    final /* synthetic */ byte[] val$jsonBytes;
    final /* synthetic */ ByteArrayInputStream val$bis;

    XssHttpServletRequestWrapper.1(byte[] byArray, ByteArrayInputStream byteArrayInputStream) {
        this.val$jsonBytes = byArray;
        this.val$bis = byteArrayInputStream;
    }

    public boolean isFinished() {
        return true;
    }

    public boolean isReady() {
        return true;
    }

    public int available() throws IOException {
        return this.val$jsonBytes.length;
    }

    public void setReadListener(ReadListener readListener) {
    }

    public int read() throws IOException {
        return this.val$bis.read();
    }
}
