package com.mur.service.security;

import java.util.Set;

public interface PermissionService {

    /**
     * 根据账户查找权限
     * 
     * @param accounts
     * @return
     */
    Set<String> findPermission(String accounts);
}
