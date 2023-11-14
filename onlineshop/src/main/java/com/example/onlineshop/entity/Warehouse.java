package com.example.onlineshop.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 库位实体类
 */
@Data
@TableName(value="warehouse")
public class Warehouse implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id")
    private Integer id;

    private String address;

    private Integer volume;

    private String information;

}
