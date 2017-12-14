package com.mur.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.mur.controller.PageResult;
import com.mur.controller.Result;
import com.mur.domain.security.User;
import com.mur.service.security.UserService;
import com.mur.utils.shiro.ShiroUtils;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "changePass", method = RequestMethod.POST)
    @ResponseBody
    public Result changePass(String pass, String checkPass, String checkPass2) {
        User user = ShiroUtils.getUser();
        userService.changePass(user.getAccounts(), pass, checkPass, checkPass2);
        return Result.ok("修改密码成功");
    }

    @RequestMapping("/index")
    public String userIndex() {
        return "user/user";
    }

    @RequestMapping("/queryPage")
    @ResponseBody
    public PageResult queryPage(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize, User user) {
        Page<User> page = userService.queryPage(currentPage, pageSize, user);
        return PageResult.buildPageResult(page);
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public Result saveUser(@RequestBody User user) {
        User operator = ShiroUtils.getUser();
        userService.saveOrUpdateUserAndRoleRel(user, user.getRoleIds(), operator.getAccounts());
        return Result.ok("保存成功");
    }

    @RequestMapping(value = "/del/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public Result delUser(@PathVariable("userId") String userId) {
        userService.delUser(userId);
        return Result.ok("删除成功");
    }

    @RequestMapping(value = "/findUserById/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public Result findUserById(@PathVariable("userId") String userId) {
        User user = userService.findUserById(userId);
        return Result.ok("查询成功", user);
    }

    @GetMapping("/role/{roleId}")
    @ResponseBody
    public List<User> findUserByRoleId(@PathVariable("roleId") String roleId) {
        return userService.findUserByRoleId(roleId);
    }
}
