/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.bot.domain;

public class GuideFinishResponse {
    public boolean success;
    public Long taskId;
    public String message;

    public GuideFinishResponse(boolean success, Long taskId, String message) {
        this.success = success;
        this.taskId = taskId;
        this.message = message;
    }

    public GuideFinishResponse() {
    }

    public boolean isSuccess() {
        return this.success;
    }

    public Long getTaskId() {
        return this.taskId;
    }

    public String getMessage() {
        return this.message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GuideFinishResponse)) {
            return false;
        }
        GuideFinishResponse other = (GuideFinishResponse)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.isSuccess() != other.isSuccess()) {
            return false;
        }
        Long this$taskId = this.getTaskId();
        Long other$taskId = other.getTaskId();
        if (this$taskId == null ? other$taskId != null : !((Object)this$taskId).equals(other$taskId)) {
            return false;
        }
        String this$message = this.getMessage();
        String other$message = other.getMessage();
        return !(this$message == null ? other$message != null : !this$message.equals(other$message));
    }

    protected boolean canEqual(Object other) {
        return other instanceof GuideFinishResponse;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isSuccess() ? 79 : 97);
        Long $taskId = this.getTaskId();
        result = result * 59 + ($taskId == null ? 43 : ((Object)$taskId).hashCode());
        String $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        return result;
    }

    public String toString() {
        return "GuideFinishResponse(success=" + this.isSuccess() + ", taskId=" + this.getTaskId() + ", message=" + this.getMessage() + ")";
    }
}
