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

class RepeatedlyRequestWrapper.1
extends ServletInputStream {
    final /* synthetic */ ByteArrayInputStream val$bais;

    RepeatedlyRequestWrapper.1(ByteArrayInputStream byteArrayInputStream) {
        this.val$bais = byteArrayInputStream;
    }

    public int read() throws IOException {
        return this.val$bais.read();
    }

    public int available() throws IOException {
        return RepeatedlyRequestWrapper.this.body.length;
    }

    public boolean isFinished() {
        return false;
    }

    public boolean isReady() {
        return false;
    }

    public void setReadListener(ReadListener readListener) {
    }
}
