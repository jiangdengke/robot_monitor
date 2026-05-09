/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.core.controller;

import com.robotmonitor.common.utils.DateUtils;
import java.beans.PropertyEditorSupport;

class BaseController.1
extends PropertyEditorSupport {
    BaseController.1() {
    }

    @Override
    public void setAsText(String text) {
        this.setValue(DateUtils.parseDate(text));
    }
}
