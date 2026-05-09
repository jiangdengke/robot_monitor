/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.code.kaptcha.Producer
 *  com.robotmonitor.common.config.RobotmonitorConfig
 *  com.robotmonitor.common.constant.Constants
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.core.redis.RedisCache
 *  com.robotmonitor.common.utils.sign.Base64
 *  com.robotmonitor.common.utils.uuid.IdUtils
 *  com.robotmonitor.system.service.ISysConfigService
 *  jakarta.annotation.Resource
 *  jakarta.servlet.http.HttpServletResponse
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.util.FastByteArrayOutputStream
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.robotmonitor.web.controller.common;

import com.google.code.kaptcha.Producer;
import com.robotmonitor.common.config.RobotmonitorConfig;
import com.robotmonitor.common.constant.Constants;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.redis.RedisCache;
import com.robotmonitor.common.utils.sign.Base64;
import com.robotmonitor.common.utils.uuid.IdUtils;
import com.robotmonitor.system.service.ISysConfigService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CaptchaController {
    @Resource(name="captchaProducer")
    private Producer captchaProducer;
    @Resource(name="captchaProducerMath")
    private Producer captchaProducerMath;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ISysConfigService configService;

    @GetMapping(value={"/captchaImage"})
    public AjaxResult getCode(HttpServletResponse response) throws IOException {
        AjaxResult ajax = AjaxResult.success();
        boolean captchaOnOff = this.configService.selectCaptchaOnOff();
        ajax.put("captchaOnOff", (Object)captchaOnOff);
        if (!captchaOnOff) {
            return ajax;
        }
        String uuid = IdUtils.simpleUUID();
        String verifyKey = "captcha_codes:" + uuid;
        String capStr = null;
        String code = null;
        BufferedImage image = null;
        String captchaType = RobotmonitorConfig.getCaptchaType();
        if ("math".equals(captchaType)) {
            String capText = this.captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = this.captchaProducerMath.createImage(capStr);
        } else if ("char".equals(captchaType)) {
            capStr = code = this.captchaProducer.createText();
            image = this.captchaProducer.createImage(capStr);
        }
        this.redisCache.setCacheObject(verifyKey, (Object)code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write((RenderedImage)image, "jpg", (OutputStream)os);
        }
        catch (IOException e) {
            return AjaxResult.error((String)e.getMessage());
        }
        ajax.put("uuid", (Object)uuid);
        ajax.put("img", (Object)Base64.encode((byte[])os.toByteArray()));
        return ajax;
    }
}
