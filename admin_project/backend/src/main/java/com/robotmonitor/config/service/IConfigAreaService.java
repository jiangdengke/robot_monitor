/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.config.service;

import com.robotmonitor.config.domain.ConfigArea;
import com.robotmonitor.config.domain.ConfigAreaDetail;
import com.robotmonitor.config.dto.ConfigAreaDto;
import java.util.List;

public interface IConfigAreaService {
    public ConfigArea selectConfigAreaById(Long var1);

    public List<ConfigArea> selectConfigAreaList(ConfigArea var1);

    public boolean insertConfigArea(ConfigArea var1);

    public boolean updateConfigArea(ConfigArea var1);

    public int deleteConfigAreaByIds(Long[] var1);

    public boolean deleteConfigAreaById(Long var1);

    public List<ConfigAreaDto> convetToDto(List<ConfigArea> var1, String var2);

    public List<ConfigAreaDto> selectAreaList(String var1, String var2);

    public List<ConfigAreaDto> selectAreaListForDigitalTwin(String var1, String var2);

    public ConfigArea selectAreaByRoomCodeAndNameAndLanguage(String var1, String var2, String var3);

    public ConfigAreaDetail getConfigAreaDetailById(Long var1);

    public String findAreaNameByRoomCodeAndLanguageType(String var1, String var2);

    public List<ConfigAreaDetail> selectAreaByRegionId(Long var1);
}
