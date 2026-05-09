/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.controller.BaseController
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.config.domain.RecognitionResult
 *  com.robotmonitor.config.domain.deepglint.compare.ListRegisterRequest
 *  com.robotmonitor.config.domain.deepglint.compare.ListRegisterResponse
 *  com.robotmonitor.config.domain.deepglint.compare.RegisterPersonToCompareRepoRequest
 *  com.robotmonitor.config.domain.deepglint.compare.RegisterPersonToCompareRepoResponse
 *  com.robotmonitor.config.domain.deepglint.face.FaceHistoryAlertRequest
 *  com.robotmonitor.config.domain.deepglint.face.FaceHistoryAlertResponse
 *  com.robotmonitor.config.service.IDeepGlintService
 *  com.robotmonitor.flight.service.IPassengerService
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.robotmonitor.web.controller.deepglint;

import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.config.domain.RecognitionResult;
import com.robotmonitor.config.domain.deepglint.compare.ListRegisterRequest;
import com.robotmonitor.config.domain.deepglint.compare.ListRegisterResponse;
import com.robotmonitor.config.domain.deepglint.compare.RegisterPersonToCompareRepoRequest;
import com.robotmonitor.config.domain.deepglint.compare.RegisterPersonToCompareRepoResponse;
import com.robotmonitor.config.domain.deepglint.face.FaceHistoryAlertRequest;
import com.robotmonitor.config.domain.deepglint.face.FaceHistoryAlertResponse;
import com.robotmonitor.config.service.IDeepGlintService;
import com.robotmonitor.flight.service.IPassengerService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/rest/deepglint"})
public class DeepGlintController
extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(DeepGlintController.class);
    @Autowired
    private IDeepGlintService deepGlintService;
    @Autowired
    private IPassengerService passengerService;

    @PostMapping(value={"/repo/register"})
    public AjaxResult registerPersonToCompareRepo(@RequestBody RegisterPersonToCompareRepoRequest request) {
        try {
            log.info("Received RegisterPersonToCompareRepoRequest: {}", (Object)request);
            if (request == null) {
                log.error("RegisterPersonToCompareRepoRequest is null");
                return AjaxResult.error((String)"\u8bf7\u6c42\u53c2\u6570\u4e3a\u7a7a");
            }
            RegisterPersonToCompareRepoResponse response = this.deepGlintService.registerPersonToCompareRepo(request);
            return AjaxResult.success((Object)response);
        }
        catch (Exception e) {
            log.error("\u6bd4\u5bf9\u5e93\u4eba\u5458\u6ce8\u518c\u5931\u8d25: {}", (Object)e.getMessage(), (Object)e);
            return AjaxResult.error((String)("\u6bd4\u5bf9\u5e93\u4eba\u5458\u6ce8\u518c\u5931\u8d25: " + e.getMessage()));
        }
    }

    @PostMapping(value={"/repo/register/list"})
    public AjaxResult listRegister(@RequestBody ListRegisterRequest request) {
        try {
            log.info("Received ListRegisterRequest: {}", (Object)request);
            if (request == null) {
                log.error("ListRegisterRequest is null");
                return AjaxResult.error((String)"\u8bf7\u6c42\u53c2\u6570\u4e3a\u7a7a");
            }
            ListRegisterResponse response = this.deepGlintService.listRegister(request);
            return AjaxResult.success((Object)response);
        }
        catch (Exception e) {
            log.error("\u67e5\u770b\u6ce8\u518c\u4eba\u5458\u5217\u8868\u5931\u8d25: {}", (Object)e.getMessage(), (Object)e);
            return AjaxResult.error((String)("\u67e5\u770b\u6ce8\u518c\u4eba\u5458\u5217\u8868\u5931\u8d25: " + e.getMessage()));
        }
    }

    @PostMapping(value={"/face/alert"})
    public AjaxResult queryFaceHistoryAlert(@RequestBody FaceHistoryAlertRequest request) {
        try {
            log.info("Received FaceHistoryAlertRequest: {}", (Object)request);
            if (request == null) {
                log.error("FaceHistoryAlertRequest is null");
                return AjaxResult.error((String)"\u8bf7\u6c42\u53c2\u6570\u4e3a\u7a7a");
            }
            FaceHistoryAlertResponse response = this.deepGlintService.queryFaceHistoryAlert(request);
            return AjaxResult.success((Object)response);
        }
        catch (Exception e) {
            log.error("\u4eba\u8138\u5386\u53f2\u544a\u8b66\u67e5\u8be2\u5931\u8d25: {}", (Object)e.getMessage(), (Object)e);
            return AjaxResult.error((String)("\u4eba\u8138\u5386\u53f2\u544a\u8b66\u67e5\u8be2\u5931\u8d25: " + e.getMessage()));
        }
    }

    @GetMapping(value={"/findPassage"})
    public AjaxResult findPassage(@RequestParam(value="reId") String reId) {
        try {
            log.info("Received findPassage request for reId: {}", (Object)reId);
            if (reId == null || reId.isEmpty()) {
                log.error("reId is null or empty");
                return AjaxResult.error((String)"\u4eba\u5458ID\u4e0d\u80fd\u4e3a\u7a7a");
            }
            RecognitionResult region = this.passengerService.findPassage(reId);
            if (region != null) {
                return AjaxResult.success((Object)region);
            }
            return AjaxResult.success((String)"\u672a\u627e\u5230\u8be5\u4eba\u5458\u7684\u6293\u62cd\u8bb0\u5f55");
        }
        catch (Exception e) {
            log.error("\u5bfb\u4eba\u67e5\u8be2\u5931\u8d25: {}", (Object)e.getMessage(), (Object)e);
            return AjaxResult.error((String)("\u5bfb\u4eba\u67e5\u8be2\u5931\u8d25: " + e.getMessage()));
        }
    }

    @PostMapping(value={"/debug/test"})
    public AjaxResult testRequestBody(@RequestBody Map<String, Object> request) {
        try {
            log.info("Debug - Received request body: {}", request);
            return AjaxResult.success(request);
        }
        catch (Exception e) {
            log.error("Debug - \u6d4b\u8bd5\u8bf7\u6c42\u4f53\u63a5\u6536\u5931\u8d25: {}", (Object)e.getMessage(), (Object)e);
            return AjaxResult.error((String)("Debug - \u6d4b\u8bd5\u8bf7\u6c42\u4f53\u63a5\u6536\u5931\u8d25: " + e.getMessage()));
        }
    }
}
