/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.mapper;

import com.robotmonitor.flight.domain.GetInTmp;
import java.util.List;

public interface GetInTmpMapper {
    public GetInTmp selectGetInTmpById(Long var1);

    public List<GetInTmp> selectGetInTmpList(GetInTmp var1);

    public int insertGetInTmp(GetInTmp var1);

    public int updateGetInTmp(GetInTmp var1);

    public int deleteGetInTmpById(Long var1);

    public int deleteGetInTmpByIds(Long[] var1);
}
