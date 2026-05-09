/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.utils.http;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

private static class HttpUtils.TrustAnyHostnameVerifier
implements HostnameVerifier {
    private HttpUtils.TrustAnyHostnameVerifier() {
    }

    @Override
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }
}
