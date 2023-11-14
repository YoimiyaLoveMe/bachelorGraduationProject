package com.example.shopping.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 主板实体类
 * @author wu
 * 2022.3.27
 */
@Data
@TableName(value="goods_motherboard")
public class Motherboard implements Serializable {
    /**
     * 错误日志
     * @author wu
     * 2022.3.27.23:40
     * 问题简述：前端不输入id，entity中没有注解type = IdType.AUTO，数据库中id为普通int类型，无数据库中无论是否设置id自增,都出现添加错误
     * 排错：在允许输入id的情况下，添加功能本身没有错误,
     * 报错信息：Could not set property "id' of 'class com.example.shopping.entity.Motherboard' with value '1508107665480474626'
     * 已使用redis解决id自增
     */

    /**
     * 主键
     */
    @TableId(value = "id")
    private Integer id;

    private String name;

    private String brand;

    private String picture;

    private String chipset;

    @TableField(value = "cpu_slot")
    private String cpuSlot;

    @TableField(value = "board_type")
    private String boardType;

    @TableField(value = "memory_type")
    private String memoryType;

    @TableField(value = "memory_volume")
    private Integer memoryVolume;

    @TableField(value = "pci_e")
    private BigDecimal pciE;

    @TableField(value = "storage_interface")
    private String storageInterface;

    @TableField(value = "power_interface")
    private String powerInterface;

    @TableField(value = "cpu_power_interface")
    private String cpuPowerInterface;

    @TableField(value = "is_wifi")
    private Integer isWifi;


    @TableField(value = "is_super")
    private Integer isSuper;

    /**
     * MySQL数据库中decimal类型用BigDecimal映射
     */
    @TableField(value = "sale_price")
    private BigDecimal salePrice;

    private BigDecimal price;

    private String information;

    @TableField(value = "safe_stock")
    private Integer safeStock;

}
