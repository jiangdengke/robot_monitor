/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.ai.domain.AiKnowledgeBase
 *  com.robotmonitor.ai.service.EmbeddingService
 *  com.robotmonitor.ai.service.IAiKnowledgeBaseService
 *  com.robotmonitor.common.annotation.Log
 *  com.robotmonitor.common.core.controller.BaseController
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.core.domain.entity.SysUser
 *  com.robotmonitor.common.core.page.TableDataInfo
 *  com.robotmonitor.common.enums.BusinessType
 *  com.robotmonitor.common.utils.poi.ExcelUtil
 *  jakarta.servlet.http.HttpServletResponse
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.security.access.prepost.PreAuthorize
 *  org.springframework.web.bind.WebDataBinder
 *  org.springframework.web.bind.annotation.DeleteMapping
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.InitBinder
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.PutMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.robotmonitor.web.controller.ai;

import com.robotmonitor.ai.domain.AiKnowledgeBase;
import com.robotmonitor.ai.service.EmbeddingService;
import com.robotmonitor.ai.service.IAiKnowledgeBaseService;
import com.robotmonitor.common.annotation.Log;
import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.domain.entity.SysUser;
import com.robotmonitor.common.core.page.TableDataInfo;
import com.robotmonitor.common.enums.BusinessType;
import com.robotmonitor.common.utils.poi.ExcelUtil;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/ai/knowledge"})
public class AiKnowledgeBaseController
extends BaseController {
    @Autowired
    private IAiKnowledgeBaseService aiKnowledgeBaseService;
    @Autowired
    private EmbeddingService embeddingService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields(new String[]{"updateBy"});
    }

    @PreAuthorize(value="@ss.hasPermi('ai:knowledge:list')")
    @GetMapping(value={"/list"})
    public TableDataInfo list(AiKnowledgeBase aiKnowledgeBase) {
        this.startPage();
        List list = this.aiKnowledgeBaseService.selectAiKnowledgeBaseList(aiKnowledgeBase);
        return this.getDataTable(list);
    }

    @PreAuthorize(value="@ss.hasPermi('ai:knowledge:export')")
    @Log(title="\u77e5\u8bc6\u5e93", businessType=BusinessType.EXPORT)
    @PostMapping(value={"/export"})
    public void export(HttpServletResponse response, AiKnowledgeBase aiKnowledgeBase) {
        List list = this.aiKnowledgeBaseService.selectAiKnowledgeBaseList(aiKnowledgeBase);
        ExcelUtil util = new ExcelUtil(AiKnowledgeBase.class);
        util.exportExcel(response, list, "\u77e5\u8bc6\u5e93\u6570\u636e");
    }

    @PreAuthorize(value="@ss.hasPermi('ai:knowledge:query')")
    @GetMapping(value={"/{id}"})
    public AjaxResult getInfo(@PathVariable(value="id") Long id) {
        return AjaxResult.success((Object)this.aiKnowledgeBaseService.selectAiKnowledgeBaseById(id));
    }

    @PreAuthorize(value="@ss.hasPermi('ai:knowledge:add')")
    @Log(title="\u77e5\u8bc6\u5e93", businessType=BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AiKnowledgeBase aiKnowledgeBase) {
        SysUser sysUser = this.getLoginUser().getUser();
        aiKnowledgeBase.setCreateBy(sysUser.getUserName());
        aiKnowledgeBase.setUpdateBy(sysUser.getUserName());
        return this.toAjax(this.aiKnowledgeBaseService.insertAiKnowledgeBase(aiKnowledgeBase));
    }

    @PreAuthorize(value="@ss.hasPermi('ai:knowledge:edit')")
    @Log(title="\u77e5\u8bc6\u5e93", businessType=BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AiKnowledgeBase aiKnowledgeBase) {
        return this.toAjax(this.aiKnowledgeBaseService.updateAiKnowledgeBase(aiKnowledgeBase));
    }

    @PreAuthorize(value="@ss.hasPermi('ai:knowledge:edit')")
    @Log(title="\u77e5\u8bc6\u5e93", businessType=BusinessType.UPDATE)
    @PostMapping(value={"/embedding"})
    public AjaxResult embedding(@RequestBody Long[] ids) {
        if (null == ids || ids.length <= 0) {
            return AjaxResult.error((String)"\u6ca1\u6709\u9700\u8981\u5411\u91cf\u5316\u7684\u77e5\u8bc6");
        }
        SysUser sysUser = this.getLoginUser().getUser();
        this.aiKnowledgeBaseService.embedding(ids, sysUser.getUserName());
        return AjaxResult.success();
    }

    @PreAuthorize(value="@ss.hasPermi('ai:knowledge:edit')")
    @Log(title="\u77e5\u8bc6\u5e93", businessType=BusinessType.UPDATE)
    @PostMapping(value={"/enable"})
    public AjaxResult enable(@RequestBody Long[] ids) {
        if (null == ids || ids.length <= 0) {
            return AjaxResult.error((String)"\u6ca1\u6709\u9700\u8981\u542f\u7528\u7684\u77e5\u8bc6");
        }
        SysUser sysUser = this.getLoginUser().getUser();
        this.aiKnowledgeBaseService.enable(ids, sysUser.getUserName());
        return AjaxResult.success();
    }

    @PreAuthorize(value="@ss.hasPermi('ai:knowledge:edit')")
    @Log(title="\u77e5\u8bc6\u5e93", businessType=BusinessType.UPDATE)
    @PostMapping(value={"/disable"})
    public AjaxResult disable(@RequestBody Long[] ids) {
        if (null == ids || ids.length <= 0) {
            return AjaxResult.error((String)"\u6ca1\u6709\u9700\u8981\u7981\u7528\u7684\u77e5\u8bc6");
        }
        SysUser sysUser = this.getLoginUser().getUser();
        this.aiKnowledgeBaseService.disable(ids, sysUser.getUserName());
        return AjaxResult.success();
    }

    @PreAuthorize(value="@ss.hasPermi('ai:knowledge:remove')")
    @Log(title="\u77e5\u8bc6\u5e93", businessType=BusinessType.DELETE)
    @DeleteMapping(value={"/{ids}"})
    public AjaxResult remove(@PathVariable Long[] ids) {
        if (null == ids || ids.length <= 0) {
            return AjaxResult.error((String)"\u6ca1\u6709\u9700\u8981\u5220\u9664\u7684\u77e5\u8bc6");
        }
        ArrayList aiKnowledgeBases = new ArrayList(ids.length);
        ArrayList<String> documentIds = new ArrayList<String>(ids.length);
        for (Long id : ids) {
            AiKnowledgeBase aiKnowledgeBase = this.aiKnowledgeBaseService.selectAiKnowledgeBaseById(id);
            documentIds.add(aiKnowledgeBase.getVectorId());
        }
        this.embeddingService.deleteVectorStoreByIds(documentIds);
        return this.toAjax(this.aiKnowledgeBaseService.deleteAiKnowledgeBaseByIds(ids));
    }
}
