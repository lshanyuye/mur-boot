package com.mur.mapper.security;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PermissionMapper {

    @Select("select menu_item_id from permissions p,resource r where p.menu_item_id=r.id and role_id=#{roleId}")
    List<String> findPermissionByRoleId(String roleId);

    @Insert("insert into permissions(role_id, menu_item_id) values(#{roleId}, #{menuItemId})")
    void insertPermission(@Param("roleId") String roleId, @Param("menuItemId") String menuItemId);

    @Select("select p.menu_item_id from permissions p,resources r where p.menu_item_id=r.id and exists (select 1 from user_role_rel urr,user u where u.id=urr.user_id and urr.role_id=p.role_id and u.accounts=#{accounts})")
    List<String> findPermissionByAccounts(String accounts);

}
