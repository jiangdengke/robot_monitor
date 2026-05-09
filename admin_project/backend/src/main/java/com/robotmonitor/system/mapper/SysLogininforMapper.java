/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.system.mapper;

import com.robotmonitor.system.domain.SysLogininfor;
import java.util.List;

public interface SysLogininforMapper {
    public void insertLogininfor(SysLogininfor var1);

    public List<SysLogininfor> selectLogininforList(SysLogininfor var1);

    public int deleteLogininforByIds(Long[] var1);

    public int cleanLogininfor();
}
