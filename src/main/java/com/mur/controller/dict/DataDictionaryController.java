package com.mur.controller.dict;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.mur.controller.Result;
import com.mur.domain.dict.DataDictionary;
import com.mur.domain.security.User;
import com.mur.service.dict.DataDictionaryService;
import com.mur.utils.shiro.ShiroUtils;

@Controller
@RequestMapping("dict")
public class DataDictionaryController {

    @Autowired
    private DataDictionaryService dataDictionaryService;

    @RequestMapping("/index")
    public String index() {
        return "dict/dict";
    }
    
    /**
     * 查询数据字典组分页数据
     * 
     * @param currentPage
     * @param pageSize
     * @param dict
     * @return
     */
    @PostMapping("/page")
    @ResponseBody
    public Page<DataDictionary> listGroupPage(@RequestParam("currentPage") int currentPage,
            @RequestParam("pageSize") int pageSize,
            DataDictionary dict) {
        dict.setIsGroup(Boolean.TRUE);
        return dataDictionaryService.listPage(currentPage, pageSize, dict);
    }

    /**
     * 查询数据字典
     * 
     * @param currentPage
     * @param pageSize
     * @param dict
     * @return
     */
    @PostMapping("/filter")
    @ResponseBody
    public List<DataDictionary> listChildPage(DataDictionary dict) {
        return dataDictionaryService.findDicts(dict);
    }

    /**
     * 新增、修改数据字典
     * 
     * @param dict
     * @return
     */
    @PostMapping("/")
    @ResponseBody
    public Result saveOrUpdateDict(@RequestBody DataDictionary dict) {
        User operator = ShiroUtils.getUser();
        dataDictionaryService.saveOrUpdateDataDict(dict, operator.getAccounts());
        return Result.ok("保存成功");
    }

    /**
     * 删除数据字典
     * 
     * @param dictId
     * @return
     */
    @DeleteMapping("/{dictId}")
    @ResponseBody
    public Result deleteDict(@PathVariable("dictId") String dictId) {
        dataDictionaryService.deleteDataDict(dictId);
        return Result.ok("删除成功");
    }

    @GetMapping("/{dictId}")
    @ResponseBody
    public DataDictionary findDictById(@PathVariable("dictId") String dictId) {
        return dataDictionaryService.findDictById(dictId);
    }

    @GetMapping("/filter/{parentCode}")
    @ResponseBody
    public List<DataDictionary> findDictByParentCode(@PathVariable("parentCode") String parentCode) {
        return dataDictionaryService.findDictByParentCode(parentCode);
    }
}
