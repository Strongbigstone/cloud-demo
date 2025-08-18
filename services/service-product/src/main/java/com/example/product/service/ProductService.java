package com.example.product.service;

import com.atguigu.Product;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ProductService extends IService<Product> {
    Product getProductById(String id);
}
