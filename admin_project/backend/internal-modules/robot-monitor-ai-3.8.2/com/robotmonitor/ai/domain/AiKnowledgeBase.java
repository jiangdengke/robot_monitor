/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Excel
 *  com.robotmonitor.common.core.domain.BaseEntity
 *  org.apache.commons.lang3.builder.ToStringBuilder
 *  org.apache.commons.lang3.builder.ToStringStyle
 *  org.springframework.ai.document.Document
 */
package com.robotmonitor.ai.domain;

import com.robotmonitor.common.annotation.Excel;
import com.robotmonitor.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.ai.document.Document;

public class AiKnowledgeBase
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    @Excel(name="\u4fdd\u5b58\u7684\u77e5\u8bc6\u5185\u5bb9")
    private String content;
    @Excel(name="\u77e5\u8bc6\u6765\u6e90")
    private String source;
    @Excel(name="\u7c7b\u578b\uff0c1\uff1a\u624b\u5de5\u5bfc\u5165\uff1b2\uff1a\u751f\u6210\u7684\u95ee\u7b54\u5bf9\uff1b3\uff1a\u6587\u6863\u5bfc\u5165")
    private String type;
    @Excel(name="\u72b6\u6001\uff0c1\uff1a\u65b0\u63d2\u5165\uff1b2\uff1a\u5411\u91cf\u5316\u4e2d\uff0c3\uff1a\u5df2\u5411\u91cf\u5316\uff1b4\uff1a\u5411\u91cf\u5316\u5931\u8d25\uff1b5\uff1a\u751f\u6210\u95ee\u7b54\u5bf9\u4e2d\uff1b6\uff1a\u5df2\u751f\u6210\u95ee\u7b54\u5bf9\uff1b7\uff1a\u751f\u6210\u95ee\u7b54\u5bf9\u5931\u8d25")
    private String status;
    @Excel(name="\u5ba1\u6838\u72b6\u6001(0\uff1a\u672a\u5ba1\u6838\u4e0d\u4f7f\u7528\uff1b1\uff1a\u5df2\u5ba1\u6838\u5e76\u4f7f\u7528)")
    private String enable;
    @Excel(name="\u95ee\u7b54\u5bf9\u751f\u6210\u6240\u4f7f\u7528\u7684\u6e90ID")
    private Long fId;
    @Excel(name="\u5411\u91cf\u6570\u636e\u5e93\u4e2d\u7684ID")
    private String vectorId;
    private Document document;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return this.source;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getEnable() {
        return this.enable;
    }

    public void setfId(Long fId) {
        this.fId = fId;
    }

    public Long getfId() {
        return this.fId;
    }

    public String getVectorId() {
        return this.vectorId;
    }

    public void setVectorId(String vectorId) {
        this.vectorId = vectorId;
    }

    public Document getDocument() {
        return this.document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String toString() {
        return new ToStringBuilder((Object)this, ToStringStyle.MULTI_LINE_STYLE).append("id", (Object)this.getId()).append("content", (Object)this.getContent()).append("source", (Object)this.getSource()).append("type", (Object)this.getType()).append("status", (Object)this.getStatus()).append("enable", (Object)this.getEnable()).append("fId", (Object)this.getfId()).append("vectorId", (Object)this.getVectorId()).append("createBy", (Object)this.getCreateBy()).append("createTime", (Object)this.getCreateTime()).append("updateBy", (Object)this.getUpdateBy()).append("updateTime", (Object)this.getUpdateTime()).append("remark", (Object)this.getRemark()).toString();
    }
}
