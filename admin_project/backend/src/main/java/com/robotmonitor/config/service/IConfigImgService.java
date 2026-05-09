/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.config.ConfigImg
 */
package com.robotmonitor.config.service;

import com.robotmonitor.common.core.domain.config.ConfigImg;
import java.util.List;

public interface IConfigImgService {
    public ConfigImg selectConfigImgById(Long var1);

    public List<ConfigImg> selectConfigImgList(ConfigImg var1);

    public int insertConfigImg(ConfigImg var1);

    public int updateConfigImg(ConfigImg var1);

    public int deleteConfigImgByIds(Long[] var1);

    public int deleteConfigImgById(Long var1);

    public List<ConfigImg> selectConfigImgListByIds(Long[] var1);
}
