package com.example.onlineshop.entity;

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
@TableName(value="")
public class Normal implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id")
    private Integer id;

    private String name;

    private String brand;

    @TableField(value = "")
    private String aa;

    private BigDecimal price;

    private String information;

    @TableField(value = "safe_stock")
    private Integer safeStock;

    @TableField(value = "is_sale")
    private Integer isSale;



}
