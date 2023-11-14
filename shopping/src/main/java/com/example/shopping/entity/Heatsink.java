package com.example.shopping.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 散热器实体类
 * @author wu
 * 2022.4.1
 */
@Data
@TableName(value="goods_heatsink")
public class Heatsink implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id")
    private Integer id;

    private String name;

    private String brand;

    private String picture;

    private Integer cfm;

    private Integer size;

    @TableField(value = "sale_price")
    private BigDecimal salePrice;

    private BigDecimal price;

    private String information;

    @TableField(value = "safe_stock")
    private Integer safeStock;

}
