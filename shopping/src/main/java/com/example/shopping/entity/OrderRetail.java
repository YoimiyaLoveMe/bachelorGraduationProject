package com.example.shopping.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 实体类
 */
@Data
@TableName(value="order_retail")
public class OrderRetail implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id")
    private Integer id;

    @TableField(value = "customer_id")
    private String customerId;

    @TableField(value = "goods_id")
    private Integer goodsId;

    @TableField(value = "goods_name")
    private String goodsName;

    private Integer number;

    private BigDecimal price;

    @TableField(value = "sum_price")
    private BigDecimal sumPrice;

    @TableField(value = "sum_sale_price")
    private BigDecimal sumSalePrice;

    private String state;

    private String address;

    private String logistics;

}
