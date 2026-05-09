/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.config.domain.deepglint.relation;

public class RepoAssociation {
    private String repoId;
    private Integer precise;
    private Boolean rankRegister;

    public RepoAssociation() {
    }

    public RepoAssociation(String repoId) {
        this.repoId = repoId;
    }

    public String getRepoId() {
        return this.repoId;
    }

    public void setRepoId(String repoId) {
        this.repoId = repoId;
    }

    public Integer getPrecise() {
        return this.precise;
    }

    public void setPrecise(Integer precise) {
        this.precise = precise;
    }

    public Boolean getRankRegister() {
        return this.rankRegister;
    }

    public void setRankRegister(Boolean rankRegister) {
        this.rankRegister = rankRegister;
    }
}
