package com.atguigu.order.service;


import com.atguigu.Order;

public interface OrderService {
    Order createOrder(Long productId, Long userId);
}
