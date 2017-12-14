package com.mur.service.security;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.mur.domain.security.Role;

public interface RoleService {

    Page<Role> listPage(int currentPage, int pageSize, Role role);

    void saveOrUpdateRole(Role role, String operator);

    Role findRoleById(String roleId);

    void delRole(String roleId);

    List<Role> findAllRole();

    List<String> findAllMenuItemByRoleId(String roleId);

    void roleGrant(String roleId, List<String> menuItemIds);
}
