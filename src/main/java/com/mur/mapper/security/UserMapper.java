package com.mur.mapper.security;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.mur.domain.security.User;
import com.mur.provider.user.UserProvider;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    // @Select("select u.*,u.id user_id from user u where u.is_deleted=0")
    @SelectProvider(type = UserProvider.class, method = "listUser")
    @Results({
            @Result(property = "roles", column = "user_id", many = @Many(select = "com.mur.mapper.security.RoleMapper.findRolesByUserId")) })
    List<User> pageUser(Pagination page, User user);

    @Select("select u.*,urr.role_id from user u,user_role_rel urr where urr.role_id=#{roleId} and u.is_deleted=0 and urr.user_id=u.id")
    @Results({
            @Result(property = "roles", column = "role_id", many = @Many(select = "com.mur.mapper.security.RoleMapper.selectById")) })
    List<User> findUserByRoleId(String roleId);

    @Insert("insert into user_role_rel(role_id, user_id) values(#{roleId},#{userId}) ")
    void insertUserRoleRel(@Param("roleId") String roleId, @Param("userId") String userId);

    @Delete("delete from user_role_rel where user_id = #{userId}")
    void deleteUserRoleRelByUserId(String userId);

    @Select("select u.*, u.id user_id from user u where u.is_deleted=0 and u.id = #{userId}")
    @Results({
            @Result(property = "roles", column = "user_id", many = @Many(select = "com.mur.mapper.security.RoleMapper.findRolesByUserId")) })
    User findUserById(String userId);

    @Select("select * from user where accounts = #{accounts}")
    User selectUserByAccounts(String accounts);
}