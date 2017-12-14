package com.mur.config;

import java.util.Properties;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;


/**
 * Mybatis配置
 * @author MuR
 * @email lshanyuye@qq.com
 */
@Configuration
@MapperScan("com.mur.mapper")
public class MyBatisConfig {
    
    /**
     * Mybatis分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor pagination() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        page.setLocalPage(true);
        return  page;
    }
}
