/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.builder.ToStringBuilder
 *  org.apache.commons.lang3.builder.ToStringStyle
 */
package com.robotmonitor.common.core.domain.config;

import com.robotmonitor.common.annotation.Excel;
import com.robotmonitor.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class MessageLog
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    @Excel(name="\u6d88\u606f\u6807\u9898")
    private String title;
    @Excel(name="\u6d88\u606f\u5185\u5bb9")
    private String content;
    @Excel(name="\u6d88\u606f\u72b6\u6001", readConverterExp="0=\uff1a\u521b\u5efa\uff1b1\uff1a\u5df2\u8bfb\uff1b2\uff1a\u5df2\u5904\u7406\uff1b3\uff1a\u65e0\u9700\u5904\u7406\uff1b")
    private String status;
    @Excel(name="\u6d88\u606f\u6765\u6e90")
    private String source;
    @Excel(name="\u8d35\u5bbe\u5ba4code")
    private String roomCode;
    @Excel(name="\u6d88\u606f\u5904\u7406\u8005")
    private String processor;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return this.source;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getRoomCode() {
        return this.roomCode;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getProcessor() {
        return this.processor;
    }

    public String toString() {
        return new ToStringBuilder((Object)this, ToStringStyle.MULTI_LINE_STYLE).append("id", (Object)this.getId()).append("title", (Object)this.getTitle()).append("content", (Object)this.getContent()).append("status", (Object)this.getStatus()).append("source", (Object)this.getSource()).append("roomCode", (Object)this.getRoomCode()).append("createTime", (Object)this.getCreateTime()).append("processor", (Object)this.getProcessor()).append("updateTime", (Object)this.getUpdateTime()).toString();
    }
}
