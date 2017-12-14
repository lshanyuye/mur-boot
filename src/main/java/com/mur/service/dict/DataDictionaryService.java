package com.mur.service.dict;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.mur.domain.dict.DataDictionary;

public interface DataDictionaryService {
    
    void saveOrUpdateDataDict(DataDictionary dict, String operator);
    
    void deleteDataDict(String dictId);
    
    DataDictionary findDictById(String dictId);

    Page<DataDictionary> listPage(int currentPage, int pageSize, DataDictionary dict);
    
    List<DataDictionary> findDicts(DataDictionary dict);

    List<DataDictionary> findDictByParentCode(String parentCode);
}
