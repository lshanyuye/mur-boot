package com.mur.service.security.impl;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mur.domain.security.User;
import com.mur.service.security.PermissionService;
import com.mur.service.security.UserService;
import com.mur.utils.EncryptUtils;
import com.mur.utils.spring.SpringContextUtils;

public class UserRealm extends AuthorizingRealm {
    
    private Logger logger = LoggerFactory.getLogger(UserRealm.class);
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private PermissionService permissionService;

    /**
     * 获取身份信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        logger.info("get accounts");
        String accounts = (String)principal.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        if (permissionService == null) {
            permissionService = (PermissionService) SpringContextUtils.getBean("permissionService");
        }
        //设置用户权限
        authorizationInfo.setStringPermissions(permissionService.findPermission(accounts));
        
        return authorizationInfo;
    }

    @Override
    public void setCachingEnabled(boolean cachingEnabled) {
        super.setCachingEnabled(false);
    }

    /**
     * 身份验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        String accounts = userToken.getUsername();
        if(userService == null) {
            userService = (UserService) SpringContextUtils.getBean("userServiceImpl");
        }
        User user = userService.findUserByAccounts(accounts);
        if(user == null) {
            throw new UnknownAccountException("账户不存在");
        }
        if (!user.getEnabled()) {
            throw new LockedAccountException("帐号已被锁定，无法登录");
        }
        
        AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getAccounts(), user.getPassword(), getName());
        
        return authenticationInfo;
    }


    class CredentialsMatcher extends SimpleCredentialsMatcher {
        @Override
        public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
            UsernamePasswordToken uToken = (UsernamePasswordToken) token;
            String inPassword = new String(uToken.getPassword());
            return EncryptUtils.encrypt(inPassword).equals(info.getCredentials());
        }

    }

    @Override
    public org.apache.shiro.authc.credential.CredentialsMatcher getCredentialsMatcher() {
        return new CredentialsMatcher();
    }

}
