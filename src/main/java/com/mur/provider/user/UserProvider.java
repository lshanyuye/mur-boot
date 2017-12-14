package com.mur.provider.user;

import com.mur.domain.security.User;

public class UserProvider {

    public String listUser(User user) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select u.*,u.id user_id from user u where u.is_deleted=0 ");
        if (user.getEnabled() != null) {
            sql.append(" and u.enabled = #{enabled} ");
        }
        if (user.getAccounts() != null && user.getName() != null) {
            sql.append(" and (u.accounts like '%' #{accounts} '%' or u.name like '%' #{name} '%') ");
        } else if (user.getAccounts() != null) {
            sql.append(" and u.accounts like '%' #{accounts} '%' ");
        } else if (user.getName() != null) {
            sql.append(" and u.name like '%' #{name} '%' ");
        }
        sql.append(" order by u.created_time ");
        return sql.toString();
    }
}
