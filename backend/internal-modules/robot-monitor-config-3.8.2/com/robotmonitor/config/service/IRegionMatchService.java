/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.config.ConfigRegion
 */
package com.robotmonitor.config.service;

import com.robotmonitor.common.core.domain.config.ConfigRegion;
import com.robotmonitor.config.domain.deepglint.face.CaptureFace;

public interface IRegionMatchService {
    public ConfigRegion matchRegion(CaptureFace var1, String var2);

    public ConfigRegion matchRegion(String var1, String var2, Long var3, Integer var4, Integer var5, Integer var6, Integer var7, Integer var8, Integer var9);

    public CaptureFace getLatestCaptureFace(String var1);
}
