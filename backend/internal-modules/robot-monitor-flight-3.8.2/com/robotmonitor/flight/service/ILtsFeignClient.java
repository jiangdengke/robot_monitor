/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestHeader
 */
package com.robotmonitor.flight.service;

import com.robotmonitor.flight.domain.AccessInfoParam;
import com.robotmonitor.flight.domain.AccessInfoResponse;
import com.robotmonitor.flight.domain.AuthResponse;
import com.robotmonitor.flight.domain.BarCodeParam;
import com.robotmonitor.flight.domain.BarCodeRespons;
import com.robotmonitor.flight.domain.CollectInParam;
import com.robotmonitor.flight.domain.CollectInParam2;
import com.robotmonitor.flight.domain.CollectInResponse;
import com.robotmonitor.flight.domain.CollectInResponse2;
import com.robotmonitor.flight.domain.Result;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="ltsService", url="http://10.211.7.227/openapi")
public interface ILtsFeignClient {
    @PostMapping(value={"/collect/selectAccessInfo/v1.0"})
    public Result<BarCodeRespons> barCode(@RequestHeader Map<String, String> var1, @RequestBody BarCodeParam var2);

    @PostMapping(value={"/auth/get/v1.0"})
    public Result<AuthResponse> GetAuth(@RequestHeader Map<String, String> var1, @RequestBody String var2);

    @PostMapping(value={"/collect/selectAccessInfo/v1.0"})
    public Result<AccessInfoResponse> selectAccessInfo(@RequestHeader Map<String, String> var1, @RequestBody AccessInfoParam var2);

    @PostMapping(value={"/access/collectIn/v1.0"})
    public Result<CollectInResponse> collectIn(@RequestHeader Map<String, String> var1, @RequestBody CollectInParam var2);

    @PostMapping(value={"/autoCollect/saveCollect/v1.0"})
    public CollectInResponse2 saveCollect(@RequestHeader Map<String, String> var1, @RequestBody CollectInParam2 var2);
}
