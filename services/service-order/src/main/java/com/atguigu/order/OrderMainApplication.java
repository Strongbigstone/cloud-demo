package com.atguigu.order;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusLanguageDriverAutoConfiguration;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@EnableFeignClients//开启feign远程调用
@EnableDiscoveryClient//服务发现 
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
        MybatisPlusAutoConfiguration.class, // 排除 MyBatis-Plus 的自动配置
        MybatisPlusLanguageDriverAutoConfiguration.class }) // 排除语言驱动配置
public class OrderMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderMainApplication.class, args);
    }
    //1.项目启动就监听配置文件变化
    //2.发生变化后拿到变化信息
    //3.发送邮件

    /**
     * 使用编码的方式实时监听配置里的变化
     * param nacosConfigManager
     * return
     */
    @Bean
    ApplicationRunner applicationRunner(NacosConfigManager nacosConfigManager) {
        return args -> {
            ConfigService configService = nacosConfigManager.getConfigService();
            configService.addListener("service-order.properties",
                    "order",
                    new Listener() {
                        @Override
                        public Executor getExecutor() {
                            return Executors.newFixedThreadPool(4);
                        }

                        @Override
                        public void receiveConfigInfo(String s) {
                            System.out.println("变化的配置" + s);
                            System.out.println("邮件发送。。。");
                        }
                    });
            System.out.println("监听中");
        };
    }
}
