package com.example.onlineshop.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 电源实体类
 * @author wu
 * 2022.4.1
 */
@Data
@TableName(value="goods_power")
public class Power implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id")
    private Integer id;

    private String name;

    private String brand;

    private String picture;

    @TableField(value = "rated_power")
    private Integer ratedPower;

    @TableField(value = "grade_80plus")
    private String grade80plus;

    @TableField(value = "gc_interface_number")
    private Integer gcInterNumber;

    @TableField(value = "ssd_interface_number")
    private Integer ssdInterNumber;

    @TableField(value = "ot_interface_number")
    private Integer otInterNumber;

    private String module;

    private String size;

    @TableField(value = "sale_price")
    private BigDecimal salePrice;

    private BigDecimal price;

    private String information;

    @TableField(value = "safe_stock")
    private Integer safeStock;
}
