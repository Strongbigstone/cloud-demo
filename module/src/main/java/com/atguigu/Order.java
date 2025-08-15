package com.atguigu;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@TableName("db_order")
public class Order {

    @TableId("id")
    private String id;

    @TableField("total_amount")
    private BigDecimal totalAmount;

    @TableField("user_id")
    private String userId;

    @TableField("nick_name")
    private String nickName;

    @TableField("address")
    private String address;

    @TableField(exist = false)
    private List<Product> productList;
}
