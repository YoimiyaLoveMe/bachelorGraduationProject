package com.example.onlineshop.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 显卡实体类
 * @author wu
 * 2022.4.1
 */
@Data
@TableName(value="goods_graphics_card")
public class Graphicscard implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id")
    private Integer id;

    private String name;

    private String brand;

    private String picture;

    private String core;

    @TableField(value = "core_firm")
    private String coreFirm;

    @TableField(value = "core_series")
    private String coreSeries;

    @TableField(value = "ram_type")
    private String ramType;

    @TableField(value = "ram_volume")
    private Integer ramVolume;

    @TableField(value = "ram_bit")
    private Integer ramBit;

    @TableField(value = "io_interface")
    private String io;

    private Integer accuracy;

    private String resolution;

    @TableField(value = "pci_e")
    private BigDecimal pciE;

    @TableField(value = "power_waste")
    private Integer powerWaste;

    @TableField(value = "power_interface")
    private Integer powerInterface;

    private Integer size;

    private Integer performance;

    @TableField(value = "sale_price")
    private BigDecimal salePrice;

    private BigDecimal price;

    private String information;

    @TableField(value = "safe_stock")
    private Integer safeStock;

}
