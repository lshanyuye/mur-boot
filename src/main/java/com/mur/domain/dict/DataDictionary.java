package com.mur.domain.dict;

import com.baomidou.mybatisplus.annotations.TableField;
import com.mur.domain.Domain;

public class DataDictionary extends Domain {

    private String code;

    private String name;

    private String pid;

    private Boolean enabled;

    @TableField(exist = false)
    private Boolean isGroup;

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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(Boolean isGroup) {
        this.isGroup = isGroup;
    }

}
