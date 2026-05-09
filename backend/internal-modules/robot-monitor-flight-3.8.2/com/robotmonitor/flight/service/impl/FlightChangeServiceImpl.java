/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.flight.service.impl;

import com.robotmonitor.flight.domain.FlightChange;
import com.robotmonitor.flight.dto.CmdDto;
import com.robotmonitor.flight.dto.CmdItemDto;
import com.robotmonitor.flight.mapper.FlightChangeMapper;
import com.robotmonitor.flight.service.IFlightChangeService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightChangeServiceImpl
implements IFlightChangeService {
    @Autowired
    private FlightChangeMapper flightChangeMapper;

    @Override
    public List<FlightChange> queryList() {
        return this.flightChangeMapper.queryList();
    }

    @Override
    public List<CmdDto> queryCmdList() {
        List<CmdItemDto> list = this.flightChangeMapper.queryCmdList();
        Map<String, List<CmdItemDto>> map = list.stream().collect(Collectors.groupingBy(CmdItemDto::getType));
        List<CmdItemDto> itemList = null;
        Set keys = map.keySet();
        keys = keys.stream().sorted().collect(Collectors.toCollection(TreeSet::new));
        CmdDto dto = null;
        ArrayList<CmdDto> result = new ArrayList<CmdDto>();
        for (String k : keys) {
            itemList = map.get(k);
            if (itemList == null || itemList.size() == 0) continue;
            dto = new CmdDto();
            dto.setType(k);
            dto.setName(itemList.get(0).getName());
            list = map.get(k).stream().sorted(Comparator.comparingInt(CmdItemDto::getDataSort)).collect(Collectors.toList());
            dto.setList(list);
            result.add(dto);
        }
        return result;
    }
}
