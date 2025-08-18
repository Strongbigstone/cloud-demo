package com.atguigu;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("db_product")
public class Product {
    @TableId("id")
    private String id;

    @TableField("price")
    private BigDecimal price;

    @TableField("name")
    private String name;

    @TableField("num")
    private int num;
}
