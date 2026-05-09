/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.utils.DateUtils
 *  com.robotmonitor.config.deepglint.DeepGlintApiConfig
 *  com.robotmonitor.config.domain.deepglint.compare.DeleteRegisterRequest
 *  com.robotmonitor.config.domain.deepglint.compare.DeleteRegisterResponse
 *  com.robotmonitor.config.domain.deepglint.compare.ListRegisterData
 *  com.robotmonitor.config.domain.deepglint.compare.ListRegisterRequest
 *  com.robotmonitor.config.domain.deepglint.compare.ListRegisterResponse
 *  com.robotmonitor.config.domain.deepglint.compare.RegisterData
 *  com.robotmonitor.config.domain.deepglint.compare.RegisterPerson
 *  com.robotmonitor.config.service.IDeepGlintService
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 */
package com.robotmonitor.quartz.task;

import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.config.deepglint.DeepGlintApiConfig;
import com.robotmonitor.config.domain.deepglint.compare.DeleteRegisterRequest;
import com.robotmonitor.config.domain.deepglint.compare.DeleteRegisterResponse;
import com.robotmonitor.config.domain.deepglint.compare.ListRegisterData;
import com.robotmonitor.config.domain.deepglint.compare.ListRegisterRequest;
import com.robotmonitor.config.domain.deepglint.compare.ListRegisterResponse;
import com.robotmonitor.config.domain.deepglint.compare.RegisterData;
import com.robotmonitor.config.domain.deepglint.compare.RegisterPerson;
import com.robotmonitor.config.service.IDeepGlintService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="registerCleanTask")
public class RegisterCleanTask {
    private static final Logger log = LoggerFactory.getLogger(RegisterCleanTask.class);
    @Autowired
    private IDeepGlintService deepGlintService;
    @Autowired
    private DeepGlintApiConfig deepGlintApiConfig;

