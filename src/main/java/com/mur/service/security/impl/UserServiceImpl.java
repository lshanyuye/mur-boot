package com.mur.service.security.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.mur.constants.ValidityConstants;
import com.mur.domain.security.User;
import com.mur.exception.BusinessException;
import com.mur.mapper.security.UserMapper;
import com.mur.service.security.UserService;
import com.mur.utils.EncryptUtils;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    /**
     * 保存或更新用户信息
     */
    @Override
    @Transactional
    public User saveOrUpdateUser(User user, String operator) {
        if (StringUtils.isEmpty(operator)) {
            throw new BusinessException("operator is not null");
        } else if (StringUtils.isEmpty(user.getAccounts())) {
            throw new BusinessException("user.accounts is not null");
        } else if (StringUtils.isEmpty(user.getName())) {
            throw new BusinessException("user.name is not uull");
        }
        // 验证用户名是否存在
        User existsUser = findUserByAccounts(user.getAccounts());
        if (existsUser != null && !existsUser.getId().equals(user.getId())) {
            throw new BusinessException(String.format("账户 [%s]已存在", user.getAccounts()));
        }
        user.update(operator);
        if (StringUtils.isEmpty(user.getId())) {// 新增
            if (logger.isDebugEnabled()) {
                logger.debug("add user {} by {}", user.getAccounts(), operator);
            }
            user.setEnabled(ValidityConstants.ENABLED);
            user.setPassword(EncryptUtils.encrypt("123"));
            userMapper.insert(user);
        } else {// 修改
            if (logger.isDebugEnabled()) {
                logger.debug("update user {} by {}", user.getAccounts(), operator);
            }
            userMapper.updateById(user);
        }
        return user;
    }

    /**
     * 根据账户查找用户
     */
    @Override
    public User findUserByAccounts(String accounts) {
        if (logger.isDebugEnabled()) {
            logger.debug("find user by accounts {}", accounts);
        }
        if (StringUtils.isEmpty(accounts)) {
            return null;
        }
        return userMapper.selectUserByAccounts(accounts);
    }

    /**
     * 修改密码
     */
    @Override
    public void changePass(String accounts, String pass, String checkPass, String checkPass2) {
        User entity = new User();
        entity.setAccounts(accounts);
        User user = userMapper.selectOne(entity);
        if (user == null) {
            throw new BusinessException("用户在系统中不存在");
        } else if (StringUtils.isEmpty(pass)) {
            throw new BusinessException("原始密码不能为空");
        } else if (StringUtils.isEmpty(checkPass)) {
            throw new BusinessException("新密码不能为空");
        } else if (StringUtils.isEmpty(checkPass2)) {
            throw new BusinessException("再次确认密码不能为空");
        } else if (pass.equals(checkPass)) {
            throw new BusinessException("新密码不能与原始密码相同");
        } else if (!checkPass2.equals(checkPass)) {
            throw new BusinessException("两次输入密码不一致");
        } else if (!EncryptUtils.encrypt(pass).equals(user.getPassword())) {
            throw new BusinessException("原始密码错误");
        }
        user.setPassword(EncryptUtils.encrypt(checkPass));
        userMapper.updateById(user);
    }

    @Override
    public Page<User> queryPage(int currentPage, int pageSize, User user) {
        Page<User> page = new Page<>(currentPage, pageSize);
        List<User> users = userMapper.pageUser(page, user);
        page.setRecords(users);
        return page;
    }

    @Override
    public void delUser(String userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new BusinessException("用户ID不能为空");
        }
        userMapper.deleteById(userId);
    }

    @Override
    public User findUserById(String userId) {
        if (StringUtils.isEmpty(userId)) {
            return null;
        }
        return userMapper.findUserById(userId);
    }

    @Override
    public List<User> findUserByRoleId(String roleId) {
        if (StringUtils.isEmpty(roleId)) {
            return null;
        }
        return userMapper.findUserByRoleId(roleId);
    }

    /**
     * 新增、更新用户信息和用户角色关系表
     */
    @Transactional
    @Override
    public User saveOrUpdateUserAndRoleRel(User user, List<String> roleIds, String operator) {
        User u = saveOrUpdateUser(user, operator);
        updateUserRoleRelByUser(user.getId(), roleIds);
        return u;
    }

    private void updateUserRoleRelByUser(String userId, List<String> roleIds) {
        userMapper.deleteUserRoleRelByUserId(userId);
        if (roleIds != null) {
            for (String roleId : roleIds) {
                userMapper.insertUserRoleRel(roleId, userId);
            }
        }
    }

}
