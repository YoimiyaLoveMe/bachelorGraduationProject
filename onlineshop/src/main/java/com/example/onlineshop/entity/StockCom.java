package com.example.onlineshop.entity;


import lombok.Data;

import java.io.Serializable;

/**
 * 实体类
 */
@Data
public class StockCom implements Serializable {
    /**
     * 主键
     */

    private Integer id;

    private String name;

    private String category;

    private Integer stock;

}
