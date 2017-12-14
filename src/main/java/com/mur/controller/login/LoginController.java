package com.mur.controller.login;


import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mur.controller.Result;
import com.mur.domain.security.User;
import com.mur.service.security.UserService;




@Controller
public class LoginController {
    Logger logger = LoggerFactory.getLogger(LoginController.class);
    
    @Autowired
    private UserService userService;
    
    @RequestMapping("login")
    public String loginPage() {
        return "login";
    }
    
    @RequestMapping("/")
    public String home() {
        return "login";
    }
    
    /**
     * 登录
     * @param accounts
     * @param password
     * @param session
     * @return
     */
    @RequestMapping("ajaxLogin")
    @ResponseBody
    public Result login(String accounts, String password, HttpSession session){
        UsernamePasswordToken token = new UsernamePasswordToken(accounts, password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        User user = userService.findUserByAccounts(accounts);
        session.setAttribute("user", user);

        return Result.ok("登录成功");
    }
    
    @RequestMapping("index")
    public String index() {
        return "index";
    }
    
}
