/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.flight.service.impl;

import com.robotmonitor.flight.domain.GetInTmp;
import com.robotmonitor.flight.mapper.GetInTmpMapper;
import com.robotmonitor.flight.service.IGetInTmpService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetInTmpServiceImpl
implements IGetInTmpService {
    @Autowired
    private GetInTmpMapper getInTmpMapper;

    @Override
    public GetInTmp selectGetInTmpById(Long id) {
        return this.getInTmpMapper.selectGetInTmpById(id);
    }

    @Override
    public List<GetInTmp> selectGetInTmpList(GetInTmp getInTmp) {
        return this.getInTmpMapper.selectGetInTmpList(getInTmp);
    }

    @Override
    public int insertGetInTmp(GetInTmp getInTmp) {
        return this.getInTmpMapper.insertGetInTmp(getInTmp);
    }

    @Override
    public int updateGetInTmp(GetInTmp getInTmp) {
        return this.getInTmpMapper.updateGetInTmp(getInTmp);
    }

    @Override
    public int deleteGetInTmpByIds(Long[] ids) {
        return this.getInTmpMapper.deleteGetInTmpByIds(ids);
    }

    @Override
    public int deleteGetInTmpById(Long id) {
        return this.getInTmpMapper.deleteGetInTmpById(id);
    }
}