    public void cleanExpiredRegisters() {
        log.info("\u5f00\u59cb\u6267\u884c\u6e05\u7406\u8fc7\u671f\u6ce8\u518c\u4eba\u5458\u5b9a\u65f6\u4efb\u52a1");
        try {
            Date referenceDate = DateUtils.getTodayStart();
            Calendar cal = Calendar.getInstance();
            cal.setTime(referenceDate);
            cal.add(5, -2);
            Date twoDaysAgoStart = cal.getTime();
            long twoDaysAgoStartTimestamp = twoDaysAgoStart.getTime() * 1000000L;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String twoDaysAgoFormatted = sdf.format(twoDaysAgoStart);
            log.info("\u4e24\u5929\u524d\u7684\u8d77\u59cb\u65f6\u95f4: {}", (Object)twoDaysAgoFormatted);
            log.info("\u4e24\u5929\u524d\u7684\u8d77\u59cb\u65f6\u95f4\u6233\uff08\u7eb3\u79d2\uff09: {}", (Object)twoDaysAgoStartTimestamp);
            ListRegisterRequest listRequest = new ListRegisterRequest();
            listRequest.setRepoId(this.deepGlintApiConfig.getRepoId());
            listRequest.setLimit(Integer.valueOf(10000));
            ListRegisterResponse listResponse = this.deepGlintService.listRegister(listRequest);
            if (listResponse != null && listResponse.getCode() != null && listResponse.getCode() == 1) {
                ListRegisterData data = listResponse.getData();
                if (data != null && data.getRegisters() != null) {
                    ArrayList<String> expiredRegisterIds = new ArrayList<String>();
                    for (RegisterData registerData : data.getRegisters()) {
                        RegisterPerson registerPerson = registerData.getRegisterPerson();
                        if (registerPerson == null) continue;
                        Long createTime = registerPerson.getCts();
                        Date createTimeDate = new Date(createTime / 1000000L);
                        String createTimeFormatted = sdf.format(createTimeDate);
                        if (createTime == null || createTime >= twoDaysAgoStartTimestamp) continue;
                        expiredRegisterIds.add(registerPerson.getRegisterId());
                        log.info("\u53d1\u73b0\u8fc7\u671f\u6ce8\u518c\u4eba\u5458: ID={}, Name={}, \u521b\u5efa\u65f6\u95f4={}, \u521b\u5efa\u65f6\u95f4\u6233={}", new Object[]{registerPerson.getRegisterId(), registerPerson.getName(), createTimeFormatted, createTime});
                    }
                    if (!expiredRegisterIds.isEmpty()) {
                        log.info("\u5171\u627e\u5230 {} \u4e2a\u8fc7\u671f\u7684\u6ce8\u518c\u4eba\u5458\uff0c\u5f00\u59cb\u5220\u9664...", (Object)expiredRegisterIds.size());
                        int successCount = 0;
                        int failureCount = 0;
                        for (String registerId : expiredRegisterIds) {
                            DeleteRegisterRequest deleteRequest = new DeleteRegisterRequest();
                            deleteRequest.setRepoId(this.deepGlintApiConfig.getRepoId());
                            deleteRequest.setRegisterId(registerId);
                            DeleteRegisterResponse deleteResponse = this.deepGlintService.deleteRegister(deleteRequest);
                            if (deleteResponse != null && deleteResponse.getCode() != null && deleteResponse.getCode() == 1) {
                                log.info("\u6210\u529f\u5220\u9664\u6ce8\u518c\u4eba\u5458\uff0cID: {}", (Object)registerId);
                                ++successCount;
                            } else {
                                log.error("\u5220\u9664\u6ce8\u518c\u4eba\u5458\u5931\u8d25\uff0cID: {}\uff0c\u9519\u8bef\u4fe1\u606f: {}", (Object)registerId, (Object)(deleteResponse != null ? deleteResponse.getMsg() : "\u672a\u77e5\u9519\u8bef"));
                                ++failureCount;
                            }
                            try {
                                Thread.sleep(100L);
                            }
                            catch (InterruptedException e) {
                                log.warn("\u5220\u9664\u8fc7\u7a0b\u4e2d\u7ebf\u7a0b\u88ab\u4e2d\u65ad");
                                Thread.currentThread().interrupt();
                                break;
                            }
                        }
                        log.info("\u5b8c\u6210\u5220\u9664\u8fc7\u671f\u6ce8\u518c\u4eba\u5458\uff0c\u6210\u529f: {} \u4e2a\uff0c\u5931\u8d25: {} \u4e2a\uff0c\u603b\u8ba1: {} \u4e2a", new Object[]{successCount, failureCount, expiredRegisterIds.size()});
                    } else {
                        log.info("\u6ca1\u6709\u627e\u5230\u8fc7\u671f\u7684\u6ce8\u518c\u4eba\u5458\uff0c\u65e0\u9700\u5220\u9664");
                    }
                } else {
                    log.info("\u6ca1\u6709\u83b7\u53d6\u5230\u6ce8\u518c\u4eba\u5458\u6570\u636e\u6216\u6570\u636e\u4e3a\u7a7a");
                }
            } else {
                log.error("\u67e5\u8be2\u6ce8\u518c\u4eba\u5458\u5217\u8868\u5931\u8d25\uff0c\u9519\u8bef\u7801: {}, \u9519\u8bef\u4fe1\u606f: {}", listResponse != null ? listResponse.getCode() : "null", (Object)(listResponse != null ? listResponse.getMsg() : "null"));
            }
        }
        catch (Exception e) {
            log.error("\u6267\u884c\u6e05\u7406\u8fc7\u671f\u6ce8\u518c\u4eba\u5458\u5b9a\u65f6\u4efb\u52a1\u65f6\u53d1\u751f\u5f02\u5e38", (Throwable)e);
            throw e;
        }
        log.info("\u7ed3\u675f\u6267\u884c\u6e05\u7406\u8fc7\u671f\u6ce8\u518c\u4eba\u5458\u5b9a\u65f6\u4efb\u52a1");
    }
}
