/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.controller.BaseController
 *  org.springframework.security.access.prepost.PreAuthorize
 *  org.springframework.stereotype.Controller
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.robotmonitor.web.controller.tool;

import com.robotmonitor.common.core.controller.BaseController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value={"/tool/swagger"})
public class SwaggerController
extends BaseController {
    @PreAuthorize(value="@ss.hasPermi('tool:swagger:view')")
    @GetMapping
    public String index() {
        return this.redirect("/swagger-ui.html");
    }
}
