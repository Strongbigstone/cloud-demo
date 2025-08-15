package com.atguigu.order.mapper.primary;

import com.atguigu.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PrimaryOrderMapper extends BaseMapper<Order> {

    List<Order> queryList(Order order);
}
