package com.zyz.sparrow;


import io.shardingsphere.jdbc.spring.boot.SpringBootConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

//禁掉SpringBoot的自动配置数据源类
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class , SpringBootConfiguration.class})
@ComponentScan("com.zyz")
public class SparrowApplication {
	public static void main(String[] args) {
		SpringApplication.run(SparrowApplication.class, args);
	}
	
}
