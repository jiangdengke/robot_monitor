/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.config.ConfigImg
 *  com.robotmonitor.common.utils.DateUtils
 *  com.robotmonitor.common.utils.DictUtils
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.config.service.impl;

import com.robotmonitor.common.core.domain.config.ConfigImg;
import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.common.utils.DictUtils;
import com.robotmonitor.config.mapper.ConfigImgMapper;
import com.robotmonitor.config.service.IConfigImgService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigImgServiceImpl
implements IConfigImgService {
    @Autowired
    private ConfigImgMapper configImgMapper;

    @Override
    public ConfigImg selectConfigImgById(Long id) {
        return this.configImgMapper.selectConfigImgById(id);
    }

    @Override
    public List<ConfigImg> selectConfigImgList(ConfigImg configImg) {
        List<ConfigImg> list = this.configImgMapper.selectConfigImgList(configImg);
        for (ConfigImg info : list) {
            info.setImgTypeStr(DictUtils.getDictLabel((String)"pic_type", (String)info.getImgType()));
        }
        return list;
    }

    @Override
    public int insertConfigImg(ConfigImg configImg) {
        configImg.setIsDelete("0");
        return this.configImgMapper.insertConfigImg(configImg);
    }

    @Override
    public int updateConfigImg(ConfigImg configImg) {
        configImg.setUpdateTime(DateUtils.getNowDate());
        return this.configImgMapper.updateConfigImg(configImg);
    }

    @Override
    public int deleteConfigImgByIds(Long[] ids) {
        return this.configImgMapper.deleteConfigImgByIds(ids);
    }

    @Override
    public int deleteConfigImgById(Long id) {
        return this.configImgMapper.deleteConfigImgById(id);
    }

    @Override
    public List<ConfigImg> selectConfigImgListByIds(Long[] ids) {
        return this.configImgMapper.selectConfigImgListByIds(ids);
    }
}
