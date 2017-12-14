package com.mur.mapper.resource;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mur.domain.resource.Resource;

public interface ResourceMapper extends BaseMapper<Resource> {

    @Delete("delete from resources")
    void deleteAll();

    @Select("select * from resources order by order_by")
    List<Resource> selectAll();

    @Select("select * from resources where type = #{type} order by order_by")
    List<Resource> selectByType(String type);

    @Select("select r.* from resources r where exists (select 1 from permissions p,role l,user u,user_role_rel urr where p.role_id=l.id and p.menu_item_id=r.id and urr.role_id = l.id and u.id=urr.user_id and u.accounts=#{accounts}) order by order_by")
    List<Resource> findResourceByAccounts(String accounts);
}
