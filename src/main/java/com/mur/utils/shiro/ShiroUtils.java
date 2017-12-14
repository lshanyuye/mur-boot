package com.mur.utils.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.mur.domain.security.User;

public class ShiroUtils {
    
    public static User getUser() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getSession().getAttribute("user");
        return user;
    }
}
