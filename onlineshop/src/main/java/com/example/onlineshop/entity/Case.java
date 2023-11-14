package com.example.onlineshop.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 机箱实体类
 * @author wu
 * 2022.4.1
 */
@Data
@TableName(value="goods_case")
public class Case implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id")
    private Integer id;

    private String name;

    private String brand;

    private String picture;

    private String size;

    private String structure;

    @TableField(value = "motherboard_limit")
    private String mbLimit;

    @TableField(value = "heatsink_limit")
    private Integer htLimit;

    @TableField(value = "gc_limit")
    private Integer gcLimit;

    @TableField(value = "power_limit")
    private String pwLimit;

    @TableField(value = "pci_slot_number")
    private Integer pciSlot;

    @TableField(value = "sale_price")
    private BigDecimal salePrice;

    private BigDecimal price;

    private String information;

    @TableField(value = "safe_stock")
    private Integer safeStock;


}
