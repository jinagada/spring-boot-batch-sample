package com.example.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

public abstract class DatabaseConfig {
    public static final String BASE_PACKAGE = "com.example.mapper";
    @Value("${mybatis.config-location}")
    private String COMFIG_LOCATION_PATH;
    @Value("${mybatis.mapper-locations}")
    public String MAPPER_LOCATION_PATH;

    protected void configureSqlSessionFactory(SqlSessionFactoryBean sessionFactoryBean, DataSource dataSource) throws
            IOException {
        sessionFactoryBean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactoryBean.setConfigLocation(resolver.getResource(COMFIG_LOCATION_PATH));
        sessionFactoryBean.setMapperLocations(resolver.getResources(MAPPER_LOCATION_PATH));
    }
}
