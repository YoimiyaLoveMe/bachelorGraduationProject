package com.example.onlineshop.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 采购单实体类
 * @author wu
 */
@Data
@TableName(value="purchase_order")
public class Purchase implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id")
    private Integer id;

    @TableField(value = "goods_category")
    private String category;

    @TableField(value = "goods_id")
    private Integer goodsId;

    @TableField(value = "goods_name")
    private String goodsName;

    @TableField(value = "supply_id")
    private Integer supplyId;

    @TableField(value = "supplier_id")
    private Integer supplierId;

    @TableField(value = "supplier_name")
    private String supplierName;

    private BigDecimal price;

    private Integer number;

    @TableField(value = "sum_price")
    private BigDecimal sumPrice;

    private String information;

    @TableField(value = "worker_purchase_id")
    private String purchaseId;

    private String state;

    @TableField(value = "worker_examine_id")
    private String examineId;

}
