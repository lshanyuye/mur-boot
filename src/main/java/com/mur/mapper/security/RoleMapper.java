package com.mur.mapper.security;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.mur.domain.security.Role;
import com.mur.provider.role.RoleProvider;

public interface RoleMapper extends BaseMapper<Role> {

    @SelectProvider(type = RoleProvider.class, method = "listRole")
    List<Role> listPage(Pagination page, Role role);

    @Select("select r.* from role r,user_role_rel urr where urr.user_id=#{userId} and urr.role_id=r.id and r.is_deleted=0")
    List<Role> findRolesByUserId(String userId);

    @Select("select p.menu_item_id from permissions p where p.role_id=#{roleId}")
    List<String> findAllMenuItemByRoleId(String roleId);

    @Delete("delete from permissions where role_id=#{roleId}")
    void deletePermissionByRoleId(String roleId);

    @Select("select * from role where is_deleted=0 and code = #{code}")
    Role selectRoleByCode(String code);
}
