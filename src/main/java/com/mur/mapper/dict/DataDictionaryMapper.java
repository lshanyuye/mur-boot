package com.mur.mapper.dict;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.mur.domain.dict.DataDictionary;
import com.mur.provider.dict.DataDictionaryProvider;

public interface DataDictionaryMapper extends BaseMapper<DataDictionary> {

    @SelectProvider(type = DataDictionaryProvider.class, method = "listPage")
    List<DataDictionary> listPage(Pagination page, DataDictionary dict);

    @SelectProvider(type = DataDictionaryProvider.class, method = "listPage")
    List<DataDictionary> findDicts(DataDictionary dict);

    @Select("select * from data_dictionary where is_deleted=0 and code = #{code}")
    DataDictionary findDictByCode(String code);

    @Select("select * from data_dictionary where is_deleted=0 and pid = #{pid}")
    List<DataDictionary> findDictByPid(String pid);

    @SelectProvider(type=DataDictionaryProvider.class, method = "findDictByParentCode")
    List<DataDictionary> findDictByParentCode(String parentCode);

}
