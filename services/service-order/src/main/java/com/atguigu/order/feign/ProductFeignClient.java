package com.atguigu.order.feign;

import com.atguigu.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-product")//feign客户端
public interface ProductFeignClient {

    //getMapping标注在controller上是接受类型
    //getMapping标注在feign客户端是以get请求发送
    @GetMapping("/product/{id}")
    Product getProductById(@PathVariable("id") String id);
}
