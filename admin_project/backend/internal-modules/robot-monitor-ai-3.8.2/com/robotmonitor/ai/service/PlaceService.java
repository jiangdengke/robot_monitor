/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.ai.service;

import com.robotmonitor.ai.domain.PlaceInfo;

public interface PlaceService {
    public PlaceInfo findPlaceInfo(String var1, String var2, String var3, String var4);

    public String getPlaceNames(String var1, String var2);
}
