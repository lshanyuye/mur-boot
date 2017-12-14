package com.mur.service.menuItem;

import java.util.List;

import com.mur.domain.security.MenuItem;

public interface MenuItemService {

    /**
     * 查询菜单
     * 
     * @return
     */
    List<MenuItem> findMemu(String accounts);

    /**
     * 获取所有菜单资源
     * 
     * @return
     */
    List<MenuItem> findAllMenuItem();

}
