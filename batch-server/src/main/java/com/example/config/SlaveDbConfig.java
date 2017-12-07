package com.example.config;

import com.example.annotation.SlaveDb;
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
        annotationClass = SlaveDb.class,
        sqlSessionFactoryRef = "slaveSqlSessionFactory"
)
public class SlaveDbConfig extends DatabaseConfig {
    @Bean
    @ConfigurationProperties(prefix = "slave.datasource")
    public DataSourceProperties slaveDataSourceProp() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "slave.datasource.tomcat")
    public DataSource slaveDataSource() {
        return slaveDataSourceProp().initializeDataSourceBuilder().build();
    }

    @Bean
    public SqlSessionFactory slaveSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        configureSqlSessionFactory(sqlSessionFactoryBean, slaveDataSource());
        return sqlSessionFactoryBean.getObject();
    }
}
