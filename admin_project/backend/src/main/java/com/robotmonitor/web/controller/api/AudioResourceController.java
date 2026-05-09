/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.config.ConfigImg
 *  com.robotmonitor.config.service.IConfigImgService
 *  org.apache.logging.log4j.util.Strings
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.http.ContentDisposition
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.HttpStatusCode
 *  org.springframework.http.MediaType
 *  org.springframework.http.ResponseEntity
 *  org.springframework.util.MultiValueMap
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.robotmonitor.web.controller.api;

import com.robotmonitor.common.core.domain.config.ConfigImg;
import com.robotmonitor.config.service.IConfigImgService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/rest"})
public class AudioResourceController {
    private static final Logger log = LoggerFactory.getLogger(AudioResourceController.class);
    @Autowired
    private IConfigImgService configImgService;

    @GetMapping(value={"/image/config/{id}"})
    public ResponseEntity<byte[]> downloadImage(@PathVariable Long id) throws IOException {
        ConfigImg configImg = this.configImgService.selectConfigImgById(id);
        byte[] data = null;
        Object fileSuffix = "";
        if (null != configImg && Strings.isNotBlank((String)configImg.getImg())) {
            String base64Data = configImg.getImg().replaceFirst("(?i)^data:image/.*;base64,", "");
            if (configImg.getImg().matches("(?i)^data:image/.*")) {
                fileSuffix = "." + configImg.getImg().substring(configImg.getImg().indexOf("/") + 1, configImg.getImg().indexOf(";"));
            }
            data = Base64.getDecoder().decode(base64Data);
        }
        if (data == null) {
            return ResponseEntity.notFound().build();
        }
        HttpHeaders headers = new HttpHeaders();
        String mimeType = Files.probeContentType(Paths.get(configImg.getImgName() + (String)fileSuffix, new String[0]));
        headers.setContentType(MediaType.parseMediaType((String)mimeType));
        headers.setContentDisposition(ContentDisposition.attachment().filename(configImg.getImgName()).build());
        return new ResponseEntity((Object)data, (MultiValueMap)headers, (HttpStatusCode)HttpStatus.OK);
    }
}
