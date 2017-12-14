package com.mur.service.resource;

import java.util.List;

import com.mur.domain.resource.Resource;
import com.mur.enumrate.resource.ResourceEnum;

public interface ResourceService {

    void updateAllResource(List<Resource> resources);

    List<Resource> findResource(ResourceEnum type);

    List<Resource> findAllResource();

    List<Resource> findResourceByAccounts(String accounts);
}
