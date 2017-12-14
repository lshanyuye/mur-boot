package com.mur.service.security.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mur.domain.security.Role;
import com.mur.exception.BusinessException;
import com.mur.mapper.security.PermissionMapper;
import com.mur.mapper.security.RoleMapper;
import com.mur.service.security.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionsMapper;

    @Override
    public Page<Role> listPage(int currentPage, int pageSize, Role role) {
        Page<Role> page = new Page<>(currentPage, pageSize);
        List<Role> roles = roleMapper.listPage(page, role);
        page.setRecords(roles);
        return page;
    }

    @Override
    @Transactional
    public void saveOrUpdateRole(Role role, String operator) {
        if (StringUtils.isEmpty(operator)) {
            throw new BusinessException("操作人不能为空");
        } else if (StringUtils.isEmpty(role.getCode())) {
            throw new BusinessException("角色编码不能为空");
        } else if (StringUtils.isEmpty(role.getName())) {
            throw new BusinessException("角色名称不能为空");
        }
        Role isExistsRole = roleMapper.selectRoleByCode(role.getCode());
        if (isExistsRole != null && !isExistsRole.getId().equals(role.getId())) {
            throw new BusinessException("角色编码已存在");
        } else if (StringUtils.isEmpty(role.getId())) {// 新增
            role.update(operator);
            roleMapper.insert(role);
        } else {// 修改
            role.update(operator);
            roleMapper.updateById(role);
        }

    }

    @Override
    public Role findRoleById(String roleId) {
        if (StringUtils.isEmpty(roleId)) {
            throw new BusinessException("ID不能为空");
        }
        return roleMapper.selectById(roleId);
    }

    @Override
    @Transactional
    public void delRole(String roleId) {
        if (StringUtils.isEmpty(roleId)) {
            throw new BusinessException("ID不能为空");
        }
        roleMapper.deleteById(roleId);
    }

    @Override
    public List<Role> findAllRole() {
        return roleMapper.selectList(new EntityWrapper<Role>());
    }

    @Override
    public List<String> findAllMenuItemByRoleId(String roleId) {
        return roleMapper.findAllMenuItemByRoleId(roleId);
    }

    @Override
    @Transactional
    public void roleGrant(String roleId, List<String> menuItemIds) {
        roleMapper.deletePermissionByRoleId(roleId);
        for (String menuItemId : menuItemIds) {
            permissionsMapper.insertPermission(roleId, menuItemId);
        }
    }

}
