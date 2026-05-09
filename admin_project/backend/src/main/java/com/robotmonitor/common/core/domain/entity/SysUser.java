/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.validation.constraints.Email
 *  jakarta.validation.constraints.NotBlank
 *  jakarta.validation.constraints.Size
 *  org.apache.commons.lang3.builder.ToStringBuilder
 *  org.apache.commons.lang3.builder.ToStringStyle
 */
package com.robotmonitor.common.core.domain.entity;

import com.robotmonitor.common.annotation.Excel;
import com.robotmonitor.common.annotation.Excels;
import com.robotmonitor.common.core.domain.BaseEntity;
import com.robotmonitor.common.core.domain.entity.SysDept;
import com.robotmonitor.common.core.domain.entity.SysRole;
import com.robotmonitor.common.xss.Xss;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SysUser
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @Excel(name="\u7528\u6237\u5e8f\u53f7", cellType=Excel.ColumnType.NUMERIC, prompt="\u7528\u6237\u7f16\u53f7")
    private Long userId;
    @Excel(name="\u90e8\u95e8\u7f16\u53f7", type=Excel.Type.IMPORT)
    private Long deptId;
    @Excel(name="\u767b\u5f55\u540d\u79f0")
    private String userName;
    @Excel(name="\u7528\u6237\u540d\u79f0")
    private String nickName;
    @Excel(name="\u7528\u6237\u90ae\u7bb1")
    private String email;
    @Excel(name="\u624b\u673a\u53f7\u7801")
    private String phonenumber;
    @Excel(name="\u7528\u6237\u6027\u522b", readConverterExp="0=\u7537,1=\u5973,2=\u672a\u77e5")
    private String sex;
    private String avatar;
    private String password;
    @Excel(name="\u5e10\u53f7\u72b6\u6001", readConverterExp="0=\u6b63\u5e38,1=\u505c\u7528")
    private String status;
    private String delFlag;
    @Excel(name="\u6700\u540e\u767b\u5f55IP", type=Excel.Type.EXPORT)
    private String loginIp;
    @Excel(name="\u6700\u540e\u767b\u5f55\u65f6\u95f4", width=30.0, dateFormat="yyyy-MM-dd HH:mm:ss", type=Excel.Type.EXPORT)
    private Date loginDate;
    @Excels(value={@Excel(name="\u90e8\u95e8\u540d\u79f0", targetAttr="deptName", type=Excel.Type.EXPORT), @Excel(name="\u90e8\u95e8\u8d1f\u8d23\u4eba", targetAttr="leader", type=Excel.Type.EXPORT)})
    private SysDept dept;
    private List<SysRole> roles;
    private Long[] roleIds;
    private Long[] postIds;
    private Long roleId;
    private String userType;

    public String getUserType() {
        return this.userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public SysUser() {
    }

    public SysUser(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isAdmin() {
        return SysUser.isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

    public Long getDeptId() {
        return this.deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    @Xss(message="\u7528\u6237\u6635\u79f0\u4e0d\u80fd\u5305\u542b\u811a\u672c\u5b57\u7b26")
    @Size(min=0, max=30, message="\u7528\u6237\u6635\u79f0\u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc730\u4e2a\u5b57\u7b26")
    public @Size(min=0, max=30, message="\u7528\u6237\u6635\u79f0\u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc730\u4e2a\u5b57\u7b26") String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Xss(message="\u7528\u6237\u8d26\u53f7\u4e0d\u80fd\u5305\u542b\u811a\u672c\u5b57\u7b26")
    @NotBlank(message="\u7528\u6237\u8d26\u53f7\u4e0d\u80fd\u4e3a\u7a7a")
    @Size(min=0, max=30, message="\u7528\u6237\u8d26\u53f7\u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc730\u4e2a\u5b57\u7b26")
    public @NotBlank(message="\u7528\u6237\u8d26\u53f7\u4e0d\u80fd\u4e3a\u7a7a") @Size(min=0, max=30, message="\u7528\u6237\u8d26\u53f7\u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc730\u4e2a\u5b57\u7b26") String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Email(message="\u90ae\u7bb1\u683c\u5f0f\u4e0d\u6b63\u786e")
    @Size(min=0, max=50, message="\u90ae\u7bb1\u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc750\u4e2a\u5b57\u7b26")
    public @Email(message="\u90ae\u7bb1\u683c\u5f0f\u4e0d\u6b63\u786e") @Size(min=0, max=50, message="\u90ae\u7bb1\u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc750\u4e2a\u5b57\u7b26") String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Size(min=0, max=11, message="\u624b\u673a\u53f7\u7801\u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc711\u4e2a\u5b57\u7b26")
    public @Size(min=0, max=11, message="\u624b\u673a\u53f7\u7801\u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc711\u4e2a\u5b57\u7b26") String getPhonenumber() {
        return this.phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelFlag() {
        return this.delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getLoginIp() {
        return this.loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginDate() {
        return this.loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public SysDept getDept() {
        return this.dept;
    }

    public void setDept(SysDept dept) {
        this.dept = dept;
    }

    public List<SysRole> getRoles() {
        return this.roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }

    public Long[] getRoleIds() {
        return this.roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }

    public Long[] getPostIds() {
        return this.postIds;
    }

    public void setPostIds(Long[] postIds) {
        this.postIds = postIds;
    }

    public Long getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String toString() {
        return new ToStringBuilder((Object)this, ToStringStyle.MULTI_LINE_STYLE).append("userId", (Object)this.getUserId()).append("deptId", (Object)this.getDeptId()).append("userName", (Object)this.getUserName()).append("nickName", (Object)this.getNickName()).append("email", (Object)this.getEmail()).append("phonenumber", (Object)this.getPhonenumber()).append("sex", (Object)this.getSex()).append("avatar", (Object)this.getAvatar()).append("password", (Object)this.getPassword()).append("status", (Object)this.getStatus()).append("delFlag", (Object)this.getDelFlag()).append("loginIp", (Object)this.getLoginIp()).append("loginDate", (Object)this.getLoginDate()).append("createBy", (Object)this.getCreateBy()).append("createTime", (Object)this.getCreateTime()).append("updateBy", (Object)this.getUpdateBy()).append("updateTime", (Object)this.getUpdateTime()).append("remark", (Object)this.getRemark()).append("dept", (Object)this.getDept()).toString();
    }
}
