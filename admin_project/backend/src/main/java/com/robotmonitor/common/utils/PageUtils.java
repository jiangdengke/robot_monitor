/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.pagehelper.PageHelper
 */
package com.robotmonitor.common.utils;

import com.github.pagehelper.PageHelper;
import com.robotmonitor.common.core.page.PageDomain;
import com.robotmonitor.common.core.page.TableSupport;
import com.robotmonitor.common.utils.sql.SqlUtil;

public class PageUtils
extends PageHelper {
    public static void startPage() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
        Boolean reasonable = pageDomain.getReasonable();
        PageHelper.startPage((int)pageNum, (int)pageSize, (String)orderBy).setReasonable(reasonable);
    }

    public static void clearPage() {
        PageHelper.clearPage();
    }
}
