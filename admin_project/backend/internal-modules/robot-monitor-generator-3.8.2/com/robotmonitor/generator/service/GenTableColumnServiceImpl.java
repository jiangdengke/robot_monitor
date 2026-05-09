/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.text.Convert
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.generator.service;

import com.robotmonitor.common.core.text.Convert;
import com.robotmonitor.generator.domain.GenTableColumn;
import com.robotmonitor.generator.mapper.GenTableColumnMapper;
import com.robotmonitor.generator.service.IGenTableColumnService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenTableColumnServiceImpl
implements IGenTableColumnService {
    @Autowired
    private GenTableColumnMapper genTableColumnMapper;

    @Override
    public List<GenTableColumn> selectGenTableColumnListByTableId(Long tableId) {
        return this.genTableColumnMapper.selectGenTableColumnListByTableId(tableId);
    }

    @Override
    public int insertGenTableColumn(GenTableColumn genTableColumn) {
        return this.genTableColumnMapper.insertGenTableColumn(genTableColumn);
    }

    @Override
    public int updateGenTableColumn(GenTableColumn genTableColumn) {
        return this.genTableColumnMapper.updateGenTableColumn(genTableColumn);
    }

    @Override
    public int deleteGenTableColumnByIds(String ids) {
        return this.genTableColumnMapper.deleteGenTableColumnByIds(Convert.toLongArray((String)ids));
    }
}
