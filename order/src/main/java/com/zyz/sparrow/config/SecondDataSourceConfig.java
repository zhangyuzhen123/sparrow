package com.zyz.sparrow.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.google.common.collect.Lists;
import io.shardingsphere.core.api.ShardingDataSourceFactory;
import io.shardingsphere.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import io.shardingsphere.core.api.config.ShardingRuleConfiguration;
import io.shardingsphere.core.api.config.TableRuleConfiguration;
import io.shardingsphere.core.api.config.strategy.ShardingStrategyConfiguration;
import io.shardingsphere.core.api.config.strategy.StandardShardingStrategyConfiguration;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@MapperScan(basePackages = "com.zyz.sparrow.mapper.second", sqlSessionTemplateRef = "secondSqlSessionTemplate")
public class SecondDataSourceConfig {

    @Autowired
    private DataBaseProperties prop;

    @Autowired
    private PreciseShardingAlgorithm preciseShardingAlgorithm;

    //    创建数据源
    @Bean("dataSource")
    public DataSource dataSource(@Qualifier("ds0") DataSource ds0) throws SQLException {
        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();
        dataSourceMap.put("ds0", ds0);
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
//        shardingRuleConfig.getTableRuleConfigs().add(getOrderItemTableRuleConfiguration());

        Properties p = new Properties();
        p.setProperty("sql.show", Boolean.TRUE.toString());
        // 获取数据源对象
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new ConcurrentHashMap(), p);
        return dataSource;
    }


    // 创建SessionFactory
    @Bean(name = "secondSqlSessionFactory")
    public SqlSessionFactory secondSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setTypeAliasesPackage("");
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mybatis/mapper/second/*.xml"));

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

    @Bean("secondTransactionManger")
    public DataSourceTransactionManager secondTransactionManger(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    // 创建SqlSessionTemplate

    @Bean(name = "secondSqlSessionTemplate")
    public SqlSessionTemplate secondSqlSessionTemplate(@Qualifier("secondSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


    @Bean("ds0")
    public DataSource ds0() {
        Map<String, Object> dsMap = new HashMap<String, Object>();
        dsMap.put("type", prop.getType());
        dsMap.put("url", prop.getUrl());
        dsMap.put("username", prop.getUsername());
        dsMap.put("password", prop.getPassword());
        dsMap.put("driverClassName",prop.getDriverClassName());

        DruidDataSource ds = (DruidDataSource) DataSourceUtil.buildDataSource(dsMap);
        ds.setProxyFilters(Lists.newArrayList(statFilter()));
        // 每个分区最大的连接数
        ds.setMaxActive(20);
        // 每个分区最小的连接数
        ds.setMinIdle(5);

        return ds;
    }

    @Bean
    public Filter statFilter() {
        StatFilter filter = new StatFilter();
        filter.setSlowSqlMillis(5000);
        filter.setLogSlowSql(true);
        filter.setMergeSql(true);
        return filter;
    }

    TableRuleConfiguration getOrderTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration();
        result.setLogicTable("t_user");
        result.setActualDataNodes("ds0.t_user${0..1}");
        result.setTableShardingStrategyConfig(getStrategyConfiguration());
        result.setKeyGeneratorColumnName("id");
        return result;
    }

    ShardingStrategyConfiguration getStrategyConfiguration() {
        StandardShardingStrategyConfiguration standardStrategy = new StandardShardingStrategyConfiguration("city_id",preciseShardingAlgorithm);
        return standardStrategy;
    }


}