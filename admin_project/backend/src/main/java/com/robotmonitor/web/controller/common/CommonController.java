/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.config.RobotmonitorConfig
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.utils.StringUtils
 *  com.robotmonitor.common.utils.file.FileUploadUtils
 *  com.robotmonitor.common.utils.file.FileUtils
 *  com.robotmonitor.framework.config.ServerConfig
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.robotmonitor.web.controller.common;

import com.robotmonitor.common.config.RobotmonitorConfig;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.common.utils.file.FileUploadUtils;
import com.robotmonitor.common.utils.file.FileUtils;
import com.robotmonitor.framework.config.ServerConfig;
import java.lang.invoke.CallSite;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value={"/common"})
public class CommonController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);
    @Autowired
    private ServerConfig serverConfig;
    private static final String FILE_DELIMETER = ",";

    @PostMapping(value={"/uploads"})
    public AjaxResult uploadFiles(List<MultipartFile> files) throws Exception {
        try {
            String filePath = RobotmonitorConfig.getUploadPath();
            ArrayList<CallSite> urls = new ArrayList<CallSite>();
            ArrayList<String> fileNames = new ArrayList<String>();
            ArrayList<String> newFileNames = new ArrayList<String>();
            ArrayList<String> originalFilenames = new ArrayList<String>();
            for (MultipartFile file : files) {
                String fileName = FileUploadUtils.upload((String)filePath, (MultipartFile)file);
                String url = this.serverConfig.getUrl() + fileName;
                urls.add((CallSite)((Object)url));
                fileNames.add(fileName);
                newFileNames.add(FileUtils.getName((String)fileName));
                originalFilenames.add(file.getOriginalFilename());
            }
            AjaxResult ajax = AjaxResult.success();
            ajax.put("urls", (Object)StringUtils.join(urls, (String)FILE_DELIMETER));
            ajax.put("fileNames", (Object)StringUtils.join(fileNames, (String)FILE_DELIMETER));
            ajax.put("newFileNames", (Object)StringUtils.join(newFileNames, (String)FILE_DELIMETER));
            ajax.put("originalFilenames", (Object)StringUtils.join(originalFilenames, (String)FILE_DELIMETER));
            return ajax;
        }
        catch (Exception e) {
            return AjaxResult.error((String)e.getMessage());
        }
    }
}
