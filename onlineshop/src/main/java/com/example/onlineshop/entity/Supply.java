package com.example.onlineshop.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 供货表实体类
 * @author wu
 */
@Data
@TableName(value="supply")
public class Supply implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id")
    private Integer id;

    @TableField(value = "supplier_id")
    private Integer supplierId;

    @TableField(value = "supplier_name")
    private String supplierName;

    private String category;

    @TableField(value = "goods_id")
    private Integer goodsId;

    @TableField(value = "goods_name")
    private String goodsName;

    private BigDecimal price;

    private Integer number;

    private String information;

    @TableField(value = "editor_id")
    private String editor;

}
