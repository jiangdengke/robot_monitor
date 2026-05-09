/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.robotmonitor.common.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.robotmonitor.common.core.domain.entity.SysDept;
import com.robotmonitor.common.core.domain.entity.SysMenu;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class TreeSelect
implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String label;
    @JsonInclude(value=JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect() {
    }

    public TreeSelect(SysDept dept) {
        this.id = dept.getDeptId();
        this.label = dept.getDeptName();
        this.children = dept.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public TreeSelect(SysMenu menu) {
        this.id = menu.getMenuId();
        this.label = menu.getMenuName();
        this.children = menu.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<TreeSelect> getChildren() {
        return this.children;
    }

    public void setChildren(List<TreeSelect> children) {
        this.children = children;
    }
}
