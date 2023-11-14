package com.example.onlineshop.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 供应商实体类
 * @author wu
 */
@Data
@TableName(value="supplier")
public class Supplier implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id")
    private Integer id;

    private String name;

    @TableField(value = "callnumber")
    private String callNumber;

    private String information;

}
