package com.mur.enumrate.resource;

public enum ResourceEnum {

    MENU("0", "菜单"), BUTTON("1", "按钮");

    private String val;

    private String name;

    private ResourceEnum(String val, String name) {
        this.val = val;
        this.name = name;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
