package com.atguigu.order.mapper.secondary;

import com.atguigu.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SecondaryOrderMapper extends BaseMapper<Order> {

    List<Order> queryList(Order order);
}
