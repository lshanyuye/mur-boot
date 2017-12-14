package com.mur.domain.security;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {
    
    private String id;
    
    private String title;
    
    private String path;
    
    private String icon;
    
    private String type;
    
    private List<MenuItem> children = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<MenuItem> getChildren() {
        return children;
    }

    public void setChildren(List<MenuItem> children) {
        this.children = children;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
