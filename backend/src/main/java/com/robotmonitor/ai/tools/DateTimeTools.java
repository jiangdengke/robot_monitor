/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.ai.tool.annotation.Tool
 *  org.springframework.context.i18n.LocaleContextHolder
 *  org.springframework.stereotype.Component
 */
package com.robotmonitor.ai.tools;

import java.time.LocalDateTime;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class DateTimeTools {
    @Tool(description="Get the current date and time in the user's timezone")
    public String getCurrentDateTime() {
        System.out.println("getCurrentDateTime()...");
        return LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
    }
}
