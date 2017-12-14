package com.mur.controller;

import com.baomidou.mybatisplus.plugins.Page;

public class PageResult {
    
    public int total;
    
    public Object result;

    public PageResult(int total, Object result) {
        this.total = total;
        this.result = result;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
    
    public static PageResult buildPageResult(Page page) {
        return new PageResult(page.getTotal(), page.getRecords());
    }
}
