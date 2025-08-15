package com.atguigu.order.controller;


import com.atguigu.Order;
import com.atguigu.order.mapper.secondary.SecondaryOrderMapper;
import com.atguigu.order.properies.OrderProperties;
import com.atguigu.order.service.OrderService;
import com.atguigu.order.service.impl.SecondaryOrderServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/order")
//@RefreshScope//自动刷新
@RestController
public class OrderController {

    /*    @Value("${order.timeout}")
        String timeout;

        @Value("${order.auto.confirm}")
        String autoConfirm;*/
    @Resource
    OrderProperties orderProperties;

    @Resource
    SecondaryOrderServiceImpl orderService;

    @GetMapping("/config")
    public String createOrder() {
        return String.format("超时时间：%s, 自动确认：%s, 数据库地址：%s",
                orderProperties.getTimeout(),
                orderProperties.getAutoConfirm(),
                orderProperties.getDbUrl());
    }

    @GetMapping("/create")
    public Order createOrder(@RequestParam("userId") String userId,
                             @RequestParam("productId") String productId) {
        return orderService.createOrder(productId, userId);
    }

    @GetMapping("/query/{orderId}")
    public Order queryOrder(@PathVariable("orderId") String orderId) {
        return orderService.queryOrder(orderId);
    }
}
