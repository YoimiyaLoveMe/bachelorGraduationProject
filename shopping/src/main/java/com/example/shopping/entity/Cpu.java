package com.example.shopping.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 芯片实体类
 * @author wu
 * 2022.3.28
 */
@Data
@TableName(value="goods_cpu")
public class Cpu implements Serializable {

    /**
     * 主键
     */
    @TableId(value = "id")
    private Integer id;

    private String name;

    private String brand;

    private String picture;

    private String series;

    @TableField(value = "core_number")
    private Integer coreNumber;

    @TableField(value = "thread_number")
    private Integer threadNumber;

    private String slot;

    private Integer accuracy;

    @TableField(value = "core_cord")
    private String coreCord;

    private String structure;

    @TableField(value = "basic_frequency")
    private BigDecimal basicFrequency;

    private BigDecimal turbo;

    private Integer tdp;

    private Integer mtp;

    @TableField(value = "memory_volume")
    private Integer memoryVolume;

    @TableField(value = "memory_type")
    private String memoryType;

    @TableField(value = "memory_channel")
    private Integer memoryChannel;

    @TableField(value = "pci_e")
    private BigDecimal pciE;

    @TableField(value = "integrated_cpu")
    private String integratedCpu;

    private Integer performance;

    @TableField(value = "sale_price")
    private BigDecimal salePrice;

    private BigDecimal price;

    private String information;

    @TableField(value = "safe_stock")
    private Integer safeStock;

}
