package com.atguigu.order.service;


import com.atguigu.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface OrderService extends IService<Order> {
    Order createOrder(String productId, String userId);

    Order queryOrder(String orderId);

    List<Order> queryAll();
}
