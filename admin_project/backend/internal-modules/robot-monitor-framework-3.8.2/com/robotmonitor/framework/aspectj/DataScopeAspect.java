/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.DataScope
 *  com.robotmonitor.common.core.domain.BaseEntity
 *  com.robotmonitor.common.core.domain.entity.SysRole
 *  com.robotmonitor.common.core.domain.entity.SysUser
 *  com.robotmonitor.common.core.domain.model.LoginUser
 *  com.robotmonitor.common.utils.SecurityUtils
 *  com.robotmonitor.common.utils.StringUtils
 *  org.aspectj.lang.JoinPoint
 *  org.aspectj.lang.annotation.Aspect
 *  org.aspectj.lang.annotation.Before
 *  org.springframework.stereotype.Component
 */
package com.robotmonitor.framework.aspectj;

import com.robotmonitor.common.annotation.DataScope;
import com.robotmonitor.common.core.domain.BaseEntity;
import com.robotmonitor.common.core.domain.entity.SysRole;
import com.robotmonitor.common.core.domain.entity.SysUser;
import com.robotmonitor.common.core.domain.model.LoginUser;
import com.robotmonitor.common.utils.SecurityUtils;
import com.robotmonitor.common.utils.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataScopeAspect {
    public static final String DATA_SCOPE_ALL = "1";
    public static final String DATA_SCOPE_CUSTOM = "2";
    public static final String DATA_SCOPE_DEPT = "3";
    public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";
    public static final String DATA_SCOPE_SELF = "5";
    public static final String DATA_SCOPE = "dataScope";

    @Before(value="@annotation(controllerDataScope)")
    public void doBefore(JoinPoint point, DataScope controllerDataScope) throws Throwable {
        this.clearDataScope(point);
        this.handleDataScope(point, controllerDataScope);
    }

    protected void handleDataScope(JoinPoint joinPoint, DataScope controllerDataScope) {
        SysUser currentUser;
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (StringUtils.isNotNull((Object)loginUser) && StringUtils.isNotNull((Object)(currentUser = loginUser.getUser())) && !currentUser.isAdmin()) {
            DataScopeAspect.dataScopeFilter(joinPoint, currentUser, controllerDataScope.deptAlias(), controllerDataScope.userAlias());
        }
    }

    public static void dataScopeFilter(JoinPoint joinPoint, SysUser user, String deptAlias, String userAlias) {
        Object params;
        StringBuilder sqlString = new StringBuilder();
        for (SysRole role : user.getRoles()) {
            String dataScope = role.getDataScope();
            if (DATA_SCOPE_ALL.equals(dataScope)) {
                sqlString = new StringBuilder();
                break;
            }
            if (DATA_SCOPE_CUSTOM.equals(dataScope)) {
                sqlString.append(StringUtils.format((String)" OR {}.dept_id IN ( SELECT dept_id FROM sys_role_dept WHERE role_id = {} ) ", (Object[])new Object[]{deptAlias, role.getRoleId()}));
                continue;
            }
            if (DATA_SCOPE_DEPT.equals(dataScope)) {
                sqlString.append(StringUtils.format((String)" OR {}.dept_id = {} ", (Object[])new Object[]{deptAlias, user.getDeptId()}));
                continue;
            }
            if (DATA_SCOPE_DEPT_AND_CHILD.equals(dataScope)) {
                sqlString.append(StringUtils.format((String)" OR {}.dept_id IN ( SELECT dept_id FROM sys_dept WHERE dept_id = {} or find_in_set( {} , ancestors ) )", (Object[])new Object[]{deptAlias, user.getDeptId(), user.getDeptId()}));
                continue;
            }
            if (!DATA_SCOPE_SELF.equals(dataScope)) continue;
            if (StringUtils.isNotBlank((CharSequence)userAlias)) {
                sqlString.append(StringUtils.format((String)" OR {}.user_id = {} ", (Object[])new Object[]{userAlias, user.getUserId()}));
                continue;
            }
            sqlString.append(StringUtils.format((String)" OR {}.dept_id = 0 ", (Object[])new Object[]{deptAlias}));
        }
        if (StringUtils.isNotBlank((CharSequence)sqlString.toString()) && StringUtils.isNotNull((Object)(params = joinPoint.getArgs()[0])) && params instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity)params;
            baseEntity.getParams().put(DATA_SCOPE, " AND (" + sqlString.substring(4) + ")");
        }
    }

    private void clearDataScope(JoinPoint joinPoint) {
        Object params = joinPoint.getArgs()[0];
        if (StringUtils.isNotNull((Object)params) && params instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity)params;
            baseEntity.getParams().put(DATA_SCOPE, "");
        }
    }
}
