package com.example.product.service.impl;

import com.atguigu.Product;
import com.example.product.mapper.ProductMapper;
import com.example.product.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Override
    public Product getProductById(String id) {
        Product product = new Product();
        product.setId(id);
        product.setPrice(new BigDecimal("99"));
        product.setName("苹果" + id);
        product.setNum(3);
        return product;
    }
}
