package com.zyz.sparrow.config;

import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;
import java.util.Map;

public class DataSourceUtil {

    private static final String DATASOURCE_TYPE_DEFAULT = "com.alibaba.druid.pool.DruidDataSource";

    public static DataSource buildDataSource(Map<String, Object> dataSourceMap) {
        Object type = dataSourceMap.get("type");
        if (type == null) {
            type = DATASOURCE_TYPE_DEFAULT;
        }
        try {
            Class<? extends DataSource> dataSourceType;
            dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);
            String driverClassName = dataSourceMap.get("driverClassName").toString();
            String url = dataSourceMap.get("url").toString();
            String username = dataSourceMap.get("username").toString();
            String password = dataSourceMap.get("password").toString();
            // 自定义DataSource配置
            DataSourceBuilder factory = DataSourceBuilder.create().url(url).username(username).password(password).type(dataSourceType).driverClassName(driverClassName);
            return factory.build();
        } catch (Exception e) {

        }
        return null;
    }

}
