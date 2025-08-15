package com.atguigu.order.service.impl;

import com.atguigu.Order;
import com.atguigu.Product;
import com.atguigu.order.feign.ProductFeignClient;
import com.atguigu.order.mapper.secondary.SecondaryOrderMapper;
import com.atguigu.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;

@Slf4j
@Service
public class SecondaryOrderServiceImpl extends ServiceImpl<SecondaryOrderMapper, Order> implements OrderService {

    @Resource
    ProductFeignClient productFeignClient;

    @Resource(name = "secondarySqlSessionTemplate")
    SqlSessionTemplate sqlSessionTemplate;;

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

   /* @Override
    @Transactional(transactionManager = "secondaryTransactionManager")
    public Order queryOrder(String orderId) {
        return sqlSessionTemplate.selectOne("com.atguigu.order.mapper.secondary.SecondaryOrderMapper.selectById", orderId);
    }*/

    @Override
    public Order queryOrder(String orderId) {
        return getById(orderId);
    }
}
