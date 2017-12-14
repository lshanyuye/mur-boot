package com.mur.service.menuItem.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mur.domain.resource.Resource;
import com.mur.domain.security.MenuItem;
import com.mur.exception.BusinessException;
import com.mur.service.menuItem.MenuItemService;
import com.mur.service.resource.ResourceService;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    @Autowired
    private ResourceService resourceService;
    /**
     * 查询菜单
     */
    @Override
    public List<MenuItem> findMemu(String accounts) {
        List<Resource> resources = new ArrayList<>();
        resources = resourceService.findResourceByAccounts(accounts);
        return buildMenuItem(resources);
    }

    /**
     * 查询所有的菜单资源，包括按钮
     */
    @Override
    public List<MenuItem> findAllMenuItem() {
        List<Resource> resources = resourceService.findAllResource();
        Map<String, List<MenuItem>> menuItemChildren = new HashMap<>();
        return buildMenuItem(resources);
    }

    private List<MenuItem> buildMenuItem(List<Resource> resources) {
        List<MenuItem> rootMenuItem = new ArrayList<>();
        resources.forEach((Resource resource) -> {
            if (StringUtils.isEmpty(resource.getPid())) {
                MenuItem menuItem = createMenuItem(resource);
                rootMenuItem.add(menuItem);
                buildMenuItemChildren(menuItem, resources);
            }
        });
        return rootMenuItem;
    }

    private List<MenuItem> buildMenuItemChildren(MenuItem parentMenuItem, List<Resource> resources) {
        List<MenuItem> children = new ArrayList<>();
        resources.forEach((Resource resource) -> {
            if (parentMenuItem.getId().equals(resource.getPid())) {
                MenuItem menuItem = createMenuItem(resource);
                children.add(menuItem);
                buildMenuItemChildren(menuItem, resources);
            }
        });
        parentMenuItem.setChildren(children);
        return children;
    }

    private MenuItem createMenuItem(Resource resource) {
        MenuItem menuItem = new MenuItem();
        try {
            BeanUtils.copyProperties(menuItem, resource);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new BusinessException("菜单解析失败");
        }
        return menuItem;
    }
}
