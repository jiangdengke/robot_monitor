/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.config.ConfigRegion
 *  com.robotmonitor.config.dto.ConfigAreaDto
 */
package com.robotmonitor.flight.service;

import com.robotmonitor.common.core.domain.config.ConfigRegion;
import com.robotmonitor.config.dto.ConfigAreaDto;
import com.robotmonitor.flight.domain.digitalTwin.DigitalTwinDto;
import java.util.List;

public interface IDigitalTwinService {
    public List<ConfigRegion> selectRegionList(String var1);

    public List<ConfigAreaDto> selectAreaList(String var1, String var2);

    public Long robotGuide(Long var1);

    public DigitalTwinDto getAll(String var1);

    public void handleInspection(String var1);
}
