package com.mur.provider.dict;

import com.mur.domain.dict.DataDictionary;

public class DataDictionaryProvider {

    public String listPage(DataDictionary dict) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select * from data_dictionary ");
        sql.append(" where is_deleted = 0 ");
        if (dict.getEnabled() != null) {
            sql.append(" and enabled = #{enabled} ");
        }
        if (dict.getCode() != null && dict.getName() != null) {
            sql.append(" and (code like '%' #{code} '%' or name like '%' #{name} '%') ");
        }
        if (dict.getIsGroup() != null && dict.getIsGroup()) {
            sql.append(" and pid is null ");
        }

        if (dict.getPid() != null) {
            sql.append(" and pid = #{pid} ");
        }

        return sql.toString();
    }

    public String findDictByParentCode(String parentCode) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from data_dictionary d");
        sql.append(" where d.is_deleted = 0 and d.enabled = 1 ");
        sql.append(
                " and exists (select 1 from data_dictionary pd where d.pid=pd.id and pd.is_deleted = 0 and pd.code = #{parentCode}) ");
        return sql.toString();
    }
}
