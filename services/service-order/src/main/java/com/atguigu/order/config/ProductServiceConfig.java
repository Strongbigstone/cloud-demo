package com.atguigu.order.config;

import feign.Logger;
import feign.Retryer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ProductServiceConfig {

    //重试器
    @Bean
    public Retryer retryer(){
        return new Retryer.Default();
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @LoadBalanced//负载均衡注解
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
