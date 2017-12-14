package com.mur.provider.role;

import com.mur.domain.security.Role;

public class RoleProvider {

    public String listRole(Role role) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select * from role where is_deleted=0 ");
        if (role.getCode() != null && role.getName() != null) {
            sql.append("and (code like '%' #{code} '%' or name like '%' #{name} '%') ");
        } else if (role.getCode() != null) {
            sql.append("and code like '%' #{code} '%' ");
        } else if (role.getName() != null) {
            sql.append("and name like '%' #{name} '%' ");
        }
        return sql.toString();
    }
}
