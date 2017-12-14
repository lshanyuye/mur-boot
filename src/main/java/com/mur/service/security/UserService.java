package com.mur.service.security;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.mur.domain.security.User;

public interface UserService {

    /**
     * 保存或更新用户信息
     * 
     * @param user
     * @param operator
     * @return
     */
    User saveOrUpdateUser(User user, String operator);

    User saveOrUpdateUserAndRoleRel(User user, List<String> roleIds, String operator);

    User findUserByAccounts(String accounts);

    void changePass(String accounts, String pass, String checkPass, String checkPass2);

    Page<User> queryPage(int currentPage, int pageSize, User user);

    void delUser(String userId);

    User findUserById(String userId);

    List<User> findUserByRoleId(String roleId);
}
