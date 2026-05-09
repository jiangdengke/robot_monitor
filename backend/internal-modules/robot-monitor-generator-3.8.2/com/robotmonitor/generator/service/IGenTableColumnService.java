/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.generator.service;

import com.robotmonitor.generator.domain.GenTableColumn;
import java.util.List;

public interface IGenTableColumnService {
    public List<GenTableColumn> selectGenTableColumnListByTableId(Long var1);

    public int insertGenTableColumn(GenTableColumn var1);

    public int updateGenTableColumn(GenTableColumn var1);

    public int deleteGenTableColumnByIds(String var1);
}
