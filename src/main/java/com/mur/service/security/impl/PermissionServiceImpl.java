package com.mur.service.security.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mur.mapper.security.PermissionMapper;
import com.mur.service.security.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Set<String> findPermission(String accounts) {
        if (StringUtils.isEmpty(accounts)) {
            return null;
        }
        List<String> permissions = permissionMapper.findPermissionByAccounts(accounts);
        if(permissions == null) {
            return null;
        }
        return new HashSet<>(permissions);
    }

}
