package com.mur.controller.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.mur.controller.PageResult;
import com.mur.controller.Result;
import com.mur.domain.security.Role;
import com.mur.domain.security.User;
import com.mur.service.security.RoleService;
import com.mur.utils.shiro.ShiroUtils;

@Controller
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("index")
    public String index() {
        return "role/role";
    }

    @RequestMapping("listPage")
    @ResponseBody
    public PageResult listPage(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize, Role role) {
        Page<Role> page = roleService.listPage(currentPage, pageSize, role);
        return PageResult.buildPageResult(page);
    }

    @RequestMapping(value = "findRoleById/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public Result findRoleById(@PathVariable("roleId") String roleId) {
        Role role = roleService.findRoleById(roleId);
        return Result.ok("查询成功", role);
    }

    @RequestMapping(value = "/del/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public Result delRole(@PathVariable("roleId") String roleId) {
        roleService.delRole(roleId);
        return Result.ok("删除成功");
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public Result saveRole(Role role) {
        User operator = ShiroUtils.getUser();
        roleService.saveOrUpdateRole(role, operator.getAccounts());
        return Result.ok("保存成功");
    }

    @RequestMapping("/all")
    @ResponseBody
    public List<Role> findAllRoles() {
        return roleService.findAllRole();
    }

    @GetMapping("/permission/{roleId}")
    @ResponseBody
    public List<String> findAllMenuItemByRoleId(@PathVariable("roleId") String roleId) {
        return roleService.findAllMenuItemByRoleId(roleId);
    }

    @PostMapping("/permission/grant/{roleId}")
    @ResponseBody
    public Result roleGrant(@PathVariable("roleId") String roleId, @RequestBody List<String> menuItemIds) {
        roleService.roleGrant(roleId, menuItemIds);
        return Result.ok("授权成功");
    }
}
