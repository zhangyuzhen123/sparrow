package com.zyz.sparrow.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.google.common.collect.Lists;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
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
import java.util.List;

@Configuration
@MapperScan(basePackages = "com.zyz.sparrow.mapper.first",sqlSessionTemplateRef ="firstSqlSessionTemplate")
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
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setTypeAliasesPackage("");
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/first/*.xml"));

        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        sqlSessionFactory.setConfiguration(configuration);


        List<Interceptor> interceptorList = Lists.newArrayList();
        //分页插件
        interceptorList.add(new PaginationInterceptor());
        //乐观锁插件
        interceptorList.add(new OptimisticLockerInterceptor());

        Interceptor[] interceptorArray = new Interceptor[interceptorList.size()];
        sqlSessionFactory.setPlugins(interceptorList.toArray(interceptorArray));
        return sqlSessionFactory.getObject();
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

}