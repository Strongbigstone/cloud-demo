package com.example.product;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusLanguageDriverAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.aspectj.annotation.BeanFactoryAspectInstanceFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        MybatisPlusAutoConfiguration.class,
        MybatisPlusLanguageDriverAutoConfiguration.class
})
@EnableAspectJAutoProxy(proxyTargetClass = true) // 强制使用CGLIB代理
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ) // 使用AspectJ事务
@EnableDiscoveryClient
@MapperScan(basePackages = "com.example.product.mapper")
public class ProductMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductMainApplication.class, args);
    }

    @Bean
    public BeanFactoryAspectInstanceFactory aspectInstanceFactory(BeanFactory beanFactory) {
        return new BeanFactoryAspectInstanceFactory(beanFactory, "dynamicDataSourceAspect");
    }
}
