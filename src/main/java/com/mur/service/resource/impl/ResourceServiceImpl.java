package com.mur.service.resource.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mur.domain.resource.Resource;
import com.mur.enumrate.resource.ResourceEnum;
import com.mur.exception.BusinessException;
import com.mur.mapper.resource.ResourceMapper;
import com.mur.service.resource.ResourceService;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    /**
     * 更新所有的资源
     */
    @Override
    @Transactional
    public void updateAllResource(List<Resource> resources) {
        // 1.删除原有的资源
        resourceMapper.deleteAll();
        // 2.插入新的资源
        resources.forEach((Resource resource) -> {
            resourceMapper.insert(resource);
        });
    }

    /**
     * 根据资源类型查询资源
     */
    @Override
    public List<Resource> findResource(ResourceEnum type) {
        if (type == null) {
            throw new BusinessException("查询参数不能为空");
        }
        return resourceMapper.selectByType(type.getVal());
    }

    /**
     * 查询所有资源
     */
    @Override
    public List<Resource> findAllResource() {
        return resourceMapper.selectAll();
    }

    /**
     * 根据账户查询所有资源
     */
    @Override
    public List<Resource> findResourceByAccounts(String accounts) {
        return resourceMapper.findResourceByAccounts(accounts);
    }

}
