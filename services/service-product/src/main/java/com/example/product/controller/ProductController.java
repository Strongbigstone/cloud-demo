package com.example.product.controller;

import com.atguigu.Product;
import com.example.product.annotations.DynamicDataSource;
import com.example.product.constants.DataSourceConstants;
import com.example.product.service.ProductService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@RestController()
public class ProductController {

    @Resource
    ProductService productService;

    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable("id") String id) throws InterruptedException {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        System.out.println(request.getHeader("X-Token"));
        System.out.println("hello");
        Thread.sleep(5000);
        return productService.getProductById(id);
    }

    @DynamicDataSource(value = DataSourceConstants.SECONDARY_DS)
    @GetMapping("/productById/{id}")
    public Product getProductById(@PathVariable("id") String id){
        return productService.getById(id);
    }

    @DynamicDataSource
    @GetMapping("/productById2/{id}")
    public Product getProductById2(@PathVariable("id") String id){
        return productService.getById(id);
    }
}
