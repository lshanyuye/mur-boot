package com.mur.domain.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mur.domain.Domain;

public class User extends Domain {

    private String accounts;

    private String password;

    private String name;

    private Boolean enabled;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expireDate;

    @TableField(exist = false)
    private List<Role> roles = new ArrayList<>();

    @TableField(exist = false)
    private List<String> roleIds = new ArrayList<>();

    @TableField(exist = false)
    private String roleNames;

    public String getAccounts() {
        return accounts;
    }

    public void setAccounts(String accounts) {
        this.accounts = accounts;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {

        this.roles = roles;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getRoleIds() {
        for (Role role : roles) {
            roleIds.add(role.getId());
        }
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }

    public String getRoleNames() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < roles.size(); i++) {
            Role role = roles.get(i);
            if (i == roles.size() - 1) {
                sb.append(role.getName());
            } else {
                sb.append(role.getName()).append(",");
            }
        }
        roleNames = sb.toString();
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }
}