package com.zyz.sparrow.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.sun.shard.mapper.first",sqlSessionTemplateRef ="firstSqlSessionTemplate")
public class FirstDataSourceConfig {

    @Autowired
    private DataBaseProperties prop;

    //    创建数据源
    @Bean(name = "firstDS")
    @Primary
    public DataSource getFirstDataSource() {DataSource build =  DataSourceBuilder.create()
            .driverClassName(prop.driverClassName)
            .url(prop.url)
            .username(prop.username)
            .password(prop.password)
            .build();

        return build;
    }


    // 创建SessionFactory
    @Bean(name = "firstSqlSessionFactory")
    @Primary
    public SqlSessionFactory firstSqlSessionFactory(@Qualifier("firstDS") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/first/*.xml"));
        return bean.getObject();
    }

    // 创建事务管理器

    @Bean("firstTransactionManger")
    @Primary
    public DataSourceTransactionManager firstTransactionManger(@Qualifier("firstDS") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    // 创建SqlSessionTemplate

    @Bean(name = "firstSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate firstSqlSessionTemplate(@Qualifier("firstSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }


    private Class getType(String type) {
        try {
            return Class.forName(type);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


}