package com.mur.service.dict.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.mur.domain.dict.DataDictionary;
import com.mur.exception.BusinessException;
import com.mur.mapper.dict.DataDictionaryMapper;
import com.mur.service.dict.DataDictionaryService;

@Service
public class DataDictionaryServiceImpl implements DataDictionaryService {

    @Autowired
    private DataDictionaryMapper dataDictionaryMapper;

    @Override
    public Page<DataDictionary> listPage(int currentPage, int pageSize, DataDictionary dict) {
        Page<DataDictionary> page = new Page<>(currentPage, pageSize);
        List<DataDictionary> list = dataDictionaryMapper.listPage(page, dict);
        page.setRecords(list);
        return page;
    }

    @Override
    public void saveOrUpdateDataDict(DataDictionary dict, String operator) {
        if (StringUtils.isBlank(dict.getCode())) {
            throw new BusinessException("编码不能为空");
        }
        if (StringUtils.isBlank(dict.getName())) {
            throw new BusinessException("名称不能为空");
        }
        DataDictionary existedDict = dataDictionaryMapper.findDictByCode(dict.getCode());
        if (existedDict != null && !existedDict.getId().equals(dict.getId())) {
            throw new BusinessException("编码已经存在，不能重复添加");
        }
        dict.update(operator);
        if (StringUtils.isBlank(dict.getId())) {
            dataDictionaryMapper.insert(dict);
        } else {
            if (!existedDict.getEnabled().equals(dict.getEnabled())) {
                updateChildDict(dict, operator);
            }
            dataDictionaryMapper.updateById(dict);
        }

    }

    /**
     * 更新数据字典子元素状态
     * 
     * @param dict
     * @param operator
     */
    private void updateChildDict(DataDictionary dict, String operator) {
        List<DataDictionary> childDict = dataDictionaryMapper.findDictByPid(dict.getId());
        if (childDict != null && !childDict.isEmpty()) {
            childDict.forEach((DataDictionary child) -> {
                child.setEnabled(dict.getEnabled());
                child.update(operator);
                dataDictionaryMapper.updateById(child);
            });
        }
    }

    @Override
    public void deleteDataDict(String dictId) {
        List<DataDictionary> dicts = dataDictionaryMapper.findDictByPid(dictId);
        if (dicts != null && !dicts.isEmpty()) {
            throw new BusinessException("数据字典存在子元素，不能删除");
        }
        dataDictionaryMapper.deleteById(dictId);
    }

    @Override
    public DataDictionary findDictById(String dictId) {
        if (StringUtils.isBlank(dictId)) {
            return null;
        }
        return dataDictionaryMapper.selectById(dictId);
    }

    @Override
    public List<DataDictionary> findDicts(DataDictionary dict) {
        return dataDictionaryMapper.findDicts(dict);
    }

    @Override
    public List<DataDictionary> findDictByParentCode(String parentCode) {
        if (StringUtils.isBlank(parentCode)) {
            return null;
        }
        return dataDictionaryMapper.findDictByParentCode(parentCode);
    }
}
