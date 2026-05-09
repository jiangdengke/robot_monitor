/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.mapper;

import com.robotmonitor.flight.domain.digitalTwin.InspectionDto;
import java.util.List;

public interface DigitalTwinMapper {
    public List<InspectionDto> getInspectionList(String var1);

    public void handleInspection(String var1);

    public String selectTaskStatusById(Long var1);
}
