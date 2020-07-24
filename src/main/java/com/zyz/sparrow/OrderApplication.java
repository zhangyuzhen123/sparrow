package com.zyz.sparrow;


import io.shardingsphere.jdbc.spring.boot.SpringBootConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ComponentScan;

//禁掉SpringBoot的自动配置数据源类
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class , SpringBootConfiguration.class})
@EnableDiscoveryClient
@RefreshScope
@ComponentScan("com.zyz")
public class OrderApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}
	
}
