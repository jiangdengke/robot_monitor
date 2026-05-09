/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.AutoFill
 *  com.robotmonitor.common.core.domain.config.ConfigImg
 *  com.robotmonitor.common.enums.OperationType
 */
package com.robotmonitor.config.mapper;

import com.robotmonitor.common.annotation.AutoFill;
import com.robotmonitor.common.core.domain.config.ConfigImg;
import com.robotmonitor.common.enums.OperationType;
import java.util.List;

public interface ConfigImgMapper {
    public ConfigImg selectConfigImgById(Long var1);

    public List<ConfigImg> selectConfigImgList(ConfigImg var1);

    @AutoFill(value=OperationType.INSERT)
    public int insertConfigImg(ConfigImg var1);

    @AutoFill(value=OperationType.UPDATE)
    public int updateConfigImg(ConfigImg var1);

    @AutoFill(value=OperationType.UPDATE)
    public int deleteConfigImgById(Long var1);

    @AutoFill(value=OperationType.UPDATE)
    public int deleteConfigImgByIds(Long[] var1);

    public List<ConfigImg> selectConfigImgListByIds(Long[] var1);
}
