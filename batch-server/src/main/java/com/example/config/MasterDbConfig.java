package com.example.config;

import com.example.annotation.MasterDb;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@MapperScan(
        basePackages = DatabaseConfig.BASE_PACKAGE,
        annotationClass = MasterDb.class,
        sqlSessionFactoryRef = "masterSqlSessionFactory"
)
public class MasterDbConfig extends DatabaseConfig {
    @Bean
    @ConfigurationProperties(prefix = "master.datasource")
    public DataSourceProperties masterDataSourceProp() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "master.datasource.tomcat")
    public DataSource masterDataSource() {
        return masterDataSourceProp().initializeDataSourceBuilder().build();
    }

    @Bean
    public SqlSessionFactory masterSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        configureSqlSessionFactory(sqlSessionFactoryBean, masterDataSource());
        return sqlSessionFactoryBean.getObject();
    }
}
