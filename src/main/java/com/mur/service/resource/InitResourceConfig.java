package com.mur.service.resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.mur.domain.resource.Resource;

/**
 * 初始化菜单资源
 * 
 * @author MuR
 * @email lshanyuye@qq.com
 */
@Component
public class InitResourceConfig implements InitializingBean {

    Logger logger = LoggerFactory.getLogger(InitResourceConfig.class);

    private List<Resource> resources = new ArrayList<>();

    @Value("${system.isResetMenu}")
    private Boolean isResetMenu = Boolean.FALSE;

    @Autowired
    private ResourceService resourceService;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (isResetMenu) {
            if (logger.isDebugEnabled()) {
                logger.debug("reset resource started...");
            }
            this.refreshResource();
            resourceService.updateAllResource(resources);
            if (logger.isDebugEnabled()) {
                logger.debug("reset resource end...");
            }
        }
    }

    public void refreshResource() throws FileNotFoundException, DocumentException {
        File file = ResourceUtils.getFile("classpath:menu.xml");
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        Element menuBoot = document.getRootElement();
        Iterator it = menuBoot.elementIterator();
        while (it.hasNext()) {
            Element subElement = (Element) it.next();
            buildResource(subElement, null);
        }
    }

    private void buildResource(Element element, Resource parent) {
        Resource resource = new Resource();
        List<Attribute> attributes = element.attributes();
        for (Attribute attribute : attributes) {
            setVal(resource, attribute.getName(), attribute.getValue());
            if (parent != null) {
                resource.setPid(parent.getId());
            }
        }
        resources.add(resource);
        Iterator it = element.elementIterator();
        while (it.hasNext()) {
            Element subElement = (Element) it.next();
            buildResource(subElement, resource);
        }
    }

    private void setVal(Resource resource, String fieldName, Object val) {
        try {
            Field field = resource.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Class clazz = field.getType();
            if (Integer.class.equals(clazz)) {
                val = Integer.valueOf(val.toString());
            }
            field.set(resource, val);
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
