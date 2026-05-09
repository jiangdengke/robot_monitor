/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.utils.RedisRequestClient
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.data.redis.core.StringRedisTemplate
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.config.service.impl;

import com.robotmonitor.common.utils.RedisRequestClient;
import com.robotmonitor.config.domain.deepglint.changelist.ChangeListRequest;
import com.robotmonitor.config.domain.deepglint.changelist.ChangeListResponse;
import com.robotmonitor.config.domain.deepglint.compare.DeleteRegisterRequest;
import com.robotmonitor.config.domain.deepglint.compare.DeleteRegisterResponse;
import com.robotmonitor.config.domain.deepglint.compare.ListRegisterRequest;
import com.robotmonitor.config.domain.deepglint.compare.ListRegisterResponse;
import com.robotmonitor.config.domain.deepglint.compare.RegisterPersonToCompareRepoRequest;
import com.robotmonitor.config.domain.deepglint.compare.RegisterPersonToCompareRepoResponse;
import com.robotmonitor.config.domain.deepglint.face.FaceHistoryAlertRequest;
import com.robotmonitor.config.domain.deepglint.face.FaceHistoryAlertResponse;
import com.robotmonitor.config.domain.deepglint.facelist.FaceListRequest;
import com.robotmonitor.config.domain.deepglint.facelist.FaceListResponse;
import com.robotmonitor.config.service.IDeepGlintService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class DeepGlintServiceImpl
implements IDeepGlintService {
    private static final Logger log = LoggerFactory.getLogger(DeepGlintServiceImpl.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public RegisterPersonToCompareRepoResponse registerPersonToCompareRepo(RegisterPersonToCompareRepoRequest request) {
        RedisRequestClient redisRequestClient = new RedisRequestClient(this.stringRedisTemplate);
        return (RegisterPersonToCompareRepoResponse)redisRequestClient.sendAndReceive("registerPersonToCompareRepoQueue", (Object)request, RegisterPersonToCompareRepoResponse.class, 30);
    }

    @Override
    public ListRegisterResponse listRegister(ListRegisterRequest request) {
        RedisRequestClient redisRequestClient = new RedisRequestClient(this.stringRedisTemplate);
        return (ListRegisterResponse)redisRequestClient.sendAndReceive("listRegisterQueue", (Object)request, ListRegisterResponse.class, 30);
    }

    @Override
    public DeleteRegisterResponse deleteRegister(DeleteRegisterRequest request) {
        RedisRequestClient redisRequestClient = new RedisRequestClient(this.stringRedisTemplate);
        return (DeleteRegisterResponse)redisRequestClient.sendAndReceive("deleteRegisterQueue", (Object)request, DeleteRegisterResponse.class, 30);
    }

    @Override
    public FaceListResponse queryFaceList(FaceListRequest request) {
        RedisRequestClient redisRequestClient = new RedisRequestClient(this.stringRedisTemplate);
        return (FaceListResponse)redisRequestClient.sendAndReceive("queryFaceListQueue", (Object)request, FaceListResponse.class, 30);
    }

    @Override
    public FaceHistoryAlertResponse queryFaceHistoryAlert(FaceHistoryAlertRequest request) {
        RedisRequestClient redisRequestClient = new RedisRequestClient(this.stringRedisTemplate);
        return (FaceHistoryAlertResponse)redisRequestClient.sendAndReceive("queryFaceListQueue", (Object)request, FaceHistoryAlertResponse.class, 30);
    }

    @Override
    public ChangeListResponse personChangelist(ChangeListRequest request) {
        RedisRequestClient redisRequestClient = new RedisRequestClient(this.stringRedisTemplate);
        return (ChangeListResponse)redisRequestClient.sendAndReceive("personChangelistQueue", (Object)request, ChangeListResponse.class, 30);
    }
}
