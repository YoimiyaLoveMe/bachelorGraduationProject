package com.example.shopping.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 整机实体类
 * @author wu
 * 2022.4.17
 */
@Data
@TableName(value="order_combination")
public class OrderCombination implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id")
    private Integer id;

    @TableField(value = "customer_id")
    private String customerId;

    @TableField(value = "motherboard_id")
    private Integer motherboardId;

    @TableField(value = "motherboard_price")
    private BigDecimal motherboardPrice;

    @TableField(value = "motherboard_sale_price")
    private BigDecimal motherboardSalePrice;

    @TableField(value = "cpu_id")
    private Integer cpuId;

    @TableField(value = "cpu_price")
    private BigDecimal cpuPrice;

    @TableField(value = "cpu_sale_price")
    private BigDecimal cpuSalePrice;

    @TableField(value = "gc_id")
    private Integer gcId;

    @TableField(value = "gc_price")
    private BigDecimal gcPrice;

    @TableField(value = "gc_sale_price")
    private BigDecimal gcSalePrice;

    @TableField(value = "ram_id")
    private Integer ramId;

    @TableField(value = "ram_price")
    private BigDecimal ramPrice;

    @TableField(value = "ram_sale_price")
    private BigDecimal ramSalePrice;

    @TableField(value = "ssd_id")
    private Integer ssdId;

    @TableField(value = "ssd_price")
    private BigDecimal ssdPrice;

    @TableField(value = "ssd_sale_price")
    private BigDecimal ssdSalePrice;

    @TableField(value = "heatsink_id")
    private Integer heatsinkId;

    @TableField(value = "heatsink_price")
    private BigDecimal heatsinkPrice;

    @TableField(value = "heatsink_sale_price")
    private BigDecimal heatsinkSalePrice;

    @TableField(value = "power_id")
    private Integer powerId;

    @TableField(value = "power_price")
    private BigDecimal powerPrice;

    @TableField(value = "power_sale_price")
    private BigDecimal powerSalePrice;

    @TableField(value = "case_id")
    private Integer caseId;

    @TableField(value = "case_price")
    private BigDecimal casePrice;

    @TableField(value = "case_sale_price")
    private BigDecimal caseSalePrice;
    
    @TableField(value = "sum_price")
    private BigDecimal sumPrice;

    @TableField(value = "sum_sale_price")
    private BigDecimal sumSalePrice;

    private String state;

    private String address;

    private String logistics;

}
