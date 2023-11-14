package com.example.onlineshop.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 销售商品通用实体类
 * @author wu
 * 2022.4.21
 */
@Data
public class GoodsCom implements Serializable {

    private Integer id;

    private String name;

    private String brand;

    private String picture;

    private BigDecimal price;

    private BigDecimal salePrice;

    private String information;

    private String category;

}
