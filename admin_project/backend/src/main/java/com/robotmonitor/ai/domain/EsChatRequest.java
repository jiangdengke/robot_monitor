/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.ai.domain;

public class EsChatRequest {
    private String problemSource;
    private String agent;
    private String key = "esChat:" + System.currentTimeMillis();
    private String longSentence;

    public String getProblemSource() {
        return this.problemSource;
    }

    public String getAgent() {
        return this.agent;
    }

    public String getKey() {
        return this.key;
    }

    public String getLongSentence() {
        return this.longSentence;
    }

    public void setProblemSource(String problemSource) {
        this.problemSource = problemSource;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setLongSentence(String longSentence) {
        this.longSentence = longSentence;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof EsChatRequest)) {
            return false;
        }
        EsChatRequest other = (EsChatRequest)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$problemSource = this.getProblemSource();
        String other$problemSource = other.getProblemSource();
        if (this$problemSource == null ? other$problemSource != null : !this$problemSource.equals(other$problemSource)) {
            return false;
        }
        String this$agent = this.getAgent();
        String other$agent = other.getAgent();
        if (this$agent == null ? other$agent != null : !this$agent.equals(other$agent)) {
            return false;
        }
        String this$key = this.getKey();
        String other$key = other.getKey();
        if (this$key == null ? other$key != null : !this$key.equals(other$key)) {
            return false;
        }
        String this$longSentence = this.getLongSentence();
        String other$longSentence = other.getLongSentence();
        return !(this$longSentence == null ? other$longSentence != null : !this$longSentence.equals(other$longSentence));
    }

    protected boolean canEqual(Object other) {
        return other instanceof EsChatRequest;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $problemSource = this.getProblemSource();
        result = result * 59 + ($problemSource == null ? 43 : $problemSource.hashCode());
        String $agent = this.getAgent();
        result = result * 59 + ($agent == null ? 43 : $agent.hashCode());
        String $key = this.getKey();
        result = result * 59 + ($key == null ? 43 : $key.hashCode());
        String $longSentence = this.getLongSentence();
        result = result * 59 + ($longSentence == null ? 43 : $longSentence.hashCode());
        return result;
    }

    public String toString() {
        return "EsChatRequest(problemSource=" + this.getProblemSource() + ", agent=" + this.getAgent() + ", key=" + this.getKey() + ", longSentence=" + this.getLongSentence() + ")";
    }
}
