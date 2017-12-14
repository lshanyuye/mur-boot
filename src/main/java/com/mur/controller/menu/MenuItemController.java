package com.mur.controller.menu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mur.domain.security.MenuItem;
import com.mur.domain.security.User;
import com.mur.service.menuItem.MenuItemService;
import com.mur.utils.shiro.ShiroUtils;

@Controller
@RequestMapping("menu")
public class MenuItemController {
    
    @Autowired
    private MenuItemService menuItemService;

    /**
     * 获取所有菜单资源
     * 
     * @return
     */
    @RequestMapping("getAllMenu")
    @ResponseBody
    public List<MenuItem> getAllMenu(){
        User user = ShiroUtils.getUser();
        return menuItemService.findMemu(user.getAccounts());
    }
    
    /**
     * 获取所有权限资源
     * 
     * @return
     */
    @RequestMapping("getAllMenuItem")
    @ResponseBody
    public List<MenuItem> getAllMenuItem(){
        return menuItemService.findAllMenuItem();
    }


}
