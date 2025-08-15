package com.atguigu.order.service.impl;

import com.atguigu.Order;
import com.atguigu.Product;
import com.atguigu.order.feign.ProductFeignClient;
import com.atguigu.order.mapper.primary.OrderMapper;
import com.atguigu.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Resource
    RestTemplate restTemplate;
    @Resource
    DiscoveryClient discoveryClient;
    @Resource
    LoadBalancerClient loadBalancerClient;
    @Resource
    ProductFeignClient productFeignClient;

    @Override
    public Order createOrder(String productId, String userId) {
        Order order = new Order();
        order.setId(productId);
        Product remoteProduct = productFeignClient.getProductById(productId);
        //总价格
        order.setTotalAmount(remoteProduct.getPrice().multiply(new BigDecimal(remoteProduct.getNum())));
        order.setUserId(userId);
        order.setNickName("里斯");
        order.setAddress("硅谷");
        order.setProductList(Collections.singletonList(remoteProduct));

        return order;
    }

    @Override
    public Order queryOrder(String orderId) {
        return getById(orderId);
    }

    //负载均衡调用 1
    public Product getRemoteProductWithLoadBalanceAnnotation(Long productId){
        String url = "http://service-product/product/" + productId;
        //restTemplate会根据service-product动态获取IP端口
        return restTemplate.getForObject(url, Product.class);
    }

    //负载均衡调用 2
    public Product getRemoteProductWithLoadBalance(Long productId){
        ServiceInstance serviceInstance = loadBalancerClient.choose("service-product");
        //http://localhost:8001/product/4
        String url = "http://"+ serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/product/" + productId;
        log.info("调用服务url：{}", url);
        return restTemplate.getForObject(url, Product.class);
    }

    //初版
    public Product getRemoteProduct(Long productId){
        List<ServiceInstance> instances = discoveryClient.getInstances("service-product");
        ServiceInstance serviceInstance = instances.get(0);
        //http://localhost:8001/product/4
        String url = "http://"+ serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/product/" + productId;
        return restTemplate.getForObject(url, Product.class);
    }
}
