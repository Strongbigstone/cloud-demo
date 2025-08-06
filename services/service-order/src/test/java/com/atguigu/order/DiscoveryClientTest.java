package com.atguigu.order;

import com.alibaba.cloud.nacos.discovery.NacosDiscoveryClient;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

@SpringBootTest
public class DiscoveryClientTest {

    @Resource
    DiscoveryClient discoveryClient;

    @Resource
    NacosDiscoveryClient nacosDiscoveryClient;

    @Test
    void nacosDiscoveryClient() {

    }

    @Test
    void discoveryClient(){
        for (String service : discoveryClient.getServices()) {
            System.out.println("service= " + service);
            for (ServiceInstance instance : discoveryClient.getInstances(service)) {
                System.out.println("ip =" + instance.getHost() + ";post: " + instance.getPort());
            }
        }
    }
}
