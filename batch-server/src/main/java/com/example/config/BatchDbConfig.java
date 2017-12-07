package com.example.config;

import com.example.annotation.BatchDb;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
@MapperScan(
        basePackages = DatabaseConfig.BASE_PACKAGE,
        annotationClass = BatchDb.class,
        sqlSessionFactoryRef = "batchSqlSessionFactory"
)
public class BatchDbConfig extends DatabaseConfig {
    @Primary
    @Bean
    @ConfigurationProperties(prefix = "batch.datasource")
    public DataSourceProperties batchDataSourceProp() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "batchDataSource")
    @ConfigurationProperties(prefix = "batch.datasource.tomcat")
    public DataSource batchDataSource() {
        return batchDataSourceProp().initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean
    public SqlSessionFactory batchSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        configureSqlSessionFactory(sqlSessionFactoryBean, batchDataSource());
        return sqlSessionFactoryBean.getObject();
    }
}
