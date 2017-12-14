package com.mur.domain.security;

import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.mur.domain.Domain;

public class Role extends Domain {

    private String code;

    private String name;

    @TableField(exist = false)
    private List<User> usrs;

    @TableField(exist = false)
    private List<String> userIds;

    @TableField(exist = false)
    private String userNames;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsrs() {
        return usrs;
    }

    public void setUsrs(List<User> usrs) {
        this.usrs = usrs;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public String getUserNames() {
        return userNames;
    }

    public void setUserNames(String userNames) {
        this.userNames = userNames;
    }
}
