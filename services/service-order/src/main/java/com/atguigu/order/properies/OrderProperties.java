package com.atguigu.order.properies;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "order")//无需@Value注解和@RefreshScope 动态读取nacos配置
public class OrderProperties {

    String timeout;

    String autoConfirm;

    String dbUrl;
}
