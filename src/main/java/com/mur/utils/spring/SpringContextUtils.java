package com.mur.utils.spring;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.mur.service.security.UserService;

@Component
public class SpringContextUtils implements ApplicationContextAware{
    
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @SuppressWarnings("static-access")
    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.applicationContext = context;
    }
    
    public static Object getBean(String name) throws BeansException{
        return applicationContext.getBean(name);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Object getBean(String name, Class requiredType) throws BeansException{
        return applicationContext.getBean(name, requiredType);
    }
    
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException{
        return applicationContext.isSingleton(name);
    }

    public static Class getType(String name) throws NoSuchBeanDefinitionException{
        return applicationContext.getType(name);
    }
}
