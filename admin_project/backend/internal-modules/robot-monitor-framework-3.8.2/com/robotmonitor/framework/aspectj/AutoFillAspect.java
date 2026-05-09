/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.robotmonitor.common.annotation.AutoFill
 *  com.robotmonitor.common.core.domain.BaseEntity
 *  com.robotmonitor.common.enums.OperationType
 *  com.robotmonitor.common.utils.DateUtils
 *  com.robotmonitor.common.utils.SecurityUtils
 *  org.apache.commons.lang3.ObjectUtils
 *  org.aspectj.lang.ProceedingJoinPoint
 *  org.aspectj.lang.annotation.Around
 *  org.aspectj.lang.annotation.Aspect
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 */
package com.robotmonitor.framework.aspectj;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robotmonitor.common.annotation.AutoFill;
import com.robotmonitor.common.core.domain.BaseEntity;
import com.robotmonitor.common.enums.OperationType;
import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.common.utils.SecurityUtils;
import java.util.Date;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AutoFillAspect {
    private static final Logger log = LoggerFactory.getLogger(AutoFillAspect.class);
    @Autowired
    private ObjectMapper objectMapper;

    @Around(value="@annotation(autoFill)")
    public Object around(ProceedingJoinPoint pjp, AutoFill autoFill) throws Throwable {
        Object[] args;
        for (Object arg : args = pjp.getArgs()) {
            if (!(arg instanceof BaseEntity)) continue;
            this.fillFields((BaseEntity)arg, autoFill.value());
        }
        return pjp.proceed(args);
    }

    private void fillFields(BaseEntity entity, OperationType type) {
        String currentUser = "";
        if (ObjectUtils.isNotEmpty((Object)SecurityUtils.getAuthentication()) && ObjectUtils.isNotEmpty((Object)SecurityUtils.getLoginUser())) {
            currentUser = SecurityUtils.getLoginUser().getUser().getUserName();
        }
        Date now = DateUtils.getNowDate();
        if (type == OperationType.INSERT) {
            entity.setCreateTime(now);
            entity.setCreateBy(currentUser);
        }
        entity.setUpdateTime(now);
        entity.setUpdateBy(currentUser);
    }
}
