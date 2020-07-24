package com.zyz.sparrow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

public class NacosConsumerConfiguration {
    @Bean(name = "restTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
